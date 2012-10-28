/**
 * 
 */
package WLAN;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import WLAN.oldFiles.Data;

/**
 * @author Daniel
 * @version 1.0
 * @date: 26.10.2012
 */
public class Client extends Thread {
	private static Socket socket = null;
	private static ServerSocket server = null;
	
	Client()throws IOException{
		
	}
	/**
	 * @param args
	 */
	public void run(){
		int i=0;
		byte b[] = new byte[4];
		b[0] = 12;
		b[1] = 11;
		b[2] = 123;
		try{
			System.out.println("From Client i is now: " + i);
			Connecting.connectWithTimeOut(2000);
			System.out.println("Client is connect");
			ConnectionHandler.sleep(2000);
			System.out.println("Your Socket is open");
			
			Send_Read.sendByteArray(b);
			System.out.println("Sending Data...");
			ConnectionHandler.sleep(2000);
			i++;
		}catch(Exception e){
			System.out.println("Socket not accessible!");
			System.exit(1);
			
		} // catch
		
		System.out.println("Connection ready. Transfer your Data!");
		
		
		
//		boolean isCon;
//		isCon = socket.isConnected();
//		
//		while(true){
//			System.out.println(Arrays.toString(Send_Read.read()));
//			ConnectionHandler.sleep(2000);
//			System.out.println("Transfer your Data");
//			System.out.println(Arrays.toString(Send_Read.read()));
//			
//			if(socket.isClosed() == true){
//				System.out.println("Your Socket is closed");
//				ConnectionHandler.closeConnection();
//				Connecting.connectWithTimeOut(2000);
//			}
//			
//			System.out.println("\n Content of sendWait");
//			System.out.println(Arrays.toString(Send_Read.read()));
//			isCon = false;
//			ConnectionHandler.closeConnection();
//		}
	}

}
