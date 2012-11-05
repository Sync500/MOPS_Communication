package WLAN;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads from given CommunicationHardware and calls a message listener for handling.
 * 
 * @author matthias
 * 
 */
public class ConnectionManagerReader implements Runnable {
  
  CommunicationHardware hw;
  MessageListener       listener;
  private static Logger log = LoggerFactory.getLogger(ConnectionManagerReader.class);
  
  ConnectionManagerReader(CommunicationHardware hardware, MessageListener listener) {
    this.hw = hardware;
    this.listener = listener;
  }
  
  @Override
  public void run() {
    try {
      while (true) {
        try {
          byte[] msg = hw.read();
          listener.messageReceived(msg);
        } catch (IOException e) {
          log.error("Failure while reading from communication hardware: " + e.getLocalizedMessage());
        }
      }
    } catch (InterruptedException e) {
      /* exit on interruption */
    }
    
  }
  
}
