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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.tipolisto.MSXTools.beans.RGB;
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
	private JLabel jLabelProgressBar;
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
		progressBar.setBounds(10, 395, 580, 28);
		contentPane.add(progressBar);
		progressBar.setBackground(Color.green);
		progressBar.setVisible(false);
	
		
		jLabelProgressBar = new JLabel("");
		jLabelProgressBar.setBounds(18, 238, 572, 20);
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
		buttonConvertBMP2SC5.setBounds(10, 226, 580, 35);
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
		buttonConvertPNG2SC5.setBounds(10, 166, 580, 35);
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
		
		
		
		JButton buttonConvertBMP2SC2 = new JButton("Convert BMP 2 SC2");
		buttonConvertBMP2SC2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		buttonConvertBMP2SC2.setBounds(10, 336, 580, 35);
		buttonConvertBMP2SC2.setVisible(false);
		contentPane.add(buttonConvertBMP2SC2);
		
		JButton buttonConvertPNG2SC2 = new JButton("Convert PGN 2 SC2");
		buttonConvertPNG2SC2.setBounds(10, 279, 580, 35);
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
		

		
		

	}
}
