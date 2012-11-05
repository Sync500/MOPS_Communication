/**
 * 
 */
package WLAN;

import java.io.IOException;

/**
 * @author matthias
 * 
 */
public interface MessageListener {
  /**
   * Called whenever a new message is received.
   * 
   * @param message
   *          message payload corresponding to current layer
   */
  public void messageReceived(byte[] message);
}
