/**
 * 
 */
package WLAN;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import de.uniol.mops.communication;

/**
 * @author Daniel
 * @version 1.0
 * @date: 04.11.2012
 */
public class WlanHardwareImpl implements CommunicationHardware {
	private static Socket serverSo;
	private static Socket socket;
	private static DataInputStream in;
	private static DataOutputStream out;
//	 private static final Logger log =
//	 LoggerFactory.getLogger(WlanHardwareImpl.class);
	private static final Logger log = Logger.getLogger(WlanHardwareImpl.class
			.getName());

	/* (non-Javadoc)
	 * @see WLAN.CommunicationHardware#connect()
	 */
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
	
	/**
	 * set the Socket that send from Client or Server
	 * 
	 * @param socket
	 */
	public void setSocket(Socket socket){
		WlanHardwareImpl.socket = socket;
	}
	
	/**
	 * getSocket give the actual server or client Socket to use 
	 * 
	 * @return socket
	 */
	public static Socket getSocket(){
		return socket;
	}
	
	/* (non-Javadoc)
	 * @see WLAN.CommunicationHardware#send(byte[])
	 */
	public void send(byte[] data) throws UnknownHostException {
		socket = getSocket();
		out = Connecting.createOutputStream(socket);
		Send_Read.sendByteArray(data, out);
		log.log(Level.INFO, "Sending Data..");
	}

	/* (non-Javadoc)
	 * @see WLAN.CommunicationHardware#sendBlock(byte[], int)
	 */
	public boolean sendBlock(byte[] data, int timeout) {
		socket = getSocket();
		out = Connecting.createOutputStream(socket);
		Send_Read.sendBlock(data, timeout, socket, out);
		return true;
	}

	/* (non-Javadoc)
	 * @see WLAN.CommunicationHardware#read()
	 */
	public byte[] read() throws IOException {
		serverSo = getSocket();
		in = Connecting.createInputStream(serverSo);
		log.log(Level.INFO, "Read Data..");
		return Send_Read.read(in);
	}
	
	/* (non-Javadoc)
	 * @see WLAN.CommunicationHardware#getThroughput()
	 */
	@Override
	public int getThroughput() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see WLAN.CommunicationHardware#getLatency()
	 */
	@Override
	public int getLatency() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see WLAN.CommunicationHardware#linkState(int)
	 */
	/*
	   * (non-Javadoc)
	   * 
	   * @see WLAN.CommunicationHardware#linkState(int)
	   */
	  @Override
	  public boolean linkState(int timeout) {
	    socket = getSocket();
	    boolean isOn;
	    isOn = ConnectionHandler.connectionState(socket);
	    if (isOn){
	      log.info("Connect");
	      return true;
	    }else{
	      log.info("DisConnect");
	      return false;
	    }
	  }
	}