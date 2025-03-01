package Component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import BLL.AnswerBLL;
import DTO.AnswerDTO;


public class FormEditAnswer extends JDialog {
    private JTextField txtContent;
    private JTextField txtPictures;
    private JCheckBox chkIsRight;
    private JButton btnSave, btnCancel;
    private AnswerBLL answerBLL;
    private int awID;

    public FormEditAnswer(int awID) {
        this.awID = awID;
        answerBLL = new AnswerBLL();

        setTitle("Chỉnh sửa đáp án");
        setSize(400, 250);
        setLayout(new GridLayout(4, 2, 10, 10));
        setModal(true);

        // Lấy dữ liệu đáp án hiện tại
        AnswerDTO answer = answerBLL.getAnswerByID(awID);
        if (answer == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy đáp án!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Các thành phần nhập liệu
        add(new JLabel("Nội dung:"));
        txtContent = new JTextField(answer.getAwContent());
        add(txtContent);

        add(new JLabel("Hình ảnh (URL):"));
        txtPictures = new JTextField(answer.getAwPictures());
        add(txtPictures);

        add(new JLabel("Đáp án đúng:"));
        chkIsRight = new JCheckBox();
        chkIsRight.setSelected(answer.isRight());
        add(chkIsRight);

        // Nút lưu và hủy
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        add(btnSave);
        add(btnCancel);

        // Sự kiện nút lưu
        btnSave.addActionListener(e -> saveAnswer());

        // Sự kiện nút hủy
        btnCancel.addActionListener(e -> dispose());

        setLocationRelativeTo(null);
    }

    private void saveAnswer() {
        String content = txtContent.getText().trim();
        String pictures = txtPictures.getText().trim();
        boolean isRight = chkIsRight.isSelected();

        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nội dung không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AnswerDTO updatedAnswer = new AnswerDTO(awID, 0, content, pictures, isRight, 1);
        if (answerBLL.updateAnser(updatedAnswer)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}

