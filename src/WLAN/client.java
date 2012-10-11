/**
 * 
 */
package WLAN;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * @author Syncmaster_extreme
 *
 */
public class client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3860845575597353978L;
	/**
	 * set all var. with default value null
	 */
	
	Socket 				clientSocket = null;
	ObjectInputStream 	objInput = null;
	ObjectOutputStream 	objOutput = null;
	final int 			port = 6665;
	
	client(String ip){
		try{
			Socket clientSocket = new Socket(ip,port); // create the Socket - parameters: localhost and listen on port 6665
			clientSocket.setKeepAlive(true);
			
			sendObject(clientSocket);

			}catch(IOException e){
				System.out.println("Socket Error!");
			} // catch
	} // clientSocket
	
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
	} // clientSocket
	
	void close(){
		try {
			clientSocket.close();
			objOutput.close();
			objInput.close();
		} catch (IOException e) {
			System.out.println("Client isn't closed!");
			e.printStackTrace();
		} // end: client closed - connection lost
}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		client client = new client("localhost");
	}
}
