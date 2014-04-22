package networking;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import common.IMediator;




public class Network implements INetwork {

	public IMediator med;
	Selector selector = null;
	ServerSocketChannel server = null;
	static Logger logger = Logger.getLogger(Network.class);
	final ReentrantLock selectorLock = new ReentrantLock();

	public void setMediator(IMediator med)
	{
		this.med = med;
	}

	//serverul va asocia pentru fiecare client nou cate 
	public void start_server(Integer port) throws IOException
	{
		logger.warn("Start Server");
		
		try
		{
			selector = Selector.open(); 
			server = ServerSocketChannel.open(); 
			server.socket().bind(new InetSocketAddress(port)); 
			server.configureBlocking(false); 
			server.register(selector, SelectionKey.OP_ACCEPT);

			while (true) {
				selectorLock.lock();
				selectorLock.unlock();
				selector.select(400);
				for (Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i.hasNext();) { 
					SelectionKey key = i.next(); 
					i.remove(); 
					if (key.isConnectable()) {
						System.out.println("CONECT");
						((SocketChannel)key.channel()).finishConnect(); 
					} 
					if (key.isAcceptable()) { 
						// accept connection 
						
						logger.warn("SERVER: ACCEPT client ");
						SocketChannel client = server.accept(); 
						client.configureBlocking(false); 
						//	client.socket().setTcpNoDelay(true); 
						client.register(selector, SelectionKey.OP_READ);
						
						
				
					} 
					// vom asocia pentru fiecare client cate un tread
					if (key.isReadable()) 
					{
						
						SocketChannel clientChannel = (SocketChannel) key.channel();
					//	key.cancel();
						logger.warn("SERVER: sosesc date ");
						new ProcessMessageThread(new  SocketOperationAPI(clientChannel), med);
						
					}
				}
			}
		}



		catch (Throwable e) { 
			e.printStackTrace();
			throw new RuntimeException("Server failure: "+e.getMessage()  + " \n" );
		} finally {
			try {
				selector.close();
				server.socket().close();
				server.close();

			} catch (Exception e) {
				// do nothing - server failed
			}
		}

	}

	@Override
	public void retrieveFile(final String filename, final String ipFrom, final int portFrom)
			throws IOException {
		// TODO Auto-generated method stub
		logger.warn("Network retrieve file : " +   filename  + " "+ portFrom );
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					SocketOperationAPI sockAPI = new SocketOperationAPI(ipFrom, portFrom);
					
					ByteBuffer request = MessageToByte.requestGetChunckNumber(filename);
					logger.warn("send request chunck number BEFORE manu*");
					
					selectorLock.lock();
					try {
					    selector.wakeup();

					    sockAPI.sockChannel.register(selector, SelectionKey.OP_READ);
					} finally {
					    selectorLock.unlock();
					}
					
					
					logger.warn("send request chunck number BEFORE");
					sockAPI.send(request);
					logger.warn("send request chunck number DONE");
					
					
					//ByteBuffer response =sockAPI.read();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.getLocalizedMessage());
					System.out.println(e.getMessage());
					System.out.println(e.getCause());
					
				}	
				
			}
		}).start();
		
		
		
		
	}

}
