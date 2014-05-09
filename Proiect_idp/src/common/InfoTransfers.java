package common;

import java.io.RandomAccessFile;

import org.apache.log4j.Logger;

// informatii despre transferuri

public class InfoTransfers {
	
	static Logger logger = Logger.getLogger(InfoTransfers.class);
	
	public String src;				// utilizatorul sursa al transferului
	public String dest;			// utilizatorul destinatie
	public String file_name;		// numele fisierului transferat
	public String status;			// starea actuala a transferului
	
	public String private_data;		// camp comunicare cu Server Web
		
	public int progress;			// valoare progress bar (in procente)
	
	public RandomAccessFile raf = null;
	public Integer chunckNr = 0;
	public Integer chunckIndex = 0;
	
	public InfoTransfers(){
		
		this.private_data = "@#$";
	};
	
	public InfoTransfers(String data){
		
		this.private_data = data;
		
		this.src = "ceva";
		this.dest = "ceva";
		this.file_name = "ceva";
		this.status = "ceva";		
		this.progress = 1;
	};
			
	public InfoTransfers(String src, String dest, String file_name, String status, int progress){
		
		this.src = src;
		this.dest = dest;
		this.file_name = file_name;
		this.status = status;
		
		this.progress = progress;
		
		this.private_data = "@#$";
	}
		
	//=========================================================================
	
	// setare caracteristici transfer
	
	public void set_src(String src){
		this.src = src;
		logger.debug("set src -> " + src);
	}
	
	public void set_dest(String dest){
		this.dest = dest;
		logger.debug("set dest -> " + dest);
	}
	
	public void set_file_name(String file_name){
		this.file_name = file_name;
		logger.debug("set file name -> " + file_name);
	}
	
	public void set_status(String status){
		this.status = status;
		logger.debug("set status -> " + status);
	}
	
	public void set_progress(int progress){
		this.progress = progress;
	}
}

//=============================================================================
