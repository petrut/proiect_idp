package test;
import gui.GuiAPI;

import java.util.Vector;

import common.InfoTransfers;




public class MockupMediator  {

	GuiAPI guiAPI ;
	
	public Vector <InfoTransfers>transferuriNeterminate = new Vector<InfoTransfers>();
	
	
	
	public MockupMediator(GuiAPI guiAPI) 
	{
		this.guiAPI = guiAPI;
	}

	public void setUp() 
	{
		new NewTransferWorker(guiAPI,this).execute();
		new NewPackageWorker(guiAPI,this).execute();
		new NewUserWorker(guiAPI,this).execute();
	}

	
	
	
}
