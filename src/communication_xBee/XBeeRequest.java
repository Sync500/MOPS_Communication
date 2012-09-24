/**
 * 
 */
package communication_xBee;

import com.rapplogic.xbee.XBeePin;
import com.rapplogic.xbee.api.RemoteAtRequest;
import com.rapplogic.xbee.api.RemoteAtResponse;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;

/**
 * @author Daniel Fay
 * @version 1.0
 *
 */
public class XBeeRequest {

	/**
	 * @param args
	 * @throws XBeeException 
	 */
	
	public static void main(String[] args) throws XBeeException {
		// create object for the communication_sendObject with the serial Interface and port
		XBee xbee = new XBee(); 
		xbee.open("COM5", 9600); //serial port and list on port 9600
	
		// this is the Serial High (SH) + Serial Low (SL) of the remote XBee                    
		XBeeAddress64 addr64 = new XBeeAddress64(0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 0, 1);
	
		// Turn on DIO0 (Pin 20)
		RemoteAtRequest request = new RemoteAtRequest(addr64, "D0", 
				new int[XBeePin.Capability.DIGITAL_OUTPUT_HIGH.getValue()] );
		
		// send  request to xBee
		xbee.sendAsynchronous(request);
		// here comes the response
		RemoteAtResponse response = (RemoteAtResponse) xbee.getResponse();
		
		// check if response is ok
		if (response.isOk()) {
		    System.out.println("Successfully turned on DIO0");
		} else {
		    System.out.println("Attempt to turn on DIO0 failed.  Status: " + response.getStatus());
		}
	
		// shutdown the serial port and associated threads
		xbee.close();
	
	}
}
