/**
 * 
 */
package WLAN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Daniel Fay
 * @version 1.0
 *
 */
public class Communication_ {
	
	static ServerSocket 		serverSocket = null;
	static Socket 				clientSocket = null;
	private String 				ip = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			new runServer().start(); // JVM use the run-method and start two threads
			new RunClient().start(); // Client start - its the same
		
		}catch(Exception e){
			System.out.println("Server connection not possible or currently busy!");
			e.printStackTrace();
		}

	} // main
	
	void sendMessage(Object message, int priority, int timeout, int messType){
		//
	}
	
	void sendMessageEvent(Object message, int priority, int event){
		//
	}
	
	void setServerAddressWLAn(String ip){
		this.ip = ip;
	}
	
	boolean getStatus(Socket socket){
		boolean isAlive = false;
		
		isAlive = socket.isConnected();
		
		if(!isAlive){
			//System.out.println("Connection: OFF");
			return false;
		}
			//System.out.println("Connection: ON");
			return true;
	}
	
	void event(){
		//
	}
	
} // communication

class Server extends Thread{
	
	/**
	 * set all var. with default value null
	 * 
	 */
	ServerSocket 		serverSocket = null;
	Socket 				clientSocket = null;
	PrintWriter 		writer = null;
	BufferedReader 		reader = null;
	final int			port = 6665; // dont use port 1-2000
	
	public void run(){	
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		} // generate object for sockets
	}
	
	public void running(){
		System.out.println("Waiting");
		
		while(true){
			try{
					clientSocket = serverSocket.accept(); // wait on client
					System.out.println("Socket create");
					Communication_ com = new Communication_();
					System.out.println("Status is: " + com.getStatus(clientSocket));
					
					 // generate object - OutputStream with autoFlush
					writer = new PrintWriter(clientSocket.getOutputStream(), true);
					// get the InputStream - data from socket
					reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			}catch(IOException e){
				System.out.println("Server say: connection error");
			} //catch
		} // while
	} // run
} // runServer
