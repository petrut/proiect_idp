
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class Progress_bar_renderer extends JProgressBar implements TableCellRenderer{
	
	private static final long serialVersionUID = 1L;
	
    public Progress_bar_renderer()
    {
        super();
        
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setOpaque(true);
    }

    // afis progress bar custom
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column)
    {    	
    	JProgressBar pb = (JProgressBar)value;
    	int val = pb.getValue();    	
    	setValue(val);    	
        setBackground(Color.GRAY);
        this.setForeground(Color.BLACK);
        
        return this;
    }
}

