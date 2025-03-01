package Component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class FormAddUser extends JFrame{
	public FormAddUser() {
	
		setSize(500, 500);
    	setBackground(Color.WHITE);
    	setVisible(true);
    	
    	JLabel title = new JLabel("Thêm User", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel userLabel = new JLabel("User Name");
        userLabel.setFont(labelFont);
        mainPanel.add(userLabel, gbc);

        gbc.gridx = 2;
        JTextField nameValueLabel = new JTextField("", 15);
        nameValueLabel.setFont(fieldFont);
        mainPanel.add(nameValueLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("User Email");
        emailLabel.setFont(labelFont);
        mainPanel.add(emailLabel, gbc);
        
        gbc.gridx = 2;
        JTextField emailField = new JTextField("", 15);
        emailField.setFont(fieldFont);
        mainPanel.add(emailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JLabel usernameLabel = new JLabel("User Password");
        usernameLabel.setFont(labelFont);
        mainPanel.add(usernameLabel, gbc);
        
        gbc.gridx = 2;
        JTextField userField = new JTextField("", 15);
        userField.setFont(fieldFont);
        mainPanel.add(userField, gbc);
       
        gbc.gridx = 1;
        gbc.gridy = 3;
        JLabel fullNameLabel = new JLabel("User Full Name");
        fullNameLabel.setFont(labelFont);
        mainPanel.add(fullNameLabel, gbc);
        
        gbc.gridx = 2;
        JTextField fullNameField = new JTextField("", 15);
        fullNameField.setFont(fieldFont);
        mainPanel.add(fullNameField, gbc);
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        FadeButton btnSave = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"THêm");
        btnPanel.add(btnSave);

        add(mainPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
	}
}
