package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import BLL.QuestionBLL;
import DAL.DatabaseHelper;
import DTO.QuestionDTO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class QuestionManager extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtContent, txtTopic;
    private JComboBox<String> cbLevel;
    private QuestionBLL questionBLL;

    public QuestionManager() {
        try {
            Connection conn = DatabaseHelper.getConnection();
            this.questionBLL = new QuestionBLL(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Quản lý Câu Hỏi", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Nội dung", "Chủ đề", "Mức độ"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.add(new JLabel("Nội dung:"));
        txtContent = new JTextField();
        inputPanel.add(txtContent);

        inputPanel.add(new JLabel("Chủ đề:"));
        txtTopic = new JTextField();
        inputPanel.add(txtTopic);

        inputPanel.add(new JLabel("Mức độ:"));
        cbLevel = new JComboBox<>(new String[]{"Dễ", "Trung bình", "Khó"});
        inputPanel.add(cbLevel);
        add(inputPanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        add(buttonPanel, BorderLayout.SOUTH);

        loadQuestions();

        btnEdit.addActionListener(e -> editQuestion());
        btnDelete.addActionListener(e -> deleteQuestion());
        btnAdd.addActionListener(e -> {
            QuestionForm form = new QuestionForm((JFrame) SwingUtilities.getWindowAncestor(this), this);
//            form.setVisible(true);
        });


    }

    void loadQuestions() {
        List<QuestionDTO> questions = questionBLL.getAllQuestions();
        tableModel.setRowCount(0);
        for (QuestionDTO q : questions) {
            tableModel.addRow(new Object[]{q.getQID(), q.getQContent(), q.getQTopicID(), q.getQLevel()});
        }
    }

    private void addQuestion() {
        if (txtContent.getText().trim().isEmpty() || txtTopic.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String content = txtContent.getText();
        int topicID;
        try {
            topicID = Integer.parseInt(txtTopic.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Chủ đề phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String level = cbLevel.getSelectedItem().toString();
        
        QuestionDTO question = new QuestionDTO(0, content, "", topicID, level, 1);
        if (questionBLL.addQuestion(question)) {
            loadQuestions();
            JOptionPane.showMessageDialog(null, "Thêm câu hỏi thành công!");
            txtContent.setText("");
            txtTopic.setText("");
            cbLevel.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm câu hỏi.");
        }
    }



    private void editQuestion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn câu hỏi để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int qID = (int) tableModel.getValueAt(selectedRow, 0);
            String content = txtContent.getText().trim().isEmpty() ? (String) tableModel.getValueAt(selectedRow, 1) : txtContent.getText().trim();
            int topicID;
            
            try {
                topicID = txtTopic.getText().trim().isEmpty() ? (int) tableModel.getValueAt(selectedRow, 2) : Integer.parseInt(txtTopic.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Chủ đề phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String level = cbLevel.getSelectedItem().toString();

            QuestionDTO question = new QuestionDTO(qID, content, "", topicID, level, 1);
            boolean success = questionBLL.updateQuestion(question);

            if (success) {
                loadQuestions();
                JOptionPane.showMessageDialog(this, "Cập nhật câu hỏi thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật câu hỏi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi sửa câu hỏi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void deleteQuestion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn câu hỏi để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa câu hỏi này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int qID = (int) tableModel.getValueAt(selectedRow, 0);
            boolean success = questionBLL.deleteQuestion(qID);
            if (success) {
                loadQuestions();
                JOptionPane.showMessageDialog(this, "Xóa câu hỏi thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa câu hỏi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý Câu Hỏi");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new QuestionManager());
        frame.setVisible(true);
        }

    
}
