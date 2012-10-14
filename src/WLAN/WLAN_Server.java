package WLAN;

import java.io.*;
import java.net.*;

public class WLAN_Server{
	private static ServerSocket 	providerSocket;
	private static Socket 			connection = null;
	private static ObjectOutputStream 		out;
	static ObjectInputStream 		in;
	private static BufferedReader 		reader = null;
	private static InputStreamReader   isr = null;
	
	final static int port = 6665;
	final static String host = "192.168.1.7";
	
	WLAN_Server(){				
	}
	
	public static void connectServerSocket() throws InterruptedException{
		try {
			providerSocket = new ServerSocket(port);
			WaitForClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ServerSocket dont create" + e.getMessage() +  "\n");
			connectServerSocket();
			WaitForClient();
		}
	}
	
	public static void WaitForClient(){
		System.out.println("Waiting for Client");
		try {
			connection = providerSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Client dont response" + e.getMessage());
		}
		System.out.println("Connection received from " + connection.getInetAddress().getHostName() +  "\n");
	}
	
	public static void createStreams(Socket connection) throws ClassNotFoundException{
		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("server - Streams dont created" + e.getMessage() +  "\n");
		}
		sendMessage("server - Connection and Streams successful \n");
	}
	
	public static void sendObject(Data data) throws IOException, ClassNotFoundException{
		try{
//			data = (Data) in.readObject(); // read serialize class/object - generate new object and cast 
//			System.out.println("From SOCKET: \n" + data.getOBject_message()); // show message from client
			//System.out.println("Server " + in.setMessage());
//			System.out.println("From SOCKET:" + data.getObject_calculatet()); // show message from client
		
			out.writeObject(data);
			System.out.println("Server say: successful");
			closeConnection(); // end: client closed - connection lost
		}catch(IOException ioException){
			System.out.println("server - I/Output is clear" + ioException.getMessage() +  "\n");
		}
	}
	
	
	
	public static void sendMessage(String msg) throws ClassNotFoundException{
		String yourmessage = "";
		try{
			if (out != null){
				out.writeObject(msg);
				out.flush();
//				System.out.println("server>" + msg);
				yourmessage = (String)in.readObject();
				System.out.println("server>" + yourmessage);
			}
		}
		catch(IOException ioException){
			System.out.println("server - Output is clear" + ioException.getMessage() +  "\n");
		}
		
	}
	
	public static void yourInput() throws IOException, ClassNotFoundException{
		while(true){
			String userInput = null;
			reader = new BufferedReader(isr = new InputStreamReader(System.in));
			
			userInput = reader.readLine();
			
			if(userInput.equals("exit")){ // with exit -  connection end
				System.out.println("close connection");
				break;
			}else{
				System.out.println("Message from Server is: " + userInput.toUpperCase());
				out.writeObject(userInput+"\n");
				out.flush();
			} // if
		}
	}
	
	public static void closeConnection(){
		try{
			in.close();
			out.close();
			providerSocket.close();
			System.out.println("server - Connection OFF");
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
			e.printStackTrace();
		}
//		System.out.println( inet.getCanonicalHostName() );
//		System.out.println( inet.getHostAddress() );
//		System.out.println( inet.toString() );
		return inet.getHostAddress();
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
	
	public static void main(String args[]) throws ClassNotFoundException, IOException, InterruptedException
	{
		WLAN_Server server = new WLAN_Server();

		connectServerSocket();
		createStreams(connection);

		System.out.println( server.getLocalIP("SYNCMASTER-PC"));
		connectionState("192.168.1.5", 1000);
		Data data = new Data();
		sendObject(data);
		
		
//		String yourmessage = "";
//		reader = new BufferedReader(isr = new InputStreamReader(System.in));
//		sendMessage("Your Input is: ");
//		System.out.println("Wait for Message from Client");
//		do{
//			yourmessage = reader.readLine();
//			sendMessage(yourmessage);
//			
//		}while(!yourmessage.equals("end"));
//		closeConnection();
//		connectServerSocket();
	}
}