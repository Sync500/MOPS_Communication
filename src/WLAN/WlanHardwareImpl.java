/**
 * 
 */
package WLAN;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * @author Daniel
 * @version 1.0
 * @date: 02.11.2012
 */
abstract class WlanHardwareImpl implements CommunicationHardware {
	private static Socket serverSo = null;
	private static Socket socket = null;
	private static DataInputStream in = null;
	private static DataOutputStream out = null;
	// private static final Logger log =
	// LoggerFactory.getLogger(WlanHardwareImpl.class);
	private static final Logger log = Logger.getLogger(WlanHardwareImpl.class
			.getName());

	@Override
	public void connect() {
		System.out.println("WLAN");
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

	@Override
	public void sendByteArray(byte[] data, DataOutputStream out) {
		// out = Connecting.createOutputStream(serverSo);
		Send_Read.sendByteArray(data, out);
		log.log(Level.INFO, "Sending Data..");
	}

	@Override
	public void sendWithTimeOut(byte[] data, int timeout, Socket socket,
			DataOutputStream out) {
		// serverSo = Connecting.connectServerSocket();
		// out = Connecting.createOutputStream(serverSo);
		Send_Read.sendWithTimeOut(data, timeout, socket, out);

	}

	@Override
	public byte[] read(DataInputStream in) throws IOException {
		// in = Connecting.createInputStream(serverSo);
		log.log(Level.INFO, "Read Data..");
		return Send_Read.read(in);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		//

	}

	@Override
	public void send(byte[] data) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean sendBlock(byte[] data, int timeout) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte[] read() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getThroughput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLatency() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean linkState(int timeout) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean connectionState(Socket socket) {
		// TODO Auto-generated method stub
		return false;
	}
}
