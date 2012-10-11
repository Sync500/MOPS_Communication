/**
 * 
 */
package WLAN;

import java.net.*;
import java.io.*;

/**
 * @author Syncmaster_extreme
 *
 */
public class connect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9074252370195277792L;
	
	ServerSocket 		serverSocket = null;
	Socket 				clientSocket = null;
	ObjectInputStream 	objInput = null;
	ObjectOutputStream 	objOutput = null;
	final int 			port = 6665;
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	
	connect(){
		try{
			System.out.println("Start the client!");
			serverSocket = new ServerSocket(port); // generate object - listen on port 6665
			Socket clientSocket = serverSocket.accept(); // Wait on client
			clientSocket.setKeepAlive(true);
			
			sendObject(clientSocket);
			
		}catch(IOException e){
			System.out.println("Connection Error!");
		}catch(IllegalArgumentException iae){
			System.out.println("Only Portnumbers between 0 - 65535 allowed");
		}
	} // ServSocket
	
	void sendObject(Socket socket){
		try{
			objInput = new ObjectInputStream(socket.getInputStream()); // return Input
			TestData in =  (TestData) objInput.readObject(); // read serialize class/object - generate new object and cast 
			
			System.out.println("Server " + in.setMessage());
			
			objOutput = new ObjectOutputStream(socket.getOutputStream()); // return Output
			objOutput.writeObject(in);
			System.out.println("Server say: successful");
			
		}catch(IOException e){
			System.out.println("No Data or Object found!");
			e.printStackTrace();
			close();
		}catch (ClassNotFoundException e){
			//TODO Auto-generated catch block
			e.printStackTrace();
		} // catch
	} // ServSocket
	
	void close(){
			try {
				clientSocket.close();
				objOutput.close();
				objInput.close();
			} catch (IOException e) {
				System.out.println("Client dont closed!");
				e.printStackTrace();
			} // end: client closed - connection lost
	}
	
	public static void main(String[] args) throws IOException {
		connect connect = new connect(); // start constructor
	}

}
