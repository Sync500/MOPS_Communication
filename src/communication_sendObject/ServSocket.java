/**
 * @description: Server get object/data from client
 */
package communication_sendObject;

import java.net.*;
import java.io.*;

/**
 * @author Daniel Fay
 * @version 1.0
 */
public class ServSocket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1343171545307790701L;
	/**
	 * set all var. with default value null
	 */
	ServerSocket 		serverSocket = null;
	Socket 				clientSocket = null;
	ObjectInputStream 	objInput = null;
	ObjectOutputStream 	objOutput = null;
	InetAddress 		ia = null;
	int 				port = 6665;
	
	/**
	 * @param
	 */
	
	ServSocket(){
		try{
			System.out.println("Start the clientSocket!");
			serverSocket = new ServerSocket(port); // generate object - listen on port 6665
			Socket clientSocket = serverSocket.accept(); // Wait on client
			
			ia = clientSocket.getInetAddress();
			System.out.println("Welcome on " + ia); // show the client ip4 addr
			
			objInput = new ObjectInputStream(clientSocket.getInputStream()); // return Input
			TestData in =  (TestData) objInput.readObject(); // read serialize class/object - generate new object and cast 
			
			System.out.println("From SOCKET: \n" + in.getOBject_message()); // show message from client
			//System.out.println("Server " + in.setMessage());
			System.out.println("From SOCKET:" + in.getObject_calculatet()); // show message from client
			
			objOutput = new ObjectOutputStream(clientSocket.getOutputStream()); // return Output
			objOutput.writeObject(in);
			System.out.println("Server say: successful");
			clientSocket.close(); // end: client closed - connection lost
			
		}catch(IOException e){
			System.out.println("Server say: connection error");
			System.out.println("Please start the client with port: " + port);
		}catch (ClassNotFoundException e){
			//TODO Auto-generated catch block
			e.printStackTrace();
		} // catch
	} // ServSocket
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		ServSocket server = new ServSocket(); // start constructor
	}
}
