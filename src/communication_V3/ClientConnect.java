/**
 * 
 */
package communication_V3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *  @author Daniel Fay
 *  @version 1.0
 */

public class ClientConnect{
	
	public static void main(String[] args) throws Exception {
		@SuppressWarnings("unused")
		ClientConnect client =  new ClientConnect("localhost",6665);
		
	} // public static
	/**
	 * set all var. with default value null
	 */

	Socket 				clientSocket = null;
	BufferedWriter 		writer = null;
	BufferedReader 		reader = null;
	
	ClientConnect(String host, int port){
		try{
			clientSocket = new Socket(host, port);
			System.out.println("Client Socket create!");
			
		}catch(Exception e){
			System.out.println("Socket dont create!");
			//e.printStackTrace();
		} // catch
		
		createStreams();
		System.out.println("Your Message");
		String msg = "";
		try {
			msg = reader.readLine();
			System.out.println("Your Message is " + msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendMessage();
		
	} // _ServerConnect constructor

// connection: ok - create streams for read and write
//writer = new PrintStream(clientSocket.getOutputStream(), true); 
	
	private void sendMessage() {
		try{
			String data = "";
			if(data != ""){
				writer.write(data);
				writer.newLine();
				writer.flush();
			}else{
				close();
			} // if else
		
		}catch(IOException ioe){
			System.out.println("Message dont readable");
		}	// catch	
	} // sendMessage

	private void close() {
		try{
			writer.close();
			clientSocket.close();
			
		}catch(IOException ioe){
			System.out.println("Streams and sockets dont closed right");
		}catch(Exception e){
			System.out.println("Socket cant closed");
		} // catch
		System.exit(0);
	} // close

	private void createStreams() {
//		try{
//			writer = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream
//					(clientSocket.getOutputStream())));
//		}catch(Exception e){
//			System.out.println("reader cant construct");
//		}
		
		try{
			reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(clientSocket.getInputStream())));
			System.out.println("reader constructed");
		}catch(Exception e){
			System.out.println("reader cant construct");
		}
		
	} // createStreams
	
} // _ClientConnect
