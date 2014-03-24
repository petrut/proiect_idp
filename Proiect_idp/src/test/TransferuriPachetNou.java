package test;

import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import main.GUI;
import main.Info_transfer;

public class TransferuriPachetNou  extends SwingWorker<Integer, Info_transfer>{

	GUI gui ;
	MockupMediator med;

	public TransferuriPachetNou(GUI gui, MockupMediator med) 
	{
		this.gui = gui;
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
					Info_transfer it = med.transferuriNeterminate.get(index);
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
	protected void process(List<Info_transfer> chunks) {
		// TODO 3.3 - print values received

		for(Info_transfer e : chunks)
		{	

			gui.set_progress(e);
		}



		System.out.println(chunks);
		System.out.println("process: " + Thread.currentThread());
	}


}
