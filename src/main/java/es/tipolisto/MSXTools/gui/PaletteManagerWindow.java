package es.tipolisto.MSXTools.gui;


import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.border.LineBorder;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import javax.swing.table.TableColumnModel;

import es.tipolisto.MSXTools.beans.ColorPalette;
import es.tipolisto.MSXTools.beans.Palette;
import es.tipolisto.MSXTools.beans.RGB;
import es.tipolisto.MSXTools.data.SQLiteClient;
import es.tipolisto.MSXTools.utils.MSXPalette;
import es.tipolisto.MSXTools.utils.PaletteManager;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class PaletteManagerWindow extends JFrame {
	private static final long serialVersionUID = 8250293576909933495L;
	PaletteManager paletteManager;
	private JPanel contentPane;

	private JButton jButtonColor0,jButtonColor1,jButtonColor2,jButtonColor3,jButtonColor4,jButtonColor5;
	private JButton jButtonColor6,jButtonColor7,jButtonColor8,jButtonColor9,jButtonColor10;
	private JButton jButtonColor11,jButtonColor12,jButtonColor13,jButtonColor14,jButtonColor15;
	private JButton[] jButtons;
	private JTextField textFieldColorRed;
	private JTextField textFieldColorGreen;
	private JTextField textFieldColorBlue;

	private JLabel labelMsxColor;
	private JLabel lalbelHexageximalColor;
	private JTable table;
	private DefaultTableModel model;
	private JLabel labelSelected;
	private JLabel lblNewLabel_3;
	private JTextField textFieldNamepalette;
	private SQLiteClient sqliteClient;
	private ArrayList<Palette> arrayListpalette;
	private Palette paletteActive;
	private ColorPalette[] colorsPalettes;
	private int colorSelect;
	private JTextArea textAreaData;
	/**
	 * Create the frame.
	 */
	public PaletteManagerWindow() {
		//Configuraci�n del JFrame
		setTitle("Palette editor");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 646, 720);
		setResizable(false);
		setLocationRelativeTo(null);
		Color colorFondo=new Color(239,252,254);
	    setBackground(colorFondo);
	    setIconImage(new ImageIcon("data\\icon.png").getImage());
	    
	  
	    //En un jframe el jpanel es el que tiene los compnentes y la distribuci�n de estos
	    contentPane=new JPanel();
	    contentPane.setLayout(null);
		setContentPane(contentPane);
		initializeComponents();
		paletteManager=new PaletteManager();
		colorSelect=0;
		sqliteClient=new SQLiteClient();
		//1.Obtenemos la paleta activa(la que tiene isSelected==1) y los colores
		arrayListpalette=sqliteClient.getAllPalettes();

		//2.Obtenemos los colores y asignamos la lista activa
		for(Palette palette:arrayListpalette) {
			if(palette.getIsSelected()==1) {
				paletteActive=palette;
				updateColorsArrayAndLabel(palette);
			}
		}
		
		//2. le ponemos los colores a los botones según, los colores de la paleta activa
		colorsPalettes=sqliteClient.getColorsFromPalette(paletteActive);

		
		//4. Creamos los botones de los colores de la paleta
		jButtons=new JButton[16]; 
		int countPosition=0;
		int buttonPosiionX=0;
		int buttonPosiionY=400;
		for ( int i=0; i<16;i++) {
			JButton jButtonColor = new JButton(""+i);
			jButtonColor.setOpaque(true);
			
			if(countPosition==8) {
				countPosition=0;
				buttonPosiionY+=30;
			}
			buttonPosiionX=80*countPosition;
			jButtonColor.setBounds(buttonPosiionX, buttonPosiionY, 60, 20);
			jButtonColor.setBackground(new Color(colorsPalettes[i].getR(),colorsPalettes[i].getG(),colorsPalettes[i].getB()));
			contentPane.add(jButtonColor);
			//Al hacer click sobre algún botón..
			final int count=i;
			jButtonColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					colorSelect=count;
					//Obtenemos el seleccionado para marcarlo
					ColorPalette colorPalette=sqliteClient.getColorFromPalette(paletteActive,count);
					//System.out.println("Se encontro el  "+colorPalette.toString());
					RGB rgbColorPalette=new RGB(colorPalette.getR(), colorPalette.getG(), colorPalette.getB());
					//Rellenamos los textFileds de hexadecimal y código MSX 
					setTextFieldColors(rgbColorPalette, count);
					generateDataOnTextArea();
				}
			});
			jButtons[i]=jButtonColor;
			countPosition++;
		}

		JButton buttonCreateNewPaltte = new JButton("Create new Palette");
		buttonCreateNewPaltte.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
			
				 ColorPalette[] colorsPalettes=new ColorPalette[16];
				 //ColorPalette color0,color1,color2,color3,color4,color5,color6,color7,color8,color9,color10,color11,color12,color13,color14,color15;
				 for(int i=0;i<jButtons.length;i++) {
					 JButton jButton=jButtons[i];
					 ColorPalette colorPalette=new ColorPalette(0,"No_name"+i,jButton.getBackground().getRed(),jButton.getBackground().getGreen(),jButton.getBackground().getBlue());
					 sqliteClient.insertColor(colorPalette);
					 int lastId=sqliteClient.getLastIDInsertColor();
					 colorPalette.setId(lastId);
					 colorsPalettes[i]=colorPalette;
				 }
				
				//(int id, String name, int color0, int color1, int color2, int color3, int color4, int color5, int color6,int color7, int color8, int color9, int color10, int color11, int color12, int color13, int color14,int color15, int isSelected)
				Palette palette=null;
				if(textFieldNamepalette.getText().isEmpty())
					palette=new Palette(0,"no_name",colorsPalettes[0],colorsPalettes[1],colorsPalettes[2],colorsPalettes[3],colorsPalettes[4],colorsPalettes[5],colorsPalettes[6],colorsPalettes[7],colorsPalettes[8],colorsPalettes[9],colorsPalettes[10],colorsPalettes[11],colorsPalettes[12],colorsPalettes[13],colorsPalettes[14],colorsPalettes[15],0);
				else
					palette=new Palette(0,textFieldNamepalette.getText(),colorsPalettes[0],colorsPalettes[1],colorsPalettes[2],colorsPalettes[3],colorsPalettes[4],colorsPalettes[5],colorsPalettes[6],colorsPalettes[7],colorsPalettes[8],colorsPalettes[9],colorsPalettes[10],colorsPalettes[11],colorsPalettes[12],colorsPalettes[13],colorsPalettes[14],colorsPalettes[15],0);
				
				sqliteClient.insertPalette(palette);
				Object[] row=new Object[2];
				row[0]=sqliteClient.getLastIDInsertPalette();
				row[1]=palette.getName();
				model.addRow(row);
				textFieldNamepalette.setText("");
			}
		});
		buttonCreateNewPaltte.setBounds(10, 293, 575, 23);
		contentPane.add(buttonCreateNewPaltte);
		

		
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setBounds(10, 114, 800, 157);
		JScrollPane scrollPaneTable = new JScrollPane(table);
		scrollPaneTable.setBounds(10,114,247,157);
		scrollPaneTable.setViewportView(table);
		contentPane.add(scrollPaneTable);
		//table.createDefaultColumnsFromModel();
		model=(DefaultTableModel)table.getModel();
		
		table.setModel(model);
		

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//Cambiamos la paleta activa
				//sqliteClient.updateActivePalette(arrayListpalette,table.getSelectedRow(), paletteActive.getId());
				//señalamos la fila activa dentro de la table
				int selectRow=table.getSelectedRow();
				updateTable(selectRow);
				generateDataOnTextArea();
			}
		});
		
		//3.rellenamos la tabla
		fillTable(arrayListpalette);
		generateDataOnTextArea();
	}

	
	

	
	
	
	private void initializeComponents() {
		//final HashMap<MSXPalette, RGB> palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMS();
		textAreaData = new JTextArea();
		textAreaData.setBounds(299, 110, 274, 164);
		textAreaData.setLineWrap(true);
		contentPane.add(textAreaData);
		
		JLabel lblNewLabel_2 = new JLabel("Selected:");
		lblNewLabel_2.setBounds(10, 81, 93, 23);
		contentPane.add(lblNewLabel_2);
		
		labelSelected = new JLabel("");
		labelSelected.setBounds(66, 81, 519, 18);
		contentPane.add(labelSelected);
		
		lblNewLabel_3 = new JLabel("Name");
		lblNewLabel_3.setBounds(10, 352, 105, 14);
		contentPane.add(lblNewLabel_3);
		
		textFieldNamepalette = new JTextField();
		textFieldNamepalette.setBounds(110, 349, 475, 20);
		contentPane.add(textFieldNamepalette);
		textFieldNamepalette.setColumns(10);
		
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
		labelMsxColor.setBounds(279, 567, 46, 14);
		contentPane.add(labelMsxColor);
		

		JButton jButtonResetColors = new JButton("Reset colors");
		jButtonResetColors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetPalette();
			}
		});
		jButtonResetColors.setBounds(229, 612, 142, 23);
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
						  //String rgbComponent="red";
						  changeRGBOnPalette(colorSelect);
						  generateDataOnTextArea();
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
					  //String rgbComponent="green";
					  changeRGBOnPalette(colorSelect);
					  generateDataOnTextArea();
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
					  //String rgbComponent="blue";
					  changeRGBOnPalette(colorSelect);
					  generateDataOnTextArea();
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
	
	
	
	private void changeRGBOnPalette(int numberColor) {
		/*final HashMap<MSXPalette, RGB> palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMS();
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			if(colorSelect==entry.getKey().ordinal()) {
				if (rgbComponent.equals("red")) entry.getValue().setRed(Integer.valueOf(textFieldColorRed.getText()));
				if (rgbComponent.equals("green")) entry.getValue().setGreen(Integer.valueOf(textFieldColorGreen.getText()));
				if (rgbComponent.equals("blue")) entry.getValue().setBlue(Integer.valueOf(textFieldColorBlue.getText()));
				jButtons[colorSelect].setBackground(new Color(entry.getValue().getRed(),entry.getValue().getGreen(),entry.getValue().getBlue()));
			}
		}*/
		ColorPalette colorPalette=sqliteClient.getColorFromPalette(paletteActive, numberColor);
		int red=0;
		int green=0;
		int blue=0;
		red=Integer.valueOf(textFieldColorRed.getText());
		green=Integer.valueOf(textFieldColorGreen.getText());
		blue=Integer.valueOf(textFieldColorBlue.getText());
		if(red>=0 && red<256)
			colorPalette.setR(red);
		else
			textFieldColorRed.setText("");
		if(green>=0 && green<256)
			colorPalette.setG(green);
		else
			textFieldColorGreen.setText("");
		if(blue>=0 && blue<256)
			colorPalette.setB(blue);
		else
			textFieldColorBlue.setText("");
		sqliteClient.updateColor(colorPalette);
		jButtons[colorSelect].setBackground(new Color(red,green,blue));
		for(int i=0;i<16;i++) {
			if(i==numberColor) {
				colorsPalettes[i]=colorPalette;
			}
		}
		paletteActive.setColor0(colorsPalettes[0]);
		paletteActive.setColor1(colorsPalettes[1]);
		paletteActive.setColor2(colorsPalettes[2]);
		paletteActive.setColor3(colorsPalettes[3]);
		paletteActive.setColor4(colorsPalettes[4]);
		paletteActive.setColor5(colorsPalettes[5]);
		paletteActive.setColor6(colorsPalettes[6]);
		paletteActive.setColor7(colorsPalettes[7]);
		paletteActive.setColor8(colorsPalettes[8]);
		paletteActive.setColor9(colorsPalettes[9]);
		paletteActive.setColor10(colorsPalettes[10]);
		paletteActive.setColor11(colorsPalettes[11]);
		paletteActive.setColor12(colorsPalettes[12]);
		paletteActive.setColor13(colorsPalettes[13]);
		paletteActive.setColor14(colorsPalettes[14]);
		paletteActive.setColor15(colorsPalettes[15]);
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
		
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			jButtons[entry.getKey().ordinal()].setBackground(new Color(entry.getValue().getRed(),entry.getValue().getGreen(),entry.getValue().getBlue()));
			ColorPalette colorPalette=new ColorPalette(entry.getKey().ordinal(),entry.getKey().toString(),entry.getValue().getRed(),entry.getValue().getGreen(),entry.getValue().getBlue());
			sqliteClient.updateColor(colorPalette);
			//System.out.println("reseteo color"+colorPalette.getId());
			colorsPalettes[entry.getKey().ordinal()]=colorPalette;
		}

		paletteActive.setColor0(colorsPalettes[0]);
		paletteActive.setColor1(colorsPalettes[1]);
		paletteActive.setColor2(colorsPalettes[2]);
		paletteActive.setColor3(colorsPalettes[3]);
		paletteActive.setColor4(colorsPalettes[4]);
		paletteActive.setColor5(colorsPalettes[5]);
		paletteActive.setColor6(colorsPalettes[6]);
		paletteActive.setColor7(colorsPalettes[7]);
		paletteActive.setColor8(colorsPalettes[8]);
		paletteActive.setColor9(colorsPalettes[9]);
		paletteActive.setColor10(colorsPalettes[10]);
		paletteActive.setColor11(colorsPalettes[11]);
		paletteActive.setColor12(colorsPalettes[12]);
		paletteActive.setColor13(colorsPalettes[13]);
		paletteActive.setColor14(colorsPalettes[14]);
		paletteActive.setColor15(colorsPalettes[15]);
		
		JOptionPane.showMessageDialog(null, "Restored colors");
	}
	
	
	private void fillTable(ArrayList<Palette> arrayListPalette) {

		Object[] row=new Object[2];
		JTableHeader header = table.getTableHeader();
		TableColumnModel tableColumnModel = table.getColumnModel();

		//model.addColumn("Id");
		//tableColumnModel.getColumn(0).setMaxWidth(10);
		//tableColumnModel.getColumn(0).setPreferredWidth(10);
		//tableColumnModel.getColumn(0).setWidth(10);
		model.addColumn("id ");
		model.addColumn("Name");
		tableColumnModel.getColumn(0).setMaxWidth(60);
		tableColumnModel.getColumn(0).setPreferredWidth(60);
		tableColumnModel.getColumn(0).setWidth(60);
		


		/*for(int i=0; i<16;i++) {
			model.addColumn("color "+i);
			tableColumnModel.getColumn(i+1).setMaxWidth(20);
			tableColumnModel.getColumn(i+1).setPreferredWidth(20);
			tableColumnModel.getColumn(i+1).setWidth(20);
		}*/
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	    //header.repaint();
	    //table.getTableHeader().repaint();

		
	    int count=0;
		for(Palette palette:arrayListPalette) {
			ColorPalette colorPalette;
			//System.out.println(palette.toString());
			row[0]=palette.getId();
			row[1]=palette.getName();
			/*colorPalette = palette.getColor0();
			row[1]=colorPalette.toString();
			colorPalette = palette.getColor1();
			row[2]=colorPalette.toString();
			colorPalette = palette.getColor2();
			row[3]=colorPalette.toString();
			colorPalette = palette.getColor3();
			row[4]=colorPalette.toString();
			colorPalette = palette.getColor4();
			row[5]=colorPalette.toString();
			colorPalette = palette.getColor5();
			row[6]=colorPalette.toString();
			colorPalette = palette.getColor6();
			row[7]=colorPalette.toString();
			colorPalette = palette.getColor7();
			row[8]=colorPalette.toString();
			colorPalette = palette.getColor8();
			row[9]=colorPalette.toString();
			colorPalette = palette.getColor9();
			row[10]=colorPalette.toString();		
			colorPalette = palette.getColor10();
			row[11]=colorPalette.toString();
			colorPalette = palette.getColor11();
			row[12]=colorPalette.toString();
			colorPalette = palette.getColor12();
			row[13]=colorPalette.toString();
			colorPalette = palette.getColor13();
			row[14]=colorPalette.toString();
			colorPalette = palette.getColor14();
			row[15]=colorPalette.toString();
			colorPalette = palette.getColor15();
			row[16]=colorPalette.toString();*/
			
			model.addRow(row);
			//Con esto conseguimos que cuando se cargue la tabla se quede selecionada la fila con la paleta activa;
			if(palette.getIsSelected()==1) {
				paletteActive=palette;
				table.getSelectionModel().addSelectionInterval(count, count);
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
	
	private void updateColorsArrayAndLabel(Palette palette) {
		colorsPalettes=new ColorPalette[16];
		colorsPalettes[0]=palette.getColor0();
		colorsPalettes[1]=palette.getColor1();
		colorsPalettes[2]=palette.getColor2();
		colorsPalettes[3]=palette.getColor3();
		colorsPalettes[4]=palette.getColor4();
		colorsPalettes[5]=palette.getColor5();
		colorsPalettes[6]=palette.getColor6();
		colorsPalettes[7]=palette.getColor7();
		colorsPalettes[8]=palette.getColor8();
		colorsPalettes[9]=palette.getColor9();
		colorsPalettes[10]=palette.getColor10();
		colorsPalettes[11]=palette.getColor11();
		colorsPalettes[12]=palette.getColor12();
		colorsPalettes[13]=palette.getColor13();
		colorsPalettes[14]=palette.getColor14();
		colorsPalettes[15]=palette.getColor15();

		labelSelected.setText(palette.getName()+
				", color 0: "+colorsPalettes[0].toString()+
				", color 1: "+colorsPalettes[1].toString()+
				", color 2: "+colorsPalettes[2].toString()+
				", color 3: "+colorsPalettes[3].toString()+
				", ....");
	}
	
	private void updateTable(int selectRow) {
		table.getSelectionModel().addSelectionInterval(selectRow, selectRow);
		 //Obteemos la paleta activa
		for(int i=0;i<arrayListpalette.size();i++) {
			if(i==selectRow) {
				paletteActive=arrayListpalette.get(i);
			}
		}

		//System.out.println("el color 1 es "+colorsPalettes[1]);
		for(int i=0; i<colorsPalettes.length;i++) {
			ColorPalette colorPalette=colorsPalettes[i];
			jButtons[i].setBackground(new Color(colorPalette.getR(),colorPalette.getG(),colorPalette.getB()));
		}
		//Actualizamos los labels
		updateColorsArrayAndLabel(paletteActive);
	}
	
	private void generateDataOnTextArea() {
		int line=10201;
		StringBuilder stringBuilder=new StringBuilder();
		line+=1;
		stringBuilder.append(line+" DATA ");
		for(int i=0;i<colorsPalettes.length;i++) {
			ColorPalette colorPalette=colorsPalettes[i];
			int MSXColorRed=(colorPalette.getR()*7)/255;
			int MSXColorGreen=(colorPalette.getG()*7)/255;
			int MSXColorBlue=(colorPalette.getB()*7)/255;
			if(i<colorsPalettes.length-1)
				stringBuilder.append(MSXColorRed+","+MSXColorGreen+","+MSXColorBlue+",");
			else
				stringBuilder.append(MSXColorRed+","+MSXColorGreen+","+MSXColorBlue);
		}
		stringBuilder.append("\r\n");
		line+=1;
		stringBuilder.append(line+" FOR C=0 TO 15:READ R,G,B:COLOR=(C,R,G,B):NEXT\r\n");
		textAreaData.setText(stringBuilder.toString());
	}
}
