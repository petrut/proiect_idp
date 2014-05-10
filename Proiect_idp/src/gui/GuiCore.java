package gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.ScrollPaneConstants;
import org.apache.log4j.Logger;
import common.InfoTransfers;
import common.InfoUser;
import common.Main;
import common.Mediator;


// clasa interfata grafica

public class GuiCore {

	static GuiAPI transfer_gg;
	JFrame frame;
	private JTable table;
	
	JLabel label_notif;
	FileTransfers tab_transfer;
	final JList<String> list_users;			// lista utilizatori
	final JList<String> list_files;			// lista fisiere utilizator selectat

	static Logger logger = Logger.getLogger(Main.class);
	
	public GuiCore(GuiAPI gg) {
		
		transfer_gg = gg;
		
		list_users = new JList<String>(transfer_gg.users);
		list_files = new JList<String>(transfer_gg.files);
		
		initialize();
	}
	
	//=====================================================================

	public static void do_on_exit() throws NumberFormatException, IOException{
		
		System.out.println("\n> Aplicatia se inchide!\n");
		
		InfoUser info_web = new InfoUser("web_server");
		
		String name = ((Mediator)(transfer_gg.med)).guiAPI.current_user;
		
		String ip = info_web.getUserIP();
		int port = Integer.parseInt(info_web.getUserPort());
		
		((Mediator)(transfer_gg.med)).network.retrieveInfo(9, name, ip, port);
		
		System.exit(0);
	}
	
	// initializare componente
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Intense traffic ...");
		
		frame.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent event) {
	            try {
					do_on_exit();
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
		
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
				
		// la dublu click pe files initializeaza transfer fisiere
		list_files.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					
					String selected_file = (list_files.getSelectedValue());
					
					logger.warn("transfer_gg = " +  transfer_gg.infoUser.getUser());
					
					if(! transfer_gg.current_user.equals(transfer_gg.infoUser.getUser())){

						transfer_gg.current_file = selected_file;
						transfer_gg.start_download(selected_file);					
					           				    
					    label_notif.setText("Start download file <<< " + selected_file + " >>>");
					}
					else{
						label_notif.setText("Own file.");
					}
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
				
		tab_transfer = new FileTransfers(GuiCore.transfer_gg);
				
		table = new JTable(tab_transfer);
		
		table.getColumnModel().getColumn(3).setCellRenderer(new ProgressBarRenderer());
		
		JScrollPane scrollPane_2 = new JScrollPane(table);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setPreferredSize(new Dimension(300, 290));
		panel_4.add(scrollPane_2, BorderLayout.NORTH);
		
		//=====================================================================
		
		// actiuni asupra tabelului de transfer
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnClearSelect = new JButton("Clear select completed");
		btnClearSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int sel = table.getSelectedRow();
				if(sel != -1){
					if(tab_transfer.getValueAt(sel, 4).toString().equals(transfer_gg.stat_fin)){						
						
						for(int i = 0; i < transfer_gg.list_transfers.size(); i++){
							
							if(transfer_gg.list_transfers.elementAt(i).src.equals(tab_transfer.getValueAt(sel, 1)) &&
									transfer_gg.list_transfers.elementAt(i).dest.equals(tab_transfer.getValueAt(sel, 2)) &&
									transfer_gg.list_transfers.elementAt(i).file_name.equals(tab_transfer.getValueAt(sel, 3))){
								
								InfoTransfers  it  = transfer_gg.list_transfers.get(i);
								transfer_gg.med.removeTransfer(it);
								transfer_gg.list_transfers.remove(i);
								break;
							}
						}
						tab_transfer.removeRow(sel);
					}
				}
			}
		});
		panel_5.add(btnClearSelect);
		
		JButton btnClearAllFinished = new JButton("Clear all finished");
		btnClearAllFinished.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(int i = 0; i < tab_transfer.getRowCount(); i++){
					if(tab_transfer.getValueAt(i, 4).equals(transfer_gg.stat_fin)){
						tab_transfer.removeRow(i);
					}
				}
			}
		});
		//panel_5.add(btnClearAllFinished);
		
		JButton btnCancelTransfer = new JButton("Cancel transfer");
		btnCancelTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int sel = table.getSelectedRow();
				if(sel != -1)
					tab_transfer.removeRow(sel);
			}
		});
		panel_5.add(btnCancelTransfer);
		
		//=====================================================================
		
		// lista utilizatori
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JButton btnSelectMyFolder = new JButton("Refresh my folder");
		btnSelectMyFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//list_users.setSelectedIndex(0);
				
				String temp = "Refresh files folder ...";
			    label_notif.setText(temp);
			    
			    transfer_gg.my_folder = temp;
			    	
			    try {
					transfer_gg.reset_files_user();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			    				    	
			    Mediator sel_med = (Mediator) transfer_gg.med;
			    try {
					sel_med.resetNameBuffer();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    
			}
		});
		panel_2.add(btnSelectMyFolder, BorderLayout.NORTH);
				
		// la dublu click pe users reset lista files
		list_users.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() == 2) {
		        	
		           String selected_user = (list_users.getSelectedValue());
		           
		           transfer_gg.current_user = selected_user;
		           transfer_gg.switch_user(selected_user);
		           
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
