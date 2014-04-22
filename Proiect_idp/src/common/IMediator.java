package common;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.List;

// interfata mediator

public interface IMediator {
	public void setUp();
	public void addTransfer(InfoTransfers it);
	public void removeTransfer(InfoTransfers it);
	public List<InfoTransfers> getUnfinishedTransfers();
	public ByteBuffer getFileBuffer(String fileName);
	public void startServer();
	public Integer getPort() ;
	public Integer addChunck(String filename,byte[] chunck);
	public void setChunckNr(String filename,int chunckNr);
	
	
}

