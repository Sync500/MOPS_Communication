/**
 * 
 */
package WLAN;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Daniel
 * @version 1.0
 * @date: 26.10.2012
 */
public class WlanManager{

	public static void main(String[] args) throws IOException {
		try{
			new Server().start(); // JVM use the run-method and start two threads
			new Client().start(); // Client start - its the same
		
		}catch(Exception e){
			System.out.println("Server connection not possible or currently busy!");
			e.printStackTrace();
		}
	}
}

class Server extends Thread{
	
	private static Socket socket;
	
	public void run(){
		
		System.out.println("run from WlanManager");
		int i = 0;
		while(true){
			System.out.println("From Server i is now: " + i);
			Connecting.connectServerSocket();
		    System.out.println("Sockets created - server");
		    try {
				System.out.println(Arrays.toString(Send_Read.read()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			boolean isCon = socket.isConnected();
			System.out.println("Connect state is: " + isCon);
			System.out.println("Transfer your Data");
			i++;
		}
	}
}
