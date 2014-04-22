package networking;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import common.IMediator;
import common.InfoTransfers;
import gui.*;

public class ProcessMessageThread {

	SocketOperationAPI sockOP = null;
	IMediator med = null;
	static Logger logger = Logger.getLogger(Process.class);
	public ProcessMessageThread(SocketOperationAPI sockOP, IMediator med) {
		super();
		this.sockOP = sockOP;
		this.med = med;
		run();
	}

	public void run() 
	{
		try {
			
			ByteBuffer byteBuf = sockOP.read();
			byteBuf.flip();
			
			int type = byteBuf.getInt();
			logger.fatal("Process Msg type = " + type);
			switch(type)
			{
			case ServerConstants.RequestChunckType:
			{
			
				int chunckID = byteBuf.getInt();
				byte msgDataByte[] = new byte[byteBuf.remaining()];
				byteBuf.get(msgDataByte);
				String msgDataString = new String(msgDataByte);
				
				String data[] = msgDataString.split("\\s+");
				String fileNameString = data[0];
				
				ByteBuffer bb = med.getFileBuffer(fileNameString);

				logger.fatal("message type = RequestChunckType chunkID= "  + chunckID + "bb.limit = " + bb.limit() );
				

				int length = bb.limit() > (chunckID+1)* ServerConstants.BufferSize ? ServerConstants.BufferSize:bb.limit() - chunckID* ServerConstants.BufferSize;
				byte chunckData[] = new byte[length];
				logger.fatal("message type = RequestChunckType chunkID= "  + chunckID + "bb.limit = " + bb.limit() 
						+ "length = " + length  + "start = "+ chunckID*ServerConstants.BufferSize);
				
				bb.position(chunckID*ServerConstants.BufferSize);
				bb.get(chunckData,0,length);
				bb.flip();
				
				InfoTransfers tempIt= new InfoTransfers(data[1], data[2],data[0],Status.Sending,0);
				med.addChunckSending(tempIt);
				
				sockOP.send(MessageToByte.responseChunck(chunckID, msgDataString, chunckData));

			}


			break;
			case ServerConstants.RequestGetChunckNumber:
			{
				logger.fatal("message type = RequestGetChunckNumber");
				byte msgDataByte[] = new byte[byteBuf.remaining()];
				byteBuf.get(msgDataByte);
				String msgDataString = new String(msgDataByte);
				String data[] = msgDataString.split("\\s+");
				String fileNameString = data[0];
				ByteBuffer bb = med.getFileBuffer(fileNameString);
				
				int chunckNumber = bb.limit()/ServerConstants.BufferSize;
				if(bb.limit()%ServerConstants.BufferSize != 0)
					chunckNumber++;
				
				InfoTransfers it = new InfoTransfers(data[1], data[2], data[0],Status.Sending , 0);
				it.chunckNr = chunckNumber;
				med.addSendingTransfer(it);
				
				
				
				
				
				sockOP.send(MessageToByte.responseGetChunckNumber(msgDataString, chunckNumber));
				
			}
			break;
			case ServerConstants.ResponseGetChunckNumber:
			{
				logger.fatal("message type = ResponseGetChunckNumber");
				int chunckNumber = byteBuf.getInt();
				
				byte msgDataByte[] = new byte[byteBuf.remaining()];
				byteBuf.get(msgDataByte);
				String msgDataString = new String(msgDataByte);
				
				String data[] = msgDataString.split("\\s+");
				String fileNameString = data[0];
				
				med.setChunckNr(fileNameString, chunckNumber);
				logger.fatal("message type = ResponseGetChunckNumber filename = " + fileNameString);
				
				
				ByteBuffer byteBufferRequestChunck  = MessageToByte.requestChunck(0, msgDataString);
				
				sockOP.send(byteBufferRequestChunck);
				
				
			}
			break;
			
			case ServerConstants.ResponseChunckDataType:
			{
				logger.fatal("message type = ResponseGetChunckData");
				int chunckDataLength = byteBuf.getInt();
				byte chunckData[] = new byte[chunckDataLength];
				byteBuf.get(chunckData);
				byte msgDataByte[] = new byte[byteBuf.remaining()];
				byteBuf.get(msgDataByte);
				String msgDataString = new String(msgDataByte);
				
				String data[] = msgDataString.split("\\s+");
				String fileNameString = data[0];
				
				Integer nextChunck = med.addChunckReceveing(fileNameString, chunckData);
				logger.fatal("nextChunck = " + nextChunck);
				if(nextChunck != null)
				{
					ByteBuffer byteBufferRequestChunck  = MessageToByte.requestChunck(nextChunck, msgDataString);
					sockOP.send(byteBufferRequestChunck);
				}
				
			}
			break;
			
			}

			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
