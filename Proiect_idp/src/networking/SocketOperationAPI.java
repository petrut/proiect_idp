package networking;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;



public class SocketOperationAPI {
	
	static Logger logger = Logger.getLogger(SocketOperationAPI.class);
	SocketChannel sockChannel = null;
	
	public SocketOperationAPI(String IP, int port) throws IOException
	{
		
		logger.warn("SOCKET OP : connect" + " " + IP + " " + port);
		try{
			
			 sockChannel= SocketChannel.open();

			// we open this channel in non blocking mode
			sockChannel.configureBlocking(false);
			sockChannel.connect(new InetSocketAddress(IP, port));

			long timeOld = System.currentTimeMillis();


			while (!sockChannel.finishConnect()) {
				long timeNow = System.currentTimeMillis();
				if( timeNow -timeOld >1000)
				{
					//return null;
					break;
				}
			}
		}
		catch(IOException e)
		{
			//e.printStackTrace();
			//System.out.println(e.getMessage());
			
			
		}
		
	}

	public  SocketOperationAPI(SocketChannel socketChannel) throws IOException
	{
		this.sockChannel = socketChannel;
	}
	
	
	public void send(ByteBuffer byteBuffer) throws IOException
	{
		
		
		while (byteBuffer.hasRemaining()) {

			sockChannel.write(byteBuffer);
		}
	}
	
	
	
	
	public ByteBuffer read() throws IOException
	{
				ByteBuffer info = ByteBuffer.allocate(4);
				int remainingBytes = 4;
				int bytesRead = -1;
				info.clear();
				
				logger.warn("message read" );
				do
				{
					bytesRead = sockChannel.read(info);
			
					logger.warn("message read bread = " +bytesRead  );
					if(bytesRead == -1)
					{
						throw new IOException("Stream ended");
					}
					if(bytesRead == 0)
						throw new IOException("No data, but client still connected1");

					remainingBytes -= bytesRead; 
					

				}
				while(info.remaining() > 0);
				
				//?? FLIP
				info.flip();
				remainingBytes = info.getInt();
				logger.warn("message length :" + remainingBytes );
				info = ByteBuffer.allocate(remainingBytes);
				info.clear();
				
				
				do
				{
					bytesRead = sockChannel.read(info);
			
					if(bytesRead == -1)
					{
						throw new IOException("Stream ended");
					}
					if(bytesRead == 0)
						throw new IOException("No data, but client still connected1");

					remainingBytes -= bytesRead; 
					

				}
				while(info.remaining() > 0);

				//? FLIP
				
				
				return info;

	}
}