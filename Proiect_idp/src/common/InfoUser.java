package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class InfoUser {
	
	private String user = "me";			/* nume utilizator curent */
	
	private String root_dir_name = "Users_info";
	
	private String config_file_name;	/* cale fisier configurare */
	private String log_file_name;		/* cale fisier logare */
	private String files_dir_name;		/* cale director fisiere de transfer */
	
	public InfoUser(){
		
		config_file_name	= root_dir_name + "/" + user + "/" + user + ".txt";
		log_file_name		= root_dir_name + "/" + user + "/" + user + ".log";
		files_dir_name		= root_dir_name + "/" + user + "/" + user + "_files";
	}
	
	public InfoUser(String user){
		
		this.user = user;
		
		config_file_name	= root_dir_name + "/" + user + "/" + user + ".txt";
		log_file_name		= root_dir_name + "/" + user + "/" + user + ".log";
		files_dir_name		= root_dir_name + "/" + user + "/" + user + "_files";
	}
	
	/*-----------------------------------------------------------------------*/
	
	public String getArgumentValue(String atribut) throws IOException{
		
		String rez = null;
		String temp;
		StringTokenizer st;
		
		FileReader file = new FileReader(config_file_name);
		BufferedReader br = new BufferedReader(file);
		
		String line = br.readLine();
		
		while(line != null){
			
			st = new StringTokenizer(line);
			temp = st.nextToken();
			
			if(temp.equalsIgnoreCase(atribut)){
				
				rez = st.nextToken();
				
				break;
			}
			
			line = br.readLine();			
		}
		
		br.close();
		
		return rez;		
	}
	
	/*-----------------------------------------------------------------------*/
	
	/* cauta adresa utilizatorului curent */
	public String getUserIP() throws IOException{
		
		return getArgumentValue("ip");
	}

	/*-----------------------------------------------------------------------*/
	
	/* cauta portul utilizatorului curent */
	public String getUserPort() throws IOException{
		
		return getArgumentValue("port");
	}
	
	/*-----------------------------------------------------------------------*/
	
	/* cauta lista de fisiere a utilizatorului */
	public ArrayList<String> getUserFileList(){
		
		ArrayList<String> fl = new ArrayList<String>();
		
		File dir = new File(files_dir_name);
		
		for( File fis : dir.listFiles()){
			fl.add(fis.getAbsolutePath());
        }
        
		return fl;
	}
	
	/*-----------------------------------------------------------------------*/
	
	public static void main(String []args) throws IOException{
		
		InfoUser iu = new InfoUser("radu");
		
		System.out.println("\n> User = " + iu.user + ", log_file_name = " + iu.log_file_name);
		
		System.out.println("> User = " + iu.user + ", IP = " + iu.getUserIP());
		
		System.out.println("> User = " + iu.user + ", Port = " + iu.getUserPort());
		
		ArrayList<String> fis = iu.getUserFileList();
		
		System.out.println("\n> lista de fisiere a utilizatorului < " + iu.user + " >\n");
		
		for(String path : fis){
			System.out.println(path);
		}
	}
}

/*---------------------------------------------------------------------------*/