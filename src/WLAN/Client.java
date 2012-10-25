/**
 * 
 */
package WLAN;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import WLAN.oldFiles.Data;

/**
 * @author Daniel
 * @version 1.0
 * @date: 25.10.2012 - 18:00
 */
public class Client {
	
	private static Socket socket = null;

	/**
	 * @param args
	 */
	public static void main(String args[]) throws IOException
	{
//		WLAN_Client_old client = new WLAN_Client_old();
		Connecting.connectWithTimeOut(2000);
		
		boolean isCon;
		isCon = socket.isConnected();
		
		while(isCon == true){
			System.out.println(Arrays.toString(Send_Read.read()));
			ConnectionHandling.sleep(2000);
			System.out.println("Transfer your Data");
			System.out.println(Arrays.toString(Send_Read.read()));
			
			if(socket.isClosed() == true){
				System.out.println("Your Socket is closed");
				ConnectionHandling.closeConnection();
				Connecting.connectWithTimeOut(2000);
			}
			
			System.out.println("\n Content of sendWait");
			System.out.println(Arrays.toString(Send_Read.read()));
			isCon = false;
		}
		ConnectionHandling.closeConnection();
	}

}
