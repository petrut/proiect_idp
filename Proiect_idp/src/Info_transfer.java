
public class Info_transfer {

	int id_transfer;		// identificator transfer
	
	String src;				// utilizatorul sursa al transferului
	String dest;			// utilizatorul destinatie
	String file_name;		// numele fisierului transferat
	String status;			// starea actuala a transferului 
	
	int progress;			// valoare progress bar (in procente)
	
	public Info_transfer(){		
	}
	
	public Info_transfer(String src, String dest, String file_name, String status, int progress){
		
		this.src = src;
		this.dest = dest;
		this.file_name = file_name;
		this.status = status;
		
		this.progress = progress;
	}
	
	public Info_transfer(int id_transfer, String src, String dest, String file_name, String status, int progress){
		
		this.id_transfer = id_transfer;
		
		this.src = src;
		this.dest = dest;
		this.file_name = file_name;
		this.status = status;
		
		this.progress = progress;
	}
	
	//=========================================================================
	
	public void set_id_transfer(int id){
		this.id_transfer = id;
	}
	
	public void set_src(String src){
		this.src = src;
	}
	
	public void set_dest(String dest){
		this.dest = dest;
	}
	
	public void set_file_name(String file_name){
		this.file_name = file_name;		
	}
	
	public void set_status(String status){
		this.status = status;
	}
	
	public void set_progress(int progress){
		this.progress = progress;
	}
}

//=============================================================================
