package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DAL.DatabaseHelper;

public class AssignQuestion extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> cbExam;
    private JButton btnAssign;
    private Connection conn;

    public AssignQuestion() {
        try {
            conn = DatabaseHelper.getConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Gán câu hỏi vào đề thi", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel examPanel = new JPanel();
        examPanel.add(new JLabel("Chọn đề thi:"));
        cbExam = new JComboBox<>();
        loadExamList();
        examPanel.add(cbExam);
        add(examPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Nội dung", "Chủ đề", "Mức độ"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        btnAssign = new JButton("Thêm vào đề thi");
        buttonPanel.add(btnAssign);
        add(buttonPanel, BorderLayout.SOUTH);

        btnAssign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assignQuestionToExam();
            }
        });
        loadQuestions();
    }

    private void loadExamList() {
        try {
            String sql = "SELECT testCode FROM exams";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cbExam.addItem(rs.getString("testCode")); // Thay thế examName bằng testCode
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi tải danh sách đề thi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    
    private void loadQuestions() {
        try {
            String sql = "SELECT qID, qContent, qTopicID, qLevel FROM questions";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("qID"),
                    rs.getString("qContent"),
                    rs.getInt("qTopicID"),
                    rs.getString("qLevel")
                });
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi tải danh sách câu hỏi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    
    private void assignQuestionToExam() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String exam = cbExam.getSelectedItem().toString();
            String questionID = tableModel.getValueAt(selectedRow, 0).toString();
            try {
                String sql = "INSERT INTO exam_questions (testCode, questionID) VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, exam); // testCode thay vì examName
                pstmt.setString(2, questionID);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Đã thêm câu hỏi vào " + exam);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm câu hỏi vào đề thi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn câu hỏi!");
        }
    }

}
