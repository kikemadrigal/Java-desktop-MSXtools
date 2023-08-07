package es.tipolisto.MSXTools.gui.pixelEditor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;


import es.tipolisto.MSXTools.beans.Pixel;
import es.tipolisto.MSXTools.beans.RGB;
import es.tipolisto.MSXTools.beans.Sprite;
import es.tipolisto.MSXTools.dialogs.AutoDismiss;
import es.tipolisto.MSXTools.gui.PaletteManagerWindow;
import es.tipolisto.MSXTools.utils.MSXPalette;
import es.tipolisto.MSXTools.utils.NumberManager;
import es.tipolisto.MSXTools.utils.PaletteManager;

import javax.swing.JLabel;
import javax.swing.JMenu;
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
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class SpriteEditorWindow extends JFrame  {

	private static final long serialVersionUID = -5085349918068681072L;
	private JPanel panel;
	private PaneDrawableSpriteEditor canvas;
	private JTextArea textAreaDefinition,textAreaColor;
	private JLabel lblSpriteNumberOnCanvas,lblNumberSpriteRiboon,lblNameSpriteRiboon;
	private JTextField jtFieldName;
	private JLabel lbltextAreaColor;
	private byte selectedColor;
	//0 background, 1 foregrounf
	private byte foreOrBackGround;
	private byte fileColor;
	private JButton[] jButtons0;
	private JButton[] jButtons1;
	private JButton btnSelectAllButtons0,btnSelectAllButtons1;
	//private JComboBox<String> comboBoxScreenInEditor,comboBoxTypeSpriteInEditor;
	private JCheckBox chckbxAutoSaved, chckbxSpritesAudmented;
	private boolean setAllColors0;
	private boolean setAllColors1;
	private boolean sustituteColors0;
	private boolean sustituteColors1;
	private Color colorFondo;
	private JButton btnClearAll;
	private ArrayList<Sprite> arrayListSprites;
	private JLabel lblScreenOrSpriteNumCOlors,lblSprteSize;
	private RiboonSpriteEditor riboon;
	private JPopupMenu jPopupMenuSprite;
	private MainMenuSpriteEditor menuBar;
	private JScrollPane jscrollPanelImageSprites;
	private Dimension dimensionJScrollPaneSprites;
	private byte screenMode;
	private boolean startAnimation;
	//spriteType=8 sprites de 8x8, spriteType=16 sprites de 16x16px
	private byte spriteType;
	//spriteNUmColors=0 para screen 0, 1 para screen 1,etc
	private byte spriteNumColorsOrScreenMode;

	
	public SpriteEditorWindow(byte spriteType, byte spriteNumColorsOrScreenMode) {
		//Configuración del JFrame
		setTitle("Sprite editor");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 896, 720);
		setResizable(false);
		setLocationRelativeTo(null);
		colorFondo=new Color(239,252,254);
	    setBackground(colorFondo);
	    ImageIcon imageIcon=new ImageIcon(getClass().getResource("/images/icon.png"));
	    setIconImage(imageIcon.getImage());
	    
	    //En un jframe el jpanel es el que tiene los compnentes y la distribución de estos
		panel=new JPanel();
		panel.setLayout(null);
		setContentPane(panel);
		

		this.spriteType=spriteType;
		this.spriteNumColorsOrScreenMode=spriteNumColorsOrScreenMode;
		//La clase canvas (o donde se dibuja) trabaja con JButtons0, JButton1, TextAreaDefinition y TextAreaColor 
		canvas = new PaneDrawableSpriteEditor(spriteType, spriteType);
		panel.add(canvas);
		
		arrayListSprites=new ArrayList<Sprite>();
		initComponents();
	    
		selectedColor=10;
		foreOrBackGround=0;
		fileColor=0;
		setAllColors0=false;
		setAllColors1=false;
		sustituteColors0=false;
		sustituteColors1=false;
		startAnimation=false;

		jPopupMenuSprite = new JPopupMenu();
		
		
		riboon = new RiboonSpriteEditor(canvas,arrayListSprites,
				lblSpriteNumberOnCanvas,lblNumberSpriteRiboon,lblNameSpriteRiboon,jtFieldName,
				textAreaDefinition,textAreaColor,
				jButtons0,jButtons1);	

		jscrollPanelImageSprites=new JScrollPane();
		dimensionJScrollPaneSprites=new Dimension(riboon.getWidth(),riboon.getHeight()-20);
		riboon.setDimension(dimensionJScrollPaneSprites);
		jscrollPanelImageSprites.setBounds(riboon.getX(),riboon.getY() ,riboon.getWidth(),riboon.getHeight());
		jscrollPanelImageSprites.setViewportView(riboon);
		riboon.setPreferredSize(dimensionJScrollPaneSprites);
		panel.add(jscrollPanelImageSprites);
		

				
		menuBar = new MainMenuSpriteEditor(arrayListSprites, canvas, riboon, chckbxAutoSaved, 
				lblSpriteNumberOnCanvas,jtFieldName,spriteNumColorsOrScreenMode,this);
		//menuBar=null;
		canvas.setMainMenuSpriteEditor(menuBar);
		setJMenuBar(menuBar);
		
		
		//Ponemos que el color de frente amarillo
		selectedColor=10;
		setAllColorButtonsWithActiveColor((byte)0);
		//Ponemos el comolor de fondo en negro
		selectedColor=1;
		setAllColorButtonsWithActiveColor((byte)1);
		selectedColor=10;
		checkStateForScreenChange(spriteNumColorsOrScreenMode);
		lblScreenOrSpriteNumCOlors.setText("Screen "+spriteNumColorsOrScreenMode);
		lblSprteSize.setText("Size "+spriteType+"x"+spriteType+" pixels");
		
		JButton btnConfigurePalette = new JButton("Configure Palette");
		btnConfigurePalette.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PaletteManagerWindow frame = new PaletteManagerWindow();
				frame.setVisible(true);
			}
		});
		btnConfigurePalette.setBounds(682, 58, 167, 23);
		panel.add(btnConfigurePalette);
		
 
	
		addWindowListener(new WindowAdapter(){
		  //Este es el evento que se ejecuta cuando un JFrame se carga
		  public void windowOpened(WindowEvent e){
				//canvas.deleteAll();
			  AutoDismiss.showMessageDialog(null, "Sprite editor", "MSX Spain 2022", 700);
			  canvas.deleteAll();
			  canvas.fillTextAreaDefinitionAndColor();
		  }
		});
	
	}

	public MainMenuSpriteEditor getMainMenuSpriteEditor() {
		return this.menuBar;
	}
	public void setSpriteNumColorsOrScreenMode(byte spriteNumColorsOrScreenMode) {
		this.spriteNumColorsOrScreenMode=spriteNumColorsOrScreenMode;
		lblScreenOrSpriteNumCOlors.setText("Screen "+spriteNumColorsOrScreenMode);
	}
	public void setSpriteType(byte spriteType) {
		this.spriteType=spriteType;
		lblSprteSize.setText("Size "+spriteType+"x"+spriteType+" pixels");
	}
	
	public void setArrayListSprites(Sprite[] sprites) {
		for (int i=0;i<sprites.length;i++) {
			Sprite sprite=sprites[i];
			arrayListSprites.add(sprite);
			riboon.updateRiboon(sprite);
		}
	}
	public void showOrHideColorsTextAreaAndLabel(boolean showOrHideTextAreaAndLabelColor) {
		textAreaColor.setVisible(showOrHideTextAreaAndLabelColor);
		lbltextAreaColor.setVisible(showOrHideTextAreaAndLabelColor);
	}
	public PaneDrawableSpriteEditor getCanvas() {
		return this.canvas;
	}
	public void setCanvas(PaneDrawableSpriteEditor canvas) {
		this.canvas=canvas;
	}
	private void createSprite() {
		int positionSprite=arrayListSprites.size();
		//1.Creamos el sprite
		// Sprite(int number, int x, int y, String name)
		Sprite sprite = new Sprite(positionSprite, 0, 0, "No_name" +positionSprite,spriteType, spriteNumColorsOrScreenMode);	
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
		Pixel[][] pixelsSprite=new Pixel[spriteType][spriteType];
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
		//le asignamos el screen y el tipo (8x8 o 16x16)
		sprite.setType(spriteType);
		sprite.setNumColors(spriteNumColorsOrScreenMode);
		//5.Ponemos el nombre y el número
		sprite.setNumber(positionSprite);
		lblSpriteNumberOnCanvas.setText(String.valueOf(sprite.getNumber()));
		sprite.setName("Sprite-"+sprite.getNumber());
		String textFromTextField=jtFieldName.getText();
		//Si no está vacío y no tiene el texto por defecto
		if (!textFromTextField.isEmpty() && !textFromTextField.matches("^Sprite-[0-100]$")) {
			sprite.setName(jtFieldName.getText());
		}else {
			jtFieldName.setText("Sprite-"+sprite.getNumber());
		}
		//7.Lo añadimos a la lista el JFrame 
		arrayListSprites.add(sprite);
		//8.Creamos el botón del ribbon
		riboon.updateRiboon(sprite);
		//9.Actualizamos el riboon para verlo, lo seleccionamos y ponemos el borde
		riboon.updateBorders(sprite);
		//10 selo metemos al canvas, como el que se está dubjando
		canvas.setSpriteOnCanvasSelected(sprite);
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
		Pixel[][] pixelsSprite=new Pixel[spriteType][spriteType];
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
		lblSpriteNumberOnCanvas.setText(String.valueOf(sprite.getNumber()));
		if(jtFieldName.getText().isEmpty()) {
			sprite.setName("Sprite_name"+sprite.getNumber());
			jtFieldName.setText("No_name"+sprite.getNumber());
		}
		else {
			sprite.setName(jtFieldName.getText());
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
		int sizeTile=canvas.getPixelSizeOfRenderCanvas();
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
	
	private void changeNameSpriteCanvas() {
		if(arrayListSprites.size()>0 ||arrayListSprites!=null ) {
			for(int i=0;i<arrayListSprites.size();i++) {
				if(canvas.getSpriteOnCanvasSelected().getNumber()==arrayListSprites.get(i).getNumber()) {
					Sprite sprite=arrayListSprites.get(i);
					sprite.setName(jtFieldName.getText());
				}
			}
		}
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void initComponents() {
		/**
		 * GENERAL COMPONENTS
		 */
		lblScreenOrSpriteNumCOlors = new JLabel("Screen X");
		lblScreenOrSpriteNumCOlors.setBounds(20, 62, 155, 14);
		panel.add(lblScreenOrSpriteNumCOlors);
		
		lblSprteSize = new JLabel("Sprite size X");
		lblSprteSize.setBounds(120, 62, 211, 14);
		panel.add(lblSprteSize);
		
		chckbxSpritesAudmented = new JCheckBox("Sprites augmented");
		chckbxSpritesAudmented.setBounds(337, 58, 204, 23);
		chckbxSpritesAudmented.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chckbxSpritesAudmented.isSelected()) {
					menuBar.setSpriteAudmented(true);
				}else {
					menuBar.setSpriteAudmented(false);
				}
				
			}
		});
		panel.add(chckbxSpritesAudmented);
		
		chckbxAutoSaved = new JCheckBox("  Auto saved");
		chckbxAutoSaved.setBounds(543, 58, 109, 23);
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
		/**
		 * END GENERAL COMPONENTS
		 */
		
		
		
		
		/**
		 * TEXTAREA
		 */
		//En los textxtArea salen los datas de los sprites
		JLabel lblTextAreaDefinition = new JLabel("Definition");
		lblTextAreaDefinition.setBounds(452, 90, 81, 14);
		panel.add(lblTextAreaDefinition);
		
		lbltextAreaColor = new JLabel("Color");
		lbltextAreaColor.setBounds(659, 90, 46, 14);
		lbltextAreaColor.setVisible(true);
		panel.add(lbltextAreaColor);
		
		textAreaColor = new JTextArea();
		textAreaColor.setFont(new Font("Monospaced", Font.PLAIN, 16));
		textAreaColor.setBounds(659, 111, 190, 329);
		textAreaColor.setLineWrap(true);
		textAreaColor.setVisible(true);
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
		//JButton btnPaintPot = new JButton(new ImageIcon("data\\bote.png"));
		JButton btnPaintPot = new JButton(new ImageIcon(getClass().getResource("/images/bote.png")));
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
		JButton btnClearPixels = new JButton((new ImageIcon(getClass().getResource("/images/goma.png"))));
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
		btnClearAll = new JButton(new ImageIcon(getClass().getResource("/images/papelera.png")));
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.deleteAll();
				canvas.fillTextAreaDefinitionAndColor();
				lblSpriteNumberOnCanvas.setText("");
				jtFieldName.setText("");
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
						if(spriteNumColorsOrScreenMode>=4) {
							substituteColors(fileColor,(byte)0);
							sustituteColors0=false;
						}
					}else if(sustituteColors1) {
						if(spriteNumColorsOrScreenMode>=4) {
							substituteColors(fileColor,(byte)1);
							sustituteColors1=false;
						}
					}
					canvas.fillTextAreaDefinitionAndColor();
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
			jButton.setBounds(50, 120+(i*20), 40, 20);
			panel.add(jButton);
			final byte count=(byte)i;
			jButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//A partir el screen 4 es cuando se puede seleccionar el color
					if(spriteNumColorsOrScreenMode>=4) {
						foreOrBackGround=0;
						fileColor=count;
						jPopupMenuColors.show(jButton,jButton.getX()/canvas.getPixelSizeOfRenderCanvas(), jButton.getY()/canvas.getPixelSizeOfRenderCanvas());
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
					//A partir el screen 4 es cuando se puede seleccionar el color
					if(spriteNumColorsOrScreenMode>=4) {
						//System.out.println("has pinchado en uno de los botones de la derecha");
						foreOrBackGround=1;
						fileColor=count;
						jPopupMenuColors.show(jButton,jButton.getX()/canvas.getPixelSizeOfRenderCanvas(), jButton.getY()/canvas.getPixelSizeOfRenderCanvas());
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
				jPopupMenuColors.show(btnSelectAllButtons0,btnSelectAllButtons0.getX()/canvas.getPixelSizeOfRenderCanvas(), btnSelectAllButtons0.getY()/canvas.getPixelSizeOfRenderCanvas());
				setAllColors0=true;
			}
		});
		btnSelectAllButtons0.setBounds(50, 90, 40, 20);
		panel.add(btnSelectAllButtons0);
		
		btnSelectAllButtons1 = new JButton("");
		btnSelectAllButtons1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//setAllColorButtonsWithActiveColor((byte)1);
				jPopupMenuColors.show(btnSelectAllButtons1,btnSelectAllButtons1.getX()/canvas.getPixelSizeOfRenderCanvas(), btnSelectAllButtons1.getY()/canvas.getPixelSizeOfRenderCanvas());
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
		
		lblSpriteNumberOnCanvas = new JLabel("");
		lblSpriteNumberOnCanvas.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSpriteNumberOnCanvas.setBounds(175, 96, 62, 14);
		lblSpriteNumberOnCanvas.setOpaque(true);
		panel.add(lblSpriteNumberOnCanvas);

		
		JLabel lblNextToNameSprite = new JLabel("Name:");
		lblNextToNameSprite.setBounds(247, 96, 46, 14);
		panel.add(lblNextToNameSprite);
		
		jtFieldName = new JTextField("");
		jtFieldName.setFont(new Font("Tahoma", Font.BOLD, 14));
		//lblSpriteName.setBackground(null);
		jtFieldName.setBounds(303, 90, 131, 24);
		jtFieldName.setOpaque(true);
		panel.add(jtFieldName);
		/**
		 * END LABELS SPRITES
		 */
		
		
		/**
		 * BUTTONS FOR SPRITES EDITING
		 */
		JButton btnWalking = new JButton(new ImageIcon(getClass().getResource("/images/andando.png")));
		btnWalking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!startAnimation) {
					startAnimation=true;
					btnWalking.setBackground(Color.YELLOW);
					startAnimation();
				}	
				else {
					startAnimation=false;
					btnWalking.setBackground(null);
				}
				
			}
		});
		btnWalking.setBounds(10, 451, 40, 40);
		panel.add(btnWalking);
		
		
		
		//Estos botones trabajan con la clase riboon
		JButton btnSpriteAdd = new JButton(new ImageIcon(getClass().getResource("/images/agregar.png")));
		btnSpriteAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createSprite();
			}
		});
		btnSpriteAdd.setBounds(119, 451, 40, 40);
		panel.add(btnSpriteAdd);
		
		JButton btnSpriteUpdate = new JButton(new ImageIcon(getClass().getResource("/images/actualizar.png")));
		btnSpriteUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("El sprite selecionado es "+riboon.getSpriteOnRiboonSelected());
				if(canvas.getSpriteOnCanvasSelected()==null) {
					JOptionPane.showMessageDialog(null, "2 clicks on the sprite");
				}else {
					int numberSpriteSelected=canvas.getSpriteOnCanvasSelected().getNumber();
					for(int i=0;i<arrayListSprites.size();i++) {
						Sprite sprite=arrayListSprites.get(i);
						if(sprite.getNumber()==numberSpriteSelected) {
							//La seleccionamos y ponemos el borde
							//System.out.println("actualizado el  "+sprite.getNumber());
							updateSprite(sprite);
							//Actualizamos el riboon para verlo
							riboon.updateRiboon(sprite);
							riboon.updateAllRiboon();
						}
					}
				}
				
				
			}
		});
		btnSpriteUpdate.setBounds(185, 451, 40, 40);
		panel.add(btnSpriteUpdate);
		//btnSpriteUpdate.setEnabled(false);
		
		
		JButton btnSpriteLeft = new JButton(new ImageIcon(getClass().getResource("/images/left.png")));
		btnSpriteLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				riboon.moveSpriteLeftOnRiboon();
				riboon.updateAllRiboon();
				Sprite sprite=riboon.getSpriteOnRiboonSelected();
				riboon.updateBorders(sprite);
				lblNumberSpriteRiboon.setText(""+sprite.getNumber());
				lblNameSpriteRiboon.setText(sprite.getName());
			}
		});
		btnSpriteLeft.setBounds(247, 451, 40, 40);
		panel.add(btnSpriteLeft);
		//btnSpriteLeft.setEnabled(false);
		
		JButton btnSpriteRight = new JButton(new ImageIcon(getClass().getResource("/images/right.png")));
		btnSpriteRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				riboon.moveSpriteRightOnRiboon();
				riboon.updateAllRiboon();
				Sprite sprite=riboon.getSpriteOnRiboonSelected();
				riboon.updateBorders(sprite);
				lblNumberSpriteRiboon.setText(""+sprite.getNumber());
				lblNameSpriteRiboon.setText(sprite.getName());
			}
		});
		btnSpriteRight.setBounds(311, 451, 40, 40);
		panel.add(btnSpriteRight);
		
		JButton btnDeleteSprite = new JButton(new ImageIcon(getClass().getResource("/images/delete-sprite.png")));
		btnDeleteSprite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				riboon.deleteSprite(riboon.getSpriteOnRiboonSelected());
			}
		});
		btnDeleteSprite.setBounds(820, 451, 40, 40);
		panel.add(btnDeleteSprite);
		
		
		
		JButton btnGirarHorizontal = new JButton(new ImageIcon(getClass().getResource("/images/girar-horizontal.png")));
		btnGirarHorizontal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.rotateLandscape();
				canvas.fillTextAreaDefinitionAndColor();
			}
		});
		btnGirarHorizontal.setBounds(425, 11, 40, 40);
		panel.add(btnGirarHorizontal);
		
		JButton btnGirarvertical = new JButton(new ImageIcon(getClass().getResource("/images/girar-vertical.png")));
		btnGirarvertical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.rotateUpright();
				canvas.fillTextAreaDefinitionAndColor();
			}
		});
		btnGirarvertical.setBounds(493, 11, 40, 40);
		panel.add(btnGirarvertical);
		
		JButton btnGirarDiagonal = new JButton(new ImageIcon(getClass().getResource("/images/diagonal.png")));
		btnGirarDiagonal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.diagonal();
				canvas.fillTextAreaDefinitionAndColor();
			}
		});
		btnGirarDiagonal.setBounds(560, 11, 40, 40);
		panel.add(btnGirarDiagonal);
		
		
		JButton btnMoverUnoALaIzquierda = new JButton(new ImageIcon(getClass().getResource("/images/desplaza-uno-izquierda.png")));
		btnMoverUnoALaIzquierda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.moveOneLeft();
				canvas.fillTextAreaDefinitionAndColor();
			}
		});
		btnMoverUnoALaIzquierda.setBounds(622, 11, 40, 40);
		panel.add(btnMoverUnoALaIzquierda);
		
		JButton btnMoverUnoALaDerecha = new JButton(new ImageIcon(getClass().getResource("/images/desplaza-uno-derecha.png")));
		btnMoverUnoALaDerecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.moveOneRight();
				canvas.fillTextAreaDefinitionAndColor();
			}
		});
		btnMoverUnoALaDerecha.setBounds(682, 11, 40, 40);
		panel.add(btnMoverUnoALaDerecha);
		
		JButton btnMoverUnoArriba = new JButton(new ImageIcon(getClass().getResource("/images/desplaza-uno-arriba.png")));
		btnMoverUnoArriba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.moveOneUp();
				canvas.fillTextAreaDefinitionAndColor();
			}
		});
		btnMoverUnoArriba.setBounds(743, 11, 40, 40);
		panel.add(btnMoverUnoArriba);
		
		JButton btnMoverUnoAbajo = new JButton(new ImageIcon(getClass().getResource("/images/desplaza-uno-abajo.png")));
		btnMoverUnoAbajo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.moveOneDown();
				canvas.fillTextAreaDefinitionAndColor();
			}
		});
		btnMoverUnoAbajo.setBounds(808, 11, 40, 40);
		panel.add(btnMoverUnoAbajo);

		/**
		 * END SBUTTONS FOR SPRITES EDITING
		 */
		
		
		
		/**
		 * COMBOBOX AND SPRITESIZE
		 */

		
		
		
		
		lblNumberSpriteRiboon = new JLabel("");
		lblNumberSpriteRiboon.setBounds(537, 477, 83, 14);
		panel.add(lblNumberSpriteRiboon);
		
		lblNameSpriteRiboon = new JLabel("");
		lblNameSpriteRiboon.setBounds(622, 477, 188, 14);
		panel.add(lblNameSpriteRiboon);

	}
	
	
	private void startAnimation() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0;i<arrayListSprites.size();i++) {
					if(startAnimation) {
						if(startAnimation==false)break;
						try {
							Thread.sleep(1000);
							Sprite sprite=arrayListSprites.get(i);
							riboon.showSpriteOnContentPane(sprite);
							riboon.updateRiboon(sprite);
							riboon.updateBorders(sprite);
							canvas.setSpriteOnCanvasSelected(sprite);
							if(sprite.getNumber()==arrayListSprites.size()-1) i=-1;
						} catch (InterruptedException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
					}	
				}	
			}
		}).start();	
	}
	
	
	public void checkStateForScreenChange(byte spriteNumColorsOrScreenMode) {
			switch (spriteNumColorsOrScreenMode) {
			case 1:
				textAreaColor.setVisible(false);
				lbltextAreaColor.setVisible(false);
				menuBar.getJMenuExportSC1().setEnabled(true);
				menuBar.getJMenuExportSC2().setEnabled(false);
				menuBar.getJMenuExportSC3().setEnabled(false);
				menuBar.getJMenuExportSC4().setEnabled(false);
				menuBar.getJMenuExportSC5().setEnabled(false);
				break;
			case 2:
				textAreaColor.setVisible(false);
				lbltextAreaColor.setVisible(false);
				menuBar.getJMenuExportSC1().setEnabled(false);
				menuBar.getJMenuExportSC2().setEnabled(true);
				menuBar.getJMenuExportSC3().setEnabled(false);
				menuBar.getJMenuExportSC4().setEnabled(false);
				menuBar.getJMenuExportSC5().setEnabled(false);
				break;
			case 3:
				chckbxSpritesAudmented.setSelected(true);
				menuBar.setSpriteAudmented(true);
				textAreaColor.setVisible(false);
				lbltextAreaColor.setVisible(false);
				menuBar.getJMenuExportSC1().setEnabled(false);
				menuBar.getJMenuExportSC2().setEnabled(false);
				menuBar.getJMenuExportSC3().setEnabled(true);
				menuBar.getJMenuExportSC4().setEnabled(false);
				menuBar.getJMenuExportSC5().setEnabled(false);
				break;
			case 4:
				textAreaColor.setVisible(true);
				lbltextAreaColor.setVisible(true);	
				menuBar.getJMenuExportSC1().setEnabled(false);
				menuBar.getJMenuExportSC2().setEnabled(false);
				menuBar.getJMenuExportSC3().setEnabled(false);
				menuBar.getJMenuExportSC4().setEnabled(true);
				menuBar.getJMenuExportSC5().setEnabled(false);
				break;
			case 5:
				textAreaColor.setVisible(true);
				lbltextAreaColor.setVisible(true);	
				menuBar.getJMenuExportSC1().setEnabled(false);
				menuBar.getJMenuExportSC2().setEnabled(false);
				menuBar.getJMenuExportSC3().setEnabled(false);
				menuBar.getJMenuExportSC4().setEnabled(false);
				menuBar.getJMenuExportSC5().setEnabled(true);
				break;
			default:
				
		}
	}
}
