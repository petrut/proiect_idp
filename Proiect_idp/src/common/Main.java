package common;

import gui.GuiAPI;

import java.awt.EventQueue;

import test.MockupMediator;



public class Main {

	public static void main(String []args) throws InterruptedException{
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiAPI guiAPI = new GuiAPI();
					
					MockupMediator med = new MockupMediator(guiAPI);
					guiAPI.setUp(med);
					
					med.setUp();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		System.out.println("> Final test!");
	}
}
