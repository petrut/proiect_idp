package test;
import gui.GuiAPI;

import java.util.List;
import java.util.Vector;

import common.IMediator;
import common.InfoTransfers;



//MockUp Mediator ce simuleaza aparitia de evenimente
public class MockupMediator implements IMediator {

	GuiAPI guiAPI ;
	
	private Vector <InfoTransfers>transferuriNeterminate = new Vector<InfoTransfers>();
	
	
	
	public MockupMediator(GuiAPI guiAPI) 
	{
		this.guiAPI = guiAPI;
	}

	// pornire SwingWorkeri pentru simulare evenimente
	public void setUp() 
	{
		new NewTransferWorker(guiAPI,this).execute();
		new NewPackageWorker(guiAPI,this).execute();
		new NewUserWorker(guiAPI,this).execute();
	}

	@Override
	public void addTransfer(InfoTransfers it) {
		transferuriNeterminate.add(it);
	}

	@Override
	public List<InfoTransfers> getUnfinishedTransfers() {
		return transferuriNeterminate;
	}

	@Override
	public void removeTransfer(InfoTransfers it) {
		transferuriNeterminate.remove(it);
	}

	
	
	
}
