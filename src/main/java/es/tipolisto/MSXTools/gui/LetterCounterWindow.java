package es.tipolisto.MSXTools.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;

public class LetterCounterWindow extends JFrame implements DocumentListener {

	private JPanel contentPane;
	private JTextArea jTextArea;
	private JLabel labelResult;
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
		
		
		jTextArea = new JTextArea();
		jTextArea.setBounds(10, 66, 586, 378);
		jTextArea.setLineWrap(true);
		contentPane.add(jTextArea);
		jTextArea.getDocument().addDocumentListener(this);
		
		JLabel labelLeterCounterTitle = new JLabel("Leter counter");
		labelLeterCounterTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		labelLeterCounterTitle.setBounds(210, 33, 135, 22);
		contentPane.add(labelLeterCounterTitle);
		
		JLabel lblNewLabel = new JLabel("Result");
		lblNewLabel.setBounds(10, 506, 52, 14);
		contentPane.add(lblNewLabel);
		
		labelResult = new JLabel("");
		labelResult.setFont(new Font("Tahoma", Font.BOLD, 22));
		labelResult.setBounds(94, 463, 502, 101);
		contentPane.add(labelResult);
	}


	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		String text=jTextArea.getText();
		labelResult.setText(String.valueOf(text.length()));
	}


	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
