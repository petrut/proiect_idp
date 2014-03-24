package main;

import java.awt.EventQueue;

import test.MockupMediator;



public class Main {

	public static void main(String []args) throws InterruptedException{
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI gui = new GUI();
					
					MockupMediator med = new MockupMediator(gui);
					gui.setUp(med);
					
					med.setUp();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		System.out.println("> Final test!");
	}
}
