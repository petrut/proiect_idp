import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


public class Progress_bar_renderer extends JProgressBar implements TableCellRenderer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    //private final JProgressBar pb;

    public Progress_bar_renderer()
    {
        super();
        /*
        pb = new JProgressBar();

        pb.setStringPainted(true);
        pb.setMinimum(0);
        pb.setMaximum(100);

        pb.setBorderPainted(true);
        pb.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pb.setOpaque(true);
        */
        
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column)
    {
    	
    	JProgressBar pb = (JProgressBar)value;
    	int val = pb.getValue();   	
    	
    	setValue(val);
    	
        setBackground(Color.BLUE);
        
        return this;
    }
}

