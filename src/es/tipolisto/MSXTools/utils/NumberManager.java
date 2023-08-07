package es.tipolisto.MSXTools.utils;

public class NumberManager {
	
	public static String decimalAHexadecimal(int decimal) {
	    String hexadecimal = "";
	    String caracteresHexadecimales = "0123456789abcdef";
	    
	    while (decimal > 0) {
	        int residuo = decimal % 16;
	        hexadecimal = caracteresHexadecimales.charAt(residuo) + hexadecimal; // Agregar a la izquierda
	        decimal /= 16;
	    }
	    return hexadecimal;
	}
	public static String decimalAHexadecimalWithCero(int decimal) {
	    String hexadecimal = "";
	    String caracteresHexadecimales = "0123456789abcdef";
	    if(decimal==0)hexadecimal="0";
	    while (decimal > 0) {
	        int residuo = decimal % 16;
	        hexadecimal = caracteresHexadecimales.charAt(residuo) + hexadecimal; // Agregar a la izquierda
	        decimal /= 16;
	    }
	    return hexadecimal;
	}
	
	public static int hexadecimalToDecimal(String hexadecimal) {
		return Integer.parseInt(hexadecimal, 16);
	}
	
	private static String convertToBinary(String cadena) {
		//Si es 0 e el color de frente, 1 color de fondo y 2 sin color
		int contador0=0;
		int contador1=0;

		for(int i=0; i<cadena.length();i++) {
			//int posicionUnoComa=cadena.indexOf("1\'");
			if(cadena.indexOf("0")!=-1) {
				contador0++;
			}else if(cadena.indexOf("1")!=-1) {
				contador1++;
			}
		}
		
		return "";
	}
	
	public static int getDecimal(int binary){  
	    int decimal = 0;  
	    int n = 0;  
	    while(true){  
	      if(binary == 0){  
	        break;  
	      } else {  
	          int temp = binary%10;  
	          decimal += temp*Math.pow(2, n);  
	          binary = binary/10;  
	          n++;  
	       }  
	    }  
	    return decimal;  
	}  

}
