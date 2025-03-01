package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import BLL.UserBLL;
import DTO.UserDTO;


public class LoginGUI extends JFrame {
	private JPanel jpn_login;
    private ImageIcon img_logo;
    private JLabel jlb_name, jlb_pass, jlb_logo,jlb_tittle,jlb_content;
    private JTextField jtf_name;
    private JPasswordField jtf_pass;
    private JButton btn_login,btn_register;
    
    public LoginGUI() {
    	this.setTitle("Login Form");
        this.setSize(2000, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font f = new Font("Fira Code", Font.BOLD, 20);
        Font f_title = new Font("Fira Code", Font.BOLD, 40);
     
        jpn_login = new JPanel();
        
        jpn_login.setLayout(null);
        jpn_login.setPreferredSize(new Dimension(500, 800)); 
        jpn_login.setBackground(new Color(253, 233, 226));

        
        img_logo = new ImageIcon("src\\image\\flower.png");
        Image scaledLogo = img_logo.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        img_logo = new ImageIcon(scaledLogo);

        
        jlb_logo = new JLabel(img_logo); 
        jlb_logo.setBounds(150, -20, 200, 200);

        jlb_name = new JLabel("Username");
        jlb_tittle = new JLabel("LOGIN");
        jlb_pass = new JLabel("Password");
        jtf_name = new RoundedTextField(20); 
        jtf_pass = new RoundedPasswordField(20); 
        btn_login = new RoundedButton("LOGIN");
        btn_register = new JButton("REGISTER");
        jlb_content = new JLabel("You dont have account.");

        // Set position and size for components
        jlb_name.setBounds(30, 240, 100, 30);
        jlb_name.setFont(f);
        jtf_name.setBounds(30, 270, 400, 40);
        jlb_pass.setBounds(30,330,100,30);
        jlb_pass.setFont(f);
        jtf_pass.setBounds(30,360,400,40);
        btn_login.setBounds(150, 500, 170, 50);
        btn_login.setFont(f);
        btn_login.setForeground(Color.white);
        jlb_tittle.setBounds(180, 175, 250, 40); 
        jlb_tittle.setFont(f_title); 
        jlb_tittle.setForeground(Color.DARK_GRAY);
        jlb_content.setBounds(100,420,200,50);
        jlb_content.setFont(new Font("Arial",Font.ITALIC,15));
        jlb_content.setForeground(Color.red);
        btn_register.setBounds(265,420,90,50);
        btn_register.setBackground(null);
        btn_register.setBorder(null);
        btn_register.setFont(new Font("Arial",Font.BOLD,16));
        btn_register.setForeground(Color.red);
        
        jpn_login.add(jlb_logo); 
        jpn_login.add(jlb_name);
        jpn_login.add(jtf_name);
        jpn_login.add(jlb_pass);
        jpn_login.add(jtf_pass);
        jpn_login.add(btn_login);
        jpn_login.add(btn_register);
        jpn_login.add(jlb_tittle);
        jpn_login.add(jlb_content);
        
        btn_register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RegisterGUI register = new RegisterGUI();
				register.setVisible(true);
				register.setExtendedState(JFrame.MAXIMIZED_BOTH);
				dispose();
			}
		});
        
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("src//image//study2.png"); 
                g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        btn_login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = jtf_name.getText().trim();
				String pass = new String(jtf_pass.getPassword());
				
				if(username.isEmpty() || pass.isEmpty()) {
					JOptionPane.showMessageDialog(LoginGUI.this, "Thông tin không được để trống","Error",JOptionPane.ERROR_MESSAGE);
	            	return;
				}
				UserBLL userbll = new UserBLL();
				UserDTO user = userbll.login(username, pass);
				if(userbll.kiemTraTonTai(username) == false) {
					JOptionPane.showMessageDialog(LoginGUI.this, "Tài khoản không tồn tại","Error",JOptionPane.ERROR_MESSAGE);
					jtf_name.setText("");jtf_pass.setText("");
	            	return;
				}
				if (user != null) {
		            if (user.isAdmin()) { 
		                AdminGUI admingui = new AdminGUI();
		                admingui.setVisible(true);
		                admingui.setExtendedState(JFrame.MAXIMIZED_BOTH);
		            } else { 
		                UserGUI usergui = new UserGUI();
		                usergui.setVisible(true);
		                usergui.setExtendedState(JFrame.MAXIMIZED_BOTH);
		            }
		            dispose(); 
		        } else {
		            JOptionPane.showMessageDialog(LoginGUI.this, "Tên đăng nhập hoặc mật khẩu không chính xác", "Error", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});

        imagePanel.setPreferredSize(new Dimension(0, 0));
        
        this.setLayout(new BorderLayout());
        this.add(jpn_login, BorderLayout.WEST);
        this.add(imagePanel, BorderLayout.CENTER);
    }
 // Rounded JTextField class
    class RoundedTextField extends JTextField {
        private int radius;

        public RoundedTextField(int radius) {
            super();
            this.radius = radius;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(null); // Border color
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
    }

    // Rounded JPasswordField class
    class RoundedPasswordField extends JPasswordField {
        private int radius;

        public RoundedPasswordField(int radius) {
            super();
            this.radius = radius;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(null);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
    }

     //Rounded JButton class
    class RoundedButton extends JButton {
        private int radius;

        public RoundedButton(String label) {
            super(label);
            this.radius = 50; 
            setContentAreaFilled(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(235,197,184));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(null);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
    }
    public static void main(String[] args) {
    	LoginGUI login =  new LoginGUI();
    	login.pack();
    	login.setVisible(true);
    	login.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}
