package es.tipolisto.MSXTools.gui.pixelEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import es.tipolisto.MSXTools.beans.Pixel;
import es.tipolisto.MSXTools.beans.Sprite;
import es.tipolisto.MSXTools.utils.ImageManager;


/**
 * 
 * El riboon representa donde salen los sprites
 * Necesita el :
 * canvas para que al hacer click en un sprite aparezca en el canvas
 * 
 */
public class RiboonSpriteEditor extends JPanel{

	private static final long serialVersionUID = 6563947585862209486L;
	private PaneDrawableSpriteEditor canvas;
	private ArrayList<Sprite> arrayListSprites;
	private JLabel lblSpriteNumberOnCanvas;
	private JLabel lblNumberSpriteRiboon,lblNameSpriteRiboon;
	private JTextField jtFieldNameSpriteOnCanvas;
	private JTextArea textAreaDefinition,textAreaColors;
	private JButton[] jButtons0,jButtons1;

	//private JScrollPane jscrollPanelImageSprites;
	private Dimension dimensionJScrollPaneSprites;

	private Sprite spriteOnRiboonSelected;
	
	public RiboonSpriteEditor(PaneDrawableSpriteEditor canvas,
			ArrayList<Sprite> arrayListSprites,
			JLabel lblSpriteNumberOnCanvas,
			JLabel lblNumberSpriteRiboon,JLabel lblNameSpriteRiboon,
			JTextField jtFieldNameSpriteOnCanvas,
			JTextArea textAreaDefinition, JTextArea textAreaColors,
			JButton[] jButtons0, JButton[] jButtons1) {
		
		setBounds(10, 500,850, 150);
		setBackground(Color.WHITE);
		setLayout(null);

		this.canvas=canvas;
		this.arrayListSprites=arrayListSprites;
		this.textAreaDefinition=textAreaDefinition;
		this.textAreaColors=textAreaColors;
		this.lblSpriteNumberOnCanvas=lblSpriteNumberOnCanvas;
		this.lblNumberSpriteRiboon=lblNumberSpriteRiboon;
		this.lblNameSpriteRiboon=lblNameSpriteRiboon;
		this.jtFieldNameSpriteOnCanvas=jtFieldNameSpriteOnCanvas;
		this.jButtons0=jButtons0;
		this.jButtons1=jButtons1;
		
		if(arrayListSprites.size()==0) {
			lblNumberSpriteRiboon.setText("X");
			lblNameSpriteRiboon.setText("No sprite selected");
		}
		
	}
	
	public void setDimension(Dimension dimensionJScrollPaneSprites) {
		this.dimensionJScrollPaneSprites=dimensionJScrollPaneSprites;
	}
	
	
	private BufferedImage getBufferedImageButton(Sprite sprite) {
		//1.Obtenemos los pixeles
		Pixel[][] pixels = sprite.getPixels();
		//Necesitamos crear un befferedImage para poder trabajar con pixeles
		BufferedImage bufferedImageDestiny = new BufferedImage(sprite.getType(), sprite.getType(), BufferedImage.TYPE_INT_RGB);
		int colorSRGB = 0;
		for (int y = 0; y < pixels.length; y++) {
			for (int x = 0; x < pixels[0].length; x++) {
				Pixel pixel = pixels[x][y];
				Color color = pixel.getColor();
				// System.out.print("["+color.getRed()+","+color.getGreen()+","+color.getBlue()+"]");
				colorSRGB = (int) color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
				bufferedImageDestiny.setRGB(x, y, colorSRGB);
			}
			// System.out.println("");
		}
		// La transformamos para que no seva de 16x16pixeles
		Image img = bufferedImageDestiny.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		BufferedImage bufferedImageScaled = ImageManager.imageToBufferedImage(img);
		return bufferedImageScaled;
	}
	
	
	
