package es.tipolisto.MSXTools.utils;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import es.tipolisto.MSXTools.beans.Sprite;

public class FileManager {

	public FileManager() {}
	/**
	 * Permite leer un archivo de texto y devuelve un arrayList de strings con el texto
	 */
	public ArrayList<String> readFile(File file) {
		String linea="";
		ArrayList<String> arrayListString=new ArrayList<String>();
	    FileReader fr = null;
	    BufferedReader br = null;
	    String lineaSinDB="";
		try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         fr = new FileReader (file);
	         br = new BufferedReader(fr);
	         while((linea=br.readLine())!=null) {
	        	arrayListString.add(linea);
	         }
		}catch(Exception e){
         e.printStackTrace();
	    }finally{
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	    }
		return arrayListString;
	}
	/**
	 * Modificaci�n de la funci�n anterior parapoder trabajar con la clase Compress
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public ArrayList<String> readFileFromCompressWindow(File file) throws IOException {
		String linea="";
		ArrayList<String> arrayListString=new ArrayList<String>();
	    FileReader fr = null;
	    BufferedReader br = null;
	    String lineaSinDB="";
		try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         fr = new FileReader (file);
	         br = new BufferedReader(fr);
	         while((linea=br.readLine())!=null) { 
	        	 /*if (linea.substring(linea.length()-2, linea.length()).equals("00")) {
	        		 linea+="\n";
	        	 }*/
	        	
	        	arrayListString.add(linea+"\n");
	         }
		}catch(Exception e){
         e.printStackTrace();
	    }finally{
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	    }
		return arrayListString;
	}
	
	public ArrayList<String> readFileFromCompressWindow1(File file) throws IOException {
				ArrayList<String> arrayListString=new ArrayList<String>();
				BufferedReader reader1 = new BufferedReader(new InputStreamReader( new FileInputStream(file)));

				    int s1 = 0;
				    String formattedString = "";
				    while ((s1 = reader1.read()) != -1) {

				        char character = (char) s1;

				        System.out.println("Each Character: "+character+" its hexacode(Ascii): "+Integer.toHexString(character));
				        //output : "0a" --> \n 
				        //output : "0d" --> \r

				        if (character == '\n'){
				            formattedString+=" <LF> ";
				        }else if (character =='\r'){
				            formattedString+=" <CR> ";
				        }else
				            formattedString+=character;
				        
				        arrayListString.add(formattedString);
				    }
				    //System.out.println(formattedString);
		return arrayListString;
	}
	/**
	 * Escribe el arrayList de strings pasado por par�mtero en un archivo de texto
	 * @param fileDestiny
	 * @param arrayList
	 */
	public static void writeFile(File fileDestiny,ArrayList<String> arrayList) {
		FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try
        {
    		fileWriter = new FileWriter(fileDestiny, false);
            printWriter = new PrintWriter(fileWriter);
            for(String cadena: arrayList) {
            	printWriter.println(cadena);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fileWriter)
        	  fileWriter.close();
           	  System.out.println("FileManager say: file created "+fileDestiny.getAbsolutePath());
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
	
	public File createFileDestiny() {
		String fileDestinyPath=System.getProperty("user.dir");
		fileDestinyPath +="\\hex.txt";
		return new File(fileDestinyPath);
	}
	
	
	public boolean checkFile( String originFile) {
		boolean ok=false;
		try {
			File fileOrigin=new File(originFile);
			//Si existe el archivo origen y destino
			if (fileOrigin.exists()){
				if(fileOrigin.canRead()) {
					ok=true;
				}else {
					System.out.println("Impossible read "+originFile);
					ok=false;
				}
			//Si no existe file origin
			}else {
				ok=false;	
			}
		}catch(Exception ex) {
			System.out.println("There was a problem deleting comments"+ex.getMessage());
			return false;
		}	
		return ok;
	}
	
	
	
	public static void createdSpritesFileBasicDecimal(byte screen, byte spriteType, ArrayList<Sprite>arrayListSprites, boolean audmented, boolean showMessahe) {
		ArrayList<String> arrayListString=new ArrayList<String>();
		byte spriteVideo=0;
		if(spriteType==8 && audmented==false) spriteVideo=0;
		else if(spriteType==8 && audmented==true) spriteVideo=1;
		else if(spriteType==16 && audmented==false) spriteVideo=2;
		else if(spriteType==16 && audmented==true) spriteVideo=3;
		int numberLine=10200;
		int objetcs=arrayListSprites.size()-1;
		arrayListString.add(numberLine+" screen "+screen+","+spriteVideo);
		numberLine+=10;
		arrayListString.add(numberLine+" for i=0 to "+objetcs+":sp$=\"\"");
		numberLine+=10;
		if(spriteType==8)
			arrayListString.add("\t"+numberLine+" for j=0 to 7");
		if(spriteType==16)
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
		
		if(screen==4 || screen==5) {
			arrayListString.add(numberLine+" for i=0 to "+objetcs+":sp$=\"\"");
			numberLine+=10;
			if(spriteType==8)
				arrayListString.add("\t"+numberLine+" for j=0 to 7");
			else if(spriteType==16)
				arrayListString.add("\t"+numberLine+" for j=0 to 15");
			numberLine+=10;
			arrayListString.add("\t\t"+numberLine+" read a$");
			numberLine+=10;
			//arrayListString.add("\t\t"+numberLine+" sp$=sp$+chr$(val(\"&h\"+a$))");
			arrayListString.add("\t\t"+numberLine+" sp$=sp$+chr$(val(a$))");
			numberLine+=10;
			arrayListString.add("\t"+numberLine+" next J");
			numberLine+=10;
			arrayListString.add("\t"+numberLine+" color sprite$(i)=sp$");
			numberLine+=10;
			arrayListString.add(numberLine+" next I");
			numberLine+=10;
		}

		arrayListString.add(numberLine+" rem sprites data definitions");
		numberLine+=10;
		for (Sprite sprite: arrayListSprites) {
			arrayListString.add(numberLine+" rem data definition sprite "+sprite.getNumber()+", name: "+sprite.getName());
			numberLine+=10;
			String dataDefinitions=sprite.getDataDefinition();
			String[] stringsDefinitions=dataDefinitions.split("\n");
			for(String linea: stringsDefinitions) {
				arrayListString.add(numberLine+" data "+linea);
				numberLine+=10;
			}	
		}
		
		for (Sprite sprite: arrayListSprites) {
			 if(screen==4 ||screen==5) {
				arrayListString.add(numberLine+" rem data colors definitions sprite "+sprite.getNumber()+", name: "+sprite.getName());	
				String dataColors=sprite.getDataColors();
				String[] stringsColors=dataColors.split("\n");
				for(String linea: stringsColors) {
					arrayListString.add(numberLine+" data "+linea);
					numberLine+=10;
				}
			}
		}
		int file=0;
		int column=1;
		for (Sprite sprite: arrayListSprites) {
			arrayListString.add(numberLine+" rem data atrubutes sprite "+sprite.getNumber()+", name: "+sprite.getName());
			numberLine+=10;
			
			/*if(sprite.getNumber() !=0 && sprite.getNumber() % 7==0) {
				file+=33;
			}*/
			if(screen==1 || screen==2 || screen==3) {
				if(column>=33*4) {
					column=0;
					file+=33;
				}
				Color[] colors=sprite.getColorButtons0();
				Color color=colors[0];
				int positionColor=PaletteManager.getPositionColorOnPalette(color);
				arrayListString.add(numberLine+" put sprite "+sprite.getNumber()+",("+column+","+file+"),"+positionColor+","+sprite.getNumber());
			}else if(screen==4 || screen==5) {
				if(column>=33*7) {
					column=0;
					file+=33;
				}
				arrayListString.add(numberLine+" put sprite "+sprite.getNumber()+",("+column+","+file+"),,"+sprite.getNumber());
			}
			column+=33;
			numberLine+=10;
		}
		arrayListString.add(numberLine+" goto "+numberLine);
		writeFile(new File("sprites.bas"), arrayListString);		
		if(showMessahe)
			JOptionPane.showMessageDialog(null, "Created basic File");
		else
			System.out.println("Created basic File");
	}
	public static void createAutoexecBasic() {
		//creamos el autoexec.bas
		ArrayList<String> arrayListStringAutoexec=new ArrayList<String>();
		arrayListStringAutoexec.add("10 load\"sprites.bas\",r");
		writeFile(new File("autoexec.bas"), arrayListStringAutoexec);
	}
	
	public static void createdSpritesFileBinaryAutoLoad(byte screen, byte spriteType, ArrayList<Sprite> arrayListSprites, boolean audmented, boolean showConfirmaionMessage) {
		ArrayList<String> arrayListString=new ArrayList<String>();
		arrayListString.add("\t\t output sprites.bin");
		arrayListString.add("\tdb   #fe");
		arrayListString.add("\tdw   INICIO ");
		arrayListString.add("\tdw   FINAL");
		arrayListString.add("\tdw   INICIO ");
		arrayListString.add("\torg  #d000 ");
		arrayListString.add("INICIO: ");
		/*********************Estableciendo modo v�deo*******************************/
		arrayListString.add("; Setup video mode");		
		arrayListString.add("\tld a,"+screen+"");
		arrayListString.add("\tcall #005F ; CHGMOD equ #005F, change mode video to screen "+screen+"");
		/*********************Estableciendo modo tama�o sprites*******************************/
		arrayListString.add("; Setup sprite size");
		if (spriteType==8 && audmented==false){
			arrayListString.add(";sprites no ampliados de 8x8");
			arrayListString.add("\tld b,#e0");
			arrayListString.add("\tld c,1");
		}if (spriteType==8 && audmented==true){
			arrayListString.add(";sprites ampliados de 8x8");
			arrayListString.add("\tld b,#e0");
			arrayListString.add("\tld c,1");
		}else if(spriteType==16 && audmented==false) {
			arrayListString.add("; Sprites no ampliados de 16x16");
			arrayListString.add("\tld b,#e2");
			arrayListString.add("\tld c,1");
			arrayListString.add("\tcall #0047;WRTVDP equ #0047, b data to write, c number register, escribe en los registros del VDP");
		}else if(spriteType==16 && audmented==true) {
			arrayListString.add("; Sprites ampliados de 16x16");
			arrayListString.add("\tld b,#e3");
			arrayListString.add("\tld c,1");
			arrayListString.add("\tcall #0047;WRTVDP equ #0047, b data to write, c number register, escribe en los registros del VDP");

		}
		
		/*********************DEFINICICIONES*******************************/
		arrayListString.add("\tld hl, sprites_definition");
			if(screen==1 || screen==2 || screen==3)
				arrayListString.add("\tld de, 14336 ;&h3800, base(14) en sc2");
			if(screen==4 || screen==5)
				arrayListString.add("\tld de, 30720 ;&h7800, base(29) en sc5");
			 if(spriteType==8)
				 arrayListString.add("\tld bc, 8*"+arrayListSprites.size());
			 if(spriteType==16 ) 
				 arrayListString.add("\tld bc, 32*"+arrayListSprites.size());
			 arrayListString.add("\tcall  #005C; #005C=LDIRVM ");
		/******************END DEFINICICIONES*******************************/
		/**************************COLORS*******************************/
		if(screen==4 || screen==5) {
			arrayListString.add("\tld hl, sprites_colors");
			arrayListString.add("\tld de, 29696 ;&h7400, en sc5");
			arrayListString.add("\tld bc, 16*"+arrayListSprites.size());
			arrayListString.add("\tcall  #005C; #005C=LDIRVM ");
		}
		/***********************END COLORS*******************************/
		/**************************ATRIBUTES*******************************/
		arrayListString.add("\tld hl, sprites_atributes");
		if(screen==1 || screen==2 || screen==3) {	
			arrayListString.add("\tld de, 6912 ;&h1b00, base(13) en sc2");
			arrayListString.add("\tld bc, 4*"+arrayListSprites.size());
		}else if(screen==4 || screen==5) {
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
		
		if(screen==4 || screen==5)arrayListString.add("sprites_colors: ");
		for (Sprite sprite: arrayListSprites) {
			if(screen==4 || screen==5) {
				arrayListString.add(";Data colors sprite "+sprite.getNumber()+", name: "+sprite.getName());
				String dataColors=sprite.getDataColors();
				String[] stringsColors=dataColors.split("\n");
				for(String linea: stringsColors) {
					arrayListString.add("\tdb "+linea);
				}
			 }
		}
		arrayListString.add("sprites_atributes: ");
		int file=0;
		int column=0;
		for (Sprite sprite: arrayListSprites) {
			arrayListString.add(";Data atributes sprite (y,x,patron,color) "+sprite.getNumber()+", name: "+sprite.getName());
			
			if(screen==1 || screen==2 || screen==3) {
				if(column>33*4) {
					column=0;
					file+=33;
				}
				Color[] colors=sprite.getColorButtons0();
				Color color=colors[0];
				int positionColor=PaletteManager.getPositionColorOnPalette(color);
				//y,x,number,color
				if(sprite.getType()==8)
					arrayListString.add("\tdb "+file+","+column+","+sprite.getNumber()+","+positionColor);
				else if(sprite.getType()==16)
					arrayListString.add("\tdb "+file+","+column+","+sprite.getNumber()*4+","+positionColor);
			}else if(screen==4 || screen==5) {
				if(column>33*7) {
					column=0;
					file+=33;
				}
				String dataColors=sprite.getDataColors();
				String[] stringsColors=dataColors.split("\n");
				for(String linea: stringsColors) {
					arrayListString.add("\tdb "+file+","+column+","+sprite.getNumber()*4+", \" \"");
				}
			}
			column+=33;
		}

		arrayListString.add("FINAL: ");
		writeFile(new File("sprites.asm"), arrayListString);
		if(showConfirmaionMessage)
			JOptionPane.showMessageDialog(null, "Created bin File");
		else
			System.out.println("Created bin File");
	}
	
	
	public static void createdSpritesFileBinary( byte spriteType, ArrayList<Sprite> arrayListSprites, boolean showConfirmaionMessage) {
		ArrayList<String> arrayListString=new ArrayList<String>();
		arrayListString.add("\t\t output sprites.bin");
		arrayListString.add("\tdb   #fe");
		arrayListString.add("\tdw   INICIO ");
		arrayListString.add("\tdw   FINAL");
		arrayListString.add("\tdw   INICIO ");
		arrayListString.add("\torg  #d000 ");
		arrayListString.add("INICIO: ");
		arrayListString.add("sprites_definition:");
		for (Sprite sprite: arrayListSprites) {
			arrayListString.add(";Definition sprite "+sprite.getNumber()+", name: "+sprite.getName());
			String dataDefinitions=sprite.getDataDefinition();
			String[] stringsDefinitions=dataDefinitions.split("\n");
			for(String linea: stringsDefinitions) {
				arrayListString.add("\tdb "+linea);
			}	
		}
		arrayListString.add("FINAL: ");
		writeFile(new File("sprites.asm"), arrayListString);
		if(showConfirmaionMessage)
			JOptionPane.showMessageDialog(null, "Created bin File");
		else
			System.out.println("Created bin File");
	}
	
	public static void createAutoexecBinary() {
		//Creamos el autoexec
		ArrayList<String> arrayListStringAutoexec=new ArrayList<String>();
		arrayListStringAutoexec.add("10 bload\"sprites.bin\",r");
		writeFile(new File("autoexec.bas"), arrayListStringAutoexec);
	}
	
	
	public static void createBinary(String fileName) {
		String sSistemaOperativo = System.getProperty("os.name");
		System.out.println(sSistemaOperativo);
		try {
			String cmdOpenSjasm ="";
			if(sSistemaOperativo.indexOf("Win")>=0) {
				 cmdOpenSjasm = "target\\tools\\sjasm\\sjasm.exe "+fileName; 
			}else if( (sSistemaOperativo.indexOf("mac") >= 0) || sSistemaOperativo.indexOf("nix") >= 0 || sSistemaOperativo.indexOf("nux") >= 0 || sSistemaOperativo.indexOf("aix") > 0 ) {
				cmdOpenSjasm = ".target/tools/sjasm/sjasm "+fileName; 
			}else {
				System.out.println("No found system operation.");
			}
			Runtime.getRuntime().exec(cmdOpenSjasm);
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} 
		System.out.println("Created binary File.");
		//JOptionPane.showMessageDialog(null, "Created binary File");
	}
}
