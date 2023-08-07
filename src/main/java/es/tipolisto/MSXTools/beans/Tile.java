package es.tipolisto.MSXTools.beans;

public class Tile {
	int numTile;
	//Nos sirve para identificar los bytes de definici�n
	char[][] definitions=new char[8][8];
	char[][] colors=new char[8][8];
	int colorFore;
	int colorBack;
	public Tile(int numTile) {
		this.numTile=numTile;
		//Incializamos las definiciones y los colores
		for (int y=0;y<definitions.length;y++) {
			for (int x=0; x<definitions[0].length;x++) {
				definitions[y][x]=0;
				colors[y][x]=0;
			}
		}
	}
	public char[][] getDefinitions() {
		return definitions;
	}
	public void setDefinitions(char[][] definitions) {
		this.definitions = definitions;
	}
	public char[][] getColors() {
		return colors;
	}
	public void setColors(char[][] colors) {
		this.colors = colors;
	}
	public int getColorFore() {
		return colorFore;
	}
	public void setColorFore(int colorFore) {
		this.colorFore = colorFore;
	}
	public int getColorBack() {
		return colorBack;
	}
	public void setColorBack(int colorBack) {
		this.colorBack = colorBack;
	}
	
	
	
	

}
