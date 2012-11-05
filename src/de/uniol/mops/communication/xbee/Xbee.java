/**
 * 
 */
package de.uniol.mops.communication.xbee;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.AtCommand;
import com.rapplogic.xbee.api.AtCommandResponse;
import com.rapplogic.xbee.api.IXBee;
import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.XBeeTimeoutException;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.api.zigbee.ZNetTxStatusResponse;

/**
 * @author matthias
 * 
 *         Implement Communication over Xbee Hardware.
 *         Xbee uses Rapplogic xbee-api in API mode.
 *         As we use Xbee Pro 868 devices, there is ZNet support.
 *         We deliver packages over a point to point link with given addresses.
 *         As CRC-Checking and retransmission is handled by xbee itself, we just
 *         need to use that for our transfer mechanisms.
 *         Minimalistic protocol: First byte of transmission is status:
 *         7 6 5 4 3 2 1 0
 *         LAST_PACKET: 1 if this is the last packet of a multipacket transmission.
 * 
 * 
 *         Idea:
 *         sendWait
 * 
 */
abstract public class Xbee implements WLAN.CommunicationHardware, Runnable, PacketListener {
  
  Thread                        thread;
  
  protected static Logger       log               = LoggerFactory.getLogger(Xbee.class);
  
  /**
   * last known rtt in ms.
   */
  private int                   rtt;
  
  /**
   * timestamp of last measured rtt.
   */
  private long                  rttTime;
  
  /**
   * maximum validity for rtt measurement. After that time,
   * link state will be unknown.
   */
  private final static long     MAX_RTT_VALIDITY  = 5000;
  
  private IXBee                 xbee;
  
  private int                   maxPacketSize;
  
  private final static int      SEND_WAIT_TIMEOUT = 3000;
  
  private final static int      RX_QUEUE_SIZE     = 16;
  
  private XBeeAddress64         endpointAddress;
  
  private Queue<int[]>          packets;
  private BlockingQueue<byte[]> rxPackets;
  private List<Integer>         rxPacket;
  
  private Object                sendBlock         = new Object();
  
  /**
   * Establishes connection to XBee and initializes it.
   * 
   * @param xbeeConnection
   *          opened xbee connection
   * @throws XBeeException
   */
  protected void startXbee(IXBee xbeeConnection) throws Exception {
    xbee = xbeeConnection;
    AtCommand q = new AtCommand("NP", null, xbee.getNextFrameId());
    AtCommandResponse resp = (AtCommandResponse) xbee.sendSynchronous(q, 5000);
    if (!resp.isOk() || resp.getValue().length != 2) {
      throw new XBeeException("Did not receive valid max. RF Payload response");
    }
    
    maxPacketSize = 100;
    
    if (resp.getValue()[0] != 1 || resp.getValue()[1] != 0) {
      log.warn("Xbee NP does not match expected 0x100. Fallback to PacketSize 32");
      maxPacketSize = 32;
    }
    
    log.debug("Xbee Packet Size: max. " + maxPacketSize + "bytes");
    
    rxPackets = new ArrayBlockingQueue<byte[]>(RX_QUEUE_SIZE);
    xbee.addPacketListener(this);
    
    thread = new Thread(this);
    thread.start();
    log.info("Xbee startup complete!");
  }
  
  /**
   * send a part of a higher level packet. Return with failure on timeout.
   * 
   * @param data
   * @return Transmission state: success or failure
   * @throws XBeeException
   *           on hw communication failure, has nothing to do with RF link
   */
  private boolean sendPacketPart(int[] data) throws XBeeException {
    ZNetTxRequest txrequest = new ZNetTxRequest(endpointAddress, data);
    txrequest.setFrameId(xbee.getNextFrameId());
    try {
      ZNetTxStatusResponse status = (ZNetTxStatusResponse) xbee.sendSynchronous(txrequest, SEND_WAIT_TIMEOUT);
      AtCommandResponse resp;
      
      resp = (AtCommandResponse) xbee.sendSynchronous(new AtCommand("DC", null, xbee.getNextFrameId()), 5000);
      if (!resp.isOk()) throw new XBeeException("Read duty cycle failed!");
      
      log.debug("Xbee duty cycle: " + resp.getValue()[0]);
      
      if (status.getDeliveryStatus() != ZNetTxStatusResponse.DeliveryStatus.SUCCESS) {
        return false;
      }
    } catch (XBeeTimeoutException e) {
      return false;
    }
    return true;
  }
  
