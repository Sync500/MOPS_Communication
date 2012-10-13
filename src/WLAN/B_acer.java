/**
 * 
 */
package WLAN;

import java.io.*;
import java.net.*;

public class B_acer{
	Socket 					requestSocket;
	ObjectOutputStream 		out;
 	ObjectInputStream 		in;
 	String 					message;
	BufferedReader 			reader = null;
	InputStreamReader   	isr = null;
	final int 				port = 6665;
	final String 			host = "192.168.1.7";
	
	B_acer(){
		connectSocket();
		createStreams(requestSocket);
		yourInput();
	}
	
	void connectSocket(){
		try {
			requestSocket = new Socket(host, port);
			System.out.println("Connected to Server");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Socket dont create" + e.getMessage());
		}
	}
	
	void createStreams(Socket requestSocket){
		Socket connection = requestSocket;
		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("client - Streams dont created" + e.getMessage());
		}
	}
	
	void yourInputt(){
		String yourmessage = "";
		reader = new BufferedReader(isr = new InputStreamReader(System.in));
			try {
				try {
					yourmessage = (String)in.readObject();
					System.out.println("server>" + yourmessage);
				} catch (ClassNotFoundException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				yourmessage = "";
				yourmessage = reader.readLine();
				sendMessage(yourmessage);
				if (yourmessage.equals("bye")) {closeConnection();}
				
	//			yourmessage = (String)in.readObject();
				
			} catch (IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	void yourInput(){
		do{
			try{
				message = (String)in.readObject();
				System.out.println("server>" + message);
				if (message.equals("bye"))
					sendMessage("bye");
			}
			catch(ClassNotFoundException classnot){
				System.err.println("Data received in unknown format");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(!message.equals("bye"));
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
	
	void closeConnection(){
		try{
			in.close();
			out.close();
			requestSocket.close();
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
		B_acer client = new B_acer();	 
	}
}
