package de.uniol.mops.communication.xbee;

import com.rapplogic.xbee.api.IXBee;
import com.rapplogic.xbee.api.XbeeSocketCon;

public class XbeeSocket extends Xbee {
  /**
   * Initializes Xbee Communication hardware.
   * 
   * @param ip
   * @param port
   * @throws Exception
   */
  public XbeeSocket(String ip, int port) throws Exception {
    IXBee xbeeConnection = new XbeeSocketCon();
    log.debug("Starting Xbee Socket connection to " + ip + ":" + port + "...");
    xbeeConnection.open(ip, port);
    startXbee(xbeeConnection);
  }
}
