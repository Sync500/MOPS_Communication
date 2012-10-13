/**
 * 
 */
package WLAN;

import java.io.*;
import java.net.*;

public class B{
	Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message;
	BufferedReader 		reader = null;
	InputStreamReader   isr = null;
	B(){}
	void run()
	{
		try{
			//1. creating a socket to connect to the server
			requestSocket = new Socket("localhost", 6665);
			System.out.println("Connected to localhost in port 6665");
			//2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			String yourmessage = "";
			//3: Communicating with the server
			do{
				try{
					yourmessage = (String)in.readObject();
					System.out.println("server>" + yourmessage);
					sendMessage("Hi my server");
					reader = new BufferedReader(isr = new InputStreamReader(System.in));
					
					yourmessage = reader.readLine();
					sendMessage(yourmessage);
//					sendMessage(message);
				}
				catch(ClassNotFoundException classNot){
					System.err.println("data received in unknown format");
				}
			}while(!yourmessage.equals("bye"));
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	public static void main(String args[])
	{
		B client = new B();
		client.run();
	}
}
