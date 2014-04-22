package networking;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import common.IMediator;

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
				byte fileNameByte[] = new byte[byteBuf.remaining()];
				byteBuf.get(fileNameByte);
				String fileNameString = new String(fileNameByte);
				ByteBuffer bb = med.getFileBuffer(fileNameString);

				logger.fatal("message type = RequestChunckType chunkID= "  + chunckID + "bb.limit = " + bb.limit() );
				

				int length = bb.limit() > (chunckID+1)* ServerConstants.BufferSize ? ServerConstants.BufferSize:bb.limit() - chunckID* ServerConstants.BufferSize;
				byte chunckData[] = new byte[length];
				logger.fatal("message type = RequestChunckType chunkID= "  + chunckID + "bb.limit = " + bb.limit() 
						+ "length = " + length  + "start = "+ chunckID*ServerConstants.BufferSize);
				
				bb.position(chunckID*ServerConstants.BufferSize);
				bb.get(chunckData,0,length);
				bb.flip();
				
				sockOP.send(MessageToByte.responseChunck(chunckID, fileNameString, chunckData));

			}


			break;
			case ServerConstants.RequestGetChunckNumber:
			{
				logger.fatal("message type = RequestGetChunckNumber");
				byte fileNameByte[] = new byte[byteBuf.remaining()];
				byteBuf.get(fileNameByte);
				String fileNameString = new String(fileNameByte);
				ByteBuffer bb = med.getFileBuffer(fileNameString);
				
				int chunckNumber = bb.limit()/ServerConstants.BufferSize;
				if(bb.limit()%ServerConstants.BufferSize != 0)
					chunckNumber++;
				
				sockOP.send(MessageToByte.responseGetChunckNumber(fileNameString, chunckNumber));
				
			}
			break;
			case ServerConstants.ResponseGetChunckNumber:
			{
				logger.fatal("message type = ResponseGetChunckNumber");
				int chunckNumber = byteBuf.getInt();
				
				byte fileNameByte[] = new byte[byteBuf.remaining()];
				byteBuf.get(fileNameByte);
				String fileNameString = new String(fileNameByte);
				
				med.setChunckNr(fileNameString, chunckNumber);
				logger.fatal("message type = ResponseGetChunckNumber filename = " + fileNameString);
				
				
				ByteBuffer byteBufferRequestChunck  = MessageToByte.requestChunck(0, fileNameString);
				
				sockOP.send(byteBufferRequestChunck);
				
				
			}
			break;
			
			case ServerConstants.ResponseChunckDataType:
			{
				logger.fatal("message type = ResponseGetChunckData");
				int chunckDataLength = byteBuf.getInt();
				byte chunckData[] = new byte[chunckDataLength];
				byteBuf.get(chunckData);
				byte fileNameByte[] = new byte[byteBuf.remaining()];
				byteBuf.get(fileNameByte);
				String fileNameString = new String(fileNameByte);
				Integer nextChunck = med.addChunck(fileNameString, chunckData);
				logger.fatal("nextChunck = " + nextChunck);
				if(nextChunck != null)
				{
					ByteBuffer byteBufferRequestChunck  = MessageToByte.requestChunck(nextChunck, fileNameString);
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
