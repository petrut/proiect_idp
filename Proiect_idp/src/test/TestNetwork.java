package test;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import networking.INetwork;
import networking.Network;
import networking.ProcessMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import gui.GuiAPI;
import common.IMediator;
import common.InfoUser;
import common.Mediator;
import junit.framework.TestCase;


public class TestNetwork extends TestCase {

	public TestNetwork(String name) {
		super(name);
	}
		
	static Logger logger = Logger.getLogger(TestNetwork.class);
	
	InfoUser iu = new InfoUser("radu");
	GuiAPI guiAPI = new GuiAPI(iu);
	INetwork network = new Network(new ProcessMessage());
	IMediator med = null;// = new Mediator(guiAPI, iu, network);
	
	InfoUser iu2 = new InfoUser("gigi");
	GuiAPI guiAPI2 = new GuiAPI(iu);
	INetwork network2 = new Network(new ProcessMessage());
	IMediator med2 = null;// = new Mediator(guiAPI, iu, network);
	
	public void testDownload() throws IOException, InterruptedException{
		
		String log_path = "Users_info/radu/log4j.properties";		
		PropertyConfigurator.configure(log_path);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
					try {
						med2 = new Mediator(guiAPI2, iu2, network2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					guiAPI2.setUp(med2);
					med2.setUp();
					med2.startServer();					
					
			}
		});
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
						
					try {
						med = new Mediator(guiAPI, iu, network);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
										
					guiAPI.setUp(med);
					med.setUp();
					med.startServer();									
					
					guiAPI.current_user = "gigi";
					guiAPI.current_file = "gigi_fis1.txt";
					guiAPI.start_download("gigi_fis1.txt");
					
			}
		});
		
		Thread.sleep(2000);
		
		String line;
		FileInputStream fin =  new FileInputStream("Users_info/gigi/gigi_files/gigi_fis1.txt");
		BufferedReader myInput = new BufferedReader(new InputStreamReader(fin));
		StringBuilder sb = new StringBuilder();
		while ((line = myInput.readLine()) != null) {  
			sb.append(line);
		}
		myInput.close();
		
		String line2;
		FileInputStream fin2 =  new FileInputStream("gigi_fis1.txt_received_radu");
		BufferedReader myInput2 = new BufferedReader(new InputStreamReader(fin2));
		StringBuilder sb2 = new StringBuilder();
		while ((line2 = myInput2.readLine()) != null) {  
			sb2.append(line2);
		}
		myInput2.close();
		
		System.out.println("\n\n> sb length = " + sb.length());
		System.out.println("\n\n> sb2 length = " + sb2.length());
		
		
		String str1 = new String(sb.toString().getBytes("UTF-8"), "UTF-8");
		String str2 = new String(sb2.toString().getBytes("UTF-8"), "UTF-8");		
		
		//assertEquals("testInfoUser", sb.length(), sb2.length());
		assertEquals("testInfoUser", str1, str2);
	}
}
