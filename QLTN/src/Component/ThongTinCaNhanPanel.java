package Component;

import javax.swing.*;
import java.awt.*;

public class ThongTinCaNhanPanel extends JPanel {
    public ThongTinCaNhanPanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("LEA / UPDATE INFO", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel avatar = new JLabel(new ImageIcon("avatar.png"));
        JButton btnUpdateAvatar = new JButton("Update");

        JLabel lblName = new JLabel("Name:");
        JTextField txtName = new JTextField("Nguyễn Văn Linh");

        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField("linhnxpd05401@gmail.com");

        JLabel lblGender = new JLabel("Gender:");
        JRadioButton male = new JRadioButton("Male", true);
        JRadioButton female = new JRadioButton("Female");

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        JPanel genderPanel = new JPanel();
        genderPanel.add(male);
        genderPanel.add(female);

        JLabel lblPhone = new JLabel("Phone:");
        JTextField txtPhone = new JTextField("0819515456");

        JLabel lblBirthday = new JLabel("Birthday:");
        JTextField txtBirthday = new JTextField("08-02-1998");

        JLabel lblAddress = new JLabel("Address:");
        JTextField txtAddress = new JTextField("Quảng Bình");

        mainPanel.add(lblName); mainPanel.add(txtName);
        mainPanel.add(lblEmail); mainPanel.add(txtEmail);
        mainPanel.add(lblGender); mainPanel.add(genderPanel);
        mainPanel.add(lblPhone); mainPanel.add(txtPhone);
        mainPanel.add(lblBirthday); mainPanel.add(txtBirthday);
        mainPanel.add(lblAddress); mainPanel.add(txtAddress);

        add(mainPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        add(btnPanel, BorderLayout.SOUTH);
    }
}
