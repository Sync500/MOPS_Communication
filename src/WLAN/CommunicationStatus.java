package WLAN;

/**
 * Java Bean for the status of the Communication
 * 
 * @author Kamran for PG-MOPS
 * 
 */
public class CommunicationStatus {
  
  public boolean getConnected() {
    // TODO
    return false;
  }
  
  /**
   * return "WLAN", "XBee" or null if not connected
   * 
   * @return
   */
  public String getConnectionType() {
    if (!this.getConnected()) return null;
    
    return "WLAN";
  }
  
}
