/**
 * 
 */
package communication_V3;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Daniel Fay
 * @version 1.0
 *
 */
public class ServerConnect{
	/**
	 * set all var. with default value null
	 * 
	 */
	ServerSocket 		serverSocket = null;
	Socket 				clientSocket = null;
	BufferedWriter 		writer = null;
	BufferedReader 		reader = null;
	String				info = null;
	//private int 		port;  dont use port 1-2000
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated methode stub7
		@SuppressWarnings("unused")
		ServerConnect server = new ServerConnect(6665);	
	}
	
	ServerConnect(int port){
		try{
			serverSocket = new ServerSocket(port);
			System.out.println("Wait for client");
			clientSocket = serverSocket.accept(); // wait for the client
			System.out.println("Server Socket create!");
			
		}catch(Exception e){
			System.out.println("Socket dont create!");
			//e.printStackTrace();
		} // catch
		
		InetAddress iA = null;
		iA = clientSocket.getInetAddress();
//		info = "Server wait for the client on host " 
//				+ InetAddress.getLocalHost().getCanonicalHostName() 
//				+ " on port " + serverSocket.getLocalPort() +"";
		System.out.println("ClientIP: " + iA.getHostAddress() + "\n Porte: " + port);
		
		createStreams();
		
		while(true){
			try{
				if(reader.ready()){
					System.out.println("_ServerConnect will read data");
					readData();
					wait(500);
				} // if
			}catch(Exception e){
					//
			} // catch
		} // while	
	} // _ServerConnect constructor

// connection: ok - create streams for read and write
//writer = new PrintStream(clientSocket.getOutputStream(), true); 
	
	private void readData() throws IOException{
		String userMessage = "";
		userMessage = reader.readLine();
		try{
			if(userMessage == "close"){
				close();
			}else{
				while(reader.ready()){
					userMessage = reader.readLine();
					System.out.println("ServerMessage: " + userMessage + "\n");
				} // while
			} // if else	
		}catch(IOException ioe){
			System.out.println("Message dont readable");
		}	// catch
	} // readData

	private void close() {
		try{
			reader.close();
			clientSocket.close();
			serverSocket.close();
			
		}catch(IOException ioe){
			System.out.println("Streams and sockets dont closed right");
		}catch(Exception e){
			System.out.println("Socket cant closed");
		} // catch
		System.exit(0);
	} // close

	private void createStreams(){
//		try{
//			reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(clientSocket.getInputStream())));
//			System.out.println("reader constructed");
//		}catch(Exception e){
//			System.out.println("reader cant construct");
//		}
		try{
			writer = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream
					(clientSocket.getOutputStream())));
		}catch(Exception e){
			System.out.println("writer cant construct");
		}
	} // createStreams
	
} // _ServerConnect
