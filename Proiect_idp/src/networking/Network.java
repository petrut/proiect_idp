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
import common.InfoTransfers;



//clasa ce ofera API pentru modulul de network
public class Network implements INetwork {

	public IMediator med;
	Selector selector = null;
	ServerSocketChannel server = null;
	static Logger logger = Logger.getLogger(Network.class);
	final ReentrantLock selectorLock = new ReentrantLock();
	IProcessMessage procMessage;
	
	public Network(IProcessMessage procMessage)
	{
		this.procMessage = procMessage;
	}
	public void setMediator(IMediator med)
	{
		this.med = med;
		procMessage.setMediator(med);
	}

	// pornire server
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
					
					// daca trebuuie sa acceptam conexiuni
					if (key.isAcceptable()) { 
						
						
						logger.warn("SERVER: ACCEPT client ");
						SocketChannel client = server.accept(); 
						client.configureBlocking(false); 
						//	client.socket().setTcpNoDelay(true); 
						client.register(selector, SelectionKey.OP_READ);
						
						
				
					} 
					// in functie de tipul mesajului il vom procesa
					if (key.isReadable()) 
					{
						
						SocketChannel clientChannel = (SocketChannel) key.channel();
				
						logger.warn("SERVER: sosesc date ");
						boolean finnish = procMessage.proccesMessage(new SocketOperationAPI(clientChannel));
						if(finnish == true)
						{
							key.cancel();
							clientChannel.close();
						}
						
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

	// obtinere fisier de la destinatie
	@Override
	public void retrieveFile(final InfoTransfers it, final String ipFrom, final int portFrom)
			throws IOException {
		// TODO Auto-generated method stub
		logger.warn("Network retrieve file : " +   it.file_name  + " "+ portFrom );
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					SocketOperationAPI sockAPI = new SocketOperationAPI(ipFrom, portFrom);
					
					String msg= it.file_name + " " + it.src + " " + it.dest;
					
					ByteBuffer request = MessageToByte.requestGetChunckNumber(msg);
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
