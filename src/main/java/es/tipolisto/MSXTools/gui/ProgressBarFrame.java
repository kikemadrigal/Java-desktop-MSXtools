package es.tipolisto.MSXTools.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;

public class ProgressBarFrame extends JFrame implements Runnable {

	private JPanel contentPane;
	JLabel lblNewLabel;
	JProgressBar progressBar;
	int value;
	public ProgressBarFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 421, 108);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		lblNewLabel = new JLabel("Progress...");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		//lblNewLabel.setVisible(false);
		
		progressBar = new JProgressBar();
		contentPane.add(progressBar, BorderLayout.CENTER);
		progressBar.setMaximum(256*212);
		//progressBar.setVisible(false);
		setVisible(false);
	}
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	public void setProgress(int value) {
		progressBar.setValue(value);
		repaint();
	}
	
	public void mostrar() {
		//lblNewLabel.setVisible(true);
		//progressBar.setVisible(true);
		
	}


	@Override
	public void run() {
		for (int i=0;i<=20;i++) {
			try {
				Thread.sleep(600);
				progressBar.setValue(i);
				repaint();
				System.out.println(""+i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		//if(progressBar.getValue()==256*212) setVisible(false);
		
	}
}
