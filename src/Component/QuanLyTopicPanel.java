package Component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import BLL.AnswerBLL;
import BLL.TopicBLL;
import DTO.AnswerDTO;
import DTO.TopicDTO;

public class QuanLyTopicPanel extends JPanel{
	private JTable topicTable;
	private TopicBLL topicbll = new TopicBLL();
	
	public QuanLyTopicPanel() {
		setLayout(new BorderLayout());
		JLabel title = new JLabel("Quản lý chủ đề",SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		add(title,BorderLayout.NORTH);
		
		//bảng hiển thị đáp án
		topicTable = new JTable();
		loadTopicTable();
		add(new JScrollPane(topicTable),BorderLayout.CENTER);
		
		//hiener thị các nút chức năng
		JPanel btnPanel = new JPanel();
		FadeButton btnAdd = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Thêm chủ đề");
		FadeButton btnEdit = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Sửa chủ để");
		FadeButton btnDelete = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Xóa chủ đề");
		FadeButton btnSearch = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Tìm kiếm chủ đề");
		FadeButton btnRefresh = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Làm mới");
		
		btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnSearch);
        btnPanel.add(btnRefresh);
        
        add(btnPanel, BorderLayout.SOUTH);
        
        btnAdd.addActionListener(e -> AddTopic());
        btnDelete.addActionListener(e -> DeleteTopic());
        btnEdit.addActionListener(e -> EditTopic());
        btnSearch.addActionListener(e -> SearchTopic());
        btnRefresh.addActionListener(e -> loadTopicTable());
	}
	private void loadTopicTable() {
		DefaultTableModel model = new DefaultTableModel();
        topicTable.setModel(model);
        String[] columnNames = { "Topic ID","Tiêu đề chủ đề", "Topic Parent", "Topic Status" };
        model.setColumnIdentifiers(columnNames);
        
        ArrayList<TopicDTO> list = topicbll.getAllTopic();
        for (TopicDTO ans : list) {
            model.addRow(new Object[]{ ans.getTpID(), ans.getTpTitle(), ans.getTpParent(), ans.getTpStatus()});
        }
	}
	public void AddTopic() {
		FormAddTopic form = new FormAddTopic();
        form.setLocationRelativeTo(this);
        form.setVisible(true);
        form.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
            	loadTopicTable();
            }
        });
	}
	public void DeleteTopic() {
		int index = topicTable.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chủ đề để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa chủ đề này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int tpid = Integer.parseInt(topicTable.getValueAt(index, 0).toString());
            if (topicbll.deleteTopic(tpid)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTopicTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
	}
	public void EditTopic() {
		int index = topicTable.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chủ đề để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int tpid = Integer.parseInt(topicTable.getValueAt(index, 0).toString());
        FormEditTopic form = new FormEditTopic(tpid);
        form.setLocationRelativeTo(this);
        form.setVisible(true);
        form.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                loadTopicTable();;
            }
        });
	}
	public void SearchTopic() {
	    String keyword = JOptionPane.showInputDialog(this, "Nhập từ khóa tìm kiếm:");
	    if (keyword != null && !keyword.trim().isEmpty()) {
	        DefaultTableModel model = (DefaultTableModel) topicTable.getModel(); // Lấy model của JTable
	        model.setRowCount(0); // Xóa dữ liệu cũ

	        ArrayList<TopicDTO> searchResults = topicbll.searchTopic(keyword);
	        for (TopicDTO topic : searchResults) {
	            model.addRow(new Object[]{ topic.getTpID(), topic.getTpTitle(), topic.getTpParent(), topic.getTpStatus() });
	        }

	        if (searchResults.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Không tìm thấy chủ đề nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	        }
	    }
	}
	
}
