/**
 * 
 */
package WLAN;

import java.io.*;
import java.net.*;

/**
 * @author Daniel Fay
 * @version 1.0
 * @date: 23.10.2012 - 18:00
 */
public class WLAN_Server {
  
  private static ServerSocket     server = null;
  private static Socket           socket = null;
  private static DataOutputStream out;
  
  final static int                port   = 6665;
  final static String             host   = "192.168.1.5";
  
  WLAN_Server() {
  }
  
  /**
   * @description: create ServerSocket and wait for client
   * @throws IOException
   *           - if an I/O error occurs when opening the socket
   * 
   */
  public static void connectServerSocket() {
    try {
      server = new ServerSocket(port); // throw IOException
      WaitForClient();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      System.out.println("ServerSocket dont create: " + e.getMessage() + "\n");
    }
  }
  
  /**
   * @description: Server wait for response from client
   * @throws IOException
   *           - if an I/O error occurs when waiting for a connection
   *           if an I/O error occurs when creating the output stream or if the socket is not connected
   * 
   */
  public static void WaitForClient() {
    System.out.println("Waiting for Client");
    try {
      socket = server.accept(); // throw IOException
      createStreams(socket); // throw IOException
    } catch (IOException e) {
      // TODO Auto-generated catch block
      System.out.println("Client dont response: " + e.getMessage());
    }
    System.out.println("Socket received from " + socket.getInetAddress().getHostName());
  }
  
  /**
   * @description: create necessary streams to transfer Data
   * @param socket
   * @throws IOException
   *           -if an I/O error occurs when creating the output stream or
   *           if the socket is not connected
   */
  public static void createStreams(Socket socket) {
    try {
      out = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      System.out.println("server> Streams dont created: " + e.getMessage() + "\n");
    }
  }
  
  /**
   * @description: send a blob of data. Sending may fail without notice or with exception
   * @param data
   *          - data to send
   * @throws IOException
   *           on exception or failure in underlying protocol
   */
  public static void sendByteArray(byte[] data) {
    send(data, 0, data.length);
  }
  
  /**
   * @description: send ByteArray to receiver
   *               check length of the array and her IndexBound
   * @param ByteArray
   * @param start
   *          point of array
   * @param len
   *          of the passed ByteArray
   * @throws IOException
   *           or IllegalArgumentException or IndexOutOfBoundsException
   */
  public static void send(byte[] ByteArray, int start, int len) {
    if (len < 0) {
      throw new IllegalArgumentException("The length of the Array cannot be negative");
    }
    if (start < 0 || start >= ByteArray.length) {
      throw new IndexOutOfBoundsException("Your value is: Out of bounds: " + start);
    }
    
    try {
      // send ByteArray Length and content to receiver
      out.writeInt(len);
      // System.out.println("Length is: " + len);
      if (len > 0) {
        out.write(ByteArray, start, len);
        // System.out.println("Sending ByteArray - complete");
      }
    } catch (IOException ioe) {
      ioe.getMessage();
    }
    
  }
  
  /**
   * @description: send a blob of data and block until success or failure is signaled
   * @param data
   *          data to send
   */
  public static void sendWait(byte[] data, int timeout) {
    System.out.println("Waiting: " + timeout);
    send(data, 0, data.length);
    System.out.println("Sending Data - complete");
  }
}