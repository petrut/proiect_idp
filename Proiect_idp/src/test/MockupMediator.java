package test;
import gui.GuiAPI;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Vector;

import common.IMediator;
import common.InfoTransfers;
import common.InfoUser;


//MockUp Mediator ce simuleaza aparitia de evenimente
public class MockupMediator implements IMediator {

	GuiAPI guiAPI;
	InfoUser infoUser;
	
	private Vector <InfoTransfers>transferuriNeterminate = new Vector<InfoTransfers>();
	
	
	
	public MockupMediator(GuiAPI guiAPI, InfoUser iu) 
	{
		this.guiAPI = guiAPI;
		this.infoUser = iu;
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

	@Override
	public ByteBuffer getFileBuffer(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startServer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReceivingTransfer(InfoTransfers it) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSendingTransfer(InfoTransfers it) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getPort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer addChunckReceveing(String filename, byte[] chunck) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addChunckSending(InfoTransfers tempIt) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setChunckNr(String filename, int chunckNr) {
		// TODO Auto-generated method stub
		
	}
}
