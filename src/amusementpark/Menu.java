/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusementpark;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 *
 * @author sukap
 */
public class Menu extends JFrame{
    private JPanel contentPane;
    private JTextField playerNameInp;
//    Image closeImg = new ImageIcon("data/closeIcon.png").getImage();
    public JTextField getPlayerName() {
    	return this.playerNameInp;
    }
    public void setPlayerName(String name) {
    	 this.playerNameInp.setText(name);
    }

	/**
	 * Create the frame.
	 */
	public Menu() {
		setBackground(new Color(178, 227, 230));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 746, 382);
		setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(178, 227, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Amusement Park");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(263, 65, 236, 38);
		contentPane.add(lblNewLabel);
		
		JButton startButton = new JButton("Start Game");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                            if (!playerNameInp.getText().equals("")) {
                                ParkGUI p = new ParkGUI(playerNameInp.getText());                   
                                setVisible(false);
                            }
			}
		});
		startButton.setBounds(337, 230, 117, 58);
		contentPane.add(startButton);
		
		JLabel welcomeLabel = new JLabel("WELCOME!");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		welcomeLabel.setBackground(Color.WHITE);
		welcomeLabel.setBounds(323, 125, 140, 38);
//		welcomeLabel.setBorder(border);
		welcomeLabel.setOpaque(true);
		contentPane.add(welcomeLabel);
		
		JLabel nameLabel = new JLabel("Player name:");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBackground(Color.WHITE);
		nameLabel.setBounds(320, 189, 85, 26);
		nameLabel.setOpaque(true);
		contentPane.add(nameLabel);
		
		playerNameInp = new JTextField();
		playerNameInp.setBounds(417, 186, 85, 32);
		contentPane.add(playerNameInp);
		playerNameInp.setColumns(10);
		
		JLabel closeIcon = new JLabel(new ImageIcon("data/close6.png"));
		closeIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		closeIcon.setBounds(706, 6, 34, 38);

		contentPane.add(closeIcon);
                
                setVisible(true);
	}
}
