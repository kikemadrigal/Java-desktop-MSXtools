package es.tipolisto.MSXTools.utils;

import java.io.File;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.tipolisto.MSXTools.beans.RGB;

public class CreateLineNumbers {
	private FileManager filemanager;
	private File fileOrigin;
	private File fileDestiny;
	private int numberLine;
	private StringManager stringManager;
	private final String regex;
	private final String regexIsDigits;
	private Pattern pattern,patterIsDigit;
	private Matcher matcher;

	public CreateLineNumbers() {
		filemanager=new FileManager();
		stringManager=new StringManager();
		numberLine=0;
		regex="\\{.*\\}";
		regexIsDigits="^\\d.*";
		pattern = Pattern.compile(regex);
		patterIsDigit=Pattern.compile(regexIsDigits);
	}
	
	public void convertNameDestinyLess(File fileOrigin) {
		ArrayList<String> arrayList=filemanager.readFile(fileOrigin);
		ArrayList<String>arrayListFormatTexts=createLineNumbers(arrayList);
		String newNameFileDestiny=fileOrigin.getName().substring(0,fileOrigin.getName().length()-4)+"-lines.bas";
		String os = System.getProperty("os.name");
		String absolutePathDestiny="";
		String fileDestinyAbsolutePath="";
		//System.out.println(os);
		if(os.contains("Windows")) {
			absolutePathDestiny=fileOrigin.getAbsolutePath().substring(0,fileOrigin.getAbsolutePath().lastIndexOf("\\"));
			fileDestinyAbsolutePath=absolutePathDestiny+"\\"+newNameFileDestiny;
		}else{
			absolutePathDestiny=fileOrigin.getAbsolutePath().substring(0,fileOrigin.getAbsolutePath().lastIndexOf("/"));
			fileDestinyAbsolutePath=absolutePathDestiny+"/"+newNameFileDestiny;
		}
		filemanager.writeFile(new File(fileDestinyAbsolutePath), arrayListFormatTexts);
	}

	
	
	private ArrayList<String> createLineNumbers(ArrayList<String> arrayList) {
		 String fomartText="";
		 ArrayList<String> arrayListFormatTexts=new ArrayList<String>();
		 HashMap<String, String> palabrasDestino=new HashMap<String,String>();
		 boolean isNumeric=false;
		 int position=0;
		 String numeroInicial="";
		 //1.Añadimos el númeo de línea y buscamos las palabaras destino
		 //buscamos todas las ocurrencias de esa palabra y las sustituimos por el número de línea
         for (int i=0;i<arrayList.size();i++) {
        	String linea=arrayList.get(i);
        	//Quitamos los espacios en blanco al principio y al final de la línea
        	linea=linea.trim();
        	if(linea!="" && linea!=null && linea.length()>0 && !linea.equals(" ")) {
        		//Si la línea empieza con un número asignamos ese número al contador de líneas
        		do{
        			isNumeric=checkIsNumeric(linea, position);
        			if(isNumeric)
        				numeroInicial=linea.substring(0,position+1);
        			position++;
        		}while(isNumeric);
        	
        		if(!numeroInicial.isEmpty()) {
        			numberLine=Integer.valueOf(numeroInicial);
        			linea=linea.substring(position, linea.length());
        			linea=linea.trim();
        		}else {
    	    		numberLine+=10;
    	    		
        		}
        		position=0;
        		numeroInicial="";

	  	   		matcher=pattern.matcher(linea);
		  	   	if (matcher.find()) {
		  	   		int posicionCorcheteAbierto=linea.indexOf("{");
		  	   		int posicionCorcheteCerrado=linea.indexOf("}");
		  	   		String palabraDestino=linea.substring(posicionCorcheteAbierto+1,posicionCorcheteCerrado);
		  	   		if( existsWordInMap(palabrasDestino, palabraDestino)) {
		  	   			linea="10 print \"El destino "+palabraDestino+" ya existe.\" ";
		  	   			arrayListFormatTexts.add(linea);
		  	   			return arrayListFormatTexts;
		  	   		}else if(palabraDestino.length()<3){
		  	   			linea="10 print \"El destino "+palabraDestino+" tiene que tener al menos 3 letras.\" ";
		  	   			arrayListFormatTexts.add(linea);
		  	   			return arrayListFormatTexts;
		  	   		}else if(patterIsDigit.matcher(palabraDestino).find()){
		  	   			linea="10 print \"El destino "+palabraDestino+" no puede empezar por un digito.\" ";
		  	   			arrayListFormatTexts.add(linea);
		  	   			return arrayListFormatTexts;
		  	   		}else {
			  	   		//kitamos la palabra destino de la linea
			  	   		linea=matcher.replaceAll("");		   	
			  	   		palabrasDestino.put(palabraDestino,String.valueOf(numberLine));
		  	   		}
		  	   	}
		  	   	fomartText=String.valueOf(numberLine)+" "+linea;
		  	   	arrayList.set(i, fomartText);
        	}
  	   	 }

         for (Entry<String, String> entry : palabrasDestino.entrySet()) {
			 String palabra=entry.getKey();
			 String numberLine=entry.getValue();
        	 //System.out.println("palabra destino: "+palabra+" numberline: "+numberLine);
			 for (int i=0;i<arrayList.size();i++) {
				 String linea=arrayList.get(i);
				 if(linea.contains(palabra)) {
					 //System.out.println("encontrada en linea "+i+" palabra "+palabra+" linea: "+numberLine);
					 linea=linea.replace(palabra,numberLine);
					 //System.out.println(linea);
					 arrayList.set(i, linea);
				 }
			 }
		 }
         

         
         
         for(String linea:arrayList) {
        	 arrayListFormatTexts.add(linea);
         }

		 
		 
		 
		 
		 
		 

         return arrayListFormatTexts;
	}
	
	/*private void replaceWord(String wordToReplace, String replace, ArrayList<String> arrayList ) {
		System.out.println("a reemplazar: "+wordToReplace+" reemplazo: "+replace);
		 for (String linea:arrayList) {
			 
		
		 }
	}*/
	private boolean checkIsNumeric(String linea, int position) {
		if(Character.isDigit(linea.charAt(position))) return true;
		return false;
	}
	
	private boolean existsWordInMap(HashMap<String, String> palabrasDestino,String word) {
		boolean exists=false;
		 for (Entry<String, String> entry : palabrasDestino.entrySet()) {
			 String palabra=entry.getKey();
			 String numberLine=entry.getValue();
			 if(word.equalsIgnoreCase(palabra)) {
				 System.out.println("la palabra "+palabra+" ya existe en línea "+numberLine);
				 return true;
			 }
		 }
		
		return exists;
		
	}

}
