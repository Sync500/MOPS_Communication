/**
 * @author Daniel Fay
 * @version 1.0
 * @date: 17.10.2012 - 17:00
 */
package WLAN;

import java.io.*;
import java.net.*;
import java.util.Arrays;

/**
 *  @description: set member variable
 */
public class WLAN_Client{
	private static Socket 					socket = null;
	private static ObjectOutputStream 		out;
 	private static ObjectInputStream 		in;
 	private static DataInputStream			inByte;
// 	private static BufferedReader 			reader;
// 	private static InputStreamReader   		isr;
 	
	final static int 						port = 6665;
	final static String 					host = "192.168.1.7";
	
	WLAN_Client(){}
	
	/**
	 * @description: connecting to Server without timeout
	 * 
	 * @throws UnknownHostException - if the IP/host address of the host could 
	 * 		   not be determined
	 * @throws IOException - if an I/O error occurs when creating the socket
	 */
	public static void connectSocket() throws UnknownHostException{
		try {
			socket = new Socket(host, port);
			System.out.println("Connect to Server");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Socket dont create: " + e.getMessage() + "\n");
		}
	}
	
	/**
	 * @description: connect to Server with timeout
	 * 
	 * @param timeout - set timeout if Server don't response 
	 * @throws IOException - if Server don't response then connect again
	 */
	public static void connectWithTimeOut(int timeout){
		System.out.println("Timeout is: " + timeout);
		SocketAddress sockaddr = new InetSocketAddress(host, port);
		socket = new Socket();
		try {
			socket.connect(sockaddr, timeout);
			createStreams(socket);
		} catch (IOException e) {
			System.out.println("Server dont response");
			connectWithTimeOut(timeout);
		}
	}
	
	/**
	 * @description: create necessary streams to transfer Data
	 * 
	 * @param socket
	 * @throws IOException -if an I/O error occurs when creating the output stream or 
	 * 		   if the socket is not connected
	 */
	public static void createStreams(Socket socket) throws IOException{
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in =  new ObjectInputStream(socket.getInputStream());
			inByte = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("client> Streams dont created: " + e.getMessage() +  "\n");
			closeConnection();
		}
	}
	
	/**
	   * @description: blocking read of data. Returns data in blobs as it was sent!
	   * 			   Use of Exception is implementation defined.
	   * 
	   * @return ByteArray or null if content is empty
	   * @throws IOException - the stream has been closed and the contained input stream
	   *         does not support reading after close, or another I/O error occurs
	   */
	public static byte[] getByteArray() throws IOException {		
		try{
			int len = 0;
	    	len = inByte.readInt();
			System.out.println("ByteArray lenght: " + len);
			byte[] ByteArray = new byte[len];				
		    	if (len > 0){
		    		// read the ByteArray from InputStream
					inByte.readFully(ByteArray); 
				}
				return ByteArray;
	    }catch(IOException e){
	    	e.getMessage();
		}
		return null; 
	}
	
	/**
	 * @description: Object from sender will be show 
	 * @return object - data type is Data (serialize class)
	 * 
	 * @throws IOException - If an I/O error has occurred
	 * @throws ClassNotFoundException - if Class of a serialized object cannot be found
	 */
	public static Data getObject(Data data){
		try{
			data = (Data) in.readObject(); // read serialize class/object
			in.close();
//			System.out.println("Object received");
		}catch(IOException ioException){
			System.out.println("I/Output is empty: " + ioException.getMessage() +  "\n");
		} catch (ClassNotFoundException cnfe) {
			cnfe.getMessage();
		}
		return data;
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
				System.out.println("client>" + msg);
			}
		}
		catch(IOException ioException){
			System.out.println("client> Output is clear" + ioException.getMessage() +  "\n");
		}
		
	}
	
	/**
	 * @description: read Object send from Sender
	 * return the content
	 * 
	 * @throws ClassNotFoundException - if serialized class not found
	 * @throws IOException - if InputStream throw any Exception
	 */
	public static String getMessage(){
		String yourmessage = "";
		try{
			if (in.available() != 0){
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
	 * show user message that the connection is off 
	 * 
	 * @throws IOException if connection can't closed
	 */
	public static void closeConnection(){
		try{
			in.close();
			out.close();
			inByte.close();
			socket.close();
			System.out.println("client> Connection OFF");
			sleep(1000);
		}
		catch(IOException ioe){
			System.out.println("Connection isnt closed: " + ioe.getMessage() + "\n");
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
//		WLAN_Client client = new WLAN_Client();
		connectWithTimeOut(2000);
		
		boolean isCon;
		isCon = socket.isConnected();
		
		while(isCon == true){
			System.out.println(Arrays.toString(getByteArray()));
			sleep(2000);
			System.out.println("Transfer your Data");
			System.out.println(Arrays.toString(getByteArray()));
			
			Data data = new Data();
			getObject(data);
			System.out.println("Object received");
			
			if(socket.isClosed() == true){
				System.out.println("Your Socket is closed");
				closeConnection();
				connectWithTimeOut(2000);
			}
			
			System.out.println("\n Content of sendWait");
			System.out.println(Arrays.toString(getByteArray()));
			isCon = false;
		}
		closeConnection();
	}
}
