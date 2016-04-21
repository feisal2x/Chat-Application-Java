import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class clientAction {
	
	/**
	 * This constructor is a TCP based file transfer to a server
	 * @param IP address , Serverport,The file you wish to transfer as a File.
	 **/
	public void clientRequest(String ip ,String port,File file) throws IOException{
	     
		try{
	       byte [ ] sentByteBuffer= new byte[32] ; // The messageSent is to be converted to bytes for output
	                                  // stream tranmission
	       byte [] recieved = new byte[32];

	     //   byte [ ] receivedByteBuffer = new byte[1000]; // message that is echoed back by the server
			int p=Integer.parseInt(port);
			byte[] name= (byte[])(file.getName()).getBytes();
	        FileInputStream x= new FileInputStream(file);
	        
	        Socket sok = new Socket(ip,p);
	        OutputStream out= sok.getOutputStream();
	        out.write(name);
	        while(x.read(sentByteBuffer)!=-1){	        
	        out.write(sentByteBuffer);
	        }
	      	x.close();
	      	InputStream in= sok.getInputStream();
	      	in.read(recieved);
	      	if(recieved.toString().contains("end"))
	      	{
	      		out.close();
	      		in.close();
	      		sok.close();
	      	}
		}catch(Exception e){
	        	
	        e.printStackTrace();
	        }
	      	

		
	}

}
