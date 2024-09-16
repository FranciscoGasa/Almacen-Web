package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import data.Almacen;
import data.Material;
import gui.FrmMain;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

public class IfrMateriales extends JInternalFrame {
	private JTextField txtName;
	private JTable tabResult;

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws IllegalStateException 
	 */
	public IfrMateriales(FrmMain frm) throws IllegalStateException, SQLException, IOException {
		setTitle("Materiales");
		setBounds(100, 100, 450, 300);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblLabel = new JLabel("Nombre");
		panel.add(lblLabel);
		
		txtName = new JTextField();
		panel.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblcmbAlmacen = new JLabel("Almacen");
		panel.add(lblcmbAlmacen);
		
		JComboBox<Almacen> cmbAlmacen = new JComboBox();
		cmbAlmacen.setEditable(true);
		cmbAlmacen.setModel(new AlmacenListModel(Almacen.Search(null)));
		panel.add(cmbAlmacen);
		
		JButton butSearch = new JButton("Buscar");
		butSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(cmbAlmacen.getSelectedItem() != null)
						tabResult.setModel( 
								new MaterialesTableModel(Material.Search(txtName.getText(), cmbAlmacen.getSelectedItem().toString())));
					else
						tabResult.setModel( 
								new MaterialesTableModel(Material.Search(txtName.getText(), null)));
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
						// Se activa con doble clic sobre una fila.
						 if (e.getClickCount() == 2) {
							 int iRow = ((JTable)e.getSource()).getSelectedRow();
							 Material material = ((MaterialesTableModel)tabResult.getModel()).getData(iRow);
							 if (material != null)
								 frm.ShowInternalFrame(new IfrMaterial(material), 500, 10, 700, 400);
						 }
					} catch (SQLException | IOException ex) {
						JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						
					}
			}
		});
		getContentPane().add(tabResult, BorderLayout.CENTER);
	}
}
