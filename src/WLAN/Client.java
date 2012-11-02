/**
 * 
 */
package WLAN;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import WLAN.oldFiles.Data;

/**
 * @author Daniel
 * @version 1.0
 * @date: 30.10.2012
 */

public class Client extends Thread {
	/**
	 * initialization of the member var.
	 * 
	 */

	private static Socket socket = null;
	private static DataInputStream input = null;
	private static DataOutputStream output = null;
	private static final Logger log = Logger.getLogger(Client.class.getName());

	/**
	 * Constructor generate ClientSocket with I/O-Streams
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred
	 */
	Client() throws IOException {
		socket = Connecting.connectSocket();
		input = Connecting.createInputStream(socket);
		output = Connecting.createOutputStream(socket);
		// System.out.println("Socket and streams created - client");
	}

	/**
	 * Test
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {

//		byte b[] = new byte[4];
//		b[0] = 12;
//		b[1] = 11;
//		b[2] = 123;
//		if (socket.isConnected()){
//			try {
//				Send_Read.sendByteArray(b, output);
//				System.out.println("Sending Data complete");
//			} catch (Exception e) {
//				System.out.println("Socket not accessible!");
//				System.exit(1);
//			} // catch
//		} // if
	} // run
}
