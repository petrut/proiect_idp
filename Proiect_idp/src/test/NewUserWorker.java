package test;

import java.util.List;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.SwingWorker;

import main.GUI;

public class TranzitUtilizatori extends SwingWorker<Integer, Integer>{

	GUI gui;
	MockupMediator med;
	private Random rand = new Random();

	public TranzitUtilizatori(GUI gui, MockupMediator med)
	{
		this.gui = gui;
		this.med = med;
	}

	@Override
	protected Integer doInBackground() throws Exception {
		int i = 1;
		while(true)
		{
			publish(i);
			i++;

			Thread.sleep(5000);
		}
	}


	@Override
	protected void process(List<Integer> chunks) {
		for(Integer i : chunks)
		{
			
			if(i%3 == 0)
			{
				int destination  = (1 + Math.abs(rand.nextInt()))%gui.users.size();
				DefaultListModel<String> ll = new DefaultListModel<String>();
				ll.addElement("modf_file_" + (i + 1));
				ll.addElement("modf_file_" + (i + 2));
				ll.addElement("modf_file_" + (i + 3));
				
				gui.add_new_user(gui.users.elementAt(destination), ll);
			}
			else
			if(i % 5 != 0)
			{
				DefaultListModel<String> ll = new DefaultListModel<String>();
				ll.addElement("file_" + (i + 1));
				ll.addElement("file_" + (i + 2));
				ll.addElement("file_" + (i + 3));
				gui.add_new_user("User_" + i, ll);
			}
			else
			{
				if(gui.users.size() > 1)
				{
					int destination  = (1 + Math.abs(rand.nextInt()))%gui.users.size();
					gui.remove_user(gui.users.elementAt(destination));
				}
			}
		}


	}
}