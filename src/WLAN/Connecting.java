/**
 * 
 */
package WLAN;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel
 * @version 1.0
 * @date: 02.11.2012
 */
public class Connecting {

	private static Socket socket;
	private static ServerSocket server = null;
	private static DataInputStream in;
	private static DataOutputStream out;
	private static final Logger log = Logger.getLogger(Connecting.class
			.getName());
	final static int port = 6665;
	final static String host = "localhost";

	// Client Implementation Begin
	/**
	 * connecting to Server without timeout
	 * 
	 * @throws UnknownHostException
	 *             if the IP/host address of the host could not be determined
	 * @throws IOException
	 *             if an I/O error occurs when creating the socket
	 * @return clientSocket
	 */
	public static Socket connectSocket() throws UnknownHostException {
		try {
			socket = new Socket(host, port);
			log.log(Level.INFO, "Connect to Server successful");
			return socket;
		} catch (IOException e) {
			log.log(Level.WARNING,
					"ClientSocket dont create: " + e.getMessage() + "\n");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * connect to Server with timeout
	 * 
	 * @param timeout
	 *            set timeout if Server don't response
	 * @throws IOException
	 *             if Server don't response then connect again
	 * @return clientSocket
	 */
	public static Socket connectWithTimeOut(int timeout, Socket socket) {
		// log.log(Level.WARNING, "Timeout is: " + timeout);
		SocketAddress sockaddr = new InetSocketAddress(host, port);
		socket = new Socket();
		try {
			socket.connect(sockaddr, timeout);
			return socket;
		} catch (IOException e) {
			log.log(Level.WARNING, "Server dont response");
		}
		return null;
	}

	// Client Implementation End

	// Server Implementation Begin
	/**
	 * generate ServerSocket and wait for client
	 * 
	 * @throws IOException
	 *             - if an I/O error occurs when opening the socket
	 * @return Socket
	 */
	public static Socket connectServerSocket() {
		try {
			server = new ServerSocket(port); // throw IOException
			socket = WaitForClient();
			return socket;
		} catch (IOException e) {
			log.log(Level.WARNING,
					"ServerSocket dont create: " + e.getMessage() + "\n");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Server wait for response from client
	 * 
	 * @throws IOException
	 *             - if an I/O error occurs when waiting for a connection if an
	 *             I/O error occurs when creating the output stream or if the
	 *             socket is not connected
	 * @return Socket
	 * 
	 */
	public static Socket WaitForClient() {
		log.log(Level.INFO, "Waiting for Client");
		try {
			socket = server.accept(); // throw IOException
			return socket;
		} catch (IOException e) {
			log.log(Level.WARNING, "Client dont response: " + e.getMessage());
			e.printStackTrace();
		}
		log.log(Level.INFO, "Socket received from "
				+ socket.getInetAddress().getHostName());
		return null;
	}

	// Server Implementation End

	/**
	 * create necessary InputStream for Data transfer
	 * 
	 * @param socket
	 * @throws IOException
	 *             if an I/O error occurs when creating the output stream or if
	 *             the socket is not connected
	 * @return DataInputStream
	 */
	public static DataInputStream createInputStream(Socket socket) {
		try {
			in = new DataInputStream(socket.getInputStream());
			return in;
		} catch (IOException e) {
			log.log(Level.WARNING, "Streams dont created: " + e.getMessage()
					+ "\n");
			return null;
		}
	}

	/**
	 * create necessary OutputStream for Data transfer
	 * 
	 * @param socket
	 * @throws IOException
	 *             if an I/O error occurs when creating the output stream or if
	 *             the socket is not connected
	 * @return DataOutputStream
	 */
	public static DataOutputStream createOutputStream(Socket socket) {
		try {
			out = new DataOutputStream(socket.getOutputStream());
			return out;
		} catch (IOException e) {
			log.log(Level.WARNING, "Streams dont created: " + e.getMessage()
					+ "\n");
			return null;
		}
	}
}
