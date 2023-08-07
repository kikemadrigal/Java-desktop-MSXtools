package es.tipolisto.MSXTools.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;


import es.tipolisto.MSXTools.beans.Pixel;
import es.tipolisto.MSXTools.beans.RGB;
import es.tipolisto.MSXTools.beans.Sprite;
import es.tipolisto.MSXTools.dialogs.AutoDismiss;
import es.tipolisto.MSXTools.menus.MainMenuSpriteEditor;
import es.tipolisto.MSXTools.utils.MSXPalette;
import es.tipolisto.MSXTools.utils.NumberManager;
import es.tipolisto.MSXTools.utils.PaletteManager;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.Icon;

public class SpriteEditorWindow extends JFrame  {

	private static final long serialVersionUID = -5085349918068681072L;
	private JPanel panel;
	private PaneDrawableSpriteEditor canvas;
	private JTextArea textAreaDefinition,textAreaColor;
	private JLabel lblSpriteNumber,lblSpriteName;
	private JLabel lbltextAreaColor;
	private byte selectedColor;
	//0 background, 1 foregrounf
	private byte foreOrBackGround;
	private byte fileColor;
	private JButton[] jButtons0;
	private JButton[] jButtons1;
	private JButton btnSelectAllButtons0,btnSelectAllButtons1;


	private boolean setAllColors0;
	private boolean setAllColors1;
	private boolean sustituteColors0;
	private boolean sustituteColors1;
	private Color colorFondo;
	private JButton btnClearAll;
	private ArrayList<Sprite> arrayListSprites;
	//private int countSprites=0;
	private RiboonSpriteEditor riboon;
	private JPopupMenu jPopupMenuSprite;
	private MainMenuSpriteEditor menuBar;

	private JScrollPane jscrollPanelImageSprites;
	private Dimension dimensionJScrollPaneSprites;
	private byte screenMode;

	
	public SpriteEditorWindow() {
		//Configuración del JFrame
		setTitle("Sprite editor");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 896, 720);
		setResizable(false);
		setLocationRelativeTo(null);
		colorFondo=new Color(239,252,254);
	    setBackground(colorFondo);
	    
	    //En un jframe el jpanel es el que tiene los compnentes y la distribución de estos
		panel=new JPanel();
		panel.setLayout(null);
		setContentPane(panel);
		
		//La clase canvas (o donde se dibuja) trabaja con JButtons0, JButton1, TextAreaDefinition y TextAreaColor 
		canvas = new PaneDrawableSpriteEditor(16, 16);
		panel.add(canvas);
		
		initComponents();
	    
		selectedColor=10;
		foreOrBackGround=0;
		fileColor=0;
		setAllColors0=false;
		setAllColors1=false;
		sustituteColors0=false;
		sustituteColors1=false;
		arrayListSprites=new ArrayList<Sprite>();
		screenMode=5;
		// Creamos el comportamiento de menú emergente
		jPopupMenuSprite = new JPopupMenu();
		
		
		riboon = new RiboonSpriteEditor(canvas,arrayListSprites,
				lblSpriteNumber,lblSpriteName,
				textAreaDefinition,textAreaColor,
				jButtons0,jButtons1);	

		jscrollPanelImageSprites=new JScrollPane();
		dimensionJScrollPaneSprites=new Dimension(riboon.getWidth(),riboon.getHeight()-20);
		riboon.setDimension(dimensionJScrollPaneSprites);
		jscrollPanelImageSprites.setBounds(riboon.getX(),riboon.getY() ,riboon.getWidth(),riboon.getHeight());
		jscrollPanelImageSprites.setViewportView(riboon);
		riboon.setPreferredSize(dimensionJScrollPaneSprites);
		panel.add(jscrollPanelImageSprites);
		
