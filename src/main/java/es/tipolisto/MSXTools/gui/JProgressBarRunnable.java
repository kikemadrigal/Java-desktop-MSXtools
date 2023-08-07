package es.tipolisto.MSXTools.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ProgressBarUI;
import java.awt.BorderLayout;

public class JProgressBarRunnable  implements Runnable{
	JProgressBar progressBar;
	JLabel jLabelProgressBar;
	public JProgressBarRunnable() {
		//progressBar=new JProgressBar();
	}

	@Override
	public void run() {
		//if(jProgressBar==null)System.out.println("dentro del hilo progressBar es null");
		progressBar.setIndeterminate(true);
		progressBar.setVisible(true);
		/*for (int i=1;i<=100;i++) {
			try {
				Thread.sleep(100);
				//this.getBar().setValue(i);
				jLabelProgressBar.setText(""+i);
				progressBar.setValue(i);
				System.out.printf("\r%s", " "+i);
				//System.out.print(" "+i);
			}catch(InterruptedException ex) {
				System.out.print(ex.toString());
			}
			//this.getBar().setValue(i);
		}*/
	}

	
	public void setBar(JProgressBar bar) {
		this.progressBar=bar;
	}
	

	public void stop() {
		//jProgressBar.setIndeterminate(false);
		//progressBar.setVisible(false);
		//jLabel.setText("0");
		progressBar.setIndeterminate(false);
	}

	public void setTextProgressBar(JLabel jLabelProgressBar) {
		this.jLabelProgressBar=jLabelProgressBar;
		
	}
}
