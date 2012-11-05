/**
 * 
 */
package WLAN;

/**
 * @author matthias; Kamran for PG-MOPS
 * 
 */
public class CommunicationInput {
  
  private Object  objectData;
  
  private boolean isObject;
  
  private byte[]  byteData;
  
  /**
   * @return the byteData
   */
  public byte[] getByteData() {
    return byteData;
  }
  
  /**
   * @param byteData
   *          the byteData to set
   */
  public void setByteData(byte[] byteData) {
    this.byteData = byteData;
  }
  
  /**
   * @return the objectData
   */
  public Object getObjectData() {
    return objectData;
  }
  
  /**
   * @param objectData
   *          the objectData to set
   */
  public void setObjectData(Object objectData) {
    this.objectData = objectData;
  }
  
  /**
   * Is data an object or a byte array?
   * 
   * @return isObject
   */
  public boolean isObject() {
    return isObject;
  }
  
  /**
   * @param isObject
   *          the isObject to set
   */
  public void setObject(boolean isObject) {
    this.isObject = isObject;
  }
}
