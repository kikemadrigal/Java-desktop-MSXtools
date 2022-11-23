package es.tipolisto.MSXTools.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import es.tipolisto.MSXTools.utils.CovertCSVTSXToASM;
import es.tipolisto.MSXTools.utils.FileManager;

import javax.swing.JComboBox;

public class CSVTSXToASMWindow extends JFrame {

	private File fileOrigin;
	private File fileDestiny;
	private FileManager fileManager;
	private JPanel contentPane;
	private JTextField textFieldRAMDirection;



	/**
	 * Create the frame.
	 */
	public CSVTSXToASMWindow() {
		fileManager=new FileManager();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 463, 441);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//Centramos la ventana en la pantalla
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("data\\icon.png").getImage());
		
		JLabel lblNewLabel = new JLabel("CSV / TMX to ASM");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(164, 24, 168, 29);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_3 = new JLabel("Select your assembler");
		lblNewLabel_3.setBounds(10, 65, 189, 14);
		contentPane.add(lblNewLabel_3);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(10, 102, 168, 22);
		comboBox.addItem("Sjasm");
		comboBox.addItem("asMSX");
		comboBox.addItem("Pasmo");
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_4 = new JLabel("Type");
		lblNewLabel_4.setBounds(217, 64, 46, 14);
		contentPane.add(lblNewLabel_4);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(218, 102, 168, 22);
		comboBox_1.addItem("TMX");
		comboBox_1.addItem("CSV");
		contentPane.add(comboBox_1);
		

		
		
		JLabel lblNewLabel_1 = new JLabel("Select file origin");
		lblNewLabel_1.setBounds(10, 207, 427, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel jLabelPathFileOrigin = new JLabel("");
		jLabelPathFileOrigin.setBounds(10, 244, 427, 14);
		contentPane.add(jLabelPathFileOrigin);
		
		
		JLabel jLabelDestinyFile = new JLabel("");
		jLabelDestinyFile.setBounds(10, 309, 427, 14);
		contentPane.add(jLabelDestinyFile);
		
		JButton jButtonSelectFileOrigin = new JButton("File origin");
		jButtonSelectFileOrigin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir"));
					jFileChooser.setDialogTitle("Selecciona un archivo");
					int result=jFileChooser.showSaveDialog(null);
					if(result==JFileChooser.APPROVE_OPTION) {
						fileOrigin=jFileChooser.getSelectedFile();
						String fileDestinyPathParent=fileOrigin.getParent();
						String fileDestinyName=fileOrigin.getName().substring(0,fileOrigin.getName().length()-4)+".asm";
						fileDestinyPathParent=fileDestinyPathParent+"\\"+fileDestinyName;
						jLabelPathFileOrigin.setText(fileOrigin.getPath().toString());
						fileDestiny=new File(fileDestinyPathParent);
						jLabelDestinyFile.setText(fileDestinyPathParent);
					}
				
			}
		});
		jButtonSelectFileOrigin.setBounds(10, 269, 427, 29);
		contentPane.add(jButtonSelectFileOrigin);
		
		JButton btnNewButton = new JButton("Covert!!");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				convert();
			}
		});
		btnNewButton.setBounds(10, 334, 427, 57);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("RAM direction");
		lblNewLabel_2.setBounds(10, 135, 118, 14);
		contentPane.add(lblNewLabel_2);
		
		textFieldRAMDirection = new JTextField();
		textFieldRAMDirection.setText("53248");
		textFieldRAMDirection.setBounds(10, 160, 189, 20);
		contentPane.add(textFieldRAMDirection);
		textFieldRAMDirection.setColumns(10);
		
		JLabel labelRAMDirection = new JLabel("D000");
		labelRAMDirection.setBounds(217, 163, 220, 14);
		contentPane.add(labelRAMDirection);
		

	}
	private void convert() {
		if(fileOrigin==null || fileOrigin.getName()=="") {
			JOptionPane.showMessageDialog(null, "Tienes que seleccionar un archivo"); 
		}else {
			try {
				//fileDestiny=new File(fileOrigin.getPath());
				String fileDestinyName=fileOrigin.getName().substring(0,fileOrigin.getName().length()-4)+".asm";
				fileDestiny=new File(fileDestinyName);
				//Comprobamos el tipo de archivo
				String typeFile=fileOrigin.getName().substring(fileOrigin.getName().length()-3,fileOrigin.getName().length());
				if(typeFile.equalsIgnoreCase("TMX") || typeFile.equalsIgnoreCase("CSV")) {
					CovertCSVTSXToASM conversor=new CovertCSVTSXToASM();
					//Le indicamos que es TMX(boolean=true)
					ArrayList<String> arrayList=conversor.extractASMDataTMXSjasm(fileOrigin,true);
					conversor.writeASMDataToFile(fileDestiny,arrayList);
					/*for(String data:arrayList) {
						System.out.println(data);
					}*/
					JOptionPane.showMessageDialog(null, "Convertido"); 
				}else {
					JOptionPane.showMessageDialog(null, "Tipo de archivo no permitido "+ typeFile); 
				}
				
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage()); 
			}
			
		}
	}
}
