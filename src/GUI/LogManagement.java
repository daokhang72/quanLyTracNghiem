package GUI;

import BLL.LogBLL;
import DTO.LogDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class LogManagement extends JFrame {
    private LogBLL logBLL = new LogBLL();
    private JTable logTable = new JTable();

    public LogManagement() {
        setTitle("Quản lý nhật ký hoạt động");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new JScrollPane(logTable), BorderLayout.CENTER);
        refreshLogs();
    }

    private void refreshLogs() {
        try {
            List<LogDTO> logs = logBLL.getAllLogs();
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new Object[]{"ID", "Nội dung", "Người dùng", "Mã đề", "Ngày"});

            for (LogDTO log : logs) {
                model.addRow(new Object[]{
                        log.getLogID(),
                        log.getLogContent(),
                        log.getLogUserID(),
                        log.getLogExID(),
                        log.getLogDate()
                });
            }
            logTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu nhật ký!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LogManagement().setVisible(true));
    }
}