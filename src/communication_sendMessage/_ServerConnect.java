/**
 * 
 */
package communication_sendMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Daniel Fay
 * @version 1.0
 *
 */
public class _ServerConnect {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated methode stub
		try{
				new Server().start(); // JVM use the run-method and start two threads
				new _ClientConnect().start(); // Client start - its the same
			
		}catch(Exception e){
			System.out.println("Server conn. not possible or currently busy!");
			//e.printStackTrace();
		}
	} // _ServerConnect
}

class Server extends Thread{
	
	/**
	 * set all var. with default value null
	 * 
	 */
	ServerSocket 		serverSocket = null;
	Socket 				clientSocket = null;
	PrintWriter 		writer = null;
	BufferedReader 		reader = null;
	InetAddress 		ip = null;
	int 				port = 6665; // dont use port 1-2000
	
	Server() throws Exception{	
		serverSocket = new ServerSocket(port); // generate object for sockets
	}

	@Override
	public void run(){
		
		while(true){
			try{
					clientSocket = serverSocket.accept(); // wait on client
					System.out.println("Socket create");
					
					ip = clientSocket.getInetAddress();
					System.out.println("Server IP is:"+ ip + "\n"); // show the client ip4 addr.
					
					 // generate object - OutputStream with autoFlush
					writer = new PrintWriter(clientSocket.getOutputStream(), true);
					// get the InputStream - data from socket
					reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					
					while(true){
						String zeile = reader.readLine(); // read message line-by-line from client
						
						if(zeile.equals("exit")){ // with exit -  Server connection end
							System.out.println("close connection");
							break;
						}else{
							System.out.println("Server say: " + zeile + "\n");
							writer.print(zeile+"\n");
							writer.flush();
						} // if
					} // while

				break;
					
				}catch(IOException e){
					System.out.println("Server say: connection error");
					System.out.println("Please start the client and listen on port: " + port);
				} //catch
		} // while
	} //run
}
