package Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BLL.UserBLL;
import DTO.UserDTO;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class QuanLyUserPanel extends JPanel {
	JTable userTable;
	UserBLL userBll;
    public QuanLyUserPanel() {
    	userBll = new UserBLL();
    	setLayout(new BorderLayout());
    	setBackground(Color.WHITE);
    	
    	JPanel btnPanel = new JPanel();
    	btnPanel.setBackground(Color.WHITE);
        FadeButton btnEdit = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Cập Nhập Thông Tin");
        FadeButton btnAdd = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Thêm Người Dùng");
        FadeButton btnDelete = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Xóa Người Dùng");
        
        btnPanel.add(btnDelete);
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        
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
        	}
      );

    }
    private void loadUserTable() {
    	DefaultTableModel model = new DefaultTableModel();
    	userTable.setModel(model);    	 
        String[] columnNames = { "userID", "userName", "userEmail", "userPassword", "userFullName" };
    	ArrayList<UserDTO> arrListUser= new ArrayList<UserDTO>();
    	arrListUser = userBll.getAllUsers();
    	
    	for(int i =0 ; i< columnNames.length; i++) {
    		model.addColumn(columnNames[i]);
    	}
    	for(int i =0 ; i< arrListUser.size(); i++) {
    		model.addRow(new Object[] 
    				{
    						arrListUser.get(i).getUserID(),
    						arrListUser.get(i).getUserName(),
    						arrListUser.get(i).getUserEmail(),
    						arrListUser.get(i).getUserPassword(),
    						arrListUser.get(i).getUserPassword(),
    						});
    	}
    	
    }
    private void userDelete() {
    	int index = userTable.getSelectedRow();
    	userBll.deleteUser(Integer.parseInt(userTable.getValueAt(index, 0).toString()));
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
    	if (selectedRow != -1) {
    	    String userId = userTable.getValueAt(selectedRow, 0).toString();
    	    String username = userTable.getValueAt(selectedRow, 1).toString();
    	    String email = userTable.getValueAt(selectedRow, 2).toString();
    	    String fullName = userTable.getValueAt(selectedRow, 3).toString();
    	    
    	    FormEditUser formuser = new FormEditUser();
    	    formuser.setUserData(userId, username, email,fullName);
    	    formuser.setLocationRelativeTo(this);    	
    	
	    	formuser.addWindowListener(new java.awt.event.WindowAdapter() {
	            @Override
	            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
	            	loadUserTable();
	            }
	        });
    	}

    }
}
