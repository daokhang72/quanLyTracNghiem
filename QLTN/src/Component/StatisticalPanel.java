package Component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BLL.StatisticalBLL;

public class StatisticalPanel extends JPanel {
    private JTable statisticalTable;
    private DefaultTableModel tableModel;
    private StatisticalBLL statisticalBLL;

    public StatisticalPanel() {
        statisticalBLL = new StatisticalBLL();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        
        JLabel title = new JLabel("Quản Lý Thống Kê", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        
        tableModel = new DefaultTableModel();
        statisticalTable = new JTable(tableModel);
        add(new JScrollPane(statisticalTable), BorderLayout.CENTER);

        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);

        
        FadeButton btnUserStats = new FadeButton(new Color(230, 247, 230), new Color(0, 150, 136), new Color(0, 0, 0), "Thống kê người dùng");
        FadeButton btnTestStats = new FadeButton(new Color(230, 247, 230), new Color(0, 150, 136), new Color(0, 0, 0), "Thống kê đề thi");
        FadeButton btnPassFailStats = new FadeButton(new Color(230, 247, 230), new Color(0, 150, 136), new Color(0, 0, 0), "Thống kê số lượng pass/fail");
        FadeButton btnLogStats = new FadeButton(new Color(230, 247, 230), new Color(0, 150, 136), new Color(0, 0, 0), "Thống kê số lượng truy cập");

        btnPanel.add(btnUserStats);
        btnPanel.add(btnTestStats);
        btnPanel.add(btnPassFailStats);
        btnPanel.add(btnLogStats);
        add(btnPanel, BorderLayout.SOUTH);

        
        btnUserStats.addActionListener(e -> loadUserStatistics());
        btnTestStats.addActionListener(e -> loadTestStatistics());
        btnPassFailStats.addActionListener(e -> loadPassFailStatistics());
        btnLogStats.addActionListener(e -> loadLogStatistics());
    }

    
    private void loadUserStatistics() {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new Object[]{"User ID", "Username", "Email", "Full Name", "Is Admin"});

        ArrayList<Object[]> userList = statisticalBLL.getAllUsers();
        int totalUsers = userList.size(); 
        int totalAdmins = 0;
        int totalParticipants = 0;

        for (Object[] user : userList) {
            tableModel.addRow(user);
            if ((int) user[4] == 1) {
                totalAdmins++;
            } else {
                totalParticipants++;
            }
        }

        // Thêm dòng tổng hợp vào cuối bảng
        tableModel.addRow(new Object[]{"Tổng cộng", "", "", "", ""});
        tableModel.addRow(new Object[]{"Tổng số người dùng", totalUsers, "", "", ""});
        tableModel.addRow(new Object[]{"Số lượng Admin", totalAdmins, "", "", ""});
        tableModel.addRow(new Object[]{"Số lượng thí sinh", totalParticipants, "", "", ""});
    }



    
    private void loadTestStatistics() {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new Object[]{"Mã đề", "Tiêu đề", "Số câu hỏi", "Tổng thời gian", "Số người dự thi"});

        ArrayList<Object[]> testList = statisticalBLL.getAllTests();

        for (Object[] row : testList) {
            tableModel.addRow(row);
        }
    }
    
    private void loadPassFailStatistics() {
        String testCode = JOptionPane.showInputDialog(this, "Nhập mã đề thi:");
        
        if (testCode == null) {
            return; 
        }

        testCode = testCode.trim().toUpperCase(); 

        if (testCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã đề thi!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new Object[]{"User ID", "Họ tên", "Môn học", "Điểm số", "Kết quả"});

        ArrayList<Object[]> resultList = statisticalBLL.getPassFailParticipants(testCode);

        if (resultList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu cho mã đề: " + testCode, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Object[] row : resultList) {
            tableModel.addRow(row);
        }
    }


    
    private void loadLogStatistics() {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new Object[]{"Thông tin", "Giá trị"});
        
        int logCount = statisticalBLL.getLogStatistics();
        tableModel.addRow(new Object[]{"Tổng số lượt truy cập", logCount});
    }
}
