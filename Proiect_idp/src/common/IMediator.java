package common;

import java.nio.ByteBuffer;
import java.util.List;

// interfata mediator

public interface IMediator {
	public void setUp();
	public void addReceivingTransfer(InfoTransfers it);
	public void addSendingTransfer(InfoTransfers it);
	public void addTransfer(InfoTransfers it);

	
	public void removeTransfer(InfoTransfers it);
	public List<InfoTransfers> getUnfinishedTransfers();
	public ByteBuffer getFileBuffer(String fileName);
	public void startServer();
	public Integer getPort() ;
	public Integer addChunckReceveing(String filename,byte[] chunck);
	public boolean addChunckSending(InfoTransfers tempIt);
	
	public void setChunckNr(String filename,int chunckNr);
	
	
}

