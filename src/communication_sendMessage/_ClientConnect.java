/**
 * 
 */
package communication_sendMessage;

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
	//String			host ="192.168.1.7";
	String				host;
	int 				port = 6665;
	
	public String setValues(int nr) throws IOException {
		reader = new BufferedReader(isr = new InputStreamReader(System.in));
		String value = "";
		int i = 0;
		int yourTry = 6; // User have 5trys to write the right IP Address
		
		// this gives user to write the IP Address to the Server 
		// also we check if the Input value is empty or the length is less 9 characters
		if (nr == 1){
			try {
				do{
					if (i == yourTry){ // User have 5trys
						System.out.println("You have " + yourTry + " trys. Now we use the default Host 'localhost'!");
						value = "localhost";
						return value;
					}
					System.out.println("No IP detected or wrong IP! Please write the IP to the Server!");
					System.out.println("For example: 127.0.0.1 or 192.168.1.1 or localhost! Your can also write the Hostname!");
					value = reader.readLine();
					value = value.trim();
					i++;
					System.out.println("Its your " + i + "try.");
				}while(value.isEmpty() || (value.length() < 9));
				
				return value;
				
			} catch (IOException e) {
				e.printStackTrace();
				reader.close();
				return "Something is bad";
			} // catch
		} // if
		return host;
	}
	
	_ClientConnect() throws IOException{
		try{
			host = setValues(1);
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
			} //catch
			
			if(userInput.equals("exit")){ // with exit -  connection end
				writer.print(userInput+"\n");
				writer.flush(); // write the last data bytes
				writer.close();
				break;
			}else{
				System.out.println("InputStream: " + userInput.toUpperCase());
				writer.print(userInput+"\n");
				writer.flush();
			} // if
		} // while
	} // run
}
