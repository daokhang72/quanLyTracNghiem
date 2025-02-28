package Component;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class ThongTinCaNhanPanel extends JPanel {
    public ThongTinCaNhanPanel() {
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
        avatarLabel.setPreferredSize(new Dimension(150, 150));

        mainPanel.add(avatarLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Name"), gbc);

        gbc.gridx = 2;
        JTextField nameField = new JTextField("name");
        mainPanel.add(nameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Email"), gbc);

        gbc.gridx = 2;
        JTextField emailField = new JTextField("email");
        mainPanel.add(emailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Gender"), gbc);

        gbc.gridx = 2;
        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");
        male.setBackground(Color.WHITE);
        female.setBackground(Color.WHITE);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        JPanel genderPanel = new JPanel();
        genderPanel.setBackground(Color.WHITE);
        genderPanel.add(male);
        genderPanel.add(female);
        mainPanel.add(genderPanel, gbc);

        gbc.gridy = 6; gbc.gridx = 1;
        mainPanel.add(new JLabel("Phone"), gbc);
        gbc.gridx = 2;
        mainPanel.add(new JLabel("Birthday"), gbc);

        gbc.gridy = 7; gbc.gridx = 1;
        JTextField txtPhone = new JTextField("0819515456");
        mainPanel.add(txtPhone, gbc);

        gbc.gridx = 2;
        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner jspBirthday = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(jspBirthday, "dd/MM/yyyy");
        jspBirthday.setEditor(editor);        
        mainPanel.add(jspBirthday, gbc);

        gbc.gridy = 8; gbc.gridx = 1;
        mainPanel.add(new JLabel("Address"), gbc);
        gbc.gridy = 9; gbc.gridwidth = 2;
        JTextField txtAddress = new JTextField("Quảng Bình");
        mainPanel.add(txtAddress, gbc);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        FadeButton btnSave = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Cập Nhập Thông Tin");
        btnPanel.add(btnSave);

        add(mainPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }
}
