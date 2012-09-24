package communication_xBee;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.wpan.RxResponseIoSample;

/**
 * @author Daniel Fay
 * @version 1.0
 *
 */

public class XBeeResponse {

	/**
	 * @param args
	 * @throws XBeeException 
	 */
	public static void main(String[] args) throws XBeeException {
		// create object for the communication_sendObject with the serial Interface and port
		XBee xbee = new XBee();         
		xbee.open("COM5", 9600);  //serial port and list on port 9600
		                        
		while (true) {
		    //here comes the response from xBee
			RxResponseIoSample ioSample = (RxResponseIoSample) xbee.getResponse();

		    System.out.println("We received data from " + ioSample.getSourceAddress());     
		                        
		    if (ioSample.containsAnalog()) {
		        System.out.println("10-bit temp reading (pin 19) is " 
		        		+ ioSample.getSamples()[0].getAnalog1());
		    }
		}

	}

}
