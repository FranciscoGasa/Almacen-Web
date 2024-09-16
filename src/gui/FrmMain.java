package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class FrmMain {

	private JFrame _frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMain window = new FrmMain();
					window._frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public FrmMain() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(
				UIManager.getSystemLookAndFeelClassName());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		_frame = new JFrame();
		_frame.setTitle("Gestor Materiales");
		_frame.setBounds(50, 50, 1250, 600); // Ventana principal
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		_frame.setJMenuBar(menuBar);
		
		JMenu mnuNew = new JMenu("Nuevo");
		mnuNew.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		menuBar.add(mnuNew);
		
		JMenuItem mitNewMaterial = new JMenuItem("Material");
		mitNewMaterial.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mitNewMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ShowInternalFrame(new IfrMaterial(), 10, 10, 900, 500); // Ventana nuevo material
				} catch (IllegalStateException | SQLException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnuNew.add(mitNewMaterial);
		
		JMenuItem mitNewAlmacen = new JMenuItem("Almacen");
		mitNewAlmacen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mitNewAlmacen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowInternalFrame(new IfrAlmacen(), 10, 10, 900, 500); // Ventana nuevo almacen
			}
		});
		mnuNew.add(mitNewAlmacen);
		
		JMenu mnuSearch = new JMenu("Buscar");
		mnuSearch.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		menuBar.add(mnuSearch);
		
		JMenuItem mitSearchMaterial = new JMenuItem("Material");
		mitSearchMaterial.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuSearch.add(mitSearchMaterial);
		
		mitSearchMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ShowInternalFrame(new IfrMateriales(getThis()), 10, 10, 800, 500); // Ventana buscar material
				} catch (IllegalStateException | SQLException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JMenuItem mitSearchAlmacen = new JMenuItem("Almacen");
		mitSearchAlmacen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuSearch.add(mitSearchAlmacen);
		
		mitSearchAlmacen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowInternalFrame(new IfrAlmacenes(getThis()), 10, 10, 800, 500); // Ventana buscar almacen
			}
		});
		
		_frame.getContentPane().setLayout(null);
	}
	
	public void ShowInternalFrame(
		JInternalFrame ifr, int iX, int iY, int iWidth, int iHeight) {
		ifr.setBounds(iX, iY, iWidth, iHeight);
		_frame.getContentPane().add(ifr);
		ifr.setVisible(true);
	}
	
	public FrmMain getThis() { return this; }
}
