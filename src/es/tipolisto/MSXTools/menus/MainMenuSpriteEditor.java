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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import es.tipolisto.MSXTools.beans.Sprite;
import es.tipolisto.MSXTools.gui.PaneDrawableSpriteEditor;
import es.tipolisto.MSXTools.gui.RiboonSpriteEditor;
import es.tipolisto.MSXTools.utils.FileManager;
import es.tipolisto.MSXTools.utils.StringManager;


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
	private JComboBox comboBoxScreenInEditor,comboBoxTypeSpriteInEditor;
	private JMenu mnMenuExportSC2,mnMenuExportSC5;

	//int countSprites;
	public MainMenuSpriteEditor(ArrayList<Sprite> arrayListSprites,
			PaneDrawableSpriteEditor canvas,
			RiboonSpriteEditor riboon,
			JCheckBox checkBoxAutoSaved,
			JLabel lblSpriteNumber,
			JTextField jtFieldName,
			JComboBox comboBoxScreenInEditor,JComboBox comboBoxTypeSpriteInEditor) {
		this.arrayListSprites=arrayListSprites;
		this.canvas=canvas;
		this.riboon=riboon;
		this.checkBoxAutoSaved=checkBoxAutoSaved;
		this.lblSpriteNumber=lblSpriteNumber;
		this.jtFieldName=jtFieldName;
		this.comboBoxScreenInEditor=comboBoxScreenInEditor;
		this.comboBoxTypeSpriteInEditor=comboBoxTypeSpriteInEditor;
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
				closeAll();
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
		
		mnMenuExportSC2 = new JMenu("SC2");
		mnMenuExport.add(mnMenuExportSC2);
		JMenuItem mntmNewMenuItem = new JMenuItem("Export to bas");
		mnMenuExportSC2.add(mntmNewMenuItem);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					FileManager.createdSpritesFileBasicDecimal((byte)2,(byte)2, arrayListSprites, true);	
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
					FileManager.createdSpritesFileBinaryDecimal((byte)2,(byte)2, arrayListSprites);	
				}
			}
		});
		
		
		mnMenuExportSC5 = new JMenu("SC5");
		mnMenuExport.add(mnMenuExportSC5);
		JMenuItem mntmNewMenuSC5ExportToBas = new JMenuItem("Export to bas");
		mnMenuExportSC5.add(mntmNewMenuSC5ExportToBas);
		mntmNewMenuSC5ExportToBas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					//Le tenemos que especificar el screen y el tamaño de los sprites que son 1 para 8x8px o 2 para 16x16px
					FileManager.createdSpritesFileBasicDecimal((byte)5,(byte)2, arrayListSprites, true);	
				}
			}
		});

		JMenuItem mntMenuSC5ExportToBin = new JMenuItem("Export to bin");
		mnMenuExportSC5.add(mntMenuSC5ExportToBin);
		mntMenuSC5ExportToBin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arrayListSprites.size()<=0) {
					JOptionPane.showMessageDialog(null, "There are no sprites");
				}else {
					FileManager.createdSpritesFileBinaryDecimal((byte)5,(byte)2, arrayListSprites);		
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
	
	
	
	
	public JMenu getJMenuExportSC2() {
		return this.mnMenuExportSC2;
	}
	public JMenu getJMenuExportSC5() {
		return this.mnMenuExportSC5;
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
				
				if(sprites!=null) {
					Sprite sprite0=sprites[0];
					//comboBoxTypeSpriteInEditor tiene el la posici�n 1 la palabra SC0 y en la posici�n 1 tiene SC1, etc		
					if(sprite0.getNumColors()==2)comboBoxScreenInEditor.setSelectedIndex(2);
					else if(sprite0.getNumColors()==5)comboBoxScreenInEditor.setSelectedIndex(5);
					//comboBoxTypeSpriteInEditor tiene en la posici�n 0 8x8 pixeles, 1 para 16x16, etc
					if(sprite0.getType()==8)comboBoxTypeSpriteInEditor.setSelectedIndex(0);
					else if(sprite0.getType()==16)comboBoxTypeSpriteInEditor.setSelectedIndex(1);
					
				}
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
