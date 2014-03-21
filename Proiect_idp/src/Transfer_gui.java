import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

// clasa interfata grafica

public class Transfer_gui {

	GUI transfer_gg;
	JFrame frame;
	private JTable table;
	
	JLabel label_notif;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Transfer_gui window = new Transfer_gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Transfer_gui() {
		initialize();
	}

	public Transfer_gui(GUI gg) {
		this.transfer_gg = gg;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Intense traffic ...");
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		//=====================================================================
		
		// lista de fisiere
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		final JList<String> list_files = new JList<String>(transfer_gg.files);
		
		// la dublu click pe files initializeaza transfer fisiere
		list_files.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					
					String selected_file = (list_files.getSelectedValue());
					
					transfer_gg.crrent_file = selected_file;
					transfer_gg.start_download(selected_file);					
				           				    
				    label_notif.setText("Start download file <<< " + selected_file + " >>>");				    
				}
			}
		});		
		
		JScrollPane scrollPane_1 = new JScrollPane(list_files);
		panel_3.add(scrollPane_1, BorderLayout.NORTH);
		
		//=====================================================================
		
		// tabel stare transfer
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		DefaultTableModel model = new DefaultTableModel();
		
		model.addColumn("Source");
		model.addColumn("Destination");
		model.addColumn("File Name");
		model.addColumn("Progress");
		model.addColumn("Status");
				
		Table_transfer tt = new Table_transfer(this.transfer_gg);
				
		table = new JTable(tt);
		
		table.getColumnModel().getColumn(3).setCellRenderer(new Progress_bar_renderer());
		
		JScrollPane scrollPane_2 = new JScrollPane(table);
		panel_4.add(scrollPane_2, BorderLayout.NORTH);
		
		//=====================================================================
		
		// actiuni asupra tabelului de transfer
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnClearSelect = new JButton("Clear select");
		panel_5.add(btnClearSelect);
		
		JButton btnClearAllFinished = new JButton("Clear all finished");
		panel_5.add(btnClearAllFinished);
		
		JButton btnCancelTransfer = new JButton("Cancel transfer");
		panel_5.add(btnCancelTransfer);
		
		//=====================================================================
		
		// lista utilizatori
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JButton btnSelectMyFolder = new JButton("Select my folder");
		panel_2.add(btnSelectMyFolder, BorderLayout.NORTH);
		
		final JList<String> list_users = new JList<String>(transfer_gg.users);
		
		// la dublu click pe users reset lista files
		list_users.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() == 2) {
		        	
		           String selected_user = (list_users.getSelectedValue());
		           
		           transfer_gg.current_user = selected_user;
		           transfer_gg.user_selected(selected_user);
		           
		           label_notif.setText("Switch user to <<< " + selected_user + " >>>");
		        }
		    }
		});
		
		JScrollPane scrollPane = new JScrollPane(list_users);
		panel_2.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_6 = new JPanel();
		panel.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		// notificari
		
		label_notif = new JLabel("Notification ...");
		panel_6.add(label_notif, BorderLayout.NORTH);
				
		//=====================================================================
		
	}

}
