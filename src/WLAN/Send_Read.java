/**
 * 
 */
package WLAN;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel
 * @version 1.0
 * @date: 26.10.2012
 */
public class Send_Read {
	
	private static DataInputStream in;
	private static DataOutputStream out;
	private static final Logger log = Logger.getLogger(Logger.class.getName());
	/**
	 * send a blob of data. Sending may fail without notice or with exception
	 * 
	 * @param data
	 *            data to send
	 * @throws IOException
	 *             on exception or failure in underlying protocol
	 */
	public static void sendByteArray(byte[] data) {
		send(data, 0, data.length);
	}

	/**
	 * send ByteArray to receiver check length of the array and her IndexBound
	 * 
	 * @param ByteArray
	 * @param start
	 *            point of array
	 * @param len
	 *            of the passed ByteArray
	 * @throws IOException
	 *             or IllegalArgumentException or IndexOutOfBoundsException
	 */
	public static void send(byte[] ByteArray, int start, int len) {
		if (len < 0) {
			throw new IllegalArgumentException(
					"The length of the Array cannot be negative");
		}
		if (start < 0 || start >= ByteArray.length) {
			throw new IndexOutOfBoundsException(
					"Your value is: Out of bounds: " + start);
		}

		try {
			// send ByteArray Length and content to receiver
			out.writeInt(len);
			// System.out.println("Length is: " + len);
			if (len > 0) {
				out.write(ByteArray, start, len);
				// System.out.println("Sending ByteArray - complete");
			}
		} catch (IOException ioe) {
			ioe.getMessage();
		}

	}

	/**
	 * send a blob of data and break until unsuccessful or failure is signaled
	 * 
	 * @param data
	 *            data to send
	 * @param timeout
	 *            for transfer operation
	 * @return false if timeout overrun
	 * @return true if send transfer is complete
	 */
	public static boolean sendContent(byte[] data, int timeout) {
		log.log(Level.WARNING, "Waiting: " + timeout);
		if (data == null) {
			return false;
		} else {
			send(data, 0, data.length);
			log.log(Level.WARNING, "Sending Data - complete");
			return true;
		}
	}

	/**
	 * blocking read of data. Returns data in blobs as it was sent! Use of
	 * Exception is implementation defined.
	 * 
	 * @return ByteArray or null if content is empty
	 * @throws IOException
	 *             the stream has been closed and the contained input stream
	 *             does not support reading after close, or another I/O error
	 *             occurs
	 */
	public static byte[] read() throws IOException {
		try {
			int len = 0;
			len = in.readInt();
			log.log(Level.WARNING, "ByteArray lenght: " + len);
			byte[] ByteArray = new byte[len];
			if (len > 0) {
				// read the ByteArray from InputStream
				in.readFully(ByteArray);
			}
			return ByteArray;
		} catch (IOException e) {
			e.getMessage();
		}
		return null;
	}	
}
