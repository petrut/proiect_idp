
package test;

import java.io.IOException;
import java.util.ArrayList;

import common.InfoTransfers;
import common.InfoUser;
import junit.framework.TestCase;

public class TestCommon extends TestCase{

	InfoUser iu = new InfoUser("gigi");
	InfoTransfers it = new InfoTransfers("sursa", "destinatie", "fisier", "Sending", 11);
	
	public TestCommon(String name) {
		super(name);
	}
			
	public void testUserName(){
		
		assertEquals("testInfoUser", iu.getUser(), "gigi");
	}
	
	public void testUserIP() throws IOException{
		
		assertEquals("testInfoUser", iu.getUserIP(), "localhost");
	}

	public void testUserPort() throws IOException{
		
		assertEquals("testInfoUser", iu.getUserPort(), "7823");
	}
	
	public void testUserFileName() throws IOException{
	
		ArrayList<String> fl = iu.getUserFilesName();
	
		assertEquals("testInfoUser", fl.get(0), "gigi_fis1.txt");
	}	
	
	public void testSetSrc(){
				
		it.set_src("radu");
		
		assertEquals("testInfoTransfer", it.src, "radu");
	}
	
	public void testSetDest(){
				
		it.set_dest("gigi");
		
		assertEquals("testInfoTransfer", it.dest, "gigi");
	}
	
	public void testSetFileName(){
		
		it.set_file_name("fisierul_meu");
		
		assertEquals("testInfoTransfer", it.file_name, "fisierul_meu");
	}

	public void testSetStatus(){
			
		it.set_status("Sending");
		
		assertEquals("testInfoTransfer", it.status, "Sending");
	}

	public void testSetProgress(){
		
		it.set_progress(23);
		
		assertEquals("testInfoTransfer", it.progress, 23);
	}
}