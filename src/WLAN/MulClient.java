package WLAN;

import java.net.*;
import java.io.*;
class MulClient
{
  public static void main( String[] args )
  {
    Socket server = null;
    try
    {
      server = new Socket( "localhost", 6665 );
      InputStream in = server.getInputStream();
      OutputStream out = server.getOutputStream();
      out.write( 4 );
      out.write( 9 );
      int result = in.read();
      System.out.println( result );
    }
    catch ( UnknownHostException e ) {
      e.printStackTrace();
    }
    catch ( IOException e ) {
      e.printStackTrace();
    }
    finally
    {
      if ( server != null )
        try { server.close(); } catch ( IOException e ) { e.printStackTrace(); }
    }
  }
}