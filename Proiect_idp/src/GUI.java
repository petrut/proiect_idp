import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;

// componenta care se ocupa de partea grafica a programului
public class GUI {

	DefaultListModel<Info_transfer> list_transfers = new DefaultListModel<Info_transfer>();		// va fi mutata pe mediator
	
	String my_folder = ".";				// directorul pentru upload
	
	// zona grafica GUI
	Transfer_gui tg;										// componenta de desenare
	final JFileChooser file_chooser = new JFileChooser();	// selectare director personal
		
	// zona de date/actiuni GUI
	
	String current_user = "me";				// user pentru download
	String crrent_file;						// fisier de download
	
	DefaultListModel<String> users = new DefaultListModel<String>();	// lista utilizatorilor -> pentru JList
	DefaultListModel<String> files = new DefaultListModel<String>();	// lista fisierelor		-> JList
	
	// lista de utilizatori cu propria lista de fisiere
	Hashtable<String, DefaultListModel<String>> users_files = new Hashtable<String, DefaultListModel<String>>();
	
	//=========================================================================
	
	public GUI(){	
		
		users_files.put("me", new DefaultListModel<String>());
		
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
		
		Info_transfer it1 = new Info_transfer("de la el", "catre mine", "fisier important", "in curs de", 77);
		add_transfer(it1);
		
		Info_transfer it2 = new Info_transfer("de 11la el", "catre mineq", "fisierq important", "complete", 100);
		add_transfer(it2);
		
		Info_transfer it3 = new Info_transfer("de11 la el", "catreq mine", "fisier qqimportant", "in curs de", 33);
		add_transfer(it3);
		
		Info_transfer it4 = new Info_transfer("de la el1", "catre mineq", "fisier importantq", "complete", 100);
		add_transfer(it4);
		
		Info_transfer it5 = new Info_transfer("de la1 el", "catre mine", "fisierqqq important", "in curs de", 88);
		add_transfer(it5);
		
		DefaultListModel<String> lp = new DefaultListModel<String>();
		lp.addElement("p1");
		lp.addElement("p2");
		lp.addElement("p3");
		add_new_user("Petrut", lp);
		
		DefaultListModel<String> ld = new DefaultListModel<String>();
		ld.addElement("d1");
		ld.addElement("d2");
		ld.addElement("d3");
		add_new_user("Dragos", ld);
		
		
		afis_hash();
	}
	
	//=========================================================================
	
	// afis utilizatorii si fisierele detinute
	public void afis_hash(){
		
		System.out.print(">>> hash:");
		
		Enumeration<String> en = users_files.keys();
		
		while(en.hasMoreElements()){
			
			String name = en.nextElement();			
			System.out.print("\n" + name + ": ");
			
			DefaultListModel<String> lm = users_files.get(name);
			
			for(int i = 0; i < lm.size(); i++){
				System.out.print(lm.elementAt(i) + ", ");				
			}			
		}
		System.out.println();
	}
	
	//=========================================================================
	
	// adauga unn nou utilizator in program / s-a realizat o noua conexiune
	public void add_new_user(String name, DefaultListModel<String> lm){
		
		users_files.put(name, lm);
		users.addElement(name);
	}
	
	//=========================================================================
	
	// reseteaza lista de fisiere dupa ce s-a reactualizat directorul personal de upload
	public void reset_me(){
		
		files.clear();
		
		DefaultListModel<String> lm = users_files.get("me");
		
		for(int i = 0; i < lm.size(); i++){
			files.addElement(lm.elementAt(i));			
		}
	}
	
	//=========================================================================
	
	// la selectarea unui nou utilizator(DUBLU click) resetare lista fisiere
	public void switch_user(String user){
		
		files.clear();
		DefaultListModel<String> lm = users_files.get(user);
		
		for(int i = 0; i < lm.size(); i++){
			files.addElement(lm.elementAt(i));
		}
	}
	
	//=========================================================================
	
	// selectare fisier(DUBLU click), initializare transfer(download)
	public void start_download(String file){
		System.out.println(">>> Incepe descarcarea fisierului: " + file);
	}
	
	//=========================================================================
	
	// adauga informatiile unui nou transfer initiat
	public void add_transfer(Info_transfer it){
		
		tg.tab_transfer.add_new_transfer(it);
	}
	
	//=========================================================================
	
	// resetare folder personal, utilizat pentru upload
	public void reset_my_folder(){
		
		String name;
		
		File dir = new File(this.my_folder);
		File[] list_files = dir.listFiles();
		
		users_files.get("me").clear();
		
		for (File file : list_files) {
		    if (file.isFile()) {
		    	name = file.getName();
		        System.out.println(name);
		        
		        users_files.get("me").addElement(name);		        
		    }
		}
		
		reset_me();
	}
	
	//=========================================================================
	
	// nimic
	public void nimic(){}
}

//=========================================================================
