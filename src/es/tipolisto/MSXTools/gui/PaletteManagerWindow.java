package es.tipolisto.MSXTools.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import es.tipolisto.MSXTools.beans.Palette;
import es.tipolisto.MSXTools.beans.RGB;
import es.tipolisto.MSXTools.data.SQL;
import es.tipolisto.MSXTools.data.SQLiteClient;
import es.tipolisto.MSXTools.utils.MSXPalette;
import es.tipolisto.MSXTools.utils.PaletteManager;
import javax.swing.JTable;

public class PaletteManagerWindow extends JFrame {

	PaletteManager paletteManager;
	private JPanel contentPane;
	private JComboBox comboBoxPalettes;
	private JButton jButtonColor0,jButtonColor1,jButtonColor2,jButtonColor3,jButtonColor4,jButtonColor5;
	private JButton jButtonColor6,jButtonColor7,jButtonColor8,jButtonColor9,jButtonColor10;
	private JButton jButtonColor11,jButtonColor12,jButtonColor13,jButtonColor14,jButtonColor15;
	private JButton[] jButtons;
	private JTextField textFieldColorRed;
	private JTextField textFieldColorGreen;
	private JTextField textFieldColorBlue;
	private int paletteSelect;
	private JLabel labelMsxColor;
	private JLabel lalbelHexageximalColor;
	private JTable table;
	private DefaultTableModel model;
	private JLabel labelSelected;
	private JLabel lblNewLabel_3;
	private JTextField textField;
	private SQLiteClient sqliteClient;
	private static final float VERSION_SQLITE=2.0f;
	/**
	 * Create the frame.
	 */
	public PaletteManagerWindow() {
		//Configuración del JFrame
		setTitle("Sprite editor");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 646, 720);
		setResizable(false);
		setLocationRelativeTo(null);
		Color colorFondo=new Color(239,252,254);
	    setBackground(colorFondo);
	    setIconImage(new ImageIcon("data\\icon.png").getImage());
	    
	  
	    //En un jframe el jpanel es el que tiene los compnentes y la distribución de estos
	    contentPane=new JPanel();
	    contentPane.setLayout(null);
		setContentPane(contentPane);
		
		paletteManager=new PaletteManager();
		paletteSelect=0;
		/*

		JLabel lblNewLabel_5 = new JLabel("Select palette");
		lblNewLabel_5.setBounds(10, 269, 91, 14);
		contentPane.add(lblNewLabel_5);
		
		
		comboBoxPalettes = new JComboBox();
		comboBoxPalettes.setBounds(123, 265, 467, 22);
		comboBoxPalettes.addItem("Philips 8255 NMS");
		comboBoxPalettes.addItem("TMS9918 1");
		comboBoxPalettes.addItem("TMS9219 2");
		comboBoxPalettes.addItem("TOSHIBA");
		comboBoxPalettes.addItem("V9938");
		contentPane.add(comboBoxPalettes);
		*/
		
		JButton buttonCreateNewPaltte = new JButton("Create new Palette");
		buttonCreateNewPaltte.setBounds(10, 293, 575, 23);
		contentPane.add(buttonCreateNewPaltte);
		
		initializeColorButtons();
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setBounds(10, 114, 800, 157);
		JScrollPane scrollPaneTable = new JScrollPane(table);
		scrollPaneTable.setBounds(10,114,575,157);
		scrollPaneTable.setViewportView(table);
		contentPane.add(scrollPaneTable);
		
