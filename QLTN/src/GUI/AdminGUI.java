package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Component.QuanLyAnswerPanel;
import Component.QuanLyExam;
import Component.QuanLyTopicPanel;
import Component.QuanLyUserPanel;
import Component.RoundedButton;

public class AdminGUI extends JFrame {
    private JPanel jpn_login, jpn_rightPanel;
    private ImageIcon img_logo;
    private JLabel jlb_logo;
    private JButton btn_user, btn_question, btn_answer, btn_test, btn_result, btn_exam, btn_topic, btn_log,btn_logout;
    private CardLayout card;

    public AdminGUI() {
        init();
    }

    public void init() {
        this.setTitle("Admin Page");
        this.setSize(2000, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font f_button = new Font("Arial", Font.BOLD, 16); 
        Color btnColor = new Color(240, 128, 128); 

        // --- TẠO PANEL TRÁI---
        jpn_login = new JPanel();
        jpn_login.setLayout(new GridBagLayout()); 
        jpn_login.setPreferredSize(new Dimension(400, 800));
        jpn_login.setBackground(new Color(253, 233, 226));
        
        // --- TẠO PANEL PHẢI ---
        card = new CardLayout();
        jpn_rightPanel = new JPanel(card);
        jpn_rightPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- LOGO ---
        img_logo = new ImageIcon("src\\image\\flower.png");
        Image scaledLogo = img_logo.getImage().getScaledInstance(170, 150, Image.SCALE_SMOOTH);
        img_logo = new ImageIcon(scaledLogo);
        jlb_logo = new JLabel(img_logo);
        jpn_login.add(jlb_logo, gbc);

        // --- TẠO DANH SÁCH NÚT ---
        gbc.gridy++;
        btn_user = createButton("User Management", f_button, btnColor);
        jpn_login.add(btn_user, gbc);

        gbc.gridy++;
        btn_question = createButton("Question Management", f_button, btnColor);
        jpn_login.add(btn_question, gbc);

        gbc.gridy++;
        btn_answer = createButton("Answer Management", f_button, btnColor);
        jpn_login.add(btn_answer, gbc);

        gbc.gridy++;
        btn_test = createButton("Test Management", f_button, btnColor);
        jpn_login.add(btn_test, gbc);

        gbc.gridy++;
        btn_result = createButton("Result Management", f_button, btnColor);
        jpn_login.add(btn_result, gbc);

        gbc.gridy++;
        btn_exam = createButton("Exam Management", f_button, btnColor);
        jpn_login.add(btn_exam, gbc);

        gbc.gridy++;
        btn_topic = createButton("Topic Management", f_button, btnColor);
        jpn_login.add(btn_topic, gbc);

        gbc.gridy++;
        btn_log = createButton("Log Management", f_button, btnColor);
        jpn_login.add(btn_log, gbc);
        
        gbc.gridy++;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH; 
        btn_logout = createButton("Logout", f_button, Color.RED);
        jpn_login.add(btn_logout, gbc);
        
        // --- xỬ LÝ SỰ KIỆN CHO CÁC NÚT ---
        btn_logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				int confirm = JOptionPane.showConfirmDialog(AdminGUI.this, "Bạn có chắc chắn muốn đăng xuất?","Information",JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.YES_OPTION) {
					login.setVisible(true);
					login.setExtendedState(JFrame.MAXIMIZED_BOTH);
					dispose();
					
				}
			}
		});
        
     // Thêm các panel vào card layout
        jpn_rightPanel.add(new JLabel("<html><h1 style='color:blue;'>Xin chào Admin</h1></html>", SwingConstants.CENTER), "HOME");
        jpn_rightPanel.add(new QuanLyUserPanel(), "USER");
        jpn_rightPanel.add(new QuestionManager(), "QUESTION");
        jpn_rightPanel.add(new QuanLyAnswerPanel(), "ANSWER");
        jpn_rightPanel.add(new JLabel("Test Management Panel"), "TEST");
        jpn_rightPanel.add(new JLabel("Result Management Panel"), "RESULT");
        jpn_rightPanel.add(new QuanLyExam(), "EXAM");
        jpn_rightPanel.add(new JLabel("Topic Management Panel"), "TOPIC");
        jpn_rightPanel.add(new QuanLyTopicPanel(),"TOPIC");
        jpn_rightPanel.add(new LogManagement(), "LOG");

        // --- SỰ KIỆN CHUYỂN PANEL ---
        btn_user.addActionListener(e -> card.show(jpn_rightPanel, "USER"));
        btn_question.addActionListener(e -> card.show(jpn_rightPanel, "QUESTION"));
        btn_answer.addActionListener(e -> card.show(jpn_rightPanel, "ANSWER"));
        btn_test.addActionListener(e -> card.show(jpn_rightPanel, "TEST"));
        btn_result.addActionListener(e -> card.show(jpn_rightPanel, "RESULT"));
        btn_exam.addActionListener(e -> card.show(jpn_rightPanel, "EXAM"));
        btn_topic.addActionListener(e -> card.show(jpn_rightPanel, "TOPIC"));
        btn_log.addActionListener(e -> card.show(jpn_rightPanel, "LOG"));

        // --- THÊM PANEL VÀO FRAME ---
        this.setLayout(new BorderLayout());
        this.add(jpn_login, BorderLayout.WEST);
        this.add(jpn_rightPanel, BorderLayout.CENTER);
    }

    // --- HÀM TẠO NÚT ---
    private JButton createButton(String text, Font font, Color bgColor) {
        RoundedButton btn = new RoundedButton(text);
        btn.setFont(font);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(230, 40));
        return btn;
    }

    public static void main(String[] args) {
        AdminGUI ad = new AdminGUI();
        ad.pack();
        ad.setVisible(true);
        ad.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}

