/**
 * @author feisalsalim
 * */
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class lab6Gui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JTextField txtIpAddress;
	public  static JTextField txtPort;
	private static JTextField txtMessage;
	public JRadioButton rdbtnFileTransfer;
	public JRadioButton rdbtnInitiateServer;
	public JButton btntransfer;
	public static JTextArea textArea;
	
	public JRadioButton rdbtnInitiateClient;
	private JFileChooser file= new JFileChooser();
	public static JButton btnSend ;
	public static JButton btnDisconnect ;
	public static JButton btnConnect ;
	ChatServer chat;
	
	public File temp;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lab6Gui frame = new lab6Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public lab6Gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFeisalSalim = new JLabel("Feisal Salim ");
		lblFeisalSalim.setBounds(6, 6, 98, 23);
		contentPane.add(lblFeisalSalim);
		
		JLabel lblSid = new JLabel("SID:824317549");
		lblSid.setBounds(6, 28, 104, 16);
		contentPane.add(lblSid);
		
		txtIpAddress = new JTextField();
		txtIpAddress.setText("IP Address");
		txtIpAddress.setBounds(6, 79, 134, 28);
		contentPane.add(txtIpAddress);
		txtIpAddress.setColumns(10);
		
		txtPort = new JTextField();
		txtPort.setText("Port #");
		txtPort.setBounds(6, 111, 134, 28);
		contentPane.add(txtPort);
		txtPort.setColumns(10);
		
		txtMessage = new JTextField();
		txtMessage.setText("Message");
		txtMessage.setBounds(6, 148, 134, 75);
		contentPane.add(txtMessage);
		txtMessage.setColumns(10);
		
		
		btnSend = new JButton("Send");
		btnSend.setBounds(6, 235, 117, 29);
		contentPane.add(btnSend);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConnect.setBounds(135, 235, 117, 29);
		contentPane.add(btnConnect);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(271, 235, 117, 29);
		contentPane.add(btnDisconnect);
		

		rdbtnInitiateClient = new JRadioButton("Initiate Client");
		rdbtnInitiateClient.setBounds(164, 38, 141, 23);
		contentPane.add(rdbtnInitiateClient);
		
		 rdbtnInitiateServer = new JRadioButton("Initiate Server");
		rdbtnInitiateServer.setBounds(164, 60, 141, 23);
		contentPane.add(rdbtnInitiateServer);
		
		rdbtnFileTransfer = new JRadioButton("File Transfer");
		rdbtnFileTransfer.setBounds(164, 81, 141, 23);
		contentPane.add(rdbtnFileTransfer);
		
		final ButtonGroup g = new ButtonGroup();
		
		g.add(rdbtnInitiateServer);
		g.add(rdbtnInitiateClient);
	    textArea = new JTextArea();
	    
	    
	    textArea.setWrapStyleWord(true);
		textArea.setColumns(4);
		textArea.setRows(3);
		textArea.setEditable(false);
		textArea.setBorder(null);
		//textArea.setBounds(163, 117, 252, 106);
		textArea.setCaretPosition(0);
		
		
		
	    JScrollPane scrollV = new JScrollPane ();
	    scrollV.setViewportView(textArea);
	    
		scrollV.setBounds(163, 117, 252, 106);
		
	    	
	   
	    
		//contentPane.add(textArea);
		contentPane.add(scrollV);
		
		
		btntransfer= new JButton("Transfer File");
		btntransfer.setEnabled(false);
		btntransfer.setBounds(283, 80, 117, 29);
		contentPane.add(btntransfer);
		
		
		rdbtnFileTransfer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rdbtnFileTransfer.isSelected())
					btntransfer.setEnabled(true);
					else 
						btntransfer.setEnabled(false);
					
			}
		});
		btntransfer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				file.showOpenDialog(btnSend);
				temp = file.getSelectedFile();
			}
			
		});
		btnConnect.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(rdbtnInitiateClient.isSelected()&&(rdbtnFileTransfer.isSelected()==false)){
					System.out.println("client is selected");
					String h = txtIpAddress.getText().toString();
					int p=Integer.parseInt(txtPort.getText());
					ChatClient client = new ChatClient(h,p,txtMessage.getText());
					client.start(); 
					
				}
				if(rdbtnInitiateServer.isSelected()&&(rdbtnFileTransfer.isSelected()==false)){
					
					//int p=Integer.parseInt(txtPort.getText());
					int p=Integer.parseInt(txtPort.getText());
				    chat = new ChatServer(p);
				    chat.start();
					validate();
					textArea.setText("Server Initialized");
				
				}
				if(rdbtnFileTransfer.isSelected()&&rdbtnInitiateServer.isSelected()){
					
					System.out.println("filetransfer is selected");
					
					
				    new ServerTCP(Integer.parseInt(txtPort.getText())).start();
					validate();
					
				}
			}
			
		});
		btnSend.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					if(rdbtnFileTransfer.isSelected()&&rdbtnInitiateClient.isSelected() ){
						
						try{
							
						System.out.println("filetransfer is selected");
						
						
						clientAction c = new clientAction();
						c.clientRequest(txtIpAddress.getText(),txtPort.getText() , temp);
						validate();
						}catch(IOException g){
							g.printStackTrace();
							
						}
						
					}
				
			
				
			}
			  	
			
			
		});
		
		
		
	}

	public static String getTxtMessage() {
		String x=txtMessage.getText();
		return x;
	}
	public static int getPortNum(){
		return Integer.parseInt(txtPort.getText());
	}

	public static JButton getSendStatus(){
		return btnSend;
	}
	
	
}
