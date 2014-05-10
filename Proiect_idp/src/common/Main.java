package common;

import gui.GuiAPI;

import java.awt.EventQueue;
import java.util.ArrayList;

import networking.INetwork;
import networking.Network;
import networking.ProcessMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class Main {
	
	static String current_user = "me";
	static Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String []args) throws InterruptedException{
		
		String log_path;
		
		log_path = "Users_info/" + current_user + "/log4j.properties";		
		PropertyConfigurator.configure(log_path);
		
		logger.warn("> args = " + args[0]);
		
		if(args.length > 0){
			if(!args[0].equals("${args}") && !args[0].equals("${arg0}"))
				current_user = args[0];
		}
		
		logger.warn("> current user = " + current_user);
				
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
					IMediator med = new Mediator(guiAPI, iu, network);
					guiAPI.setUp(med);
					med.setUp();
					med.startServer();
										
					String identitate = current_user + " " + iu.getUserIP() + " " + iu.getUserPort();
					ArrayList <String> tempf = iu.getUserFilesName();
					
					for(int i = 0; i < tempf.size(); i++){
						identitate = identitate + " " + tempf.get(i);
					}
					
					network.retrieveInfo(0, identitate,
							new InfoUser("web_server").getUserIP(),
							Integer.parseInt(new InfoUser("web_server").getUserPort()));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}
}
