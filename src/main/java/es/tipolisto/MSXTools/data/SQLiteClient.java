package es.tipolisto.MSXTools.data;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;

import es.tipolisto.MSXTools.beans.ColorPalette;
import es.tipolisto.MSXTools.beans.Palette;

public class SQLiteClient {
	private String dbName;
	private String user;
	private String password;
	private float version;
	private Connection connection;
	private float VERSION_SQLITE=2.0f;

	public SQLiteClient() {
		this.version=version;

		//File fileDB=new File("target\\tools\\sqlite\\msxtools.db");
		File fileDB=new File("MSXTools.db");
		if(fileDB.length()==0)VERSION_SQLITE=1.0f;
		/*URL resource = getClass().getClassLoader().getResource("MSXTools.db");
		URI path=null;
		File fileDB=null;
		try {
			path = resource.toURI();
			fileDB = new File(resource.toURI());			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//String url="jdbc:sqlite:MSXTools.db";
		String url="jdbc:sqlite:"+fileDB.getAbsolutePath();

		try {	
			Class.forName("org.sqlite.JDBC");
			connection=DriverManager.getConnection(url);

			if(VERSION_SQLITE==1.0f) {
				Statement statement=connection.createStatement();
				statement.addBatch(SQL.createTableColor);
				statement.addBatch(SQL.createTablePalette);

				statement.addBatch(SQL.insertColor0);
				statement.addBatch(SQL.insertColor1);
				statement.addBatch(SQL.insertColor2);
				statement.addBatch(SQL.insertColor3);
				statement.addBatch(SQL.insertColor4);
				statement.addBatch(SQL.insertColor5);
				statement.addBatch(SQL.insertColor6);
				statement.addBatch(SQL.insertColor7);
				statement.addBatch(SQL.insertColor8);
				statement.addBatch(SQL.insertColor9);
				statement.addBatch(SQL.insertColor10);
				statement.addBatch(SQL.insertColor11);
				statement.addBatch(SQL.insertColor12);
				statement.addBatch(SQL.insertColor13);
				statement.addBatch(SQL.insertColor14);
				statement.addBatch(SQL.insertColor15);
				
				statement.addBatch(SQL.insertPaletteMSX);
				statement.addBatch(SQL.insertPaletteCustom1);
				statement.addBatch(SQL.insertPaletteCustom2);
				statement.executeBatch();
			}
		} catch (  Exception ex) {
			//System.out.println("No se pudo conectar a la base de datos: "+fileDB.getAbsolutePath());
			System.out.println(ex.getMessage().toString());
			JOptionPane.showMessageDialog(null, "Not found database");
		}
	}
	
	public void SQLiteClose() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public float getVersion() {
		return this.version;
	}
	/********************************PALETTES*********************************/
	public ColorPalette getColorFromPalette(Palette palette, int index) {
		ColorPalette[] colorsPalettes=new ColorPalette[16];
		ColorPalette colorPalette=null;
		try {
			Statement statementSelectColorFromPaletteFromPositionPalette=connection.createStatement();
			ResultSet resulset0=statementSelectColorFromPaletteFromPositionPalette.executeQuery("select * from palettes where id='"+palette.getId()+"'");
			//System.out.println("Buscando "+resulset0.getFetchSize());
			while(resulset0.next()) {
				int id=resulset0.getInt("id");
				String name=resulset0.getString("name");
				for(int i=0;i<colorsPalettes.length;i++) {
					colorsPalettes[i]=getColor(resulset0.getInt("idColor"+i));
				}

				for(int i=0;i<colorsPalettes.length;i++) {
					if(i==index) {
						colorPalette=colorsPalettes[i];
					}
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return colorPalette;
	}
	
	public ArrayList<Palette> getAllPalettes() {
		ArrayList<Palette> arrayListPalette=new ArrayList<Palette>();
		try {
			Statement statementSelectAllFromPalettes=connection.createStatement();
			ResultSet resulset=statementSelectAllFromPalettes.executeQuery(SQL.selectAllPalete);

			while(resulset.next()) {
				int id=resulset.getInt("id");
				String name=resulset.getString("name");
				ColorPalette colorPalette0=getColor(resulset.getInt("idColor0"));
				ColorPalette colorPalette1=getColor(resulset.getInt("idColor1"));
				ColorPalette colorPalette2=getColor(resulset.getInt("idColor2"));
				ColorPalette colorPalette3=getColor(resulset.getInt("idColor3"));
				ColorPalette colorPalette4=getColor(resulset.getInt("idColor4"));
				ColorPalette colorPalette5=getColor(resulset.getInt("idColor5"));
				ColorPalette colorPalette6=getColor(resulset.getInt("idColor6"));
				ColorPalette colorPalette7=getColor(resulset.getInt("idColor7"));
				ColorPalette colorPalette8=getColor(resulset.getInt("idColor8"));
				ColorPalette colorPalette9=getColor(resulset.getInt("idColor9"));
				ColorPalette colorPalette10=getColor(resulset.getInt("idColor10"));
				ColorPalette colorPalette11=getColor(resulset.getInt("idColor11"));
				ColorPalette colorPalette12=getColor(resulset.getInt("idColor12"));
				ColorPalette colorPalette13=getColor(resulset.getInt("idColor13"));
				ColorPalette colorPalette14=getColor(resulset.getInt("idColor14"));
				ColorPalette colorPalette15=getColor(resulset.getInt("idColor15"));
				int isSelected=resulset.getInt("isSelected");
				Palette palette=new Palette(id,name,colorPalette0,colorPalette1,colorPalette2,colorPalette3,colorPalette4,colorPalette5,colorPalette6,colorPalette7,colorPalette8,colorPalette9,colorPalette10,colorPalette11,colorPalette12,colorPalette13,colorPalette14,colorPalette15,isSelected);	
				arrayListPalette.add(palette);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayListPalette;
	}
	public Palette getActivePalette(ArrayList<Palette> arrayListPalettes) {
		Palette paletteActive=null;
		arrayListPalettes=getAllPalettes();
		for(Palette palette:arrayListPalettes) {
			if(palette.getIsSelected()==1) {
				paletteActive=palette;
			}
		}
		return paletteActive;
	}
	public ColorPalette[] getColorsFromPalette(Palette palette) {
		ColorPalette[] colorsPalette=new ColorPalette[16];
		ColorPalette colorPalette=null;
		try {
			Statement statementSelectColorFromPalette=connection.createStatement();
			ResultSet resulset=statementSelectColorFromPalette.executeQuery("select * from palettes where id='"+palette.getId()+"'");
			while(resulset.next()) {
				for(int i=0;i<16;i++) {
					colorsPalette[i]=getColor(resulset.getInt("idColor"+i));	
				}			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return colorsPalette;
	}
	public int getLastIDInsertPalette() {
		int lastId=0;
		 try {
			 	Statement statementLastId = connection.createStatement();
				ResultSet resulset=statementLastId.executeQuery("SELECT max(id) AS max_id FROM palettes LIMIT 1;");
				//System.out.println(resulset.toString());
				while(resulset.next()) {
					lastId=resulset.getInt("max_id");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return lastId;
	}
	
	public void insertPalette(Palette palette) {
		Statement statementInsertPalette;
		try {
			statementInsertPalette = connection.createStatement();
			//(int id, String name, int color0, int color1, int color2, int color3, int color4, int color5, int color6,	int color7, int color8, int color9, int color10, int color11, int color12, int color13, int color14,int color15, int isSelected)
			//ResultSet resulset=statementInsertPalette.executeQuery("insert into palettes values(null,"+palette.getName()+","+palette.getColor1().getId()+","+palette.getColor2().getId()+","+palette.getColor3().getId()+","+palette.getColor4().getId()+","+palette.getColor5().getId()+","+palette.getColor6().getId()+","+palette.getColor7().getId()+","+palette.getColor8().getId()+","+palette.getColor9().getId()+","+palette.getColor10().getId()+","+palette.getColor11().getId()+","+palette.getColor12().getId()+","+palette.getColor13().getId()+","+palette.getColor14().getId()+","+palette.getColor15().getId()+palette.getIsSelected()+");");
			statementInsertPalette.executeUpdate("insert into palettes values(null,'"+palette.getName()+
					"','"+palette.getColor0().getId()+
					"','"+palette.getColor1().getId()+
					"','"+palette.getColor2().getId()+
					"','"+palette.getColor2().getId()+
					"','"+palette.getColor3().getId()+
					"','"+palette.getColor4().getId()+
					"','"+palette.getColor5().getId()+
					"','"+palette.getColor6().getId()+
					"','"+palette.getColor7().getId()+
					"','"+palette.getColor8().getId()+
					"','"+palette.getColor9().getId()+
					"','"+palette.getColor10().getId()+
					"','"+palette.getColor11().getId()+
					"','"+palette.getColor12().getId()+
					"','"+palette.getColor13().getId()+
					"','"+palette.getColor14().getId()+
					"','"+palette.getColor15().getId()+
					"');");
				System.out.println("Creada paleta nueva");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Hubo un problema "+e.getMessage());
			
		}
	}
	public void updateActivePalette(ArrayList<Palette> arrayListPalettes,int newPaletteActive, int oldPaletteActive) {
		for(int i=0;i<arrayListPalettes.size();i++) {
			Palette palette=arrayListPalettes.get(i);
			try {
				Statement statementUpdatePalette=connection.createStatement();
				if(i==newPaletteActive) {
					System.out.println("Activada: "+newPaletteActive);
					statementUpdatePalette.executeQuery("update palettes set isSelected='1' where id='"+palette.getId()+"';");
				}
				if(i==oldPaletteActive) {
					System.out.println("Desactivada: "+oldPaletteActive);
					statementUpdatePalette.executeQuery("update palettes set isSelected='0' where id='"+palette.getId()+"';");
				}
					
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/****************************END PALETTES*********************************/
	/********************************COLORS*********************************/
	public ColorPalette getColor(int idColor) {
		ColorPalette colorPalette=null;
		try {
			Statement statementSelectAllFromPalettes=connection.createStatement();
			//select * from colors where id='2';
			ResultSet resulset=statementSelectAllFromPalettes.executeQuery("select * from colors where id='"+idColor+"';");

			while(resulset.next()) {
				//(int id, String name, String hexadecimal, String msx, int r, int g, int b) 
				colorPalette=new ColorPalette(resulset.getInt("id"),resulset.getString("name"),resulset.getInt("r"),resulset.getInt("g"),resulset.getInt("b"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return colorPalette;
	}
	
	public void insertColor(ColorPalette color) {
		Statement statementInsertColor;
		try {
			statementInsertColor = connection.createStatement();
			statementInsertColor.executeUpdate("insert into colors values(null,'"+color.getName()+"',"+color.getR()+","+color.getG()+","+color.getB()+");");
			//System.out.println("resulset "+resulset.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getLastIDInsertColor() {
		int lastId=0;
		 try {
			 	Statement statementLastId = connection.createStatement();
				ResultSet resulset=statementLastId.executeQuery("SELECT max(id) AS max_id FROM colors LIMIT 1;");
				//System.out.println(resulset.toString());
				while(resulset.next()) {
					lastId=resulset.getInt("max_id");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return lastId;
	}
	
	public void updateColor(ColorPalette colorPalette) {
		
			try {
				Statement statementUpdatePalette=connection.createStatement();
				//System.out.println("Actualizado color : "+colorPalette.getId()+" r "+colorPalette.getR()+" g "+colorPalette.getG()+" b "+colorPalette.getB());
				statementUpdatePalette.executeUpdate("update colors set r="+colorPalette.getR()+", g="+colorPalette.getG()+", b="+colorPalette.getB()+" where id="+colorPalette.getId()+";");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
