/**
 * 
 */
package WLAN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *  @author Daniel Fay
 *  @version 1.0
 */

public class RunClient extends Thread {

	/**
	 * set all var. with default value null
	 */
	
	Socket 				clientSocket = null;
	PrintWriter 		writer = null;
	BufferedReader 		reader = null;
	InputStreamReader   isr = null;
	String				host ="127.0.0.1";
	int 				port = 6665;
	
	RunClient() throws IOException{
		try{
			// create the Socket - parameters: localhost and listen on port
			Socket clientSocket = new Socket(host,port);
			
			// generate object - OutputStream with autoFlush
			writer = new PrintWriter(clientSocket.getOutputStream(), true);
			// get the InputStream - data from socket
			//reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	
		}catch(Exception e){
			System.out.println("Socket not accessible!");
			System.exit(1);
			
		} // catch
	} // RunClient
	
	public void run(){
			System.out.println("Connection ready. Transfer your Data!");
	} // run
}
