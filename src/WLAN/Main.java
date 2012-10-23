/**
 * 
 */
package WLAN;

/**
 * @author Syncmaster
 *
 */
public class Main {
	  
	  /**
	   * @param args
	   */
	  public static void main(String[] args) {
	    
	    WLAN_Client client = new WLAN_Client();
	    WLAN_Server server = new WLAN_Server();
	    
	    server.connectServerSocket();
	    client.connectWithTimeOut(2000);
	    byte b[] = new byte[4];
	    b[0] = 12;
	    b[1] = 11;
	    b[2] = 123;    
	  }
	  
	}