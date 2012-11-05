package de.uniol.mops.communication.xbee;

import com.rapplogic.xbee.api.IXBee;
import com.rapplogic.xbee.api.XbeeSocketCon;

public class XbeeSerial extends Xbee {
  /**
   * @param port
   * @param baudrate
   * @param dummy
   * @throws Exception
   */
  public XbeeSerial(String port, int baudrate) throws Exception {
    IXBee xbeeConnection = new XbeeSocketCon();
    log.debug("Starting Xbee Serial connection to " + port + "@" + baudrate + "...");
    xbeeConnection.open(port, baudrate);
    startXbee(xbeeConnection);
  }
}
