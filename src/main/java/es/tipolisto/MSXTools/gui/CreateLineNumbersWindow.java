package es.tipolisto.MSXTools.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import javax.swing.border.EmptyBorder;

import es.tipolisto.MSXTools.utils.ConvertDeleteComents;
import es.tipolisto.MSXTools.utils.CreateLineNumbers;
import es.tipolisto.MSXTools.utils.FileManager;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class CreateLineNumbersWindow extends JFrame {

	private JPanel contentPane;
	private File fileOrigin;

	public CreateLineNumbersWindow() {
		FileManager fileManager=new FileManager();
		String stringFileDestiny="";
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 583, 541);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//Centramos la ventana en la pantalla
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("data\\icon.png").getImage());
		
		
		JLabel lblNewLabel_2 = new JLabel("Create line numbers");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2.setBounds(10, 25, 414, 14);
		contentPane.add(lblNewLabel_2);
		
		
		JLabel jLabelPathOriginFile = new JLabel("Path origin file");
		jLabelPathOriginFile.setBounds(10, 115, 414, 14);
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
		jButtonOriginFile.setBounds(10, 81, 528, 23);
		contentPane.add(jButtonOriginFile);
		
		
		
		JButton jButtonConvertir = new JButton("Covert");
		jButtonConvertir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileOrigin==null) {
					JOptionPane.showConfirmDialog(null,"Select one file origin");
				}else if(stringFileDestiny.isEmpty()) {
					if (fileManager.checkFile(fileOrigin.getAbsolutePath())) {
						CreateLineNumbers conversor=new CreateLineNumbers();
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
		jButtonConvertir.setBounds(10, 183, 528, 67);
		contentPane.add(jButtonConvertir);
		
		JTextArea txtrPrintholaMundo = new JTextArea();
		txtrPrintholaMundo.setText("print \"hola mundo\"\r\ngoto destino\r\nprint \"hola mundo 2\"\r\n{destino}print \"hola mundo 3\"\r\nprint \"hola mundo 4\"\r\n{destino2}print \"hola mundo 5\"\r\ngoto destino2\r\ngoto destino");
		txtrPrintholaMundo.setBounds(10, 280, 254, 211);
		contentPane.add(txtrPrintholaMundo);
		
		JTextArea txtrprintholaMundo = new JTextArea();
		txtrprintholaMundo.setText("10print \"hola mundo\"\r\n20goto 40\r\n30print \"hola mundo 2\"\r\n40print \"hola mundo 3\"\r\n50print \"hola mundo 4\"\r\n60print \"hola mundo 5\"\r\n70goto 60\r\n80goto 40");
		txtrprintholaMundo.setBounds(296, 280, 242, 211);
		contentPane.add(txtrprintholaMundo);
	}
}
