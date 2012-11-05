package de.uniol.mops.communication.xbee;

/**
 * Represent the possible flags in the header byte.
 * 
 * @author matthias
 * 
 */
class XbeePacketPartFlag {
  
  boolean newPacket;
  boolean continuation;
  boolean lastPacketPart;
  boolean pingRequest;
  boolean pongResponse;
  
  /**
   * Create an empty flag.
   */
  public XbeePacketPartFlag() {
    this(0);
  }
  
  /**
   * Create flag info structure from existing header.
   * 
   * @param flag
   */
  public XbeePacketPartFlag(int flag) {
    parseData(flag);
  }
  
  /**
   * parse existing header.
   * 
   * @param flag
   */
  public void parseData(int flag) {
    setNewPacket((flag & 1) > 0);
    setContinuation((flag & 2) > 0);
    setLastPacketPart((flag & 4) > 0);
    setPingRequest((flag & 8) > 0);
    setPongResponse((flag & 16) > 0);
  }
  
  /**
   * Construct a header byte from set flag information.
   * 
   * @return header byte
   */
  public int getData() {
    int flag = 0;
    if (isNewPacket()) flag |= 1;
    if (isContinuation()) flag |= 2;
    if (isLastPacketPart()) flag |= 4;
    if (isPingRequest()) flag |= 8;
    if (isPongResponse()) flag |= 16;
    
    return flag;
  }
  
  /**
   * @return the newPacket
   */
  public boolean isNewPacket() {
    return newPacket;
  }
  
  /**
   * @param newPacket
   *          the newPacket to set
   */
  public void setNewPacket(boolean newPacket) {
    this.newPacket = newPacket;
  }
  
  /**
   * @return the continuation
   */
  public boolean isContinuation() {
    return continuation;
  }
  
  /**
   * @param continuation
   *          the continuation to set
   */
  public void setContinuation(boolean continuation) {
    this.continuation = continuation;
  }
  
  /**
   * @return the lastPacketPart
   */
  public boolean isLastPacketPart() {
    return lastPacketPart;
  }
  
  /**
   * @param lastPacketPart
   *          the lastPacketPart to set
   */
  public void setLastPacketPart(boolean lastPacketPart) {
    this.lastPacketPart = lastPacketPart;
  }
  
  /**
   * @return the pingRequest
   */
  public boolean isPingRequest() {
    return pingRequest;
  }
  
  /**
   * @param pingRequest
   *          the pingRequest to set
   */
  public void setPingRequest(boolean pingRequest) {
    this.pingRequest = pingRequest;
  }
  
  /**
   * @return the pongResponse
   */
  public boolean isPongResponse() {
    return pongResponse;
  }
  
  /**
   * @param pongResponse
   *          the pongResponse to set
   */
  public void setPongResponse(boolean pongResponse) {
    this.pongResponse = pongResponse;
  }
  
  /**
   * basic sanity checks on packet flags.
   * 
   * @return are flags sane?
   */
  public boolean isValid() {
    if (isNewPacket() && isContinuation()) return false;
    
    if (!isNewPacket() && !isContinuation() && !isPingRequest() && !isPongResponse()) return false;
    
    if (isPingRequest() && isPongResponse()) return false;
    
    return true;
  }
  
  @Override
  public String toString() {
    return "---" + (isPongResponse() ? ">" : "-") + (isPingRequest() ? "<" : "-") + (isLastPacketPart() ? "L" : "-")
        + (isContinuation() ? "C" : "-") + (isNewPacket() ? "S" : "-");
  }
}