/**
 * 
 */
package WLAN;

/**
 * @author matthias
 * 
 *         Interface for ConnectionManager
 */
public interface ConnectionManagerI {
  
  /**
   * used for adding a new, initialized communicationHardware to the connection manager.
   * 
   * <pre>
   * make sure that communicationDevice is completely initialized!
   * @param hardware initialized CommunicationHardware
   * @return CommunicationDevice to refer to the newly added device
   */
  public CommunicationDevice addDevice(CommunicationHardware hardware);
  
  /**
   * remove given CommunicationDevice. It may be readded later.
   * 
   * @param device
   *          identifier
   */
  public void removeDevice(CommunicationDevice device);
  
  /**
   * Send a message, non-blocking. If message parameter "isBlocking" is true, message will
   * be delivered with more effort and message tracking is available.
   * 
   * @param message
   *          Message to transmit
   * @return Message ID used to track message state (use getMessageState), negative number when there is no tracking
   */
  void addToQueue(Message message);
  
  /**
   * register a callback for incoming messages.
   * 
   * @param listener
   *          listener that will be called.
   */
  void registerMessageListener(MessageListener listener);
  
  /**
   * Check given message for transmission state. Will be false until we are really sure that it is delivered!
   * Caution: Untracked messages (message parameter isBlocking false) will result to a positive state!
   * 
   * @param messageId
   *          message uuid to check
   * @return Deliverystate of Message
   */
  boolean getMessageState(int messageId);
  
  /**
   * check given message transmission state. Block until state is success or timeout is over.
   * 
   * @param messageId
   *          message uuid to check
   * @param timeout
   *          timeout in ms
   * @return deliverystate of Message
   */
  boolean waitMessageState(int messageId, int timeout);
  
  /**
   * Retrieves hardware object of given device for setting parameters etc.
   * 
   * @param device
   *          device identifier
   * @return hardware object
   */
  CommunicationHardware getCommunicationHardware(CommunicationDevice device);
  
}
