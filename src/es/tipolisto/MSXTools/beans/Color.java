package es.tipolisto.MSXTools.beans;

public class Color {
	private int id;
	private String name;
	private String hexadecimal; 
	private String msx;
	private int r;
	private int g;
	private int b;
	public Color(int id, String name, String hexadecimal, String msx, int r, int g, int b) {
		super();
		this.id = id;
		this.name = name;
		this.hexadecimal = hexadecimal;
		this.msx = msx;
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
	public String getHexadecimal() {
		return hexadecimal;
	}
	public void setHexadecimal(String hexadecimal) {
		this.hexadecimal = hexadecimal;
	}
	public String getMsx() {
		return msx;
	}
	public void setMsx(String msx) {
		this.msx = msx;
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

	public String toString() {
		return  r + "," + g + "," + b;
	}
	

}
