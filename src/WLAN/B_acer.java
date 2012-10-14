/**
 * 
 */
package WLAN;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class B_acer{
	private static Socket 					requestSocket;
	private static ObjectOutputStream 		out;
 	private static ObjectInputStream 		in;
 	private static BufferedReader 			reader = null;
 	private static InputStreamReader   		isr = null;
 	
	final static int 						port = 6665;
	final static String 					host = "192.168.1.7";
	
	B_acer(){
//		connectSocket();
//		createStreams(requestSocket);
//		yourInput();
	}
	
	public static void connectSocket(){
		try {
			requestSocket = new Socket(host, port);
			System.out.println("Connected to Server");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Socket dont create: " + e.getMessage() + "\n");
		}
	}
	
	public static void connectWithTimeOut(int timeout) throws UnknownHostException, IOException{
		System.out.println("Timeout is: " + timeout);
		SocketAddress sockaddr = new InetSocketAddress(host, port);
		requestSocket = new Socket();
		try {
			requestSocket.connect(sockaddr, timeout);
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("Server dont response");
			connectWithTimeOut(timeout);
		}
	}
	
	public static void createStreams(Socket requestSocket) throws ClassNotFoundException, IOException{
		Socket connection = requestSocket;
		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			in =  new ObjectInputStream(connection.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("client - Streams dont created" + e.getMessage() +  "\n");
		}
		sendMessage("client - Connection and Streams successful \n");
	}
	
	public static void yourInput() throws IOException, ClassNotFoundException{
		while(true){
			String userInput = null;
			reader = new BufferedReader(isr = new InputStreamReader(System.in));
			
			try {
				userInput = (String)in.readObject();
				System.out.println("Last Messag from Server: " + userInput);
			} catch (IOException e) {
				System.out.println("InputStream not readably!");
				e.printStackTrace();
			} //catch
			
			
			if(userInput.equals("exit")){ // with exit -  connection end
				out.writeObject(userInput+"\n");
				out.flush(); // write the last data bytes
				out.close();
				break;
			}else{
				userInput = reader.readLine();
				out.writeObject(userInput+"\n");
				out.flush();
			} // if
		}
	}
	
	public static void readObject() throws IOException, ClassNotFoundException, InterruptedException{
		try{
//			Socket connection = requestSocket;
//			out.writeObject(data);
			Data data = new Data();
			data = (Data) in.readObject(); // read serialize class/object - generate new object and cast
//			System.out.println("From SERVER : " + data.getOBject_message()); // the var. content send to Server
			System.out.println("Object received");
//			System.out.println("From SERVER : " + data.getObject_calculatet()); // the var. content send to Server
			out.writeObject(data);
			out.flush();
		}catch(IOException ioException){
			System.out.println("client - I/Output is clear" + ioException.getMessage() +  "\n");
		}
	}
	
	public static void sendMessage(String msg) throws ClassNotFoundException, IOException{
		String yourmessage = "";
		try{
			if (out != null){
				out.writeObject(msg);
				out.flush();
//				System.out.println("client>" + msg);
				yourmessage = (String)in.readObject();
				System.out.println("client>" + yourmessage);
			}
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		
	}
	
	public static void closeConnection(){
		try{
			in.close();
			out.close();
			requestSocket.close();
			System.out.println("client - Connection OFF");
		}
		catch(IOException ioe){
			System.out.println("Connection isnt closed" + ioe.getMessage() + "\n");
		}
	}
	
	String getLocalIP(String host){
		InetAddress inet = null;
		try {
			inet = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Host dont know: " + e.getMessage() + "\n");
		}
//		System.out.println( inet.getCanonicalHostName() );
//		System.out.println( inet.getHostAddress() );
//		System.out.println( inet.toString() );
		return inet.getHostAddress();
	}
	
	public static String getMacAddress() throws IOException
	{
	    Process proc = Runtime.getRuntime().exec( "cmd /c ipconfig /all" );
	    Scanner s = new Scanner( proc.getInputStream() );
	    return s.findInLine( "\\p{XDigit}\\p{XDigit}(-\\p{XDigit}\\p{XDigit}){5}" );
	}
	
	public static boolean connectionState(String host, int timeout){
		boolean istAn = true;
		try {
			istAn = InetAddress.getByName( host ).isReachable(timeout);
			if (istAn == false){
				System.out.println("Connection is OFF");
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Connection is ON");
		return true;
	}
	
	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException
	{
		B_acer client = new B_acer();	
		System.out.println(getMacAddress());
//		connectSocket();
		connectWithTimeOut(3000);
		createStreams(requestSocket);
		
		System.out.println( client.getLocalIP("LENOVO_E525"));
		connectionState("192.168.1.7",1000);
		
//		Data data = new Data();
		readObject();
		
		
//		String yourmessage = "";
//		reader = new BufferedReader(isr = new InputStreamReader(System.in));
//		sendMessage("Your Input is: ");
//		System.out.println("Wait for Message from Server");	
//		do{
//			yourmessage = reader.readLine();
//			sendMessage(yourmessage);
//					
//		}while(!yourmessage.equals("end"));
//		connectWithTimeOut(3000);
	}
}
