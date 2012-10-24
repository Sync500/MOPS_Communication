/**
 * @author Daniel Fay
 * @version 1.0
 * @date: 17.10.2012 - 17:00
 */

package WLAN.oldFiles;

import java.io.*;
import java.net.*;


public class WLAN_Server_old{
	/**
	 *  @description: set member variable
	 */
	private static ServerSocket 					server = null;
	private static Socket 							socket = null;
	private static ObjectOutputStream 				out;
	private static ObjectInputStream 				in;
	private static DataOutputStream					outByte;
//	private static BufferedReader 					reader;
//	private static InputStreamReader   				isr;
	
	final static int 								port = 6665;
	final static String 							host = "192.168.1.5";
	
	WLAN_Server_old(){}
	
	/**
	 * @description: create ServerSocket and wait for client
	 * 
	 * @throws IOException - if an I/O error occurs when opening the socket
	 * 
	 */
	public static void connectServerSocket(){
		try {
			server = new ServerSocket(port); // throw IOException
			WaitForClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ServerSocket dont create: " + e.getMessage() +  "\n");
		}
	}
	
	/**
	 * @description: Server wait for response from client
	 * 
	 * @throws IOException - if an I/O error occurs when waiting for a connection
	 * if an I/O error occurs when creating the output stream or if the socket is not connected
	 * 
	 */
	public static void WaitForClient(){
		System.out.println("Waiting for Client");
		try {
			socket = server.accept(); // throw IOException
			sleep(1000);
			createStreams(socket); // throw IOException
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Client dont response: " + e.getMessage());
		} 
		System.out.println("Socket received from " + socket.getInetAddress().getHostName());
		connectionState(socket);
	}
	
	/**
	 * @description: create necessary streams to transfer Data
	 * 
	 * @param socket
	 * @throws IOException -if an I/O error occurs when creating the output stream or 
	 * 		   if the socket is not connected
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
	   * @description: send a blob of data. Sending may fail without notice or with exception
	   * 
	   * @param data -  data to send
	   * @throws IOException  on exception or failure in underlying protocol
	   */
	public static void sendByteArray(byte[] data){
		send(data, 0, data.length);
	}
	
	/**
	 * @description: send ByteArray to receiver
	 * 				 check length of the array and her IndexBound
	 * 				 maybe others checks are necessary
	 * 
	 * @param ByteArray
	 * @param start point of array
	 * @param len of the passed ByteArray
	 * @throws IOException or IllegalArgumentException or IndexOutOfBoundsException
	 */
	public static void send(byte[] ByteArray, int start, int len){
		if (len < 0){
			throw new IllegalArgumentException("The length of the Array cannot be negative");
		}
		if (start < 0 || start >= ByteArray.length){
	        throw new IndexOutOfBoundsException("Your value is: Out of bounds: " + start);
		}
		
		try{
			// send ByteArray Length and content to receiver
			outByte.writeInt(len);
//			System.out.println("Length is: " + len);
			if (len > 0){
				outByte.write(ByteArray, start, len);
//				System.out.println("Sending ByteArray - complete");
			}
		}catch (IOException ioe){
			ioe.getMessage();
		}
		
	}
	  
	  /**
	   * @description: send a blob of data and block until success or failure is signaled
	   * 
	   * @param data data to send
	   */
	public static void sendWait(byte[] data, int timeout){
		System.out.println("Waiting: " + timeout);
		send(data, 0, data.length);
		sleep(timeout);
		System.out.println("Sending Data - complete");
	}
	  
	/**
	 * @description: send Object to receiver 
	 * 
	 * @param data - serialize class with the whole content 
	 * @throws IOExceptioin - Any exception thrown by the underlying OutputStream or I/O error has occurred
	 */
	public static void sendObject(Data data) {
		try{
			out.writeObject(data); // throw IOException
			out.flush(); // throw IOException
			out.close(); // throw IOException
			System.out.println("Server say: successful");
		}catch(IOException ioe){
			System.out.println("server> I/Output is empty: " + ioe.getMessage() +  "\n");
		}
	}
	
	/**
	 * @description: send Message to receiver
	 * first check if the output is ready then transfer the message as object
	 * with flush we send also the last bytes from stream to receiver 
	 * 
	 * @param msg - this message will send to receiver
	 * @throws IOException -Any exception thrown by the underlying OutputStream or
	 * 		   I/O error has occurred
	 */
	public static void sendMessage(String msg){
		try{
			if (out != null){
				out.writeObject(msg);
				out.flush();
				System.out.println("server>" + msg);
			}
		}
		catch(IOException ioException){
			System.out.println("server> Output is empty: " + ioException.getMessage() +  "\n");
		}	
	}
	
	/**
	 * @description: read Object send from Sender
	 * @return the message from receiver
	 * 
	 * @throws ClassNotFoundException - if serialized class not found
	 * @throws IOException - if InputStream throw any Exception
	 */
	public static String getMessage(){
		String yourmessage = "";
		try{
			if (in != null){
				yourmessage = (String)in.readObject();
			}
		}
		catch(IOException ioException){
			System.out.println("client> I/Output is empty: " + ioException.getMessage() +  "\n");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		return yourmessage;
	}
	
	/**
	 * @description: thread will wait timeout seconds then continue
	 * 
	 * @param timeout
	 * @throws IntrruptedException - when a thread is waiting, sleeping, or otherwise occupied, 
	 * 		   and the thread is interrupted, either before or during the activity 
	 */
	public static void sleep(int timeout){
		try{
			Thread.sleep(timeout);
		}catch(InterruptedException ie){
			ie.getMessage(); 
		}
	}
	
	/**
	 * @description: close all streams and sockets
	 * show user message that connection is off 
	 * 
	 * @throws IOException - if an I/O error occurs
	 */
	public static void closeConnection(){
		try{
			in.close();
			out.close();
			outByte.close();
			server.close();
			socket.close();
			System.out.println("server> Connection OFF");
			sleep(1000);
		}
		catch(IOException ioe){
			System.out.println( ioe.getMessage() );
		}
	}
	
	
	/**  
	 * @description: check the state of the socket connection 
	 * show state of the connection
	 * @return boolean
	 */
	public static boolean connectionState(Socket socket){
		boolean istOn = true;
//		istAn = InetAddress.getByName( host ).isReachable(timeout);
		istOn = socket.isConnected();
		
		if (istOn == false){
			System.out.println("Connection is: " + socket.isConnected());
			closeConnection();
			return false;
		}
		System.out.println("Connection is: " + socket.isConnected());
		return true;
	}
	
	
	/**
	 * @description: the main
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException
	{
//			WLAN_Server_old server = new WLAN_Server_old();
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
			    sleep(2000);
			    
			    sendByteArray(c);
			    sleep(2000);
			    
			    Data data;
				data = new Data();
			    sendObject(data);
			    System.out.println("Send complete");
//			    connectionState(socket);
			    
			    if(socket.isClosed() == true){
					System.out.println("Your Socket is closed");
					closeConnection();
					connectServerSocket();
				}
			    
			    System.out.println("\n Content of sendWait");
			    sendWait(c,2000);
			    isCon=false;
			}
		    closeConnection();
	}
}