  private boolean sendPacket(int[] data) throws XBeeException {
    XbeePacketPartFlag flag = new XbeePacketPartFlag();
    int[] packetData;
    int nextPayloadLength = data.length;
    int payloadPosition = 0;
    
    /* first packet part */
    flag.setNewPacket(true);
    if (nextPayloadLength > maxPacketSize - 1) nextPayloadLength = maxPacketSize - 1;
    else flag.setLastPacketPart(true);
    if (log.isDebugEnabled()) {
      log.debug("Sending packet of " + data.length + "bytes. Part 1, flags: " + flag + "length " + nextPayloadLength);
    }
    packetData = new int[nextPayloadLength + 1];
    System.arraycopy(data, 0, packetData, 1, nextPayloadLength);
    packetData[0] = flag.getData();
    if (!sendPacketPart(packetData)) return false;
    
    /* second ... Nth packet part */
    flag.setNewPacket(false);
    flag.setContinuation(true);
    while (payloadPosition + nextPayloadLength < data.length) {
      payloadPosition += nextPayloadLength;
      nextPayloadLength = data.length - payloadPosition;
      if (nextPayloadLength > maxPacketSize - 1) nextPayloadLength = maxPacketSize - 1;
      else flag.setLastPacketPart(true);
      if (log.isDebugEnabled()) {
        log.debug("part " + payloadPosition + "(+" + nextPayloadLength + "), flags: " + flag);
      }
      packetData = new int[nextPayloadLength + 1];
      System.arraycopy(data, payloadPosition, packetData, 1, nextPayloadLength);
      packetData[0] = flag.getData();
      if (!sendPacketPart(packetData)) return false;
    }
    return true;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {
    packets = new LinkedList<int[]>();
    int[] data = null;
    while (true) {
      while (data == null) {
        synchronized (packets) {
          data = packets.poll();
          if (data == null) {
            try {
              packets.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      }
      
      try {
        synchronized (sendBlock) {
          sendPacket(data);
        }
      } catch (XBeeException e) {
        /* TODO we will get this exception on HW error, maybe do something more like power cycle? */
        log.error("Xbee error: " + e.getMessage());
      }
      data = null;
    }
  }
  
  private void enqueue(int[] data) {
    synchronized (packets) {
      packets.add(data);
      packets.notify();
    }
  }
  
  /**
   * Enqueue a packet for transmission and return on success/failure.
   * 
   * @param data
   *          packet data
   * 
   *          Not thread safe!
   * @return
   */
  private boolean enqueueBlock(int[] data) {
    synchronized (sendBlock) {
      try {
        return sendPacket(data);
      } catch (XBeeException e) {
        log.error("Xbee error: " + e.getMessage());
      }
    }
    return false;
  }
  
  @Override
  public void processResponse(XBeeResponse response) {
    if (response.getApiId() == ApiId.ZNET_RX_RESPONSE) {
      ZNetRxResponse rx = (ZNetRxResponse) response;
      int[] data = rx.getData();
      if (data.length > 0) {
        XbeePacketPartFlag flag = new XbeePacketPartFlag(data[0]);
        if (!flag.isValid()) {
          /* TODO log invalid packet */
        } else if (rxPacket == null && !flag.isNewPacket()) {
          /* TODO log continuation without start */
        } else if (flag.isPingRequest() || flag.isPongResponse()) {
          /* no response, ping can be obtained using lower level methods. */
        } else if (flag.isNewPacket()) {
          int c = (data.length >= maxPacketSize) ? 4 * maxPacketSize : data.length - 1;
          rxPacket = new ArrayList<Integer>(c);
        }
        
        if (data.length > 1) {
          if (rxPacket != null) {
            
            for (int i = 1; i < data.length; ++i) {
              rxPacket.add(Integer.valueOf(data[i]));
            }
          } else {
            /* TODO: log tx start packet missed! */
          }
        }
        
        if (flag.isLastPacketPart()) {
          if (rxPacket != null) {
            byte[] packet = new byte[rxPacket.size()];
            for (int i = 0; i < rxPacket.size(); ++i)
              packet[i] = rxPacket.get(i).byteValue();
            
            if (!rxPackets.offer(packet)) {
              /* TODO log buffer overflow */
            }
            rxPacket = null;
          }
        }
      }
    }
  }
  
  /**
   * reads a packet from the input, blocking until it becomes available.
   * 
   * @return packet
   * @throws InterruptedException
   */
  @Override
  public byte[] read() throws InterruptedException {
    return rxPackets.take();
  }
  
  private static int[] dataToInt(byte[] data) {
    IntBuffer res = IntBuffer.allocate(data.length);
    for (byte d : data) {
      res.put(d);
    }
    return res.array();
  }
  
  private boolean rttValid() {
    return rttTime >= System.currentTimeMillis() - MAX_RTT_VALIDITY;
  }
  
  @Override
  /**
   * nothing is done on connect for xbee, as we communicate stateless.
   */
  public void connect() {
    /**
     * nothing
     */
  }
  
  @Override
  public void send(byte[] data) throws IOException {
    enqueue(dataToInt(data));
  }
  
  @Override
  public boolean sendBlock(byte[] data, int timeout) throws IOException {
    /** TODO timeout missing */
    return enqueueBlock(dataToInt(data));
  }
  
  @Override
  public int getThroughput() {
    if (rttValid()) return 2400 / 10;
    return 0;
  }
  
  @Override
  public int getLatency() {
    if (rttValid()) return rtt;
    return -rtt;
  }
  
  public int[] getXbeeAddress() {
    AtCommand q;
    AtCommandResponse resp;
    int[] res = new int[8];
    q = new AtCommand("SH", null, xbee.getNextFrameId());
    try {
      resp = (AtCommandResponse) xbee.sendSynchronous(q, 5000);
      if (!resp.isOk()) return null;
      // throw new XBeeException("Invalid response to ATSH command");
      
      System.arraycopy(resp.getValue(), 0, res, 0, 4);
      
      q = new AtCommand("SL", null, xbee.getNextFrameId());
      resp = (AtCommandResponse) xbee.sendSynchronous(q, 5000);
      if (!resp.isOk()) return null;
      
      System.arraycopy(resp.getValue(), 0, res, 4, 4);
      
    } catch (XBeeException e) {
      log.error(e.toString());
    }
    return res;
  }
  
  public void setDestinationAddress(int[] address) {
    endpointAddress = new XBeeAddress64(address);
  }
  
  public void setEncryptionKey(int[] key) {
    AtCommandResponse resp;
    try {
      resp = (AtCommandResponse) xbee.sendSynchronous(new AtCommand("KY", key, xbee.getNextFrameId()), 5000);
      if (!resp.isOk()) {
        log.error("Setting encryption key failed!");
        // throw new XBeeException("Setting encryption key failed!");
      }
    } catch (XBeeTimeoutException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (XBeeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public void setEncryption(boolean on) {
    AtCommandResponse resp;
    int onOff = on ? 1 : 0;
    
    try {
      resp = (AtCommandResponse) xbee.sendSynchronous(new AtCommand("EE", new int[] { onOff }, xbee.getNextFrameId()),
          5000);
      
      maxPacketSize = 100;
      resp = (AtCommandResponse) xbee.sendSynchronous(new AtCommand("NP", null, xbee.getNextFrameId()), 5000);
      if (resp.getValue()[0] != 1 || resp.getValue()[1] != 0) {
        log.warn("Xbee NP does not match expected 0x100. Fallback to PacketSize 32");
        maxPacketSize = 32;
      }
    } catch (XBeeTimeoutException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (XBeeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
  
  @Override
  public boolean linkState(int timeout) {
    
    return true;
  }
  
}
