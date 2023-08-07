package utils;

import java.awt.Color;
//https://paulwratt.github.io/programmers-palettes/HW-MSX/HW-MSX-palettes.html
public class MSXColor {
	public static Color getColor(int number) {
		Color activeColor;
		switch(number) {
			case 0:
				activeColor=new Color(0,0,0);
				break;
			case 1:
				activeColor=new Color(1,1,1);
				break;
			case 2:
				activeColor=new Color(62,184,73);
				break;
			case 3:
				activeColor=new Color(116,208,125);
				break;
			case 4:
				activeColor=new Color(89,85,224);
				break;
			case 5:
				activeColor=new Color(128,118,241);
				break;
			case 6:
				activeColor=new Color(185,94,81);
				break;
			case 7:
				activeColor=new Color(101,219,239);
				break;
			case 8:
				activeColor=new Color(219,101,89);
				break;
			case 9:
				activeColor=new Color(255,137,125);
				break;
			case 10:
				activeColor=new Color(204,195,94);
				break;
			case 11:
				activeColor=new Color(222,208,135);
				break;
			case 12:
				activeColor=new Color(58,162,65);
				break;
			case 13:
				activeColor=new Color(183,102,181);
				break;
			case 14:
				activeColor=new Color(204,204,204);
				break;
			default:
			activeColor=new Color(255,255,255);
		}
		return activeColor;
	}

}
