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
import javax.swing.AbstractAction;
import javax.swing.Action;

public class CompressWindow extends JFrame implements ActionListener, DocumentListener{

	//protected static final boolean String = false;
	private JPanel contentPane;
	private JLabel jLabelCompress;
	private JLabel jLabelDecompress;
	private JLabel jLabelFileSelected;
	private JLabel jLabelFileSave;
	private JLabel jLabelNumberButesCompress;
	private JLabel jLabelNumberButesDecompress;
	private JTextArea jTextAreaDecompress;
	private JTextArea jTextAreaCompress;
	private JToggleButton toogleButtonCompress;
	private JButton jButtonSsave;
	private JButton jButtonLoadFileHex;

	
	private FileManager fileManager;
	private CompressManager compressManager;


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
		
		
		componentsInizialice();
		
		
	}

	private void componentsInizialice() {
		JLabel jlaberlTitle = new JLabel("Compress / decompress");
		jlaberlTitle.setFont(new Font("Tahoma", Font.PLAIN, 17));
		jlaberlTitle.setBounds(185, 25, 201, 32);
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
		jTextAreaCompress.setBounds(10, 233, 586, 194);
		//jTextAreaCompress.setLineWrap(true);
		contentPane.add(jTextAreaCompress);
		//JScrollPane
		JScrollPane jscrollPaneCompress = new JScrollPane(jTextAreaCompress);
		jscrollPaneCompress.setBounds(10, 233, 586, 194);
		contentPane.add(jscrollPaneCompress);
		jTextAreaCompress.getDocument().addDocumentListener(this);
		
		jTextAreaDecompress = new JTextArea();
		jTextAreaDecompress.setBounds(10, 473, 586, 194);
		//jTextAreaDescompress.setLineWrap(true);
		contentPane.add(jTextAreaDecompress);
		JScrollPane jscrollPaneDescompress = new JScrollPane(jTextAreaDecompress);
		jscrollPaneDescompress.setBounds(10, 473, 586, 194);
		contentPane.add(jscrollPaneDescompress);

		
		toogleButtonCompress = new JToggleButton("Mode compress");
		toogleButtonCompress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		toogleButtonCompress.setBounds(437, 11, 144, 49);
		contentPane.add(toogleButtonCompress);
		toogleButtonCompress.addActionListener(this);
		
		jButtonSsave = new JButton("Save");
		jButtonSsave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> arrayList=new ArrayList<String>();
				//Comprobamos que el campo a comprimir tenga datos
				String text=jTextAreaCompress.getText();
				if (!text.equals("") || text==null) {
					//1.Seleccionamos el archivo destino
					JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir"));
					jFileChooser.setDialogTitle("Selecciona un archivo");
					int result=jFileChooser.showSaveDialog(null);
					if(result==JFileChooser.APPROVE_OPTION) {
						File fileOrigin=jFileChooser.getSelectedFile();
						String fileDestinyPathParent=fileOrigin.getParent();
						String fileDestinyName=fileOrigin.getName().substring(0,fileOrigin.getName().length())+"-rle16.txt";
						String fileDestinyAbsolutePathParent=fileDestinyPathParent+"\\"+fileDestinyName;
						File fileDestiny=new File(fileDestinyAbsolutePathParent);
						//2.Obtenemos todos las líneas
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
		jButtonSsave.setBounds(10, 132, 165, 32);
		contentPane.add(jButtonSsave);
		
		jButtonLoadFileHex = new JButton("Load file Hex");
		jButtonLoadFileHex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> arrayList=new ArrayList<String>();
				JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir"));
				jFileChooser.setDialogTitle("Selecciona un archivo");
				int result=jFileChooser.showSaveDialog(null);
				if(result==JFileChooser.APPROVE_OPTION) {
					//JOptionPane.showConfirmDialog(null, "WIP");
					String content="";
					String contentCompress="";
					File fileOrigin=jFileChooser.getSelectedFile();
					String fileName=fileOrigin.getName();
					String extension=fileName.substring(fileName.length()-8, fileName.length());
					if (!extension.equals("-hex.txt")) {
						JOptionPane.showMessageDialog(null,"You have to go to the main menu and click on CSV/TSX to Hex");
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
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					

					
					
				}
			}
		});
		jButtonLoadFileHex.setBounds(10, 73, 165, 32);
		contentPane.add(jButtonLoadFileHex);
		
		jLabelFileSelected = new JLabel("");
		jLabelFileSelected.setBounds(10, 111, 568, 14);
		contentPane.add(jLabelFileSelected);
		
		jLabelFileSave = new JLabel("");
		jLabelFileSave.setBounds(10, 175, 571, 14);
		contentPane.add(jLabelFileSave);
		

		
		/*JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(10, 25, 89, 23);
		contentPane.add(btnNewButton);*/
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(toogleButtonCompress==e.getSource()) {
			//JOptionPane.showMessageDialog(null, "clickado!!!");
			if (toogleButtonCompress.isSelected()) {
				toogleButtonCompress.setText("Mode descompress");
				jLabelCompress.setText("Test to descompress:");
				jLabelDecompress.setText("Compress:");

			}else {
				toogleButtonCompress.setText("Mode compress");
				jLabelCompress.setText("Test to compress:");
				jLabelDecompress.setText("Descompress:");
			}
			jTextAreaCompress.setText("");
			jTextAreaDecompress.setText("");
		}
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
}
