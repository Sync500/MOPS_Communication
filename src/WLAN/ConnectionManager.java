/**
 * 
 */
package WLAN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author matthias
 * 
 */
public class ConnectionManager implements ConnectionManagerI, Runnable, MessageListener {
  
  private static final int            MESSAGE_QUEUE_SIZE       = 20;
  private static final int            DEFAULT_BLOCKING_TIMEOUT = 2000;
  
  private static Logger               log                      = LoggerFactory.getLogger(ConnectionManager.class);
  
  /* TODO: this is not very nice implemented so far... */
  private List<CommunicationHardware> availableHardware;
  private ArrayBlockingQueue<Message> messageQueue;
  private Map<Integer, Message>       typedMessages;
  private List<MessageListener>       messageListener;
  private Set<Integer>                trackedMessages;
  private Thread                      sendThread;
  private Vector<Thread>              receiveThreads;
  
  public ConnectionManager() {
    availableHardware = new ArrayList<CommunicationHardware>(3);
    messageQueue = new ArrayBlockingQueue<Message>(MESSAGE_QUEUE_SIZE);
    typedMessages = new HashMap<Integer, Message>();
    messageListener = new Vector<MessageListener>(1);
    trackedMessages = new ConcurrentSkipListSet<Integer>();
    receiveThreads = new Vector<Thread>(2);
    sendThread = new Thread(this);
    sendThread.start();
  }
  
  @Override
  public CommunicationDevice addDevice(CommunicationHardware hardware) {
    CommunicationDevice device = new CommunicationDevice();
    availableHardware.add(hardware);
    device.setdeviceId(availableHardware.size() - 1);
    Thread newReceiver = new Thread(new ConnectionManagerReader(hardware, this));
    receiveThreads.add(newReceiver);
    newReceiver.start();
    return device;
  }
  
  @Override
  public void removeDevice(CommunicationDevice device) {
    availableHardware.set(device.getdeviceId(), null);
    receiveThreads.get(device.getdeviceId()).interrupt();
  }
  
  @Override
  public void addToQueue(Message message) {
    if (message.getType() >= 0) synchronized (typedMessages) {
      typedMessages.put(message.getType(), message);
    }
    if (message.getIsBlocking()) trackedMessages.add(message.getUuid());
    messageQueue.add(message);
  }
  
  @Override
  public void registerMessageListener(MessageListener listener) {
    messageListener.add(listener);
  }
  
  @Override
  public boolean getMessageState(int messageId) {
    return !trackedMessages.contains(messageId);
  }
  
  @Override
  public boolean waitMessageState(int messageId, int timeout) {
    synchronized (trackedMessages) {
      if (trackedMessages.contains(messageId)) {
        try {
          trackedMessages.wait(timeout);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    return getMessageState(messageId);
  }
  
  @Override
  public void messageReceived(byte[] message) {
    for (MessageListener l : messageListener) {
      l.messageReceived(message);
    }
    
  }
  
  @Override
  public void run() {
    Message m;
    try {
      while (true) {
        m = messageQueue.take();
        boolean messageSent = false;
        for (CommunicationHardware hw : availableHardware) {
          if (!messageSent && hw.linkState(0)) {
            try {
              if (m.getIsBlocking()) {
                if (hw.sendBlock(m.getMessage(), DEFAULT_BLOCKING_TIMEOUT)) messageSent = true;
              } else {
                hw.send(m.getMessage());
                messageSent = true;
              }
            } catch (IOException e) {
              log.error("Communication Error: " + e.getLocalizedMessage());
            }
          }
        }
        if (messageSent) {
          if (m.getIsBlocking()) {
            trackedMessages.remove(m.getUuid());
            synchronized (trackedMessages) {
              trackedMessages.notify();
            }
          }
          
        } else {
          /* readd message at the end of queue for transmission... */
          messageQueue.put(m);
        }
        
      }
    } catch (InterruptedException e) {
      /* exit on interruption */
    }
    
  }
  
  @Override
  public CommunicationHardware getCommunicationHardware(CommunicationDevice device) {
    return availableHardware.get(device.deviceId);
  }
  
}
