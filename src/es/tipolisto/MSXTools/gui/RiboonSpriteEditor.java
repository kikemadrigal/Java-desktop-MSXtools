package es.tipolisto.MSXTools.gui;

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

import es.tipolisto.MSXTools.beans.Pixel;
import es.tipolisto.MSXTools.beans.Sprite;
import utils.ImageManager;

/**
 * 
 * El riboon representa donde salen los sprites
 * Necesita el :
 * canvas para que al hacer click en un sprite aparezca en el canvas
 * 
 */
public class RiboonSpriteEditor extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private PaneDrawableSpriteEditor canvas;
	private ArrayList<Sprite> arrayListSprites;
	private JLabel lblSpriteNumber,lblSpriteName;
	private JTextArea textAreaDefinition,textAreaColors;
	private JButton[] jButtons0,jButtons1;

	//private JScrollPane jscrollPanelImageSprites;
	private Dimension dimensionJScrollPaneSprites;

	private Sprite spriteSelected;
	
	public RiboonSpriteEditor(PaneDrawableSpriteEditor canvas,
			ArrayList<Sprite> arrayListSprites,
			JLabel lblSpriteNumber,JLabel lblSpriteName,
			JTextArea textAreaDefinition, JTextArea textAreaColors,
			JButton[] jButtons0, JButton[] jButtons1) {
		
		setBounds(10, 500,850, 150);
		setBackground(Color.WHITE);
		setLayout(null);

		this.canvas=canvas;
		this.arrayListSprites=arrayListSprites;
		this.textAreaDefinition=textAreaDefinition;
		this.textAreaColors=textAreaColors;
		this.lblSpriteNumber=lblSpriteNumber;
		this.lblSpriteName=lblSpriteName;
		this.jButtons0=jButtons0;
		this.jButtons1=jButtons1;
		spriteSelected=new Sprite(0,0,0,"Sprite_name0");
	}
	
	public void setDimension(Dimension dimensionJScrollPaneSprites) {
		this.dimensionJScrollPaneSprites=dimensionJScrollPaneSprites;
	}
	
	
	private BufferedImage getBufferedImageButton(Sprite sprite) {
		//1.Obtenemos los pixeles
		Pixel[][] pixels = sprite.getPixels();
		//Necesitamos crear un befferedImage para poder trabajar con pixeles
		BufferedImage bufferedImageDestiny = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
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
	 * Al pichar en el botón
	 */
	private void createButtonRiboon(Sprite sprite, BufferedImage bufferedImageScaled) {
		dimensionJScrollPaneSprites.setSize(sprite.getNumber() * 150,getHeight());
		JButton btnImageSprites = new JButton("");
		//Ubicamos la imagen en la posición que queramos
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
						showSpriteOnContentPane(sprite);
						spriteSelected=sprite;						
					}else {
						spriteSelected=sprite;
						updateBorders(sprite);
					}
					//Menú borrar
				}else if(e.getButton()==MouseEvent.BUTTON3) {
					spriteSelected=sprite;
					updateBorders(sprite);
					
					deleteSprite(sprite);
					
				}	
			}
		});
	}
	
	
	public void updateRiboon(Sprite sprite) {
		//Vamos a mostrar este sprite en el riboon
		//Obtenemos la imagen para el botón
		BufferedImage bufferedImageScaled=getBufferedImageButton(sprite);
		//Creamos su JButton, le ponemos la imagen y le aplicamos los comportamientos
		createButtonRiboon(sprite,bufferedImageScaled);
		//La seleccionamos y ponemos el borde
		spriteSelected=sprite;
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
			//Le modificamos para que el número de sprite sea el correcto
			sprite.setNumber(countNumberSprite);
			//Obtenemos la imagen
			BufferedImage bufferedImageScaled=getBufferedImageButton(sprite);
			createButtonRiboon(sprite,bufferedImageScaled);
			countNumberSprite++;
		}	
		updateBorders(spriteSelected);
	}
	
	
	private void showSpriteOnContentPane(Sprite sprite) {
		//1.Obtenemos el color de los botones y se los asignamos al contentPane
		Color[] colorButtons0Sprite=sprite.getColorButtons0();
		Color[] colorButtons1Sprite=sprite.getColorButtons1();
		for (int i=0;i<jButtons0.length;i++) {
			//Obtenemos el botón
			JButton button0Frame=jButtons0[i];
			JButton button1Frame=jButtons1[i];
			//Obtenemos el color guardado en el sprite
			Color color0Frame=colorButtons0Sprite[i];
			Color color1Sprite=colorButtons1Sprite[i];
			//Se lo ponemos al bpotón del fram
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
		
		//Ponemos el nombre y número
		//contentPane.repaint();
		lblSpriteNumber.setText(String.valueOf(sprite.getNumber()));
		lblSpriteName.setText(sprite.getName());
		//lblSpriteNumber.setBackground(Color.WHITE);
		//lblSpriteName.setBackground(Color.WHITE);
	}






	public void moveSpriteLeftOnRiboon() {
		for(int i=0;i<arrayListSprites.size();i++) {
			Sprite sprite=arrayListSprites.get(i);
			if(sprite.getNumber()==spriteSelected.getNumber() && i>0) {
				//Cogemos el sprite anterior
				Sprite spriteHelp=arrayListSprites.get(i-1);
				//ponemos nuestro sprite seleccionado en la posición anterior
				arrayListSprites.set(i-1, spriteSelected);
				//Ponemos el sprite guardado en la posición siguiente
				arrayListSprites.set(i, spriteHelp);
				lblSpriteNumber.setText(String.valueOf(i-1));
			}
		}	
	}

	public void moveSpriteRightOnRiboon() {
		for(int i=arrayListSprites.size()-1;i>=0;i--) {
			Sprite sprite=arrayListSprites.get(i);
			if(sprite.getNumber()==spriteSelected.getNumber() && i<arrayListSprites.size()-1) {
				//Cogemos el sprite siguente
				Sprite spriteHelp=arrayListSprites.get(i+1);
				//Metemos el seleccionado en la i
				arrayListSprites.set(i+1, spriteSelected);
				//Ponemos el sprite guardado en la posición siguiente
				arrayListSprites.set(i, spriteHelp);	
				lblSpriteNumber.setText(String.valueOf(i+1));
			}	
		}	
	}
	
	
	public void updateBorders(Sprite sprite) {
		for(int i=0;i<arrayListSprites.size();i++) {
			JButton button=(JButton) getComponent(i);
			if(spriteSelected.getNumber()!=arrayListSprites.get(i).getNumber()) {
				button=(JButton) getComponent(i);
				button.setBorder(null);
			}else {
				button.setBorder(BorderFactory.createLineBorder(Color.red, 5));
			}
		}
	}
	
	public Sprite getSpriteSelected() {
		return spriteSelected;		
	}
	
	public void deleteSprite(Sprite sprite) {
		int input = JOptionPane.showConfirmDialog(null, "Delete item?");
        // 0=yes, 1=no, 2=cancel
		if(input==0) {
			if(arrayListSprites.size()<=1)
				spriteSelected=arrayListSprites.get(1);
			//Si el tamaño es mayor que 1
			else
				if(spriteSelected.getNumber()==0)
					spriteSelected=arrayListSprites.get(1);
				else
					spriteSelected=arrayListSprites.get(sprite.getNumber()-1);
			arrayListSprites.remove(sprite.getNumber());
			//System.out.println("borrado el "+sprite.getNumber());
			updateAllRiboon();
		}
	}
}
