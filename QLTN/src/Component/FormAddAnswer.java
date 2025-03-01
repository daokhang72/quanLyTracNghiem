package Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import BLL.AnswerBLL;
import DTO.AnswerDTO;

public class FormAddAnswer extends JDialog {
    private JTextField txtQid, txtContent, txtPicture, txtStatus;
    private JCheckBox chkIsCorrect;
    private JButton btnSave, btnCancel;
    private AnswerBLL answerBll = new AnswerBLL();

    public FormAddAnswer() {
        setTitle("Thêm đáp án");
        setSize(400, 300);
        setLayout(new GridBagLayout());
        setModal(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); 

        // Label và TextField cho "Câu hỏi ID"
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Câu hỏi ID:"), gbc);
        gbc.gridx = 1;
        txtQid = new JTextField(15);
        add(txtQid, gbc);

        // Label và TextField cho "Nội dung đáp án"
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Nội dung đáp án:"), gbc);
        gbc.gridx = 1;
        txtContent = new JTextField(15);
        add(txtContent, gbc);

        // Label và TextField cho "Hình ảnh (URL)"
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Hình ảnh (URL):"), gbc);
        gbc.gridx = 1;
        txtPicture = new JTextField(15);
        add(txtPicture, gbc);

        // Label và CheckBox cho "Là đáp án đúng"
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Là đáp án đúng:"), gbc);
        gbc.gridx = 1;
        chkIsCorrect = new JCheckBox();
        add(chkIsCorrect, gbc);

        // Label và TextField cho "Trạng thái của đáp án"
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Trạng thái của đáp án:"), gbc);
        gbc.gridx = 1;
        txtStatus = new JTextField(15);
        add(txtStatus, gbc);

        // Nút Lưu và Hủy
        gbc.gridx = 0; gbc.gridy = 5;
        btnSave = new JButton("Lưu");
        add(btnSave, gbc);
        gbc.gridx = 1;
        btnCancel = new JButton("Hủy");
        add(btnCancel, gbc);

        btnSave.addActionListener(e -> saveAnswer());
        btnCancel.addActionListener(e -> dispose());
    }

    private void saveAnswer() {
        int qid = Integer.parseInt(txtQid.getText());
        String content = txtContent.getText();
        String picture = txtPicture.getText();
        boolean isCorrect = chkIsCorrect.isSelected();
        int status = Integer.parseInt(txtStatus.getText());

        AnswerDTO answer = new AnswerDTO(0, qid, content, picture, isCorrect, status);
        
        if (answerBll.addAnswer(answer)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}


