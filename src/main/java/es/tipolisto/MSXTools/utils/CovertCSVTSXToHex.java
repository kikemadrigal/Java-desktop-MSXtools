package es.tipolisto.MSXTools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
/**
 * 
 * @author MSX Murcia, tipolisto.es
 * Esta clase recibe 2 strings en su constructor con la ruta
 * 
 * Va leyendo las líenas del archivo.tmx, si encuentra un "<" o "!" se la salta
 * Después las convierte a hexadecimal que es más facil de trabajar, tu código basic debe de estar en hexadecimal
 */
public class CovertCSVTSXToHex {
	FileManager fileManager;
	public CovertCSVTSXToHex() {
		fileManager=new FileManager();
	}
	
	
	/**
	 * Devuelve un arrayList co los datos hexdecimales por línea
	 * @param file
	 * @return arrayList
	 */ 
	public ArrayList<String> extractHexData(File fileOrigin) {
		fileManager=new FileManager();
		String cadenaValorHexadecimal="";
		ArrayList<String> arrayListStringNotFormat=new ArrayList<String>();
		ArrayList<String> arrayListStringFormat=new ArrayList<String>();
		arrayListStringNotFormat=fileManager.readFile(fileOrigin);
		for(String linea: arrayListStringNotFormat) {
			//System.out.println(linea);
			//Si la linea no contiene un signo de "<" y no es null ni está vacía
       	 	if (!StringManager.buscarMenorQue(linea) && linea!="" && linea!=null && linea.length()>0) {
       	 		//System.out.println(linea);
       	 		//1.Obtenemos los números
	 	   		String[] parts = linea.split(",");
	 	   		String lineaFormateada="";
	 	   		int valorDecimal=0;
	 	   		//comprobamos el numero de dígitos
	 	   		for (String argumento: parts) {
	 	   			//2.obtenemos su valor decimal
	 	   			try {
	 	   				valorDecimal=Integer.valueOf(argumento);
	 	   			}catch(Exception ex) {
	 	   				System.out.println("excepcion en entero "+ex.getMessage());
	 	   				cadenaValorHexadecimal="0";
	 	   			}
	   				//3.Si es un 0 o -1 escribimos 00
	   				if (valorDecimal==0 || valorDecimal==-1) cadenaValorHexadecimal="00";
	   				else
	   					//4.Convertimos el valor decimal del CSV en hexadecimal
	   					cadenaValorHexadecimal=NumberManager.decimalAHexadecimal(valorDecimal);
	 	   			//5.Si es un solo digito le ponemos un 0 a la izquierda
	   				if(cadenaValorHexadecimal.length()==1) {
	 	   				cadenaValorHexadecimal="0"+String.valueOf(cadenaValorHexadecimal);
	 	   			}
	   				//Si son 2 digitos no hay que hacer nada
	   				lineaFormateada=lineaFormateada+cadenaValorHexadecimal;
	 	   		}
	 	   		//Añadimos las líneas formateadas
	 	   		arrayListStringFormat.add(lineaFormateada);	
	 	   		//System.out.println(lineaFormateada);	
       	 	}
		}
		return arrayListStringFormat;
	}

	public void writeHexDataToFile(File writeFile, ArrayList arrayList) {
		fileManager.writeFile(writeFile, arrayList);
	}
	/*
	private void leerArchivo() {
		File archivo = null;
	    FileReader fr = null;
	    BufferedReader br = null;
	    String lineaSinDB="";
		try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         archivo = new File (nombreArchivoAConvertir);
	         if(!archivo.canRead()) {
	        	 System.out.println("Hubo un problema de lectura");
	         }else {
	        	 
	         }
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         String textoFormateado;
	         String linea;
	         String cadenaValorHexadecimal;
	         while((linea=br.readLine())!=null) {
	        	 //Si encuentra una coma () o 1 ' o 1  ' simplemente no escribe esa línea
	        	 //linea=StringManager.eliminarEspacios(linea);
	        	 if (!StringManager.buscarMenorQue(linea)) {
			     	   	if(linea!="" && linea!=null && linea.length()>0) {
			     	   		//1.Obtenemos los números
			     	   		String[] parts = linea.split(",");
			     	   		String lineaFormateada="";
			     	   		//comprobamos el numero de dígitos
			     	   		for (String argumento: parts) {
			     	   			//2.obtenemos su valor decimal
		     	   				int valorDecimal=Integer.valueOf(argumento);
		     	   				//3.Le restamos 1
		     	   				valorDecimal =valorDecimal-1;
		     	   				//4.Si es un 0 escribimos 00
		     	   				if (valorDecimal==0) cadenaValorHexadecimal="00";
		     	   				else
		     	   					//5.Convertimos el valor decimal del CSV en hexadecimal
		     	   					cadenaValorHexadecimal=NumberManager.decimalAHexadecimal(valorDecimal);
			     	   			//5.Si es un solo digito le ponemos un 0 a la izquierda
		     	   				if(cadenaValorHexadecimal.length()==1) {
			     	   				cadenaValorHexadecimal="0"+String.valueOf(cadenaValorHexadecimal);
			     	   			}
		     	   				//Si son 2 digitos no hay que hacer nada
		     	   				lineaFormateada=lineaFormateada+cadenaValorHexadecimal;

			     	   		}
			     	   		escribirEnFichero(lineaFormateada);
			     	   	}
	        	 }
	         }
	         System.out.println("Convertido!!");
		}catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }

	}
	*/

	
	
	
	




}
