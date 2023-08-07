package es.tipolisto.MSXTools.utils;


import java.awt.Color;
import java.util.HashMap;
import java.util.Map.Entry;

import es.tipolisto.MSXTools.beans.RGB;
//https://paulwratt.github.io/programmers-palettes/HW-MSX/HW-MSX-palettes.html
////https://github.com/theNestruo/pcxtools
public class PaletteManager {
 private static RGB[] rgbMSXPhilips8255NMS= {new RGB(0,0,0),new RGB(0,0,0),new RGB(36,218,36),new RGB(109,255,109),new RGB(36,36,255),new RGB(72,109,255),new RGB(182,36,36),new RGB(72,218,255),new RGB(255,36,36),new RGB(255,109,109),new RGB(218,218,36),new RGB(218,218,145),new RGB(36,145,36),new RGB(218,72,182),new RGB(182,182,182),new RGB(255,255,255)};
 public static RGB[] getRGB8255NMS() {
	 return rgbMSXPhilips8255NMS;
 }
 public static HashMap<MSXPalette, RGB> getPalettePhilips8255NMS(){
	 HashMap<MSXPalette,RGB> hasMapPalettePhilipsNMS8255=new HashMap<MSXPalette,RGB>();
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.TRANSPARENTE, new RGB(0,0,0));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.NEGRO, new RGB(0,0,0));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.VERDE_MEDIO, new RGB(36,218,36));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.VERDE_CLARO, new RGB(109,255,109));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AZUL_OSCURO, new RGB(36,36,255));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AZUL_MEDIO, new RGB(72,109,255));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.ROJO_OSCURO, new RGB(182,36,36));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AZUL_CLARO, new RGB(72,218,255));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.ROJO_MEDIO, new RGB(255,36,36));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.ROJO_CLARO, new RGB(255,109,109));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AMARILLO_OSCURO, new RGB(218,218,36));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AMARILLO_CLARO, new RGB(218,218,145));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.VERDE_OSCURO, new RGB(36,145,36));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.VIOLETA, new RGB(218,72,182));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.GRIS, new RGB(182,182,182));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.BLANCO, new RGB(255,255,255));	
	 return hasMapPalettePhilipsNMS8255;
 }

 public static HashMap<MSXPalette, RGB> getPalettePhilips8255NMS2(){
	 HashMap<MSXPalette,RGB> hasMapPalettePhilipsNMS8255=new HashMap<MSXPalette,RGB>();
	 //Si es transparente da 255 pero el alpha es true
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.TRANSPARENTE, new RGB(255,255,255));
	 //Si es negra da 0
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.NEGRO, new RGB(0,0,0));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.VERDE_MEDIO, new RGB(36,218,36));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.VERDE_CLARO, new RGB(109,255,109));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AZUL_OSCURO, new RGB(36,36,255));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AZUL_MEDIO, new RGB(72,109,255));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.ROJO_OSCURO, new RGB(182,36,36));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AZUL_CLARO, new RGB(72,218,255));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.ROJO_MEDIO, new RGB(255,36,36));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.ROJO_CLARO, new RGB(255,109,109));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AMARILLO_OSCURO, new RGB(218,218,36));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AMARILLO_CLARO, new RGB(218,218,145));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.VERDE_OSCURO, new RGB(36,145,36));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.VIOLETA, new RGB(218,72,182));
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.GRIS, new RGB(182,182,182));
	 //Si es blanco da 255 pero el alpha es false
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.BLANCO, new RGB(255,255,255));	
	 return hasMapPalettePhilipsNMS8255;
 }
 
 public static HashMap<MSXPalette, RGB> getPalettePhilips8255NMSForSprites(){
	 HashMap<MSXPalette,RGB> hasMapPalettePhilipsNMS8255=new HashMap<MSXPalette,RGB>();
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.TRANSPARENTE, new RGB(0,0,0));//0
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.NEGRO, new RGB(1,1,1));//1
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.VERDE_MEDIO, new RGB(36,218,36));//2
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.VERDE_CLARO, new RGB(109,255,109));//3
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AZUL_OSCURO, new RGB(36,36,255));//4
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AZUL_MEDIO, new RGB(72,109,255));//5
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.ROJO_OSCURO, new RGB(182,36,36));//6
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AZUL_CLARO, new RGB(72,218,255));//7
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.ROJO_MEDIO, new RGB(255,36,36));//8
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.ROJO_CLARO, new RGB(255,109,109));//9
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AMARILLO_OSCURO, new RGB(218,218,36));//10
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.AMARILLO_CLARO, new RGB(218,218,145));//11
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.VERDE_OSCURO, new RGB(36,145,36));//12
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.VIOLETA, new RGB(218,72,182));//13
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.GRIS, new RGB(182,182,182));//14
	 hasMapPalettePhilipsNMS8255.put(MSXPalette.BLANCO, new RGB(255,255,255));	//15
	 return hasMapPalettePhilipsNMS8255;
 }
 
 
 
	public static int getPositionColorOnPalette(Color color) {
		HashMap<MSXPalette, RGB> palettePhilips8255NMS=getPalettePhilips8255NMSForSprites();
		int number=0;
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			RGB rgbItem=entry.getValue();
			if(rgbItem.getRed()==color.getRed() && rgbItem.getBlue()==color.getBlue() && rgbItem.getGreen()==color.getGreen()) {
				number=entry.getKey().ordinal();
			}
		}
		return number;
	}
	public static String getNameColorOnPalette(Color color) {
		HashMap<MSXPalette, RGB> palettePhilips8255NMS=getPalettePhilips8255NMSForSprites();
		String name="Sin color";
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			RGB rgbItem=entry.getValue();
			if(rgbItem.getRed()==color.getRed() && rgbItem.getBlue()==color.getBlue() && rgbItem.getGreen()==color.getGreen()) {
				name=entry.getKey().name();
			}
		}
		return name;
	}
	
	public static Color getColorFromPallete(int number) {
		HashMap<MSXPalette, RGB> palettePhilips8255NMS=getPalettePhilips8255NMSForSprites();
		Color colorSelected=new Color(0,0,0);
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			if(entry.getKey().ordinal()==number) {
				colorSelected=new Color(entry.getValue().getRed(),entry.getValue().getGreen(),entry.getValue().getBlue());
			}
		}
		return colorSelected;	
	}
}


