/**
 * 
 */
package WLAN;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Syncmaster_extreme
 *
 */
public class CommunicationImplTest {

	/**
	 * @throws java.lang.Exception
	 */
	
//	 @Override
//	  public CommunicationDevice addSocketClient(String ip, int port) {
//	    try {
//	      new Client();
//	      WlanHardwareImpl impl = new WlanHardwareImpl();
//	      CommunicationDevice device = connectionManager.addDevice(impl);
//	      device.setType(CommunicationDeviceType.WlanSocket);
//	      return device;
//	    } catch (IOException ioe) {
//	      ioe.printStackTrace();
//	    }
//	    return null;
//	  }
//	  
//	  @Override
//	  public CommunicationDevice addSocketServer(String ip, int port) {
//	    WlanManager wlan = new WlanManager(ip,port);
//	    WlanHardwareImpl impl = new WlanHardwareImpl();
//	    CommunicationDevice device = connectionManager.addDevice(impl);
//	    device.setType(CommunicationDeviceType.WlanSocket);
//	    return device;
//
//	  }
	  
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link WLAN.CommunicationImpl#addSocketClient(java.lang.String, int)}.
	 */
	@Test
	public void testAddSocketClient() {
			try {
		      new Client();
		    } catch (IOException ioe) {
		      ioe.printStackTrace();
		    }
	}

	/**
	 * Test method for {@link WLAN.CommunicationImpl#addSocketServer(java.lang.String, int)}.
	 */
	@Test
	public void testAddSocketServer() {
		 WlanManager wlan = new WlanManager("localhost",6665);
	}

}
