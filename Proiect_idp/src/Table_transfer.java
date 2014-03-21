
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

// tabel evolutie transferuri

public class Table_transfer extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;
	
	GUI tab_gg;			// referinta la obiectul central GUI
		
	public Table_transfer(GUI gg){
		
		super();
		tab_gg = gg;
		
		this.addColumn("Source");
		this.addColumn("Destination");
		this.addColumn("File Name");
		this.addColumn("Progress");
		this.addColumn("Status");
	}
	
	//=========================================================================
	// elementele tabelului nu pot fi editate (cerinta tema)
	
	public boolean isCellEditable(){
		return false;		
	}
	
	public boolean isCellEditable(int x, int y){
		return false;		
	}
	
	//=========================================================================
		
	// adauga informatiile desre noul transfer in tabel
	public void add_new_transfer(Info_transfer it){
		
		tab_gg.list_transfers.addElement(it);
		
		Object []temp = new Object[5];
		JProgressBar pb = new JProgressBar();
	
		pb.setValue(it.progress);
		
		temp[0] = it.src;
		temp[1] = it.dest;
		temp[2] = it.file_name;
		temp[3] = pb;
		temp[4] = it.status;
				
		//this.addRow(temp);					// adauga transfer la finalul tabelului
		this.insertRow(0, temp);				// adauga transfer la inceputul tabelului
	}
	
	//=========================================================================
	
	// reseteaza valoare progres_bar
	public void reset_progress(String src, String dest, String file, int progress){
		
		for(int i = 0; i < this.getRowCount(); i++){
			
			if(this.getValueAt(i, 0).equals(src) && this.getValueAt(i, 1).equals(dest) 
					&& this.getValueAt(i, 2).equals(file) ){
								
				JProgressBar pb = (JProgressBar)this.getValueAt(i, 3);
				pb.setValue(progress);				
				this.setValueAt(pb, i, 3);	
				
				break;
			}
		}
	}
	
	// reseteaza status
	public void reset_status(String src, String dest, String file, String status){
		
		for(int i = 0; i < this.getRowCount(); i++){
			
			if(this.getValueAt(i, 0).equals(src) && this.getValueAt(i, 1).equals(dest) 
					&& this.getValueAt(i, 2).equals(file) ){
				
				this.setValueAt(status, i, 4);
				break;
			}
		}
	}
	
	// reactualizeaza lista de transferuri
	public void refresh_transfer_list(){
		
	}
	
	//=========================================================================
}
