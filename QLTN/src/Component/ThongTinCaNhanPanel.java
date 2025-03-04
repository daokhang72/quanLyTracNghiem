package Component;

import javax.swing.*;

import BLL.UserBLL;
import DTO.UserDTO;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class ThongTinCaNhanPanel extends JPanel {
    public ThongTinCaNhanPanel() {
    	UserBLL userBll = new UserBLL();
    	UserDTO userDto = Session.getCurrentUser();
    	if (userDto == null) {
    	    JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy thông tin người dùng!");
    	    return;
    	}

    	
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Thông Tin Cá Nhân", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();

        ImageIcon avatar = new ImageIcon("src/image/icons/user.png");
        JLabel avatarLabel = new JLabel(new ImageIcon(avatar.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 1; 
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.fill = GridBagConstraints.BOTH; 
        avatarLabel.setOpaque(true);
        avatarLabel.setBackground(new Color(230, 247, 230));
        avatarLabel.setPreferredSize(new Dimension(100, 100));

        mainPanel.add(avatarLabel, gbc);
        
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
        JLabel nameValueLabel = new JLabel(userDto.getUserName());
        nameValueLabel.setFont(fieldFont);
        nameValueLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        nameValueLabel.setOpaque(true);
        nameValueLabel.setBackground(Color.WHITE);
        nameValueLabel.setPreferredSize(new Dimension(200, 30));
        mainPanel.add(nameValueLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Full Name");
        emailLabel.setFont(labelFont);
        mainPanel.add(emailLabel, gbc);
        
        gbc.gridx = 2;
        JTextField emailField = new JTextField(userDto.getUserEmail());
        emailField.setFont(fieldFont);
        mainPanel.add(emailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JLabel usernameLabel = new JLabel("Email Address");
        usernameLabel.setFont(labelFont);
        mainPanel.add(usernameLabel, gbc);
        
        gbc.gridx = 2;
        JTextField userField = new JTextField(userDto.getUserEmail());
        userField.setFont(fieldFont);
        mainPanel.add(userField, gbc);
       
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        FadeButton btnSave = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Cập Nhập Thông Tin");
        btnPanel.add(btnSave);

        add(mainPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }
}
