package es.tipolisto.MSXTools.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Caret;
import javax.tools.FileObject;

import es.tipolisto.MSXTools.utils.CompressManager;
import es.tipolisto.MSXTools.utils.FileManager;
import es.tipolisto.MSXTools.utils.StringManager;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class CompressWindow extends JFrame implements DocumentListener{

	//protected static final boolean String = false;
	private JPanel contentPane;
	private JLabel jLabelCompress;
	private JLabel jLabelDecompress;
	private JLabel jLabelFileSelected;
	private JLabel jLabelFileSave;
	private JLabel jLabelNumberButesCompress;
	private JLabel jLabelNumberButesDecompress;
	private JLabel jLabelFileToRead;
	private JTextArea jTextAreaDecompress;
	private JTextArea jTextAreaCompress;
	private JButton jButtonSsave;
	private JButton jButtonLoadFileHex;
	
	
	private FileManager fileManager;
	private CompressManager compressManager;
	private File fileOrigin;
	private ArrayList<String> arrayList;

	public CompressWindow() {
		fileManager=new FileManager();
		compressManager=new CompressManager();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 622, 717);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//Centramos la ventana en la pantalla
		setLocationRelativeTo(null);
		setTitle("Compress / descompress");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setIconImage(new ImageIcon("data\\icon.png").getImage());
		
		arrayList=new ArrayList<String>();
		componentsInizialice();
		
		
	}

	private void componentsInizialice() {
		JLabel jlaberlTitle = new JLabel("Compress / decompress");
		jlaberlTitle.setFont(new Font("Tahoma", Font.PLAIN, 17));
		jlaberlTitle.setBounds(185, 11, 201, 32);
		contentPane.add(jlaberlTitle);
		
		jLabelCompress = new JLabel("Decompress");
		jLabelCompress.setBounds(10, 208, 165, 14);
		contentPane.add(jLabelCompress);
		
		jLabelDecompress = new JLabel("Compress");
		jLabelDecompress.setBounds(10, 437, 154, 14);
		contentPane.add(jLabelDecompress);
		
		
		jLabelNumberButesCompress = new JLabel("");
		jLabelNumberButesCompress.setBounds(125, 208, 183, 14);
		contentPane.add(jLabelNumberButesCompress);
		
		jLabelNumberButesDecompress = new JLabel("");
		jLabelNumberButesDecompress.setBounds(147, 437, 183, 14);
		contentPane.add(jLabelNumberButesDecompress);
		
		

		jTextAreaCompress = new JTextArea();
		//jTextAreaCompress.setBounds(10, 232, 586, 194);
		jTextAreaCompress.getDocument().addDocumentListener(this);
		JScrollPane jScrollPaneTextAreaCompress=new JScrollPane(jTextAreaCompress);
		jScrollPaneTextAreaCompress.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPaneTextAreaCompress.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPaneTextAreaCompress.setBounds(10, 232, 586, 194);
		//jTextAreaCompress.setLineWrap(true);
		//contentPane.add(jTextAreaCompress);
		contentPane.add(jScrollPaneTextAreaCompress);

		
		
		jTextAreaDecompress = new JTextArea();
		//jTextAreaDecompress.setBounds(10, 473, 586, 194);
		//jTextAreaDescompress.setLineWrap(true);
		JScrollPane jScrollPaneTextAreaDecompress=new JScrollPane(jTextAreaDecompress);
		jScrollPaneTextAreaDecompress.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPaneTextAreaDecompress.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPaneTextAreaDecompress.setBounds(10, 473, 586, 194);
		//contentPane.add(jTextAreaDecompress);
		contentPane.add(jScrollPaneTextAreaDecompress);
		
		jButtonSsave = new JButton("Save");
		jButtonSsave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arrayList.clear();
				//Comprobamos que el campo a comprimir tenga datos
				String text=jTextAreaCompress.getText();
				if (!text.equals("") || text==null) {
					//1.Seleccionamos el archivo destino
					JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir"));
					jFileChooser.setDialogTitle("Selecciona un archivo");
					int result=jFileChooser.showSaveDialog(null);
					if(result==JFileChooser.APPROVE_OPTION) {
						fileOrigin=jFileChooser.getSelectedFile();
						String fileDestinyPathParent=fileOrigin.getParent();
						String fileDestinyName=fileOrigin.getName().substring(0,fileOrigin.getName().length())+"-rle16.txt";
						String fileDestinyAbsolutePathParent=fileDestinyPathParent+"\\"+fileDestinyName;
						File fileDestiny=new File(fileDestinyAbsolutePathParent);
						//2.Obtenemos todos las l�neas
						//String text=jTextAreaCompress.getText();
						String[] lines=text.split("\n");
						//String contentCompress="";
						for (String value:lines) {
							value=value.replace("\n", "");
							String temp=compressManager.compressManagerLine2Digits(value);
							arrayList.add(temp);
						}
						fileManager.writeFile(fileDestiny, arrayList);
						JOptionPane.showMessageDialog(null, "File created.");
					}
				}else {
					JOptionPane.showMessageDialog(null, "The compress field is empty.");
				}
				
				
			}
		});
		jButtonSsave.setBounds(10, 126, 109, 32);
		contentPane.add(jButtonSsave);
		
		
		jLabelFileToRead = new JLabel("");
		jLabelFileToRead.setVerticalAlignment(SwingConstants.TOP);
		jLabelFileToRead.setBounds(10, 97, 571, 25);
		contentPane.add(jLabelFileToRead);
		
		
		jButtonLoadFileHex = new JButton("Load file Hex");
		jButtonLoadFileHex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arrayList.clear();
				JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir"));
				jFileChooser.setDialogTitle("Selecciona un archivo");
				int result=jFileChooser.showSaveDialog(null);
				if(result==JFileChooser.APPROVE_OPTION) {
					fileOrigin=jFileChooser.getSelectedFile();
					readFile();		
				}
			}
		});
		jButtonLoadFileHex.setBounds(10, 54, 183, 32);
		contentPane.add(jButtonLoadFileHex);
		
		jLabelFileSelected = new JLabel("");
		jLabelFileSelected.setBounds(10, 111, 568, 14);
		contentPane.add(jLabelFileSelected);
		
		jLabelFileSave = new JLabel("");
		jLabelFileSave.setBounds(125, 126, 471, 32);
		contentPane.add(jLabelFileSave);
		
		JButton jButtonReLoadFileHex = new JButton("Reload");
		jButtonReLoadFileHex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arrayList.clear();
				readFile();
			}
		});
		jButtonReLoadFileHex.setBounds(217, 54, 154, 32);
		contentPane.add(jButtonReLoadFileHex);
		


		
		/*JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(10, 25, 89, 23);
		contentPane.add(btnNewButton);*/
	}

	


	@Override
	public void insertUpdate(DocumentEvent e) {
		String text=jTextAreaCompress.getText();
		/*
		//Compress 1 digit :(
		String value=compressManager.compress1digit(text);
		jTextAreaDescompress.setText(String.valueOf(value));
		jLabelNumberButesDecompress.setText(String.valueOf(value.length()));
		*/
		//Compress 2 digits
		String[] lines=text.split("\n");
		String contentCompress="";
		for (String value:lines) {
			String temp=compressManager.compressManagerLine2Digits(value);
			contentCompress+=temp;
			jTextAreaDecompress.setText(contentCompress+"\n");
		}
		
		jLabelNumberButesCompress.setText(String.valueOf(text.length()));
		jLabelNumberButesDecompress.setText(String.valueOf(contentCompress.length()));
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

	}
	
	

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	
	private void readFile() {
		if(fileOrigin!=null) {
			String content="";
			String contentCompress="";
			String fileName=fileOrigin.getName();
			String extension=fileName.substring(fileName.length()-8, fileName.length());
			if (!extension.equals("-hex.txt")) {
				JOptionPane.showMessageDialog(null,"The file name must end in \"-hext.txt\" which is a file generated by MSXTools");
				System.out.println(extension);
			}else {
				try {
					arrayList=fileManager.readFileFromCompressWindow(fileOrigin);
					for (String string:arrayList) {
						content+=string;
						//System.out.println(string);
						String temp=compressManager.compressManagerLine2Digits(string);
						contentCompress+=temp;
					}
					jTextAreaCompress.setText(content);
					jTextAreaDecompress.setText(contentCompress);
					jLabelFileToRead.setText(fileOrigin.getAbsolutePath());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}		
		}else {
			JOptionPane.showMessageDialog(null,"Selected one file");		
		}
		
		
	}
}
