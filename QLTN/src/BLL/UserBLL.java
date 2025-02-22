package BLL;

import java.util.ArrayList;

import DAL.UsersDAL;
import DTO.UserDTO;

public class UserBLL {
	private UsersDAL daluser;
	
	public UserBLL() {
		daluser = new UsersDAL();
	}
	// lay danh sach tat ca nguoi dung
	public ArrayList<UserDTO> getAllUsers(){
		return daluser.getAllUsers();
	}
	//lay thong tin nguoi dung theo ID
	public UserDTO getUserByID(int userID) {
		return daluser.getUserByID(userID);
	}
	//them nguoi dung moi
	public boolean addUser(UserDTO u) {
		return daluser.addUser(u);
	}
	//cap nhat thong tin nguoi dung
	public boolean updateUser(UserDTO u) {
		return daluser.updateUser(u);
	}
	//xoa nguoi dung theo id
	public boolean deleteUser(int userID) {
		return daluser.deleteUser(userID);
	}
	//kiem tra dang nhap
	public UserDTO login(String username,String pass) {
		return daluser.login(username, pass);
	}
	//reset pass
	public boolean resetPass(int userID,String newPass) {
		return daluser.resetPass(userID, newPass);
	}
}
