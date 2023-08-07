package es.tipolisto.MSXTools.beans;

public class Tile {
	//Nos sirve para identificar los bytes de definición
	boolean[][] rows=new boolean[8][8];
	int colorFore;
	int colorBack;
	public Tile() {
		for (int y=0;y<rows.length;y++) {
			for (int x=0; x<rows[0].length;x++) {
				rows[y][x]=false;
			}
		}
	}
	

}
