package es.tipolisto.MSXTools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ConvertCSVTSXToBas { 
	private File originFile;
	private FileManager fileManager;


	public ConvertCSVTSXToBas() {
		this.originFile=originFile;
		this.fileManager=new FileManager();
	}
	

	public void covertToBas(File originFile, int lineCounter) {
    	ArrayList<String> arrayListTexts=fileManager.readFile(originFile);
    	System.out.println(" obtenidos "+arrayListTexts.size());
    	arrayListTexts=formatToBasic(arrayListTexts, lineCounter);
    	System.out.println(" formateados "+arrayListTexts.size());   	
    	String newFileName=originFile.getAbsolutePath();
    	newFileName=newFileName.substring(0,newFileName.length()-4)+"-tobas.txt";
    	File newFile=new File(newFileName);
    	fileManager.writeFile(newFile, arrayListTexts);
	}
	
	/**
	 * 
	 * @param arrayListStrings
	 * @return
	 */
	public ArrayList<String> formatToBasic(ArrayList<String> arrayListStrings,int counterLine) {
		ArrayList<String> formattedTexts=new ArrayList<String>();
		for(String cadena:arrayListStrings ) {
			String formattedText="";
			//No queremos los dígitos -1, en su lugar ponemos 0
			int posicionMenosUno=cadena.indexOf("-1");
			if(posicionMenosUno!=-1) {
				counterLine++;
				cadena=cadena.replace("-1", "0");
				formattedText=counterLine+" data "+cadena;
	   		}else {
	   			if(cadena!="" && cadena!=null && cadena.length()>0) {
	   				counterLine++;
	   				formattedText=counterLine+" data "+formattedText;
	   			}
	   		}
	   			formattedTexts.add(formattedText);
		}
		 
		return formattedTexts;
	}



}
