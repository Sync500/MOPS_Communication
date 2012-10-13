package WLAN;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class MulServer
{
  private final ServerSocket server;
  public MulServer( int port ) throws IOException
  {
    server = new ServerSocket( port );
  }
  private void startServing()
  {
    while ( true )
    {
      Socket client = null;
      try
      {
        client = server.accept();
        handleConnection ( client );
      }
      catch ( IOException e ) {
        e.printStackTrace();
      }
      finally {
        if ( client != null )
          try { client.close(); } catch ( IOException e ) { e.printStackTrace(); }
      }
    }
  }
  private void handleConnection( Socket client ) throws IOException
  {
    InputStream  in  = client.getInputStream();
    OutputStream out = client.getOutputStream();
    int factor1 = in.read();
    int factor2 = in.read();
    out.write( factor1 * factor2 );
  }
  public static void main( String[] args ) throws IOException
  {
    MulServer server = new MulServer(6665);
    server.startServing();
  }
}
