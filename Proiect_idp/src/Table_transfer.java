import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class Table_transfer extends DefaultTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Table_transfer(){
		super();
		
		this.addColumn("Source");
		this.addColumn("Destination");
		this.addColumn("File Name");
		this.addColumn("Progress");
		this.addColumn("Status");
		
		init_temp();
	}
	
	public boolean isCellEditable(){
		return false;		
	}
	
	public boolean isCellEditable(int x, int y){
		return false;		
	}
	
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
}
