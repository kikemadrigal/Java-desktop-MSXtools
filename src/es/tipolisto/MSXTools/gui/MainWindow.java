package es.tipolisto.MSXTools.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class MainWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 462, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		//Centramos la ventana en la pantalla
		setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("Tools MSX");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(164, 11, 120, 21);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		contentPane.add(lblNewLabel);
		
		JButton jButtonDeleteComents = new JButton("Delete coments");
		jButtonDeleteComents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteComentsWindow frame = new DeleteComentsWindow();
				frame.setVisible(true);
			}
		});
		jButtonDeleteComents.setBounds(118, 57, 232, 23);
		contentPane.add(jButtonDeleteComents);
		
		JButton jButonCSVToBas = new JButton("CSV / TMX to Bas");
		jButonCSVToBas.setBounds(118, 103, 232, 23);
		jButonCSVToBas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CSVTSXToBasWindow csvToBasWindow=new CSVTSXToBasWindow();
				csvToBasWindow.setVisible(true);
			}
		});
		contentPane.add(jButonCSVToBas);
		
		JButton btnNewButton = new JButton("Compress / decompress RLE 16");
		btnNewButton.setBounds(118, 233, 232, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompressWindow frame = new CompressWindow();
				frame.setVisible(true);
			}
		});
		contentPane.add(btnNewButton);
		
		JButton jButtonCSVToHex = new JButton("CSV / TMX  to Hex");
		jButtonCSVToHex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CSVTSXToHexWindow frame=new CSVTSXToHexWindow();
				frame.setVisible(true);
			}
		});
		jButtonCSVToHex.setBounds(118, 146, 232, 23);
		contentPane.add(jButtonCSVToHex);
		
		JLabel lblNewLabel_1 = new JLabel("MSX Murcia 2022 ");
		lblNewLabel_1.setBounds(305, 458, 131, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton jButtonLetterCounter = new JButton("Letter counter");
		jButtonLetterCounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LetterCounterWindow frame = new LetterCounterWindow();
				frame.setVisible(true);
			}
		});
		jButtonLetterCounter.setBounds(118, 278, 232, 23);
		contentPane.add(jButtonLetterCounter);
		
		JButton jButtonPixelMap = new JButton("Tile editor");
		jButtonPixelMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//MainPixelWindows frame = new MainPixelWindows();
				//frame.setVisible(true);
				JOptionPane.showMessageDialog(null, "WIP");

			}
		});
		jButtonPixelMap.setBounds(118, 326, 231, 23);
		contentPane.add(jButtonPixelMap);
		
		JButton buttonBmp2SC5 = new JButton("IMG 2 SC");
		buttonBmp2SC5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IMG2SCWindow frame=new IMG2SCWindow();
				frame.setVisible(true);
			}
		});
		buttonBmp2SC5.setBounds(118, 415, 232, 21);
		contentPane.add(buttonBmp2SC5);
		
		JButton buttonCSVTMXToASM = new JButton("CSV / TMX  to ASM");
		buttonCSVTMXToASM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CSVTSXToASMWindow frame = new CSVTSXToASMWindow();
				frame.setVisible(true);
			}
		});
		buttonCSVTMXToASM.setBounds(116, 191, 234, 23);
		contentPane.add(buttonCSVTMXToASM);
		
		JButton btnSpriteEditor = new JButton("Sprite editor");
		btnSpriteEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpriteEditorWindow frame = new SpriteEditorWindow();
				frame.setVisible(true);				
			}
		});
		btnSpriteEditor.setBounds(119, 368, 231, 23);
		contentPane.add(btnSpriteEditor);
	}
}
