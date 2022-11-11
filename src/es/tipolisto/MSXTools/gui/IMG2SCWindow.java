package es.tipolisto.MSXTools.gui;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.tipolisto.MSXTools.beans.RGB;
import es.tipolisto.MSXTools.utils.IMG2SC;
import es.tipolisto.MSXTools.utils.MSXPalette;
import es.tipolisto.MSXTools.utils.NumberManager;
import es.tipolisto.MSXTools.utils.PaletteManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map.Entry;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JComboBox;

public class IMG2SCWindow extends JFrame {

	private JPanel contentPane;
	private File fileOrigin;
	private BufferedImage bufferedImageOrigin;
	private IMG2SC img2sc;
	private JButton jButtonColor0,jButtonColor1,jButtonColor2,jButtonColor3,jButtonColor4,jButtonColor5;
	private JButton jButtonColor6,jButtonColor7,jButtonColor8,jButtonColor9,jButtonColor10;
	private JButton jButtonColor11,jButtonColor12,jButtonColor13,jButtonColor14,jButtonColor15;
	private JButton[] jButtons;
	private JTextField textFieldColorRed;
	private JTextField textFieldColorGreen;
	private JTextField textFieldColorBlue;
	private JLabel lalbelHexageximalColor;
	private JCheckBox checkBoxDetectAutomaticColors;
	private JLabel labelMsxColor;
	private JLabel labelSelectedPathFile;
	
	private int paletteSelect;
	private HashMap<MSXPalette, RGB> palettePhilips8255NMS;
	private JLabel jLabelImage;
	private JLabel labelFileOrigin;
	private JLabel labelImageDestiny;
	private int valueProgressBar;
	private JLabel jLabelProgressBar;
	private static JProgressBar progressBar;
	private int similarDistance;
	private JButton buttonReset;
	private JButton btnSeleccionarArchivoPng;
	private JComboBox comboBoxPalettes;
	private JLabel lblNewLabel_5;
	
	private int screen;
	
	/**
	 * Create the frame.
	 */
	public IMG2SCWindow() {
		img2sc=new IMG2SC();
		screen=5;
		palettePhilips8255NMS=PaletteManager.getPalettePhilips8255NMS();
		paletteSelect=0;
		similarDistance=10000;
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1050, 626);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setOpaque(true); 
		contentPane.setLayout(null);
		setResizable(false);
		//Centramos la ventana en la pantalla
		setLocationRelativeTo(null);
		
		labelSelectedPathFile = new JLabel("Path origin file");
		labelSelectedPathFile.setVerticalAlignment(SwingConstants.TOP);
		labelSelectedPathFile.setBounds(600, 244, 415, 14);
		labelSelectedPathFile.setBackground(Color.WHITE);
		contentPane.add(labelSelectedPathFile);

		
		JLabel bmp2Sc5 = new JLabel("IMG 2 SC");
		bmp2Sc5.setFont(new Font("Tahoma", Font.BOLD, 18));
		bmp2Sc5.setBounds(10, 11, 580, 28);
		bmp2Sc5.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(bmp2Sc5);
		
		
		
		labelFileOrigin = new JLabel("");
		labelFileOrigin.setBounds(701, 34, 256, 212);
		contentPane.add(labelFileOrigin);
		
		labelImageDestiny = new JLabel("");
		labelImageDestiny.setBounds(701, 280, 256, 212);
		contentPane.add(labelImageDestiny);

	

		
		
		progressBar = new JProgressBar(0,100);
		progressBar.setBounds(10, 218, 580, 28);
		contentPane.add(progressBar);
		progressBar.setBackground(Color.green);
		progressBar.setVisible(false);
	
		
		jLabelProgressBar = new JLabel("");
		jLabelProgressBar.setBounds(10, 226, 572, 20);
		contentPane.add(jLabelProgressBar);
		

