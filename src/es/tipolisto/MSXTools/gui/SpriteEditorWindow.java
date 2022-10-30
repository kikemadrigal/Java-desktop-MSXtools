package es.tipolisto.MSXTools.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import es.tipolisto.MSXTools.beans.Pixel;
import es.tipolisto.MSXTools.beans.RGB;
import es.tipolisto.MSXTools.beans.Sprite;
import es.tipolisto.MSXTools.menus.MainMenu;
import es.tipolisto.MSXTools.utils.FileManager;
import es.tipolisto.MSXTools.utils.MSXPalette;
import es.tipolisto.MSXTools.utils.NumberManager;
import es.tipolisto.MSXTools.utils.Palettes;
import utils.ImageManager;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JScrollPane;

public class SpriteEditorWindow extends JFrame  {

	private PaneDrawable contentPane;
	private JTextArea textAreaColor;
	private JTextArea textAreaDefinition;
	private byte selectedColor;
	//0 background, 1 foregrounf
	private byte foreOrBackGround;
	private byte fileColor;
	private JButton[] jButtons0;
	private JButton[] jButtons1;
	private JButton btnSelectAllButtons0,btnSelectAllButtons1;
	private HashMap<MSXPalette, RGB> palettePhilips8255NMS;

	private boolean setAllColors0;
	private boolean setAllColors1;
	private boolean sustituteColors0;
	private boolean sustituteColors1;
	private Color colorFondo;
	private JButton btnClearAll;
	private ArrayList<Sprite> arrayListSprites;
	//private int countSprites=0;
	private JPanel panelImagesSprites;
	private JPopupMenu jPopupMenuSprite;
	private JScrollPane jscrollPanelImageSprites;
	private Dimension dimensionJScrollPaneSprites;
	private MainMenu menuBar;
	
	public SpriteEditorWindow() {
		setTitle("Sprite editor");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 896, 715);
		contentPane = new PaneDrawable(16, 16);
		contentPane.setLayout(null);
		setResizable(false);
		//Centramos la ventana en la pantalla
		setLocationRelativeTo(null);
		
		selectedColor=10;
		foreOrBackGround=0;
		fileColor=0;
		palettePhilips8255NMS=Palettes.getPalettePhilips8255NMSForSprites();
		setAllColors0=false;
		setAllColors1=false;
		sustituteColors0=false;
		sustituteColors1=false;
		arrayListSprites=new ArrayList<Sprite>();
		
		// Creamos el comportamiento de menú emergente
		jPopupMenuSprite = new JPopupMenu();
		
