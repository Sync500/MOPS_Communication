/**
 * 
 */
package WLAN;

import java.io.*;
import java.net.*;
public class WLAN_Server{
	private ServerSocket 	providerSocket;
	private Socket 			connection = null;
	ObjectOutputStream 		out;
	ObjectInputStream 		in;
	String 					message;
	BufferedReader 		reader = null;
	InputStreamReader   isr = null;
	
	final int port = 6665;
	final String host = "192.168.1.7";
	
	WLAN_Server(){
		
		connectServerSocket();
		WaitForClient();
		createStreams(connection);
				
	}
	
	void connectServerSocket(){
		try {
			providerSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ServerSocket dont create" + e.getMessage());
		}
	}
	
	void WaitForClient(){
		System.out.println("Waiting for connection");
		try {
			connection = providerSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Client dont response" + e.getMessage());
		}
		System.out.println("Connection received from " + connection.getInetAddress().getHostName());
	}
	
	void createStreams(Socket connection){
		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("server - Streams dont created" + e.getMessage());
		}
		sendMessage("Connection and Streams successful \n");
	}
	
	
	
	void sendMessage(String msg){
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	void yourInput(){
		String yourmessage = "";
		reader = new BufferedReader(isr = new InputStreamReader(System.in));
			try {
				try {
					yourmessage = (String)in.readObject();
					System.out.println("server>" + yourmessage);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				yourmessage = reader.readLine();
				sendMessage(yourmessage);
				if (!yourmessage.equals("bye")) {closeConnection();}
				
	//			yourmessage = (String)in.readObject();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	void closeConnection(){
		try{
			in.close();
			out.close();
			providerSocket.close();
		}
		catch(IOException ioe){
			System.out.println("Connection isnt closed" + ioe.getMessage());
		}
	}
	
	String getLocalIP(String host){
		InetAddress inet = null;
		try {
			inet = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println( inet.getCanonicalHostName() );
//		System.out.println( inet.getHostAddress() );
//		System.out.println( inet.toString() );
		return inet.getHostAddress();
	}
	
	public static void main(String args[]) throws UnknownHostException
	{
		WLAN_Server server = new WLAN_Server();
					
			System.out.println( server.getLocalIP("SYNCMASTER-PC"));
			
			boolean istAn = true;
			try {
				istAn = InetAddress.getByName( "192.168.1.7" ).isReachable(3000);
				System.out.println(istAn);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
