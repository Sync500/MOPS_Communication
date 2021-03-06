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
	
	static XBee xbee;
	String comInt; // serial interface
	int port; // and port number
	
	/**
	 * @param args
	 * @throws XBeeException 
	 */
	
	public void response(String comInt, int port) throws XBeeException{
	// create object for the communication with serial Interface and port
			xbee = new XBee(); 
			xbee.open(comInt,port);
			                        
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

	public static void main(String[] args) {
			//unsused
	}

}
