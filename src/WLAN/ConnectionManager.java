/**
 * 
 */
package WLAN;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Daniel
 * @version 1.0
 * @date: 25.10.2012 - 18:00
 */
public class ConnectionManager {

	private static Socket socket = null;
	private static ServerSocket server = null;

	public static void main(String[] args) throws IOException {
		Connecting.connectServerSocket();
		byte b[] = new byte[4];
		b[0] = 12;
		b[1] = 11;
		b[2] = 123;

		byte c[] = new byte[4];
		c[0] = 20;

		boolean isCon;
		isCon = socket.isConnected();

		while (true) {
			try {
				Send_Read.sendByteArray(b);
				ConnectionHandling.sleep(2000);

				Send_Read.sendByteArray(c);
				ConnectionHandling.sleep(2000);

				if (socket.isClosed() == true) {
					System.out.println("Your Socket is closed");
					ConnectionHandling.closeConnection();
					Connecting.connectServerSocket();
				}
			} finally {
				if (socket != null)
					try {
						socket.close();
					} catch (IOException e) {
					}
			}
			ConnectionHandling.closeConnection();
		}
	}
}
