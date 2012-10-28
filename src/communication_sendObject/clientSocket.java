/**
 * @description: client send object/data to Server
 */
package communication_sendObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;

/**
 *  @author Daniel Fay
 *  @version 1.0
 */
public class clientSocket implements Serializable {

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
	int 				port = 6665;
	
	clientSocket(){
		try{
			@SuppressWarnings("resource")
			Socket clientSocket = new Socket("localhost",port); // create the Socket - parameters: localhost and listen on port 6665
			
			TestData po = new TestData(); // instance of the serialize class
			objOutput = new ObjectOutputStream(new PrintStream(clientSocket.getOutputStream())); // return Output
			objOutput.writeObject(po);
			
			objInput = new ObjectInputStream(clientSocket.getInputStream()); // return Input
			TestData in = (TestData)objInput.readObject(); // read serialize class/object - generate new object and cast
			
			System.out.println("From SERVER : " + in.getOBject_message()); // the var. content send to Server
			//System.out.println("Client " + in.setMessage());
			System.out.println("From SERVER : " + in.getObject_calculatet()); // the var. content send to Server

			}catch(IOException e){
				System.out.println("Client say: Socket Error");
			}catch (ClassNotFoundException e){
				//TODO Auto-generated catch block
				e.printStackTrace();
			} // catch
	} // clientSocket
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		clientSocket client = new clientSocket();
	}
}
