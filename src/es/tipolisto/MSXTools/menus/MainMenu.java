package es.tipolisto.MSXTools.menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.tipolisto.MSXTools.beans.Pixel;
import es.tipolisto.MSXTools.beans.Sprite;
import es.tipolisto.MSXTools.gui.PaneDrawable;
import es.tipolisto.MSXTools.utils.FileManager;
import utils.ImageManager;

public class MainMenu extends JMenuBar{
	private JTextArea textAreaDefinition,textAreaColors;
	private ArrayList<Sprite> arrayListSprites;
	private PaneDrawable contentPane;
	private JPanel panelImagesSprites;
	private JPopupMenu jPopupMenuSprite;
	private JScrollPane jscrollPanelImageSprites;
	private Dimension dimensionJScrollPaneSprites;
	private JButton[] jButtons0,jButtons1;
	//int countSprites;
	public MainMenu(PaneDrawable contentPane,JPanel panelImagesSprites,JPopupMenu jPopupMenuSprite,JScrollPane jscrollPanelImageSprites,Dimension dimensionJScrollPaneSprites,ArrayList<Sprite> arrayListSprites,JTextArea textAreaDefinition, JTextArea textAreaColors, JButton[] jButtons0, JButton[] jButtons1) {
		this.contentPane=contentPane;
		this.panelImagesSprites=panelImagesSprites;
		this.jPopupMenuSprite=jPopupMenuSprite;
		this.jscrollPanelImageSprites=jscrollPanelImageSprites;
		this.dimensionJScrollPaneSprites=dimensionJScrollPaneSprites;
		this.arrayListSprites=arrayListSprites;
		this.textAreaDefinition=textAreaDefinition;
		this.textAreaColors=textAreaColors;
		this.jButtons0=jButtons0;
		this.jButtons1=jButtons1;
		//this.countSprites=0;
		
		/****************Menu File********************************/
		JMenu mnNewMenuFile = new JMenu("File");
	    add(mnNewMenuFile);
	    
	    JMenuItem mntmNewMenuItemOpenSprites = new JMenuItem("Open");
	    mnNewMenuFile.add(mntmNewMenuItemOpenSprites);
	    mntmNewMenuItemOpenSprites.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()>0) {
					JOptionPane.showMessageDialog(null, "Close open sprites");
				}else {
					openFile();
				}
			}
		});
	    JMenuItem mntmNewMenuItemNewSprites = new JMenuItem("New / Close");
	    mnNewMenuFile.add(mntmNewMenuItemNewSprites);
	    mntmNewMenuItemNewSprites.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arrayListSprites.clear();
				contentPane.deleteAll();
				//Actualizamos los textareas
				contentPane.fillFields();
				//panelImagesSprites.getGraphics().clearRect(panelImagesSprites.getX(), panelImagesSprites.getY(), panelImagesSprites.getWidth(), panelImagesSprites.getHeight());
				panelImagesSprites.removeAll();
				panelImagesSprites.repaint();
			}
		});
	    
	    JMenuItem mntmNewMenuItemSaveSprites = new JMenuItem("Save");
	    mnNewMenuFile.add(mntmNewMenuItemSaveSprites);
	    mntmNewMenuItemSaveSprites.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()==0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");		
				}else {
					String fileName=JOptionPane.showInputDialog(null, "Enter file name");
					fileName=fileName+".spr";
					File file=new File(fileName);
					if(file.exists()) {
						int input = JOptionPane.showConfirmDialog(null, "The file exists, do you want to overwrite it?");
						// 0=yes, 1=no, 2=cancel
						if(input==0) {
							saveFile(file);
						}	
					}else {
						saveFile(file);
					}	
				}
				
			}
		});
	    
	    JMenuItem mntmNewMenuItemCloseSprites = new JMenuItem("Exit");
	    mnNewMenuFile.add(mntmNewMenuItemCloseSprites);
	    mntmNewMenuItemCloseSprites.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);			
			}
		});
	    /**************End Menu File********************************/
	    
	    JMenu mnNewMenuExport = new JMenu("Export");
	    add(mnNewMenuExport);
	    
	    JMenuItem mntmNewMenuItemExportBasicDecimal = new JMenuItem("Export data to basic decimal");
	    mnNewMenuExport.add(mntmNewMenuItemExportBasicDecimal);
	    
	    mntmNewMenuItemExportBasicDecimal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createdFileBasicDecimal();				
			}
		});
	    
	    JMenuItem mntmNewMenuItemExportBasicHexadecimal = new JMenuItem("Export data to basic hexadecimal");
	    mnNewMenuExport.add(mntmNewMenuItemExportBasicHexadecimal);
	    
	    JMenu mnNewMenuRun = new JMenu("Run");
	    add(mnNewMenuRun);
	    
	    JMenuItem mntmNewMenuItemRunOpenMSX = new JMenuItem("OpenMSX");
	    mnNewMenuRun.add(mntmNewMenuItemRunOpenMSX);
	    mntmNewMenuItemRunOpenMSX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String[] args = { "CMD", "/C", "COPY", "/Y", "sprites.bas", "tools\\openmsx\\dsk" };
					Runtime.getRuntime().exec(args);
								
					String cmdOpenMSX = "tools\\openmsx\\openmsx.exe -machine Philips_NMS_8255 -diska tools\\openmsx\\dsk"; //Comando de apagado en linux
					Runtime.getRuntime().exec(cmdOpenMSX); 
					
				} catch (IOException ioe) {
					System.out.println (ioe);
					 JOptionPane.showMessageDialog(null, ""+ioe);
				}		
			}
		});

	}
	
	
	
	
	
	
	private void createdFileBasicDecimal() {
		ArrayList<String> arrayListString=new ArrayList<String>();
		//String textDefinition=textAreaDefinition.getText();
		//String textColors=textAreaColors.getText();

		int numberLine=10200;
		int objetcs=arrayListSprites.size()-1;
		//if(textDefinition.isEmpty() || textColors.isEmpty()) JOptionPane.showMessageDialog(null, "Empty fields");
		//else {
			arrayListString.add(numberLine+" screen 5,2");
			numberLine+=10;
			arrayListString.add(numberLine+" for i=0 to "+objetcs+":sp$=\"\"");
			numberLine+=10;
			arrayListString.add("\t"+numberLine+" for j=0 to 31");
			numberLine+=10;
			arrayListString.add("\t\t"+numberLine+" read a$");
			numberLine+=10;
			arrayListString.add("\t\t"+numberLine+" sp$=sp$+chr$(val(a$))");
			numberLine+=10;
			arrayListString.add("\t"+numberLine+" next J");
			numberLine+=10;
			arrayListString.add("\t"+numberLine+" sprite$(i)=sp$");
			numberLine+=10;
			arrayListString.add(numberLine+" next i");
			numberLine+=10;
			arrayListString.add(numberLine+" for i=0 to "+objetcs+":sp$=\"\"");
			numberLine+=10;
			arrayListString.add("\t"+numberLine+" for j=0 to 15");
			numberLine+=10;
			arrayListString.add("\t\t"+numberLine+" read a$");
			numberLine+=10;
			arrayListString.add("\t\t"+numberLine+" sp$=sp$+chr$(val(\"&h\"+a$))");
			numberLine+=10;
			arrayListString.add("\t"+numberLine+" next J");
			numberLine+=10;
			arrayListString.add("\t"+numberLine+" color sprite$(i)=sp$");
			numberLine+=10;
			arrayListString.add(numberLine+" next I");
			numberLine+=10;
			arrayListString.add(numberLine+" rem sprites data definitions");
			numberLine+=10;
			
			for (Sprite sprite: arrayListSprites) {
				arrayListString.add(numberLine+" rem data definition sprite "+sprite.getNumber()+", name: "+sprite.getName());
				String dataDefinitions=sprite.getDataDefinition();
				String[] stringsDefinitions=dataDefinitions.split("\n");
				for(String linea: stringsDefinitions) {
					arrayListString.add(numberLine+" data "+linea);
					numberLine+=10;
				}	
			}
			for (Sprite sprite: arrayListSprites) {
				arrayListString.add(numberLine+" rem data colors definitions sprite "+sprite.getNumber()+", name: "+sprite.getName());
				String dataColors=sprite.getDataColors();
				String[] stringsColors=dataColors.split("\n");
				for(String linea: stringsColors) {
					arrayListString.add(numberLine+" data "+linea);
					numberLine+=10;
				}
			}
			for (Sprite sprite: arrayListSprites) {
				arrayListString.add(numberLine+" put sprite "+sprite.getNumber()+",(20*"+sprite.getNumber()+",100),,"+sprite.getNumber());
				numberLine+=10;
			}
			arrayListString.add(numberLine+" goto "+numberLine);
			
			
			FileManager fileManager=new FileManager();
			fileManager.writeFile(new File("sprites.bas"), arrayListString);
			JOptionPane.showMessageDialog(null, "Created File");
		//}
		
	}
	
	
	
	
	public void updateRiboon(Sprite sprite) {
		//Vamos a mostrar este sprite en el riboon
		//1.Obtenemos los pixeles
		Pixel[][] pixels = sprite.getPixels();
		//Necesitamos crear un befferedImage para poder trabajar con pixeles
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
		//Ubicamos la imagen en la posición que queramos
		btnImageSprites.setBounds(10 + (sprite.getNumber() * 120), 10, 16 * 7, 16 * 7);
		dimensionJScrollPaneSprites.setSize(panelImagesSprites.getWidth() + (sprite.getNumber() * 100),
				panelImagesSprites.getHeight());
		panelImagesSprites.add(btnImageSprites);
		// lblLabelImageSprites = sprite.getjLabel();
		btnImageSprites.setIcon(new ImageIcon(bufferedImageScaled));
		// Creamos en la imagen los comportamientos
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
				if(e.getButton()==MouseEvent.BUTTON1) {
					if (e.getClickCount() == 2) {
						showSpriteOnContentPane(sprite);
					}
				}else if(e.getButton()==MouseEvent.BUTTON3) {
					int input = JOptionPane.showConfirmDialog(null, "Delete item?");
			        // 0=yes, 1=no, 2=cancel
					if(input==0) {
						panelImagesSprites.remove(btnImageSprites);
						panelImagesSprites.repaint();
						arrayListSprites.remove(sprite.getNumber());
					}
				}
				
			}
		});

		
		/*JMenuItem itemJPopMenu = new JMenuItem("Delete " + sprite.getNumber());
		itemJPopMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// arrayListSprites.remove(sprite.getNumber());
				System.out.println("click");
			}
		});
		jPopupMenuSprite.show(btnImageSprites, sprite.getNumber()*120, btnImageSprites.getY());
		jPopupMenuSprite.add(itemJPopMenu);*/
		

	}
	
	
	
	
	private void openFile() {
		File fileOrigin=null;
		JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir"));
		jFileChooser.setDialogTitle("Selecciona un archivo");
		int result=jFileChooser.showSaveDialog(null);
		if(result==JFileChooser.APPROVE_OPTION) {
			//JOptionPane.showConfirmDialog(null, "WIP");
			String content="";
			String contentCompress="";
			fileOrigin=jFileChooser.getSelectedFile();
			try {
				arrayListSprites.clear();
				ObjectInputStream objectInputputStream=new ObjectInputStream(new FileInputStream(fileOrigin));
				Sprite[] sprites=(Sprite[]) objectInputputStream.readObject();
				for (int i=0;i<sprites.length;i++) {
					Sprite sprite=sprites[i];
					//System.out.println(sprite.toString());
					//sprite.setPixels(contentPane.getPixels());
					arrayListSprites.add(sprite);
					updateRiboon(sprite);
					//countSprites++;
					

					
				}
				
				objectInputputStream.close();
				//JOptionPane.showMessageDialog(null,  "Created file");
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null,  "Exception "+ex);
			}
		}
	}
	
	
	
	/**
	 * Crea un archivo donde se guardan los sprites
	 */
	private void saveFile(File file) {
		try {
			ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(file));
			Sprite[] sprites=new Sprite[arrayListSprites.size()];
			for (int i=0;i<arrayListSprites.size();i++) {
				Sprite sprite=arrayListSprites.get(i);
				sprites[i]=sprite;
			}
			objectOutputStream.writeObject(sprites);
			objectOutputStream.close();
			JOptionPane.showMessageDialog(null,  "Created file");
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null,  "Exception "+ex);
		}
	}
	
	
	private void showSpriteOnContentPane(Sprite sprite) {
		//1.Obtenemos el color de los botones y se los asignamos al contentPane
		Color[] colorButtons0Sprite=sprite.getColorButtons0();
		Color[] colorButtons1Sprite=sprite.getColorButtons1();
		for (int i=0;i<jButtons0.length;i++) {
			//Obtenemos el botón
			JButton button0Frame=jButtons0[i];
			JButton button1Frame=jButtons1[i];
			//Obtenemos el color guardado en el sprite
			Color color0Frame=colorButtons0Sprite[i];
			Color color1Sprite=colorButtons1Sprite[i];
			//Se lo ponemos al bpotón del fram
			button0Frame.setBackground(color0Frame);
			button1Frame.setBackground(color1Sprite);
		}

		//2.Obtenemos los pixeles para dibujarlos en el canvas
		Pixel[][] pixelsSprite=sprite.getPixels();
		for(int y=0;y<pixelsSprite.length;y++) {
			for(int x=0;x<pixelsSprite[0].length;x++) {
				Pixel pixelSprite=pixelsSprite[x][y];
				contentPane.paintPixelAtPoint(pixelSprite.getPositionX(), pixelSprite.getPositionY(), pixelSprite.getForOrBrackground());
			}
		}
		
		//3.Obtenemos los textsAreas
		String dataDefinition=sprite.getDataDefinition();
		textAreaDefinition.setText(dataDefinition);
		String dataColors=sprite.getDataColors();
		textAreaDefinition.setText(dataDefinition);
		textAreaColors.setText(dataColors);		
	}






	public void moveSpriteLeftOnRiboon(Sprite spriteSelected) {
		// TODO Auto-generated method stub
		
	}






	public void moveSpriteRightOnRiboon(Sprite spriteSelected) {
		// TODO Auto-generated method stub
		
	}
	
}
