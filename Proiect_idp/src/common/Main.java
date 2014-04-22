package common;

import gui.GuiAPI;
import test.MockupMediator;
import java.awt.EventQueue;

import networking.INetwork;
import networking.Network;
import networking.ProcessMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


// clasa temporara pentru testarea aplicatiei (partea grafica)

public class Main {
	
	static String current_user = "me";	
	static Logger logger = Logger.getLogger(Main.class);

	public static void main(String []args) throws InterruptedException{
		
		String log_path;
		
		if(args.length > 0){
			if(!args[0].equals("${args}"))
				current_user = args[0];
		}
		
		System.out.println("> current user = " + current_user);
		
		log_path = "Users_info/" + current_user + "/log4j.properties";		
		PropertyConfigurator.configure(log_path);
				
		logger.debug("Test debug message");
		logger.info("Test info message");
		logger.warn("Test warn message");
		logger.error("Test error message");
		logger.fatal("Test fatal message");

		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					InfoUser iu = new InfoUser(current_user);
					GuiAPI guiAPI = new GuiAPI(iu);
					INetwork network = new Network(new ProcessMessage());
					IMediator med = new Mediator(guiAPI, iu,network);
					guiAPI.setUp(med);
					med.setUp();
					med.startServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}
}
