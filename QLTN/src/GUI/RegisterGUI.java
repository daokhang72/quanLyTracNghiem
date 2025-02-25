package GUI;

import javax.swing.*;

import BLL.UserBLL;
import DTO.UserDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterGUI extends JFrame {
	private JPanel jpn_login;
    private ImageIcon img_logo;
    private JLabel jlb_name, jlb_pass,jlb_email,jlb_fullname, jlb_logo,jlb_tittle,jlb_content;
    private JTextField jtf_name,jtf_email,jtf_fullname;
    private JPasswordField jtf_pass;
    private JButton btn_login,btn_register;
    public RegisterGUI() {
    	this.setTitle("Register Form");
        this.setSize(2000, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font f = new Font("Fira Code", Font.BOLD, 20);
        Font f_title = new Font("Fira Code", Font.BOLD, 40);
     
        jpn_login = new JPanel();
        
        jpn_login.setLayout(null);
        jpn_login.setPreferredSize(new Dimension(500, 800)); 
        jpn_login.setBackground(new Color(253, 233, 226));

        
        img_logo = new ImageIcon("C:\\Users\\Admin\\Downloads\\flower.png");
        Image scaledLogo = img_logo.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        img_logo = new ImageIcon(scaledLogo);

        
        jlb_logo = new JLabel(img_logo); 
        jlb_logo.setBounds(150, -20, 200, 200);

        jlb_name = new JLabel("Username");
        jlb_email = new JLabel("Email");
        jlb_fullname = new JLabel("Fullname");
        jlb_tittle = new JLabel("REGISTER");
        jlb_pass = new JLabel("Password");
        jtf_name = new RoundedTextField(20); 
        jtf_email = new RoundedTextField(20);
        jtf_pass = new RoundedPasswordField(20); 
        jtf_fullname = new RoundedTextField(20);
        btn_register = new RoundedButton("REGISTER");
        btn_login = new JButton("LOGIN");
        jlb_content = new JLabel("Already have an account.");

        // Set position and size for components
        jlb_name.setBounds(30, 240, 100, 30);
        jlb_name.setFont(f);
        jtf_name.setBounds(30, 270, 400, 40);
        jlb_email.setBounds(30,330,100,30);
        jlb_email.setFont(f);
        jtf_email.setBounds(30,360,400,40);
        jlb_pass.setBounds(30, 420, 100, 30);
        jlb_pass.setFont(f);
        jtf_pass.setBounds(30, 450, 400, 40);
        jlb_fullname.setBounds(30,510,200,30);
        jlb_fullname.setFont(f);
        jtf_fullname.setBounds(30,540,400,40);
        btn_register.setBounds(130, 650, 170, 50);
        btn_register.setFont(f);
        btn_register.setForeground(Color.white);
        jlb_tittle.setBounds(140, 175, 250, 40); 
        jlb_tittle.setFont(f_title); 
        jlb_tittle.setForeground(Color.DARK_GRAY);
        jlb_content.setBounds(100,600,200,50);
        jlb_content.setFont(new Font("Arial",Font.ITALIC,15));
        jlb_content.setForeground(Color.red);
        btn_login.setBounds(280,600,50,50);
        btn_login.setBackground(null);
        btn_login.setBorder(null);
        btn_login.setFont(new Font("Arial",Font.BOLD,16));
        btn_login.setForeground(Color.red);
        
        jpn_login.add(jlb_logo); // Add logo to the login panel
        jpn_login.add(jlb_name);
        jpn_login.add(jtf_name);
        jpn_login.add(jlb_email);
        jpn_login.add(jtf_email);
        jpn_login.add(jlb_pass);
        jpn_login.add(jtf_pass);
        jpn_login.add(jlb_fullname);
        jpn_login.add(jtf_fullname);
        jpn_login.add(btn_login);
        jpn_login.add(btn_register);
        jpn_login.add(jlb_tittle);
        jpn_login.add(jlb_content);
        
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("D:\\HinhAnhDoAn\\study2.png"); 
                g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        imagePanel.setPreferredSize(new Dimension(0, 0));
        
        this.setLayout(new BorderLayout());
        this.add(jpn_login, BorderLayout.WEST);
        this.add(imagePanel, BorderLayout.CENTER);
        
        btn_login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				login.setExtendedState(JFrame.MAXIMIZED_BOTH);
				dispose();
			}
		});
        
        btn_register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = jtf_name.getText().trim();
				String email = jtf_email.getText().trim();
				String password = new String(jtf_pass.getPassword());
				String fullname = jtf_fullname.getText().trim();
				
				int dk = JOptionPane.showConfirmDialog(RegisterGUI.this, "Bạn có muốn đăng kí","Comfirm",JOptionPane.YES_NO_OPTION);
				if(dk == JOptionPane.NO_OPTION) {
					return;
				}
				//regex email
				String regex_email = "^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+.)+[a-zA-Z]{2,7}$";
				Pattern p_email = Pattern.compile(regex_email);
				Matcher m_email = p_email.matcher(jtf_email.getText());
				
				//regex password
				String regex_pass = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,20}$";
				char[] pass = jtf_pass.getPassword();
				String passString = new String(pass);
				Pattern p_pass = Pattern.compile(regex_pass);
				Matcher m_pass = p_pass.matcher(passString);
				
				//Kiểm tra thông tin bị trống hay không
				if(username.isEmpty() || email.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
					JOptionPane.showMessageDialog(RegisterGUI.this, "Thông tin không được để trống","Error",JOptionPane.ERROR_MESSAGE);
	            	return;
				}
				// Kiểm tra định dạng email
		        if (!m_email.matches()) {
		            JOptionPane.showMessageDialog(RegisterGUI.this, "Email không đúng định dạng", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		     // Kiểm tra mật khẩu
		        if (!m_pass.matches()) {
		            JOptionPane.showMessageDialog(RegisterGUI.this, "Mật khẩu phải chứa ít nhất 1 chữ cái và 1 số, từ 6-20 ký tự", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        UserDTO userdto = new UserDTO();
		        userdto.setUserName(username);
		        userdto.setUserEmail(email);
		        userdto.setUserPassword(password);
		        userdto.setUserFullName(fullname);
		        userdto.setAdmin(false);
		        
		        UserBLL userbll = new UserBLL();
		        if(userbll.addUser(userdto)) {
		        	JOptionPane.showMessageDialog(RegisterGUI.this, "Đăng kí thành công","Success",JOptionPane.INFORMATION_MESSAGE);
		        	LoginGUI login = new LoginGUI();
		        	login.setVisible(true);
		        	login.setExtendedState(JFrame.MAXIMIZED_BOTH);
		        	dispose();
		        }
		        else {
		        	JOptionPane.showMessageDialog(RegisterGUI.this, "Đăng kí thất bại","Fail",JOptionPane.ERROR_MESSAGE);
		        	return;
		        }
			}
		});
        
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
        RegisterGUI register =  new RegisterGUI();
        register.pack();
        register.setVisible(true);
        register.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
