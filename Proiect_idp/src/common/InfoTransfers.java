package common;

import java.io.RandomAccessFile;

// informatii despre transferuri

public class InfoTransfers {
	
	public String src;				// utilizatorul sursa al transferului
	public String dest;			// utilizatorul destinatie
	public String file_name;		// numele fisierului transferat
	public String status;			// starea actuala a transferului 
	
	public int progress;			// valoare progress bar (in procente)
	
	public RandomAccessFile raf = null;
	public Integer chunckNr = 0;
	public Integer chunckIndex = 0;
	
	public InfoTransfers(String src, String dest, String file_name, String status, int progress){
		
		this.src = src;
		this.dest = dest;
		this.file_name = file_name;
		this.status = status;
		
		this.progress = progress;
	}
		
	//=========================================================================
	
	// setare caracteristici transfer
	
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
