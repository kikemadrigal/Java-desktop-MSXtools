package es.tipolisto.MSXTools.data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import es.tipolisto.MSXTools.beans.Color;
import es.tipolisto.MSXTools.beans.Palette;

public class SQLiteClient {
	private String dbName;
	private String user;
	private String password;
	private float version;
	private Connection connection;

	private PreparedStatement preparedStatement;
	//preparedStatement=connection.prepareStatement(SQL.createTablePalette);
	public SQLiteClient(float version) {
		this.version=version;
		String url="jdbc:sqlite:tools\\sqlite\\msxtools.db";
		try {
			connection=DriverManager.getConnection(url);
			
			if(connection!=null) {
				System.out.println("Se conectó exitosamente");
			}else {
				File fileDB=new File("tools\\\\sqlite\\\\msxtools.db?allowMultiQueries=true");
				System.out.println("Se creó el archivo tools/sqlite/MSXtools.db, vuelva a conectar");
			}
			if(version==1.0f) {
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
				
				statement.addBatch(SQL.insertPaletteMSX1);
				statement.addBatch(SQL.insertPaletteMSX2);
				statement.addBatch(SQL.insertPaletteCustom1);
				statement.addBatch(SQL.insertPaletteCustom2);
				statement.executeBatch();
				/*Statement statementCreateTableColor=connection.createStatement();
				statementCreateTableColor.executeQuery(SQL.createTableColor);
				Statement statementCreateTablePalette=connection.createStatement();
				statementCreateTablePalette.executeQuery(SQL.createTablePalette);
				statementCreateTablePalette.close();
				Statement statementInsertPaletteMSX1=connection.createStatement();
				statementInsertPaletteMSX1.executeQuery(SQL.insertPaletteMSX1);
				statementInsertPaletteMSX1.close();
				Statement statementInsertPaletteMSX2=connection.createStatement();
				statementInsertPaletteMSX2.executeQuery(SQL.insertPaletteMSX2);
				statementInsertPaletteMSX2.close();
				Statement statementInsertCustom1=connection.createStatement();
				statementInsertCustom1.executeQuery(SQL.insertPaletteCustom1);
				statementInsertCustom1.close();
				Statement statementInsertCustom2=connection.createStatement();
				statementInsertCustom2.executeQuery(SQL.insertPaletteCustom2);
				statementInsertCustom2.close();*/
			}
		} catch (SQLException e) {
			System.out.println("No se pudo conectar a la base de datos");
			System.out.println(e.getMessage().toString());
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
	
	public ArrayList<Palette> getAllPalettes() {
		ArrayList<Palette> arrayListPalette=new ArrayList<Palette>();
		try {
			Statement statementSelectAllFromPalettes=connection.createStatement();
			ResultSet resulset=statementSelectAllFromPalettes.executeQuery(SQL.selectAllPalete);

			while(resulset.next()) {
				Palette palette=new Palette(resulset.getInt("id"),resulset.getString("name"),resulset.getInt("idColor0"),resulset.getInt("idColor1"),resulset.getInt("idColor2"),resulset.getInt("idColor3"),resulset.getInt("idColor4"),resulset.getInt("idColor5"),resulset.getInt("idColor6"),resulset.getInt("idColor7"),resulset.getInt("idColor8"),resulset.getInt("idColor9"),resulset.getInt("idColor10"),resulset.getInt("idColor11"),resulset.getInt("idColor12"),resulset.getInt("idColor13"),resulset.getInt("idColor14"),resulset.getInt("idColor15"),resulset.getInt("isSelected"));	
				arrayListPalette.add(palette);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayListPalette;
	}
	public Color getColor(int idColor) {
		Color color=null;
		try {
			Statement statementSelectAllFromPalettes=connection.createStatement();
			//select * from colors where id='2';
			ResultSet resulset=statementSelectAllFromPalettes.executeQuery("select * from colors where id='"+idColor+"';");

			while(resulset.next()) {
				//(int id, String name, String hexadecimal, String msx, int r, int g, int b) 
				color=new Color(resulset.getInt("id"),resulset.getString("name"),resulset.getString("hexadecimal"),resulset.getString("msx"),resulset.getInt("r"),resulset.getInt("g"),resulset.getInt("b"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return color;
	}
	
	public void insertColor(Color color) {
		Statement statementInsertColor;
		try {
			statementInsertColor = connection.createStatement();
			ResultSet resulset=statementInsertColor.executeQuery("insert into colors values(null,"+color.getName()+","+color.getHexadecimal()+","+color.getMsx()+","+color.getR()+","+color.getG()+","+color.getB()+");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
