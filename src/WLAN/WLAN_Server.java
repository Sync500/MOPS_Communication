/**
 * @author Daniel Fay
 * @version 1.0
 */

package WLAN;

import java.io.*;
import java.net.*;
import java.util.Date;

public class WLAN_Server{
	/**
	 *  @description: set member variable for this class
	 */
	private static ServerSocket 					server = null;
	private static Socket 							socket = null;
	private static ObjectOutputStream 				out;
	private static ObjectInputStream 				in;
	private static DataOutputStream					outByte;
//	private static BufferedReader 					reader;
//	private static InputStreamReader   				isr;
	
	final static int port = 6665;
	final static String host = "192.168.1.5";
	
	WLAN_Server(){}
	
	/**
	 * 
	 */
	public static void connectServerSocket(){
		try {
			server = new ServerSocket(port);
			WaitForClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ServerSocket dont create: " + e.getMessage() +  "\n");
		}
	}
	
	/**
	 * 
	 */
	public static void WaitForClient(){
		System.out.println("Waiting for Client");
		try {
			socket = server.accept();
			Thread.sleep(1000);
			createStreams(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Client dont response: " + e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception in WaitForClient: " + e.getMessage());
		} 
		System.out.println("Socket received from " + socket.getInetAddress().getHostName());
		connectionState(socket);
	}
	
	/**
	 * @param socket
	 * @throws ClassNotFoundException
	 */
	public static void createStreams(Socket socket){
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			outByte = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("server> Streams dont created: " + e.getMessage() +  "\n");
			closeConnection();
		}
	}
	
	/**
	   * send a blob of data. Sending may fail without notice or with exception. 
	   * @param data  data to send
	   * @throws IOException  on exception or failure in underlying protocol
	   */
	public static void sendByteArray(byte[] data){
		send(data, 0, data.length);
	}
	
	/**
	 * @param ByteArray
	 * @param start point of the array
	 * @param len of the passed ByteArray
	 * @throws IOException or IllegalArgumentException or IndexOutOfBoundsException
	 */
	public static void send(byte[] ByteArray, int start, int len){
		//	check length of the array and her IndexBound	
		//  maybe others checks are necessary
		if (len < 0){
			throw new IllegalArgumentException("The length of the Array cannot be negative");
		}
		if (start < 0 || start >= ByteArray.length){
	        throw new IndexOutOfBoundsException("Your value is: Out of bounds: " + start);
		}
		
		try{
			// send ByteArray Length and the content to receiver
			outByte.writeInt(len);
//			System.out.println("Length is: " + len);
			if (len > 0){
				outByte.write(ByteArray, start, len);
				System.out.println("Sending ByteArray - complete");
			}
		}catch (IOException ioe){
			ioe.getMessage();
		}
		
	}
	  
	  /**
	   * send a blob of data and block until success or failure is signaled.
	   * @param data data to send
	   * @throws IOException, InterruptedException on failure
	   */
	public static void sendWait(byte[] data, int timeout){
		try {
			System.out.println("Timeout is: " + timeout);
			send(data, 0, data.length);
			Thread.sleep(timeout);
			System.out.println("Sending Data - complete");
			
		} catch (InterruptedException ie) {
			System.out.println("Exception in sendWait: " + ie.getMessage());
		}
	}
	  
	/**
	 * @param data - ByteArray 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public static void sendObject(Data data) {
		try{
			out.writeObject(data);
			out.flush();
			out.close();
			System.out.println("Server say: successful");
		}catch(IOException ioException){
			System.out.println("server> I/Output is empty: " + ioException.getMessage() +  "\n");
			closeConnection();
		}
	}
	
	/**
	 * @param msg - get messages from to receiver
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void getMessage(String msg){
		String yourmessage = "";
		try{
			if (in != null){
				yourmessage = (String)in.readObject();
				System.out.println("server>" + yourmessage);
			}
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}	
	}
	
	/**
	 * @param msg - this message will send to receiver
	 * @throws ClassNotFoundException
	 */
	public static void sendMessage(String msg) throws ClassNotFoundException{
		try{
			if (out != null){
				out.writeObject(msg);
				out.flush();
				System.out.println("server>" + msg);
			}
		}
		catch(IOException ioException){
			System.out.println("server> Output is clear" + ioException.getMessage() +  "\n");
		}	
	}
	
	/**
	 * @throws InterruptedException
	 */
	public static void closeConnection(){
		try{
			in.close();
			out.close();
			outByte.close();
			server.close();
			socket.close();
			System.out.println("server> Connection OFF");
		}
		catch(IOException ioe){
			System.out.println( ioe.getMessage() );
		}
	}
	
	
	/**  check the state of the socket connection 
	 * 
	 * @return boolean
	 */
	public static boolean connectionState(Socket socket){
		boolean istAn = true;
//		istAn = InetAddress.getByName( host ).isReachable(timeout);
		istAn = socket.isConnected();
		
		if (istAn == false){
			System.out.println("Connection is: " + socket.isConnected());
			closeConnection();
			return false;
		}
		System.out.println("Connection is: " + socket.isConnected());
		return true;
	}
	
	
	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String args[]) throws IOException, InterruptedException
	{
			//		WLAN_Server server = new WLAN_Server();
			connectServerSocket();
			byte b[] = new byte[4];
			b[0] = 12;
		    b[1] = 11;
		    b[2] = 123;
		    
		    byte c[] = new byte[4];
		    c[0] = 20;
		    
		    boolean isCon;
		    isCon = socket.isConnected();
		    
		    while(isCon == true){
			    sendByteArray(b); 
			    Thread.sleep(2000);
			    
			    sendByteArray(c);
			    Thread.sleep(2000);
//			    
			    Data data = new Data();
			    sendObject(data);
			    System.out.println("Send complete");
			    Thread.sleep(2000);
			    connectionState(socket);
			    
			    System.out.println("SendToWait");
			    sendWait(b,2000);
			    isCon=false;
			}
		    System.out.println(server.isClosed());
		    closeConnection();
		    System.out.println(server.isClosed());
	}
}