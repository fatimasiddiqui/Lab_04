
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class client {

  public final static int SOCKET_PORT = 13273;      // you may change this
  public final static String SERVER = "127.0.0.1";  // localhost
  public final static String
       FILE_TO_RECEIVED = "C:/Users/Ali/workspace/Lab_04/src/hello.txt";  // you may change this, I give a
                                                            // different name because i don't want to
                                                            // overwrite the one used by server...

  public final static int FILE_SIZE = 6022386; // file size temporary hard coded
                                               // should bigger than the file to be downloaded

  public static void main (String [] args ) throws IOException {
	//String filename = "server.txt";
    int b_Read;
    int curr = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    Socket socket = null;
    
    try {
      socket = new Socket(SERVER, SOCKET_PORT);
      System.out.println("Connecting...");

      // receive file
      byte [] mybytearray  = new byte [FILE_SIZE];
      InputStream is = socket.getInputStream();
      fos = new FileOutputStream(FILE_TO_RECEIVED);
      bos = new BufferedOutputStream(fos);
      b_Read = is.read(mybytearray,0,mybytearray.length);
      curr = b_Read;

      do {
         b_Read =
            is.read(mybytearray, curr, (mybytearray.length-curr));
         if(b_Read >= 0) curr += b_Read;
      } while(b_Read > -1);

      bos.write(mybytearray, 0 , curr);
      bos.flush();
      System.out.println("File " + FILE_TO_RECEIVED
          + " downloaded (" + curr + " bytes read)");
    }
    finally {
      if (fos != null) fos.close();
      if (bos != null) bos.close();
      if (socket != null) socket.close();
    }
  }

}