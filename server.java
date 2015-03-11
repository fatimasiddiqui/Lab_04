
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

  public final static int SOCKET_PORT = 13273;  // you may change this
  public final static String FILE_TO_SEND = "C:/Users/Ali/workspace/Lab_04/src/server.txt";  // you may change this

  public static void main (String [] args ) throws IOException {
	  
	  String filename1 = "server.txt";
	  int count = 0;
	  File dir = new File("C:/Users/Ali/workspace/Lab_04/src/");
	  String[] children = dir.list();
	  for (int i = 0; i < children.length; i++) {
          String filename = children[i];
          if (filename.equals(filename1) == true){
        	 count = 1; 
          }
          System.out.println(filename);
          System.out.println(count);
       }
	  if(count == 1){
		  System.out.println("200 ok!");
		  BufferedInputStream bis = null;
    FileInputStream fis = null;
    ServerSocket serversocket = null;
    
    OutputStream os = null;
    
    Socket sock = null;
    try {
      serversocket = new ServerSocket(SOCKET_PORT);
      while (true) {
        System.out.println("Waiting...");
        try {
          sock = serversocket.accept();
          System.out.println("Accepted connection : " + sock);
          // send file
          File myFile = new File (FILE_TO_SEND);
          byte [] mybytearray  = new byte [(int)myFile.length()];
          fis = new FileInputStream(myFile);
          bis = new BufferedInputStream(fis);
          bis.read(mybytearray,0,mybytearray.length);
          os = sock.getOutputStream();
          System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
          os.write(mybytearray,0,mybytearray.length);
          os.flush();
          System.out.println("Done.");
        }
        finally {
          if (bis != null) bis.close();
          if (os != null) os.close();
          if (sock!=null) sock.close();
        }
      }
    }
    finally {
      if (serversocket != null) serversocket.close();
    }
	  }
	  else{
		  System.out.println("Error 404!...File Could not be found!");
	  }
  }
}
