package es.tipolisto.MSXTools.gui.pixelEditor;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import es.tipolisto.MSXTools.beans.Sprite;
import es.tipolisto.MSXTools.gui.NewSpriteEditorWindow;
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
	private JCheckBox checkBoxAutoSaved;
	private JLabel lblSpriteNumber;
	private JTextField jtFieldName;
	private JMenu mnMenuExportSC1,mnMenuExportSC2,mnMenuExportSC3,mnMenuExportSC4,mnMenuExportSC5;
	private byte spriteNumColorsOrScreenMode;
	private boolean spriteAudmented;
	private SpriteEditorWindow spriteEditorWindow;
	//0 mode basic, 1 mode binary
	private byte modeBasicOBinary;
	//int countSprites;
	public MainMenuSpriteEditor(ArrayList<Sprite> arrayListSprites,
			PaneDrawableSpriteEditor canvas,
			RiboonSpriteEditor riboon,
			JCheckBox checkBoxAutoSaved,
			JLabel lblSpriteNumber,
			JTextField jtFieldName,
			byte spriteNumColorsOrScreenMode,
			SpriteEditorWindow spriteEditorWindow) {

		this.arrayListSprites=arrayListSprites;
		this.canvas=canvas;
		this.riboon=riboon;
		this.checkBoxAutoSaved=checkBoxAutoSaved;
		this.lblSpriteNumber=lblSpriteNumber;
		this.jtFieldName=jtFieldName;
		this.spriteAudmented=false;
		this.spriteEditorWindow=spriteEditorWindow;
		//0 mode basic, 1 mode binary
		this.modeBasicOBinary=0;
		/**********************************Menu File****************************************/
		JMenu mnNewMenuFile = new JMenu("File");
	    add(mnNewMenuFile);
	    
	    JMenuItem mntmNewMenuItemNewSprite = new JMenuItem("New");
	    mnNewMenuFile.add(mntmNewMenuItemNewSprite);
	    mntmNewMenuItemNewSprite.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
	    mntmNewMenuItemNewSprite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()>0) {
					JOptionPane.showMessageDialog(null, "Close open sprites");
				}else {
					NewSpriteEditorWindow frame = new NewSpriteEditorWindow();
					frame.setVisible(true);
					spriteEditorWindow.setVisible(false);
					spriteEditorWindow.dispose();
				}
			}
		});
	    
	    JMenuItem mntmNewMenuItemOpenSprites = new JMenuItem("Open     ");
	    mnNewMenuFile.add(mntmNewMenuItemOpenSprites);
	    mntmNewMenuItemOpenSprites.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
	    mntmNewMenuItemOpenSprites.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()>0) {
					JOptionPane.showMessageDialog(null, "Close open sprites");
				}else {
					openFile();
				}
			}
		});
	    JMenuItem mntmNewMenuItemNewSprites = new JMenuItem("Close     ");
	    mnNewMenuFile.add(mntmNewMenuItemNewSprites);
	    mntmNewMenuItemNewSprites.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
	    mntmNewMenuItemNewSprites.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeAll();
			}
		});
	    
	    JMenuItem mntmNewMenuItemSaveSprites = new JMenuItem("Save    ");
	    mnNewMenuFile.add(mntmNewMenuItemSaveSprites);
	    mntmNewMenuItemSaveSprites.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
	    mntmNewMenuItemSaveSprites.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 	saveFile(true);
			}
		});
	    
	    JMenuItem mntmNewMenuItemSaveAsSprites = new JMenuItem("Save as...");
	    mnNewMenuFile.add(mntmNewMenuItemSaveAsSprites);
	    mntmNewMenuItemSaveAsSprites.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
	    mntmNewMenuItemSaveAsSprites.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 	saveAsFile();
			}
		});
	    
	    JMenuItem mntmNewMenuItemCloseSprites = new JMenuItem("Exit");
	    mnNewMenuFile.add(mntmNewMenuItemCloseSprites);
	    mntmNewMenuItemCloseSprites.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, InputEvent.CTRL_DOWN_MASK));
	    mntmNewMenuItemCloseSprites.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);			
			}
		});
	    /********************************End Menu File**********************************************/
	    
	    
	    /***************************************Menu Export*****************************************/
	    JMenu mnMenuExport = new JMenu("Export");
		add(mnMenuExport);
		
		
		/**********mnMenuExportSC1**********************************************/
		mnMenuExportSC1 = new JMenu("SC1");
		mnMenuExport.add(mnMenuExportSC1);
	
		//SC1->bas
		JMenuItem mntmNewMenuItemSC1 = new JMenuItem("Export to bas");
		mnMenuExportSC1.add(mntmNewMenuItemSC1);
		mntmNewMenuItemSC1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
		mntmNewMenuItemSC1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					Sprite sprite0=arrayListSprites.get(0);
					byte spriteType=sprite0.getType();
					//(byte screen, byte spriteType, ArrayList<Sprite>arrayListSprites, boolean audmented, boolean showMessahe)
					FileManager.createdSpritesFileBasicDecimal((byte)1,(byte)spriteType, arrayListSprites,spriteAudmented ,true);	
					//0 mode basic, 1 mode binary
					modeBasicOBinary=0;
				}				
			}
		});
		//SC1->bin
		JMenuItem mnMenuExportToBinSC1 = new JMenuItem("Export to bin");
		mnMenuExportSC1.add(mnMenuExportToBinSC1);
		mnMenuExportToBinSC1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		mnMenuExportToBinSC1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					Sprite sprite0=arrayListSprites.get(0);
					byte spriteType=sprite0.getType();
					FileManager.createdSpritesFileBinaryAutoLoad((byte)1,(byte)spriteType, arrayListSprites,spriteAudmented, true);	
					modeBasicOBinary=1;
				}
			}
		});
		/**********End mnMenuExportSC1**********************************************/
		/**********mnMenuExportSC2**************************************************/
		mnMenuExportSC2 = new JMenu("SC2");
		mnMenuExport.add(mnMenuExportSC2);
	
		//SC2->bas
		JMenuItem mntmNewMenuItemToBasSC2 = new JMenuItem("Export to bas");
		mnMenuExportSC2.add(mntmNewMenuItemToBasSC2);
		mntmNewMenuItemToBasSC2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
		mntmNewMenuItemToBasSC2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					Sprite sprite0=arrayListSprites.get(0);
					byte spriteType=sprite0.getType();
					//(byte screen, byte spriteType, ArrayList<Sprite>arrayListSprites, boolean audmented, boolean showMessahe)
					FileManager.createdSpritesFileBasicDecimal((byte)2,(byte)spriteType, arrayListSprites,spriteAudmented ,true);	
					modeBasicOBinary=0;
				}				
			}
		});
		//SC2->bin
		JMenuItem mnMenuExportToBinSC2 = new JMenuItem("Export to bin");
		mnMenuExportSC2.add(mnMenuExportToBinSC2);
		mnMenuExportToBinSC2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		mnMenuExportToBinSC2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					Sprite sprite0=arrayListSprites.get(0);
					byte spriteType=sprite0.getType();
					FileManager.createdSpritesFileBinaryAutoLoad((byte)2,(byte)spriteType, arrayListSprites,spriteAudmented,true);	
					modeBasicOBinary=1;
				}
			}
		});
		/**********End mnMenuExportSC2**********************************************/
		/**********mnMenuExportSC3**************************************************/
		mnMenuExportSC3 = new JMenu("SC3");
		mnMenuExport.add(mnMenuExportSC3);
	
		//SC3->bas
		JMenuItem mntmNewMenuItemToBasSC3 = new JMenuItem("Export to bas");
		mnMenuExportSC3.add(mntmNewMenuItemToBasSC3);
		mntmNewMenuItemToBasSC3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
		mntmNewMenuItemToBasSC3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					Sprite sprite0=arrayListSprites.get(0);
					byte spriteType=sprite0.getType();
					//(byte screen, byte spriteType, ArrayList<Sprite>arrayListSprites, boolean audmented, boolean showMessahe)
					FileManager.createdSpritesFileBasicDecimal((byte)3,(byte)spriteType, arrayListSprites,spriteAudmented ,true);	
					modeBasicOBinary=0;
				}				
			}
		});

		//SC3->bin
		JMenuItem mnMenuExportToBinSC3 = new JMenuItem("Export to bin");
		mnMenuExportSC3.add(mnMenuExportToBinSC3);
		mnMenuExportToBinSC3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		mnMenuExportToBinSC3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					Sprite sprite0=arrayListSprites.get(0);
					byte spriteType=sprite0.getType();
					FileManager.createdSpritesFileBinaryAutoLoad((byte)3,(byte)spriteType, arrayListSprites,spriteAudmented, true);	
					modeBasicOBinary=1;
				}
			}
		});

		/**********End mnMenuExportSC3**********************************************/
		/**********mnMenuExportSC4**************************************************/
		mnMenuExportSC4 = new JMenu("SC4");
		mnMenuExport.add(mnMenuExportSC4);
	
		//SC4->bas
		JMenuItem mntmNewMenuItemToBasSC4 = new JMenuItem("Export to bas");
		mnMenuExportSC4.add(mntmNewMenuItemToBasSC4);
		mntmNewMenuItemToBasSC4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
		mntmNewMenuItemToBasSC4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					Sprite sprite0=arrayListSprites.get(0);
					byte spriteType=sprite0.getType();
					//(byte screen, byte spriteType, ArrayList<Sprite>arrayListSprites, boolean audmented, boolean showMessahe)
					FileManager.createdSpritesFileBasicDecimal((byte)4,(byte)spriteType, arrayListSprites,spriteAudmented ,true);	
					modeBasicOBinary=0;
				}				
			}
		});
		//SC4->bin
		JMenuItem mnMenuExportToBinSC4 = new JMenuItem("Export to bin");
		mnMenuExportSC4.add(mnMenuExportToBinSC4);
		mnMenuExportToBinSC4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		mnMenuExportToBinSC4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					Sprite sprite0=arrayListSprites.get(0);
					byte spriteType=sprite0.getType();
					FileManager.createdSpritesFileBinaryAutoLoad((byte)4,(byte)spriteType, arrayListSprites,spriteAudmented,true);	
					modeBasicOBinary=1;
				}
			}
		});
		/**********End mnMenuExportSC3**********************************************/
		
		/**********mnMenuExportSC5**********************************************/
		mnMenuExportSC5 = new JMenu("SC5");
		mnMenuExport.add(mnMenuExportSC5);
		
		//SC5->bas
		JMenuItem mntmNewMenuSC5ExportToBas = new JMenuItem("Export to bas     ");
		mnMenuExportSC5.add(mntmNewMenuSC5ExportToBas);
		mntmNewMenuSC5ExportToBas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
		mntmNewMenuSC5ExportToBas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
			   		JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					//Le tenemos que especificar el screen y el tamaño de los sprites que son 1 para 8x8px o 2 para 16x16px
					Sprite sprite0=arrayListSprites.get(0);
					byte spriteType=sprite0.getType();
					boolean showConfirmaionMessage=true;
					FileManager.createdSpritesFileBasicDecimal((byte)5,(byte)spriteType, arrayListSprites, spriteAudmented,showConfirmaionMessage);	
					modeBasicOBinary=0;
				}
			}
		});
		//SC5->bin
		JMenuItem mntMenuSC5ExportToBin = new JMenuItem("Export to bin    ");
		mnMenuExportSC5.add(mntMenuSC5ExportToBin);
		mntMenuSC5ExportToBin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		mntMenuSC5ExportToBin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					Sprite sprite0=arrayListSprites.get(0);
					byte spriteType=sprite0.getType();
					boolean showConfirmaionMessage=true;
					//SpriteType puede ser 8 o 16
					FileManager.createdSpritesFileBinaryAutoLoad((byte)5,(byte)spriteType, arrayListSprites,spriteAudmented,showConfirmaionMessage);		
					modeBasicOBinary=1;
				}
			}
		});
		/**********End mnMenuExportSC5**********************************************/
		
		
	    
	    /**************Menu Run********************************/
	    JMenu mnNewMenuRun = new JMenu("Run");
	    add(mnNewMenuRun);
	    
	    JMenuItem mntmNewMenuItemRunOpenMSX = new JMenuItem("OpenMSX");
	    mnNewMenuRun.add(mntmNewMenuItemRunOpenMSX);
	    mntmNewMenuItemRunOpenMSX.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
	    mntmNewMenuItemRunOpenMSX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
						JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					//Vemos el sistema operativo
					String sSistemaOperativo = System.getProperty("os.name");
					//System.out.println(sSistemaOperativo);

					try {
						if(modeBasicOBinary==0) {
							File fileSpriteBAS=new File("sprites.bas");
							if(fileSpriteBAS.exists()) {
								FileManager.createAutoexecBasic();
								//Si es windows
								if(sSistemaOperativo.indexOf("Win")>=0) {
									String[] argsBas = { "CMD", "/C", "MOVE", "/Y", "sprites.bas", "target\\tools\\openmsx\\dsk" };
									String[] argsAutoexec = { "CMD", "/C", "MOVE", "/Y", "autoexec.bas", "target\\tools\\openmsx\\dsk" };
									Runtime.getRuntime().exec(argsBas);
									Runtime.getRuntime().exec(argsAutoexec);
									//Si es linux o MAC
								}else if( (sSistemaOperativo.indexOf("mac") >= 0) || sSistemaOperativo.indexOf("nix") >= 0 || sSistemaOperativo.indexOf("nux") >= 0 || sSistemaOperativo.indexOf("aix") > 0 ) {
									String[] argsBas = { "/bin/sh", "-c", "cp", "-f", "sprites.bas", ".target/tools/openmsx/dsk" };
									String[] argsAutoexec = { "/bin/sh", "-c", "cp", "-f", "autoexec.bas", ".target/tools/openmsx/dsk" };
									Runtime.getRuntime().exec(argsBas);
									Runtime.getRuntime().exec(argsAutoexec);
								}
								
							}
						}else if(modeBasicOBinary==1) {
							FileManager.createBinary("sprites.asm");
							FileManager.createAutoexecBinary();
							if(sSistemaOperativo.indexOf("Win")>=0) {
								String[] argsBin = { "CMD", "/C", "MOVE", "/Y", "sprites.bin", "target\\tools\\openmsx\\dsk" };	
								String[] argsAutoexec = { "CMD", "/C", "MOVE", "/Y", "autoexec.bas", "target\\tools\\openmsx\\dsk" };
								Runtime.getRuntime().exec(argsBin);
								Runtime.getRuntime().exec(argsAutoexec);
							}else if( (sSistemaOperativo.indexOf("mac") >= 0) || sSistemaOperativo.indexOf("nix") >= 0 || sSistemaOperativo.indexOf("nux") >= 0 || sSistemaOperativo.indexOf("aix") > 0 ) {
								String[] argsBin = { "/bin/sh", "-c", "cp", "-f", "sprites.bin", ".target/tools/openmsx/dsk" };	
								String[] argsAutoexec = { "/bin/sh", "-c", "cp", "-f", "autoexec.bas", ".target/tools/openmsx/dsk" };
								Runtime.getRuntime().exec(argsBin);
								Runtime.getRuntime().exec(argsAutoexec);
							}
							
						}
						

						String cmdOpenMSX = "target\\tools\\openmsx\\openmsx.exe -machine Philips_NMS_8255 -diska target\\tools\\openmsx\\dsk"; //Comando de apagado en linux
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
	
	
	
	public JMenu getJMenuExportSC1() {
		return this.mnMenuExportSC1;
	}
	public JMenu getJMenuExportSC2() {
		return this.mnMenuExportSC2;
	}
	public JMenu getJMenuExportSC3() {
		return this.mnMenuExportSC3;
	}
	public JMenu getJMenuExportSC4() {
		return this.mnMenuExportSC4;
	}
	public JMenu getJMenuExportSC5() {
		return this.mnMenuExportSC5;
	}
	public void setSpriteAudmented(boolean audmented) {
		this.spriteAudmented=audmented;
	}
	
	
	
	
	
	
	
	
	
	
	
	private void openFile() {
		File fileOrigin=null;
		JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir"));
		jFileChooser.setDialogTitle("Open file");
		//Creamos el filtro
	    FileNameExtensionFilter filtro = new FileNameExtensionFilter("spr","SPR");
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    //Le indicamos el filtro
	    jFileChooser.setFileFilter(filtro);
		int result=jFileChooser.showSaveDialog(null);
		if(result==JFileChooser.APPROVE_OPTION) {
			//JOptionPane.showConfirmDialog(null, "WIP");
			String content="";
			String contentCompress="";
			fileOrigin=jFileChooser.getSelectedFile();
			canvas.setFileAutoSaved(fileOrigin.getAbsolutePath());
			try {
				ObjectInputStream objectInputputStream=new ObjectInputStream(new FileInputStream(fileOrigin));
				Sprite[] sprites=(Sprite[]) objectInputputStream.readObject();	
				if(sprites!=null) {
					Sprite sprite0=sprites[0];
					SpriteEditorWindow frame = new SpriteEditorWindow(sprite0.getType(),spriteNumColorsOrScreenMode);
					frame.setVisible(true);
				
					switch (sprite0.getNumColors()) {
						case 1:
							frame.setSpriteNumColorsOrScreenMode((byte)1);
							frame.checkStateForScreenChange((byte)1);
							break;
						case 2:
							frame.setSpriteNumColorsOrScreenMode((byte)2);
							frame.checkStateForScreenChange((byte)2);
							break;
						case 3:
							frame.setSpriteNumColorsOrScreenMode((byte)3);
							frame.checkStateForScreenChange((byte)3);
							break;
						case 4:
							frame.setSpriteNumColorsOrScreenMode((byte)4);
							frame.checkStateForScreenChange((byte)4);
							break;
						case 5:
							frame.setSpriteNumColorsOrScreenMode((byte)5);
							frame.checkStateForScreenChange((byte)5);
							break;
					}
					frame.setSpriteType(sprite0.getType());
					frame.setArrayListSprites(sprites);
					frame.getCanvas().setFileAutoSaved(canvas.getFileAutoSaved());
					//frame.setTitle("Sprite editor: file "+canvas.getFileAutoSaved());
		
					spriteEditorWindow.setVisible(false);
					spriteEditorWindow.dispose();
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
		boolean canSave=true;
		File file=null;
		if(arrayListSprites.size()==0) {
			JOptionPane.showMessageDialog(null, "There are no sprites");		
		}else {
			try {
				//Comprobamos si hay un archivo previamente abierto
				file=new File(canvas.getFileAutoSaved());
			}catch(Exception ex) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("Save");
			    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			    if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			    	file=chooser.getSelectedFile();
			    	if(file.exists() && !canvas.getAutoSaved()) {
			    			int input = JOptionPane.showConfirmDialog(null, "The file exists, do you want to overwrite it?");
							// 0=yes, 1=no, 2=cancel
							if(input==0) {
								canSave=true;
							}else {
								canSave=false;
							}	
			    	}else {
			    		file=new File(chooser.getSelectedFile().getAbsolutePath()+".spr");
			    	}
			    }
			}
		}	
		
		if(canSave) {
			//System.out.println("archivo "+file.getAbsolutePath());
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
					JOptionPane.showMessageDialog(null,  "Saved file");
				System.out.println("Saved file: "+file.getAbsolutePath());
				canvas.setFileAutoSaved(file.getAbsolutePath());
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null,  "Exception "+ex);
				System.out.println("Not saved file: "+file.getAbsolutePath());
			}
		}
	}
	
	private void saveAsFile() {
		if(arrayListSprites.size()==0) {
			JOptionPane.showMessageDialog(null, "There are no sprites");	
		}else {
			//JFileChooser chooser=new JFileChooser(System.getProperty("user.dir"));
			JFileChooser chooser = new JFileChooser();
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Save as");
		    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
		    	System.out.println("Aprobado");
		    	boolean canSave=true;
				File file=null;
				String fileNameSavedRecovery=chooser.getSelectedFile()+".spr";
				canvas.setFileAutoSaved(fileNameSavedRecovery);			
				file=new File(canvas.getFileAutoSaved());
				if(file.exists() && !canvas.getAutoSaved()) {
					int input = JOptionPane.showConfirmDialog(null, "The file exists, do you want to overwrite it?");
					// 0=yes, 1=no, 2=cancel
					if(input==0) {
						System.out.println("puede grabar");
						canSave=true;
					}else {
						System.out.println("No puede grabar");
						canSave=false;
					}		
				}

				if(canSave) {
					System.out.println("vamos a grabar grabar");
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
		    } else {
		      System.out.println("No Selection ");
		    }
		}

	}
	private void disableTextField(Component[] comp)
	  {
	    for(int x = 0; x < comp.length; x++)
	    {
	      if(comp[x] instanceof JPanel) disableTextField(((JPanel)comp[x]).getComponents());
	      else if(comp[x] instanceof JTextField)
	      {
	        //((JTextField)comp[x]).setEditable(false);	    	
	        ((JTextField)comp[x]).setText("");
	        //return;
	      }
	      
	    }
	  }
	public void closeAll() {
		canvas.setAutoSaved(false);
		canvas.setFileAutoSaved(null);
		checkBoxAutoSaved.setSelected(false);
		canvas.deleteAll();
		//Actualizamos los textareas
		canvas.fillTextAreaDefinitionAndColor();
		arrayListSprites.clear();
		//panelImagesSprites.getGraphics().clearRect(panelImagesSprites.getX(), panelImagesSprites.getY(), panelImagesSprites.getWidth(), panelImagesSprites.getHeight());
		riboon.removeAll();
		riboon.repaint();
		lblSpriteNumber.setText("");
		jtFieldName.setText("");
	}

	
}
