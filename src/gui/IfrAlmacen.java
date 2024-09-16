package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import data.Almacen;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class IfrAlmacen extends JInternalFrame{
	private JTextField txtName;
	private Almacen _almacen = null;
	
	private SimpleDateFormat _SimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	private Timer _timMessage;
	
	/**
	 * Create the frame.
	 */
	public IfrAlmacen() {
		setResizable(true);
		setClosable(true);
		setTitle("Almacen");
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
		
		JButton butSave = new JButton("Guardar");
		butSave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		butSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(_almacen == null)
						_almacen = new Almacen(txtName.getText());
					else
						_almacen.SetName(txtName.getText());
					
					_almacen.Save();
					lblMessage.setText("El Almacen se ha guardado con exito.");
					_timMessage.start();
					_SimpleDateFormat.format(new Date());
					
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		butSave.setBounds(370, 175, 100, 50);
		getContentPane().add(butSave);
		
		_timMessage = new Timer(5000, new ActionListener(){
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 lblMessage.setText("");
				 ((Timer)e.getSource()).stop();
			 }});
	}
	
	public IfrAlmacen(Almacen almacen) {
		this();
		if(almacen == null)
			throw new IllegalArgumentException("El parámetro no puede ser null.");
		txtName.setText(almacen.GetName());
		_almacen = almacen;
	}
}


