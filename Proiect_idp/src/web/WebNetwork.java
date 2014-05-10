package web;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;
import common.IMediator;
import common.InfoTransfers;


//clasa ce ofera API pentru modulul de network

public class WebNetwork implements WebINetwork {

	public IMediator med;
	Selector selector = null;
	ServerSocketChannel server = null;
	static Logger logger = Logger.getLogger(WebMain.class);
	final ReentrantLock selectorLock = new ReentrantLock();
	WebIProcessMessage procMessage;
	
	
	/* informatii despre utilizatorii din sistem */	
	public static Hashtable<String, String> info_base = 
			new Hashtable<String, String>();
	
	
	public WebNetwork(WebIProcessMessage procMessage)
	{
		this.procMessage = procMessage;
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
						boolean finnish = procMessage.proccesMessage(new WebSocketOperationAPI(clientChannel));
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
					WebSocketOperationAPI sockAPI = new WebSocketOperationAPI(ipFrom, portFrom);
					
					String msg= it.file_name + " " + it.src + " " + it.dest;
					
					ByteBuffer request = WebMessageToByte.requestGetChunckNumber(msg);
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
	
	
	// obtinere informatii de la Web server
		@Override
		
		public void transmitInfo(final int tip, final String info_req, final String ipFrom, final int portFrom)
				throws IOException {
			
			logger.warn("Client retrieve info : " +   info_req  + " "+ portFrom );
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						WebSocketOperationAPI sockAPI = new WebSocketOperationAPI(ipFrom, portFrom);
						
						ByteBuffer request;
						
						if(tip == 9){
							request = WebMessageToByte.requestExit(info_req);
							
							logger.warn("< WEB > Network SigKill 9");
						}
						else{
							request = WebMessageToByte.responseInfoEncode(info_req);
						}
																		
						logger.warn("send request info BEFORE");
						
						try{
						sockAPI.send(request);
						
						sockAPI.close();				
						}
						catch (NotYetConnectedException e){
							
						}
						logger.warn("send request info DONE");
												
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
		
		public void procesare_mesaj_info(int tip, String mesaj_brut) throws NumberFormatException, IOException{
			
			Boolean exista = false;
			String data[] = mesaj_brut.split("\\s+");
			
			logger.warn("> 172 mesaj brut [ " + mesaj_brut + " ]");
			
			String new_nume	= data[0];
			String new_ip = "";
			String new_port = "";
			
			if(tip == 0){
				new_ip = data[1];
				new_port = data[2];
			}
			for(String key: info_base.keySet()){
				
				String temp[] = info_base.get(key).split("\\s+");
				
				if(! new_nume.equals(temp[0])){					
					
					/* transmite catre restul utilizatorilor */
					transmitInfo(tip, mesaj_brut, temp[1], Integer.parseInt(temp[2]));
					
					if(tip == 0){
						/* date despre restul utilizatorilor trimise catre cel nou din sistem */
						transmitInfo(tip, info_base.get(key), new_ip, Integer.parseInt(new_port));
					}
				}
				else{
					exista = true;
				}
			}
			
			if(!exista && tip == 0){
				info_base.put(new_nume, mesaj_brut);
			}
			
			if(exista && tip == 0){
				info_base.remove(new_nume);
				info_base.put(new_nume, mesaj_brut);
			}
			
			if(exista && tip == 9){
				info_base.remove(new_nume);
			}
		}

	public void remove_user(String user){
		
		info_base.remove(user);
	}
}
