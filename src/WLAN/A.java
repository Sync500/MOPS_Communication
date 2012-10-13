/**
 * 
 */
package WLAN;

import java.io.*;
import java.net.*;
public class A{
	private ServerSocket 	providerSocket;
	private Socket 			connection = null;
	ObjectOutputStream 		out;
	ObjectInputStream 		in;
	String 					message;
	BufferedReader 		reader = null;
	InputStreamReader   isr = null;
	A(){}
	void run()
	{
		try{
			//1. creating a server socket
			providerSocket = new ServerSocket(6665);
			//2. Wait for connection
			System.out.println("Waiting for connection");
			connection = providerSocket.accept();
			System.out.println("Connection received from " + connection.getInetAddress().getHostName());
			//3. get Input and Output streams
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			sendMessage("Connection successful");
			String yourmessage = "";
			//4. The two parts communicate via the input and output streams
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
				providerSocket.close();
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
			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	String getLocalIP(String host){
		InetAddress inet = null;
		try {
			inet = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println( inet.getCanonicalHostName() );
//		System.out.println( inet.getHostAddress() );
//		System.out.println( inet.toString() );
		return inet.getHostAddress();
	}
	
	public static void main(String args[]) throws UnknownHostException
	{
		A server = new A();
		
		while(true){
			server.run();
			
			System.out.println( server.getLocalIP("SYNCMASTER-PC"));
			
//			boolean istAn = true;
//			try {
//				istAn = InetAddress.getByName( "192.168.1.7" ).isReachable(3000);
//				System.out.println(istAn);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
		
	}
}
