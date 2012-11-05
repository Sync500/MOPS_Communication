package WLAN;

public class CommunicationDevice {
  int                     deviceId;
  CommunicationDeviceType type;
  
  /**
   * @return the id
   */
  public int getdeviceId() {
    return deviceId;
  }
  
  /**
   * @param id
   *          the id to set
   */
  public void setdeviceId(int id) {
    this.deviceId = id;
  }
  
  /**
   * @return the type
   */
  public CommunicationDeviceType getType() {
    return type;
  }
  
  /**
   * @param type
   *          the type to set
   */
  public void setType(CommunicationDeviceType type) {
    this.type = type;
  }
}
