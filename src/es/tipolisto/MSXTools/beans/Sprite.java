package es.tipolisto.MSXTools.beans;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.JLabel;

public class Sprite implements Serializable {
	private int number;
	private int x;
	private int y;
	private String name;
	//type 8 sprite 8x8, type 16 sprites 16x16
	private byte type;
	//Seg�n el n�mero de colores nos dice que screen es, 1=screen 1, 2=screen 2,etc
	private byte numColors;
	private Pixel[][] pixels;
	private String dataDefinition;
	private String dataColors;
	private Color[] colorButtons0;
	private Color[] colorButtons1;
	
	private BufferedImage bufferedImage;
	public Sprite(int number, int x, int y, String name, byte type, byte numColors) {
		super();
		this.number = number;
		this.x = x;
		this.y = y;
		this.name = name;
		this.type=type;
		this.pixels=new Pixel[16][16];
		/*for(int file=0;file<pixels.length;file++) {
			for(int column=0;column<pixels[0].length;column++) {
				Pixel pixel=new Pixel((column*16)+120,(file*16)+120,(byte)2,new Color(239,252,254));
				pixels[x][y]=pixel;
			}
		}*/
		this.colorButtons0=new Color[16];
		this.colorButtons1=new Color[16];
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
	public Pixel[][] getPixels() {
		return pixels;
	}
	public void setPixels(Pixel[][] pixels) {
		this.pixels = pixels;
	}
	
	public String getDataDefinition() {
		return dataDefinition;
	}
	public void setDataDefinition(String dataDefinition) {
		this.dataDefinition = dataDefinition;
	}
	public String getDataColors() {
		return dataColors;
	}
	public void setDataColors(String dataColors) {
		this.dataColors = dataColors;
	}	
	public Color[] getColorButtons0() {
		return colorButtons0;
	}
	public void setColorButtons0(Color[] colorButtons0) {
		this.colorButtons0 = colorButtons0;
	}
	public Color[] getColorButtons1() {
		return colorButtons1;
	}
	public void setColorButtons1(Color[] colorButtons1) {
		this.colorButtons1 = colorButtons1;
	}
	
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	
	public byte getNumColors() {
		return numColors;
	}
	public void setNumColors(byte numColors) {
		this.numColors = numColors;
	}
	@Override
	public String toString() {
		return "Sprite [number=" + number + ", x=" + x + ", y=" + y + ", name=" + name + "]";
	}
	

}