
 import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
 import java.net.*;

 class ChatClient extends Thread implements ActionListener { // class ChatClient begins here
	 public Socket mySocket;
	 public DataInputStream dataIn;
	 public DataOutputStream dataOut;
	 public String host;
	 public int port;
	 public String username;
	 public ChatClient(String host , int port ,String username){
		 this.host=host;
		 this.port=port;
		 this.username=username;
	 }
	
	 
	 
	 	public void run(){
	 		
	 		
	 		try { // begin try block
                 mySocket = new Socket(host, port); // server IP and port #


                   /********************* Create DataInputStream instance ********/
                dataIn = new DataInputStream ( mySocket.getInputStream() );
                   
                  /********************* Create DataOutputStream instance *******/

                dataOut = new DataOutputStream (mySocket.getOutputStream ( ) );

  
                lab6Gui.btnSend.addActionListener(this);
                lab6Gui.btnDisconnect.addActionListener(this);
                  while(true){   
                	  		
                	  		String messageReceived="";
                          
                	  		   System.out.println("\nEnter message here to be sent to server:\t");                    
                               messageReceived = dataIn.readUTF( );
                               lab6Gui.textArea.append(messageReceived);
                               lab6Gui.textArea.append("\n");
                               if(messageReceived.contains("Connection Closed"))                            	   
                                 break; 

                         /**************** Display the received message **********/

                         System.out.println("\nReceived Message from the Server: "
                         		+ "\n"+ messageReceived);
                  }
                  mySocket.close();
                        
	 		 } // end of the try block
            catch (Exception e) { // Exception catch block begins here...
               e.printStackTrace();
            } // end of the Exception catch block
	 		
	 		}
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(lab6Gui.btnSend)) {
			String messageSent = "";
			messageSent += "\n "+username+":\t";
			messageSent +=lab6Gui.getTxtMessage();
			try {
				dataOut.writeUTF ( messageSent);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			if(e.getSource().equals(lab6Gui.btnDisconnect)){
				try {
					dataOut.writeUTF ("end");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	 	

     


 } // class ChatClient ends here