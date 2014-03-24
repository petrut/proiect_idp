package test;

import gui.GuiAPI;

import java.util.List;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.SwingWorker;

public class NewUserWorker extends SwingWorker<Integer, Integer>{

	GuiAPI guiAPI;
	MockupMediator med;
	private Random rand = new Random();

	public NewUserWorker(GuiAPI guiAPI, MockupMediator med)
	{
		this.guiAPI = guiAPI;
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
				int destination  = (1 + Math.abs(rand.nextInt()))%guiAPI.users.size();
				DefaultListModel<String> ll = new DefaultListModel<String>();
				ll.addElement("modf_file_" + (i + 1));
				ll.addElement("modf_file_" + (i + 2));
				ll.addElement("modf_file_" + (i + 3));
				
				guiAPI.add_new_user(guiAPI.users.elementAt(destination), ll);
			}
			else
			if(i % 5 != 0)
			{
				DefaultListModel<String> ll = new DefaultListModel<String>();
				ll.addElement("file_" + (i + 1));
				ll.addElement("file_" + (i + 2));
				ll.addElement("file_" + (i + 3));
				guiAPI.add_new_user("User_" + i, ll);
			}
			else
			{
				if(guiAPI.users.size() > 1)
				{
					int destination  = (1 + Math.abs(rand.nextInt()))%guiAPI.users.size();
					guiAPI.remove_user(guiAPI.users.elementAt(destination));
				}
			}
		}


	}
}