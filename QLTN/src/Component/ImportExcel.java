package Component;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import BLL.QuestionBLL;
import DTO.QuestionDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ImportExcel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnImport;

    public ImportExcel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Nhập câu hỏi từ Excel", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nội dung", "Chủ đề", "Mức độ"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnImport = new JButton("Chọn file Excel");
        add(btnImport, BorderLayout.SOUTH);

        btnImport.addActionListener(e -> importExcel());
    }

    private void importExcel() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(selectedFile);
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
}
