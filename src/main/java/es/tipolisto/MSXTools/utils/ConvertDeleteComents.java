package es.tipolisto.MSXTools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ConvertDeleteComents {
	private FileManager filemanager;
	private File fileOrigin;
	private File fileDestiny;
	
	private StringManager stringManager;

	public ConvertDeleteComents() {
		filemanager=new FileManager();
		stringManager=new StringManager();
	}
	
	public void convertNameDestinyLess(File fileOrigin) {
		ArrayList<String> arrayList=filemanager.readFile(fileOrigin);
		ArrayList<String>arrayListFormatTexts=deleteComents(arrayList);
		String newNameFileDestiny=fileOrigin.getName().substring(0,fileOrigin.getName().length()-4)+"-del.bas";
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

	public void converterWithFileDestiny(File fileOrigin, File fileDestiny) {
		//System.out.println("Vamos a borrar el archivo: "+fileOrigin.getAbsolutePath()+" en el archivo "+fileDestiny.getAbsolutePath());
		ArrayList<String> arrayList=filemanager.readFile(fileOrigin);
		System.out.println(" origen "+fileOrigin.getAbsolutePath()+" destino "+fileDestiny.getAbsolutePath());
		System.out.println(" leidos "+arrayList.size());
		ArrayList<String>arrayListFormatTexts=deleteComents(arrayList);
		filemanager.writeFile(fileDestiny, arrayListFormatTexts);
	}
	
	private ArrayList<String> deleteComents(ArrayList<String> arrayList) {
		 String fomartText="";
		 ArrayList<String> arrayListFormatTexts=new ArrayList<String>();
         for (String linea:arrayList) {
        	 //Si encuentra un 1' o 1 ' o 1  ' simplemente no escribe esa l�nea
        	 linea=stringManager.eliminarEspacios(linea);
        	 if (!stringManager.buscarComentario(linea)) {
		     	   	if(linea!="" && linea!=null && linea.length()>0) {
		     	   		fomartText=linea;
		     	   		arrayListFormatTexts.add(linea);
		     	   	}
        	 }
         }
         return arrayListFormatTexts;
	}
	
	

	




}
