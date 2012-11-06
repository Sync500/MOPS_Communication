/**
 * 
 */
package WLAN;

import java.io.IOException;

/**
 * @author matthias Interface for communication hardware. Should be implemented
 *         by all available hardware channels.
 */
public interface CommunicationHardware {
	/**
	 * Try to establish a connection on this link.
	 */
	void connect();

	/**
	 * send a blob of data. Sending may fail without notice or with exception.
	 * 
	 * @param data
	 *            data to send
	 * @throws IOException
	 *             on exception or failure in underlying protocol
	 */
	void send(byte[] data) throws IOException;

	/**
	 * send a blob of data
	 * 
	 * @param data
	 *            data to send
	 * @param timeout
	 *            in ms
	 * @return boolean is data known to be transmitted?
	 * @throws IOException
	 *             on failure
	 */
	boolean sendBlock(byte[] data, int timeout) throws IOException;

	/**
	 * blocking read of data. Returns data in blobs as it was sent! Use of
	 * Exception is implementation defined.
	 * 
	 * @return blob of data.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	byte[] read() throws IOException, InterruptedException;

	/**
	 * Get an estimation of current/recent link throughput capacity. This should
	 * be negative if the current link state is unknown or disconnected.
	 * 
	 * @return throughput in bytes per second
	 */
	int getThroughput();

	/**
	 * Get an estimation of current/recent link latency. This should be negative
	 * if the link state is unknown or disconnected.
	 * 
	 * @return latency for a round trip in ms.
	 */
	int getLatency();

	/**
	 * Try to check link state. Block until link state is known or timeout
	 * exceeded.
	 * 
	 * @param timeout
	 *            maximum time in ms to wait for a result
	 * @return link state: Active or not
	 */
	boolean linkState(int timeout);
}