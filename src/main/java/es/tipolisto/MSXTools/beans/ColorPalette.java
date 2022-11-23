package es.tipolisto.MSXTools.beans;

public class ColorPalette {
	private int id;
	private String name;
	private int r;
	private int g;
	private int b;
	public ColorPalette(int id, String name, int r, int g, int b) {
		super();
		this.id = id;
		this.name = name;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	@Override
	public String toString() {
		return r + ", " + g + ", " + b ;
	}
	

}
