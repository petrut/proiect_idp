package gui;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;

import common.IMediator;
import common.InfoTransfers;
import common.InfoUser;


// componenta care se ocupa de partea grafica a programului
public class GuiAPI {

	String stat_in = "Receiving";			// tip status transfer
	String stat_out = "Sending";
	String stat_fin = "Completed";
	
	InfoUser infoUser;

	DefaultListModel<InfoTransfers> list_transfers = new DefaultListModel<InfoTransfers>();

	String my_folder = ".";							// directorul pentru upload

	// zona grafica GuiAPI
	GuiCore tg;												// componenta de desenare
	final JFileChooser file_chooser = new JFileChooser();	// selectare director personal

	// zona de date/actiuni GuiAPI

	public String current_user = "me";				// user pentru download
	public String current_file;						// fisier de download

	/* lista utilizatorilor -> pentru JList */
	public DefaultListModel<String> users = new DefaultListModel<String>();
	
	/* lista fisierelor		-> JList */
	DefaultListModel<String> files = new DefaultListModel<String>();

	
	// lista de utilizatori cu propria lista de fisiere a fiecaruia
	public Hashtable<String, DefaultListModel<String>> users_files = 
			new Hashtable<String, DefaultListModel<String>>();

	IMediator med;
	
	//=========================================================================

	public GuiAPI(){	

		DefaultListModel<String> meFiles = new DefaultListModel<String>();
		for(int i = 0; i < 20; i++){
			meFiles.addElement("me_file_nr_" + i);
		}
		users_files.put("me", meFiles);
		users.addElement("me");
		files.addElement(">>> Start by selecting a folder to share!");

		tg = new GuiCore(this);
		tg.frame.setVisible(true);

		//init_gui();		/* simulare 3 utilizatori */
	}
	
	//=========================================================================
	
	public GuiAPI(InfoUser iu){
		
		this.infoUser = iu;		
		this.current_user = iu.getUser();
	
		ArrayList<String> fis_names = iu.getUserFilesName();
		
		DefaultListModel<String> meFiles = new DefaultListModel<String>();
		
		for(int i = 0; i < fis_names.size(); i++){
			meFiles.addElement(fis_names.get(i));
		}
		users_files.put(current_user, meFiles);
		users.addElement(current_user);
		files.addElement(">>> [ " + current_user.toUpperCase() + 
				" ] you can start by selecting a folder to share!");

		tg = new GuiCore(this);
		tg.frame.setVisible(true);

		//init_gui();		/* simulare 3 utilizatori */
	}
	
	//=========================================================================
	
	public void setUp(IMediator med)
	{
		this.med = med;
	}
		
	//=========================================================================

	public void init_gui(){						// temporar	functie de test
		
		
		InfoUser info1 = new InfoUser("radu");
		InfoUser info2 = new InfoUser("gigi");
		InfoUser info3 = new InfoUser("mihai");
		
		if(!infoUser.getUser().equals("radu")){
			DefaultListModel<String> lp1 = new DefaultListModel<String>();
			ArrayList<String> fis_names1 = info1.getUserFilesName();
			for(String fis : fis_names1){
				lp1.addElement(fis);
			}
			add_new_user(info1.getUser(), lp1);
		}
		
		if(!infoUser.getUser().equals("gigi")){
			DefaultListModel<String> lp2 = new DefaultListModel<String>();
			ArrayList<String> fis_names2 = info2.getUserFilesName();
			for(String fis : fis_names2){
				lp2.addElement(fis);
			}
			add_new_user(info2.getUser(), lp2);
		}
		
		if(!infoUser.getUser().equals("mihai")){
			DefaultListModel<String> lp3 = new DefaultListModel<String>();
			ArrayList<String> fis_names3 = info3.getUserFilesName();
			for(String fis : fis_names3){
				lp3.addElement(fis);
			}
			add_new_user(info3.getUser(), lp3);
		}
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

		InfoTransfers it = new InfoTransfers(current_user, infoUser.getUser(),
				current_file, stat_in, 0);
		
		med.addReceivingTransfer(it);
		
		tg.tab_transfer.add_new_transfer(it);
	}

	//=========================================================================

	// adauga informatiile unui nou transfer initiat
	public void add_transfer(InfoTransfers it){

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

	public void set_progress(InfoTransfers e){

		this.tg.tab_transfer.reset_progress(e.src, e.dest, e.file_name,e.progress);
		
		if(e.progress > 99)
		{
			e.status = Status.Completed;
			set_status(e.src, e.dest, e.file_name,Status.Completed);
		}
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
	
}

//=========================================================================
