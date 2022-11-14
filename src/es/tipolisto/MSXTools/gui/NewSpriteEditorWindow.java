package es.tipolisto.MSXTools.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.tipolisto.MSXTools.gui.pixelEditor.SpriteEditorWindow;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;

public class NewSpriteEditorWindow extends JFrame {
	private static final long serialVersionUID = 4824455430418537328L;
	private JPanel panel;
	private JComboBox<String> comboBoxScreenInEditor, comboBoxTypeSpriteInEditor;
	private byte spriteNumColorsOrScreenMode;
	private byte spriteType;
	public NewSpriteEditorWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 355, 343);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
		//Centramos la ventana en la pantalla
		setLocationRelativeTo(null);
		setTitle("Compress / descompress");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setIconImage(new ImageIcon("data\\icon.png").getImage());

	
		JLabel lblScreen = new JLabel("Screen");
		lblScreen.setBounds(10, 94, 62, 23);
		panel.add(lblScreen);
	
		comboBoxScreenInEditor = new JComboBox<String>();
		comboBoxScreenInEditor.setBounds(128, 94, 165, 22);
		comboBoxScreenInEditor.setBackground(Color.WHITE);
		comboBoxScreenInEditor.setBackground(Color.WHITE);
		//comboBoxScreenInEditor.addItem("SC0");//Si cambias esto entonces cambia el item que sale por defecto en el comboBox, línea 137 de esta clase
		comboBoxScreenInEditor.addItem("SC1");
		comboBoxScreenInEditor.addItem("SC2");
		comboBoxScreenInEditor.addItem("SC3");//Si cambias esto entonces cambia el item que sale por defecto en el comboBox, línea 137 de esta clase
		comboBoxScreenInEditor.addItem("SC4");//Si cambias esto entonces cambia el item que sale por defecto en el comboBox, línea 137 de esta clase
		comboBoxScreenInEditor.addItem("SC5");
		comboBoxScreenInEditor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem=(String)comboBoxScreenInEditor.getSelectedItem();
				if(!selectedItem.equals("SC"+String.valueOf(spriteNumColorsOrScreenMode))) {
					if(comboBoxScreenInEditor.getSelectedItem().equals("SC1")) {
						spriteNumColorsOrScreenMode=1;
					}else if(comboBoxScreenInEditor.getSelectedItem().equals("SC2")) {
						spriteNumColorsOrScreenMode=2;
						//System.out.println("Cambiado a sprite color screen "+spriteNumColorsOrScreenMode);
					}else if(comboBoxScreenInEditor.getSelectedItem().equals("SC3")) {
						spriteNumColorsOrScreenMode=3;
						//System.out.println("Cambiado a sprite color screen "+spriteNumColorsOrScreenMode);
					}else if(comboBoxScreenInEditor.getSelectedItem().equals("SC4")) {
						spriteNumColorsOrScreenMode=4;
						//System.out.println("Cambiado a sprite color screen "+spriteNumColorsOrScreenMode);
					}else if(comboBoxScreenInEditor.getSelectedItem().equals("SC5")) {
						spriteNumColorsOrScreenMode=5;
						//System.out.println("Cambiado a sprite color screen "+spriteNumColorsOrScreenMode);
					}
				}
			}
		});
		panel.add(comboBoxScreenInEditor);
		
		comboBoxTypeSpriteInEditor = new JComboBox();
		comboBoxTypeSpriteInEditor.setBounds(128, 161, 165, 22);
		comboBoxTypeSpriteInEditor.setBackground(Color.WHITE);
		comboBoxTypeSpriteInEditor.addItem("8x8 pixels");
		comboBoxTypeSpriteInEditor.addItem("8x8 pixels augmented");
		comboBoxTypeSpriteInEditor.addItem("16x16 pixels");
		comboBoxTypeSpriteInEditor.addItem("16x16 pixels augmented");
		comboBoxTypeSpriteInEditor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem=(String)comboBoxTypeSpriteInEditor.getSelectedItem();
				byte spriteSize=0;
				switch (selectedItem){
					case "8x8 pixels":
						spriteSize=8;
						break;
					case "8x8 pixels augmented":
						spriteSize=8;
						break;
					case "16x16 pixels":
						spriteSize=16;
					case "16x16 pixels augmented":
						spriteSize=16;
				}
				if(spriteSize!=spriteType) {
					spriteType=spriteSize;
					//System.out.println("Cambiado a sprite tipo "+spriteType);
				}
			}
		});
		panel.add(comboBoxTypeSpriteInEditor);
		
		JLabel lblSpriteSize = new JLabel("Sprite size");
		lblSpriteSize.setBounds(10, 161, 95, 23);
		panel.add(lblSpriteSize);
		
		JLabel lblNewLabel = new JLabel("New Sprite");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(128, 30, 83, 33);
		panel.add(lblNewLabel);
		
		
		JButton btnMake = new JButton("Make");
		btnMake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpriteEditorWindow frame = new SpriteEditorWindow(spriteType,spriteNumColorsOrScreenMode);
				frame.setVisible(true);			
				setVisible(false);
				dispose();
			}
		});
		btnMake.setBounds(122, 237, 89, 23);
		panel.add(btnMake);
		
		
		
		//Ponemos los comboBox en el screen correspondiente
		comboBoxScreenInEditor.setSelectedIndex(4);
		comboBoxTypeSpriteInEditor.setSelectedIndex(2);
		
	}
}
