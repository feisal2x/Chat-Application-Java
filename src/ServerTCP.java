/**
 * @author feisalsalim
 * 
 * */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP extends Thread {
	 private static String modFile;
	 private static final int BUFFERSIZE = 32; // Size of the receive buffer
	 private static FileOutputStream stream;
	 private static File f;
	 private static int port;
	 public ServerTCP(int x){
		 this.port=x;
		 
	 }
     public void run()  {
     // main application begins here
        
         try{
          int receivedMessageSize=0; // size of the received message from Client
          byte [ ] byteBuffer = new byte [BUFFERSIZE]; // create byte buffer
          byte [] msg = new byte[20];

        
      

             ServerSocket serverSocket = new ServerSocket(port);

             for ( ; ; ) { 
                Socket clientSock  = serverSocket.accept();  // Get the client connection
        
                  // Now that we have the Client connection accepted, 
                  // Display client socket information

                  System.out.println("\n....Handling Client at:....");

           System.out.println( clientSock.getInetAddress().getHostAddress() + "\n  on port number:\t" + clientSock.getPort() );
         
            // Create an instance of InputStream from instance of Client Socket

                 InputStream in = clientSock.getInputStream();

           // Create an instance of OutputStream from instance of Client Socket

                 OutputStream out = clientSock.getOutputStream();


           // Keep on receiving the data from the Client until end of 
           // the data (refer by -1) 

                 try{
                 while (( receivedMessageSize = in.read(byteBuffer)) != -1){
                         out.write(byteBuffer, 0, receivedMessageSize);
                         String x=new String(byteBuffer).trim();
                        
	                         if(x.endsWith(".txt")){                                       
	                        	 	                       	 	     	 	                        	 	
	                        	 	stream = new FileOutputStream(x,false);
	                          }
	                         if(x.isEmpty()==true){
	                        	 stream.close();
	                        	 
	                         }else if(!x.endsWith(".txt")){                                       
	                     	 	stream.write(byteBuffer);
	                         } 
                         }
                 
                 // as the while loop ends (terminated by -1)
                 System.out.println("code reaches here");
                 // close this client's socket
                  msg="end".getBytes();
                  out.write(msg);
                  in.close();
                  out.close();
                  clientSock.close(); // end of this client's echoed message
                 }catch(Exception e){
                	 e.printStackTrace();
                	 
                 } 
                         
                         // FileOutputStream file = new FileOutputStream();
                 
               
                 

             } // end of the for loop

                /******* this part can only be reached with Ctrl C ******/
         }
         catch(IOException e){
        	 e.printStackTrace();
         }
     } // main application ends here

}
