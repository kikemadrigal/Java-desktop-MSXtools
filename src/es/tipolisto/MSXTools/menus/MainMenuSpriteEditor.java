package es.tipolisto.MSXTools.menus;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import es.tipolisto.MSXTools.beans.Sprite;
import es.tipolisto.MSXTools.gui.PaneDrawableSpriteEditor;
import es.tipolisto.MSXTools.gui.RiboonSpriteEditor;
import es.tipolisto.MSXTools.utils.FileManager;


/**
 * 
 * Esta clase representa el menú superior para guardar archivos serializados y leerlos
 * Necesitará la lista de sprites, el riboon y el canvas
 */
public class MainMenuSpriteEditor extends JMenuBar{
	private ArrayList<Sprite> arrayListSprites;
	private PaneDrawableSpriteEditor canvas;
	private RiboonSpriteEditor riboon;
	//int countSprites;
	public MainMenuSpriteEditor(ArrayList<Sprite> arrayListSprites, PaneDrawableSpriteEditor canvas, RiboonSpriteEditor riboon, JCheckBox checkBoxAutoSaved ) {
		this.arrayListSprites=arrayListSprites;
		this.canvas=canvas;
		this.riboon=riboon;
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
				canvas.setAutoSaved(false);
				canvas.setFileAutoSaved(null);
				checkBoxAutoSaved.setSelected(false);
				canvas.deleteAll();
				//Actualizamos los textareas
				canvas.fillTextAreaDefinitionAndColot();
				arrayListSprites.clear();
				//panelImagesSprites.getGraphics().clearRect(panelImagesSprites.getX(), panelImagesSprites.getY(), panelImagesSprites.getWidth(), panelImagesSprites.getHeight());
				riboon.removeAll();
				riboon.repaint();
			}
		});
	    
	    JMenuItem mntmNewMenuItemSaveSprites = new JMenuItem("Save");
	    mnNewMenuFile.add(mntmNewMenuItemSaveSprites);
	    mntmNewMenuItemSaveSprites.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 	saveFile(true);
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
	    
	    
	    /**************Menu Export********************************/
	    JMenu mnMenuExport = new JMenu("Export");
		add(mnMenuExport);
		
		JMenu mnMenuExportSC2 = new JMenu("SC2");
		mnMenuExport.add(mnMenuExportSC2);
		JMenuItem mntmNewMenuItem = new JMenuItem("Export to bas");
		mnMenuExportSC2.add(mntmNewMenuItem);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					FileManager.createdFileBasicDecimal((byte)2,(byte)2, arrayListSprites, true);	
				}
								
			}
		});
		
		JMenuItem mnMenuExportToBin = new JMenuItem("Export to bin");
		mnMenuExportSC2.add(mnMenuExportToBin);
		mnMenuExportToBin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					createdFileBinaryDecimal((byte)2,(byte)2);	
				}
			}
		});
		
		
		JMenu mnMenuSC5 = new JMenu("SC5");
		mnMenuExport.add(mnMenuSC5);
		JMenuItem mntmNewMenuSC5ExportToBas = new JMenuItem("Export to bas");
		mnMenuSC5.add(mntmNewMenuSC5ExportToBas);
		mntmNewMenuSC5ExportToBas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					//Le tenemos que especificar el screen y el tamaño de los sprites que son 1 para 8x8px o 2 para 16x16px
					FileManager.createdFileBasicDecimal((byte)5,(byte)2, arrayListSprites, true);	
				}
			}
		});

		JMenuItem mntMenuSC5ExportToBin = new JMenuItem("Export to bin");
		mnMenuSC5.add(mntMenuSC5ExportToBin);
		mntMenuSC5ExportToBin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					createdFileBinaryDecimal((byte)5,(byte)2);		
				}
			}
		});
	    /**************End Menu Export********************************/
		
		
	    
	    /**************Menu Run********************************/
	    JMenu mnNewMenuRun = new JMenu("Run");
	    add(mnNewMenuRun);
	    
	    JMenuItem mntmNewMenuItemRunOpenMSX = new JMenuItem("OpenMSX");
	    mnNewMenuRun.add(mntmNewMenuItemRunOpenMSX);
	    mntmNewMenuItemRunOpenMSX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
						JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					try {
						File fileSpriteBAS=new File("sprites.bas");
						if(fileSpriteBAS.exists()) {
							String[] argsBas = { "CMD", "/C", "COPY", "/Y", "sprites.bas", "tools\\openmsx\\dsk" };
							Runtime.getRuntime().exec(argsBas);
							String[] argsAutoexec = { "CMD", "/C", "COPY", "/Y", "autoexec.bas", "tools\\openmsx\\dsk" };
							Runtime.getRuntime().exec(argsAutoexec);
						}
						File fileSpriteBin=new File("sprites.bin");
						if(fileSpriteBin.exists()) {
							String[] argsBin = { "CMD", "/C", "COPY", "/Y", "sprites.bin", "tools\\openmsx\\dsk" };
							Runtime.getRuntime().exec(argsBin);
							String[] argsAutoexec = { "CMD", "/C", "COPY", "/Y", "autoexec.bas", "tools\\openmsx\\dsk" };
							Runtime.getRuntime().exec(argsAutoexec);
						}
	
						String cmdOpenMSX = "tools\\openmsx\\openmsx.exe -machine Philips_NMS_8255 -diska tools\\openmsx\\dsk"; //Comando de apagado en linux
						//String cmdOpenMSX = "tools\\openmsx\\openmsx.exe -script tools\\openMSX\\emul_start_config.txt"; //Comando de apagado en linux
						Runtime.getRuntime().exec(cmdOpenMSX); 
						
					} catch (IOException ioe) {
						System.out.println (ioe);
						 JOptionPane.showMessageDialog(null, ""+ioe);
					}	
				}
			}
		});
	    /**************End Menu Run********************************/
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void createdFileBinaryDecimal(byte screen, byte spriteVideo) {
		ArrayList<String> arrayListString=new ArrayList<String>();
		arrayListString.add("\t\t output sprites.bin");
		arrayListString.add("\tdb   #fe");
		arrayListString.add("\tdw   INICIO ");
		arrayListString.add("\tdw   FINAL");
		arrayListString.add("\tdw   INICIO ");
		arrayListString.add("\torg  #d000 ");
		arrayListString.add("INICIO: ");
		/*********************Estableciendo modo vídeo*******************************/
		arrayListString.add("; Setup video mode");
		if(screen==2) {
			arrayListString.add("\tld a,2");
		}else if(screen==5) {
			arrayListString.add("\tld a,5");
		}
		arrayListString.add("\tcall #005F ; CHGMOD equ #005F, change mode video to screen 2 or 5 or ...");
		/*********************Estableciendo modo tamaño sprites*******************************/
		arrayListString.add("; Setup sprite size");
		if(spriteVideo!=0) {
			arrayListString.add("; Sprites no ampliados de 16x16");
			arrayListString.add("\tld b,#e2");
			arrayListString.add("\tld c,1");
			arrayListString.add("\tcall #0047;WRTVDP equ #0047, escribe en los registros del VDP");
		}else {
			arrayListString.add(";sprites no ampliados de 8x8");
		}
		

		/*********************DEFINICICIONES*******************************/
		arrayListString.add("\tld hl, sprites_definition");
		if(screen==2) {
			arrayListString.add("\tld de, 14336 ;&h3800, base(14) en sc2");
		}else if(screen==5) {
			arrayListString.add("\tld de, 30720 ;&h7800, base(29) en sc5");
		}	
		arrayListString.add("\tld bc, 32*"+arrayListSprites.size());
		arrayListString.add("\tcall  #005C; #005C=LDIRVM ");
		/******************END DEFINICICIONES*******************************/
		/**************************COLORS*******************************/
		if(screen==5) {
			arrayListString.add("\tld hl, sprites_colors");
			arrayListString.add("\tld de, 29696 ;&h7400, en sc5");
			arrayListString.add("\tld bc, 16*"+arrayListSprites.size());
			arrayListString.add("\tcall  #005C; #005C=LDIRVM ");
		}
		/***********************END COLORS*******************************/
		/**************************ATRIBUTES*******************************/
		arrayListString.add("\tld hl, sprites_atributes");
		if(screen==2) {	
			arrayListString.add("\tld de, 6912 ;&h1b00, base(28) en sc5");
			arrayListString.add("\tld bc, 4*"+arrayListSprites.size());
		}else if(screen==5) {
			arrayListString.add("\tld de, 30208 ;&h7600, base(28) en sc5");
			arrayListString.add("\tld bc, 4*"+arrayListSprites.size());
		}
		arrayListString.add("\tcall  #005C; #005C=LDIRVM ");
		/************************END ATRIBUTES*******************************/
		
		arrayListString.add(".bucle: ");
		arrayListString.add("\tjr .bucle");
		
		arrayListString.add("sprites_definition:");
		for (Sprite sprite: arrayListSprites) {
			arrayListString.add(";Definition sprite "+sprite.getNumber()+", name: "+sprite.getName());
			String dataDefinitions=sprite.getDataDefinition();
			String[] stringsDefinitions=dataDefinitions.split("\n");
			for(String linea: stringsDefinitions) {
				arrayListString.add("\tdb "+linea);
			}	
		}
		
		arrayListString.add("sprites_colors: ");
		for (Sprite sprite: arrayListSprites) {
			arrayListString.add(";Data colors sprite "+sprite.getNumber()+", name: "+sprite.getName());
			if(screen==5) {
				String dataColors=sprite.getDataColors();
				String[] stringsColors=dataColors.split("\n");
				for(String linea: stringsColors) {
					arrayListString.add("\tdb "+linea);
				}
			 }
			
		}
		arrayListString.add("sprites_atributes: ");
		for (Sprite sprite: arrayListSprites) {
			arrayListString.add(";Data atributes sprite (y,x,patron,color) "+sprite.getNumber()+", name: "+sprite.getName());
			if(screen==2) {
				//y,x,number,color
				arrayListString.add("\tdb "+192/2+","+sprite.getNumber()*20+","+sprite.getNumber()*4+",2");
			}else if(screen==5) {
				String dataColors=sprite.getDataColors();
				String[] stringsColors=dataColors.split("\n");
				for(String linea: stringsColors) {
					arrayListString.add("\tdb "+192/2+","+sprite.getNumber()*20+","+sprite.getNumber()*4+", \" \"");
				}
			}
		}

		arrayListString.add("FINAL: ");
		FileManager fileManager=new FileManager();
		fileManager.writeFile(new File("sprites.asm"), arrayListString);
		//Creamos el autoexec
		ArrayList<String> arrayListStringAutoexec=new ArrayList<String>();
		arrayListStringAutoexec.add("10 bload\"sprites.bin\",r");
		fileManager.writeFile(new File("autoexec.bas"), arrayListStringAutoexec);
		//Creamos el .bin
		String cmdOpenSjasm = "tools\\sjasm\\sjasm.exe sprites.asm"; 
		try {
			Runtime.getRuntime().exec(cmdOpenSjasm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} 
		//Copiamos el autoexec y el bin a la carpeta dsk de openMSX
		
		
		JOptionPane.showMessageDialog(null, "Created binary File");
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
					arrayListSprites.add(sprite);
					riboon.updateRiboon(sprite);
				}
				objectInputputStream.close();
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null,  "Exception "+ex);
			}
			canvas.setFileAutoSaved(fileOrigin.getName());
		}
	}
	
	
	
	/**
	 * Crea un archivo donde se guardan los sprites
	 */
	public void saveFile(boolean confirmDialog) {
		System.out.println("Salvado.");
		boolean canSave=false;
		File file=null;
		if(arrayListSprites.size()==0) {
			JOptionPane.showMessageDialog(null, "There are no sprites");		
		}else {
			if(canvas.getFileAutoSaved()==null) {
				String fileNameSavedRecovery=JOptionPane.showInputDialog(null, "Enter file name");
				fileNameSavedRecovery=fileNameSavedRecovery+".spr";
				canvas.setFileAutoSaved(fileNameSavedRecovery);
			}
			file=new File(canvas.getFileAutoSaved());
			if(file.exists() && !canvas.getAutoSaved()) {
				int input = JOptionPane.showConfirmDialog(null, "The file exists, do you want to overwrite it?");
				// 0=yes, 1=no, 2=cancel
				if(input==0) {
					canSave=true;
				}	
			}else {
				canSave=true;
			}	
		}	
		if(canSave) {
			try {
				ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(file));
				Sprite[] sprites=new Sprite[arrayListSprites.size()];
				for (int i=0;i<arrayListSprites.size();i++) {
					Sprite sprite=arrayListSprites.get(i);
					sprites[i]=sprite;
				}
				objectOutputStream.writeObject(sprites);
				objectOutputStream.close();
				if(confirmDialog)
					JOptionPane.showMessageDialog(null,  "Created file");
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null,  "Exception "+ex);
			}
		}
	}
	
	
	
	
}
