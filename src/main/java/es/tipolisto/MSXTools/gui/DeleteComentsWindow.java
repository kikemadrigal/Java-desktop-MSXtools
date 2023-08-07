package es.tipolisto.MSXTools.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.tipolisto.MSXTools.utils.ConvertCSVTSXToBas;
import es.tipolisto.MSXTools.utils.ConvertDeleteComents;
import es.tipolisto.MSXTools.utils.FileManager;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class DeleteComentsWindow extends JFrame {
	private File fileOrigin;
	private String stringFileDestiny;
	private JPanel contentPane;
	private FileManager fileManager;
	private JTextField textFieldFileDestiny;

	public DeleteComentsWindow() {
		fileManager=new FileManager();
		stringFileDestiny="";
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//Centramos la ventana en la pantalla
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("data\\icon.png").getImage());
		
		JLabel lblNewLabel = new JLabel("Delete comments");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(112, 11, 218, 39);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("The comments are the ones that start with 1' or 1 ' , Those containing ^ simbol");
		lblNewLabel_1.setBounds(10, 58, 414, 25);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Los comentarios son los que empiezan por  1' o 1 ', est\u00E1n excluidos los que contienen !");
		lblNewLabel_2.setBounds(10, 82, 414, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel jLabelPathOriginFile = new JLabel("Path origin file");
		jLabelPathOriginFile.setBounds(10, 141, 414, 14);
		contentPane.add(jLabelPathOriginFile);
		
		JButton jButtonOriginFile = new JButton("Origin File");
		jButtonOriginFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir"));
				jFileChooser.setDialogTitle("Selecciona un archivo");
				int result=jFileChooser.showSaveDialog(null);
				if(result==JFileChooser.APPROVE_OPTION) {
					fileOrigin=jFileChooser.getSelectedFile();
					jLabelPathOriginFile.setText(fileOrigin.getPath().toString());
				}
			}
		});
		jButtonOriginFile.setBounds(10, 107, 414, 23);
		contentPane.add(jButtonOriginFile);
		
		
		JButton jButtonConvertir = new JButton("Covert");
		jButtonConvertir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileOrigin==null) {
					JOptionPane.showConfirmDialog(null,"Select one file origin");
				}else if(stringFileDestiny.isEmpty()) {
					if (fileManager.checkFile(fileOrigin.getAbsolutePath())) {
						ConvertDeleteComents conversor=new ConvertDeleteComents();
						conversor.convertNameDestinyLess(fileOrigin);
						JOptionPane.showMessageDialog(null,"Convert!");
					}else {
						JOptionPane.showMessageDialog(null,"There was a problem converting.");
					}			       
				//Si tiene un nombre
				}else {
					File fileDestiny=new File(stringFileDestiny);
					if (fileManager.checkFile(fileOrigin.getAbsolutePath())){
						ConvertDeleteComents conversor=new ConvertDeleteComents();
						conversor.converterWithFileDestiny(fileOrigin, fileDestiny);
						//System.out.println(" origen "+fileOrigin.getAbsolutePath()+" destino "+stringFileDestiny);
						JOptionPane.showMessageDialog(null,"Convert!!");
					}else {
						JOptionPane.showMessageDialog(null,"There was a problem converting.");
					}
				}
			}
		});
		jButtonConvertir.setBounds(10, 223, 414, 67);
		contentPane.add(jButtonConvertir);
		
		textFieldFileDestiny = new JTextField();
		textFieldFileDestiny.setBounds(10, 192, 414, 20);
		contentPane.add(textFieldFileDestiny);
		textFieldFileDestiny.setColumns(10);
		textFieldFileDestiny.getDocument().addDocumentListener(new DocumentListener() {
		  	  public void changedUpdate(DocumentEvent e) {
				  	 
			  }
			  public void removeUpdate(DocumentEvent e) {
				 
			  }
			  public void insertUpdate(DocumentEvent e) {
				  stringFileDestiny=textFieldFileDestiny.getText().toString();
			  }
		});
		
		JLabel lblNewLabel_3 = new JLabel("Enter the new name or leave blank for automatic");
		lblNewLabel_3.setBounds(10, 167, 414, 14);
		contentPane.add(lblNewLabel_3);
	}
}
