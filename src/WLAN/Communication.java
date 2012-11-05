package WLAN;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author matthias
 *         Interface for Communication class.
 *         A communication may be established over different channels.
 *         Implemented so far are sockets and xbee.
 *         For socket communication, configure (at least) one side with a socketServer
 *         and the other side with a socketClient.
 *         You are allowed to add as many socket or xbee connections you like.
 * 
 */
public interface Communication {
  
  /**
   * Add a new socket connection as ClientSocket.
   * 
   * @param ip
   *          destination IP
   * @param port
   *          destination Port
   * @return CommunicationDevice used for referring to this connection
   */
  CommunicationDevice addSocketClient(String ip, int port);
  
  /**
   * Add a new socket connection as ServerSocket.
   * 
   * @param ip
   *          listening IP (may be 0.0.0.0 or ::0 for everything)
   * @param port
   *          listen Port
   * @return CommunicationDevice used for referring to this connection
   */
  CommunicationDevice addSocketServer(String ip, int port);
  
  /**
   * Add a new Xbee device connected by a socket (e.g. external conversion of Socket <-> Serial)
   * 
   * @param ip
   *          IP address of converter (e.g. 127.0.0.1 or localhost)
   * @param port
   *          port of converter
   * @return CommunicationDevice used for referring to this connection
   */
  CommunicationDevice addXbeeSocketDestination(String ip, int port);
  
  /**
   * Add a new Xbee device connected by a serial connection using Java serial port access.
   * 
   * @param port
   *          name of serial port (e.g. /dev/ttyUSB1)
   * @param port
   *          baudrate of serial port (e.g. 9600 for default Xbee Pro 868)
   * @return CommunicationDevice used for referring to this connection
   */
  CommunicationDevice addXbeeSerialDestination(String port, int baudrate);
  
  /**
   * Set the 64 bit device address of a targeted Xbee.
   * 
   * @param device
   *          xbee device to use for reaching given target
   * @param addr
   *          int[8] address, leftmost byte first
   */
  void setXbeeDestinationAddr(CommunicationDevice device, int[] addr);
  
  /**
   * Return the 64 bit device address of given Xbee.
   * 
   * @param device
   *          xbee device to get address from
   * @return int[8] address, leftmost byte first
   */
  int[] getXbeeAddr(CommunicationDevice device);
  
  /**
   * Xbee modules support AES encryption using a 128 bit key. Set this key.
   * 
   * @param key
   *          int[16] for encryption key
   */
  void setXbeeEncryptionKey(CommunicationDevice device, int[] key);
  
  /**
   * Enable or disable xbee encryption. Defaults to false. You should set an encryption key before!
   * 
   * @param on
   *          or off?
   */
  void setXbeeEncryption(CommunicationDevice device, boolean on);
  
  /**
   * remove given device so that it will not be used for communication. It may
   * be read any time later.
   * 
   * @param device
   */
  void removeDevice(CommunicationDevice device);
  
  /**
   * Try to send given message non blocking. Communication will try whatever possible
   * to deliver this message at the earliest possible time (regarding priorities).
   * 
   * @param message
   * @param priority
   * @throws IOException
   *           in case of error (you can assume this message will never reach the target)
   */
  void send(byte[] message, CommunicationPriority priority) throws IOException;
  
  /**
   * Try to send given message non blocking. Communication will try whatever possible
   * to deliver this message at the earliest possible time (regarding priorities).
   * 
   * @param message
   * @param priority
   * @throws IOException
   *           in case of error (you can assume this message will never reach the target)
   */
  void send(Serializable message, CommunicationPriority priority) throws IOException;
  
  /**
   * Try to send given message blocking. Communication will try whatever possible
   * to deliver this message at the earliest possible time (regarding priorities).
   * 
   * @param message
   * @param priority
   * @param timeout
   *          timeout in ms we will block. After that time, the message may still get delivered!
   * @return Success of sending the message. A false value does only mean "we do not know if the message
   *         reached the target".
   * @throws IOException
   *           in case of error (you can assume this message will never reach the target)
   */
  boolean sendBlock(byte[] message, CommunicationPriority priority, int timeout) throws IOException;
  
  /**
   * Try to send given message blocking. Communication will try whatever possible
   * to deliver this message at the earliest possible time (regarding priorities).
   * 
   * @param message
   * @param priority
   * @param timeout
   *          timeout in ms we will block. After that time, the message may still get delivered!
   * @return Success of sending the message. A false value does only mean "we do not know if the message
   *         reached the target".
   * @throws IOException
   *           in case of error (you can assume this message will never reach the target)
   */
  boolean sendBlock(Serializable message, CommunicationPriority priority, int timeout) throws IOException;
  
  /**
   * Update internal map of messages. A typed message will be delivered every time it is changed.
   * All previously pending requests of the same type will not get delivered after the message gets updated.
   * 
   * @param message
   * @param priority
   * @throws IOException
   *           in case of error (you can assume this message will never reach the target)
   */
  void updateTypedMessage(byte[] message, CommunicationPriority priority, int type) throws IOException;
  
  /**
   * Update internal map of messages. A typed message will be delivered every time it is changed.
   * All previously pending requests of the same type will not get delivered after the message gets updated.
   * 
   * @param message
   * @param priority
   * @throws IOException
   *           in case of error (you can assume this message will never reach the target)
   */
  void updateTypedMessage(Serializable message, CommunicationPriority priority, int type) throws IOException;
  
  /**
   * blocking read of communication data. Each return of this function returns one object or byte array
   * (check returned member isObject) as sent by one of the sending methods.
   * If no message is waiting to be read, this function will return 'null'.
   * 
   * @return message data
   */
  CommunicationInput read();
  
  /**
   * Query the status of communication. Returned status object indicates a lot of data you may use...
   * 
   * @return
   */
  CommunicationStatus getStatus();
  
}
