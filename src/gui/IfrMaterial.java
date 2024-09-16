package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import data.Almacen;
import data.Material;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;

public class IfrMaterial extends JInternalFrame {
	private JTextField txtName;
	private JTextField txtDescription;
	private JTextField txtStock;
	private JComboBox<Almacen> cmbAlmacen;
	private Material _material = null;
	
	private SimpleDateFormat _SimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	private Timer _timMessage;
	
	public IfrMaterial(Material material) throws IllegalStateException, SQLException, IOException {
		this();
		if(material == null)
			throw new IllegalArgumentException("El parámetro no puede ser null.");
		cmbAlmacen.getModel().setSelectedItem(material.GetAlmacen());
		txtName.setText(material.GetName());
		txtDescription.setText(material.GetDescription());
		txtStock.setText(String.valueOf(material.GetStock()));
		_material = material;
	}
	
	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws IllegalStateException 
	 */
	public IfrMaterial() throws IllegalStateException, SQLException, IOException {
		setResizable(true);
		setClosable(true);
		setTitle("Material");
		setBounds(100, 100, 878, 494);
		getContentPane().setLayout(null);
		
		JLabel lblMessage = new JLabel("");
		lblMessage.setForeground(new Color(0, 128, 255));
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setBounds(225, 10, 400, 13);
		getContentPane().add(lblMessage);
		
		JLabel lblName = new JLabel("Nombre");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(350, 55, 100, 13);
		getContentPane().add(lblName);
		
		txtName = new JTextField();
		txtName.setBackground(new Color(255, 255, 255));
		txtName.setBounds(350, 75, 150, 20);
		getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JLabel lblDescrition = new JLabel("Descripción");
		lblDescrition.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDescrition.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescrition.setBounds(350, 130, 150, 13);
		getContentPane().add(lblDescrition);
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStock.setBounds(350, 191, 100, 13);
		getContentPane().add(lblStock);
		
		txtDescription = new JTextField();
		txtDescription.setBounds(350, 150, 150, 19);
		getContentPane().add(txtDescription);
		txtDescription.setColumns(10);
		
		txtStock = new JTextField();
		txtStock.setBounds(350, 203, 150, 20);
		getContentPane().add(txtStock);
		txtStock.setColumns(10);
		
		cmbAlmacen = new JComboBox<Almacen>();
		cmbAlmacen.setBounds(350, 255, 150, 21);
		getContentPane().add(cmbAlmacen);
		cmbAlmacen.setModel(new AlmacenListModel(Almacen.Search(null)));
		
		JButton butSave = new JButton("Guardar");
		butSave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		butSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(cmbAlmacen.getModel().getSelectedItem() == null)
						lblMessage.setText("El campo almacen no puede estar vacio");
					else {
						if(_material == null) 
							_material = new Material((Almacen)cmbAlmacen.getModel().getSelectedItem(), txtName.getText(),
													 txtDescription.getText(), Integer.parseInt(txtStock.getText()));
						else {
							_material.SetAlmacen((Almacen)cmbAlmacen.getModel().getSelectedItem());
							_material.SetName(txtName.getText());
							_material.SetDescription(txtDescription.getText());
							_material.SetStock(Integer.parseInt(txtStock.getText()));
						}
						_material.Save();
						lblMessage.setText("El material se ha guardado con exito.");
						_timMessage.start();
						_SimpleDateFormat.format(new Date());
					}
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		butSave.setBounds(370, 313, 100, 50);
		getContentPane().add(butSave);
		
		JLabel lblAlmacen = new JLabel("Almacen");
		lblAlmacen.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAlmacen.setBounds(350, 233, 100, 13);
		getContentPane().add(lblAlmacen);
		
		_timMessage = new Timer(5000, new ActionListener(){s
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 lblMessage.setText("");
				 ((Timer)e.getSource()).stop();
			 }});
	}
}
