package es.tipolisto.MSXTools;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.*;

import es.tipolisto.MSXTools.beans.Sprite;
import es.tipolisto.MSXTools.gui.CompressWindow;
import es.tipolisto.MSXTools.gui.MainWindow;
import es.tipolisto.MSXTools.utils.ConvertDeleteComents;
import es.tipolisto.MSXTools.utils.CovertCSVTSXToASM;
import es.tipolisto.MSXTools.utils.FileManager;
import es.tipolisto.MSXTools.utils.IMG2SC;

 
public class Main {
	public static void main(String[] args) {
		//Si hay argumentos
		if(args.length<=0) {
			createMainWindow();
		}else {
			//Mode 
			int mPosition=-1;
			//Help
			int hPosition=-1;
			//origin file
			int oPosition=-1;
			//destiny file
			int dPosition=-1;
			int mIndex=0;
			int hIndex=0;
			int oIndex=0;
			int dIndex=0;
			boolean mEncontrado=false;
			boolean hEncontrado=false;
			boolean oEncontrado=false;
			boolean dEncontrado=false;
			String originFile="";
			String destinyFile="";
			//Recorremos todo el array en busca de las letras -m -h -o -d
			int i=0;
			for (String arg:args){
				//o=file origen
				oPosition=arg.indexOf("-o");
				//d=file destiny
				dPosition=arg.indexOf("-d");
				//m=mode
				mPosition=arg.indexOf("-m");
				//h=help
				hPosition=arg.indexOf("-h");
				if (mPosition != -1) {
					mEncontrado=true;
					mIndex=i;
				}
				if (hPosition != -1) {
					hEncontrado=true;
					hIndex=i;
				}
				if (oPosition != -1) {
					oEncontrado=true;
					oIndex=i;
				}
				if (dPosition != -1) {
					dEncontrado=true;
					dIndex=i;
				}
				i++;
			}
			
			
			if(!mEncontrado && !hEncontrado || hEncontrado) {
				sendHelpMessage();
				System.exit(0);
			}
			
	
			
			
			if (oEncontrado){
				originFile=args[oIndex].substring(oPosition+3, args[oIndex].length());
			}else {
				 System.out.println("Origin file not found "+originFile);
				 System.exit(0);
			}
			

			if(mEncontrado) {
				//Si el modo elegido es la opción de borrar (d)
				//java -jar -o=main.bas -m=d
				if (args[mIndex].length()>2 && args[mIndex].indexOf("d")!=-1) {
					if(checkFile("d",originFile)) {
						System.out.println("Let's remove the comments from the file "+originFile);
						ConvertDeleteComents conversor=new ConvertDeleteComents();
						System.out.println("Let's delete comments ");
						conversor.convertNameDestinyLess(new File(originFile));
						System.out.println("Comments deleted.");
					}		
					//-m=a creará un archivo asm list apara ser ensamblado
				//java -jar -o=tilemap.tmx -m=a
				}else if (args[mIndex].length()>2 && args[mIndex].indexOf("a")!=-1) {
					if (checkFile("a",originFile)) {
						System.out.println("Let's covert tmx/csv file to asm file");
						CovertCSVTSXToASM conversor=new CovertCSVTSXToASM();
						//Le indicamos que es un TSX(boolean=true)
						ArrayList<String> arrayList=conversor.extractASMDataTMXSjasm(new File(originFile),true);
						String fileDestinyName=originFile.substring(0,originFile.length()-4)+".asm";
						conversor.writeASMDataToFile(new File(fileDestinyName),arrayList);
						System.out.println("TMX/CSV converted.");
					}
				//El -m=s creará un sc5 de la imagen PNG dada en originFile
				}else if (args[mIndex].length()>2 && args[mIndex].indexOf("t")!=-1) {
					if (checkFile("t",originFile)) {
						System.out.println("Let's convert png to sc5...");
						IMG2SC img2sc=new IMG2SC();
						BufferedImage bufferedImageOrigin=img2sc.getBufferedImage(new File(originFile));
						String fileExtension=originFile.substring(originFile.length()-3,originFile.length());
						if(fileExtension.equalsIgnoreCase("PNG")) {						
							StringBuilder result=img2sc.associateColorsPNGWithPaletteReturnStringSC5(bufferedImageOrigin,10000);
							try {
								img2sc.createFileWithString(result, originFile);
								System.out.println("File created");
							} catch (IOException e1) {
								System.out.println("There was a problem and the image could not be created: "+e1);
							}							
						}else {
							System.out.println("The image is not PNG format");
						}
					}else {
						System.out.println("Choose a file");
					}	
					//con m=b convertirmos un archivo spr a basic sc5
				}else if (args[mIndex].length()>2 && args[mIndex].indexOf("b")!=-1) {
					System.out.println("Let's convert spr to basic sc5...");
					//1.Creamos un arrayList de sprites
					ArrayList<Sprite> arrayListSprites=new ArrayList<Sprite>();
					//2.Obtenemos los sprites
					try {
						arrayListSprites.clear();
						ObjectInputStream objectInputputStream=new ObjectInputStream(new FileInputStream(originFile));
						Sprite[] sprites=(Sprite[]) objectInputputStream.readObject();
						for (int i2=0;i2<sprites.length;i2++) {
							Sprite sprite=sprites[i2];
							arrayListSprites.add(sprite);
						}
						objectInputputStream.close();
						FileManager.createdFileBasicDecimal((byte)5,(byte)2, arrayListSprites, false);	
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null,  "Exception "+ex);
					}
					
				}else {
					System.out.println("Mode found but not defined");
					System.exit(0);
				}
			}
	
		}//Final del idf si la longitud es menor de 0
		
	}
	
	/**
	 * Chekea el acceso al archivo según el modo
	 * @param option
	 * @param originFile
	 * @return
	 */
	private static boolean checkFile(String option, String originFile) {
		boolean ok=false;
		try {
			File fileOrigin=new File(originFile);
			//Si existe el archivo origen y destino
			if (fileOrigin.exists()){
				if(fileOrigin.canRead()) {
					//Si la opción es borrar comentarios
					//Borrar comentarios
					if (option.equals("d")) {
						ok=true;
						//Convertir imagen a screen 2
					}else if (option.equals("s")) {
						ok=true;
						//Convertir imagen a screen 5
					}else if (option.equals("t")) {
						ok=true;
						//crear archivo asm sjasm a patir de tmx/csv
					}else if (option.equals("a")) {
						ok=true;
						//Convertir archivo spr en basic sc5
					}else if (option.equals("b")) {
						ok=true;
					}
				}else {
					System.out.println("Impossible read "+originFile);
					ok=false;
				}
			//Si no existe file origin
			}else {
				//Si la opción es borrar comentarios si no existe un
				if (option.equals("d")) {
					System.out.println("File "+originFile+" not found."); 
					ok=false;
					//Si la opción es convertir a sc5
				}else if (option.equals("s") || option.equals("t")) {
					System.out.println("File "+originFile+" not found."); 
					ok=false;
				}else if (option.equals("a")) {
					System.out.println("File "+originFile+" not found."); 
					ok=false;
				}else if (option.equals("b")) {
					System.out.println("File "+originFile+" not found."); 
					ok=false;
				}
			}
		}catch(Exception ex) {
			System.out.println("There was a problem deleting comments"+ex.getMessage());
			return false;
		}	
		return ok;
	}
	
	


	
	
	
	
	private static void showLogo() {
		System.out.print( "                                                  \n"+
							"                                  .               \n"+
							"                                  i.              \n"+
							" MSX MUrcia 2022              :.  i.  ..          \n"+
							"                         :.    i  .  .i   ..:     \n"+
							"                          .::.  .....   .::.      \n"+
							"                               :i::::i. .         \n"+
							"                          ... .i::.:.:i  ...      \n"+
							"                              .i:::.::i           \n"+
							"                            .:  :i:i::  :.        \n"+
							"                         .::.            .::.     \n"+
							"               :bZ       .    .i  .. .r.    .     \n"+
							"  :            grQ            .   i.              \n"+
							"B5Q      .:vKQBBRQ                i.              \n"+
							"B.XUL7L5BBBBgsv MB                .               \n"+
							"Ur   7qP7.        Mr                              \n"+
							":giJM1.            B.                             \n"+
							 "B..               UQrrsri::...               5qS \n"+
							 "Bb              :KDr2M5ii77Yj1sJL2S511uY7r:.:B B \n"+
							 "5Bs          :qRQqi.                      i2BB B \n"+
							 "7vQq     :7IRBKr.             sEQL     :vSSJ:  B \n"+
							 "rB .PQDDqJvIr                 B.7g rKDgUi      B \n"+
							 ".B    7P  r.                  M: BXs:          B \n"+
							  "B1vr:.PL.KI...               dj          .iJP B \n"+
							  "B:v7vL7jUJUu2YvuI25121IujLYrrB2      .r1PIr B.B \n"+
							  "Q.7PQBR2riirigBqr77sYLv7vYvvrB2  .r2K5r.    PQd \n"+
							  "Q B          .:     ....::i::XI Qur.            \n"+
							  "gKQ                          .5 B               \n\n\n");
	}
	
	private static void sendHelpMessage() {
		showLogo();
		System.out.println("write java -jar -o=<origin file> -m=<parameter> ");
		System.out.println("-m=d for deletecomments on file bas");
		System.out.println("-m=b for converter file csv to bas");
		System.out.println("-m=h for converter file tsx to hex");
		System.out.println("-m=a for converter file tsx to file asm sjasm");
		System.out.println("-m=s for converter BMP image to sc5");
		System.out.println("-m=t for converter PNG image to sc5");
		System.out.println("-m=b for converter SPR file to basic sc5 code");
		System.out.println("Examples:");
		System.out.println("java -jar -m=s -o=assets\\test.bmp");
		System.out.println("java -jar -m=t -o=test.png");
		System.out.println("java -jar -m=d -o=assets\\main.bas");
		System.out.println("java -jar -m=a -o=tilemap.tmx");
		
		
	}
	
	

	private static void createMainWindow() {
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
}
