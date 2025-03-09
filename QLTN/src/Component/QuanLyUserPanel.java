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
import BLL.UserBLL;
import DTO.QuestionDTO;
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


public class QuanLyUserPanel extends JPanel {
	JTable userTable;
	UserBLL userBll;
    DefaultTableModel tableModel;

    public QuanLyUserPanel() {
    	userBll = new UserBLL();
    	setLayout(new BorderLayout());
    	setBackground(Color.WHITE);
    	
    	JPanel btnPanel = new JPanel();
    	btnPanel.setBackground(Color.WHITE);
        FadeButton btnEdit = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Cập Nhập Thông Tin");
        FadeButton btnAdd = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Thêm Người Dùng");
        FadeButton btnDelete = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Xóa Người Dùng");
        FadeButton btnExcel = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Nhập Excel");

        btnPanel.add(btnDelete);
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnExcel);
        
        JLabel title = new JLabel("Quản Lý User", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
    	userTable = new JTable();
    	loadUserTable();
    	add(title, BorderLayout.NORTH);
        add(new JScrollPane(userTable), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
         
        btnDelete.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		  userDelete();
        	  } 
        	} 
        );
        btnAdd.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) { 
      		userAdd();
      	  } 
      	});
        
        btnEdit.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		userEdit();
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

            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();

            while (rowIterator.hasNext()) {

                Row row = rowIterator.next();
                String userName = getCellValueAsString(row.getCell(0));
                String userEmail = getCellValueAsString(row.getCell(1));
                String userPassword = getCellValueAsString(row.getCell(2));
                String userFullName = getCellValueAsString(row.getCell(3));
                if (userName.isEmpty() || userPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ ở dòng: " + (row.getRowNum() + 1), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    continue;
                }


                UserDTO newUser = new UserDTO(0, userName, userEmail, userPassword, userFullName, false);
                boolean isAdded = userBll.addUser(newUser);
                if (!isAdded) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi thêm Tài Khoản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            JOptionPane.showMessageDialog(null, "Nhập dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadUserTable();
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
    private void loadUserTable() {
    	 
        String[] columnNames = { "userID", "userName", "userEmail", "userPassword", "userFullName","isAdmin" };
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable.setModel(tableModel);    	
    	ArrayList<UserDTO> arrListUser= new ArrayList<UserDTO>();
    	arrListUser = userBll.getAllUsers();
    	

    	for(int i =0 ; i< arrListUser.size(); i++) {
    		tableModel.addRow(new Object[] 
    				{
    						arrListUser.get(i).getUserID(),
    						arrListUser.get(i).getUserName(),
    						arrListUser.get(i).getUserEmail(),
    						arrListUser.get(i).getUserPassword(),
    						arrListUser.get(i).getUserFullName(),
    						arrListUser.get(i).isAdmin() ? 1 : 0
    						});
    	}
    	
    }
    private void userDelete() {
    	int selectedRow = userTable.getSelectedRow();
    	if (selectedRow==-1) {
            JOptionPane.showMessageDialog(this, "Vui Lòng Chọn User cần xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
    	try
    	{
    		userBll.deleteUser(Integer.parseInt(userTable.getValueAt(selectedRow, 0).toString()));
    	}
    		
    	catch(Exception e) {
			  JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    	}
    	loadUserTable();
    	
    }
    private void userAdd() {
    	FormAddUser formuser = new FormAddUser();
    	formuser.setLocationRelativeTo(this);
    	formuser.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
            	loadUserTable();
            }
        });

    }
    private void userEdit() {
    	int selectedRow = userTable.getSelectedRow();
    	
    	if (selectedRow==-1) {
            JOptionPane.showMessageDialog(this, "Vui Lòng Chọn User cần sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
    	
	    String userId = userTable.getValueAt(selectedRow, 0).toString();
	    String username = userTable.getValueAt(selectedRow, 1).toString();
	    String email = userTable.getValueAt(selectedRow, 2).toString();
	    String passwrod = userTable.getValueAt(selectedRow, 3).toString();
	    String fullName = userTable.getValueAt(selectedRow, 4).toString();
	    
	    FormEditUser formuser = new FormEditUser();
	    formuser.setUserData(Integer.parseInt(userId),username,  email, passwrod,fullName);
	    formuser.setLocationRelativeTo(this);    	
	
		formuser.addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
	        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
	        	loadUserTable();
	        }
	    });
    	

    }
}