	/**
	 * 
	 * Al pichar en el bot�n
	 */
	private void createButtonRiboon(Sprite sprite, BufferedImage bufferedImageScaled) {
		dimensionJScrollPaneSprites.setSize(sprite.getNumber() * 150,getHeight());
		JButton btnImageSprites = new JButton("");
		//Ubicamos la imagen en la posici�n que queramos
		btnImageSprites.setBounds(10 + (sprite.getNumber() * 120), 10, 16 * 7, 16 * 7);
		//System.out.println("nueva dimension: "+dimensionJScrollPaneSprites.getWidth());
		add(btnImageSprites);
		// lblLabelImageSprites = sprite.getjLabel();
		btnImageSprites.setIcon(new ImageIcon(bufferedImageScaled));
		// Creamos en la imagen los comportamientos
		btnImageSprites.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON1) {
					if (e.getClickCount() == 2) {
						//Cuando hacemos dos clicks es que queremos editarlo y por lo tanto se mete en el canvas
						showSpriteOnContentPane(sprite);
						canvas.setSpriteOnCanvasSelected(sprite);	
					}
					spriteOnRiboonSelected=sprite;	
					lblNumberSpriteRiboon.setText(""+sprite.getNumber());
					lblNameSpriteRiboon.setText(sprite.getName());
					updateBorders(sprite);
					//Men� borrar
				}else if(e.getButton()==MouseEvent.BUTTON3) {
					spriteOnRiboonSelected=sprite;
					updateBorders(sprite);
					deleteSprite(sprite);
				}	
			}
		});
	}
	
	
	public void updateRiboon(Sprite sprite) {
		//Vamos a mostrar este sprite en el riboon
		//Obtenemos la imagen para el bot�n
		BufferedImage bufferedImageScaled=getBufferedImageButton(sprite);
		//Creamos su JButton, le ponemos la imagen y le aplicamos los comportamientos
		createButtonRiboon(sprite,bufferedImageScaled);
		repaint();
		//La seleccionamos y ponemos el borde
		spriteOnRiboonSelected=sprite;
		lblNumberSpriteRiboon.setText(""+sprite.getNumber());
		lblNameSpriteRiboon.setText(sprite.getName());
		updateBorders(sprite);
	}
	
	
	
	public void updateAllRiboon(){
		//Limpiamos todo los pixeles del canvas
		//canvas.deleteAll();
		//Limpiamos todo el riboon
		removeAll();
		repaint();
		int countNumberSprite=0;
		for (Sprite sprite:arrayListSprites) {
			//Le modificamos para que el n�mero de sprite sea el correcto
			sprite.setNumber(countNumberSprite);
			//Obtenemos la imagen
			BufferedImage bufferedImageScaled=getBufferedImageButton(sprite);
			createButtonRiboon(sprite,bufferedImageScaled);
			countNumberSprite++;
		}	
		updateBorders(spriteOnRiboonSelected);
	}
	
	
	public void showSpriteOnContentPane(Sprite sprite) {
		//1.Obtenemos el color de los botones y se los asignamos al jFrame
		Color[] colorButtons0Sprite=sprite.getColorButtons0();
		Color[] colorButtons1Sprite=sprite.getColorButtons1();
		for (int i=0;i<jButtons0.length;i++) {
			//Obtenemos el bot�n
			JButton button0Frame=jButtons0[i];
			JButton button1Frame=jButtons1[i];
			//Obtenemos el color guardado en el sprite
			Color color0Frame=colorButtons0Sprite[i];
			Color color1Sprite=colorButtons1Sprite[i];
			//Se lo ponemos al bpot�n del fram
			button0Frame.setBackground(color0Frame);
			button1Frame.setBackground(color1Sprite);
		}

		//2.Obtenemos los pixeles para dibujarlos en el canvas
		Pixel[][] pixelsSprite=sprite.getPixels();
		for(int y=0;y<pixelsSprite.length;y++) {
			for(int x=0;x<pixelsSprite[0].length;x++) {
				Pixel pixelSprite=pixelsSprite[x][y];
				canvas.paintPixelAtPoint(pixelSprite.getPositionX(), pixelSprite.getPositionY(), pixelSprite.getForOrBrackground());
			}
		}
		
		//3.Obtenemos los textsAreas
		String dataDefinition=sprite.getDataDefinition();
		textAreaDefinition.setText(dataDefinition);
		String dataColors=sprite.getDataColors();
		textAreaDefinition.setText(dataDefinition);
		textAreaColors.setText(dataColors);		
		
		//Ponemos el nombre y n�mero
		lblSpriteNumberOnCanvas.setText(String.valueOf(sprite.getNumber()));
		jtFieldNameSpriteOnCanvas.setText(sprite.getName());
		
		//Se lo ponemos al canvas
		//canvas.setSpriteOnCanvasSelected(sprite);
	}






	public void moveSpriteLeftOnRiboon() {
		for(int i=0;i<arrayListSprites.size();i++) {
			Sprite sprite=arrayListSprites.get(i);
			if(sprite.getNumber()==spriteOnRiboonSelected.getNumber() && i>0) {
				//Cogemos el sprite anterior
				Sprite spriteHelp=arrayListSprites.get(i-1);
				//ponemos nuestro sprite seleccionado en la posici�n anterior
				arrayListSprites.set(i-1, spriteOnRiboonSelected);
				//Ponemos el sprite guardado en la posici�n siguiente
				arrayListSprites.set(i, spriteHelp);
				lblSpriteNumberOnCanvas.setText(String.valueOf(i-1));
			}
		}	
	}

	public void moveSpriteRightOnRiboon() {
		for(int i=arrayListSprites.size()-1;i>=0;i--) {
			Sprite sprite=arrayListSprites.get(i);
			if(sprite.getNumber()==spriteOnRiboonSelected.getNumber() && i<arrayListSprites.size()-1) {
				//Cogemos el sprite siguente
				Sprite spriteHelp=arrayListSprites.get(i+1);
				//Metemos el seleccionado en la i
				arrayListSprites.set(i+1, spriteOnRiboonSelected);
				//Ponemos el sprite guardado en la posici�n siguiente
				arrayListSprites.set(i, spriteHelp);	
				lblSpriteNumberOnCanvas.setText(String.valueOf(i+1));
			}	
		}	
	}
	
	
	public void updateBorders(Sprite sprite) {
		for(int i=0;i<arrayListSprites.size();i++) {
			JButton button=(JButton) getComponent(i);
			if(spriteOnRiboonSelected.getNumber()!=arrayListSprites.get(i).getNumber()) {
				button=(JButton) getComponent(i);
				button.setBorder(null);
			}else {
				button.setBorder(BorderFactory.createLineBorder(Color.red, 5));
			}
		}
	}
	
	public Sprite getSpriteOnRiboonSelected() {
		return spriteOnRiboonSelected;		
	}
	
	public void deleteSprite(Sprite sprite) {
		int input = JOptionPane.showConfirmDialog(null, "Delete item?");
        // 0=yes, 1=no, 2=cancel
		if(input==0) {
			Sprite spriteNext=null;
			if(arrayListSprites.size()<=1)
				spriteNext=arrayListSprites.get(0);
			//Si el tama�o es mayor que 1
			else
				if(spriteOnRiboonSelected.getNumber()==0) 
					spriteNext=arrayListSprites.get(1);
				else
					spriteNext=arrayListSprites.get(sprite.getNumber()-1);
			spriteOnRiboonSelected=spriteNext;
			lblNumberSpriteRiboon.setText(""+spriteNext.getNumber());
			lblNameSpriteRiboon.setText(spriteNext.getName());
			arrayListSprites.remove(sprite.getNumber());
			//System.out.println("borrado el "+sprite.getNumber());
			updateAllRiboon();
		}
	}
}
