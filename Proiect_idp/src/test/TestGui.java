
package test;

import java.awt.EventQueue;

import common.*;
import gui.FileTransfers;
import gui.GuiAPI;
import junit.framework.TestCase;


public class TestGui extends TestCase{
	
	public TestGui(String name) {
		super(name);
	}
	
	public void testAddNewTransfer(){
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				GuiAPI ga = new GuiAPI();
				FileTransfers ft = new FileTransfers(ga);
				
				System.out.println("> test GUI add new transfer ");
				
				InfoTransfers it = new InfoTransfers("ana", "are", "mere", "multe", 23);
				
				ft.add_new_transfer(it);
								
				assertEquals("TestGui", ft.getValueAt(0, 0).toString(), "ana");
				assertEquals("TestGui", ft.getValueAt(0, 1).toString(), "are");	
				assertEquals("TestGui", ft.getValueAt(0, 2).toString(), "mere");	
				assertEquals("TestGui", ft.getValueAt(0, 3).toString(), "multe");	
				assertEquals("TestGui", ft.getValueAt(0, 4).toString(), "23");	
			}
		});
	}
	
	
	public void testResetProgress(){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				GuiAPI ga = new GuiAPI();
				FileTransfers ft = new FileTransfers(ga);
				
				System.out.println("> test GUI add new transfer ");
				
				InfoTransfers it = new InfoTransfers("ana", "are", "mere", "multe", 23);
				
				ft.add_new_transfer(it);
				ft.reset_progress("ana", "are", "mere", 55);
				
				assertEquals("TestGui", ft.getValueAt(0, 4).toString(), "55");	
			}
		});
	}
	
	
	public void testResetStatus(){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				GuiAPI ga = new GuiAPI();
				FileTransfers ft = new FileTransfers(ga);
				
				System.out.println("> test GUI add new transfer ");
				
				InfoTransfers it = new InfoTransfers("ana", "are", "mere", "multe", 23);
				
				ft.add_new_transfer(it);
				ft.reset_status("ana", "are", "mere", "Completed");
				
				assertEquals("TestGui", ft.getValueAt(0, 3).toString(), "Completed");	
			}
		});
	}
	
	
}