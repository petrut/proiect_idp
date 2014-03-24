package test;

import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import main.GUI;
import main.Info_transfer;

public class TransferuriNoi  extends SwingWorker<Integer, Info_transfer>{
	
	GUI gui ;
	MockupMediator med;
	
	public TransferuriNoi(GUI gui, MockupMediator med) 
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
			int i  = 5000+ Math.abs(x.nextInt()) %1000;
			int destination = Math.abs(x.nextInt()) %gui.users.size();
			int file = Math.abs(x.nextInt()) % gui.users_files.get("me").size();
			
			Info_transfer it = new Info_transfer("me", gui.users.get(destination), gui.users_files.get("me").get(file), "Sending", 0);
			publish(it);
			med.transferuriNeterminate.add(it);
			Thread.sleep(i);
		}
		
	}



	@Override
	protected void process(List<Info_transfer> chunks) {
		// TODO 3.3 - print values received

		for(Info_transfer e : chunks)
		{	
			gui.add_transfer(e);
		}



		System.out.println(chunks);
		System.out.println("process: " + Thread.currentThread());
	}


}
