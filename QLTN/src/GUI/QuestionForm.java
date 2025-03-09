package GUI;

import javax.swing.*;

import BLL.QuestionBLL;
import DTO.QuestionDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionForm extends JDialog {
    private JTextField contentField, pictureField;
    private JComboBox<String> topicBox, levelBox;
    private JButton saveButton, cancelButton;
    private QuestionManager questionManager; 


    public QuestionForm(JFrame parent, QuestionManager questionManager) {
    	super(parent, "Thêm/Sửa Câu Hỏi", true);
        this.questionManager = questionManager; 
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2));

        // Form nhập
        add(new JLabel("Nội dung:"));
        contentField = new JTextField();
        add(contentField);

        add(new JLabel("Hình ảnh (URL):"));
        pictureField = new JTextField();
        add(pictureField);

        add(new JLabel("Chủ đề:"));
        topicBox = new JComboBox<>(new String[]{"Toán", "Lý", "Hóa"});
        add(topicBox);

        add(new JLabel("Mức độ:"));
        levelBox = new JComboBox<>(new String[]{"Dễ", "Trung bình", "Khó"});
        add(levelBox);

        // Nút lưu và hủy
        saveButton = new JButton("Lưu");
        cancelButton = new JButton("Hủy");
        add(saveButton);
        add(cancelButton);
        
        saveButton.addActionListener(e -> {
            String content = contentField.getText().trim();
            String picture = pictureField.getText().trim();
            int topicID = topicBox.getSelectedIndex() + 1;  
            String level = levelBox.getSelectedItem().toString();

            if (content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nội dung câu hỏi không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            QuestionDTO newQuestion = new QuestionDTO(0, content, picture, topicID, level, 1);
            QuestionBLL questionBLL = new QuestionBLL();
            
            boolean success = questionBLL.addQuestion(newQuestion);
            if (success) {
                JOptionPane.showMessageDialog(this, "Thêm câu hỏi thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                if (questionManager != null) {
                    questionManager.loadQuestions(); 
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm câu hỏi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  
            }
        });


        setVisible(true);
    }

}
