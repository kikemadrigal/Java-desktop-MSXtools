package es.tipolisto.MSXTools.utils;

import java.io.File;
import java.util.ArrayList;

public class CovertCSVTSXToASM {
	FileManager fileManager;

	public CovertCSVTSXToASM() {
		fileManager=new FileManager();
	}
	
	/**
	 * Devuelve un arrayList co los datos hexdecimales por línea
	 * @param file
	 * @return arrayList
	 */ 
	public ArrayList<String> extractASMDataTMXSjasm(File fileOrigin,boolean CSVOrTMX) {
		fileManager=new FileManager();
		String linea="";
		String lineaFormateada="";
		int counter=7;
		String ultimaLetra="";
		//ArrayList<String> arrayListStringASMTemplates=new ArrayList<String>();
		ArrayList<String> arrayListStringNotFormat=new ArrayList<String>();
		ArrayList<String> arrayListStringFormat=new ArrayList<String>();
		ArrayList<String>arrayListCaca=fileManager.readFile(fileOrigin);
		//Le indicamos que es TMX(boolean=true)
		arrayListStringNotFormat=subtractOne(arrayListCaca,CSVOrTMX);
		//System.out.println("tamaño not format "+arrayListStringNotFormat.size());
		//cogemos el nombre del archivo destino
		String fileDestiny=fileOrigin.getName().substring(0,fileOrigin.getName().length()-4);
		arrayListStringFormat.add("\t\t output "+fileDestiny+".bin");
		arrayListStringFormat.add("\tdb   #fe");
		arrayListStringFormat.add("\tdw   INICIO ");
		arrayListStringFormat.add("\tdw   FINAL");
		arrayListStringFormat.add("\tdw   INICIO ");
		arrayListStringFormat.add("\torg  #d000 ");
		arrayListStringFormat.add("INICIO: ");

		//for(String linea: arrayListStringNotFormat) {
		for (int i=arrayListStringNotFormat.size()-1;i>=0;i--) {
			linea=arrayListStringNotFormat.get(i);

			//System.out.println(linea);
			//Si la linea no contiene un signo de "<" y no es null ni está vacía
       	 	if (!StringManager.buscarMenorQue(linea) && linea!="" && linea!=null && linea.length()>0) {
	 	   		lineaFormateada="";
	 	   		lineaFormateada="\tdb "+linea;	
	 	   		//Comprobamos si la última letra es una com para quitarla
	 	   		//ultimaLetra=substring(fileOrigin.getName().length(),fileOrigin.getName().length()-1);
	 	   		ultimaLetra=lineaFormateada.substring(lineaFormateada.length()-1, lineaFormateada.length());
	 	   		//ultimaLetra=lineaFormateada.charAt(fileOrigin.getName().length());
	 	   		if(ultimaLetra.equals(",")) lineaFormateada=lineaFormateada.substring(0, lineaFormateada.length()-1);
	 	   		//if(ultimaLetra==',') lineaFormateada=lineaFormateada.substring(0, lineaFormateada.length()-1);
	 	   		//if(ultimaLetra==',') lineaFormateada=lineaFormateada.substring(0, lineaFormateada.length()-1);
	   		
	 	   		
	 	   		arrayListStringFormat.add(counter,lineaFormateada);	
	 	   		//System.out.println(lineaFormateada);
       	 	}
		}
		arrayListStringFormat.add("FINAL: ");
		return arrayListStringFormat;
	}

	public void writeASMDataToFile(File writeFile, ArrayList arrayList) {
		fileManager.writeFile(writeFile, arrayList);
	}
	
	private ArrayList<String> subtractOne(ArrayList<String> arrayList, boolean CSVOrTMX){
		ArrayList<String> arrayListStringFormat=new ArrayList();
		String cadenaValor="";
		for(String linea: arrayList) {
			//Si la linea no contiene un signo de "<" y no es null ni está vacía
       	 	if (!StringManager.buscarMenorQue(linea) && linea!="" && linea!=null && linea.length()>0) {
       	 		//1.Obtenemos los números
	 	   		String[] parts = linea.split(",");
	 	   		String lineaFormateada="";
	 	   		int valorDecimal=0;
	 	   		//comprobamos el numero de dígitos
	 	   		for (String argumento: parts) {
	 	   			//2.obtenemos su valor decimal
	 	   			try {
	 	   				valorDecimal=Integer.valueOf(argumento);
	 	   				if (CSVOrTMX) valorDecimal--;
	 	   				cadenaValor=String.valueOf(valorDecimal);
	 	   			}catch(Exception ex) {
	 	   				System.out.println("excepcion en entero "+ex.getMessage());	 	   				
	 	   			}
	   				lineaFormateada+=cadenaValor+",";
	 	   		}
	 	   		//Añadimos las líneas formateadas
	 	   		arrayListStringFormat.add(lineaFormateada);	
       	 	}
		}
		return arrayListStringFormat;
	}

}
