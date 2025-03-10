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
import BLL.ResultBLL;
import BLL.UserBLL;
import DTO.QuestionDTO;
import DTO.ResultDTO;
import DTO.UserDTO;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;


public class QuanLyResult extends JPanel {
	JTable userTable;
	ResultBLL resultBll;
    DefaultTableModel tableModel;

    public QuanLyResult() {
    	resultBll = new ResultBLL();
    	setLayout(new BorderLayout());
    	setBackground(Color.WHITE);
    	
    	JPanel btnPanel = new JPanel();
    	btnPanel.setBackground(Color.WHITE);
        FadeButton btnEdit = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Sửa Điểm");

        btnPanel.add(btnEdit);

        JLabel title = new JLabel("Quản Lý User", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
    	userTable = new JTable();
    	loadUserTable();
    	add(title, BorderLayout.NORTH);
        add(new JScrollPane(userTable), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
         
        
        btnEdit.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		  resultEdit();
        	  } 
        	});

    }
   
    private void loadUserTable() {
    	 
        String[] columnNames = {"RsNum", "userID", "exCode", "rsAnswers", "rsMark","rsDate" };
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable.setModel(tableModel);    	
    	ArrayList<ResultDTO> arrListResult= new ArrayList<ResultDTO>();
    	arrListResult = resultBll.getAllResult();
    	

    	for(int i =0 ; i< arrListResult.size(); i++) {
    		tableModel.addRow(new Object[] 
    				{
    					arrListResult.get(i).getRsNum(),
    					arrListResult.get(i).getUserID(),
    					arrListResult.get(i).getExCode(),
    					arrListResult.get(i).getRsAnswers(),
    					arrListResult.get(i).getRsMark(),
    					arrListResult.get(i).getRsDate(),
    						});
    	}
    	
    }
    private void resultDelete() {
    	int selectedRow = userTable.getSelectedRow();
    	if (selectedRow==-1) {
            JOptionPane.showMessageDialog(this, "Vui Lòng Chọn User cần xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
    	try
    	{
    		resultBll.deleteResult(Integer.parseInt(userTable.getValueAt(selectedRow, 0).toString()));
    	}
    		
    	catch(Exception e) {
			  JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    	}
    	loadUserTable();
    	
    }
    private void resultEdit() {
    	int selectedRow = userTable.getSelectedRow();
    	
    	if (selectedRow==-1) {
            JOptionPane.showMessageDialog(this, "Vui Lòng Chọn User cần sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
    	
	    String userId = userTable.getValueAt(selectedRow, 0).toString();
	    
	    FormEditResult form = new FormEditResult();
	    form.setUserData(Integer.parseInt(userId));
	    form.setLocationRelativeTo(this);    	
	
	    form.addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
	        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
	        	loadUserTable();
	        }
	    });
    	

    }
}