		JLabel lblNewLabel = new JLabel("Definition");
		lblNewLabel.setBounds(452, 90, 81, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Color");
		lblNewLabel_1.setBounds(659, 90, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		textAreaColor = new JTextArea();
		textAreaColor.setFont(new Font("Monospaced", Font.PLAIN, 16));
		textAreaColor.setBounds(659, 111, 190, 329);
		textAreaColor.setLineWrap(true);
		contentPane.add(textAreaColor);
		contentPane.setTextAreaColor(textAreaColor);
		
		textAreaDefinition = new JTextArea();
		textAreaDefinition.setFont(new Font("Monospaced", Font.PLAIN, 16));
		textAreaDefinition.setBounds(462, 111, 190, 329);
		textAreaDefinition.setLineWrap(true);
		contentPane.add(textAreaDefinition);
		contentPane.setTextAreaDefinition(textAreaDefinition);

	   
	    
	    
	    
	    
	    setContentPane(contentPane);
	    colorFondo=new Color(239,252,254);
	    setBackground(colorFondo);
	   

	    
	    

		
		
		JButton btnPaintPot = new JButton(new ImageIcon("data\\bote.png"));
		btnPaintPot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!contentPane.isPaintBot()) {
					btnPaintPot.setBackground(Color.YELLOW);
					contentPane.setPaintBot(true);
				}	
				else {
					btnPaintPot.setBackground(null);
					contentPane.setPaintBot(false);
				}
			}
		});
		btnPaintPot.setBounds(302, 70, 40, 40);
		contentPane.add(btnPaintPot);
	
		JButton btnClearPixels = new JButton(new ImageIcon("data\\goma.png"));
		btnClearPixels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				foreOrBackGround=2;
				if(!contentPane.isSelectedClear()) {
					btnClearPixels.setBackground(Color.YELLOW);
					contentPane.setSelectedClear(true);
				}	
				else {
					btnClearPixels.setBackground(null);
					contentPane.setSelectedClear(false);
				}
						
			}
		});
		btnClearPixels.setBounds(353, 70, 40, 40);
		contentPane.add(btnClearPixels);
		
		btnClearAll = new JButton(new ImageIcon("data\\papelera.png"));
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.deleteAll();
				contentPane.fillFields();
			}
		});
		btnClearAll.setBounds(402, 70, 40, 40);
		contentPane.add(btnClearAll);
		

		

		
		//Cremos los menús para los colores y le asignamos los comportamientos 
		JPopupMenu jPopupMenuColors=new JPopupMenu();
		for(int i=0;i<16;i++) {
			JMenuItem menuColor=new JMenuItem(""+i);
			menuColor.setBackground(getColorFromPallete(i));
			jPopupMenuColors.add(menuColor);
			final byte count=(byte)i;
			menuColor.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectedColor=count;
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
					if(sustituteColors0) {
						substituteColors(fileColor,(byte)0);
						sustituteColors0=false;
					}else if(sustituteColors1) {
						substituteColors(fileColor,(byte)1);
						sustituteColors1=false;
					}
				}
			});
		}
				
		


	
		//Creamos los botones foreground y background
		jButtons0=new JButton[16];
		for(int i=0;i<jButtons0.length;i++) {
			JButton jButton=new JButton("");
			jButton.setBounds(10, 120+(i*20), 40, 20);
			contentPane.add(jButton);
			final byte count=(byte)i;
			jButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					foreOrBackGround=0;
					fileColor=count;
					jPopupMenuColors.show(jButton,jButton.getX()/contentPane.getSizeTile(), jButton.getY()/contentPane.getSizeTile());
					sustituteColors0=true;
				}
			});
			jButtons0[i]=jButton;
		}
		jButtons1=new JButton[16];
		for(int i=0;i<jButtons1.length;i++) {
			JButton jButton=new JButton("");
			jButton.setBounds(60, 120+(i*20), 40, 20);
			contentPane.add(jButton);
			final byte count=(byte)i;
			jButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//System.out.println("has pinchado en uno de los botones de la derecha");
					foreOrBackGround=1;
					fileColor=count;
					jPopupMenuColors.show(jButton,jButton.getX()/contentPane.getSizeTile(), jButton.getY()/contentPane.getSizeTile());
					sustituteColors1=true;
				}
			});
			jButtons1[i]=jButton;
			jButton.setEnabled(false);
		}
		

		
		
		
		//Estos botones asgnar�n los colores de golpe a toda la columna
		btnSelectAllButtons0 = new JButton("");
		btnSelectAllButtons0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jPopupMenuColors.show(btnSelectAllButtons0,btnSelectAllButtons0.getX()/contentPane.getSizeTile(), btnSelectAllButtons0.getY()/contentPane.getSizeTile());
				setAllColors0=true;
			}
		});
		btnSelectAllButtons0.setBounds(10, 90, 40, 20);
		contentPane.add(btnSelectAllButtons0);
		
		btnSelectAllButtons1 = new JButton("");
		btnSelectAllButtons1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//setAllColorButtonsWithActiveColor((byte)1);
				jPopupMenuColors.show(btnSelectAllButtons1,btnSelectAllButtons1.getX()/contentPane.getSizeTile(), btnSelectAllButtons1.getY()/contentPane.getSizeTile());
				setAllColors1=true;
			}
		});
		btnSelectAllButtons1.setBounds(60, 90, 40, 20);
		contentPane.add(btnSelectAllButtons1);
		btnSelectAllButtons1.setEnabled(false);


		
		//les ponemos los colores a los botones de la i<quierda
		//setAllColorButtons();
		contentPane.setjButtons0(jButtons0);
		contentPane.setjButtons1(jButtons1);
		
		
		panelImagesSprites = new JPanel();
		panelImagesSprites.setBounds(10, 500, this.getWidth()-50, 150);
		//panelImagesSprites.setBackground(Color.BLACK);
		panelImagesSprites.setLayout(null);
		//El jScrollPane es un contenedor
		jscrollPanelImageSprites=new JScrollPane();
		dimensionJScrollPaneSprites=new Dimension(panelImagesSprites.getWidth(),panelImagesSprites.getHeight()-20);
		jscrollPanelImageSprites.setBounds(panelImagesSprites.getX(), panelImagesSprites.getY(), panelImagesSprites.getWidth(), panelImagesSprites.getHeight());
		jscrollPanelImageSprites.setViewportView(panelImagesSprites);
		panelImagesSprites.setPreferredSize(dimensionJScrollPaneSprites);
		contentPane.add(jscrollPanelImageSprites);
		


		
		
		JButton btnSpriteAdd = new JButton(new ImageIcon("data\\agregar.png"));
		btnSpriteAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createSprite();
			}
		});
		btnSpriteAdd.setBounds(119, 451, 40, 40);
		contentPane.add(btnSpriteAdd);
		
		JButton btnSpriteUpdate = new JButton(new ImageIcon("data\\actualizar.png"));
		btnSpriteUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Tamaño: "+arrayListSprites.size());
				for (int i=0;i<arrayListSprites.size();i++) {
					Sprite sprite=arrayListSprites.get(i);
					System.out.println("Sprite "+sprite.getNumber());
					Pixel[][] pixels=sprite.getPixels();
					for(int y=0;y<pixels.length;y++) {
						for(int x=0;x<pixels[0].length;x++) {
							Pixel pixel=pixels[x][y];
							Color color=pixel.getColor();
							System.out.print("|"+color.getRed()+","+color.getGreen()+","+color.getBlue());
						}
					}
					System.out.println("");
					
				}
				
			}
		});
		btnSpriteUpdate.setBounds(185, 451, 40, 40);
		contentPane.add(btnSpriteUpdate);
		


		

		
		//MainMenu(JPanel panelImagesSprites,JPopupMenu jPopupMenuSprite,JScrollPane jscrollPanelImageSprites,Dimension dimensionJScrollPaneSprites,ArrayList<Sprite> arrayListSprites,JTextArea textAreaDefinition, JTextArea textAreaColors)
	    menuBar = new MainMenu(contentPane,panelImagesSprites,jPopupMenuSprite,jscrollPanelImageSprites,dimensionJScrollPaneSprites,arrayListSprites,textAreaDefinition,textAreaColor);
	    setJMenuBar(menuBar);


		//Ponemos que el color de frente amarillo
		selectedColor=10;
		setAllColorButtonsWithActiveColor((byte)0);
		//Ponemos el comolor de fondo en negro
		selectedColor=0;
		setAllColorButtonsWithActiveColor((byte)1);
	}

	
	
	private void createSprite() {
		// Sprite(int number, int x, int y, String name)
		Sprite sprite = new Sprite(arrayListSprites.size(), 0, 0, "No_name" + arrayListSprites.size());
		sprite.setPixels(contentPane.getPixels());
		sprite.setDataDefinition(textAreaDefinition.getText());
		sprite.setDataColors(textAreaColor.getText());
		menuBar.showSpriteOnRiboon(sprite);
		arrayListSprites.add(sprite);
		System.out.println("Guardado el sprite "+sprite.getName()+"-"+sprite.getNumber());
		//Necesitamos meterle al contentPane otro array
		Pixel[][] pixels=new Pixel[16][16];
		Color deleteColor=new Color(239,252,254);
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=new Pixel((x*contentPane.getSizeTile())+contentPane.getBorderX(),(y*contentPane.getSizeTile())+contentPane.getBorderY(),(byte)2,deleteColor);
				pixels[x][y]=pixel;
			}
		}
		contentPane.setPixels(pixels);
		/*Pixel[][] pixels=sprite.getPixels();
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];
				Color color=pixel.getColor();
				System.out.print("|"+color.getRed()+","+color.getGreen()+","+color.getBlue());
			}
		}
		System.out.println("");*/
	}
	
	
	
	
	private void changeColorButton() {
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

							contentPane.setjButtons0(jButtons0);
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
							contentPane.setjButtons1(jButtons1);
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
	
	private Color getColorFromPallete(int number) {
		Color colorSelected=new Color(0,0,0);
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			if(entry.getKey().ordinal()==number) {
				colorSelected=new Color(entry.getValue().getRed(),entry.getValue().getGreen(),entry.getValue().getBlue());
			}
		}
		return colorSelected;	
	}
	
	private int getPositionColorOnPalette(Color color) {
		int number=0;
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			RGB rgbItem=entry.getValue();
			if(rgbItem.getRed()==color.getRed() && rgbItem.getBlue()==color.getBlue() && rgbItem.getGreen()==color.getGreen()) {
				number=entry.getKey().ordinal();
			}
		}
		return number;
	}
	
	
	private void substituteColors(byte fileCount,byte foreOrBackGround ) {
		//System.out.println("sustituteColors dice color recibido: "+fileCount+", fOb: "+foreOrBackGround);
		Pixel[][] pixels;
		pixels=contentPane.getPixels();
		int sizeTile=contentPane.getSizeTile();
		int borderX=contentPane.getBorderX();
		int borderY=contentPane.getBorderY();
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];
				Color color=pixel.getColor();
				int positionColorOnPalette=getPositionColorOnPalette(color);
				String cadenaValorHexadecimal=NumberManager.decimalAHexadecimal(positionColorOnPalette);
				byte frontOrBackgrounOnPixel=pixel.getForOrBrackground();
				//Cambiamos los pixeles de la fila
				if(y==fileCount) {
					if(pixel.getForOrBrackground()==foreOrBackGround) {
						contentPane.paintPixelAtPoint(pixel.getPositionX(),pixel.getPositionY(),foreOrBackGround);
						//System.out.println("(x:"+pixel.getPositionX()+"y:"+pixel.getPositionY()+")");
					}
				}
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	/*private void showSpriteOnRiboon(Sprite sprite) {
		
		System.out.println("Pintando el " + sprite.getNumber());
		Pixel[][] pixels = sprite.getPixels();
		// y apartir de estos reamos la imagen en la riboon
		BufferedImage bufferedImageDestiny = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
		int colorSRGB = 0;
		for (int y = 0; y < pixels.length; y++) {
			for (int x = 0; x < pixels[0].length; x++) {
				Pixel pixel = pixels[x][y];
				Color color = pixel.getColor();
				// System.out.print("["+color.getRed()+","+color.getGreen()+","+color.getBlue()+"]");
				colorSRGB = (int) color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
				bufferedImageDestiny.setRGB(x, y, colorSRGB);
			}
			// System.out.println("");
		}
		// La transformamos para que no seva de 16x16pixeles
		Image img = bufferedImageDestiny.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		BufferedImage bufferedImageScaled = ImageManager.imageToBufferedImage(img);
		// Obtenemos su Jlabel, le ponemos la imagen y le aplicamos los comportamientos
		JButton btnImageSprites = new JButton("");
		btnImageSprites.setBounds(10 + (sprite.getNumber() * 120), 10, 16 * 7, 16 * 7);
		dimensionJScrollPaneSprites.setSize(panelImagesSprites.getWidth() + (sprite.getNumber() * 100),
				panelImagesSprites.getHeight());
		panelImagesSprites.add(btnImageSprites);
		// lblLabelImageSprites = sprite.getjLabel();
		btnImageSprites.setIcon(new ImageIcon(bufferedImageScaled));
		// Creamos el comportamiento al hacer doble click se mostará en el canvas del
		// drawPane
		btnImageSprites.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					contentPane.fillPixellOnCanvas(pixels);
				}
			}
		});

		JMenuItem itemJPopMenu = new JMenuItem("Delete " + sprite.getNumber());
		jPopupMenuSprite.add(itemJPopMenu);
		itemJPopMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// arrayListSprites.remove(sprite.getNumber());
				System.out.println("click");
			}
		});
		jPopupMenuSprite.show(btnImageSprites, sprite.getNumber()*120, btnImageSprites.getY());
	}*/
}
