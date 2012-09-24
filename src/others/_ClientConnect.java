/**
 * 
 */
package others;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *  @author Daniel Fay
 *  @version 1.0
 */

public class _ClientConnect extends Thread {

	/**
	 * set all var. with default value null
	 */
	
	Socket 				clientSocket = null;
	PrintWriter 		writer = null;
	BufferedReader 		reader = null;
	InputStreamReader   isr = null;
	String				host ="192.168.1.4";
	int 				port = 6665;
	
	_ClientConnect() throws IOException{
		try{
			// create the Socket - parameters: localhost and listen on port
			Socket clientSocket = new Socket(host,port);
			
			// generate object - OutputStream with autoFlush
			writer = new PrintWriter(clientSocket.getOutputStream(), true);
			// get the InputStream - data from socket
			//reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	
		}catch(Exception e){
			System.out.println("Socket not accessible!");
			clientSocket.close();
			System.exit(1);
		} // catch
	} // _ClientConnect
	
	public void run(){
		while(true){
			String userInput = null; // write message from InputStream to userInput
			System.out.println("Info #: Use exit to close the connection!");
			reader = new BufferedReader(isr = new InputStreamReader(System.in)); // create InputStream
			
     		try {
				userInput = reader.readLine(); // read next line in InputStream
			} catch (IOException e) {
				System.out.println("InputStream not readably!");
				e.printStackTrace();
			}
			
			if(userInput.equals("exit")){ // with exit -  connection end
				writer.print(userInput+"\n");
				writer.flush(); // write the last data bytes
				break;
			}else{
				System.out.println("InputStream: " + userInput.toUpperCase());
				writer.print(userInput+"\n");
				writer.flush();
			} // if
		} // while
	} // run
}
