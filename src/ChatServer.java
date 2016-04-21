
 import java.io.*;
 import java.net.*;
import java.util.LinkedList;

  public class ChatServer extends Thread { //Class ChatServer begins here
	
   private int port;
   public static LinkedList<Socket> p;
   ChatServerThread th;

   				public ChatServer(int p ){
   					this.port=p;
   					
   				}
              /**************Begin the method runServer ( ) here.............*********/
       					public void run(){
       					    try {
								runServer(40050);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
       					}
       					
                       public void runServer (int c) throws IOException { //begin runServer

                          /********* Create ServerSocket Instance ****************/
                        
                        ServerSocket myServerSocket = new ServerSocket (40050);
 
                         System.out.println("\n Server is running and waiting for client....\n");

                         /********* run a loop to execute start method ... *******/
                         ThreadGroup g = new ThreadGroup("clients");
                        
                         String [] clients = new String[]{"client1","client2","client3","client4","client5","client6",
                        		 "client7","client8","client9","client10"};
                         int i=0;
                          p = new LinkedList<Socket>();
                         while ( i<clients.length ) { // run an infinite loop
                           
                                  /*********** Get the Socket Connection... *******/


                                  /**** This statement begins the communication process ******/

                                     Socket theSocket = myServerSocket.accept( );
                                    
                                 /****** Now execute the 
                                              start method that will begin 
                                                  run method in the thread ******/


                                     
                                   /*** Now execute the 
                                     start method that will begin 
                                         run method in the thread ******/


                        p.add(theSocket);
                        new ChatServerThread ( g,theSocket,clients[i], theSocket.getPort()).start();
                        i++;
                          System.out.println(g.activeCount());
                         
                          
                       
                          
                         

                      
                         } // end of infinite loop

                       } // end of the method runServer
                       
     public void messageSent(String s) {
    	 try {
			th.sentmessage(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
     }                 


  } // class ChatServer ends here