package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class InfoUser {
	
	private String user = "me";			/* nume utilizator curent */
	
	private String root_dir_name = "Users_info";
	
	private String config_file_name;	/* cale fisier configurare */
	private String log_file_name;		/* cale fisier logare */
	private String files_dir_name;		/* cale director fisiere de transfer */
	
	static Logger logger = Logger.getLogger(InfoUser.class);
	
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
		
		String rez = getArgumentValue("ip");
		
		logger.debug("get user ip = " + rez);
		
		return rez;
	}

	/*-----------------------------------------------------------------------*/
	
	/* cauta portul utilizatorului curent */
	public String getUserPort() throws IOException{
		
		String rez = getArgumentValue("port");
		
		logger.debug("get user port = " + rez);
		
		return rez;
	}
	
	/*-----------------------------------------------------------------------*/
	
	public String getUser(){
				
		logger.debug("get user = " + user);
		
		return user;
	}
	
	/*-----------------------------------------------------------------------*/
	
	/* intoarce lista de fisiere a utilizatorului - calea absoluta */
	public ArrayList<String> getUserFilesAbsolutePath(){
		
		ArrayList<String> fl = new ArrayList<String>();
		
		File dir = new File(files_dir_name);
		
		for( File fis : dir.listFiles()){
			fl.add(fis.getAbsolutePath());
        }
		
		logger.debug("cere calea absoluta a fisierelor din directorul " + files_dir_name);
        
		return fl;
	}
	
	/* intoarce lista de fisiere a utilizatorului - list fisire*/
	public File[] getUserFiles(){
				
		File dir = new File(files_dir_name);
		
		logger.debug("cere lista fisierelor (FILE) din directorul " + files_dir_name);
		
		return dir.listFiles();
	}	
	
	/*-----------------------------------------------------------------------*/
	
	/* intoarce lista de fisiere a utilizatorului - doar numele lor */
	public ArrayList<String> getUserFilesName(){
		
		ArrayList<String> fl = new ArrayList<String>();
		
		File dir = new File(files_dir_name);
		
		for( File fis : dir.listFiles()){
			fl.add(fis.getName());
        }
		
		logger.debug("cere lista fisierelor (nume) din directorul " + files_dir_name);
        
		return fl;
	}
	
	/*-----------------------------------------------------------------------*/
	
	public static void main(String []args) throws IOException{
		
		InfoUser iu = new InfoUser("radu");
		
		System.out.println("\n> User = " + iu.user + ", log_file_name = " + iu.log_file_name);
		
		System.out.println("> User = " + iu.user + ", IP = " + iu.getUserIP());
		System.out.println("> User = " + iu.user + ", Port = " + iu.getUserPort());
		
		
		ArrayList<String> fis = iu.getUserFilesAbsolutePath();
		
		System.out.println("\n> lista de fisiere(calea absoluta) a utilizatorului < " + iu.user + " >\n");
		
		for(String path : fis){
			System.out.println(path);
		}
		
		
		ArrayList<String> fis2 = iu.getUserFilesName();
		
		System.out.println("\n> lista de fisiere a utilizatorului < " + iu.user + " >\n");
		
		for(String path : fis2){
			System.out.println(path);
		}
	}
}

/*---------------------------------------------------------------------------*/