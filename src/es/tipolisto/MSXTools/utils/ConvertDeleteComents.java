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
		System.out.println("Vamos a convertir el archivo: "+fileOrigin.getAbsolutePath());
		ArrayList<String> arrayList=filemanager.readFile(fileOrigin);
		System.out.println("El tamaño del array con el texto sin formatear es : "+arrayList.size());
		ArrayList<String>arrayListFormatTexts=deleteComents(arrayList);
		System.out.println("El tamaño del array con el texto formateado es : "+arrayListFormatTexts.size());
		/*
		 String filename = new StringTokenizer(fileOrigin.getName(), ".").nextToken();
		stringFileDestiny=filename + "-del.bas";
		 */
		String newNameFileDestiny=fileOrigin.getName().substring(0,fileOrigin.getName().length()-4)+"-del.bas";
		//File fileDestiny=new File(fileOrigin.getParent()+"\\"+newNameFileDestiny);
		File fileDestiny=new File(newNameFileDestiny);
		filemanager.writeFile(fileDestiny, arrayListFormatTexts);
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
        	 //Si encuentra un 1' o 1 ' o 1  ' simplemente no escribe esa línea
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
