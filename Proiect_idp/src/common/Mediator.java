package common;

import gui.GuiAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import networking.INetwork;
import networking.Network;
import networking.SocketOperationAPI;

public class Mediator implements IMediator{

	GuiAPI guiAPI;
	InfoUser infoUser ;

	HashMap <String, MappedByteBuffer> nameBuffer = new HashMap<String, MappedByteBuffer>();
	INetwork network;
	
	private Vector <InfoTransfers>transferuriNeterminate = new Vector<InfoTransfers>();
	static Logger logger = Logger.getLogger(Mediator.class);
	
	
	public Mediator(GuiAPI guiAPI, InfoUser iu,INetwork network) throws IOException 
	{
		this.guiAPI = guiAPI;
		this.infoUser = iu;
		this.network = network;
		
		this.network.setMediator(this);
		
		for(File file : infoUser.getUserFiles())
		{
				RandomAccessFile raf = new RandomAccessFile(file, "r");
				MappedByteBuffer mappedBuffer = raf.getChannel().map(MapMode.READ_ONLY, 0, file.length());
				nameBuffer.put(file.getName(), mappedBuffer);	
		}
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}
	@Override 
	public void addTransfer(InfoTransfers it)
	{
		transferuriNeterminate.add(it);
	}
	
	@Override
	public void addReceivingTransfer(InfoTransfers it) {
		logger.warn("Add transfer + it.dest = " + it.src);
		
		File file = new File(it.file_name+"_received_"+it.src);
		try {
			RandomAccessFile raf  = new RandomAccessFile(file, "rw");
			it.raf = raf;
			transferuriNeterminate.add(it);
			InfoUser uiTemp = new InfoUser(it.src);
			try {
				network.retrieveFile(it,  uiTemp.getUserIP(), Integer.parseInt(uiTemp.getUserPort()));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	
	@Override
	public Integer getPort() {
		try {
			return Integer.parseInt(infoUser.getUserPort());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<InfoTransfers> getUnfinishedTransfers() {
		return transferuriNeterminate;
	}

	@Override
	public void removeTransfer(InfoTransfers it) {
		transferuriNeterminate.remove(it);
	}


	public ByteBuffer getFileBuffer(String fileName)
	{
		return nameBuffer.get(fileName).asReadOnlyBuffer();
	}
	
	@Override
	public boolean addChunckSending(InfoTransfers tempIt)
	{

		boolean ret = false;
		for(InfoTransfers e : transferuriNeterminate)
		{
			if(e.file_name.equals(tempIt.file_name) && e.src.equals(tempIt.src) && e.dest.equals(tempIt.dest))
			{
				
			
					e.chunckIndex++;
					if(e.chunckIndex < e.chunckNr )
						ret = true;
					else
						ret = false;
					
					e.progress = 100*e.chunckIndex/e.chunckNr;
					logger.warn("progress = " + e.progress + "e.chunkNr = " + e.chunckNr);
					final InfoTransfers constInfo = e;
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							guiAPI.set_progress(constInfo);	
						}
					});
				
			}
			
		}
		return ret;
	}
	
	
	
	@Override
	public Integer addChunckReceveing(String filename,byte[] chunck)
	{
		Integer ret  = null;
		for(InfoTransfers e : transferuriNeterminate)
		{
			if(e.file_name.equals(filename))
			{
				try {
					e.raf.write(chunck);
					e.chunckIndex++;
					if(e.chunckIndex < e.chunckNr )
						ret = e.chunckIndex;
					else
						e.raf.close();
					
					e.progress = 100*e.chunckIndex/e.chunckNr;
					logger.warn("progress = " + e.progress + "e.chunkNr = " + e.chunckNr);
					final InfoTransfers constInfo = e;
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							guiAPI.set_progress(constInfo);	
						}
					});
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		return ret;
	}
	
	
	public void setChunckNr(String filename,int chunckNr)
	{
		for(InfoTransfers e : transferuriNeterminate)
		{
			if(e.file_name.equals(filename))
			{
					e.chunckNr = chunckNr;
			}
			
		}
	}

	
	
	@Override
	public void startServer() {
		// TODO Auto-generated method stub
		final IMediator med = this;
		final Integer port =  med.getPort();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					network.start_server(port);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	
	@Override
	public void addSendingTransfer(final InfoTransfers it) {
		// TODO Auto-generated method stub
		
		transferuriNeterminate.add(it);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				guiAPI.add_transfer(it);	
			}
		});
	}
	
	
}
