/**
 * @author Daniel Fay
 * @version 1.0
 * @date: 17.10.2012 - 03:46
 */
package WLAN;

import java.io.*;
import java.net.*;
import java.util.Arrays;

/**
 *  @description: set member variable for this class
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
	 * 
	 */
	public static void connectSocket() throws UnknownHostException{
		try {
			socket = new Socket(host, port);
			System.out.println("Connected to Server");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Socket dont create: " + e.getMessage() + "\n");
		}
	}
	
	/**
	 * @param timeout
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void connectWithTimeOut(int timeout){
		System.out.println("Timeout is: " + timeout);
		SocketAddress sockaddr = new InetSocketAddress(host, port);
		socket = new Socket();
		try {
			socket.connect(sockaddr, timeout);
			createStreams(socket);
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("Server dont response");
			connectWithTimeOut(timeout);
		}
	}
	
	/**
	 * @param socket
	 * @throws ClassNotFoundException
	 * @throws IOException
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
	   * blocking read of data. Returns data in blobs as it was sent!
	   * Use of Exception is implementation defined.
	   * @return ByteArray or null if content is empty
	   * @throws IOException
	   */
	public static byte[] getByteArray() throws IOException {		
		try{
			int len = 0;
	    	len = inByte.readInt();
			System.out.println("ByteArray lenght: " + len);
			byte[] ByteArray = new byte[len];				
		    	if (len > 0){
					inByte.readFully(ByteArray); // read the ByteArray from sockets InputStream
				}
				return ByteArray;
	    }catch(IOException e){
	    	e.getMessage();
		}
		return null; 
	}
	
	/**
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Data getObject(Data data){
		try{
			// if the class is not found then throw ClassNotFoundException 
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
	 * @param msg
	 * @throws ClassNotFoundException
	 */
	public static void sendMessage(String msg) throws ClassNotFoundException{
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
	 * @param msg
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void getMessage(String msg){
		String yourmessage = "";
		try{
			if (in != null){
				yourmessage = (String)in.readObject();
				System.out.println("client>" + yourmessage);
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
	 * 
	 */
	public static void closeConnection(){
		try{
			in.close();
			out.close();
			inByte.close();
			socket.close();
			System.out.println("client> Connection OFF");
		}
		catch(IOException ioe){
			System.out.println("Connection isnt closed: " + ioe.getMessage() + "\n");
		}
	}
	
	
	/** check the state of the socket connection 
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
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public static void main(String args[]) throws IOException, InterruptedException
	{
//		WLAN_Client client = new WLAN_Client();
		connectWithTimeOut(2000);
		
		boolean isCon;
		isCon = socket.isConnected();
		
		while(isCon == true){
			System.out.println(Arrays.toString(getByteArray()));
			
			Thread.sleep(2000);
			System.out.println("Transfer your Data");
			System.out.println(Arrays.toString(getByteArray()));
			
			Data data = new Data();
			getObject(data);
			System.out.println("Object received");
			connectionState(socket);
			System.out.println("Content of sendWait");
			System.out.println(Arrays.toString(getByteArray()));
			isCon = false;
		}
		closeConnection();
	}
}
