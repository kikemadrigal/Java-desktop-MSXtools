package es.tipolisto.MSXTools.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map.Entry;

import es.tipolisto.MSXTools.beans.RGB;
import es.tipolisto.MSXTools.utils.MSXPalette;
import es.tipolisto.MSXTools.utils.PaletteManager;

public class ImageManager {
	public ImageManager() {}
	
	public static BufferedImage imageToBufferedImage(Image img)
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

	
	public static String getNameColor(Color color) {
		String colorSelected="Notfound";
		HashMap<MSXPalette, RGB> palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMS();
		RGB rgbItem=new RGB(255,255,255);
		Color deleteColor=new Color(239,252,254);
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			rgbItem=entry.getValue();
			int valRed=rgbItem.getRed();
			int valGreen=rgbItem.getGreen();
			int valBlue=rgbItem.getBlue();
			int ordinal=entry.getKey().ordinal();
			if(valRed==deleteColor.getRed()&&valGreen==deleteColor.getGreen() && valBlue==deleteColor.getBlue()) {
				colorSelected="Empty";
			}else if(valRed==color.getRed() && valGreen==color.getGreen() && valBlue==color.getBlue()) {
				System.out.println();
				colorSelected=entry.getKey().name();
			}
		}
		return colorSelected;
	}
	
	
	public static int getPositionColorOnPalette(Color color) {
		HashMap<MSXPalette, RGB> palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMSForSprites();
		int number=0;
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			RGB rgbItem=entry.getValue();
			if(rgbItem.getRed()==color.getRed() && rgbItem.getBlue()==color.getBlue() && rgbItem.getGreen()==color.getGreen()) {
				number=entry.getKey().ordinal();
			}
		}
		return number;
	}

}