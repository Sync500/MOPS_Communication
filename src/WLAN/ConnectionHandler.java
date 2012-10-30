/**
 * 
 */
package WLAN;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel
 * @version 1.0
 * @date: 30.10.2012
 */
public class ConnectionHandler {

	private static final Logger log = Logger.getLogger(ConnectionHandler.class
			.getName());

	/**
	 * check the state of the socket connection show state of the connection
	 * 
	 * @param socket
	 *            clientSocket to check the connection state
	 * @return boolean
	 */
	public static boolean connectionState(Socket socket) {
		boolean isOn = true;
		// istOn = InetAddress.getByName( host ).isReachable(timeout);
		isOn = socket.isConnected();

		if (isOn == false) {
			log.log(Level.INFO, "Connection is: " + socket.isConnected());
			return false;
		}
		log.log(Level.INFO, "Connection is: " + socket.isConnected());
		return true;
	}

	/**
	 * @description: close all streams and sockets show user message that
	 *               connection is off
	 * @param server
	 *            serverSocket
	 * @param socket
	 *            clientSocket
	 * @throws IOException
	 *             - if an I/O error occurs
	 */

	public static void closeConnection(ServerSocket server, Socket socket) {
		try {
			server.close();
			socket.close();
			log.log(Level.INFO, "Connection OFF");
		} catch (IOException ioe) {
			ioe.getMessage();
		}
	}

	/**
	 * @description: thread will wait timeout seconds then continue
	 * 
	 * @param timeout
	 * @throws IntrruptedException
	 *             - when a thread is waiting, sleeping, or otherwise occupied,
	 *             and the thread is interrupted, either before or during the
	 *             activity
	 */
	public static void sleep(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException ie) {
			ie.getMessage();
		}
	}
}
