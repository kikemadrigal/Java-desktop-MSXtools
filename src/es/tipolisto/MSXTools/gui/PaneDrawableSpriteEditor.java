package es.tipolisto.MSXTools.gui;



import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;


import javax.swing.JButton;
import javax.swing.JTextArea;
import es.tipolisto.MSXTools.beans.Pixel;
import es.tipolisto.MSXTools.menus.MainMenuSpriteEditor;
import es.tipolisto.MSXTools.utils.NumberManager;

import utils.ImageManager;





public class PaneDrawableSpriteEditor extends Canvas implements MouseListener, MouseMotionListener{
	private int borderX;
	private int borderY;
	private int maxWidth;
	private int maxHeight; 
	private int sizeTile;
	private int pixelX;
	private int pixelY;
	private int pixelOldX;
	private int pixelOldY;
	private int horizontalTiles;
	private int verticalTiles;
	private boolean autoSaved;
	private String fileAutoSaved;
	private MainMenuSpriteEditor menuBar;
	//variables necesarias para detectar el color de una fila
	private JButton[] jButtons0;
	private JButton[] jButtons1;
	private JTextArea textAreaColor;
	private JTextArea textAreaDefinition;
	private Pixel[][] pixels;
	//Variables para manejar el evento grag and drop
	private boolean clickedButton1;
	private boolean clickedButton3;
	private Color deleteColor;
	private boolean selectedClear;
	private boolean paintBot;

	
	public PaneDrawableSpriteEditor(int horizontalTiles, int verticalTiles) {
		borderX=120;
		borderY=120;
		sizeTile=20;
		maxWidth=(sizeTile*horizontalTiles)+borderX;
		maxHeight=(sizeTile*verticalTiles)+borderY;
		setBounds(borderX, borderY, maxWidth-borderX+1, maxHeight-borderY+1);
		pixelX=borderX;
		pixelY=borderY;
		pixelOldX=0;
		pixelOldY=0;
		this.horizontalTiles=horizontalTiles;
		this.verticalTiles=verticalTiles;
		this.jButtons0=jButtons0;
		this.jButtons1=jButtons1;
		//El canvas tendrá un array de pixeles independiente
		pixels=new Pixel[16][16];
		deleteColor=new Color(239,252,254);
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=new Pixel((x*sizeTile),(y*sizeTile),(byte)2,deleteColor);
				pixels[x][y]=pixel;
			}
		}
		clickedButton1=false;
		clickedButton3=false;
		selectedClear=false;
		paintBot=false;
		this.requestFocusInWindow();
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	
	public JButton[] getjButtons0() {
		return jButtons0;
	}


	public void setjButtons0(JButton[] jButtons0) {
		this.jButtons0 = jButtons0;
	}


	public JButton[] getjButtons1() {
		return jButtons1;
	}


	public void setjButtons1(JButton[] jButtons1) {
		this.jButtons1 = jButtons1;
	}



	public void drawLinesAndExes(Graphics g) {
		//Graphics g=this.getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.PINK);
        g2.setStroke(new BasicStroke(3));
        g2.draw(new Line2D.Float(-10,(8*sizeTile),maxWidth, maxHeight-(14*sizeTile)));
        g2.draw(new Line2D.Float((8*sizeTile),-10,maxWidth-(14*sizeTile), maxHeight+10));
        g2.setStroke(new BasicStroke(1));
		g.setColor(Color.BLACK);
		//Dibujamos las lineas horizontales
		for (int i=0;i<=sizeTile*horizontalTiles+1;i+=sizeTile) {
			g.drawLine(0, i, sizeTile*horizontalTiles, i);
			//System.out.println("borderX: "+borderX+" i+1: "+y+" Total: "+sizeTile*horizontalTiles);
		}
		//Dubjamos las líneas verticales	
		for (int i=0;i<=sizeTile*verticalTiles+1;i+=sizeTile) {
			g.drawLine(i, 0, i, sizeTile*verticalTiles);
		}
	
		//Seleccionamos el area donde vamos a pintar
		paintFrameAtPoint(this.getGraphics(),pixelX,pixelY);
	}
	
	//Al pulsar enter pintaremos un cuadrado de un color
	public void paintPixelAtPoint(int x, int y, byte colorBackOrFront){
		Graphics g=this.getGraphics();

		//Para obtener el botón 
		int row=y/sizeTile;
		Color colorJButton=new Color(0,0,0);
		if (colorBackOrFront==0) {
			colorJButton=jButtons0[row].getBackground();
		}else if(colorBackOrFront==1) {
			colorJButton=jButtons1[row].getBackground();
		}else {
			colorJButton=deleteColor;
		}
		g.setColor(colorJButton);
		g.fillRect(x+1, y+1, sizeTile-1,sizeTile-1);
		//Escribimos en el array de los pixeles la información que necesitamos para poder trabajar depués con él
		//int positionOnArrayColumn=(x-borderX)/sizeTile;
		//int positionOnArrayFile=(y-borderY)/sizeTile;
		int positionOnArrayColumn=(x)/sizeTile;
		int positionOnArrayFile=(y)/sizeTile;

		pixels[positionOnArrayColumn][positionOnArrayFile].setPositionX(x);
		pixels[positionOnArrayColumn][positionOnArrayFile].setPositionY(y);
		pixels[positionOnArrayColumn][positionOnArrayFile].setForOrBrackground(colorBackOrFront);
		pixels[positionOnArrayColumn][positionOnArrayFile].setColor(colorJButton);
		drawLinesAndExes(g);
		g.dispose();
		//Si está activa el autoguardado lo guardamos
		if (autoSaved) menuBar.saveFile(false);
	}
	
	//Cuando pulsamos los cursores mostramos un recuadro indicando el lugar donde vamos a 
	// pintar si pulsamos enter
	public void paintFrameAtPoint(Graphics g, int x, int y){
		//System.out.println("Vamos a pintar un marco pixel en "+x+" "+y);
		g.setColor(Color.BLACK);
		//g.fillRect(x, y, sizeTile,sizeTile);
		g.drawRect(x, y, sizeTile,sizeTile);
	}
	public void deletePixelAtPoint(Graphics g, int x, int y){
		g.setColor(Color.WHITE);
		g.fillRect(x, y, sizeTile,sizeTile);
	}
	public void deleteFrameAtPoint(Graphics g, int x, int y){
		g.setColor(null);
		//g.drawRect(x, y, sizeTile,sizeTile);
		g.fillRect(x, y, sizeTile,sizeTile);
	}
	public void deleteAll(){
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				paintPixelAtPoint(pixels[x][y].getPositionX(), pixels[x][y].getPositionY(), (byte)2);
			}
		}
	}
	public void paintPotPixels(int pointX, int pointY){
		int row=pointY/sizeTile;
		int column=pointX/sizeTile;
		Color colorJButton=new Color(0,0,0);
		colorJButton=jButtons0[row].getBackground();
		Pixel[][] pixels=getPixels();
		Pixel[][] pixelsHelp=new Pixel[16][16];
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];	
				//Cogemos su color
				if(y==row) {
					//System.out.println("Estas sobre el color "+PaletteManager.getNameColorOnPalette(color));
					pixelsHelp[x][y]=new Pixel(pixel.getPositionX(),pixel.getPositionY(), (byte)0, colorJButton);
				}else {
					pixelsHelp[x][y]=new Pixel(pixel.getPositionX(),pixel.getPositionY(), pixel.getForOrBrackground(), pixel.getColor());
				}
			}
		}
		deleteAll();
		fillPixellOnCanvas(pixelsHelp);
	}
	
	public void fillPixellOnCanvas(Pixel[][] pixelsHelp) {
		if(pixelsHelp==null) {
			//System.out.println("pixels null");
		}else {
			for(int y=0;y<pixelsHelp.length;y++) {
				for(int x=0;x<pixelsHelp[0].length;x++) {
					Pixel pixel=pixelsHelp[x][y];
					if(pixel!=null) {
						paintPixelAtPoint(pixel.getPositionX(),pixel.getPositionY(),pixel.getForOrBrackground());
						Color color=pixel.getColor();
						//System.out.print("["+color.getRed()+","+color.getGreen()+","+color.getBlue()+"]");
					}
					else {
						//System.out.print("nu,");
					}
				}
			}
		}

		
	}
	
	
	
	/**************Getters and setters************************/
	public int getBorderX() {
		return borderX;
	}
	public void setBorderX(int borderX) {
		this.borderX = borderX;
	}

	public int getBorderY() {
		return borderY;
	}
	public void setBorderY(int borderY) {
		this.borderY = borderY;
	}
	public int getMaxWidth() {
		return maxWidth;
	}
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}
	public int getMaxHeight() {
		return maxHeight;
	}
	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}
	public Pixel[][] getPixels() {
		return pixels;
	}
	public void setPixels(Pixel[][] pixels) {
		this.pixels = pixels;
	}

	public int getSizeTile() {
		return sizeTile;
	}

	public void setSizeTile(int sizeTile) {
		this.sizeTile = sizeTile;
	}
	public boolean isSelectedClear() {
		return selectedClear;
	}
	public void setSelectedClear(boolean selectedClear) {
		this.selectedClear = selectedClear;
	}
	public void setTextAreaColor(JTextArea textAreaColor) {
		this.textAreaColor = textAreaColor;
	}

	public void setTextAreaDefinition(JTextArea textAreaDefinition) {
		this.textAreaDefinition = textAreaDefinition;
	}
	public boolean isPaintBot() {
		return paintBot;
	}
	public void setPaintBot(boolean paintBot) {
		this.paintBot = paintBot;
	}
	public void setAutoSaved(boolean autoSaved) {
		this.autoSaved=autoSaved;	
	}
	public boolean getAutoSaved() {
		return autoSaved;	
	}
	public void setMainMenuSpriteEditor(MainMenuSpriteEditor menuBar) {
		this.menuBar=menuBar;
	}
	public void setFileAutoSaved(String fileAutoSaved) {
		this.fileAutoSaved=fileAutoSaved;
	}
	public String getFileAutoSaved() {
		return fileAutoSaved;
	}
	/*************End Getters and setters************************/



	public void fillTextAreaDefinitionAndColot() {
		String[][] dataDefinition=getDataDefinition();
		String stringDataDenition="";
		for (int x=0;x<4;x++) {
			for (int i=0;i<dataDefinition[0].length;i++) {
				if(i==dataDefinition[x].length-1)
					stringDataDenition+=dataDefinition[x][i];
				else
					stringDataDenition+=dataDefinition[x][i]+",";
			}
			stringDataDenition+="\n";
		}
		textAreaDefinition.setText(stringDataDenition);
		//0 convertir a decimal, 1 convertir a hexadecimal
		String[] definitionColors=getDataColors((byte)0);
		String definitionColor="";
		for(int i=0;i<definitionColors.length;i++) {
			if(i==definitionColors.length-1)
				definitionColor+=definitionColors[i];
			else
				definitionColor+=definitionColors[i]+",";
		}
		textAreaColor.setText(definitionColor);
	}
	
	/**
	 * Devuelve un array de 16 filas con los colores de los pixeles
	 * Nos  ayudamos de la clase StringBuilder
	 */
	private String[] getDataColors(byte mode) {
		String[] arrayDefinitionColors=new String[16];
		//El caracter de la izquierda es el color de fondo, el de la dereecha es el de frente
		StringBuilder resultColors = new StringBuilder("00");
		Pixel[][] pixels;
		pixels=getPixels();
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];
				Color color=pixel.getColor();
				int positionColorOnPalette=ImageManager.getPositionColorOnPalette(color);
				String stringPositionColorOnPalette=NumberManager.decimalAHexadecimal(positionColorOnPalette);
				char[] charArray=stringPositionColorOnPalette.toCharArray();
				char charPositionColorOnPalette='0';
				if(charArray.length>0)
					charPositionColorOnPalette=charArray[0];
				byte foreOrBackGround=pixel.getForOrBrackground();
				
				//i es el color de frente (0) lo ponemos en la derecha de los 2 caracteres
				if(foreOrBackGround==0) {
					resultColors.setCharAt(1, charPositionColorOnPalette);
				//Si es el color de fondo (1) lo ponemos en la izquierda
				}else if(foreOrBackGround==1) {
					resultColors.setCharAt(0, charPositionColorOnPalette);
				}else {
					resultColors.setCharAt(0, '0');
				}
				if(mode==0) {
					int result=NumberManager.hexadecimalToDecimal(resultColors.toString());
					arrayDefinitionColors[y]=String.valueOf(result);
				}else if(mode==1) {
					arrayDefinitionColors[y]=resultColors.toString();
				}

			}

		}
		return arrayDefinitionColors;
	}
	
	private String[][] getDataDefinition() {
		StringBuffer cadena=new StringBuffer();
		Pixel[][] pixels;
		pixels=getPixels();

		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];
				byte foreOrBackGround=pixel.getForOrBrackground();
				cadena.append(foreOrBackGround);
			}
		}
		
		String[] arrayBytes=new String[16*2];
		String[][] sprites=new String[4][8];
		String[][] spritesBinary=new String[4][8];
		byte position=0;
		//El formato de color 1 divide el sprite de 16 pixeles en 4 partes
		for(int i=0;i<cadena.length()+1;i++) {
			if(i>0 && i % 8==0) {
				String valor=cadena.substring(i-8, i);
				arrayBytes[position]=valor;
				position++;
			}
			
		}

		//Los numeros pares formarán parte del 1 y 3 sprites
		int count0=0;
		int count1=0;
		int count2=0;
		int count3=0;
		for(int i=0;i<arrayBytes.length;i++) {
			//System.out.println("i:"+i+", "+arrayBytes[i]);
			if(i % 2==0) {
				if (i<16) {
					sprites[0][count0]=arrayBytes[i];
					count0++;
				}else if(i>=16 && i<32) {
					sprites[1][count1]=arrayBytes[i];
					count1++;
				}
			}else {
				if (i<16) {
					sprites[2][count2]=arrayBytes[i];
					count2++;
				}else if(i>=16 && i<32) {
					sprites[3][count3]=arrayBytes[i];
					count3++;
				}
			}	
		}
		
		//Paranoia
		for (int x=0;x<4;x++) {
			//System.out.println("Sprite: "+x);
			for (int i=0;i<sprites[x].length;i++) {
				String cadenaSprite=sprites[x][i];
				cadenaSprite=cadenaSprite.replace("0","1");
				cadenaSprite=cadenaSprite.replace("2","0");
				int binary=NumberManager.getDecimal(Integer.valueOf(cadenaSprite));
				//String binaryToHexadecimal=NumberManager.decimalAHexadecimal(binary);
				spritesBinary[x][i]=String.valueOf(binary);
				sprites[x][i]=cadenaSprite;
			}
		}
		return spritesBinary;
	}

	
	
	
	
	
	
	
	//Girar horizontal
	public void rotateLandscape() {
		//System.out.println("Girar horizonatl");
		Pixel[][] pixels=getPixels();
		Pixel[][] pixelsHelp=new Pixel[16][16];
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];	
				int newX=15-x;
				int newY=15-y;
				if(x>0)
					pixelsHelp[x][y]=new Pixel((15*sizeTile)-pixel.getPositionX(),pixel.getPositionY(), pixel.getForOrBrackground(), pixel.getColor());
			}
		}
		deleteAll();
		fillPixellOnCanvas(pixelsHelp);
	}


	public void rotateUpright() {
		//System.out.println("Girar vertical");
		//Cambiamos los colores de los botones
		Color[] colors0=new Color[16];
		for(int i=0;i<jButtons0.length;i++) {
			colors0[i]=jButtons0[i].getBackground();
		}
		int countFile=0;
		for (int i=15;i<jButtons0.length;i--) {
			if(countFile<16 && countFile>0)
				jButtons0[countFile].setBackground(colors0[i]);
			countFile++;
		}
		Pixel[][] pixels=getPixels();
		Pixel[][] pixelsHelp=new Pixel[16][16];
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];	
				int newX=15-x;
				int newY=15-y;
				if(x>0)
					pixelsHelp[x][y]=new Pixel(pixel.getPositionX(),(15*sizeTile)-pixel.getPositionY(), pixel.getForOrBrackground(), pixel.getColor());
			}
		}
		deleteAll();
		fillPixellOnCanvas(pixelsHelp);
	}

	//Girrar a la izquierda
	public void turnLeft() {
		Pixel[][] pixels=getPixels();
		Pixel[][] pixelsHelp=new Pixel[16][16];
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];	
				int newX=15-x;
				int newY=15-y;
				if(x>0)
					pixelsHelp[x][y]=new Pixel((15*sizeTile)-pixel.getPositionX(),(15*sizeTile)-pixel.getPositionY(), pixel.getForOrBrackground(), pixel.getColor());
			}
		}
		deleteAll();
		fillPixellOnCanvas(pixelsHelp);
		
	}
	//Girar a la derecha
	public void turnRight() {
		Pixel[][] pixels=getPixels();
		Pixel[][] pixelsHelp=new Pixel[16][16];
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];	
				int newX=15-x;
				int newY=15-y;
				if(y<15 && x>0)
					pixelsHelp[x][y]=new Pixel(pixel.getPositionX()-(15*sizeTile),pixel.getPositionY()-(15*sizeTile), pixel.getForOrBrackground(), pixel.getColor());
			}
		}
		deleteAll();
		fillPixellOnCanvas(pixelsHelp);
		
	}


	public void moveOneLeft() {
		Pixel[][] pixels=getPixels();
		Pixel[][] pixelsHelp=new Pixel[16][16];
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];	
				int newX=15-x;
				int newY=15-y;
				if(x>0)
					pixelsHelp[x][y]=new Pixel(pixel.getPositionX()-sizeTile,pixel.getPositionY(), pixel.getForOrBrackground(), pixel.getColor());
			}
		}
		deleteAll();
		fillPixellOnCanvas(pixelsHelp);
	}


	public void moveOneRight() {
		Pixel[][] pixels=getPixels();
		Pixel[][] pixelsHelp=new Pixel[16][16];
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];	
				int newX=15-x;
				int newY=15-y;
				if(x<15)
					pixelsHelp[x][y]=new Pixel(pixel.getPositionX()+sizeTile,pixel.getPositionY(), pixel.getForOrBrackground(), pixel.getColor());
			}
		}
		deleteAll();
		fillPixellOnCanvas(pixelsHelp);
	}

	public void moveOneUp() {
		//Movemos los colores de los botones 1 arriba
		Color[] colors0=new Color[16];
		for(int i=0;i<jButtons0.length;i++) {
			colors0[i]=jButtons0[i].getBackground();
		}
		for (int i=0;i<jButtons0.length;i++) {
			int newColorPosition=i+1;
			if(newColorPosition<16)
				jButtons0[i].setBackground(colors0[newColorPosition]);
		
		}
		Pixel[][] pixels=getPixels();
		Pixel[][] pixelsHelp=new Pixel[16][16];
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];	
				int newX=15-x;
				int newY=15-y;
				if(y>0)
					pixelsHelp[x][y]=new Pixel(pixel.getPositionX(),pixel.getPositionY()-sizeTile, pixel.getForOrBrackground(), pixel.getColor());
			}
		}
		deleteAll();
		fillPixellOnCanvas(pixelsHelp);
	}

	public void moveOneDown() {
		//Movemos los colores de los botones 1 abajo
		Color[] colors0=new Color[16];
		for(int i=0;i<jButtons0.length;i++) {
			colors0[i]=jButtons0[i].getBackground();
		}
		for (int i=0;i<jButtons0.length;i++) {
			int newColorPosition=i-1;
			if(newColorPosition>0)
			jButtons0[i].setBackground(colors0[newColorPosition]);
		
		}
		Pixel[][] pixels=getPixels();
		Pixel[][] pixelsHelp=new Pixel[16][16];
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];	
				int newX=15-x;
				int newY=15-y;
				if(y<15)
					pixelsHelp[x][y]=new Pixel(pixel.getPositionX(),pixel.getPositionY()+sizeTile, pixel.getForOrBrackground(), pixel.getColor());
			}
		}
		deleteAll();
		fillPixellOnCanvas(pixelsHelp);
	}









	

	/******************MouseListener*****************************/
	@Override
	public void mouseClicked(MouseEvent e) {}

	/**
	 * Al pinchar posicionamos la x e y en la posición correcta
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		Point point=e.getPoint();
		float positionX=point.x;
		float positionY=point.y;
		//System.out.println(" x: "+positionX+" y: "+positionY);
		//esto es para obtener un n�mero entero
		float positionXRelocated=(point.x/sizeTile)*sizeTile;
		float positionYRelocated=(point.y/sizeTile)*sizeTile;
		//System.out.println(" x recoloado: "+positionXRelocated+" y recolocado: "+positionYRelocated);
		if( positionX<maxWidth && positionY<maxHeight) {
			if(e.getButton()==MouseEvent.BUTTON1) {
				clickedButton1=true;
				//Si est� seleccionado el bote de pintura llamos al m�todo que rellena los pixeles
				if(paintBot) {
					paintPotPixels((int)positionXRelocated,(int)positionYRelocated);
				}else {
					if(selectedClear) {
						paintPixelAtPoint((int)positionXRelocated,(int)positionYRelocated,(byte)2);
					}else {
						paintPixelAtPoint((int)positionXRelocated,(int)positionYRelocated,(byte)0);
					}
				}	
			}else if(e.getButton()==MouseEvent.BUTTON3) {
				clickedButton3=true;
				paintPixelAtPoint((int)positionXRelocated,(int)positionYRelocated,(byte)1);
			}
		}else {
			//System.out.println("No se pudo: x: "+positionX+", y:"+positionY+", maxWidth: "+maxWidth+", mazHeight: "+maxHeight);
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		clickedButton1=false;
		clickedButton3=false;
		fillTextAreaDefinitionAndColot();
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	/******************End MouseListener*****************************/

	/******************MouseMotionListener*****************************/
	@Override
	public void mouseDragged(MouseEvent e) {
		Point point=e.getPoint();
		float positionX=point.x;
		float positionY=point.y;
		//System.out.println(" x: "+positionX+" y: "+positionY);
		//esto es para obtener un n�mero entero
		float positionXRelocated=(point.x/sizeTile)*sizeTile;
		float positionYRelocated=(point.y/sizeTile)*sizeTile;
		//System.out.println(" x recoloado: "+positionXRelocated+" y recolocado: "+positionYRelocated);
		if(  positionXRelocated>=0 && positionXRelocated<maxWidth-borderX && positionYRelocated>=0 && positionYRelocated<maxHeight-borderY) {
			if(clickedButton1) {
				//Si est� seleccionado el bote de pintura llamos al m�todo que rellena los pixeles
				if(paintBot) {
					paintPotPixels((int)positionXRelocated,(int)positionYRelocated);
				}else {
					if(selectedClear) {
						paintPixelAtPoint((int)positionXRelocated,(int)positionYRelocated,(byte)2);
					}else {
					    paintPixelAtPoint((int)positionXRelocated,(int)positionYRelocated,(byte)0);	
					}
				}	
			}else if(clickedButton3) {
				paintPixelAtPoint((int)positionXRelocated,(int)positionYRelocated,(byte)1);
			}
		}else {
			//System.out.println("No se pudo: x: "+positionX+", y:"+positionY+", maxWidth: "+maxWidth+", mazHeight: "+maxHeight);
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println("mousemoved dice: x: "+e.getX()+", y: "+e.getY());
	}
	/******************End MouseMotionListener*****************************/
	




}