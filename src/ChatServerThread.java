
 import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.*;
 import java.net.*;
 import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

   public class ChatServerThread extends Thread implements ActionListener {  // begin the class ChatServerThread

        private Socket mySocketThread;
        private int socketPort;
        static String messageSent;
        DataInputStream dataInput;
        DataOutputStream dataOutput;
        public static int x;
        Thread name;
        public ThreadGroup myThreadGroup;
         Thread myThread;
         static boolean state=false;
         private static Lock lock=new ReentrantLock();
        
          /**************** ChatServerThread overloaded constructor begins below ******/

           ChatServerThread (ThreadGroup g ,Socket socketThread,String name,int port ) { // constructor begins here
        	   		super(g,name);
        		this.myThreadGroup=g;
                  this.mySocketThread = socketThread;
                  setSocketPort(port);
           } // end of the overloaded constructor 
          

             /******** The thread overloaded method run ( ) begins here **********/

             public void run ( ) { // the method start ( ) begins the execution or run
            	myThread=this; 
            	
               try { // the try block begins here
                     
                           String messageReceived = "";

                 /**** Create DataInputStream and DataOutput Stream from mySocketThread ***********/


        BufferedReader keyInput = new BufferedReader ( new InputStreamReader ( System.in ) );

         dataInput = new DataInputStream (mySocketThread.getInputStream() );

        dataOutput = new DataOutputStream (mySocketThread.getOutputStream ( ) );
               
        lab6Gui.btnSend.addActionListener(this);
        
        
                try{ // Inner try block begins
                	
                    while ( ( messageReceived = dataInput.readUTF () ) != null ) { // the while loop begins here
                    		
	                    	ChatServerThread.state=false;
	                    	System.out.println("ACTIVE GROUP COUNT : "+myThreadGroup.activeCount());
	                      System.out.println("Incoming message from the Client:\t" + messageReceived );
	                      	lab6Gui.textArea.append("\nIncomming message \n from client:  "+messageReceived+"\nEnter message to send:\n");
	                      	if(messageReceived.contains("end")){
	                      		dataOutput.writeUTF("Connection Closed");
	                      		break;
	                      	}                     
	                      //	dataOutput.writeUTF(messageReceived);               	
	                     // if(myThreadGroup.activeCount()>1)
	                      	System.out.println(myThread+": "+Thread.currentThread());
	                      	synchronized(this){
	                      		lock.lock();
	                    	Action();
	                      	}
                	
                    } // while loop ends here
                    mySocketThread.close();
                    
                 } // inner try block ends

                          catch (SocketException se) { // begin catch block inner
                             dataInput.close();
                             dataOutput.close ( );
                             mySocketThread.close(); // the client's messaged ended in loop and now
                              // close this client's socket.
                          } // ends catch block         
                   } // the try block ends here
                      catch (IOException ioe) { // catch block begins here

                             ioe.printStackTrace( );

                      } // catch block ends here
                      
          
              } // the method run ends here. The method start( ) will begin run ( ) method
             public void sentmessage(String x) throws IOException {
            	dataOutput.writeUTF(x);
             }
          
         
            @SuppressWarnings("static-access")
			public  void Action() throws IOException{
           	 System.out.println("\nEnter message to send out:\t");
           	 Thread[] l = new Thread[myThreadGroup.activeCount()];
           	 myThreadGroup.enumerate(l);
    		 for(int j=0;j<myThreadGroup.activeCount();j++){
    			
    			if( (ChatServer.p.get(j).getPort()) == getSocketPort()){
    				x=j;
    				 dataOutput = new DataOutputStream (ChatServer.p.get(j).getOutputStream ( ) );
    				 System.out.println(ChatServer.p.get(j).getPort() +" hold lock :"+ getSocketPort());
    				ChatServerThread.state=true;
    			}
    			
    		 }
    		 	
	    		 while(true){
	    			 System.out.print("");
	     			if(ChatServerThread.state==false){
	     				//lab6Gui.btnSend.notify();
	     				System.out.println("detected");
	     				lock.unlock();
	     				break;
	     			}
	     		}   
	    	 System.out.println("letting go");
            }
          
             public  void actionPerformed(ActionEvent e) {
				try {
					
								if(ChatServerThread.state == true){
									dataOutput = new DataOutputStream (ChatServer.p.get(x).getOutputStream ( ) );
									System.out.println(myThread.getName()+" sending");
								dataOutput.writeUTF(lab6Gui.getTxtMessage());
								ChatServerThread.state=false;
								}else{
									System.out.println(myThread.getName() + "waiting");
									
								}
								
								//this.interrupt();						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}


			public int getSocketPort() {
				return socketPort;
			}


			public void setSocketPort(int socketPort) {
				this.socketPort = socketPort;
			}
             

   } // end of the class ChatServerThread