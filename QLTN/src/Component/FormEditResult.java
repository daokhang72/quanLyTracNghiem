package Component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import BLL.ResultBLL;
import BLL.UserBLL;
import DTO.ResultDTO;
import DTO.UserDTO;


public class FormEditResult extends JFrame{
	int  userId;
	JTextField nameValueLabel;
	JTextField emailField;
	JTextField userPasswordField;
	JTextField fullNameField;
	ResultBLL rsBll;
	public FormEditResult() {
		rsBll = new ResultBLL();
		setSize(500, 500);
    	setBackground(Color.WHITE);
    	setVisible(true);
    	
    	JLabel title = new JLabel("Cập Nhập User", SwingConstants.CENTER);
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
        JLabel userLabel = new JLabel("mark");
        userLabel.setFont(labelFont);
        mainPanel.add(userLabel, gbc);

        gbc.gridx = 2;
        nameValueLabel = new JTextField("", 15);
        nameValueLabel.setFont(fieldFont);
        mainPanel.add(nameValueLabel, gbc);

        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        FadeButton btnSave = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"THêm");
        btnPanel.add(btnSave);

        add(mainPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        
        btnSave.addActionListener(new ActionListener() { 
    	  public void actionPerformed(ActionEvent e) {
    		  if (nameValueLabel.getText().toString()==null || nameValueLabel.getText().toString().length() ==0) {
				  JOptionPane.showMessageDialog(mainPanel, "Vui Lòng Nhập Điêm", "Lỗi", JOptionPane.ERROR_MESSAGE);
				  return;
    		  }
    		  String username = nameValueLabel.getText();

    		  ResultDTO rs = new ResultDTO(userId,Double.parseDouble(username));
    		  rsBll.updateResultMark(rs);
    		  dispose();
    	  } 
    	});
    
	}
	
	public void setUserData(int id) {
		this.userId = id;

    }
}
