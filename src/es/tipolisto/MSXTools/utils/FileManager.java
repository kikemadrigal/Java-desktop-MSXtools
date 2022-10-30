package es.tipolisto.MSXTools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

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
	 * Modificación de la función anterior parapoder trabajar con la clase Compress
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
	 * Escribe el arrayList de strings pasado por parámtero en un archivo de texto
	 * @param fileDestiny
	 * @param arrayList
	 */
	public void writeFile(File fileDestiny,ArrayList<String> arrayList) {
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
	
}
