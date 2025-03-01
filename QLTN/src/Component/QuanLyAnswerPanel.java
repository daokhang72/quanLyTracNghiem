package Component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.lang.runtime.SwitchBootstraps;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import BLL.AnswerBLL;
import DTO.AnswerDTO;

public class QuanLyAnswerPanel extends JPanel{
	private JTable answerTable;
	private AnswerBLL answerbll = new AnswerBLL();
	
	public QuanLyAnswerPanel() {
		setLayout(new BorderLayout());
		JLabel title = new JLabel("Quản lý đáp án",SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		add(title,BorderLayout.NORTH);
		
		//bảng hiển thị đáp án
		answerTable = new JTable();
		loadAnswerTable();
		add(new JScrollPane(answerTable),BorderLayout.CENTER);
		
		//hiener thị các nút chức năng
		JPanel btnPanel = new JPanel();
        FadeButton btnAdd = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Thêm đáp án");
        FadeButton btnEdit = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Sửa đáp án");
        FadeButton btnDelete = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Xóa đáp án");
        
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        
        add(btnPanel, BorderLayout.SOUTH);
        
        btnAdd.addActionListener(e -> AddAnswer());
        btnDelete.addActionListener(e -> DeleteAnswer());
        btnEdit.addActionListener(e -> EditAnswer());
	}
	private void loadAnswerTable() {
		DefaultTableModel model = new DefaultTableModel();
        answerTable.setModel(model);
        String[] columnNames = { "Answer ID", "Question ID ", "Nội dung", "Hình ảnh", "IsRight" };
        model.setColumnIdentifiers(columnNames);
        
        ArrayList<AnswerDTO> list = answerbll.dsAnswer();
        for (AnswerDTO ans : list) {
            model.addRow(new Object[]{ ans.getAwID(), ans.getQID(), ans.getAwContent(), ans.getAwPictures(), ans.isRight() ? "1" : "0" , ans.getAwStatus()});
        }
	}
	private void AddAnswer() {
		FormAddAnswer form = new FormAddAnswer();
        form.setLocationRelativeTo(this);
        form.setVisible(true);
        form.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                loadAnswerTable();
            }
        });
	}
	private void DeleteAnswer() {
		int index = answerTable.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đáp án để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa đáp án này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int awid = Integer.parseInt(answerTable.getValueAt(index, 0).toString());
            if (answerbll.deleteAnswer(awid)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadAnswerTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
	}
	private void EditAnswer() {
		int index = answerTable.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đáp án để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int awid = Integer.parseInt(answerTable.getValueAt(index, 0).toString());
        FormEditAnswer form = new FormEditAnswer(awid);
        form.setLocationRelativeTo(this);
        form.setVisible(true);
        form.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                loadAnswerTable();
            }
        });
	}
}
