/**
 * 
 */
package WLAN;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import de.uniol.mops.communication.xbee.*;

/**
 * @author matthias; Kamran for PG-MOPS
 * 
 */
public class CommunicationImpl implements Communication, MessageListener {
  
  private ArrayBlockingQueue<CommunicationInput> incomingList;
  private ConnectionManager                      connectionManager;
  
  public CommunicationImpl() {
    incomingList = new ArrayBlockingQueue<CommunicationInput>(16);
    connectionManager = new ConnectionManager();
    connectionManager.registerMessageListener(this);
  }
  
  /*
   * Convert an Object into byte[]
   */
  private static byte[] objectToByteArray(Object object) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(object);
    return baos.toByteArray();
  }
  
  /*
   * Convert a byte[] into Object
   */
  private static Object byteArrayToObject(byte[] object) throws IOException, ClassNotFoundException {
    ByteArrayInputStream bais = new ByteArrayInputStream(object);
    ObjectInputStream ois = new ObjectInputStream(bais);
    return ois.readObject();
  }
  
  @Override
  public CommunicationDevice addSocketClient(String ip, int port) {
    try {
      new Client();
      WlanHardwareImpl impl = new WlanHardwareImpl();
      CommunicationDevice device = connectionManager.addDevice(impl);
      device.setType(CommunicationDeviceType.WlanSocket);
      return device;
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return null;
  }
  
  @Override
  public CommunicationDevice addSocketServer(String ip, int port) {
    WlanManager wlan = new WlanManager(ip,port);
    WlanHardwareImpl impl = new WlanHardwareImpl();
    CommunicationDevice device = connectionManager.addDevice(impl);
    device.setType(CommunicationDeviceType.WlanSocket);
    return device;
  }
  
  @Override
  public CommunicationDevice addXbeeSocketDestination(String ip, int port) {
    Xbee xbee;
    try {
      xbee = new XbeeSocket(ip, port);
      xbee.connect();
      CommunicationDevice device = connectionManager.addDevice(xbee);
      device.setType(CommunicationDeviceType.Xbee);
      return device;
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
  
  @Override
  public CommunicationDevice addXbeeSerialDestination(String port, int baudrate) {
    Xbee xbee;
    try {
      xbee = new XbeeSerial(port, baudrate);
      xbee.connect();
      CommunicationDevice device = connectionManager.addDevice(xbee);
      device.setType(CommunicationDeviceType.Xbee);
      return device;
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
  
  @Override
  public void setXbeeDestinationAddr(CommunicationDevice device, int[] addr) {
    if (device.getType() == CommunicationDeviceType.Xbee) {
      Xbee xbee = (Xbee) connectionManager.getCommunicationHardware(device);
      xbee.setDestinationAddress(addr);
    }
  }
  
  @Override
  public int[] getXbeeAddr(CommunicationDevice device) {
    if (device.getType() == CommunicationDeviceType.Xbee) {
      Xbee xbee = (Xbee) connectionManager.getCommunicationHardware(device);
      return xbee.getXbeeAddress();
    }
    return null;
  }
  
  @Override
  public void setXbeeEncryptionKey(CommunicationDevice device, int[] key) {
    if (device.getType() == CommunicationDeviceType.Xbee) {
      Xbee xbee = (Xbee) connectionManager.getCommunicationHardware(device);
      xbee.setEncryptionKey(key);
    }
    
  }
  
  @Override
  public void setXbeeEncryption(CommunicationDevice device, boolean on) {
    if (device.getType() == CommunicationDeviceType.Xbee) {
      Xbee xbee = (Xbee) connectionManager.getCommunicationHardware(device);
      xbee.setEncryption(on);
    }
    
  }
  
  @Override
  public void removeDevice(CommunicationDevice device) {
    connectionManager.removeDevice(device);
    
  }
  
  @Override
  public void send(byte[] message, CommunicationPriority priority) throws IOException {
    message = addHeaderToByteArray(message, false);
    
    Message messageObj = new Message(message);
    messageObj.setPriority(priority);
    
    this.giveMessageToScheduler(messageObj);
  }
  
  @Override
  public void send(Serializable message, CommunicationPriority priority) throws IOException {
    Message messageObj = new Message(addHeaderToByteArray(objectToByteArray(message), true));
    messageObj.setPriority(priority);
    
    this.giveMessageToScheduler(messageObj);
  }
  
  @Override
  public boolean sendBlock(byte[] message, CommunicationPriority priority, int timeout) throws IOException {
    message = addHeaderToByteArray(message, false);
    
    Message messageObj = new Message(message);
    messageObj.setPriority(priority);
    messageObj.setTimeout(timeout);
    
    this.giveMessageToScheduler(messageObj);
    return false; // TODO ??
  }
  
  @Override
  public boolean sendBlock(Serializable message, CommunicationPriority priority, int timeout) throws IOException {
    Message messageObj = new Message(addHeaderToByteArray(objectToByteArray(message), true));
    messageObj.setPriority(priority);
    messageObj.setTimeout(timeout);
    
    this.giveMessageToScheduler(messageObj);
    return false; // TODO ??
  }
  
  @Override
  public void updateTypedMessage(byte[] message, CommunicationPriority priority, int type) throws IOException {
    message = addHeaderToByteArray(message, false);
    
    Message messageObj = new Message(message);
    messageObj.setPriority(priority);
    messageObj.setType(type);
    
    this.giveMessageToScheduler(messageObj);
  }
  
  @Override
  public void updateTypedMessage(Serializable message, CommunicationPriority priority, int type) throws IOException {
    Message messageObj = new Message(addHeaderToByteArray(objectToByteArray(message), true));
    messageObj.setPriority(priority);
    messageObj.setType(type);
    
    this.giveMessageToScheduler(messageObj);
  }
  
  /*
   * Give the prepared message to the next layer for transfer
   */
  private void giveMessageToScheduler(Message message) {
    connectionManager.addToQueue(message);
  }
  
  private static byte[] addHeaderToByteArray(byte[] message, boolean isObject) {
    byte[] newMessage = new byte[message.length + 1];
    newMessage[0] = 0; // First byte is '0' if message was a byte[]
    if (isObject) {
      newMessage[0] = 1; // First byte is '1' if message was an object
    }
    
    for (int i = 0; i < message.length; i++) {
      newMessage[i + 1] = message[i];
    }
    
    return newMessage;
  }
  
  @Override
  public CommunicationInput read() {
    try {
      return incomingList.take();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
  
  @Override
  public CommunicationStatus getStatus() {
    CommunicationStatus status = new CommunicationStatus();
    return status;
  }
  
  @Override
  public void messageReceived(byte[] message) {
    byte[] pMessage = new byte[message.length - 1];
    for (int i = 1; i < message.length; i++) { // give us the message without our internal header
      pMessage[i - 1] = message[i];
    }
    
    CommunicationInput outputObject = new CommunicationInput();
    
    if (message[0] == 0) { // original message is byte[]
      outputObject.setObject(false);
      outputObject.setByteData(pMessage);
      incomingList.add(outputObject);
    } else if (message[0] == 1) { // original message is an object
      Object pObject;
      try {
        pObject = byteArrayToObject(pMessage);
        outputObject.setObject(true);
        outputObject.setObjectData(pObject);
        incomingList.add(outputObject);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
