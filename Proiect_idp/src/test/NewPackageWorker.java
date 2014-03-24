package test;

import gui.GuiAPI;

import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import common.InfoTransfers;

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
		// TODO Auto-generated method stub

		Random x = new Random();
		while(true)
		{
			int i  = Math.abs(x.nextInt()) %1000;
			int incrementValue  = 5 + Math.abs(x.nextInt()) %10;
			synchronized (med.transferuriNeterminate) {

				if(med.transferuriNeterminate.size() > 0)
				{
					int index = Math.abs(x.nextInt()) %med.transferuriNeterminate.size();
					InfoTransfers it = med.transferuriNeterminate.get(index);
					it.progress += incrementValue;
					if(it.progress > 99)
						med.transferuriNeterminate.remove(it); 
					publish(it);
				}
			}
			Thread.sleep(i);





		}

	}



	@Override
	protected void process(List<InfoTransfers> chunks) {
		// TODO 3.3 - print values received

		for(InfoTransfers e : chunks)
		{	

			guiAPI.set_progress(e);
		}



		System.out.println(chunks);
		System.out.println("process: " + Thread.currentThread());
	}


}
