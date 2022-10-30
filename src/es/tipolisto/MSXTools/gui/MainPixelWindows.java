package es.tipolisto.MSXTools.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPixelWindows extends JFrame {

	private JPanel contentPane;
	private JTextField jTextFieldHorizontalTiles;
	private JTextField jTextFieldverticalTiles;



	/**
	 * Create the frame.
	 */
	public MainPixelWindows() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		jTextFieldHorizontalTiles = new JTextField();
		jTextFieldHorizontalTiles.setBounds(5, 118, 407, 20);
		contentPane.add(jTextFieldHorizontalTiles);
		jTextFieldHorizontalTiles.setColumns(10);
		
		jTextFieldverticalTiles = new JTextField();
		jTextFieldverticalTiles.setBounds(5, 174, 407, 20);
		contentPane.add(jTextFieldverticalTiles);
		jTextFieldverticalTiles.setColumns(10);
		
		JButton btnNewButton = new JButton("Create tiles");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String horizontalTiles=jTextFieldHorizontalTiles.getText();
				String verticalTiles=jTextFieldverticalTiles.getText();

				if (!horizontalTiles.matches("[+-]?\\d*(\\.\\d+)?") || !verticalTiles.matches("[+-]?\\d*(\\.\\d+)?")) {
					JOptionPane.showConfirmDialog(null, "You have to enter a number");
				}else {
					PixelWindow frame = new PixelWindow(Integer.parseInt(horizontalTiles),Integer.parseInt(verticalTiles));
					frame.setVisible(true);
				}

			}
		});
		btnNewButton.setBounds(151, 205, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Enter the number of horizontal tiles");
		lblNewLabel_2.setBounds(5, 87, 424, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Enter the number of verticaltiles");
		lblNewLabel_3.setBounds(5, 149, 407, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("Setting");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setBounds(169, 31, 89, 45);
		contentPane.add(lblNewLabel);
		

	}
	

}





