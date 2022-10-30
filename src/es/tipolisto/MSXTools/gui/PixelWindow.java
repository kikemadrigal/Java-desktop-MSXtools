package es.tipolisto.MSXTools.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import utils.MSXColor;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

public class PixelWindow extends JFrame {

	private PaneDrawable contentPane;
	private JLabel jLabelXposition;
	private JLabel jLabelYposition; 

	/**
	 * Create the frame.
	 */
	//	g.drawRect(x, y, 10,10);
	public PixelWindow(int horizontalTiles, int verticalTiles) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1000, 800);


		contentPane = new PaneDrawable(horizontalTiles, verticalTiles );
        setContentPane(contentPane);

		
		//initializeColorButtons();

		//addMouseListener(this);
		//addMouseMotionListener(this);
		
	}
	



	/***********************MouseListener**************************/
	/*@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getX() %10==0 && e.getY() % 10 ==0) {
			paintPixelAtPoint(this.getGraphics(),e.getX(), e.getY());
			jLabelXposition.setText(""+e.getX());
			jLabelYposition.setText(""+e.getY());
		}
			
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}*/
	/***************************End MouseListener**************************/
	
	
	
	
	/***********************MouseMotionListener****************************/
	/*@Override
	public void mouseDragged(MouseEvent e) {
		
		//System.out.println(" "+e.getX()+" "+e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	
		//if(e.getX() %10==0 && e.getY() %10==0) {
			if(e.getX()!=pixeloldX || e.getY()!=pixelOldY) {
				jLabelXposition.setText("");
				jLabelYposition.setText("");
				paintFrameAtPoint(this.getGraphics(),e.getX(),e.getY());
				//deletePixelAtPoint(this.getGraphics(),e.getX(),e.getY());
				jLabelXposition.setText(""+e.getX());
				jLabelYposition.setText(""+e.getY());
			}else {
				jLabelXposition.setText("");
				jLabelYposition.setText("");
			}
			//deletePixelAtPoint(this.getGraphics(),e.getX(),e.getY());
			pixeloldX=e.getX();
			pixelOldY=e.getY();
		//}

	}*/
	/******************End MouseMotionListener*****************************/
	
	

	/*
	private void initializeColorButtons() {
		contentPane.setLayout(null);
		JButton jButtonColor0 = new JButton("0");
		jButtonColor0.setForeground(Color.BLACK);
		jButtonColor0.setBackground(Color.WHITE);
		jButtonColor0.setOpaque(true);
		jButtonColor0.setBorderPainted(false);
		jButtonColor0.setBounds(37, 7, 39, 23);
		contentPane.add(jButtonColor0);
		jButtonColor0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(0);
			}
		});
		
		JButton jButtonColor1 = new JButton("1");
		jButtonColor1.setBackground(new Color(0, 0, 0));
		jButtonColor1.setForeground(new Color(255, 255, 255));
		jButtonColor1.setOpaque(true);
		jButtonColor1.setBorderPainted(false);
		jButtonColor1.setBounds(81, 7, 39, 23);
		contentPane.add(jButtonColor1);
		jButtonColor1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(1);
				contentPane.requestFocusInWindow();
			}
		});
		
		
		JButton jButtonColor2 = new JButton("2");
		jButtonColor2.setForeground(new Color(255, 255, 255));
		jButtonColor2.setBackground(new Color(0, 128, 0));
		jButtonColor2.setOpaque(true);
		jButtonColor2.setBorderPainted(false);
		jButtonColor2.setBounds(125, 7, 39, 23);
		contentPane.add(jButtonColor2);
		jButtonColor2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(2);
				contentPane.requestFocusInWindow();
			}
		});
		

		JButton jButtonColor3 = new JButton("3");
		jButtonColor3.setBackground(new Color(0, 255, 0));
		jButtonColor3.setOpaque(true);
		jButtonColor3.setBorderPainted(false);
		jButtonColor3.setBounds(169, 7, 39, 23);
		contentPane.add(jButtonColor3);
		jButtonColor3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(3);
				contentPane.requestFocusInWindow();
			}
		});
		
		JButton jButtonColor4 = new JButton("4");
		jButtonColor4.setForeground(new Color(255, 255, 255));
		jButtonColor4.setBackground(new Color(0, 0, 205));
		jButtonColor4.setOpaque(true);
		jButtonColor4.setBorderPainted(false);
		jButtonColor4.setBounds(213, 7, 39, 23);
		contentPane.add(jButtonColor4);
		jButtonColor4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(4);
				contentPane.requestFocusInWindow();
			}
		});
		
		JButton jButtonColor5 = new JButton("5");
		jButtonColor5.setBackground(new Color(0, 0, 255));
		jButtonColor5.setForeground(new Color(255, 255, 255));
		jButtonColor5.setOpaque(true);
		jButtonColor5.setBorderPainted(false);
		jButtonColor5.setBounds(257, 7, 39, 23);
		contentPane.add(jButtonColor5);
		jButtonColor5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(5);
				contentPane.requestFocusInWindow();
			}
		});
		
		JButton jButtonColor6 = new JButton("6");
		jButtonColor6.setForeground(new Color(255, 255, 255));
		jButtonColor6.setBackground(new Color(255, 0, 0));
		jButtonColor6.setOpaque(true);
		jButtonColor6.setBorderPainted(false);
		jButtonColor6.setBounds(301, 7, 39, 23);
		contentPane.add(jButtonColor6);
		jButtonColor6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(6);
				contentPane.requestFocusInWindow();
			}
		});
		
		JButton jButtonColor7 = new JButton("7");
		jButtonColor7.setOpaque(true);
		jButtonColor7.setForeground(new Color(0, 0, 0));
		jButtonColor7.setBorderPainted(false);
		jButtonColor7.setBackground(new Color(0, 255, 255));
		jButtonColor7.setBounds(345, 7, 39, 23);
		contentPane.add(jButtonColor7);
		jButtonColor7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(7);
				contentPane.requestFocusInWindow();
			}
		});
		
		JButton jButtonColor8 = new JButton("6");
		jButtonColor8.setOpaque(true);
		jButtonColor8.setForeground(Color.WHITE);
		jButtonColor8.setBorderPainted(false);
		jButtonColor8.setBackground(new Color(255, 99, 71));
		jButtonColor8.setBounds(389, 7, 39, 23);
		contentPane.add(jButtonColor8);
		jButtonColor8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(8);
				contentPane.requestFocusInWindow();
			}
		});
		
		JButton jButtonColor9 = new JButton("9");
		jButtonColor9.setOpaque(true);
		jButtonColor9.setForeground(new Color(0, 0, 0));
		jButtonColor9.setBorderPainted(false);
		jButtonColor9.setBackground(new Color(255, 182, 193));
		jButtonColor9.setBounds(433, 7, 39, 23);
		contentPane.add(jButtonColor9);
		jButtonColor9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(9);
				contentPane.requestFocusInWindow();
			}
		});
		
		JButton jButtonColor10 = new JButton("10");
		jButtonColor10.setOpaque(true);
		jButtonColor10.setForeground(new Color(0, 0, 0));
		jButtonColor10.setBorderPainted(false);
		jButtonColor10.setBackground(new Color(255, 255, 0));
		jButtonColor10.setBounds(477, 7, 45, 23);
		contentPane.add(jButtonColor10);
		jButtonColor10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(10);
				contentPane.requestFocusInWindow();
			}
		});
		
		JButton jButtonColor11 = new JButton("11");
		jButtonColor11.setOpaque(true);
		jButtonColor11.setForeground(new Color(0, 0, 0));
		jButtonColor11.setBorderPainted(false);
		jButtonColor11.setBackground(new Color(255, 222, 173));
		jButtonColor11.setBounds(527, 7, 45, 23);
		contentPane.add(jButtonColor11);
		jButtonColor11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(11);
				contentPane.requestFocusInWindow();
			}
		});
		
		JButton jButtonColor12 = new JButton("12");
		jButtonColor12.setOpaque(true);
		jButtonColor12.setForeground(new Color(0, 0, 0));
		jButtonColor12.setBorderPainted(false);
		jButtonColor12.setBackground(new Color(50, 205, 50));
		jButtonColor12.setBounds(577, 7, 45, 23);
		contentPane.add(jButtonColor12);
		jButtonColor12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(12);
				contentPane.requestFocusInWindow();
			}
		});
		
		JButton jButtonColor13 = new JButton("13");
		jButtonColor13.setOpaque(true);
		jButtonColor13.setForeground(Color.WHITE);
		jButtonColor13.setBorderPainted(false);
		jButtonColor13.setBackground(new Color(255, 0, 255));
		jButtonColor13.setBounds(627, 7, 45, 23);
		contentPane.add(jButtonColor13);
		jButtonColor13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(13);
				contentPane.requestFocusInWindow();
			}
		});
		
		JButton jButtonColor14 = new JButton("14");
		jButtonColor14.setOpaque(true);
		jButtonColor14.setForeground(Color.WHITE);
		jButtonColor14.setBorderPainted(false);
		jButtonColor14.setBackground(new Color(128, 128, 128));
		jButtonColor14.setBounds(677, 7, 45, 23);
		contentPane.add(jButtonColor14);
		jButtonColor14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(14);
				contentPane.requestFocusInWindow();
			}
		});
		
		JButton jButtonColor15 = new JButton("15");
		jButtonColor15.setOpaque(true);
		jButtonColor15.setForeground(new Color(0, 0, 0));
		jButtonColor15.setBorderPainted(false);
		jButtonColor15.setBackground(new Color(255, 255, 255));
		jButtonColor15.setBounds(727, 7, 45, 23);
		contentPane.add(jButtonColor15);
		jButtonColor15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setColor(15);
				contentPane.requestFocusInWindow();
			}
		});
		
		JLabel lblNewLabel = new JLabel("Pixel editor");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel.setBounds(828, 5, 108, 27);
		lblNewLabel.setOpaque(true);
		contentPane.add(lblNewLabel);
		
		jLabelXposition = new JLabel("");
		jLabelXposition.setBounds(941, 18, 0, 0);
		contentPane.add(jLabelXposition);
		
		jLabelYposition = new JLabel("");
		jLabelYposition.setBounds(946, 18, 0, 0);
		contentPane.add(jLabelYposition);

	}*/





}