		JCheckBox chckbxAutoSaved = new JCheckBox("  Auto saved");
		chckbxAutoSaved.setBounds(740, 62, 109, 23);
		chckbxAutoSaved.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chckbxAutoSaved.isSelected()) {
					canvas.setAutoSaved(true);
				}else {
					canvas.setAutoSaved(false);
				}
				
			}
		});
		panel.add(chckbxAutoSaved);
				
		menuBar = new MainMenuSpriteEditor(arrayListSprites, canvas, riboon, chckbxAutoSaved);
		canvas.setMainMenuSpriteEditor(menuBar);
		//Ponemos que el color de frente amarillo
		selectedColor=10;
		setAllColorButtonsWithActiveColor((byte)0);
		//Ponemos el comolor de fondo en negro
		selectedColor=0;
		setAllColorButtonsWithActiveColor((byte)1);
		
		
		JLabel lblScreen = new JLabel("Screen");
		lblScreen.setBounds(10, 62, 62, 23);
		panel.add(lblScreen);
		setJMenuBar(menuBar);
		
		
		JComboBox comboBoxScreen = new JComboBox();
		comboBoxScreen.setBounds(81, 63, 165, 22);
		comboBoxScreen.setBackground(Color.WHITE);
		comboBoxScreen.addItem("SC5");
		comboBoxScreen.addItem("SC2");
		comboBoxScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBoxScreen.getSelectedItem().equals("SC2")) {
					screenMode=2;
					textAreaColor.setVisible(false);
					lbltextAreaColor.setVisible(false);
					canvas.deleteAll();
					setAllColorButtonsWithActiveColor((byte)0);
				}else if(comboBoxScreen.getSelectedItem().equals("SC5")) {
					screenMode=5;
					textAreaColor.setVisible(true);
					lbltextAreaColor.setVisible(true);
					canvas.deleteAll();
				}	
			}
		});
		panel.add(comboBoxScreen);
		
		JComboBox comboBoxSpriteSize = new JComboBox();
		comboBoxSpriteSize.setBounds(383, 63, 124, 22);
		comboBoxSpriteSize.setBackground(Color.WHITE);
		comboBoxSpriteSize.addItem("16x16 pixels");
		comboBoxSpriteSize.addItem("8x8 pixels");
		comboBoxSpriteSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "WIP");
				/*canvas.deleteAll();
				setAllColorButtonsWithActiveColor((byte)0);
				if(comboBoxScreen.getSelectedItem().equals("8x8 pixels")) {
					canvas = new PaneDrawableSpriteEditor(8, 8);
				}else if(comboBoxScreen.getSelectedItem().equals("16x16 pixels")) {
					canvas = new PaneDrawableSpriteEditor(16, 16);
				}	
				panel.add(canvas);*/
			}
		});
		panel.add(comboBoxSpriteSize);
		
		JLabel lblSpriteSize = new JLabel("Sprite size");
		lblSpriteSize.setBounds(272, 66, 95, 14);
		panel.add(lblSpriteSize);
		

		

		

		

		


		

 
	
		addWindowListener(new WindowAdapter(){
		  //Este es el evento que se ejecuta cuando un JFrame se carga
		  public void windowOpened(WindowEvent e){
				//canvas.deleteAll();
			  AutoDismiss.showMessageDialog(null, "Sprite editor", "MSX Spain 2022", 100);
			  canvas.deleteAll();
			  canvas.fillTextAreaDefinitionAndColot();
		  }
		});
	
	}

	
	
	private void createSprite() {
		//1.Creamos el sprite
		// Sprite(int number, int x, int y, String name)
		Sprite sprite = new Sprite(arrayListSprites.size(), 0, 0, "No_name" + arrayListSprites.size());	
		//2Le metemos los textAreas
		sprite.setDataDefinition(textAreaDefinition.getText());
		sprite.setDataColors(textAreaColor.getText());
		
		//3 Le metemos los que haya en los botones del contentPane en ese momento
		JButton[] buttons0ContentPane=canvas.getjButtons0();
		JButton[] buttons1ContentPane=canvas.getjButtons1();
		Color[] colorsButtons0Sprite=new Color[16];
		Color[] colorsButtons1Sprite=new Color[16];
		for(int i=0;i<16;i++) {
			colorsButtons0Sprite[i]=buttons0ContentPane[i].getBackground();
			colorsButtons1Sprite[i]=buttons1ContentPane[i].getBackground();
			sprite.setColorButtons0(colorsButtons0Sprite);
			sprite.setColorButtons1(colorsButtons1Sprite);
		}

		//4.Vamos a meterle los pixeles que hay en el contentPane dibujados 
		Pixel[][] pixelsContentPane=canvas.getPixels();
		//Para eso crearemos una nueva definición de pixeles que tendrá el sprite
		Pixel[][] pixelsSprite=new Pixel[16][16];
		Color deleteColor=new Color(239,252,254);
		for(int y=0;y<pixelsSprite.length;y++) {
			for(int x=0;x<pixelsSprite[0].length;x++) {
				Pixel pixelContentPane=pixelsContentPane[x][y];
				//Pixel pixel=new Pixel((x*contentPane.getSizeTile())+contentPane.getBorderX(),(y*contentPane.getSizeTile())+contentPane.getBorderY(),(byte)2,deleteColor);
				Pixel pixelSprite=new Pixel(pixelContentPane.getPositionX(),pixelContentPane.getPositionY(),pixelContentPane.getForOrBrackground(),pixelContentPane.getColor());	
				pixelsSprite[x][y]=pixelSprite;
			}
		}
		sprite.setPixels(pixelsSprite);
		
		//7.Ponemos el nombre y el número
		sprite.setNumber(arrayListSprites.size());
		lblSpriteNumber.setText(String.valueOf(sprite.getNumber()));
		if(lblSpriteName.getText().isEmpty() ||lblSpriteName.getText().equals("") || lblSpriteName.getText().length()==0 ) {
			sprite.setName("Sprite_name"+sprite.getNumber());
			lblSpriteName.setText("No_name"+sprite.getNumber());
		}
		else {
			sprite.setName("Sprite_name"+String.valueOf(sprite.getNumber()));
			lblSpriteName.setText("No_name"+String.valueOf(sprite.getNumber()));
		}
		//5.Lo añadimos a la lista el JFrame
		arrayListSprites.add(sprite);
		//6.Actualizamos el rrboon para verlo
		riboon.updateRiboon(sprite);
	}
	private void updateSprite(Sprite sprite) {
		//1. Le metemos los textAreas
		sprite.setDataDefinition(textAreaDefinition.getText());
		sprite.setDataColors(textAreaColor.getText());
		
		//2 Le metemos los que haya en los botones del contentPane en ese momento
		JButton[] buttons0ContentPane=canvas.getjButtons0();
		JButton[] buttons1ContentPane=canvas.getjButtons1();
		Color[] colorsButtons0Sprite=new Color[16];
		Color[] colorsButtons1Sprite=new Color[16];
		for(int i=0;i<16;i++) {
			colorsButtons0Sprite[i]=buttons0ContentPane[i].getBackground();
			colorsButtons1Sprite[i]=buttons1ContentPane[i].getBackground();
			sprite.setColorButtons0(colorsButtons0Sprite);
			sprite.setColorButtons1(colorsButtons1Sprite);
		}

		//3.Vamos a meterle los pixeles que hay en el contentPane dibujados 
		Pixel[][] pixelsContentPane=canvas.getPixels();
		//Para eso crearemos una nueva definición de pixeles que tendrá el sprite
		Pixel[][] pixelsSprite=new Pixel[16][16];
		Color deleteColor=new Color(239,252,254);
		for(int y=0;y<pixelsSprite.length;y++) {
			for(int x=0;x<pixelsSprite[0].length;x++) {
				Pixel pixelContentPane=pixelsContentPane[x][y];
				//Pixel pixel=new Pixel((x*contentPane.getSizeTile())+contentPane.getBorderX(),(y*contentPane.getSizeTile())+contentPane.getBorderY(),(byte)2,deleteColor);
				Pixel pixelSprite=new Pixel(pixelContentPane.getPositionX(),pixelContentPane.getPositionY(),pixelContentPane.getForOrBrackground(),pixelContentPane.getColor());	
				pixelsSprite[x][y]=pixelSprite;
			}
		}
		sprite.setPixels(pixelsSprite);
		
		//4.Ponemos el nombre y el número
		sprite.setNumber(sprite.getNumber());
		lblSpriteNumber.setText(String.valueOf(sprite.getNumber()));
		if(lblSpriteName.getText().isEmpty() ||lblSpriteName.getText().equals("") || lblSpriteName.getText().length()==0 ) {
			sprite.setName("Sprite_name"+sprite.getNumber());
			lblSpriteName.setText("No_name"+sprite.getNumber());
		}
		else {
			sprite.setName("Sprite_name"+String.valueOf(sprite.getNumber()));
			lblSpriteName.setText("No_name"+String.valueOf(sprite.getNumber()));
		}
		//5.Lo actualizamos en la lista del jframe
		arrayListSprites.remove(sprite.getNumber());
		arrayListSprites.add(sprite.getNumber(),sprite);
	}
	
	
	
	private void changeColorButton() {
		HashMap<MSXPalette, RGB> palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMSForSprites();
		//Determinamos si vamos a leer los botones de back o foreground - 0 es background
		if(foreOrBackGround==0) {
			for(int i=0;i<jButtons0.length;i++) {
				//Seleccionamos el botón al que le vamos a cambiar el color
				if(i==fileColor) {
					//Le cambiamos el color
					for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
						if(entry.getKey().ordinal()==selectedColor) {
							//Si el color es el transparente
							if(entry.getKey().ordinal()==0) {
								jButtons0[i].setBackground(colorFondo);
							}else {
								Color color=new Color(entry.getValue().getRed(),entry.getValue().getGreen(),entry.getValue().getBlue());
								jButtons0[i].setBackground(color);
							}

							canvas.setjButtons0(jButtons0);
						}
					}
				}

			}
		}else {
			for(int i=0;i<jButtons1.length;i++) {
				if(i==fileColor) {
					for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
						if(entry.getKey().ordinal()==selectedColor) {
							//System.out.println("foreground "+foreOrBackGround+" file: "+fileColor+" selected: "+selectedColor);
							Color color=new Color(entry.getValue().getRed(),entry.getValue().getGreen(),entry.getValue().getBlue());
							jButtons1[i].setBackground(color);
							canvas.setjButtons1(jButtons1);
						}
							
					}
				}
			}
		}
	}
	

	
	private void setAllColorButtonsWithActiveColor(byte backOrFront) {
		Color colorSelected=getActiveColor();
		if(backOrFront==0) {
			btnSelectAllButtons0.setBackground(colorSelected);
			for(int i=0;i<jButtons0.length;i++) {
				jButtons0[i].setBackground(colorSelected);
				substituteColors((byte)i,(byte)0);
			}
		}else {
			btnSelectAllButtons1.setBackground(colorSelected);
			for(int i=0;i<jButtons1.length;i++) {
				jButtons1[i].setBackground(colorSelected);
				substituteColors((byte)i,(byte)1);
			}
		}
	}
	
	private Color getActiveColor() {
		HashMap<MSXPalette, RGB> palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMSForSprites();
		Color colorSelected=new Color(0,0,0);
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			if(entry.getKey().ordinal()==selectedColor) {
				if(entry.getKey().ordinal()==0) {
					colorSelected=colorFondo;
				}else {
					//System.out.println("foreground "+foreOrBackGround+" file: "+fileColor+" selected: "+selectedColor);
					colorSelected=new Color(entry.getValue().getRed(),entry.getValue().getGreen(),entry.getValue().getBlue());
				}
			}
		}
		return colorSelected;
	}
	

	

	
	
	private void substituteColors(byte fileCount,byte foreOrBackGround ) {
		//System.out.println("sustituteColors dice color recibido: "+fileCount+", fOb: "+foreOrBackGround);
		Pixel[][] pixels;
		pixels=canvas.getPixels();
		int sizeTile=canvas.getSizeTile();
		int borderX=canvas.getBorderX();
		int borderY=canvas.getBorderY();
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];
				Color color=pixel.getColor();
				int positionColorOnPalette=PaletteManager.getPositionColorOnPalette(color);
				String cadenaValorHexadecimal=NumberManager.decimalAHexadecimal(positionColorOnPalette);
				byte frontOrBackgrounOnPixel=pixel.getForOrBrackground();
				//Cambiamos los pixeles de la fila
				if(y==fileCount) {
					if(pixel.getForOrBrackground()==foreOrBackGround) {
						canvas.paintPixelAtPoint(pixel.getPositionX(),pixel.getPositionY(),foreOrBackGround);
						//System.out.println("(x:"+pixel.getPositionX()+"y:"+pixel.getPositionY()+")");
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void initComponents() {
		/**
		 * TEXTAREA
		 */
		//En los textxtArea salen los datas de los sprites
		JLabel lblTextAreaDefinition = new JLabel("Definition");
		lblTextAreaDefinition.setBounds(452, 90, 81, 14);
		panel.add(lblTextAreaDefinition);
		
		lbltextAreaColor = new JLabel("Color");
		lbltextAreaColor.setBounds(659, 90, 46, 14);
		panel.add(lbltextAreaColor);
		
		textAreaColor = new JTextArea();
		textAreaColor.setFont(new Font("Monospaced", Font.PLAIN, 16));
		textAreaColor.setBounds(659, 111, 190, 329);
		textAreaColor.setLineWrap(true);
		panel.add(textAreaColor);
		canvas.setTextAreaColor(textAreaColor);
		
		textAreaDefinition = new JTextArea();
		textAreaDefinition.setFont(new Font("Monospaced", Font.PLAIN, 16));
		textAreaDefinition.setBounds(462, 111, 190, 329);
		textAreaDefinition.setLineWrap(true);
		panel.add(textAreaDefinition);
		canvas.setTextAreaDefinition(textAreaDefinition);
		/**
		 * END TEXTAREA
		 */
	   
		
		
		/**
		 * TOP BUTTONS for sprite editing
		 */
		//Bote de pintura
		JButton btnPaintPot = new JButton(new ImageIcon("data\\bote.png"));
		btnPaintPot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!canvas.isPaintBot()) {
					btnPaintPot.setBackground(Color.YELLOW);
					canvas.setPaintBot(true);
				}	
				else {
					btnPaintPot.setBackground(null);
					canvas.setPaintBot(false);
				}
			}
		});
		btnPaintPot.setBounds(10, 11, 40, 40);
		panel.add(btnPaintPot);
	
		//Limpieza de un pixel del canvas
		JButton btnClearPixels = new JButton(new ImageIcon("data\\goma.png"));
		btnClearPixels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				foreOrBackGround=2;
				if(!canvas.isSelectedClear()) {
					btnClearPixels.setBackground(Color.YELLOW);
					canvas.setSelectedClear(true);
				}	
				else {
					btnClearPixels.setBackground(null);
					canvas.setSelectedClear(false);
				}
						
			}
		});
		btnClearPixels.setBounds(71, 11, 40, 40);
		panel.add(btnClearPixels);
		
		//limpieza de todo el canvas
		btnClearAll = new JButton(new ImageIcon("data\\papelera.png"));
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.deleteAll();
				canvas.fillTextAreaDefinitionAndColot();
			}
		});
		btnClearAll.setBounds(135, 11, 40, 40);
		panel.add(btnClearAll);
		/**
		 * END TOP BUTTONS 
		 */

		

		/**
		 * MENU COLORS BUTTONS
		 */
		//menu that appears when clicking on the general color buttons
		//Cremos los menús para los colores y le asignamos los comportamientos 
		JPopupMenu jPopupMenuColors=new JPopupMenu();
		for(int i=0;i<16;i++) {
			JMenuItem menuColor=new JMenuItem(""+i);
			menuColor.setBackground(PaletteManager.getColorFromPallete(i));
			jPopupMenuColors.add(menuColor);
			final byte count=(byte)i;
			menuColor.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectedColor=count;
					//Esto es que previamente hemos pinchado en uno de los botones generales de color
					// y queremos cambiar de color a todos los botones de la columna0 o columna1
					if(setAllColors0) {
						setAllColorButtonsWithActiveColor((byte)0);
						setAllColors0=false;
					}
					else if(setAllColors1) {
						setAllColorButtonsWithActiveColor((byte)1);
						setAllColors1=false;
					}else {
						changeColorButton();
					}
					//Esto significa que hemos pinchado en uno de los botones del menú
					//y queremos cambiar los pixeles de una fila
					if(sustituteColors0) {
						if(screenMode==5) {
							substituteColors(fileColor,(byte)0);
							sustituteColors0=false;
						}else {
							JOptionPane.showMessageDialog(null, "Change the screen mode");
						}

					}else if(sustituteColors1) {
						if(screenMode==5) {
							substituteColors(fileColor,(byte)1);
							sustituteColors1=false;
						}else {
							JOptionPane.showMessageDialog(null, "Change the screen mode");
						}
					}
					canvas.fillTextAreaDefinitionAndColot();
				}
			});
		}
		/**
		 * END MENU COLORS BUTTONS
		 */		
		


		/**
		 * COLOR BUTTONS
		 */
		//Creamos los botones foreground y background
		jButtons0=new JButton[16];
		for(int i=0;i<jButtons0.length;i++) {
			JButton jButton=new JButton("");
			jButton.setBounds(10, 120+(i*20), 40, 20);
			panel.add(jButton);
			final byte count=(byte)i;
			jButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(screenMode==5) {
						foreOrBackGround=0;
						fileColor=count;
						jPopupMenuColors.show(jButton,jButton.getX()/canvas.getSizeTile(), jButton.getY()/canvas.getSizeTile());
						sustituteColors0=true;
					}
				}
			});
			jButtons0[i]=jButton;
		}
		canvas.setjButtons0(jButtons0);
		
		jButtons1=new JButton[16];
		for(int i=0;i<jButtons1.length;i++) {
			JButton jButton=new JButton("");
			jButton.setBounds(60, 120+(i*20), 40, 20);
			panel.add(jButton);
			final byte count=(byte)i;
			jButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(screenMode==5) {
						//System.out.println("has pinchado en uno de los botones de la derecha");
						foreOrBackGround=1;
						fileColor=count;
						jPopupMenuColors.show(jButton,jButton.getX()/canvas.getSizeTile(), jButton.getY()/canvas.getSizeTile());
						sustituteColors1=true;
					}
				}
			});
			jButtons1[i]=jButton;
			canvas.setjButtons1(jButtons1);
			jButton.setEnabled(false);
			jButton.setVisible(false);
		}
		/**
		 * END COLOR BUTTONS
		 */

		
		
		/**
		 * COLOR GENERAL BUTTONS
		 */
		//Estos botones asgnar�n los colores de golpe a toda la columna
		btnSelectAllButtons0 = new JButton("");
		btnSelectAllButtons0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jPopupMenuColors.show(btnSelectAllButtons0,btnSelectAllButtons0.getX()/canvas.getSizeTile(), btnSelectAllButtons0.getY()/canvas.getSizeTile());
				setAllColors0=true;
			}
		});
		btnSelectAllButtons0.setBounds(10, 90, 40, 20);
		panel.add(btnSelectAllButtons0);
		
		btnSelectAllButtons1 = new JButton("");
		btnSelectAllButtons1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//setAllColorButtonsWithActiveColor((byte)1);
				jPopupMenuColors.show(btnSelectAllButtons1,btnSelectAllButtons1.getX()/canvas.getSizeTile(), btnSelectAllButtons1.getY()/canvas.getSizeTile());
				setAllColors1=true;
			}
		});
		btnSelectAllButtons1.setBounds(60, 90, 40, 20);
		panel.add(btnSelectAllButtons1);
		btnSelectAllButtons1.setEnabled(false);
		btnSelectAllButtons1.setVisible(false);
		/**
		 * END COLOR GENERAL BUTTONS
		 */
		
		
		/**
		 * LABELS SPRITES
		 */
		JLabel lblNextToNumberSprite = new JLabel("Number");
		lblNextToNumberSprite.setBounds(119, 96, 46, 14);
		panel.add(lblNextToNumberSprite);
		
		lblSpriteNumber = new JLabel("");
		lblSpriteNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		//lblSpriteNumber.setBackground(null);
		lblSpriteNumber.setBounds(175, 96, 62, 14);
		lblSpriteNumber.setOpaque(true);
		panel.add(lblSpriteNumber);

		
		JLabel lblNextToNameSprite = new JLabel("Name:");
		lblNextToNameSprite.setBounds(247, 96, 46, 14);
		panel.add(lblNextToNameSprite);
		
		lblSpriteName = new JLabel("");
		lblSpriteName.setFont(new Font("Tahoma", Font.BOLD, 14));
		//lblSpriteName.setBackground(null);
		lblSpriteName.setBounds(303, 96, 131, 14);
		lblSpriteName.setOpaque(true);
		panel.add(lblSpriteName);
		/**
		 * END LABELS SPRITES
		 */
		
		
		/**
		 * BUTTONS FOR SPRITES EDITING
		 */
		//Estos botones trabajan con la clase riboon
		JButton btnSpriteAdd = new JButton(new ImageIcon("data\\agregar.png"));
		btnSpriteAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createSprite();
			}
		});
		btnSpriteAdd.setBounds(119, 451, 40, 40);
		panel.add(btnSpriteAdd);
		
		JButton btnSpriteUpdate = new JButton(new ImageIcon("data\\actualizar.png"));
		btnSpriteUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("El sprite selecionado es "+riboon.getSpriteSelected());
				int numberSpriteSelected=riboon.getSpriteSelected().getNumber();
				for(int i=0;i<arrayListSprites.size();i++) {
					Sprite sprite=arrayListSprites.get(i);
					if(sprite.getNumber()==numberSpriteSelected) {
						//System.out.println("actualizado el  "+sprite.getNumber());
						updateSprite(sprite);
						//Actualizamos el riboon para verlo
						riboon.updateRiboon(sprite);
					}
				}
				
			}
		});
		btnSpriteUpdate.setBounds(185, 451, 40, 40);
		panel.add(btnSpriteUpdate);
		//btnSpriteUpdate.setEnabled(false);
		
		
		JButton btnSpriteLeft = new JButton(new ImageIcon("data\\left.png"));
		btnSpriteLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				riboon.moveSpriteLeftOnRiboon();
				riboon.updateAllRiboon();
				riboon.updateBorders(riboon.getSpriteSelected());
			}
		});
		btnSpriteLeft.setBounds(247, 451, 40, 40);
		panel.add(btnSpriteLeft);
		//btnSpriteLeft.setEnabled(false);
		
		JButton btnSpriteRight = new JButton(new ImageIcon("data\\right.png"));
		btnSpriteRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				riboon.moveSpriteRightOnRiboon();
				riboon.updateAllRiboon();
				riboon.updateBorders(riboon.getSpriteSelected());
			}
		});
		btnSpriteRight.setBounds(311, 451, 40, 40);
		panel.add(btnSpriteRight);
		
		JButton btnDeleteSprite = new JButton(new ImageIcon("data\\delete-sprite.png"));
		btnDeleteSprite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				riboon.deleteSprite(riboon.getSpriteSelected());
			}
		});
		btnDeleteSprite.setBounds(820, 451, 40, 40);
		panel.add(btnDeleteSprite);
		
		
		
		JButton btnGirarHorizontal = new JButton(new ImageIcon("data\\girar-horizontal.png"));
		btnGirarHorizontal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.rotateLandscape();
			}
		});
		btnGirarHorizontal.setBounds(373, 11, 40, 40);
		panel.add(btnGirarHorizontal);
		
		JButton btnGirarvertical = new JButton(new ImageIcon("data\\girar-vertical.png"));
		btnGirarvertical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.rotateUpright();
			}
		});
		btnGirarvertical.setBounds(438, 11, 40, 40);
		panel.add(btnGirarvertical);
		
		JButton btnGirarIzquierda = new JButton(new ImageIcon("data\\rotar-izquierda.png"));
		btnGirarIzquierda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.turnLeft();
			}
		});
		btnGirarIzquierda.setBounds(501, 11, 40, 40);
		panel.add(btnGirarIzquierda);
		
		JButton btnGirarDerecha = new JButton(new ImageIcon("data\\rotar-derecha.png"));
		btnGirarDerecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.turnRight();
			}
		});
		btnGirarDerecha.setBounds(560, 11, 40, 40);
		panel.add(btnGirarDerecha);
		btnGirarDerecha.setEnabled(false);
		
		JButton btnMoverUnoALaIzquierda = new JButton(new ImageIcon("data\\desplaza-uno-izquierda.png"));
		btnMoverUnoALaIzquierda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.moveOneLeft();
			}
		});
		btnMoverUnoALaIzquierda.setBounds(622, 11, 40, 40);
		panel.add(btnMoverUnoALaIzquierda);
		
		JButton btnMoverUnoALaDerecha = new JButton(new ImageIcon("data\\desplaza-uno-derecha.png"));
		btnMoverUnoALaDerecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.moveOneRight();
			}
		});
		btnMoverUnoALaDerecha.setBounds(682, 11, 40, 40);
		panel.add(btnMoverUnoALaDerecha);
		
		JButton btnMoverUnoArriba = new JButton(new ImageIcon("data\\desplaza-uno-arriba.png"));
		btnMoverUnoArriba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.moveOneUp();
			}
		});
		btnMoverUnoArriba.setBounds(743, 11, 40, 40);
		panel.add(btnMoverUnoArriba);
		
		JButton btnMoverUnoAbajo = new JButton(new ImageIcon("data\\desplaza-uno-abajo.png"));
		btnMoverUnoAbajo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.moveOneDown();
			}
		});
		btnMoverUnoAbajo.setBounds(808, 11, 40, 40);
		panel.add(btnMoverUnoAbajo);
		
		
		
		
		
		
		/**
		 * END SBUTTONS FOR SPRITES EDITING
		 */

	}
}
