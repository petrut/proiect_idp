package test;

import gui.GuiAPI;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.SwingWorker;

import common.InfoTransfers;
//Acest SwingWorker simuleaza aparatia de pachete
public class NewPackageWorker  extends SwingWorker<Integer, InfoTransfers>{

	GuiAPI guiAPI ;
	MockupMediator med;

	public NewPackageWorker(GuiAPI guiAPI, MockupMediator med) 
	{
		this.guiAPI = guiAPI;
		this.med = med;
	}

	@Override
	protected Integer doInBackground() throws Exception {
		Random x = new Random();
		Vector <InfoTransfers>transferuriNeterminate =(Vector <InfoTransfers>) med.getUnfinishedTransfers();
		while(true)
		{
			// timp random de sleep
			int i  = Math.abs(x.nextInt()) %1000;
			int incrementValue  = 5 + Math.abs(x.nextInt()) %10;
			synchronized (transferuriNeterminate) {

				// daca avem transferuri neterminate
				if(transferuriNeterminate.size() > 0)
				{
					// alege un transfer si va incrementa progresul
					int index = Math.abs(x.nextInt()) %transferuriNeterminate.size();
					InfoTransfers it = transferuriNeterminate.get(index);
					it.progress += incrementValue;
					if(it.progress > 99)
						transferuriNeterminate.remove(it); 
					publish(it);
				}
			}
			Thread.sleep(i);
		}

	}



	@Override
	protected void process(List<InfoTransfers> chunks) {
		for(InfoTransfers e : chunks)
		{	
			guiAPI.set_progress(e);
		}
	}


}
