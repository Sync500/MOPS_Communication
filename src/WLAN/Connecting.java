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
 * @date: 25.10.2012 - 18:00
 */
public class Connecting {

	private static Socket socket = null;
	private static ServerSocket server = null;
	private static DataInputStream in;
	private static DataOutputStream out;
	private static final Logger log = Logger.getLogger(Logger.class.getName());
	final static int port = 6665;
	final static String host = "192.168.1.7";
	
	// Client Implementation Begin
	/**
	 * connecting to Server without timeout
	 * 
	 * @throws UnknownHostException
	 *             if the IP/host address of the host could not be determined
	 * @throws IOException
	 *             if an I/O error occurs when creating the socket
	 */
	public static void connectSocket() throws UnknownHostException {
		try {
			socket = new Socket(host, port);
			log.log(Level.WARNING, "Connect to Server");
		} catch (IOException e) {
			log.log(Level.WARNING, "Socket dont create: " + e.getMessage()
					+ "\n");
		}
	}

	/**
	 * connect to Server with timeout
	 * 
	 * @param timeout
	 *            set timeout if Server don't response
	 * @throws IOException
	 *             if Server don't response then connect again
	 */
	public static void connectWithTimeOut(int timeout) {
		log.log(Level.WARNING, "Timeout is: " + timeout);
		SocketAddress sockaddr = new InetSocketAddress(host, port);
		socket = new Socket();
		try {
			socket.connect(sockaddr, timeout);
			createStreams(socket);
		} catch (IOException e) {
			log.log(Level.WARNING, "Server dont response");
			connectWithTimeOut(timeout);
		}
	}

	// Client Implementation End

	// Server Implementation Begin
	/**
	 * @description: create ServerSocket and wait for client
	 * @throws IOException
	 *             - if an I/O error occurs when opening the socket
	 * 
	 */
	public static void connectServerSocket() {
		try {
			server = new ServerSocket(port); // throw IOException
			WaitForClient();
		} catch (IOException e) {
			log.log(Level.WARNING,
					"ServerSocket dont create: " + e.getMessage() + "\n");
		}
	}

	/**
	 * @description: Server wait for response from client
	 * @throws IOException
	 *             - if an I/O error occurs when waiting for a connection if an
	 *             I/O error occurs when creating the output stream or if the
	 *             socket is not connected
	 * 
	 */
	public static void WaitForClient() {
		System.out.println("Waiting for Client");
		try {
			socket = server.accept(); // throw IOException
			createStreams(socket); // throw IOException
		} catch (IOException e) {
			log.log(Level.WARNING, "Client dont response: " + e.getMessage());
		}
		log.log(Level.WARNING, "Socket received from "
				+ socket.getInetAddress().getHostName());
	}

	// Server Implementation End
	
	/**
	 * create necessary streams to transfer Data
	 * 
	 * @param socket
	 * @throws IOException
	 *             if an I/O error occurs when creating the output stream or if
	 *             the socket is not connected
	 */
	public static void createStreams(Socket socket) {
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			log.log(Level.WARNING,
					"Streams dont created: " + e.getMessage() + "\n");
		}
	}
}
