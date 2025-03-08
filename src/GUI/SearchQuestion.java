package GUI;

import BLL.QuestionBLL;
import DTO.QuestionDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SearchQuestion extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JComboBox<String> cbSearchType;
    private JButton btnSearch;
    private QuestionBLL questionBLL;
    
    public SearchQuestion() {
        setLayout(new BorderLayout());
        questionBLL = new QuestionBLL();
        
        // Tiêu đề
        JLabel title = new JLabel("Tìm kiếm câu hỏi", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);
        
        // Panel tìm kiếm
        JPanel searchPanel = new JPanel();
        txtSearch = new JTextField(20);
        cbSearchType = new JComboBox<>(new String[]{"ID", "Nội dung", "Chủ đề"});
        btnSearch = new JButton("Tìm kiếm");
        
        searchPanel.add(new JLabel("Tìm kiếm theo:"));
        searchPanel.add(cbSearchType);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        add(searchPanel, BorderLayout.NORTH);
        
        // Bảng kết quả tìm kiếm
        String[] columnNames = {"ID", "Nội dung", "Chủ đề", "Mức độ"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Xử lý tìm kiếm
        btnSearch.addActionListener(this::performSearch);
    }

    private void performSearch(ActionEvent e) {
        String keyword = txtSearch.getText().trim();
        String searchType = cbSearchType.getSelectedItem().toString();
        List<QuestionDTO> results;

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (searchType) {
            case "ID" -> {
                try {
                    int id = Integer.parseInt(keyword);
                    results = questionBLL.getQuestionsByID(id);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            case "Nội dung" -> results = questionBLL.getQuestionsByContent(keyword);
            case "Chủ đề" -> results = questionBLL.getQuestionsByTopic(keyword);
            default -> {
                JOptionPane.showMessageDialog(this, "Loại tìm kiếm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Cập nhật kết quả tìm kiếm vào bảng
        tableModel.setRowCount(0);
        if (results != null && !results.isEmpty()) {
            for (QuestionDTO q : results) {
                tableModel.addRow(new Object[]{q.getQID(), q.getQContent(), q.getQTopicID(), q.getQLevel()});
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tìm kiếm câu hỏi");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SearchQuestion());
        frame.setVisible(true);
    }
}
