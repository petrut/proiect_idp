package web;

import java.awt.EventQueue;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import common.InfoUser;


/* server Web ce va comunica informatii utilizatorilor conectati */

public class WebMain {
	
	static Logger logger = Logger.getLogger(WebMain.class);
	
	public static void main(String args[]){
			
		String log_path = "Users_info/" + "web_server" + "/log4j.properties";		
		PropertyConfigurator.configure(log_path);
	
		logger.warn("> Main WEB Server");
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					InfoUser iu = new InfoUser("web_server");
					
					WebProcessMessage wnet = new WebProcessMessage();
					
					WebINetwork network = new WebNetwork(wnet);
					
					wnet.set_wnet((WebNetwork) network);
					
					network.start_server(Integer.parseInt(iu.getUserPort()));
					
					logger.warn("> Server Web pornit!");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

}
