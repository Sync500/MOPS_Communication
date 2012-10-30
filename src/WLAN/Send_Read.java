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
 * @date: 30.10.2012
 */
public class Send_Read {

	public static DataInputStream in;
	public static DataOutputStream out;
	private static final Logger log = Logger.getLogger(Send_Read.class
			.getName());

	/**
	 * send a blob of data. Sending may fail without notice or with exception
	 * 
	 * @param data
	 *            data to send
	 * @param out
	 *            DataOutputStream
	 * @throws IOException
	 *             on exception or failure in underlying protocol
	 */
	public static void sendByteArray(byte[] data, DataOutputStream out) {
		send(data, 0, data.length, out);
	}

	/**
	 * send ByteArray to receiver - check length of the array and her IndexBound
	 * 
	 * @param ByteArray
	 *            data to send
	 * @param start
	 *            point of array
	 * @param len
	 *            of the passed ByteArray
	 * @param out
	 *            DataOutputStream
	 * @throws IOException
	 *             or IllegalArgumentException or IndexOutOfBoundsException
	 */
	public static void send(byte[] ByteArray, int start, int len,
			DataOutputStream out) {
		try {
			if (len < 0) {
				log.log(Level.WARNING,
						"The length of the Array cannot be negative");
			} else if (start < 0 || start >= ByteArray.length) {
				log.log(Level.WARNING, "Your value is: Out of bounds: " + start);
			}
		} catch (IllegalArgumentException iae) {
			iae.getMessage();
		} catch (IndexOutOfBoundsException ioe) {
			ioe.getMessage();
		}

		try {
			// send ByteArray Length and content to receiver
			out.writeInt(len);
			log.log(Level.INFO, "Length is: " + len);
			if (len > 0) {
				out.write(ByteArray, start, len);
				// System.out.println("Sending ByteArray - complete");
			}
		} catch (IOException ioe) {
			ioe.getMessage();
		}
	}

	/**
	 * send a blob of data and break operation until timeout is overrun
	 * 
	 * @param data
	 *            data to send
	 * @param timeout
	 *            for transfer operation
	 * @param out
	 *            DataOutputStream
	 * @return false if timeout overrun
	 * @return true if send transfer is complete
	 */
	public static boolean sendWithTimeOut(byte[] data, int timeout,
			DataOutputStream out) {
		log.log(Level.WARNING, "Waiting: " + timeout);
		if (data == null) {
			return false;
		} else {
			send(data, 0, data.length, out);
			log.log(Level.INFO, "Sending Data - complete");
			return true;
		}
	}

	/**
	 * read of data. Returns data in blobs as it was sent! Use of Exception is
	 * implementation defined
	 * 
	 * @param in
	 *            DataInputStream
	 * @return ByteArray or null if content is empty
	 * @throws IOException
	 *             the stream has been closed and the contained input stream
	 *             does not support reading after close, or another I/O error
	 *             occurs
	 */
	public static byte[] read(DataInputStream in) throws IOException {
		try {
			int len = 0;
			len = in.readInt();
			log.log(Level.INFO, "ByteArray lenght: " + len);
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
