package es.tipolisto.MSXTools.gui;


import java.awt.Color;
import java.awt.Font;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import es.tipolisto.MSXTools.beans.RGB;
import es.tipolisto.MSXTools.beans.Tile;
import es.tipolisto.MSXTools.utils.IMG2SC;
import es.tipolisto.MSXTools.utils.MSXPalette;
import es.tipolisto.MSXTools.utils.PaletteManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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




	private JCheckBox checkBoxDetectAutomaticColors;

	

	
	private JLabel jLabelImage;
	private JLabel labelFileOrigin;
	private JLabel labelImageDestiny;
	private JLabel labelSelectedPathFile;
	private int valueProgressBar;
	private static JProgressBar progressBar;
	private int similarDistance;
	private JButton buttonReset;
	private JButton btnSeleccionarArchivoPng;

	
	private int screen;
	
	/**
	 * Create the frame.
	 */
	public IMG2SCWindow() {
		img2sc=new IMG2SC();
		screen=5;
		
		
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
		setIconImage(new ImageIcon("data\\icon.png").getImage());
		
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
		progressBar.setBounds(10, 302, 580, 28);
		contentPane.add(progressBar);
		progressBar.setBackground(Color.green);
		progressBar.setVisible(false);
		

		JButton btnNewButton = new JButton("Select file");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir"));
				FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("png & bmp Images", "png", "bmp");
				jFileChooser.setFileFilter(imgFilter);
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
					}else if(fileExtension.equalsIgnoreCase("PNG")) {	
						bufferedImageOrigin=img2sc.getBufferedImage(fileOrigin);
						labelFileOrigin.setIcon(new ImageIcon(bufferedImageOrigin));
						BufferedImage bufferedImageDestiny=img2sc.associateColorsPNGWithPalette(bufferedImageOrigin, similarDistance);
						labelImageDestiny.setIcon(new ImageIcon(bufferedImageDestiny));
					}else {
						JOptionPane.showMessageDialog(null, "The image is not PNG or BMP format");
					}
				}
			}
		});
		btnNewButton.setBounds(121, 82, 469, 23);
		contentPane.add(btnNewButton);

		

		

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
		
		JLabel lblNewLabel_6 = new JLabel("Screen");
		lblNewLabel_6.setBounds(10, 149, 58, 14);
		contentPane.add(lblNewLabel_6);
		
		JComboBox comboBoxScreen = new JComboBox();
		comboBoxScreen.addItem("5");
		comboBoxScreen.addItem("2");
		comboBoxScreen.setBounds(123, 145, 467, 22);
		comboBoxScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

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
		
		JButton btnConfigurePalette = new JButton("Configure palette");
		btnConfigurePalette.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PaletteManagerWindow frame = new PaletteManagerWindow();
				frame.setVisible(true);
			}
		});
		btnConfigurePalette.setBounds(10, 457, 580, 35);
		contentPane.add(btnConfigurePalette);
		
		JButton btnCovert = new JButton("Convert!!!");
		btnCovert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				convertFileOrigin();
				
			}
		});
		btnCovert.setBounds(10, 377, 580, 35);
		contentPane.add(btnCovert);
		

		

		
		

	}
	
	
	
	void convertFileOrigin() {
		if(fileOrigin!=null) {
			String fileExtension=fileOrigin.getName().substring(fileOrigin.getName().length()-3,fileOrigin.getName().length());
			if(fileExtension.equalsIgnoreCase("PNG")) {		
				//Covertimos cada pixel de la foto en su color más cercano de la palets
				StringBuilder result=img2sc.getPNGIndexedhexadecimalStringBuilder(bufferedImageOrigin,similarDistance);
				if(screen==5) {
					try {
						img2sc.createFileWithString(result, fileOrigin.getAbsolutePath(),fileOrigin.getName());
						JOptionPane.showMessageDialog(null, "File created");
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "There was a problem and the image could not be created");
						System.out.println("There was a problem and the image could not be created: "+e1);
					}
				}else if (screen==2) {
					/*Tile[][] bank0=new Tile[8][32];
					Tile[][] bank1=new Tile[8][32];
					Tile[][] bank2=new Tile[8][32];*/

					//char[][] tiles=new char[256][256];
					int row=0;
					int column=0;
					int i=0;
					int tile=0;
					StringBuilder tileString=new StringBuilder();
					//int column=0;
					//Procesamos los datos ya que cada 8 bytes es un tile 
					//longitud: 54272: 
					//256*256=65536
					//Paa obtener los tiles 1 obtenemos las columnas
					
					
					//for (column = 0; column < 256; column++) {
					while(row<256) {
						column++;
						if(column %8 ==0) {
							column=0;
							row++;
						}
						if(row==8) {
							column+=9;
							row=0;
						}
						if(row==0)tileString.append(result.charAt(column));
						else tileString.append(result.charAt(column+(256*row)));
						
						/*for(int ti=1;ti<16;ti++) {
							colors[row+ti][column]=result.charAt(column+(256*ti));
						}*/
						//Cada 256 px será otra línea del tile 
						
						//for (row=0;row<8*8;row++) {
							/*if(row==0)tiles[row][column]=result.charAt(column);
							else tiles[row][column]=result.charAt(column+(256*row);*/
							//if(row==0)tileString.append(result.charAt(column));
							//else tileString.append(result.charAt(column+(56*row)));
						//}
						/*tiles[row][column]=result.charAt(column);
						tiles[row+1][column]=result.charAt(column+256);
						tiles[row+2][column]=result.charAt(column+(256*2));
						tiles[row+3][column]=result.charAt(column+(256*3));
						tiles[row+4][column]=result.charAt(column+(256*4));
						tiles[row+5][column]=result.charAt(column+(256*5));
						tiles[row+6][column]=result.charAt(column+(256*6));
						tiles[row+7][column]=result.charAt(column+(256*7));*/
						//tileString.append(result.charAt(column+(256*row)));
						/*tileString.append(result.charAt(column+256));
						tileString.append(result.charAt(column+(256*2)));
						tileString.append(result.charAt(column+(256*3)));
						tileString.append(result.charAt(column+(256*4)));
						tileString.append(result.charAt(column+(256*5)));
						tileString.append(result.charAt(column+(256*6)));
						tileString.append(result.charAt(column+(256*7)));*/

					}
					System.out.println(tileString.toString());
					/*
					int []numeroCaracteres=new int[8];
					char [] caracterSeleccionado=new char[8];
					String caracteresProhibidos="";
					String caracterMasrepetido1="";
					String caracterMasrepetido2="";
					int temp;
					//Fromateamos el tileString para que cada 8 letras solo se repitan 2 colores
					for (i=0;i<tileString.length();i++) {
						if (i % 8==0 && i!=0) {
							String ochoLetras=tileString.substring(i-8,i);
							for (int x=0;x<8;x++) {
								char caracter=ochoLetras.charAt(x);
								if(caracteresProhibidos.indexOf(caracter)==-1) {
									caracteresProhibidos+=caracter;
									numeroCaracteres[x]=contarCaracteres(ochoLetras,caracter);
									caracterSeleccionado[x]=caracter;
								}else {
									numeroCaracteres[x]=0;
								}
							}
						}
					}
					System.out.println(caracterSeleccionado);
					tileString.replace(i-8, i, caracterSeleccionado.toString());
						*/
					
					
					
					
					
					byte[] data = new byte[0x4000];
					/*
					La parte del mapa es:
						6144 / h1800 -> 6911 / h1aff
					La parte de definicion de tiles es:
						banco 3: 4096 / h1000 -> 6148 /h17ff
						banco 2: 2048 / h0800 -> 4095 / h0fff
						banco 1: 0 / h0000 -> 2047 / h07ff
					La parte del color es:
						banco 3: 12288 / h3000 -> 14335 / h37ff
						banco 2: 10240 / h2800 -> 12287 / h2fff
						banco 1: 8192 / h2000 ->10239 / h27ff
					*/
					//Establecemos que solo se vean solo los pixeles de frente desde el banco 1 al 3
					for (i = 0; i < 0x1800; i ++) {
						data[i] = (byte) 0xff;   
					}
					//ponemos que en el mapa los tiles del 1 al 768 tile
					for (i = 0x1800; i < 0x1b00; i ++) {
						data[i] = (byte) i;   
					}
					row=0;
					column=0;
					//Ponemos los colores
					int letra=0;
					for (i = 0x2000; i < 0x2100; i ++) {
						/*if(column==8) {
							column=0;
							row++;
						}
						if(row==8) {
							column=8;
							row=0;
						}*/
				        //data[i] = (byte) ((Character.digit(tileString.charAt(i), 16) << 4)+ 1);   
				        //data[i] = (byte) ((Character.digit(tileString.charAt(letra), 16)<<4 ) + (Character.digit(tileString.charAt(letra+1), 16) ) );  
				        letra+=2;
					}
					try {
						img2sc.createFileWithStringSC2(data,fileOrigin.getAbsolutePath(),fileOrigin.getName());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "There was a problem and the image could not be created");
						System.out.println("There was a problem and the image could not be created: "+e1);
					}
					/*for(int file=0;file<16;file++) {
						System.out.println(" ");
						for(int columna=0;columna<48;columna++) {
							System.out.print(colors[file][columna]);
						}
					}*/


					/*System.out.println("-------------------------------------");
					System.out.println("-------------------------------------");
					System.out.println("-------------------------------------");*/
					//System.out.println(tileString.toString());
				}else {
					JOptionPane.showMessageDialog(null, "select the screen");
				}
				
			}else if(fileExtension.equalsIgnoreCase("bmp")) {
				JProgressBarRunnable runnable=new JProgressBarRunnable();
				runnable.setBar(progressBar);
				//runnable.setTextProgressBar(jLabelProgressBar);
				Thread hilo=new Thread(runnable);
				if(bufferedImageOrigin!=null && fileOrigin!=null) {
					hilo.start();
					if (screen==5) {
						StringBuilder result=img2sc.associateColorsBMPWithPaletteReturnStringSC5(bufferedImageOrigin,similarDistance);
						try {
							
							img2sc.createFileWithString(result,fileOrigin.getAbsolutePath(),fileOrigin.getName());
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
				JOptionPane.showMessageDialog(null, "The image is not BMP or PNG format");
			}
		}else {
			JOptionPane.showMessageDialog(null, "Choose a file");
		}
	}
	int contarCaracteres(String cadena, char caracter) {
        int posicion, contador = 0;
        //se busca la primera vez que aparece
        posicion = cadena.indexOf(caracter);
        while (posicion != -1) { //mientras se encuentre el caracter
            contador++;           //se cuenta
            //se sigue buscando a partir de la posición siguiente a la encontrada
            posicion = cadena.indexOf(caracter, posicion + 1);
        }
        return contador;
   }

}
