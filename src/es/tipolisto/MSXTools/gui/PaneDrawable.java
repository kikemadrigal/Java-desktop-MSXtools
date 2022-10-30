package es.tipolisto.MSXTools.gui;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import es.tipolisto.MSXTools.beans.Pixel;
import es.tipolisto.MSXTools.beans.RGB;
import es.tipolisto.MSXTools.utils.MSXPalette;
import es.tipolisto.MSXTools.utils.NumberManager;
import es.tipolisto.MSXTools.utils.Palettes;



//Create a component that you can actually draw on.
//KeyListener
public class PaneDrawable extends JPanel implements MouseListener, MouseMotionListener{
	private int borderX;
	private int borderY;
	//private int borderTop;
	//private int borderDown;
	private int maxWidth;
	private int maxHeight; 
	private int sizeTile;
	private int pixelX;
	private int pixelY;
	private int pixelOldX;
	private int pixelOldY;
	private int horizontalTiles;
	private int verticalTiles;
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
	private byte paintBotX;
	private byte paintBotY;
	
	public PaneDrawable(int horizontalTiles, int verticalTiles) {
		borderX=120;
		borderY=120;
		sizeTile=20;
		maxWidth=(sizeTile*horizontalTiles)+borderX;
		maxHeight=(sizeTile*verticalTiles)+borderY;
		pixelX=borderX;
		pixelY=borderY;
		pixelOldX=0;
		pixelOldY=0;
		this.horizontalTiles=horizontalTiles;
		this.verticalTiles=verticalTiles;
		jButtons0=new JButton[16];
		jButtons1=new JButton[16];
		//El canvas tendrá un array de pixeles independiente
		pixels=new Pixel[16][16];
		deleteColor=new Color(239,252,254);
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=new Pixel((x*sizeTile)+borderX,(y*sizeTile)+borderY,(byte)2,deleteColor);
				pixels[x][y]=pixel;
			}
		}
		clickedButton1=false;
		clickedButton3=false;
		selectedClear=false;
		paintBot=false;
		paintBotX=0;
		paintBotY=0;
		this.requestFocusInWindow();
		//addKeyListener(this);
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


	@Override
	public void paintComponent(Graphics g) {
		//Graphics graphics=this.getGraphics();		
		//dibujamos dos líneas para separar los sprites de 8x8
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.PINK);
		//g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
		//g.drawLine(borderX-10,borderY+(8*sizeTile),maxWidth+10, borderY+(8*sizeTile));
		//g.drawLine(borderX,borderY,maxWidth, maxHeight);
        g2.setStroke(new BasicStroke(2));
        g2.draw(new Line2D.Float(borderX-10,borderY+(8*sizeTile),maxWidth+10, maxHeight-(8*sizeTile)));
        g2.draw(new Line2D.Float(borderX+(8*sizeTile),borderY-10,maxWidth-(8*sizeTile), maxHeight+10));
        g2.setStroke(new BasicStroke(1));
		g.setColor(Color.BLACK);
		//Dibujamos las lineas horizontales
		for (int i=borderX;i<=borderX+(sizeTile*horizontalTiles);i+=sizeTile) {
			g.drawLine(borderX, i, borderX+(sizeTile*horizontalTiles), i);
			//System.out.println("borderX: "+borderX+" i+1: "+y+" Total: "+sizeTile*horizontalTiles);
		}
		//Dubjamos las líneas verticales	
		for (int i=borderY;i<=borderY+(sizeTile*verticalTiles);i+=sizeTile) {
			g.drawLine(i, borderY, i, borderY+(sizeTile*verticalTiles));
		}
	
		//Seleccionamos el area donde vamos a pintar
		paintFrameAtPoint(this.getGraphics(),pixelX,pixelY);
	}
	
	//Al pulsar enter pintaremos un cuadrado de un color
	public void paintPixelAtPoint(int x, int y, byte colorBackOrFront){
		//La x en el evento on click la recolocamos así:
		//x=((point.x-borderX)/sizeTile)*sizeTile
		//y=((point.y-borderY)/sizeTile)*sizeTile;
		//System.out.println("paintPixel dice, recibido: x : "+x+", y: "+y);
		Graphics g=this.getGraphics();
		//Para obtener el botón 
		int row=(y-borderY)/sizeTile;
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
		int positionOnArrayColumn=(x-borderX)/sizeTile;
		int positionOnArrayFile=(y-borderY)/sizeTile;

		pixels[positionOnArrayColumn][positionOnArrayFile].setPositionX(x);
		pixels[positionOnArrayColumn][positionOnArrayFile].setPositionX(x);
		pixels[positionOnArrayColumn][positionOnArrayFile].setPositionY(y);
		pixels[positionOnArrayColumn][positionOnArrayFile].setForOrBrackground(colorBackOrFront);
		pixels[positionOnArrayColumn][positionOnArrayFile].setColor(colorJButton);

		//System.out.println("paintPixel dice: almacenado pixel x: "+positionOnArrayColumn+", y:"+positionOnArrayFile);
		//System.out.println("paintPixel dice: almacenado : "+colorBackOrFront);
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
		//Obtenemos el color del pixel de esa fila
		int positionOnArrayColumn=(pointX-borderX)/sizeTile;
		int positionOnArrayFile=(pointY-borderY)/sizeTile;
		Pixel pixel=pixels[positionOnArrayColumn][positionOnArrayFile];
		Pixel nextPixel=pixels[positionOnArrayColumn][positionOnArrayFile];
		Color colorPixel=pixel.getColor();
		Color colorNextPixel=nextPixel.getColor();
		int count=0;
		while(count<16*16 && colorPixel.getRed()==colorNextPixel.getRed() && colorPixel.getGreen()==colorNextPixel.getGreen() && colorPixel.getBlue()==colorNextPixel.getBlue()) {
			positionOnArrayColumn++;
			if(positionOnArrayColumn>15) {
				positionOnArrayColumn=0;
				positionOnArrayFile++;
			}
			if(positionOnArrayFile>15)positionOnArrayFile=0;
			nextPixel=pixels[positionOnArrayColumn][positionOnArrayFile];
			paintPixelAtPoint(pixels[positionOnArrayColumn][positionOnArrayFile].getPositionX(), pixels[positionOnArrayColumn][positionOnArrayFile].getPositionY(), (byte)0);
			count++;
		}
	}
	
	public void fillPixellOnCanvas(Pixel[][] pixels) {
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];
				//int pointX=pixel.getPositionX();
				//int pointY=pixel.getPositionY();
				//Obtenemos el color del pixel de esa fila
				//int positionOnArrayColumn=(pointX-borderX)/sizeTile;
				//int positionOnArrayFile=(pointY-borderY)/sizeTile;
				//paintPixelAtPoint(pixels[positionOnArrayColumn][positionOnArrayFile].getPositionX(), pixels[positionOnArrayColumn][positionOnArrayFile].getPositionY(), (byte)0);
				paintPixelAtPoint(pixel.getPositionX(),pixel.getPositionY(),(byte)0);
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
	public JTextArea getTextAreaColor() {
		return textAreaColor;
	}
	public void setTextAreaColor(JTextArea textAreaColor) {
		this.textAreaColor = textAreaColor;
	}
	public JTextArea getTextAreaDefinition() {
		return textAreaDefinition;
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
	/*************End Getters and setters************************/

	/******************KeyListener*****************************/
	/*@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//System.out.println("Presionaste la tecla: "+key);
		//if(pixelX>borderX && pixelX<pixelMapWidth && pixelY>borderY && pixelY<pixelMapHeight) {
		pixelOldX=pixelX;
		pixelOldY=pixelY;
			switch(key) {
				case KeyEvent.VK_LEFT:
					 deleteFrameAtPoint(this.getGraphics(),pixelOldX,pixelOldY);
					 pixelX -= sizeTile;
				break;
				case KeyEvent.VK_RIGHT:
					deleteFrameAtPoint(this.getGraphics(),pixelOldX,pixelOldY);
					pixelX += sizeTile;
				break;
				case KeyEvent.VK_UP:
					deleteFrameAtPoint(this.getGraphics(),pixelOldX,pixelOldY);
					pixelY -= sizeTile;
				break;
				case KeyEvent.VK_DOWN:
					deleteFrameAtPoint(this.getGraphics(),pixelOldX,pixelOldY);
					pixelY += sizeTile;
				break;
				case KeyEvent.VK_ENTER:
					 paintPixelAtPoint(pixelX,pixelY, (byte)0);
				break;	
			}
			paintFrameAtPoint(this.getGraphics(),pixelX,pixelY);
		//}	 
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	*/
	/******************End KeyListener*****************************/

	


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
		if(positionX>borderX && positionX<maxWidth && positionY>borderY && positionY<maxHeight) {
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
		fillFields();
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
		float positionXRelocated=(point.x/sizeTile)*sizeTile;
		float positionYRelocated=(point.y/sizeTile)*sizeTile;
		//System.out.println(" x recoloado: "+positionXRelocated+" y recolocado: "+positionYRelocated);
		if(positionX>borderX && positionX<maxWidth && positionY>borderY && positionY<maxHeight) {
			if(clickedButton1) {
				if(selectedClear) {
					paintPixelAtPoint((int)positionXRelocated,(int)positionYRelocated,(byte)2);
				}else {
					paintPixelAtPoint((int)positionXRelocated,(int)positionYRelocated,(byte)0);
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
	
	public void fillFields() {
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
		
		String[] definitionColors=getDataColors();
		String definitionColor="";
		for(int i=0;i<definitionColors.length;i++) {
			if(i==definitionColors.length-1)
				definitionColor+=definitionColors[i];
			else
				definitionColor+=definitionColors[i]+",";
		}
		textAreaColor.setText(definitionColor);
	}
	
	
	private String[] getDataColors() {
		String[] arrayDefinitionColors=new String[16];
		//El caracter de la izquierda es el color de fondo, el de la dereecha es el de frente
		StringBuilder resultColors = new StringBuilder("00");
		Pixel[][] pixels;
		pixels=getPixels();
		for(int y=0;y<pixels.length;y++) {
			for(int x=0;x<pixels[0].length;x++) {
				Pixel pixel=pixels[x][y];
				Color color=pixel.getColor();
				int positionColorOnPalette=getPositionColorOnPalette(color);
				String stringPositionColorOnPalette=NumberManager.decimalAHexadecimal(positionColorOnPalette);
				char[] charArray=stringPositionColorOnPalette.toCharArray();
				char charPositionColorOnPalette=' ';
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
				arrayDefinitionColors[y]=resultColors.toString();
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

	
	
	private int getPositionColorOnPalette(Color color) {
		HashMap<MSXPalette, RGB> palettePhilips8255NMS=Palettes.getPalettePhilips8255NMSForSprites();
		int number=0;
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			RGB rgbItem=entry.getValue();
			if(rgbItem.getRed()==color.getRed() && rgbItem.getBlue()==color.getBlue() && rgbItem.getGreen()==color.getGreen()) {
				number=entry.getKey().ordinal();
			}
		}
		return number;
	}




}