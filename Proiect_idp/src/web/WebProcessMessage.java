package web;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;
import common.ServerConstants;

// clasa in se proceseaza mesajale
public class WebProcessMessage implements WebIProcessMessage {

	
	//IMediator med = null;
	static Logger logger = Logger.getLogger(Process.class);
	
	public WebNetwork wnet;
	
	public WebProcessMessage(){
		
	}
	
	public void set_wnet(WebNetwork wnet){
		this.wnet = wnet;
	}
	
	//Sunt doua tipuri de mesaje pentru obtinerea numarului de chunckuri 
	// si pentru obitinerea unui chunck
	// Acestea se mai impart  inca o data in Requesturi si Response 
	// if the connection needs to be close this function will return true
	@Override
	public boolean proccesMessage(WebSocketOperationAPI sockOP) 
	{
		try {
			
			ByteBuffer byteBuf = sockOP.read();
			byteBuf.flip();
			
			int type = byteBuf.getInt();
			logger.fatal("Process Msg type = " + type);
			switch(type)
			{
			
				case ServerConstants.Sigkill:
				{
					byte msgDataByte[] = new byte[byteBuf.remaining()];
					byteBuf.get(msgDataByte);
					String user_name = new String(msgDataByte);
					
					System.out.println("\n\n\n> Utilizatorul " + user_name + " a iesit din sistem!\n\n\n");
					
					wnet.procesare_mesaj_info(9, user_name);
					
					wnet.remove_user(user_name);
				
					return true;
				}
											
				case ServerConstants.RequestInfo:
				{
					byte msgDataByte[] = new byte[byteBuf.remaining()];
					byteBuf.get(msgDataByte);
					String msgDataString = new String(msgDataByte);
					
					String data[] = msgDataString.split("\\s+");
					
					String nume = data[0];
					String ip = data[1];
					String port = data[2];
					
					wnet.procesare_mesaj_info(0, msgDataString);
					
					
					System.out.println(">>> Mesaj primit de WEB Server de la un client: " + msgDataString);
					
					System.out.println("> nume = " + nume);
					System.out.println("> ip = " + ip);
					System.out.println("> port = " + port);
														
					System.out.println("> Web a trimis raspunsul de info");
					
					return true;					
				}
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
