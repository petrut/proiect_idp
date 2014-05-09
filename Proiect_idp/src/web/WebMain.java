package web;

import gui.GuiAPI;

import java.awt.EventQueue;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import networking.INetwork;
import networking.Network;
import networking.ProcessMessage;
import common.IMediator;
import common.InfoUser;
import common.Main;
import common.Mediator;

public class WebMain {
	
	static Logger logger = Logger.getLogger(WebMain.class);
	
	public static void main(String args[]){
		
		System.out.println("> Main WEB Server");
		
		String log_path = "Users_info/" + "web_server" + "/log4j.properties";		
		PropertyConfigurator.configure(log_path);
		
		/*
		logger.debug("Test debug message");
		logger.info("Test info message");
		logger.warn("Test warn message");
		logger.error("Test error message");
		logger.fatal("Test fatal message");
		*/
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					InfoUser iu = new InfoUser("web_server");
					//GuiAPI guiAPI = new GuiAPI(iu);
					
					WebProcessMessage wnet = new WebProcessMessage();
					
					WebINetwork network = new WebNetwork(wnet);
					
					wnet.set_wnet((WebNetwork) network);
					
					//IMediator med = new Mediator(guiAPI, iu, network);
					//guiAPI.setUp(med);
					//med.setUp();
					//med.startServer();
					
					network.start_server(Integer.parseInt(iu.getUserPort()));
					
					System.out.println("> Server Web pornit!");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

}
