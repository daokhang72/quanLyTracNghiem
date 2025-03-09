package Component;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import BLL.QuestionBLL;
import DTO.QuestionDTO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionManager extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtContent, txtTopic;
    private JComboBox<String> cbLevel,cbSearchType;
    private QuestionBLL questionBLL;
    private JTextField txtSearch;
    private JButton btnImport;

    public QuestionManager() {
        this.questionBLL = new QuestionBLL();
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Quản lý Câu Hỏi", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        // Panel tìm kiếm
        JPanel searchPanel = new JPanel();
        txtSearch = new JTextField(20);
        cbSearchType = new JComboBox<>(new String[]{"ID", "Nội dung", "Chủ đề"});
        JButton btnSearch = new JButton("Tìm kiếm");
        
        searchPanel.add(new JLabel("Tìm kiếm theo:"));
        searchPanel.add(cbSearchType);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        
        String[] columnNames = {"ID", "Nội dung", "Chủ đề", "Mức độ"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
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
        JButton btnExcel = new JButton("Nhập Excel");
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnExcel);
        add(buttonPanel, BorderLayout.SOUTH);

        loadQuestions();

        btnEdit.addActionListener(e -> editQuestion());
        btnDelete.addActionListener(e -> deleteQuestion());
        btnAdd.addActionListener(e -> {
            QuestionForm form = new QuestionForm((JFrame) SwingUtilities.getWindowAncestor(this), this);
        });

        
        btnSearch.addActionListener(
		new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				performSearch();
			}
		});
        
        btnExcel.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx", "xls"));

            int returnValue = fileChooser.showOpenDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(this, "Đã chọn file: " + selectedFile.getAbsolutePath());
                
                importExcel(selectedFile);
            }
        });
    }

    private void importExcel(File selectedFile) {
        try (InputStream fis = Files.newInputStream(selectedFile.toPath());
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            tableModel.setRowCount(0);

            QuestionBLL questionBLL = new QuestionBLL();
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String content = getCellValueAsString(row.getCell(1));
                int topicID = getCellValueAsInt(row.getCell(2), row.getRowNum());
                String level = getCellValueAsString(row.getCell(3));

                if (content.isEmpty() || topicID == -1 || level.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ ở dòng: " + (row.getRowNum() + 1), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                tableModel.addRow(new Object[]{null, content, topicID, level});

                QuestionDTO newQuestion = new QuestionDTO(0, content, "", topicID, level, 1);
                boolean isAdded = questionBLL.addQuestion(newQuestion);
                if (!isAdded) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi thêm câu hỏi vào CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            JOptionPane.showMessageDialog(null, "Nhập dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> "";
        };
    }

    private int getCellValueAsInt(Cell cell, int rowNum) {
        try {
            if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                return (int) cell.getNumericCellValue();
            } else if (cell != null && cell.getCellType() == CellType.STRING) {
                return Integer.parseInt(cell.getStringCellValue().trim());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ ở dòng: " + (rowNum + 1), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }
    private void performSearch() {
        String keyword = txtSearch.getText().trim();
        String searchType = cbSearchType.getSelectedItem().toString();
        ArrayList<QuestionDTO> results;

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (searchType) {
            case "ID" -> {
                try {
                    int id = Integer.parseInt(keyword);
                    results = questionBLL.searchQuestions(null,id,0);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            case "Nội dung" -> results = questionBLL.searchQuestions(keyword,0,0);
            case "Chủ đề" -> results = questionBLL.searchQuestions(keyword,0,0);
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
    void loadQuestions() {
        List<QuestionDTO> questions = questionBLL.getAllQuestion();
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
