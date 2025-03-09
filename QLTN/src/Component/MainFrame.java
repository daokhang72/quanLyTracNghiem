package Component;

import javax.swing.*;

import BLL.QuestionBLL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class MainFrame extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    public MainFrame() {
    	
        JLabel title = new JLabel("Quản lý câu hỏi", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);
        
        // Sidebar menu
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5, 1));
        
        JButton btnManage = new JButton("Thêm/Sửa/Xóa");
        JButton btnImport = new JButton("Nhập từ Excel");
        JButton btnSearch = new JButton("Tìm kiếm Câu Hỏi");
        JButton btnAssign = new JButton("Gán vào Đề Thi");
        
        menuPanel.add(btnManage);
        menuPanel.add(btnImport);
        menuPanel.add(btnSearch);
        menuPanel.add(btnAssign);
        
        // Main content panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(new QuestionManager(), "manage");
        mainPanel.add(new ImportExcel(), "import");
        mainPanel.add(new SearchQuestion(), "search");
        mainPanel.add(new AssignQuestion(), "assign");

        
        // Event handling
        btnManage.addActionListener(e -> cardLayout.show(mainPanel, "manage"));
        btnImport.addActionListener(e -> cardLayout.show(mainPanel, "import"));
        btnSearch.addActionListener(e -> cardLayout.show(mainPanel, "search"));
        btnAssign.addActionListener(e -> cardLayout.show(mainPanel, "assign"));
        
        // Main frame layout
        setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}


