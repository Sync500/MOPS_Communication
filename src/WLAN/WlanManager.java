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
 * @date: 02.11.2012
 */
public class WlanManager {
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (serverSo == null) {
			try {
				serverSo = Connecting.connectServerSocket();
				in = Connecting.createInputStream(serverSo);
				out = Connecting.createOutputStream(serverSo);
			} catch (Exception e) {
				e.printStackTrace();
				// System.out.println(e.getMessage());
			} // catch
		} // while
	} // run
} // Server
