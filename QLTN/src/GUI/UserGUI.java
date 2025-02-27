package GUI;

import Component.GradientPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserGUI extends JFrame{
	public UserGUI() {
		GradientPanel sidebar = new GradientPanel(new Color(52, 192, 175),new Color(181, 231, 162));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.add(Box.createVerticalStrut(30)); 

        JLabel logoLabel = new JLabel("Quản Lý Trắc Nghiệm");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel subtitleLabel = new JLabel("NHÓM 00");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(logoLabel);
        sidebar.add(subtitleLabel);

        sidebar.add(Box.createVerticalStrut(50)); 


        
        sidebar.add(Box.createVerticalStrut(100));
        sidebar.add(createMenuItem("Thông Tin Cá Nhân", "src/image/icons/user.png"));
        sidebar.add(createMenuItem("Môn Học", "src/image/icons/subjects.png"));
        sidebar.add(createMenuItem("Kiểm Tra", "src/image/icons//test.png"));
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(createMenuItem("Log out", "src/image/icons/logout.png"));
    	
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BorderLayout());

        JLabel contentLabel = new JLabel("Welcome", SwingConstants.CENTER);
        contentLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPanel.add(contentLabel, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        
    }

    private JPanel createMenuItem(String text, String iconPath) {
    	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 20));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(200, 40));
        
        JLabel icon = new JLabel();
        ImageIcon img = new ImageIcon(iconPath);
        Image scaledImg = img.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icon.setIcon(new ImageIcon(scaledImg));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);

        panel.add(icon);
        panel.add(label);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	panel.setOpaque(true);
                panel.setBackground(new Color(0, 180, 156));
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	panel.setOpaque(false);
                panel.setBackground(new Color(0, 150, 136));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Clicked: " + text);
            }
        });

        return panel;
    }
    
    public static void main(String[] args) {
    	UserGUI user =  new UserGUI();
    	user.pack();
    	user.setVisible(true);
    	user.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}
