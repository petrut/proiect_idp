package networking;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import common.IMediator;
import common.InfoTransfers;
import gui.*;


// clasa in se proceseaza mesajale
public class ProcessMessageThread {

	SocketOperationAPI sockOP = null;
	IMediator med = null;
	static Logger logger = Logger.getLogger(Process.class);
	public ProcessMessageThread(SocketOperationAPI sockOP, IMediator med) {
		super();
		this.sockOP = sockOP;
		this.med = med;
		
	}

	//Sunt doua tipuri de mesaje pentru obtinerea numarului de chunckuri 
	// si pentru obitinerea unui chunck
	// Acestea se mai impart  inca o data in Requesturi si Response 
	// if the connection needs to be close this function will return true
	
	public boolean proccesMessage() 
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
				
				
				
				// trimitere chunck la sursa
				sockOP.send(MessageToByte.responseChunck(chunckID, msgDataString, chunckData));
				
				InfoTransfers tempIt= new InfoTransfers(data[1], data[2],data[0],Status.Sending,0);
				
				// apelare med pentru actulizare interfata grafica si structuri proprii de evidenta
				if(med.addChunckSending(tempIt) == true)
					return true;

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
				// apelare pentru actualizare interfata grafica si evidenta transferurilor
				med.addSendingTransfer(it);		
				// trimitere raspuns
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
				//se actualizeaza numarul de chunckuri primit ca raspuns
				med.setChunckNr(fileNameString, chunckNumber);
				
				logger.fatal("message type = ResponseGetChunckNumber filename = " + fileNameString);
				
				// se trimite request pentru primul chunck
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
				
				// se foloseste mediatorul pentru actuliza structuri proprii si interfata grafica
				Integer nextChunck = med.addChunckReceveing(fileNameString, chunckData);
				logger.fatal("nextChunck = " + nextChunck);
				
				// daca adika mai avem nevoie de chuckuri se mai trimite un request
				if(nextChunck != null)
				{
					ByteBuffer byteBufferRequestChunck  = MessageToByte.requestChunck(nextChunck, msgDataString);
					sockOP.send(byteBufferRequestChunck);
				}
				else 
					return true;
				
				
				
			}
			break;
			
			}

			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
