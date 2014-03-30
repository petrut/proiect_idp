package test;

import gui.GuiAPI;

import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import common.InfoTransfers;

public class NewTransferWorker  extends SwingWorker<Integer, InfoTransfers>{

	GuiAPI guiAPI ;
	MockupMediator med;

	public NewTransferWorker(GuiAPI guiAPI, MockupMediator med) 
	{
		this.guiAPI = guiAPI;
		this.med = med;
	}

	@Override
	protected Integer doInBackground() throws Exception {
		// TODO Auto-generated method stub

		Random x = new Random();
		while(true)
		{
			int i  = 5000+ Math.abs(x.nextInt()) %1000;
			int destination = Math.abs(x.nextInt()) %guiAPI.users.size();
			int file = Math.abs(x.nextInt()) % guiAPI.users_files.get("me").size();
			if(!guiAPI.users.get(destination).equals(guiAPI.current_user))
			{
				InfoTransfers it = new InfoTransfers("me", guiAPI.users.get(destination), guiAPI.users_files.get("me").get(file), "Sending", 0);
				publish(it);
				med.addTransfer(it);
			}
			
			Thread.sleep(i);
		}

	}



	@Override
	protected void process(List<InfoTransfers> chunks) {
		// TODO 3.3 - print values received

		for(InfoTransfers e : chunks)
		{	
			guiAPI.add_transfer(e);
		}



		//System.out.println(chunks);
		//System.out.println("process: " + Thread.currentThread());
	}


}
