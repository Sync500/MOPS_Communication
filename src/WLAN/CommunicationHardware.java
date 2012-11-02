/**
 * 
 */
package WLAN;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author matthias
 * Interface for communication hardware.
 * Should be implemented by all available hardware channels.
 */
public interface CommunicationHardware {
  /**
   * Try to establish a connection on this link.
   */
  void connect(); // wlan / xbee
  
  
  /**
   * send a blob of data. Sending may fail without notice or with exception. 
   * @param data  data to send
   * @throws IOException  on exception or failure in underlying protocol
   */
  void send(byte[] data) throws IOException; // xbee
  
  /**
	 * send a blob of data. Sending may fail without notice or with exception
	 * @param data
	 *            data to send
	 * @param out
	 *            DataOutputStream
	 * @throws IOException
	 *             on exception or failure in underlying protocol
	 */
	void sendByteArray(byte[] data, DataOutputStream out); // wlan
  
  /**
   * send a blob of data 
   * @param data data to send
   * @param timeout in ms
   * @return boolean is data known to be transmitted?
   * @throws IOException on failure
   */
  boolean sendBlock(byte[] data, int timeout) throws IOException; // xbee
  
  /**
	 * send a blob of data and break operation until timeout is overrun
	 *	 * @param data
	 *            data to send
	 * @param timeout
	 *            for transfer operation
	 * @param socket
	 *            socket to set timeout
	 * @param out
	 *            DataOutputStream
	 * @return false if timeout overrun
	 * @return true if send transfer is complete
	 */
	void sendWithTimeOut(byte[] data, int timeout, Socket socket, DataOutputStream out); // wlan
  
  /**
   * blocking read of data. Returns data in blobs as it was sent!
   * Use of Exception is implementation defined.
   * @return blob of data.
   * @throws IOException
   * @throws InterruptedException 
   */
  byte[] read() throws IOException, InterruptedException; // xbee
  
  /**
	 * read of data. Returns data in blobs as it was sent! Use of Exception is
	 * implementation defined
	 * @param in
	 *            DataInputStream
	 * @return ByteArray or null if content is empty
	 * @throws IOException
	 *             the stream has been closed and the contained input stream
	 *             does not support reading after close, or another I/O error
	 *             occurs
	 */
	byte[] read(DataInputStream in) throws IOException; // wlan
  
  /**
   * Get an estimation of current/recent link throughput capacity.
   * This should be negative if the current link state is unknown or disconnected.
   * @return throughput in bytes per second
   */
  int getThroughput(); // xbee
  
  /**
   * Get an estimation of current/recent link latency.
   * This should be negative if the link state is unknown or disconnected.
   * @return latency for a round trip in ms. 
   */
  int getLatency(); // xbee
  
  
  /**
   * Try to check link state. Block until link state is known or timeout exceeded.
   * @param timeout maximum time in ms to wait for a result
   * @return link state: Active or not
   */
  boolean linkState(int timeout); // xbee
  
  /**
	 * check and show the state of the socket connection
	 * @param socket
	 *            Socket to check the connection state
	 * @return boolean
	 */
	boolean connectionState(Socket socket);
}
