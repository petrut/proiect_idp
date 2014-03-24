package test;
import java.util.Vector;
import main.Info_transfer;

import main.GUI;




public class MockupMediator  {

	GUI gui ;
	
	public Vector <Info_transfer>transferuriNeterminate = new Vector<Info_transfer>();
	
	
	
	public MockupMediator(GUI gui) 
	{
		this.gui = gui;
	}

	public void setUp() 
	{
		new TransferuriNoi(gui,this).execute();
		new TransferuriPachetNou(gui,this).execute();
	}

	
	
	
}
