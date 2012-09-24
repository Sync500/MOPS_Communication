package communicationInterface;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class sockets extends Thread{
	
	private static ServerSocket serverSocket;
	
	public void clientConnect(String servername, int port){
	    try{
	       Socket sockClient = new Socket(servername, port);
	       System.out.println("connected to " + sockClient.getRemoteSocketAddress());
	       OutputStream outToServer = sockClient.getOutputStream();
	       DataOutputStream out = new DataOutputStream(outToServer);
	       //System.out.println(out);
	
	       out.writeUTF("client is ready " + sockClient.getLocalSocketAddress());
	       InputStream inFromServer = sockClient.getInputStream();
	       DataInputStream in = new DataInputStream(inFromServer);
	       System.out.println("Server answer " + in.readUTF());
	       //sockClient.close();
	       return;
	    }catch(IOException e) {
	    	System.out.println("connection: Socket error");
	    	e.printStackTrace();
	   }
	}
	
	public void serverConnect(String servername, int port){
	         try{
	            System.out.println("Waiting for client " +
	            serverSocket.getLocalPort() + "...");
	            Socket server = serverSocket.accept();
	            System.out.println("Just connected to " + server.getRemoteSocketAddress());
	            DataInputStream in = new DataInputStream(server.getInputStream());
	            System.out.println(in.readUTF());
	            DataOutputStream out = new DataOutputStream(server.getOutputStream());
	            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress());
	            server.close();
	         }catch(SocketTimeoutException s){
	            System.out.println("Socket: timed out!");
	         }catch(IOException e){
	            e.printStackTrace();
	         }
	      }
    
    public static void main(String[] args) throws IOException{
    	serverSocket = new ServerSocket(6665);
		serverSocket.setSoTimeout(10000);
		
    	sockets client = new sockets();
    		client.clientConnect("localhost", 6665);
    		
    		client.serverConnect("localhost", 6665);
    }
}
