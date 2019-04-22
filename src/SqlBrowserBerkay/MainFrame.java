package SqlBrowserBerkay;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import java.awt.color.CMMException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JButton btnRun;
	private JScrollPane scrollPane_1;
	private JTable table;
	private JComboBox<String> comboBox;
	private JPanel panel_3;
	private JTextField txtServer;
	private JLabel lblServer;
	private JLabel lblUser;
	private JTextField txtUser;
	private JLabel lblPass;
	private JButton btnConnect;
	private boolean connected = false;
	FlexibleTableModel myModel;
	private JPasswordField txtPass;
	private JPanel panel_1;
	private JTextField txtFilter;
	private JButton btnFilter;
	TableRowSorter<FlexibleTableModel> sorter;
	private JPanel panel_2;
	private JSplitPane splitPane;
	private JScrollPane scrollPane;
	private JTextArea txtQuery;
	private JButton btnDbs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		initGUI();
	}
	private void initGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				do_this_windowClosed(e);
			}
		});
		
		setTitle("MySql Query Tool");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1101, 586);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel_2.add(splitPane, BorderLayout.CENTER);
		
		scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		
		scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		txtQuery = new JTextArea();
		scrollPane.setViewportView(txtQuery);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(panel_3, BorderLayout.NORTH);
		
		lblServer = new JLabel("Server:");
		panel_3.add(lblServer);
		
		txtServer = new JTextField();
		panel_3.add(txtServer);
		txtServer.setColumns(10);
		
		lblUser = new JLabel("User:");
		panel_3.add(lblUser);
		
		txtUser = new JTextField();
		panel_3.add(txtUser);
		txtUser.setColumns(10);
		
		lblPass = new JLabel("Pass:");
		panel_3.add(lblPass);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnConnect_actionPerformed(e);
			}
		});
		
		txtPass = new JPasswordField();
		txtPass.setColumns(10);
		panel_3.add(txtPass);
		panel_3.add(btnConnect);
		
		comboBox = new JComboBox();
		comboBox.setEnabled(false);
		
		panel_3.add(comboBox);
		
		btnDbs = new JButton("Tables");
		btnDbs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnDbs_actionPerformed(e);
			}
		});
		btnDbs.setEnabled(false);
		panel_3.add(btnDbs);
		
		btnRun = new JButton("Run");
		btnRun.setEnabled(false);
		panel_3.add(btnRun);
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnRun_actionPerformed(e);
			}
		});
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		txtFilter = new JTextField();
		panel_1.add(txtFilter);
		txtFilter.setColumns(20);
		
		btnFilter = new JButton("Filter");
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnFilter_actionPerformed(e);
			}
		});
		panel_1.add(btnFilter);
	}

	protected void do_btnRun_actionPerformed(ActionEvent e) {
		
		if(txtQuery.getText().length()==0) {
			JOptionPane.showMessageDialog(this, "Please provide query!");
			return;
					
		}
		String query = "";
		if(txtQuery.getSelectedText()!=null) {
			query = txtQuery.getSelectedText();
		}else query=txtQuery.getText();
		
		if(query.startsWith("select")) {
			
			try {
				myModel = new FlexibleTableModel(txtServer.getText(),comboBox.getSelectedItem().toString(), 
						txtUser.getText(), txtPass.getText(), query);
				table.setModel(myModel);
				
				sorter = new TableRowSorter<>(myModel);
				table.setRowSorter(sorter);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}
			
			
		}else {
			
			try {
				int result = DbFunctions.updateQuery(txtServer.getText(),comboBox.getSelectedItem().toString(), txtPass.getText(), txtUser.getText(), txtQuery.getText());
				JOptionPane.showMessageDialog(this, result +  " rows effected.");
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, e2.getMessage());
			}
			
			
			
		}

		
	}
	
	protected void do_btnConnect_actionPerformed(ActionEvent e) {
		System.out.println("Connected:" + connected);
		if(!connected) {
			if(txtServer.getText().length()!=0 || txtPass.getText().length()!=0 || txtUser.getText().length()!=0) {
				
				List<String> dbs;
				try {
					dbs = DbFunctions.getAllDbs(txtServer.getText(), txtPass.getText(), txtUser.getText());
					DefaultComboBoxModel<String> mdl = new DefaultComboBoxModel<>(dbs.toArray(new String[dbs.size()]));
					comboBox.setModel(mdl);
					JOptionPane.showMessageDialog(this, "Connected!");
					comboBox.setEnabled(true);
					btnRun.setEnabled(true);
					btnConnect.setText("Disconnect");
					btnDbs.setEnabled(true);
					connected = true;
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
					e1.printStackTrace();
				}
			}else {
				JOptionPane.showMessageDialog(this, "Please provide required info for connecting!");
			}
			
			
		}else {
			if(myModel!=null) {
				System.out.println("Disconnecting..");
				myModel.disconnect();
				table.setModel(new DefaultTableModel());
				
			}
			comboBox.setEnabled(false);
			btnDbs.setEnabled(false);
			btnRun.setEnabled(false);
			comboBox.setModel(new DefaultComboBoxModel<>());
			txtPass.setText("");
			txtServer.setText("");
			txtUser.setText("");
			connected = false;
			btnConnect.setText("Connect");
			JOptionPane.showMessageDialog(this, "Disconnected!");
			
		}
		
		
		
	}
	protected void do_this_windowClosed(WindowEvent e) {
		if(myModel!=null) {
			myModel.disconnect();
			
		}
	}
	protected void do_btnFilter_actionPerformed(ActionEvent e) {
		
		if(sorter!=null) {
			if(txtFilter.getText().length()==0) {
				sorter.setRowFilter(null);
			}else {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" +txtFilter.getText()));
			}
		}
		
		
	}
	protected void do_btnDbs_actionPerformed(ActionEvent e) {
		
		try {
			List<String> tables = DbFunctions.getAllTables(txtServer.getText(),comboBox.getSelectedItem().toString(), txtPass.getText(), txtUser.getText());
			
			tables.forEach(s->{
				txtQuery.append("\n" + "select * from " + s + ";");
			});
		
		
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage());
		}
		
	}
}
