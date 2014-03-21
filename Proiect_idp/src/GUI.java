import java.util.Hashtable;
import javax.swing.DefaultListModel;

// componenta care se ocupa de partea grafica a programului
public class GUI {

	Transfer_gui tg;
	
	String current_user;
	String crrent_file;
	
	DefaultListModel<String> users = new DefaultListModel<String>();
	DefaultListModel<String> files = new DefaultListModel<String>();
	
	Hashtable<String, DefaultListModel<String>> user_folders = new Hashtable<String, DefaultListModel<String>>();
	
	public GUI(){		
		tg = new Transfer_gui(this);
		tg.frame.setVisible(true);
		
		init_gui();
	}
	
	//=========================================================================
	
	public void init_gui(){						// temporar
		users.addElement("me");
		
		files.addElement("fis_unu");
		files.addElement("fis_doi");
		files.addElement("fis_trei");
	}
	
	public void reset_users(){
		
	}
	
	// la selectarea unui nou utilizator(DUBLU click) resetare lista fisiere
	public void user_selected(String user){
		
	}
	
	// selectare fisier(DUBLU click), initializare transfer(download)
	public void start_download(String file){
		
	}
	
	public void nimic(){}
}
