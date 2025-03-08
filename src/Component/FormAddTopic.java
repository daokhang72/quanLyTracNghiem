package Component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import BLL.AnswerBLL;
import BLL.TopicBLL;
import DTO.AnswerDTO;
import DTO.TopicDTO;

public class FormAddTopic extends JDialog {
	private JTextField jtf_title, jtf_parent, jtf_status;
    private JButton btnSave, btnCancel;
    private TopicBLL topicbll = new TopicBLL();
    
    public FormAddTopic() {
    	setTitle("Thêm chủ đề");
        setSize(400, 300);
        setLayout(new GridBagLayout());
        setModal(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
     
     // Label và TextField cho "tiêu đề chủ đề"
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Tiêu đề của chủ đề:"), gbc);
        gbc.gridx = 1;
        jtf_title = new JTextField(15);
        add(jtf_title, gbc);
        
     // Label và TextField cho "Mối quan hệ"
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Mối quan hệ của chủ đề:"), gbc);
        gbc.gridx = 1;
        jtf_parent = new JTextField(15);
        add(jtf_parent, gbc);
     // Label và TextField cho "Trạng thái của chủ đề"
	    gbc.gridx = 0; gbc.gridy = 3;
	    add(new JLabel("Trạng thái của chủ đề:"), gbc);
	    gbc.gridx = 1;
	    jtf_status = new JTextField(15);
	    add(jtf_status, gbc);
	    
	 // Nút Lưu và Hủy
        gbc.gridx = 0; gbc.gridy = 5;
        btnSave = new JButton("Lưu");
        add(btnSave, gbc);
        gbc.gridx = 1;
        btnCancel = new JButton("Hủy");
        add(btnCancel, gbc);

        btnSave.addActionListener(e -> saveTopic());
        btnCancel.addActionListener(e -> dispose());
    }
    private void saveTopic() {
        String title = jtf_title.getText();
        int tpParent = Integer.parseInt(jtf_parent.getText());
        int status = Integer.parseInt(jtf_status.getText());

        TopicDTO topic = new TopicDTO(0,title,tpParent, status);
        
        if (topicbll.addTopic(topic)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
