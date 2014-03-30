package test;

import gui.GuiAPI;

import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import common.InfoTransfers;
/*creare transfer nou */
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

		Random x = new Random();
		while(true)
		{
			// timp random de asteptat random
			int i  = 5000+ Math.abs(x.nextInt()) %1000;
			// alege destinatie random
			int destination = Math.abs(x.nextInt()) %guiAPI.users.size();
			//alege fisier random
			int file = Math.abs(x.nextInt()) % guiAPI.users_files.get(guiAPI.current_user).size();
			// alegem fisier astfel incat sa nu avem sursa identica cu destinatia
			if(!guiAPI.users.get(destination).equals(guiAPI.current_user))
			{
				InfoTransfers it = new InfoTransfers(guiAPI.current_user, guiAPI.users.get(destination), 
						guiAPI.users_files.get(guiAPI.current_user).get(file), "Sending", 0);
				publish(it);
				med.addTransfer(it);
			}
			
			Thread.sleep(i);
		}

	}



	// folosim api-ul interfetei grafice si adaugam transferul 
	@Override
	protected void process(List<InfoTransfers> chunks) {

		for(InfoTransfers e : chunks)
		{	
			guiAPI.add_transfer(e);
		}

	}


}
