package WLAN;

/**
 * Contains the data to transfer and some metainformation for the packetmanager/scheduler
 * 
 * @author Kamran, matthias for PG-MOPS
 */
public class Message {
  private static int            uuidCnt    = 1;
  private byte[]                message    = null;
  private int                   timeout;
  private CommunicationPriority priority;
  private int                   type;
  private boolean               isBlocking = false;
  private int                   uuid;
  
  Message(byte[] message) {
    this.message = message;
    this.type = -1;
    this.uuid = uuidCnt++;
  }
  
  byte[] getMessage() {
    return this.message;
  }
  
  void setTimeout(int timeout) {
    this.timeout = timeout;
  }
  
  int getTimeout() {
    return this.timeout;
  }
  
  void setPriority(CommunicationPriority priority) {
    this.priority = priority;
  }
  
  CommunicationPriority getPriority() {
    return priority;
  }
  
  void setType(int type) {
    this.type = type;
  }
  
  int getType() {
    return this.type;
  }
  
  void setIsBlocking(boolean isBlocking) {
    this.isBlocking = isBlocking;
  }
  
  boolean getIsBlocking() {
    return this.isBlocking;
  }
  
  /**
   * retrieve automatically set UUID of this message (used for tracking purposes)
   * 
   * @return uuid
   */
  int getUuid() {
    return uuid;
  }
}