		JButton buttonConvertBMP2SC5 = new JButton("Convert BMP 2 SC5");
		buttonConvertBMP2SC5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileOrigin!=null) {
					String fileExtension=fileOrigin.getName().substring(fileOrigin.getName().length()-3,fileOrigin.getName().length());
					if(fileExtension.equalsIgnoreCase("bmp")) {
						JProgressBarRunnable runnable=new JProgressBarRunnable();
						runnable.setBar(progressBar);
						runnable.setTextProgressBar(jLabelProgressBar);
						Thread hilo=new Thread(runnable);
						if(bufferedImageOrigin!=null && fileOrigin!=null) {
							hilo.start();
							if (screen==5) {
								StringBuilder result=img2sc.associateColorsBMPWithPaletteReturnStringSC5(bufferedImageOrigin,similarDistance);
								try {
									img2sc.createFileWithString(result, fileOrigin.getName());
									runnable.stop();
									JOptionPane.showMessageDialog(null, "File created");
								} catch (IOException e1) {
									runnable.stop();
									JOptionPane.showMessageDialog(null, "There was a problem and the image could not be created");
									System.out.println("There was a problem and the image could not be created: "+e1);
								}
							}else if (screen==2) {
								
							}else {
								JOptionPane.showMessageDialog(null, "select the screen");
							}
							
						}else {
							JOptionPane.showMessageDialog(null, "Choose a file");
						}	
					}else {
						JOptionPane.showMessageDialog(null, "The image is not BMP format");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Choose a file");
				}
			}
		});
		buttonConvertBMP2SC5.setBounds(10, 126, 580, 35);
		buttonConvertBMP2SC5.setVisible(false);
		contentPane.add(buttonConvertBMP2SC5);
		
		
		
		
		
		
		JButton buttonConvertPNG2SC5 = new JButton("Convert PNG 2 SC5");
		buttonConvertPNG2SC5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileOrigin!=null) {
					String fileExtension=fileOrigin.getName().substring(fileOrigin.getName().length()-3,fileOrigin.getName().length());
					if(fileExtension.equalsIgnoreCase("PNG")) {						
						if(screen==5) {
							StringBuilder result=img2sc.associateColorsPNGWithPaletteReturnStringSC5(bufferedImageOrigin,similarDistance);
							try {
								img2sc.createFileWithString(result, fileOrigin.getName());
								JOptionPane.showMessageDialog(null, "File created");
							} catch (IOException e1) {
								JOptionPane.showMessageDialog(null, "There was a problem and the image could not be created");
								System.out.println("There was a problem and the image could not be created: "+e1);
							}
						}else if (screen==2) {
							
						}else {
							JOptionPane.showMessageDialog(null, "select the screen");
						}
						
					}else {
						JOptionPane.showMessageDialog(null, "The image is not PNG format");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Choose a file");
				}
			}
		});
		buttonConvertPNG2SC5.setBounds(10, 126, 580, 35);
		buttonConvertPNG2SC5.setVisible(false);
		contentPane.add(buttonConvertPNG2SC5);
		
		
		JComboBox comboBoxFormat = new JComboBox();
		comboBoxFormat.setBounds(123, 93, 467, 22);
		comboBoxFormat.addItem("BMP");
		comboBoxFormat.addItem("PNG");
		contentPane.add(comboBoxFormat);
		comboBoxFormat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBoxFormat.getSelectedItem().equals("BMP")) {
					JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir"));
					jFileChooser.setDialogTitle("Selecciona un archivo");
					int result=jFileChooser.showSaveDialog(null);
					if(result==JFileChooser.APPROVE_OPTION) {
						fileOrigin=jFileChooser.getSelectedFile();
						String fileExtension=fileOrigin.getName().substring(fileOrigin.getName().length()-3,fileOrigin.getName().length());
						if(fileExtension.equalsIgnoreCase("BMP")) {	
							labelSelectedPathFile.setText(fileOrigin.getPath().toString());
							bufferedImageOrigin=img2sc.getBufferedImage(fileOrigin);
							labelFileOrigin.setIcon(new ImageIcon(bufferedImageOrigin));
							BufferedImage bufferedImageDestiny=img2sc.associateColorsBMPWithPalette(bufferedImageOrigin, similarDistance);
							labelImageDestiny.setIcon(new ImageIcon(bufferedImageDestiny));
							buttonConvertBMP2SC5.setVisible(true);
							buttonConvertPNG2SC5.setVisible(false);
						}else {
							JOptionPane.showMessageDialog(null, "The image is not BMP format");
						}
					}
				}else if(comboBoxFormat.getSelectedItem().equals("PNG")) {
					JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir"));
					jFileChooser.setDialogTitle("Selecciona un archivo");
					int result=jFileChooser.showSaveDialog(null);
					if(result==JFileChooser.APPROVE_OPTION) {
						fileOrigin=jFileChooser.getSelectedFile();
						String fileExtension=fileOrigin.getName().substring(fileOrigin.getName().length()-3,fileOrigin.getName().length());
						if(fileExtension.equalsIgnoreCase("PNG")) {	
							bufferedImageOrigin=img2sc.getBufferedImage(fileOrigin);
							labelFileOrigin.setIcon(new ImageIcon(bufferedImageOrigin));
							BufferedImage bufferedImageDestiny=img2sc.associateColorsPNGWithPalette(bufferedImageOrigin, similarDistance);
							labelImageDestiny.setIcon(new ImageIcon(bufferedImageDestiny));
							buttonConvertPNG2SC5.setVisible(true);
							buttonConvertBMP2SC5.setVisible(false);
						}else {
							JOptionPane.showMessageDialog(null, "The image is not PNG format");
						}
					}
				}
			}
		});

		
		
		JLabel lblNewLabel_4 = new JLabel("Select file:");
		lblNewLabel_4.setBounds(10, 97, 113, 14);
		contentPane.add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("Select palette");
		lblNewLabel_5.setBounds(10, 269, 91, 14);
		contentPane.add(lblNewLabel_5);
		
		comboBoxPalettes = new JComboBox();
		comboBoxPalettes.setBounds(123, 265, 467, 22);
		comboBoxPalettes.addItem("Philips 8255 NMS");
		comboBoxPalettes.addItem("TMS9918 1");
		comboBoxPalettes.addItem("TMS9219 2");
		comboBoxPalettes.addItem("TOSHIBA");
		comboBoxPalettes.addItem("V9938");
		contentPane.add(comboBoxPalettes);
		

		

		JSlider slider = new JSlider(1000,50000);
		slider.setBounds(743, 528, 188, 51);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(fileOrigin!=null) {
					similarDistance=slider.getValue();
					bufferedImageOrigin=img2sc.getBufferedImage(fileOrigin);
					labelFileOrigin.setIcon(new ImageIcon(bufferedImageOrigin));
					BufferedImage bufferedImageDestiny=img2sc.associateColorsBMPWithPalette(bufferedImageOrigin,similarDistance );
					labelImageDestiny.setIcon(new ImageIcon(bufferedImageDestiny));
				}else {
					JOptionPane.showMessageDialog(null, "Select a file.");
				}
				
			}
		});
		contentPane.add(slider);
		
		JLabel lblNewLabel_3 = new JLabel("Ajustar detecci\u00F3n");
		lblNewLabel_3.setBounds(814, 514, 104, 14);
		contentPane.add(lblNewLabel_3);
		
		JButton buttonTakePicture = new JButton(new ImageIcon("assets\\take_picture.png"));
		buttonTakePicture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileOrigin!=null) {
					ImageIcon imageIcon=new ImageIcon();
					imageIcon=(ImageIcon) labelImageDestiny.getIcon();
					BufferedImage buffered =(BufferedImage)imageIcon.getImage();
					img2sc.createFilePNGImage(buffered, fileOrigin);
					JOptionPane.showMessageDialog(null, "Created image.");
				}else {
					JOptionPane.showMessageDialog(null, "Select a file.");
				}
			}
		});
		buttonTakePicture.setBounds(673, 528, 60, 45);
		contentPane.add(buttonTakePicture);
		
		buttonReset = new JButton("Reset");
		buttonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slider.setValue(10000);
				bufferedImageOrigin=img2sc.getBufferedImage(fileOrigin);
				labelFileOrigin.setIcon(new ImageIcon(bufferedImageOrigin));
				BufferedImage bufferedImageDestiny=img2sc.associateColorsBMPWithPalette(bufferedImageOrigin,10000 );
				labelImageDestiny.setIcon(new ImageIcon(bufferedImageDestiny));
			}
		});
		buttonReset.setBounds(941, 528, 83, 51);
		contentPane.add(buttonReset);
		
		JButton buttonCreateNewPaltte = new JButton("Create new Palette");
		buttonCreateNewPaltte.setBounds(10, 294, 580, 23);
		contentPane.add(buttonCreateNewPaltte);
		
		JButton buttonConvertBMP2SC2 = new JButton("Convert BMP 2 SC2");
		buttonConvertBMP2SC2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		buttonConvertBMP2SC2.setBounds(10, 172, 580, 35);
		buttonConvertBMP2SC2.setVisible(false);
		contentPane.add(buttonConvertBMP2SC2);
		
		JButton buttonConvertPNG2SC2 = new JButton("Convert PGN 2 SC2");
		buttonConvertPNG2SC2.setBounds(10, 172, 580, 35);
		buttonConvertPNG2SC2.setVisible(false);
		contentPane.add(buttonConvertPNG2SC2);
		
		JLabel lblNewLabel_6 = new JLabel("Screen");
		lblNewLabel_6.setBounds(10, 61, 58, 14);
		contentPane.add(lblNewLabel_6);
		
		JComboBox comboBoxScreen = new JComboBox();
		comboBoxScreen.addItem("5");
		comboBoxScreen.addItem("2");
		comboBoxScreen.setBounds(123, 57, 467, 22);
		comboBoxScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonConvertBMP2SC2.setVisible(false);
				buttonConvertPNG2SC2.setVisible(false);
				buttonConvertBMP2SC5.setVisible(false);
				buttonConvertPNG2SC5.setVisible(false);
				String selected=comboBoxScreen.getSelectedItem().toString();
				switch(selected) {
					case "2":
						screen=2;
					break;
					case "5":
						screen=5;
					break;
				}
				if(comboBoxScreen.getSelectedItem().equals("2")) {
					screen=2;
				}else if(comboBoxScreen.getSelectedItem().equals("2")) {
					screen=2;
				}
			}
		});
		contentPane.add(comboBoxScreen);
		
		
		textFieldColorRed = new JTextField();
		textFieldColorRed.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldColorRed.setText("0");
		textFieldColorRed.setBounds(33, 445, 167, 20);
		contentPane.add(textFieldColorRed);
		textFieldColorRed.setColumns(10);
		textFieldColorRed.getDocument().addDocumentListener(new DocumentListener() {
			  	  public void changedUpdate(DocumentEvent e) {
				  	 
				  }
				  public void removeUpdate(DocumentEvent e) {
					 
				  }
				  public void insertUpdate(DocumentEvent e) {
					  if(!textFieldColorRed.getText().toString().isEmpty()) {
						  String rgbComponent="red";
						  changeRGBOnPalette(rgbComponent);
					  }	  
				  }
		});

		textFieldColorGreen = new JTextField();
		textFieldColorGreen.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldColorGreen.setText("0");
		textFieldColorGreen.setColumns(10);
		textFieldColorGreen.setBounds(210, 445, 197, 20);
		contentPane.add(textFieldColorGreen);
		textFieldColorGreen.getDocument().addDocumentListener(new DocumentListener() {
		  	  public void changedUpdate(DocumentEvent e) {
				  	 
			  }
			  public void removeUpdate(DocumentEvent e) {
				 
			  }
			  public void insertUpdate(DocumentEvent e) {
				  if(!textFieldColorGreen.getText().toString().isEmpty()) {
					  String rgbComponent="green";
					  changeRGBOnPalette(rgbComponent);
				  }	
			  }
		});

		
		textFieldColorBlue = new JTextField();
		textFieldColorBlue.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldColorBlue.setText("0");
		textFieldColorBlue.setColumns(10);
		textFieldColorBlue.setBounds(417, 445, 173, 20);
		contentPane.add(textFieldColorBlue);
		textFieldColorBlue.getDocument().addDocumentListener(new DocumentListener() {
		  	  public void changedUpdate(DocumentEvent e) {
				  	 
			  }
			  public void removeUpdate(DocumentEvent e) {
				 
			  }
			  public void insertUpdate(DocumentEvent e) {
				  if(!textFieldColorBlue.getText().toString().isEmpty()) {
					  String rgbComponent="blue";
					  changeRGBOnPalette(rgbComponent);
				  }	 
			  }
		});
		
		JLabel lblNewLabel = new JLabel("R");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(110, 411, 26, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblG = new JLabel("G");
		lblG.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblG.setBounds(299, 411, 26, 23);
		contentPane.add(lblG);
		
		JLabel lblB = new JLabel("B");
		lblB.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblB.setBounds(492, 411, 26, 23);
		contentPane.add(lblB);
		
		lalbelHexageximalColor = new JLabel("00ffffff");
		lalbelHexageximalColor.setBounds(279, 489, 46, 14);
		contentPane.add(lalbelHexageximalColor);
		
		labelMsxColor = new JLabel("1,0,0,0");
		labelMsxColor.setBounds(279, 514, 46, 14);
		contentPane.add(labelMsxColor);
		
		checkBoxDetectAutomaticColors = new JCheckBox("Trabajar con colores indexados");
		checkBoxDetectAutomaticColors.setBounds(6, 547, 234, 23);
		contentPane.add(checkBoxDetectAutomaticColors);
		
		JLabel lblNewLabel_1 = new JLabel("Origin File");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(600, 21, 424, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Destiny file");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(628, 269, 396, 14);
		contentPane.add(lblNewLabel_2);
		

		
		initializeColorButtons();

	}

		
	/*
	private void aumentarProgressBar() {
		progressBar.setValue(50);
		
	}*/
	
	
	
	
	
	
	
	
	
	
	
	private void initializeColorButtons() {
		contentPane.setLayout(null);
		jButtonColor0 = new JButton("0");
		RGB rgbTransparent=palettePhilips8255NMS.get(MSXPalette.TRANSPARENTE);
		Color colorButtonTransparent=new Color(rgbTransparent.getRed(),rgbTransparent.getGreen(),rgbTransparent.getBlue());
		jButtonColor0.setBackground(colorButtonTransparent);
		jButtonColor0.setForeground(Color.WHITE);
		jButtonColor0.setOpaque(true);
		//jButtonColor0.setBorderPainted(false);
		jButtonColor0.setBounds(10, 343, 69, 23);
		contentPane.add(jButtonColor0);
		jButtonColor0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=0;
				setTextFieldColors(rgbTransparent,0);

			}
		});
		
		
		jButtonColor1 = new JButton("1");
		RGB rgbNegro=palettePhilips8255NMS.get(MSXPalette.TRANSPARENTE);
		Color colorNegro=new Color(rgbNegro.getRed(),rgbNegro.getGreen(),rgbNegro.getBlue());
		jButtonColor1.setBackground(colorNegro);
		jButtonColor1.setForeground(new Color(255, 255, 255));
		jButtonColor1.setOpaque(true);
		//jButtonColor1.setBorderPainted(false);
		jButtonColor1.setBounds(87, 343, 60, 23);
		contentPane.add(jButtonColor1);
		jButtonColor1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=1;
				setTextFieldColors(rgbNegro, 1);

			}
		});
		
		
		jButtonColor2 = new JButton("2");
		jButtonColor2.setForeground(new Color(255, 255, 255));
		RGB rgbVerdeMedio=palettePhilips8255NMS.get(MSXPalette.VERDE_MEDIO);
		Color colorVerdeMedio=new Color(rgbVerdeMedio.getRed(),rgbVerdeMedio.getGreen(),rgbVerdeMedio.getBlue());
		jButtonColor2.setBackground(colorVerdeMedio);
		jButtonColor2.setOpaque(true);
		//jButtonColor2.setBorderPainted(false);
		jButtonColor2.setBounds(161, 343, 60, 23);
		contentPane.add(jButtonColor2);
		jButtonColor2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=2;
				setTextFieldColors(rgbVerdeMedio,2);

			}
		});
		

		jButtonColor3 = new JButton("3");
		RGB rgbVerdeClaro=palettePhilips8255NMS.get(MSXPalette.VERDE_CLARO);
		Color colorVerdeClaro=new Color(rgbVerdeClaro.getRed(),rgbVerdeClaro.getGreen(),rgbVerdeClaro.getBlue());
		jButtonColor3.setBackground(colorVerdeClaro);
		jButtonColor3.setOpaque(true);
		//jButtonColor3.setBorderPainted(false);
		jButtonColor3.setBounds(229, 343, 62, 23);
		contentPane.add(jButtonColor3);
		jButtonColor3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=3;
				setTextFieldColors(rgbVerdeClaro,3);

			}
		});
		
		jButtonColor4 = new JButton("4");
		jButtonColor4.setForeground(new Color(255, 255, 255));
		RGB rgbAzulOscuro=palettePhilips8255NMS.get(MSXPalette.AZUL_OSCURO);
		Color colorAzulOscuro=new Color(rgbAzulOscuro.getRed(),rgbAzulOscuro.getGreen(),rgbAzulOscuro.getBlue());
		jButtonColor4.setBackground(colorAzulOscuro);
		jButtonColor4.setOpaque(true);
		//jButtonColor4.setBorderPainted(false);
		jButtonColor4.setBounds(299, 343, 71, 23);
		contentPane.add(jButtonColor4);
		jButtonColor4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=4;
				setTextFieldColors(rgbAzulOscuro,4);

			}
		});
		
		jButtonColor5 = new JButton("5");
		RGB rgbAzulMedio=palettePhilips8255NMS.get(MSXPalette.AZUL_MEDIO);
		Color colorAzulMedio=new Color(rgbAzulMedio.getRed(),rgbAzulMedio.getGreen(),rgbAzulMedio.getBlue());
		jButtonColor5.setBackground(colorAzulMedio);
		jButtonColor5.setForeground(new Color(255, 255, 255));
		jButtonColor5.setOpaque(true);
		//jButtonColor5.setBorderPainted(false);
		jButtonColor5.setBounds(382, 343, 62, 23);
		contentPane.add(jButtonColor5);
		jButtonColor5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=5;
				setTextFieldColors(rgbAzulMedio,5);

			}
		});
		
		jButtonColor6 = new JButton("6");
		jButtonColor6.setForeground(new Color(255, 255, 255));
		RGB rgbRojoOscuro=palettePhilips8255NMS.get(MSXPalette.ROJO_OSCURO);
		Color colorRojoOscuro=new Color(rgbRojoOscuro.getRed(),rgbRojoOscuro.getGreen(),rgbRojoOscuro.getBlue());
		jButtonColor6.setBackground(colorRojoOscuro);
		jButtonColor6.setOpaque(true);
		//jButtonColor6.setBorderPainted(false);
		jButtonColor6.setBounds(454, 343, 64, 23);
		contentPane.add(jButtonColor6);
		jButtonColor6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=6;
				setTextFieldColors(rgbRojoOscuro,6);

			}
		});
		
		jButtonColor7 = new JButton("7");
		RGB rgbAzulClaro=palettePhilips8255NMS.get(MSXPalette.AZUL_CLARO);
		Color colorAzulClaro=new Color(rgbAzulClaro.getRed(),rgbAzulClaro.getGreen(),rgbAzulClaro.getBlue());
		jButtonColor7.setBackground(colorAzulClaro);
		jButtonColor7.setOpaque(true);
		jButtonColor7.setForeground(new Color(0, 0, 0));
		//jButtonColor7.setBorderPainted(false);
		jButtonColor7.setBounds(528, 343, 62, 23);
		contentPane.add(jButtonColor7);
		jButtonColor7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=7;
				setTextFieldColors(rgbAzulClaro,7);

			}
		});
		
		jButtonColor8 = new JButton("6");
		RGB rgbRojoMedio=palettePhilips8255NMS.get(MSXPalette.ROJO_MEDIO);
		Color colorRojoMedio=new Color(rgbRojoMedio.getRed(),rgbRojoMedio.getGreen(),rgbRojoMedio.getBlue());
		jButtonColor8.setBackground(colorRojoMedio);
		jButtonColor8.setOpaque(true);
		jButtonColor8.setForeground(Color.WHITE);
		//jButtonColor8.setBorderPainted(false);
		jButtonColor8.setBounds(8, 377, 71, 23);
		contentPane.add(jButtonColor8);
		jButtonColor8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=8;
				setTextFieldColors(rgbRojoMedio,8);

			}
		});
		
		jButtonColor9 = new JButton("9");
		RGB rgbRojoClaro=palettePhilips8255NMS.get(MSXPalette.ROJO_CLARO);
		Color colorRojoClaro=new Color(rgbRojoClaro.getRed(),rgbRojoClaro.getGreen(),rgbRojoClaro.getBlue());
		jButtonColor9.setBackground(colorRojoClaro);
		jButtonColor9.setOpaque(true);
		jButtonColor9.setForeground(new Color(0, 0, 0));
		//jButtonColor9.setBorderPainted(false);
		jButtonColor9.setBounds(89, 377, 58, 23);
		contentPane.add(jButtonColor9);
		jButtonColor9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=9;
				setTextFieldColors(rgbRojoClaro,9);
	
			}
		});
		
		jButtonColor10 = new JButton("10");
		RGB rgbAmarilloOscuro=palettePhilips8255NMS.get(MSXPalette.AMARILLO_OSCURO);
		Color colorAmarilloOscuro=new Color(rgbAmarilloOscuro.getRed(),rgbAmarilloOscuro.getGreen(),rgbAmarilloOscuro.getBlue());
		jButtonColor10.setBackground(colorAmarilloOscuro);
		jButtonColor10.setOpaque(true);
		jButtonColor10.setForeground(new Color(0, 0, 0));
		//jButtonColor10.setBorderPainted(false);
		jButtonColor10.setBounds(159, 377, 62, 23);
		contentPane.add(jButtonColor10);
		jButtonColor10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=10;
				setTextFieldColors(rgbAmarilloOscuro,10);

			}
		});
		
		jButtonColor11 = new JButton("11");
		RGB rgbAmarilloClaro=palettePhilips8255NMS.get(MSXPalette.AMARILLO_CLARO);
		Color colorAmarilloClaro=new Color(rgbAmarilloClaro.getRed(),rgbAmarilloClaro.getGreen(),rgbAmarilloClaro.getBlue());
		jButtonColor11.setBackground(colorAmarilloClaro);
		jButtonColor11.setOpaque(true);
		jButtonColor11.setForeground(new Color(0, 0, 0));
		//jButtonColor11.setBorderPainted(false);
		jButtonColor11.setBounds(229, 377, 62, 23);
		contentPane.add(jButtonColor11);
		jButtonColor11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=11;
				setTextFieldColors(rgbAmarilloClaro,11);

			}
		});
		
		jButtonColor12 = new JButton("12");
		RGB rgbVerdeOscuro=palettePhilips8255NMS.get(MSXPalette.VERDE_OSCURO);
		Color colorVerdeOscuro=new Color(rgbVerdeOscuro.getRed(),rgbVerdeOscuro.getGreen(),rgbVerdeOscuro.getBlue());
		jButtonColor12.setBackground(colorVerdeOscuro);
		jButtonColor12.setOpaque(true);
		jButtonColor12.setForeground(new Color(0, 0, 0));
		//jButtonColor12.setBorderPainted(false);
		jButtonColor12.setBounds(299, 377, 71, 23);
		contentPane.add(jButtonColor12);
		jButtonColor12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=12;
				setTextFieldColors(rgbVerdeOscuro,12);

			}
		});
		
		jButtonColor13 = new JButton("13");
		RGB rgbVioleta=palettePhilips8255NMS.get(MSXPalette.VIOLETA);
		Color colorVioleta=new Color(rgbVioleta.getRed(),rgbVioleta.getGreen(),rgbVioleta.getBlue());
		jButtonColor13.setBackground(colorVioleta);
		jButtonColor13.setOpaque(true);
		jButtonColor13.setForeground(Color.WHITE);
		//jButtonColor13.setBorderPainted(false);
		jButtonColor13.setBounds(382, 377, 62, 23);
		contentPane.add(jButtonColor13);
		jButtonColor13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=13;
				setTextFieldColors(rgbVioleta,13);

			}
		});
		
		jButtonColor14 = new JButton("14");
		RGB rgbGris=palettePhilips8255NMS.get(MSXPalette.GRIS);
		Color colorGris=new Color(rgbGris.getRed(),rgbGris.getGreen(),rgbGris.getBlue());
		jButtonColor14.setBackground(colorGris);
		jButtonColor14.setOpaque(true);
		jButtonColor14.setForeground(Color.WHITE);
		//jButtonColor14.setBorderPainted(false);
		jButtonColor14.setBounds(454, 377, 63, 23);
		contentPane.add(jButtonColor14);
		jButtonColor14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=14;
				setTextFieldColors(rgbGris,14);

			}
		});
		
		jButtonColor15 = new JButton("15");
		RGB rgbBlanco=palettePhilips8255NMS.get(MSXPalette.BLANCO);
		Color colorBlanco=new Color(rgbBlanco.getRed(),rgbBlanco.getGreen(),rgbBlanco.getBlue());
		jButtonColor15.setBackground(colorBlanco);
		jButtonColor15.setOpaque(true);
		jButtonColor15.setForeground(new Color(0, 0, 0));
		//jButtonColor15.setBorderPainted(false);
		jButtonColor15.setBounds(528, 378, 62, 23);
		contentPane.add(jButtonColor15);
		jButtonColor15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paletteSelect=15;
				setTextFieldColors(rgbBlanco,15);

			}
		});
		
		jButtons=new JButton[16];
		jButtons[0]=jButtonColor0;
		jButtons[1]=jButtonColor1;
		jButtons[2]=jButtonColor2;
		jButtons[3]=jButtonColor3;
		jButtons[4]=jButtonColor4;
		jButtons[5]=jButtonColor5;
		jButtons[6]=jButtonColor6;
		jButtons[7]=jButtonColor7;
		jButtons[8]=jButtonColor8;
		jButtons[9]=jButtonColor9;
		jButtons[10]=jButtonColor10;
		jButtons[11]=jButtonColor11;
		jButtons[12]=jButtonColor12;
		jButtons[13]=jButtonColor13;
		jButtons[14]=jButtonColor14;
		jButtons[15]=jButtonColor15;
		
		JButton jButtonResetColors = new JButton("Reset colors");
		jButtonResetColors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetPalette();
			}
		});
		jButtonResetColors.setBounds(448, 542, 142, 23);
		contentPane.add(jButtonResetColors);
		
		


		
		
		
		

	}
	
	private void setTextFieldColors(RGB rgb, int selection) {
		for(int i=0;i<jButtons.length;i++) {
			JButton jbutton=jButtons[i];
			if(i==selection)jbutton.setBorder(new LineBorder(Color.BLACK));
			else jbutton.setBorder(new LineBorder(Color.WHITE));
		}
		textFieldColorRed.setText(String.valueOf(rgb.getRed()));
		textFieldColorGreen.setText(String.valueOf(rgb.getGreen()));
		textFieldColorBlue.setText(String.valueOf(rgb.getBlue()));
		
		//color hexagesimal
		Color color=new Color(rgb.getRed(),rgb.getGreen(),rgb.getBlue());
		String hexagesimal=Integer.toHexString( color.getRGB() & 0x00ffffff );
		lalbelHexageximalColor.setText(hexagesimal);

		//Color MSX
		int MSXColorRed=(rgb.getRed()*7)/255;
		int MSXColorGreen=(rgb.getGreen()*7)/255;
		int MSXColorBlue=(rgb.getBlue()*7)/255;
		labelMsxColor.setText(selection+","+MSXColorRed+","+MSXColorGreen+","+MSXColorBlue);
		
	}
	
	private void changeRGBOnPalette(String rgbComponent) {
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			if(paletteSelect==entry.getKey().ordinal()) {
				if (rgbComponent.equals("red")) entry.getValue().setRed(Integer.valueOf(textFieldColorRed.getText()));
				if (rgbComponent.equals("green")) entry.getValue().setGreen(Integer.valueOf(textFieldColorGreen.getText()));
				if (rgbComponent.equals("blue")) entry.getValue().setBlue(Integer.valueOf(textFieldColorBlue.getText()));
				jButtons[paletteSelect].setBackground(new Color(entry.getValue().getRed(),entry.getValue().getGreen(),entry.getValue().getBlue()));
			}
		}
		
	}
	
	private void resetPalette() {
		RGB[] rgbRGB8255NMS=PaletteManager.getRGB8255NMS();
		int i=0;
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			switch(entry.getKey()) {
			case TRANSPARENTE:
				entry.getValue().setRed(rgbRGB8255NMS[0].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[0].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[0].getBlue());
				break;
			case NEGRO:
				entry.getValue().setRed(rgbRGB8255NMS[1].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[1].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[1].getBlue());
				break;
			case VERDE_MEDIO:
				entry.getValue().setRed(rgbRGB8255NMS[2].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[2].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[2].getBlue());
				break;
			case VERDE_CLARO:
				entry.getValue().setRed(rgbRGB8255NMS[3].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[3].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[3].getBlue());
				break;
			case AZUL_OSCURO:
				entry.getValue().setRed(rgbRGB8255NMS[4].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[4].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[4].getBlue());
				break;
			case AZUL_MEDIO:
				entry.getValue().setRed(rgbRGB8255NMS[5].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[5].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[5].getBlue());
				break;
			case ROJO_OSCURO:
				entry.getValue().setRed(rgbRGB8255NMS[6].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[6].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[6].getBlue());
				break;
			case AZUL_CLARO:
				entry.getValue().setRed(rgbRGB8255NMS[7].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[7].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[7].getBlue());
				break;
			case ROJO_MEDIO:
				entry.getValue().setRed(rgbRGB8255NMS[8].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[8].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[8].getBlue());
				break;
			case ROJO_CLARO:
				entry.getValue().setRed(rgbRGB8255NMS[9].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[9].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[9].getBlue());
				break;
			case AMARILLO_OSCURO:
				entry.getValue().setRed(rgbRGB8255NMS[10].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[10].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[10].getBlue());
				break;
			case AMARILLO_CLARO:
				entry.getValue().setRed(rgbRGB8255NMS[11].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[11].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[11].getBlue());
				break;
			case VERDE_OSCURO:
				entry.getValue().setRed(rgbRGB8255NMS[12].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[12].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[12].getBlue());
				break;
			case VIOLETA:
				entry.getValue().setRed(rgbRGB8255NMS[13].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[13].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[13].getBlue());
				break;
			case GRIS:
				entry.getValue().setRed(rgbRGB8255NMS[14].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[14].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[14].getBlue());
				break;
			case BLANCO:
				entry.getValue().setRed(rgbRGB8255NMS[15].getRed());
				entry.getValue().setGreen(rgbRGB8255NMS[15].getGreen());
				entry.getValue().setBlue(rgbRGB8255NMS[15].getBlue());
				break;
			}		
			i++;
		}
		JOptionPane.showMessageDialog(null, "Restored colors");
		for (Entry<MSXPalette, RGB> entry : palettePhilips8255NMS.entrySet()) {
			jButtons[entry.getKey().ordinal()].setBackground(new Color(entry.getValue().getRed(),entry.getValue().getGreen(),entry.getValue().getBlue()));
		}
	}
}
