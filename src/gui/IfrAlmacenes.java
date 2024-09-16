package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import data.Almacen;
import gui.FrmMain;

public class IfrAlmacenes extends JInternalFrame{

	private JTextField txtName;
	private JTable tabResult;

	/**
	 * Create the frame.
	 */
	public IfrAlmacenes(FrmMain frm) {
		setTitle("Almacenes");
		setBounds(100, 100, 450, 300);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblLabel = new JLabel("Nombre");
		panel.add(lblLabel);
		
		txtName = new JTextField();
		panel.add(txtName);
		txtName.setColumns(10);
		
		JButton butSearch = new JButton("Buscar");
		butSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tabResult.setModel( 
						new AlmacenesTableModel(Almacen.Search(txtName.getText())));
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.add(butSearch);
		
		tabResult = new JTable();
		tabResult.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					try {
						// Se activa con doble clic sobre una fila
						 if (e.getClickCount() == 2) {
							 int iRow = ((JTable)e.getSource()).getSelectedRow();
							 Almacen almacen = ((AlmacenesTableModel)tabResult.getModel()).getData(iRow);
							 if (almacen != null)
								 frm.ShowInternalFrame(new IfrAlmacen(almacen), 500, 10, 700, 400);
						 }
					} catch (SQLException | IOException ex) {
						JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						
					}
			}
		});
		getContentPane().add(tabResult, BorderLayout.CENTER);
	}

}
