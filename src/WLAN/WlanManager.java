/**
 * 
 */
package WLAN;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

/**
 * @author Daniel
 * @version 1.0
 * @date: 04.11.2012
 */
public class WlanManager {
	/**
	 * @param args
	 * @throws IOException
	 */
	
	public WlanManager(String ip, int port){
		Connecting con = new Connecting();
		con.setIP(ip);
		con.setPort(port);
	}

	public static void main(String[] args) throws IOException {
		String ip = "localhost";
		int port = 6665;
		new WlanManager(ip,port);
		WlanHardwareImpl server = new WlanHardwareImpl();
		server.connect();
	}
}

/**
 * class Server initializes ServerSocket
 * 
 */
class Server extends Thread {
	private static Socket serverSo;
	private String ip = Connecting.getIP();
	private int port = Connecting.getPort();
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (serverSo == null) {
			try {
				serverSo = Connecting.connectServerSocket(ip, port);
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
				// System.out.println(e.getMessage());
			} // catch
		} // while
	} // run

} // Server
