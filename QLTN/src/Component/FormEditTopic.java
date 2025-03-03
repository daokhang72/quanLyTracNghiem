package Component;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import BLL.TopicBLL;
import DTO.AnswerDTO;
import DTO.TopicDTO;

public class FormEditTopic extends JDialog{
	private JTextField jtf_title, jtf_parent, jtf_status;
	private JButton btnSave, btnCancel;
    private TopicBLL topicbll;
    private int tpID;
    
    public FormEditTopic(int tpID) {
    	this.tpID = tpID;
    	topicbll = new TopicBLL();
    	
    	setTitle("Chỉnh sửa chủ đề");
        setSize(400, 250);
        setLayout(new GridLayout(5, 2, 10, 10));
        setModal(true);
        
     // Lấy dữ liệu đáp án hiện tại
        TopicDTO topic = topicbll.getTopicByID(tpID);
        if (topic == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy chủ đề!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        
     // Các thành phần nhập liệu
        add(new JLabel("Chủ đề:"));
        jtf_title = new JTextField(topic.getTpTitle());
        add(jtf_title);
        
        add(new JLabel("Mối quan hệ:"));
        jtf_parent = new JTextField(String.valueOf(topic.getTpParent()));
        add(jtf_parent);
        
        add(new JLabel("Trạng thái hoạt động:"));
        jtf_status = new JTextField(String.valueOf(topic.getTpStatus()));
        add(jtf_status);
        
     // Nút lưu và hủy
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        add(btnSave);
        add(btnCancel);
        
     // Sự kiện nút lưu
        btnSave.addActionListener(e -> saveTopic());

        // Sự kiện nút hủy
        btnCancel.addActionListener(e -> dispose());

        setLocationRelativeTo(null);
    }
    private void saveTopic() {
        String title = jtf_title.getText().trim();
        int parent = Integer.parseInt(jtf_parent.getText().trim());
        int status = Integer.parseInt(jtf_status.getText().trim());
        

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tiêu đề không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TopicDTO updatedTopic = new TopicDTO(tpID, title, parent,status);
        if (topicbll.updateTopic(updatedTopic)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
