
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

// tabel evolutie transferuri

public class Table_transfer extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;

	GUI tab_gg;
	
	public Table_transfer(){
		super();
		
		this.addColumn("Source");
		this.addColumn("Destination");
		this.addColumn("File Name");
		this.addColumn("Progress");
		this.addColumn("Status");
		
		init_temp();
	}
	
	public Table_transfer(GUI gg){
		super();
		
		tab_gg = gg;
		
		this.addColumn("Source");
		this.addColumn("Destination");
		this.addColumn("File Name");
		this.addColumn("Progress");
		this.addColumn("Status");
		
		init_temp();
	}
	
	//=========================================================================
	// elementele tabelului nu pot fi editate
	
	public boolean isCellEditable(){
		return false;		
	}
	
	public boolean isCellEditable(int x, int y){
		return false;		
	}
	
	//=========================================================================
	
	// initializare temporara tabel			// va fi eliminata
	
	public void init_temp(){
		
		Object []temp = new Object[5];
		JProgressBar pb = new JProgressBar();
	
		pb.setValue(55);
		
		temp[0] = "from";
		temp[1] = "to";
		temp[2] = "file";
		temp[3] = pb;
		temp[4] = "in progress";
				
		this.addRow(temp);		
	}
	
	public void add_new_transfer(Info_transfer it){
		
		Object []temp = new Object[5];
		JProgressBar pb = new JProgressBar();
	
		pb.setValue(it.progress);
		
		temp[0] = it.src;
		temp[1] = it.dest;
		temp[2] = it.file_name;
		temp[3] = pb;
		temp[4] = it.status;
				
		this.addRow(temp);		
	}
	
	public void refresh_transfer_list(){
	}
	
	//=========================================================================
}
