package es.tipolisto.MSXTools.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import es.tipolisto.MSXTools.utils.ConvertCSVTSXToBas;




public class CSVTSXToBasWindow extends JFrame {
	private JPanel contentPane;
	private File fileOrigin;
	private JButton btnSeleccionaElArchivo, btnConvertir;
	private JLabel labelLocalizacion;
	private JTextField jTextFieldLine;


	public CSVTSXToBasWindow() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setTitle("MSX Murcia");
		//Centramos la ventana en la pantalla
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("data\\icon.png").getImage());
		
		inicializar();
		
		
		//Events
		btnSeleccionaElArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir"));
				jFileChooser.setDialogTitle("Selecciona un archivo");
				int result=jFileChooser.showSaveDialog(null);
				if(result==JFileChooser.APPROVE_OPTION) {
					fileOrigin=jFileChooser.getSelectedFile();
					labelLocalizacion.setText(fileOrigin.getPath().toString());
				}
			}
		});
		
		btnConvertir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				convert();
			}
		});
		
	}
	
	private void inicializar() {
		/***
		 * BorderLayout.NORTH
		 */
		contentPane.setLayout(null);
		JLabel lblAsmToBas = new JLabel("CSV TO BAS");
		lblAsmToBas.setBounds(5, 5, 424, 14);
		lblAsmToBas.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblAsmToBas);

		/***
		 * BorderLayout.WEST
		 */
		btnConvertir = new JButton("Convertir");
		btnConvertir.setBounds(5, 194, 424, 56);
		contentPane.add(btnConvertir);
		/***
		 * Final JPanelCentral
		 */
		
		
		/**
		 * BorderLayout.SOUTH->JPanel abajo
		 */
		JPanel jPanelAbajo=new JPanel();
		jPanelAbajo.setBounds(5, 93, 424, 24);
		jPanelAbajo.setLayout(new FlowLayout());
		labelLocalizacion = new JLabel("Localizacion");
		labelLocalizacion.setHorizontalAlignment(SwingConstants.CENTER);
		jPanelAbajo.add(labelLocalizacion);	
		contentPane.add(jPanelAbajo);
		btnConvertir.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnSeleccionaElArchivo = new JButton("Selecciona el archivo");
		btnSeleccionaElArchivo.setBounds(5, 41, 424, 41);
		contentPane.add(btnSeleccionaElArchivo);
		JLabel labelNumeroLinea = new JLabel("La linea comienza en:");
		labelNumeroLinea.setBounds(10, 140, 102, 14);
		contentPane.add(labelNumeroLinea);
		
		jTextFieldLine=new JTextField("15000");
		jTextFieldLine.setBounds(124, 134, 77, 20);
		contentPane.add(jTextFieldLine);
		jTextFieldLine.setHorizontalAlignment(SwingConstants.LEFT);
		/**
		 * Final JPanelAbajo
		 */
	}
	
	
	
	
	
	
	private void convert() {
		//int lineCounter=Constants.contadorLinea;
		int lineCounter=0;
		try {
			lineCounter=Integer.parseInt(jTextFieldLine.getText().toString());
			System.out.println("El contador de linea es "+lineCounter);
			ConvertCSVTSXToBas convert=new ConvertCSVTSXToBas();
			if (fileOrigin==null)
				JOptionPane.showMessageDialog(null, "select a file");
			else {
				convert.covertToBas(fileOrigin, lineCounter);
				String newFileName=fileOrigin.getAbsolutePath();
		    	newFileName=newFileName.substring(0,newFileName.length()-4)+"-tobas.txt";
				JOptionPane.showMessageDialog(null, "File created "+newFileName);
			}
				
		}catch(Exception ex) {
			System.out.println("El contador de linea en catch es "+lineCounter);
			//JOptionPane.showMessageDialog(null, "Error: "+ex.getStackTrace().toString()); 
		}
	}

}