		model=(DefaultTableModel)table.getModel();
		table.setModel(model);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				 System.out.print(table.getValueAt(table.getSelectedRow(), 0).toString());
				 System.out.print(table.getValueAt(table.getSelectedRow(), 1).toString());
				 System.out.print(table.getValueAt(table.getSelectedRow(), 2).toString());
					System.out.println("");
			}
		});

		JLabel lblNewLabel_2 = new JLabel("Selected:");
		lblNewLabel_2.setBounds(10, 81, 93, 23);
		contentPane.add(lblNewLabel_2);
		
		labelSelected = new JLabel("");
		labelSelected.setBounds(110, 81, 684, 18);
		contentPane.add(labelSelected);
		
		lblNewLabel_3 = new JLabel("Name");
		lblNewLabel_3.setBounds(10, 352, 105, 14);
		contentPane.add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.setBounds(110, 349, 475, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		sqliteClient=new SQLiteClient(VERSION_SQLITE);
		ArrayList<Palette> arrayListpalette=sqliteClient.getAllPalettes();
		
		fillTable(arrayListpalette);
		

	}

	
	
	
	
	
	
	
	
	
	private void initializeColorButtons() {
		final HashMap<MSXPalette, RGB> palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMS();

		
		JLabel lblNewLabel = new JLabel("R");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(110, 461, 26, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblG = new JLabel("G");
		lblG.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblG.setBounds(299, 461, 26, 23);
		contentPane.add(lblG);
		
		JLabel lblB = new JLabel("B");
		lblB.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblB.setBounds(492, 461, 26, 23);
		contentPane.add(lblB);
		
		lalbelHexageximalColor = new JLabel("00ffffff");
		lalbelHexageximalColor.setBounds(279, 537, 46, 14);
		contentPane.add(lalbelHexageximalColor);
		
		labelMsxColor = new JLabel("1,0,0,0");
		labelMsxColor.setBounds(279, 584, 46, 14);
		contentPane.add(labelMsxColor);
		
		
		contentPane.setLayout(null);
		jButtonColor0 = new JButton("0");
		RGB rgbTransparent=palettePhilips8255NMS.get(MSXPalette.TRANSPARENTE);
		Color colorButtonTransparent=new Color(rgbTransparent.getRed(),rgbTransparent.getGreen(),rgbTransparent.getBlue());
		jButtonColor0.setBackground(colorButtonTransparent);
		jButtonColor0.setForeground(Color.WHITE);
		jButtonColor0.setOpaque(true);
		//jButtonColor0.setBorderPainted(false);
		jButtonColor0.setBounds(10, 393, 69, 23);
		contentPane.add(jButtonColor0);
		jButtonColor0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=0;
				setTextFieldColors(rgbTransparent,0);

			}
		});
		
		
		jButtonColor1 = new JButton("1");
		RGB rgbNegro=palettePhilips8255NMS.get(MSXPalette.TRANSPARENTE);
		Color colorNegro=new Color(rgbNegro.getRed(),rgbNegro.getGreen(),rgbNegro.getBlue());
		jButtonColor1.setBackground(colorNegro);
		jButtonColor1.setForeground(new Color(255, 255, 255));
		jButtonColor1.setOpaque(true);
		//jButtonColor1.setBorderPainted(false);
		jButtonColor1.setBounds(87, 393, 60, 23);
		contentPane.add(jButtonColor1);
		jButtonColor1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=1;
				setTextFieldColors(rgbNegro, 1);

			}
		});
		
		
		jButtonColor2 = new JButton("2");
		jButtonColor2.setForeground(new Color(255, 255, 255));
		RGB rgbVerdeMedio=palettePhilips8255NMS.get(MSXPalette.VERDE_MEDIO);
		Color colorVerdeMedio=new Color(rgbVerdeMedio.getRed(),rgbVerdeMedio.getGreen(),rgbVerdeMedio.getBlue());
		jButtonColor2.setBackground(colorVerdeMedio);
		jButtonColor2.setOpaque(true);
		//jButtonColor2.setBorderPainted(false);
		jButtonColor2.setBounds(163, 393, 60, 23);
		contentPane.add(jButtonColor2);
		jButtonColor2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=2;
				setTextFieldColors(rgbVerdeMedio,2);

			}
		});
		

		jButtonColor3 = new JButton("3");
		RGB rgbVerdeClaro=palettePhilips8255NMS.get(MSXPalette.VERDE_CLARO);
		Color colorVerdeClaro=new Color(rgbVerdeClaro.getRed(),rgbVerdeClaro.getGreen(),rgbVerdeClaro.getBlue());
		jButtonColor3.setBackground(colorVerdeClaro);
		jButtonColor3.setOpaque(true);
		//jButtonColor3.setBorderPainted(false);
		jButtonColor3.setBounds(229, 393, 62, 23);
		contentPane.add(jButtonColor3);
		jButtonColor3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=3;
				setTextFieldColors(rgbVerdeClaro,3);

			}
		});
		
		jButtonColor4 = new JButton("4");
		jButtonColor4.setForeground(new Color(255, 255, 255));
		RGB rgbAzulOscuro=palettePhilips8255NMS.get(MSXPalette.AZUL_OSCURO);
		Color colorAzulOscuro=new Color(rgbAzulOscuro.getRed(),rgbAzulOscuro.getGreen(),rgbAzulOscuro.getBlue());
		jButtonColor4.setBackground(colorAzulOscuro);
		jButtonColor4.setOpaque(true);
		//jButtonColor4.setBorderPainted(false);
		jButtonColor4.setBounds(299, 393, 71, 23);
		contentPane.add(jButtonColor4);
		jButtonColor4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=4;
				setTextFieldColors(rgbAzulOscuro,4);

			}
		});
		
		jButtonColor5 = new JButton("5");
		RGB rgbAzulMedio=palettePhilips8255NMS.get(MSXPalette.AZUL_MEDIO);
		Color colorAzulMedio=new Color(rgbAzulMedio.getRed(),rgbAzulMedio.getGreen(),rgbAzulMedio.getBlue());
		jButtonColor5.setBackground(colorAzulMedio);
		jButtonColor5.setForeground(new Color(255, 255, 255));
		jButtonColor5.setOpaque(true);
		//jButtonColor5.setBorderPainted(false);
		jButtonColor5.setBounds(382, 393, 62, 23);
		contentPane.add(jButtonColor5);
		jButtonColor5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=5;
				setTextFieldColors(rgbAzulMedio,5);

			}
		});
		
		jButtonColor6 = new JButton("6");
		jButtonColor6.setForeground(new Color(255, 255, 255));
		RGB rgbRojoOscuro=palettePhilips8255NMS.get(MSXPalette.ROJO_OSCURO);
		Color colorRojoOscuro=new Color(rgbRojoOscuro.getRed(),rgbRojoOscuro.getGreen(),rgbRojoOscuro.getBlue());
		jButtonColor6.setBackground(colorRojoOscuro);
		jButtonColor6.setOpaque(true);
		//jButtonColor6.setBorderPainted(false);
		jButtonColor6.setBounds(454, 393, 64, 23);
		contentPane.add(jButtonColor6);
		jButtonColor6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=6;
				setTextFieldColors(rgbRojoOscuro,6);

			}
		});
		
		jButtonColor7 = new JButton("7");
		RGB rgbAzulClaro=palettePhilips8255NMS.get(MSXPalette.AZUL_CLARO);
		Color colorAzulClaro=new Color(rgbAzulClaro.getRed(),rgbAzulClaro.getGreen(),rgbAzulClaro.getBlue());
		jButtonColor7.setBackground(colorAzulClaro);
		jButtonColor7.setOpaque(true);
		jButtonColor7.setForeground(new Color(0, 0, 0));
		//jButtonColor7.setBorderPainted(false);
		jButtonColor7.setBounds(528, 393, 62, 23);
		contentPane.add(jButtonColor7);
		jButtonColor7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=7;
				setTextFieldColors(rgbAzulClaro,7);

			}
		});
		
		jButtonColor8 = new JButton("6");
		RGB rgbRojoMedio=palettePhilips8255NMS.get(MSXPalette.ROJO_MEDIO);
		Color colorRojoMedio=new Color(rgbRojoMedio.getRed(),rgbRojoMedio.getGreen(),rgbRojoMedio.getBlue());
		jButtonColor8.setBackground(colorRojoMedio);
		jButtonColor8.setOpaque(true);
		jButtonColor8.setForeground(Color.WHITE);
		//jButtonColor8.setBorderPainted(false);
		jButtonColor8.setBounds(10, 427, 71, 23);
		contentPane.add(jButtonColor8);
		jButtonColor8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=8;
				setTextFieldColors(rgbRojoMedio,8);

			}
		});
		
		jButtonColor9 = new JButton("9");
		RGB rgbRojoClaro=palettePhilips8255NMS.get(MSXPalette.ROJO_CLARO);
		Color colorRojoClaro=new Color(rgbRojoClaro.getRed(),rgbRojoClaro.getGreen(),rgbRojoClaro.getBlue());
		jButtonColor9.setBackground(colorRojoClaro);
		jButtonColor9.setOpaque(true);
		jButtonColor9.setForeground(new Color(0, 0, 0));
		//jButtonColor9.setBorderPainted(false);
		jButtonColor9.setBounds(89, 427, 58, 23);
		contentPane.add(jButtonColor9);
		jButtonColor9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=9;
				setTextFieldColors(rgbRojoClaro,9);
	
			}
		});
		
		jButtonColor10 = new JButton("10");
		RGB rgbAmarilloOscuro=palettePhilips8255NMS.get(MSXPalette.AMARILLO_OSCURO);
		Color colorAmarilloOscuro=new Color(rgbAmarilloOscuro.getRed(),rgbAmarilloOscuro.getGreen(),rgbAmarilloOscuro.getBlue());
		jButtonColor10.setBackground(colorAmarilloOscuro);
		jButtonColor10.setOpaque(true);
		jButtonColor10.setForeground(new Color(0, 0, 0));
		//jButtonColor10.setBorderPainted(false);
		jButtonColor10.setBounds(161, 427, 62, 23);
		contentPane.add(jButtonColor10);
		jButtonColor10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=10;
				setTextFieldColors(rgbAmarilloOscuro,10);

			}
		});
		
		jButtonColor11 = new JButton("11");
		RGB rgbAmarilloClaro=palettePhilips8255NMS.get(MSXPalette.AMARILLO_CLARO);
		Color colorAmarilloClaro=new Color(rgbAmarilloClaro.getRed(),rgbAmarilloClaro.getGreen(),rgbAmarilloClaro.getBlue());
		jButtonColor11.setBackground(colorAmarilloClaro);
		jButtonColor11.setOpaque(true);
		jButtonColor11.setForeground(new Color(0, 0, 0));
		//jButtonColor11.setBorderPainted(false);
		jButtonColor11.setBounds(229, 427, 62, 23);
		contentPane.add(jButtonColor11);
		jButtonColor11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=11;
				setTextFieldColors(rgbAmarilloClaro,11);

			}
		});
		
		jButtonColor12 = new JButton("12");
		RGB rgbVerdeOscuro=palettePhilips8255NMS.get(MSXPalette.VERDE_OSCURO);
		Color colorVerdeOscuro=new Color(rgbVerdeOscuro.getRed(),rgbVerdeOscuro.getGreen(),rgbVerdeOscuro.getBlue());
		jButtonColor12.setBackground(colorVerdeOscuro);
		jButtonColor12.setOpaque(true);
		jButtonColor12.setForeground(new Color(0, 0, 0));
		//jButtonColor12.setBorderPainted(false);
		jButtonColor12.setBounds(299, 427, 71, 23);
		contentPane.add(jButtonColor12);
		jButtonColor12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=12;
				setTextFieldColors(rgbVerdeOscuro,12);

			}
		});
		
		jButtonColor13 = new JButton("13");
		RGB rgbVioleta=palettePhilips8255NMS.get(MSXPalette.VIOLETA);
		Color colorVioleta=new Color(rgbVioleta.getRed(),rgbVioleta.getGreen(),rgbVioleta.getBlue());
		jButtonColor13.setBackground(colorVioleta);
		jButtonColor13.setOpaque(true);
		jButtonColor13.setForeground(Color.WHITE);
		//jButtonColor13.setBorderPainted(false);
		jButtonColor13.setBounds(382, 427, 62, 23);
		contentPane.add(jButtonColor13);
		jButtonColor13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=13;
				setTextFieldColors(rgbVioleta,13);

			}
		});
		
		jButtonColor14 = new JButton("14");
		RGB rgbGris=palettePhilips8255NMS.get(MSXPalette.GRIS);
		Color colorGris=new Color(rgbGris.getRed(),rgbGris.getGreen(),rgbGris.getBlue());
		jButtonColor14.setBackground(colorGris);
		jButtonColor14.setOpaque(true);
		jButtonColor14.setForeground(Color.WHITE);
		//jButtonColor14.setBorderPainted(false);
		jButtonColor14.setBounds(455, 427, 63, 23);
		contentPane.add(jButtonColor14);
		jButtonColor14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=14;
				setTextFieldColors(rgbGris,14);

			}
		});
		
		jButtonColor15 = new JButton("15");
		RGB rgbBlanco=palettePhilips8255NMS.get(MSXPalette.BLANCO);
		Color colorBlanco=new Color(rgbBlanco.getRed(),rgbBlanco.getGreen(),rgbBlanco.getBlue());
		jButtonColor15.setBackground(colorBlanco);
		jButtonColor15.setOpaque(true);
		jButtonColor15.setForeground(new Color(0, 0, 0));
		//jButtonColor15.setBorderPainted(false);
		jButtonColor15.setBounds(528, 429, 62, 23);
		contentPane.add(jButtonColor15);
		jButtonColor15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=15;
				setTextFieldColors(rgbBlanco,15);

			}
		});
		
		jButtons=new JButton[16];
		jButtons[0]=jButtonColor0;
		jButtons[1]=jButtonColor1;
		jButtons[2]=jButtonColor2;
		jButtons[3]=jButtonColor3;
		jButtons[4]=jButtonColor4;
		jButtons[5]=jButtonColor5;
		jButtons[6]=jButtonColor6;
		jButtons[7]=jButtonColor7;
		jButtons[8]=jButtonColor8;
		jButtons[9]=jButtonColor9;
		jButtons[10]=jButtonColor10;
		jButtons[11]=jButtonColor11;
		jButtons[12]=jButtonColor12;
		jButtons[13]=jButtonColor13;
		jButtons[14]=jButtonColor14;
		jButtons[15]=jButtonColor15;
		
		JButton jButtonResetColors = new JButton("Reset colors");
		jButtonResetColors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetPalette();
			}
		});
		jButtonResetColors.setBounds(229, 628, 142, 23);
		contentPane.add(jButtonResetColors);
		
		


		
		
		textFieldColorRed = new JTextField();
		textFieldColorRed.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldColorRed.setText("0");
		textFieldColorRed.setBounds(29, 495, 167, 20);
		contentPane.add(textFieldColorRed);
		textFieldColorRed.setColumns(10);
		textFieldColorRed.getDocument().addDocumentListener(new DocumentListener() {
			  	  public void changedUpdate(DocumentEvent e) {
				  	 
				  }
				  public void removeUpdate(DocumentEvent e) {
					 
				  }
				  public void insertUpdate(DocumentEvent e) {
					  if(!textFieldColorRed.getText().toString().isEmpty()) {
						  String rgbComponent="red";
						  changeRGBOnPalette(rgbComponent);
					  }	  
				  }
		});

		textFieldColorGreen = new JTextField();
		textFieldColorGreen.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldColorGreen.setText("0");
		textFieldColorGreen.setColumns(10);
		textFieldColorGreen.setBounds(209, 495, 197, 20);
		contentPane.add(textFieldColorGreen);
		textFieldColorGreen.getDocument().addDocumentListener(new DocumentListener() {
		  	  public void changedUpdate(DocumentEvent e) {
				  	 
			  }
			  public void removeUpdate(DocumentEvent e) {
				 
			  }
			  public void insertUpdate(DocumentEvent e) {
				  if(!textFieldColorGreen.getText().toString().isEmpty()) {
					  String rgbComponent="green";
					  changeRGBOnPalette(rgbComponent);
				  }	
			  }
		});

		
		textFieldColorBlue = new JTextField();
		textFieldColorBlue.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldColorBlue.setText("0");
		textFieldColorBlue.setColumns(10);
		textFieldColorBlue.setBounds(412, 495, 173, 20);
		contentPane.add(textFieldColorBlue);
		textFieldColorBlue.getDocument().addDocumentListener(new DocumentListener() {
		  	  public void changedUpdate(DocumentEvent e) {
				  	 
			  }
			  public void removeUpdate(DocumentEvent e) {
				 
			  }
			  public void insertUpdate(DocumentEvent e) {
				  if(!textFieldColorBlue.getText().toString().isEmpty()) {
					  String rgbComponent="blue";
					  changeRGBOnPalette(rgbComponent);
				  }	 
			  }
		});
		
		JLabel lblNewLabel_1 = new JLabel("Palette Manager");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(264, 33, 173, 22);
		contentPane.add(lblNewLabel_1);
		

		
		

		

	}
	
	
	private void setTextFieldColors(RGB rgb, int selection) {
		for(int i=0;i<jButtons.length;i++) {
			JButton jbutton=jButtons[i];
			if(i==selection)jbutton.setBorder(new LineBorder(Color.BLACK));
			else jbutton.setBorder(new LineBorder(Color.WHITE));
		}
		textFieldColorRed.setText(String.valueOf(rgb.getRed()));
		textFieldColorGreen.setText(String.valueOf(rgb.getGreen()));
		textFieldColorBlue.setText(String.valueOf(rgb.getBlue()));
		
		//color hexagesimal
		Color color=new Color(rgb.getRed(),rgb.getGreen(),rgb.getBlue());
		String hexagesimal=Integer.toHexString( color.getRGB() & 0x00ffffff );
		lalbelHexageximalColor.setText(hexagesimal);

		//Color MSX
		int MSXColorRed=(rgb.getRed()*7)/255;
		int MSXColorGreen=(rgb.getGreen()*7)/255;
		int MSXColorBlue=(rgb.getBlue()*7)/255;
		labelMsxColor.setText(selection+","+MSXColorRed+","+MSXColorGreen+","+MSXColorBlue);
		
	}
	
	
	
	private void changeRGBOnPalette(String rgbComponent) {
		final HashMap<MSXPalette, RGB> palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMS();
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			if(paletteSelect==entry.getKey().ordinal()) {
				if (rgbComponent.equals("red")) entry.getValue().setRed(Integer.valueOf(textFieldColorRed.getText()));
				if (rgbComponent.equals("green")) entry.getValue().setGreen(Integer.valueOf(textFieldColorGreen.getText()));
				if (rgbComponent.equals("blue")) entry.getValue().setBlue(Integer.valueOf(textFieldColorBlue.getText()));
				jButtons[paletteSelect].setBackground(new Color(entry.getValue().getRed(),entry.getValue().getGreen(),entry.getValue().getBlue()));
			}
		}
		
	}
	
	private void resetPalette() {
		final HashMap<MSXPalette, RGB> palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMS();
		RGB[] rgbRGB8255NMS=PaletteManager.getRGB8255NMS();
		int i=0;
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			switch(entry.getKey()) {
			case TRANSPARENTE:
				entry.getValue().setRed(rgbRGB8255NMS[0].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[0].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[0].getBlue());
				break;
			case NEGRO:
				entry.getValue().setRed(rgbRGB8255NMS[1].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[1].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[1].getBlue());
				break;
			case VERDE_MEDIO:
				entry.getValue().setRed(rgbRGB8255NMS[2].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[2].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[2].getBlue());
				break;
			case VERDE_CLARO:
				entry.getValue().setRed(rgbRGB8255NMS[3].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[3].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[3].getBlue());
				break;
			case AZUL_OSCURO:
				entry.getValue().setRed(rgbRGB8255NMS[4].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[4].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[4].getBlue());
				break;
			case AZUL_MEDIO:
				entry.getValue().setRed(rgbRGB8255NMS[5].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[5].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[5].getBlue());
				break;
			case ROJO_OSCURO:
				entry.getValue().setRed(rgbRGB8255NMS[6].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[6].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[6].getBlue());
				break;
			case AZUL_CLARO:
				entry.getValue().setRed(rgbRGB8255NMS[7].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[7].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[7].getBlue());
				break;
			case ROJO_MEDIO:
				entry.getValue().setRed(rgbRGB8255NMS[8].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[8].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[8].getBlue());
				break;
			case ROJO_CLARO:
				entry.getValue().setRed(rgbRGB8255NMS[9].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[9].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[9].getBlue());
				break;
			case AMARILLO_OSCURO:
				entry.getValue().setRed(rgbRGB8255NMS[10].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[10].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[10].getBlue());
				break;
			case AMARILLO_CLARO:
				entry.getValue().setRed(rgbRGB8255NMS[11].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[11].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[11].getBlue());
				break;
			case VERDE_OSCURO:
				entry.getValue().setRed(rgbRGB8255NMS[12].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[12].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[12].getBlue());
				break;
			case VIOLETA:
				entry.getValue().setRed(rgbRGB8255NMS[13].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[13].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[13].getBlue());
				break;
			case GRIS:
				entry.getValue().setRed(rgbRGB8255NMS[14].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[14].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[14].getBlue());
				break;
			case BLANCO:
				entry.getValue().setRed(rgbRGB8255NMS[15].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[15].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[15].getBlue());
				break;
			}		
			i++;
		}
		JOptionPane.showMessageDialog(null, "Restored colors");
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			jButtons[entry.getKey().ordinal()].setBackground(new Color(entry.getValue().getRed(),entry.getValue().getGreen(),entry.getValue().getBlue()));
		}
	}
	
	
	private void fillTable(ArrayList<Palette> arrayListPalette) {

		Object[] row=new Object[18];
		JTableHeader header = table.getTableHeader();
		TableColumnModel tableColumnModel = table.getColumnModel();

		//model.addColumn("Id");
		//tableColumnModel.getColumn(0).setMaxWidth(10);
		//tableColumnModel.getColumn(0).setPreferredWidth(10);
		//tableColumnModel.getColumn(0).setWidth(10);
		
		model.addColumn("Nombre");
		tableColumnModel.getColumn(0).setMaxWidth(60);
		tableColumnModel.getColumn(0).setPreferredWidth(60);
		tableColumnModel.getColumn(0).setWidth(60);
		
		for(int i=0; i<16;i++) {
			model.addColumn("color "+i);
			tableColumnModel.getColumn(i+1).setMaxWidth(20);
			tableColumnModel.getColumn(i+1).setPreferredWidth(20);
			tableColumnModel.getColumn(i+1).setWidth(20);
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	    header.repaint();
	    table.getTableHeader().repaint();
		/*model.addColumn("color 0");
		model.addColumn("color 1");
		model.addColumn("color 2");
		model.addColumn("color 3");
		model.addColumn("color 4");
		model.addColumn("color 5");
		model.addColumn("color 6");
		model.addColumn("color 7");
		model.addColumn("color 8");
		model.addColumn("color 9");
		model.addColumn("color 10");
		model.addColumn("color 11");
		model.addColumn("color 12");
		model.addColumn("color 13");
		model.addColumn("color 14");
		model.addColumn("color 15");*/
		
	    int count=0;
		for(Palette palette:arrayListPalette) {
			es.tipolisto.MSXTools.beans.Color color;
			//System.out.println(palette.toString());
			//row[0]=palette.getId();
			row[0]=palette.getName();
			int idColor0=palette.getColor0();
			color = sqliteClient.getColor(idColor0);
			row[1]=color.toString();
			int idColor1=palette.getColor1();
			color = sqliteClient.getColor(idColor1);
			row[2]=color.toString();
			int idColor2=palette.getColor2();
			color = sqliteClient.getColor(idColor2);
			row[3]=color.toString();
			int idColor3=palette.getColor3();
			color = sqliteClient.getColor(idColor3);
			row[4]=color.toString();
			int idColor4=palette.getColor4();
			color = sqliteClient.getColor(idColor4);
			row[5]=color.toString();
			int idColor5=palette.getColor5();
			color = sqliteClient.getColor(idColor5);
			row[6]=color.toString();
			int idColor6=palette.getColor6();
			color = sqliteClient.getColor(idColor6);
			row[7]=color.toString();
			int idColor7=palette.getColor7();
			color = sqliteClient.getColor(idColor7);
			row[8]=color.toString();
			int idColor8=palette.getColor8();
			color = sqliteClient.getColor(idColor8);
			row[9]=color.toString();
			int idColor9=palette.getColor9();
			color = sqliteClient.getColor(idColor9);
			row[10]=color.toString();		
			color = sqliteClient.getColor(palette.getColor10());
			row[11]=color.toString();
			color = sqliteClient.getColor(palette.getColor11());
			row[12]=color.toString();
			color = sqliteClient.getColor(palette.getColor12());
			row[13]=color.toString();
			color = sqliteClient.getColor(palette.getColor13());
			row[14]=color.toString();
			color = sqliteClient.getColor(palette.getColor14());
			row[15]=color.toString();
			color = sqliteClient.getColor(palette.getColor15());
			row[16]=color.toString();
			
			model.addRow(row);
			if(palette.getIsSelected()==1) {
				table.getSelectionModel().addSelectionInterval(count, count);
				es.tipolisto.MSXTools.beans.Color color0= sqliteClient.getColor(palette.getColor0());
				es.tipolisto.MSXTools.beans.Color color1= sqliteClient.getColor(palette.getColor1());
				es.tipolisto.MSXTools.beans.Color color2= sqliteClient.getColor(palette.getColor2());
				es.tipolisto.MSXTools.beans.Color color3= sqliteClient.getColor(palette.getColor3());
				es.tipolisto.MSXTools.beans.Color color4= sqliteClient.getColor(palette.getColor4());
				es.tipolisto.MSXTools.beans.Color color5= sqliteClient.getColor(palette.getColor5());
				es.tipolisto.MSXTools.beans.Color color6= sqliteClient.getColor(palette.getColor6());
				es.tipolisto.MSXTools.beans.Color color7= sqliteClient.getColor(palette.getColor7());
				es.tipolisto.MSXTools.beans.Color color8= sqliteClient.getColor(palette.getColor8());
				es.tipolisto.MSXTools.beans.Color color9= sqliteClient.getColor(palette.getColor9());
				
				
				labelSelected.setText(palette.getName()+
						", color0: "+color0.toString()+
						", color 1: "+color1.toString()+
						", color 2: "+color2.toString()+
						", color 3: "+color3.toString()+
						", color 4: "+color4.toString()+
						", color 5: "+color5.toString()+
						", color 6: "+color6.toString()+
						", color 7: "+color7.toString()+
						", color 8: "+color8.toString()+
						", color 9: "+color9.toString());
			}
			count++;
		}
		
		
		
		/*table.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null}
				},
				new String[] {
						"id", "Name", "Id color 0", "Id color 1", "Id color 2"
				}
			));
		 */
	}
}
