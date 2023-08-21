package es.tipolisto.MSXTools.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.tipolisto.MSXTools.beans.Sprite;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;

public class LetterCounterWindow extends JFrame implements DocumentListener {

	private JPanel contentPane;
	private JTextArea jTextArea;
	private JLabel labelResultWords, labelResultLines;
	private JScrollPane jScrollPaneTextArea;
	private Dimension dimensionJScrollPaneTextArea;
	private JTextField textFieldCut;
	private JButton btnCut;
	private int linesToCut;
	private ArrayList<String> arrayList;
	private JButton btnUndo;
	String saveText;
	public LetterCounterWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 622, 613);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setTitle("Letter counter");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//Centramos la ventana en la pantalla
		setLocationRelativeTo(null);
		
		linesToCut=0;
		arrayList=new ArrayList<String>();
		saveText="";
		
		jTextArea = new JTextArea();
		jTextArea.setBounds(10, 66, 586, 378);
		jTextArea.setLineWrap(true);
	
		jScrollPaneTextArea=new JScrollPane(jTextArea);
		jScrollPaneTextArea.setBounds(10, 66, 575, 260);
		jScrollPaneTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//contentPane.add(jTextArea);
		jTextArea.getDocument().addDocumentListener(this);
		contentPane.add(jScrollPaneTextArea);
		
		JLabel labelLeterCounterTitle = new JLabel("Leter counter");
		labelLeterCounterTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		labelLeterCounterTitle.setBounds(210, 33, 135, 22);
		contentPane.add(labelLeterCounterTitle);
		
		JLabel lblNewLabel = new JLabel("Words");
		lblNewLabel.setBounds(10, 349, 52, 14);
		contentPane.add(lblNewLabel);
		
		labelResultWords = new JLabel("");
		labelResultWords.setFont(new Font("Tahoma", Font.BOLD, 22));
		labelResultWords.setBounds(50, 337, 535, 35);
		contentPane.add(labelResultWords);
		
		JLabel lblLines = new JLabel("Lines");
		lblLines.setBounds(10, 387, 52, 14);
		contentPane.add(lblLines);
		
		labelResultLines = new JLabel("");
		labelResultLines.setFont(new Font("Tahoma", Font.BOLD, 22));
		labelResultLines.setBounds(50, 383, 535, 35);
		contentPane.add(labelResultLines);
		
		JLabel lblCut = new JLabel("Cut");
		lblCut.setBounds(10, 447, 52, 14);
		contentPane.add(lblCut);
		
		textFieldCut = new JTextField();
		textFieldCut.setBounds(72, 444, 251, 20);
		contentPane.add(textFieldCut);
		textFieldCut.setColumns(10);
		textFieldCut.setText(String.valueOf(linesToCut));
		
		btnCut = new JButton("Apply");
		btnCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Obtenemos el valor de textFieldCut
				String valueTextField=textFieldCut.getText();
				if(isDigit(valueTextField)) {
					arrayList.clear();
					String textForTextArea="";
					String text=jTextArea.getText();
					//Guardamos el texto por si queremos recuperarlos después
					saveText=text;
					String[] lines=text.split("\n");
					int lineas=0;
					//Por cada linea
					for (String line: lines) {
						lineas++;
						if(lineas<=Integer.valueOf(valueTextField)) {
							arrayList.add(line);
						}
					}
					
					if(arrayList.size()>0 ||arrayList!=null ) {
						for(int i=0;i<arrayList.size();i++) {
							textForTextArea+=arrayList.get(i)+"\n";
						}
					}else {
						JOptionPane.showMessageDialog(null, "Is empty");
					}
					jTextArea.setText(textForTextArea);
				}
			}
		});
		btnCut.setBounds(333, 443, 107, 23);
		contentPane.add(btnCut);
		
		btnUndo = new JButton("Undo");
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextArea.setText(saveText);
			}
		});
		btnUndo.setBounds(478, 443, 107, 23);
		contentPane.add(btnUndo);
	}


	@Override
	public void insertUpdate(DocumentEvent e) {
		update();
	}


	@Override
	public void removeUpdate(DocumentEvent e) {
		update();
		
	}


	@Override
	public void changedUpdate(DocumentEvent e) {
		update();
	}
	
	private void update() {
		// TODO Auto-generated method stub
		String text=jTextArea.getText();
		labelResultWords.setText(String.valueOf(text.length()));
		//Obtenemos un array con las l�neas
		String[] lines=text.split("\n");
		int lineas=0;
		//Por cada linea
		for (String line: lines) {
			lineas++;
		}
		labelResultLines.setText(String.valueOf(lineas));
	}
	
	private boolean isDigit(String cadena) {
		for(int i=0;i<cadena.length();i++) {
			char letter=cadena.charAt(i);
			if(!Character.isDigit(letter)) {
				return false;
			}
		}
		return true;
	}
}
