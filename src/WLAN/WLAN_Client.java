package WLAN;

import java.io.*;
import java.net.*;

/**
 * @author Daniel Fay
 * @version 1.0
 * @date: 23.10.2012 - 19:00
 */
public class WLAN_Client {
  private static Socket           socket = null;
  private static DataInputStream  in;
  private static DataOutputStream  out;
  // static Logger log = Logger.getLogger(Launcher.class.getName());
  final static int                port   = 6665;
  final static String             host   = "192.168.1.7";
  
  WLAN_Client() { }
  
  /**
   * @description: connecting to Server without timeout
   * @throws UnknownHostException
   *           if the IP/host address of the host could
   *           not be determined
   * @throws IOException
   *           if an I/O error occurs when creating the socket
   */
  public static void connectSocket() throws UnknownHostException {
    try {
      socket = new Socket(host, port);
      System.out.println("Connect to Server");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      System.out.println("Socket dont create: " + e.getMessage() + "\n");
    }
  }
  
  /**
   * @description: connect to Server with timeout
   * @param timeout
   *          set timeout if Server don't response
   * @throws IOException
   *           if Server don't response then connect again
   */
  public static void connectWithTimeOut(int timeout) {
    System.out.println("Timeout is: " + timeout);
    SocketAddress sockaddr = new InetSocketAddress(host, port);
    socket = new Socket();
    try {
      socket.connect(sockaddr, timeout);
      createStreams(socket);
    } catch (IOException e) {
      System.out.println("Server dont response");
      connectWithTimeOut(timeout);
    }
  }
  
  /**
   * @description: create necessary streams to transfer Data
   * @param socket
   * @throws IOException
   *           if an I/O error occurs when creating the output stream or
   *           if the socket is not connected
   */
  public static void createStreams(Socket socket) {
    try {
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {
      System.out.println("client> Streams dont created: " + e.getMessage() + "\n");
    }
  }
  
  /**
   * @description: send a blob of data. Sending may fail without notice or with exception
   * @param data
   *          data to send
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
   * @param timeout
   *          for transfer operation
   * @return false
   *         if timeout overrun
   * @return true
   *         if send transfer is complete
   */
  public static boolean sendContent(byte[] data, int timeout) {
    System.out.println("Waiting: " + timeout);
    if (data == null) {
      return false;
    } else {
      send(data, 0, data.length);
      System.out.println("Sending Data - complete");
      return true;
    }
  }
  
  /**
   * @description: blocking read of data. Returns data in blobs as it was sent!
   *               Use of Exception is implementation defined.
   * @return ByteArray or null if content is empty
   * @throws IOException
   *           the stream has been closed and the contained input stream
   *           does not support reading after close, or another I/O error occurs
   */
  public static byte[] read() throws IOException {
    try {
      int len = 0;
      len = in.readInt();
      System.out.println("ByteArray lenght: " + len);
      byte[] ByteArray = new byte[len];
      if (len > 0) {
        // read the ByteArray from InputStream
        in.readFully(ByteArray);
      }
      return ByteArray;
    } catch (IOException e) {
      e.getMessage();
    }
    return null;
  }
  
  /**
   * @description: check the state of the socket connection
   *               show state of the connection
   * @return boolean
   */
  public static boolean connectionState(Socket socket) {
    boolean isOn = true;
    // istOn = InetAddress.getByName( host ).isReachable(timeout);
    isOn = socket.isConnected();
    
    if (isOn == false) {
      System.out.println("Connection is: " + socket.isConnected());
      return false;
    }
    System.out.println("Connection is: " + socket.isConnected());
    return true;
  }
}