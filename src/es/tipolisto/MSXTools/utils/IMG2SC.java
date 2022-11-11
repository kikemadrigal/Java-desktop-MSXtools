package es.tipolisto.MSXTools.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JProgressBar;

import es.tipolisto.MSXTools.beans.RGB;
import es.tipolisto.MSXTools.gui.ProgressBarFrame;




public class IMG2SC{
	public IMG2SC() {}
	/*
	public boolean checkImageSize(File fileOrigin) {
		boolean isSizeOk=false;
		try {
			BufferedImage bufferedImage = ImageIO.read(fileOrigin);
			if (bufferedImage.getHeight()==212 && bufferedImage.getWidth()==256) {
				isSizeOk=true;
			}
			//System.out.println("Ancho "+bufferedImage.getHeight()+" alto "+bufferedImage.getWidth());
		}catch(IOException e) {
			e.printStackTrace();
		}
		return isSizeOk;
	}
	*/
	
	
	
	public BufferedImage associateColorsBMPWithPalette(BufferedImage bufferedImage, int similarDistance) {
		HashMap<MSXPalette, RGB> palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMS();
        BufferedImage bufferedImageDestiny=new BufferedImage(256, 212, BufferedImage.TYPE_INT_RGB);
        //Como en el archivo.bmp o .jps o .png solo tenemos los bytes BB GG RR
        //vamos analizando pixel a pixel si es alguno de los que tenemos en nuetsra paleta
		int colorSRGB=0;
        for (int y=0;y<bufferedImage.getHeight();y++) {
			for(int x=0;x<bufferedImage.getWidth();x++) {
				int pixelRGB=bufferedImage.getRGB(x, y);
				Color color=new Color(pixelRGB);
				int valRed=color.getRed();
				int valGreen=color.getGreen();
				int valBlue=color.getBlue();
				//Si el RGB obtenido es igual a uno de los RGB almacenados
				RGB rgb=new RGB(valRed,valGreen,valBlue);
				RGB rgbItem=new RGB(255,255,255);
				for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
					rgbItem=entry.getValue();
					int ordinal=entry.getKey().ordinal();
					if(similarTo(rgbItem, rgb, similarDistance)) {
						colorSRGB=(int) rgbItem.getRed()<< 16 | rgbItem.getGreen()<< 8 | rgbItem.getBlue();
					}
					bufferedImageDestiny.setRGB(x, y, colorSRGB);
				}
			}
		}
		return bufferedImageDestiny;
	}
	public BufferedImage associateColorsPNGWithPalette(BufferedImage bufferedImage, int similarDistance) {
		HashMap<MSXPalette, RGB> palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMS();
        BufferedImage bufferedImageDestiny=new BufferedImage(256, 212, BufferedImage.TYPE_INT_RGB);
        //Como en el archivo.bmp o .jps o .png solo tenemos los bytes BB GG RR
        //vamos analizando pixel a pixel si es alguno de los que tenemos en nuetsra paleta
		int colorSRGB=-1;
        for (int y=0;y<bufferedImage.getHeight();y++) {
			for(int x=0;x<bufferedImage.getWidth();x++) {
				int pixelRGB=bufferedImage.getRGB(x, y);
				Color color=new Color(pixelRGB);
				int valRed=color.getRed();
				int valGreen=color.getGreen();
				int valBlue=color.getBlue();
				//Si el RGB obtenido es igual a uno de los RGB almacenados
				RGB rgb=new RGB(valRed,valGreen,valBlue);
				RGB rgbItem=new RGB(255,255,255);
				if( (pixelRGB>>24) == 0x00 ) {
					bufferedImageDestiny.setRGB(x, y, -1);
				}else {
					for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
						rgbItem=entry.getValue();
						int ordinal=entry.getKey().ordinal();
						if(similarTo(rgbItem, rgb, similarDistance)) {
							colorSRGB=(int) rgbItem.getRed()<< 16 | rgbItem.getGreen()<< 8 | rgbItem.getBlue();
						}
						bufferedImageDestiny.setRGB(x, y, colorSRGB);
					}
				}
			}
		}
		return bufferedImageDestiny;
	}

	public StringBuilder associateColorsBMPWithPaletteReturnStringSC5(BufferedImage bufferedImage, int similarDistance) {
		StringBuilder sb=new StringBuilder();
		HashMap<MSXPalette, RGB> palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMS();
        BufferedImage bufferedImageDestiny=new BufferedImage(256, 212, BufferedImage.TYPE_INT_RGB);
        //Como en el archivo.bmp o .jps o .png solo tenemos los bytes BB GG RR
        //vamos analizando pixel a pixel si es alguno de los que tenemos en nuetsra paleta
		int colorSRGB;
        for (int y=0;y<bufferedImage.getHeight();y++) {
			for(int x=0;x<bufferedImage.getWidth();x++) {
				int pixelRGB=bufferedImage.getRGB(x, y);
				Color color=new Color(pixelRGB);
				int valRed=color.getRed();
				int valGreen=color.getGreen();
				int valBlue=color.getBlue();
				//Si el RGB obtenido es igual a uno de los RGB almacenados
				String hexadecimal="f";
				RGB rgb=new RGB(valRed,valGreen,valBlue);
				for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
					RGB rgbItem=entry.getValue();
					int ordinal=entry.getKey().ordinal();
					//Si hay alguno similar cambiamos el valor de result
					if(similarTo(rgbItem, rgb, similarDistance)) {
						colorSRGB=(int) rgbItem.getRed()<< 16 | rgbItem.getGreen()<< 8 | rgbItem.getBlue();
						hexadecimal=NumberManager.decimalAHexadecimal(ordinal);
					}
				}
				sb.append(hexadecimal);
			}
		}
		return sb;
	}
	public StringBuilder associateColorsPNGWithPaletteReturnStringSC5(BufferedImage bufferedImage, int similarDistance) {
		StringBuilder sb=new StringBuilder();
		HashMap<MSXPalette, RGB> palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMS();
        String result="";
        BufferedImage bufferedImageDestiny=new BufferedImage(256, 212, BufferedImage.TYPE_INT_RGB);
        //Como en el archivo.bmp o .jps o .png solo tenemos los bytes BB GG RR
        //vamos analizando pixel a pixel si es alguno de los que tenemos en nuetsra paleta
		int colorSRGB;
        for (int y=0;y<bufferedImage.getHeight();y++) {
			for(int x=0;x<bufferedImage.getWidth();x++) {
				int pixelRGB=bufferedImage.getRGB(x, y);
				Color color=new Color(pixelRGB);
				int valRed=color.getRed();
				int valGreen=color.getGreen();
				int valBlue=color.getBlue();
				//Si el RGB obtenido es igual a uno de los RGB almacenados
				String hexadecimal="f";
				RGB rgb=new RGB(valRed,valGreen,valBlue);
				if( (pixelRGB>>24) == 0x00 ) {
					hexadecimal="0";
				}else {
					for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
						RGB rgbItem=entry.getValue();
						int ordinal=entry.getKey().ordinal();
						//Si hay alguno similar cambiamos el valor de result
						if(similarTo(rgbItem, rgb, similarDistance)) {
							colorSRGB=(int) rgbItem.getRed()<< 16 | rgbItem.getGreen()<< 8 | rgbItem.getBlue();
							hexadecimal=NumberManager.decimalAHexadecimal(ordinal);
						}
					}
				}
				sb.append(hexadecimal);
			}
		}
		return sb;
	}


	public BufferedImage getBufferedImage(File imageFile) {
		HashMap<MSXPalette, RGB> palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMS2();
		//Creamos la via de comunicaci�n para poder leer la imagen
		BufferedImage bufferedImage=null;
		try {
			bufferedImage = ImageIO.read(imageFile);
			//Todas las imágenes tendrán un tamaño de 256*121px
			Image image = bufferedImage.getScaledInstance(256, 212, Image.SCALE_DEFAULT);
			bufferedImage=imageToBufferedImage(image);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bufferedImage;
	}
	
	public void createFilePNGImage(BufferedImage bufferedImageDestiny, File fileOrigin) {
		String fileName="out-"+fileOrigin.getName();
		File file=new File(fileName);
		try {
			ImageIO.write(bufferedImageDestiny, "PNG", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void createFileBMPImage(BufferedImage bufferedImageDestiny, File fileOrigin) {
		String fileNameBMP="out-"+fileOrigin.getName();
		File fileBMP=new File(fileNameBMP);
		try {
			ImageIO.write(bufferedImageDestiny, "BMP", fileBMP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void createFileWithString(StringBuilder result, String stringFileName) throws IOException {
		//System.out.println("tamaño: "+result.length());
		byte[] data = new byte[result.length()];
	    byte[] header = new byte[7];
	    header[0]=(byte)0xFE;
	    header[1]=(byte)0x00;
	    header[2]=(byte)0x00;
	    header[3]=(byte)0x00;
	    header[4]=(byte)0x6A;
	    header[5]=(byte)0x00;
	    header[6]=(byte)0x00;
	    
	    for (int i = 0; i < result.length(); i += 2) {
	        data[i / 2] = (byte) ((Character.digit(result.charAt(i), 16) << 4) + Character.digit(result.charAt(i+1), 16));   
	    }

	    //creamos el archivo
		String fileName = new StringTokenizer(stringFileName, ".").nextToken();
		fileName=fileName + ".sc5";
	    FileOutputStream fos;
		fos = new FileOutputStream(fileName);
		fos.write(header);
	    fos.write(data);
	    fos.close();
	}
	/*
	public boolean convertPNG2SC5(File fileOrigin) {
        boolean pixelNoExisteEnPaleta=false;
        boolean[] pixelesNoExistenEnPaleta=new boolean[16];
		try {
			//Obtenemos el hasmap que relacciona los colores conla paleta
			HashMap<MSXPalette, RGB> palettePhilips8255NMS=Palettes.getPalettePhilips8255NMS2();
			//Creamos la via de comunicaci�n para poder leer la imagen
			BufferedImage bufferedImage = ImageIO.read(fileOrigin);
			//Todas las imágenes tendrán un tamaño de 256*121px
			Image image = bufferedImage.getScaledInstance(256, 212, Image.SCALE_DEFAULT);
			bufferedImage=imageToBufferedImage(image);
			int alto=bufferedImage.getHeight();
			int ancho=bufferedImage.getWidth();
			//Mostramos la informaci�n por consola
			//System.out.println("Ancho: "+ancho+", alto "+alto);
	        String result="";
	        //vamos analizando pixel a pixel para obtener los colores y la trasparencia
			for (int y=0;y<alto;y++) {
				if(pixelNoExisteEnPaleta)break;
				for(int x=0;x<ancho;x++) {
					if(pixelNoExisteEnPaleta)break;
					int pixelRGB=bufferedImage.getRGB(x, y);
					Color color=new Color(pixelRGB);
					int valRed=color.getRed();
					int valGreen=color.getGreen();
					int valBlue=color.getBlue();
					boolean valTransparency=false;
					if( (pixelRGB>>24) == 0x00 ) {
					    valTransparency=true;
					}
					//Si el RGB obtenido es igual a uno de los RGB almacenados
					RGB rgb=new RGB(valRed,valGreen,valBlue);
					for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
						RGB rgbItem=entry.getValue();
						int ordinal=entry.getKey().ordinal();
						String hexadecimal=NumberManager.decimalAHexadecimal(ordinal);
						if(rgbItem.getRed()== rgb.getRed() && rgbItem.getGreen()==rgb.getGreen() && rgbItem.getBlue()==rgb.getBlue() ) {
							pixelesNoExistenEnPaleta[ordinal]=true;
							if(ordinal==15 && valTransparency) {
								result+="0";
								break;
							}
							result+=hexadecimal;
						}else {
							pixelesNoExistenEnPaleta[ordinal]=false;
						}
					}
					//Comprobamos si todas las cmprobaciones son false
					if(     pixelesNoExistenEnPaleta[0]==false && 
							pixelesNoExistenEnPaleta[1]==false && 
							pixelesNoExistenEnPaleta[2]==false && 
							pixelesNoExistenEnPaleta[3]==false && 
							pixelesNoExistenEnPaleta[4]==false && 
							pixelesNoExistenEnPaleta[5]==false && 
							pixelesNoExistenEnPaleta[6]==false && 
							pixelesNoExistenEnPaleta[7]==false && 
							pixelesNoExistenEnPaleta[8]==false && 
							pixelesNoExistenEnPaleta[9]==false && 
							pixelesNoExistenEnPaleta[10]==false && 
							pixelesNoExistenEnPaleta[11]==false && 
							pixelesNoExistenEnPaleta[12]==false && 
							pixelesNoExistenEnPaleta[13]==false && 
							pixelesNoExistenEnPaleta[14]==false && 
							pixelesNoExistenEnPaleta[15]==false)
						pixelNoExisteEnPaleta=true;
				}
			}
			if(!pixelNoExisteEnPaleta) {
				createOutPutFile(fileOrigin, result);
			}
		    //System.out.println(result);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Si devuelve true es que se ha encontrado un pixel que no está en la paleta
		return pixelNoExisteEnPaleta;
	}
		
	public BufferedImage convertPNG2BufferedWithPaletteColors(File fileOrigin,boolean createFileOutPut) {
		try {
			//Obtenemos el hasmap que relacciona los colores conla paleta
			HashMap<MSXPalette, RGB> palettePhilips8255NMS=Palettes.getPalettePhilips8255NMS2();
			//Creamos la via de comunicaci�n para poder leer la imagen
			BufferedImage bufferedImageOrigin = ImageIO.read(fileOrigin);
			//Todas las imágenes tendrán un tamaño de 256*121px
			Image imageOrigin = bufferedImageOrigin.getScaledInstance(256, 212, Image.SCALE_DEFAULT);
			BufferedImage bufferedImageDestiny = new BufferedImage(imageOrigin.getWidth(null),imageOrigin.getHeight(null),BufferedImage.TYPE_INT_RGB);
			bufferedImageOrigin=imageToBufferedImage(imageOrigin);
			//Mostramos la informaci�n por consola
			//System.out.println("Ancho: "+bufferedImageOrigin.getHeight()+", alto "+bufferedImageOrigin.getHeight());
	        String result="";
	        //Color[] pixelData=new Color[bufferedImageOrigin.getWidth()*bufferedImageOrigin.getHeight()]; 
	        int mediaPixel,colorSRGB;

	        //vamos analizando pixel a pixel para obtener los colores y la trasparencia
			for (int y=0;y<bufferedImageOrigin.getHeight();y++) {
				for(int x=0;x<bufferedImageOrigin.getWidth();x++) {
					int pixelRGB=bufferedImageOrigin.getRGB(x, y);
					Color color=new Color(pixelRGB);
					int valRed=color.getRed();
					int valGreen=color.getGreen();
					int valBlue=color.getBlue();
					boolean valTransparency=false;
					if( (pixelRGB>>24) == 0x00 ) {
					    valTransparency=true;
					}
					//Si el RGB obtenido es igual a uno de los RGB almacenados
					RGB rgb=new RGB(valRed,valGreen,valBlue);
					for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
						RGB rgbItem=entry.getValue();
						int ordinal=entry.getKey().ordinal();
						String hexadecimal="0";
						if(similarTo(rgbItem, rgb, 10000)) {
							colorSRGB=(int) rgbItem.getRed()<< 24 | rgbItem.getGreen()<< 16 | rgbItem.getBlue()<<8;
							bufferedImageDestiny.setRGB(x, y, colorSRGB);
							hexadecimal=NumberManager.decimalAHexadecimal(ordinal);
						}
						result+=hexadecimal;
					}
				}
			}
			if(createFileOutPut) {
				//System.out.println(result);
				//createOutPutFile(fileOrigin, result);
				String fileNamePNG="out-"+fileOrigin.getName();
				File filePNG=new File(fileNamePNG);
				ImageIO.write(bufferedImageDestiny, "PNG", filePNG);
				convertPNG2SC5(filePNG);
				filePNG.delete();
			}
			//BufferedImage newBufferedImage = new BufferedImage(bufferedImageOrigin.getWidth(), bufferedImageOrigin.getHeight(), BufferedImage.TYPE_INT_RGB);
			//newBufferedImage.getGraphics().drawImage(pixelData, 0, 0, null);
			return bufferedImageDestiny;
		    //System.out.println(result);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private  static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}

	private boolean similarTo(RGB pixel, RGB RGB, int similarDistance){
	    double distance = (pixel.getRed() - RGB.getRed())*(pixel.getRed() - RGB.getRed()) + (pixel.getGreen() - RGB.getGreen())*(pixel.getGreen() - RGB.getGreen()) + (pixel.getBlue() - RGB.getBlue())*(pixel.getBlue() - RGB.getBlue());
	    if(distance < similarDistance){
	        return true;
	    }else{
	        return false;
	    }
	}
	private BufferedImage imageToBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }
	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();
	    return bimage;
	}
	/*
	private void createOutPutFile(File fileOrigin, String result) {
		//Las imágenes sc5 son todas de 256x212 en MSX2
		int ancho=256;
		int alto=212;
		//le ponemos el nombre al archivo donde escribiremos
		final String filename = new StringTokenizer(fileOrigin.getName(), ".").nextToken();
        String fileDestiny=filename + ".sc5";
        //creamos el archivo
        try {
	        FileOutputStream fos = new FileOutputStream(fileDestiny);
			int len = result.length();
			//debería de dar 54272
			System.out.println("el tamaño del result es: "+len);
		    byte[] data = new byte[(ancho*alto)/2];
		    byte[] header = new byte[7];
		    header[0]=(byte)0xFE;
		    header[1]=(byte)0x00;
		    header[2]=(byte)0x00;
		    header[3]=(byte)0x06;
		    header[4]=(byte)0x6A;
		    header[5]=(byte)0x00;
		    header[6]=(byte)0x00;
		    for (int i = 0; i < len; i += 2) {
		        data[i / 2] = (byte) ((Character.digit(result.charAt(i), 16) << 4) + Character.digit(result.charAt(i+1), 16));
		    }
		    //byte[] decoded = Hex.decodeHex(result);
		    fos.write(header);
		    fos.write(data);
		    fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/




	
	/*private void createOutPutFileWithBuffer(File fileOrigin,BufferedImage bufferedImage) {
		try {
			final String filename = new StringTokenizer(fileOrigin.getName(), ".").nextToken();
	        String fileDestiny=filename + ".sc5";
	        FileOutputStream fos = new FileOutputStream(fileDestiny);
		    //byte[] data = new byte[(bufferedImage.getWidth()*bufferedImage.getHeight())/2];
	        byte[] header = new byte[7];
		    header[0]=(byte)0xFE;
		    header[1]=(byte)0x00;
		    header[2]=(byte)0x00;
		    header[3]=(byte)0x06;
		    header[4]=(byte)0x6A;
		    header[5]=(byte)0x00;
		    header[6]=(byte)0x00;
		    fos.write(header);
		    fos.write(bufferedImage.to);
		    fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	

}



