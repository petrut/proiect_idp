package main;
import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;

import org.omg.PortableInterceptor.USER_EXCEPTION;

import test.MockupMediator;


// componenta care se ocupa de partea grafica a programului
public class GUI {

	String stat_in = "Receiving";			// tip status transfer
	String stat_out = "Sending";
	String stat_fin = "Completed";

	DefaultListModel<Info_transfer> list_transfers = new DefaultListModel<Info_transfer>();		// va fi mutata pe mediator

	String my_folder = ".";				// directorul pentru upload

	// zona grafica GUI
	Transfer_gui tg;										// componenta de desenare
	final JFileChooser file_chooser = new JFileChooser();	// selectare director personal

	// zona de date/actiuni GUI

	public String current_user = "me";					// user pentru download
	String current_file;						// fisier de download

	public DefaultListModel<String> users = new DefaultListModel<String>();	// lista utilizatorilor -> pentru JList
	DefaultListModel<String> files = new DefaultListModel<String>();	// lista fisierelor		-> JList

	
	// lista de utilizatori cu propria lista de fisiere
	public Hashtable<String, DefaultListModel<String>> users_files = new Hashtable<String, DefaultListModel<String>>();

	MockupMediator med;
	//=========================================================================

	public GUI(){	

		DefaultListModel<String> meFiles = new DefaultListModel<String>();
		for(int i = 0; i < 20; i++){
			meFiles.addElement("me_file_nr_" + i);
		}
		users_files.put("me", meFiles);
		users.addElement("me");
		files.addElement(">>> Start by selecting a folder to share!");

		tg = new Transfer_gui(this);
		tg.frame.setVisible(true);

		init_gui();
	}

	
	//=========================================================================
	
	public void setUp(MockupMediator med)
	{
		this.med = med;
	}
	

	
	//=========================================================================

	public void init_gui(){						// temporar	functie de test
		/*				
		for(int i = 0; i < 30; i++){
			if(i % 3 == 0){
				Info_transfer it1 = new Info_transfer("de_la_el_" + i, "catre_mine_" + i, "fisier_important_" + i, "Completed", 100);
				add_transfer(it1);
			}
			if(i % 3 == 1){
				Info_transfer it1 = new Info_transfer("de_la_el_" + i, "catre_mine_" + i, "fisier_important_" + i, "Sending", i * 2);
				add_transfer(it1);	
			}
			if(i % 3 == 2){
				Info_transfer it1 = new Info_transfer("de_la_el_" + i, "catre_mine_" + i, "fisier_important_" + i, "Receiving", i * 3);
				add_transfer(it1);	
			}
		}

		 */

		
		
		
		DefaultListModel<String> lp = new DefaultListModel<String>();
		for(int i = 0; i < 20; i++){
			lp.addElement("fis_pt_" + i);
		}
		add_new_user("Petrut", lp);

		DefaultListModel<String> ld = new DefaultListModel<String>();
		for(int i = 0; i < 20; i++){
			ld.addElement("fis_ds_" + i);
		}
		add_new_user("Dragos", ld);

		/*
		for(int i = 0; i < 30; i++){		
			DefaultListModel<String> ll = new DefaultListModel<String>();
			ll.addElement("file_" + (i + 1));
			ll.addElement("file_" + (i + 2));
			ll.addElement("file_" + (i + 3));
			add_new_user("User_" + i, ll);
		}
		
		*/

		//set_status("de_la_el_29", "catre_mine_29", "fisier_important_29", stat_out);
		//set_progress("de_la_el_29", "catre_mine_29", "fisier_important_29", 11);

		//afis_hash();
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
		
		if(users.contains(name))
		{
			set_notificari("Utilizator " + name + " a schimbat lista de fisiere");
			users_files.put(name, lm);
			
		}
		else
		{
			set_notificari("Utilizator nou " + name +" .");
			users_files.put(name, lm);
			users.addElement(name);
		}
	}
	
	
	public void remove_user(String name){

		set_notificari("Utilizator " + name + " a fost sters." );
		users_files.remove(name);
		for(int i = 0; i < users.size(); i++)
		{
			if(users.get(i).equals(name))
			{
				users.remove(i);
			}
		}
		
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

		Info_transfer it = new Info_transfer(current_user, "me", current_file, stat_in, 0);
		
		med.transferuriNeterminate.add(it);
		tg.tab_transfer.add_new_transfer(it);
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

	// seteaza valoare progress bar, transferului identificat prin sursa - destinatie -fisier
	public void set_progress(String src, String dest, String file, int progress){

		this.tg.tab_transfer.reset_progress(src, dest, file, progress);
	}

	public void set_progress(Info_transfer e){

		this.tg.tab_transfer.reset_progress(e.src, e.dest, e.file_name,e.progress);
		
		if(e.progress > 99)
		{
			e.status = Status.Completed;
			set_status(e.src, e.dest, e.file_name,Status.Completed);
		}
	}
	
	
	// incrementeaza valoare progress bar, transferului identificat prin sursa - destinatie -fisier
	public void increment_progress(String src, String dest, String file){

		this.tg.tab_transfer.increment_progress(src, dest, file);
	}

	// seteaza status transfer (stat_out = Sending / stat_in = Receiving / stat_fin = Completed)
	public void set_status(String src, String dest, String file, String status){

		this.tg.tab_transfer.reset_status(src, dest, file, status);
	}

	
	// seteaza notificari
	
	public void set_notificari(String mesaj)
	{
		tg.label_notif.setText(mesaj);
	}
	
	//=========================================================================

	
	
	
	// nimic
	public void nimic(){}
}

//=========================================================================
