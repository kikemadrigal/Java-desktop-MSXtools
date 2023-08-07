package es.tipolisto.MSXTools.data;



public class SQL {
	/***************************COLORS********************************************************************/
	//create table colors (id integer primary key autoincrement,name varchar(100),hexadecimal VARCHAR(100),msx VARCHAR(100),r INT(3),g INT(3),b INT(3));
	public static String createTableColor="create table colors ("+
									"id integer primary key autoincrement,"+
									"name varchar(100),"+
									"r INT(3),"+
									"g INT(3),"+
									"b INT(3)"+
								");";
	public static String deleteTableColor="drop table colors;";
	public static String insertColor0="insert into colors values(0,'TRANSPARENTE',0,0,0)";
	public static String insertColor1="insert into colors values(null,'NEGRO',0,0,0)";
	public static String insertColor2="insert into colors values(null,'VERDE_MEDIO',36,218,36)";
	public static String insertColor3="insert into colors values(null,'VERDE_CLARO',109,255,109)";
	public static String insertColor4="insert into colors values(null,'AZUL_OSCURO',36,36,255)";
	public static String insertColor5="insert into colors values(null,'AZUL_MEDIO',72,109,255)";
	public static String insertColor6="insert into colors values(null,'ROJO_OSCURO',72,109,255)";
	public static String insertColor7="insert into colors values(null,'AZUL_CLARO',72,218,255)";
	public static String insertColor8="insert into colors values(null,'ROJO_MEDIO',255,36,36)";
	public static String insertColor9="insert into colors values(null,'ROJO_CLARO',255,109,109)";
	public static String insertColor10="insert into colors values(null,'AMARILLO_OSCURO',218,218,36)";
	public static String insertColor11="insert into colors values(null,'AMARILLO_CLARO',218,218,145)";
	public static String insertColor12="insert into colors values(null,'VERDE_OSCURO',36,145,36)";
	public static String insertColor13="insert into colors values(null,'VIOLETA',218,72,182)";
	public static String insertColor14="insert into colors values(null,'GRIS',182,182,182)";
	public static String insertColor15="insert into colors values(null,'BLANCO',255,255,255)";
	
	/************************END COLORS********************************************************************/

	
	/***************************PALETTES********************************************************************/
	//create table palettes (id integer primary key autoincrement,name varchar(100),idColor0 intenger,idColor1 intenger,idColor2 intenger,idColor3 intenger,idColor4 intenger,idColor5 intenger,idColor6 intenger,idColor7 intenger,idColor8 intenger,idColor9 intenger,idColor10 intenger,idColor11 intenger,idColor12 intenger,idColor13 intenger,idColor14 intenger,idColor15 intenger,isSelected int(3));
	public static String createTablePalette="create table palettes ("+
			"id integer primary key autoincrement,"+
			"name varchar(100),"+
			"idColor0 intenger,"+
			"idColor1 intenger,"+
			"idColor2 intenger,"+
			"idColor3 intenger,"+
			"idColor4 intenger,"+
			"idColor5 intenger,"+
			"idColor6 intenger,"+
			"idColor7 intenger,"+
			"idColor8 intenger,"+
			"idColor9 intenger,"+
			"idColor10 intenger,"+
			"idColor11 intenger,"+
			"idColor12 intenger,"+
			"idColor13 intenger,"+
			"idColor14 intenger,"+
			"idColor15 intenger,"+
			"isSelected int(3));";
	static String deleteTablePalette="drop table palettes;";
	public static String insertPaletteMSX="insert into palettes values(0, 'MSX', 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1 );";
	public static String insertPaletteCustom1="insert into palettes values(null, 'Custom1',0,1,9,2,3,4,5,6,7,11,9,10,11,10,8,14,0 );";
	public static String insertPaletteCustom2="insert into palettes values(null, 'Custom2', 0,1,1,2,5,4,14,6,7,4,9,15,11,2,13,3,0 );";
	

	public static String selectAllPalete="select * from palettes;";
	//static String selectAllPalete="select p.name, c.name, c.hexadecimal, c.msx, c.r, c.g, c.b from palettes p left join colors c;";
	/***************************END PALETTES********************************************************************/


}
