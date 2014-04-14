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
		
		System.out.println("> current user = " + current_user);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					InfoUser iu = new InfoUser(current_user);
					GuiAPI guiAPI = new GuiAPI(iu);
					
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
