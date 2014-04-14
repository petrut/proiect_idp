package common;

import gui.GuiAPI;

import java.awt.EventQueue;

import test.MockupMediator;

// clasa temporara pentru testarea aplicatiei (partea grafica)

public class Main {
	
	static String current_user = "me";

	public static void main(String []args) throws InterruptedException{
		
		if(args.length > 0){
			current_user = args[0];
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiAPI guiAPI = new GuiAPI();
					InfoUser iu = new InfoUser(current_user);
					
					MockupMediator med = new MockupMediator(guiAPI, iu);
					guiAPI.setUp(med);
					
					med.setUp();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}
}
