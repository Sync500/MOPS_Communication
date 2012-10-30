/**
 * 
 */
package WLAN;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @author Daniel
 * @version 1.0
 * @date: 30.10.2012
 */
public class WlanManager {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		try {
			new Server().start(); // JVM use the run-method and start two
									// threads
			new Client().start(); // Client start - its the same

		} catch (Exception e) {
			System.out
					.println("Server connection not possible or currently busy!");
			e.printStackTrace();
		}
	}
}

/**
 * class Server initializes the member var. , override run then generate
 * ServerSocket and I/O-Streams - it's same as Client side
 * 
 */
class Server extends Thread {

	private static Socket serverSo = null;
	private static DataInputStream in = null;
	private static DataOutputStream out = null;

	/**
	 * Test
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {

		while (true) {
			try {
				System.out.println("run from WlanManager");
				serverSo = Connecting.connectServerSocket();
				in = Connecting.createInputStream(serverSo);
				out = Connecting.createOutputStream(serverSo);
				// System.out.println("Socket and Streams created - server");
				System.out.println(Arrays.toString(Send_Read.read(in)));

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} // catch
		} // while
	} // run
} // Server
