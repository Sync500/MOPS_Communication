/**
 * 
 */
package WLAN;

import java.io.IOException;
import java.net.Socket;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.log4j.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel
 * @version 1.0
 * @date: 04.11.2012
 */

public class Client extends Thread {
	/**
	 * initialization of the member var.
	 * 
	 */

	private static Socket socket;
//	private static final org.apache.log4j.Logger log =
//      LoggerFactory.getLogger(Client.class);
  	private static final Logger log = Logger.getLogger(Connecting.class
			.getName());

	/**
	 * Constructor generate ClientSocket
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred
	 */
	Client() throws IOException {
		String ip = Connecting.getIP();
		int port = Connecting.getPort();
		socket = Connecting.connectSocket(ip,port);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
  public void run() {	
		System.out.println("CLient is on");
	} // run
}
