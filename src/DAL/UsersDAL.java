package DAL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.UserDTO;

public class UsersDAL {
	private Connection con;
	private String url = "jdbc:sqlserver://localhost:1433;DatabaseName=tracnghiem;encrypt=true;trustServerCertificate=true";
	private String username = "sa";
	private String pass = "1234";
	
	public UsersDAL() {
		try {
			con = DriverManager.getConnection(url,username,pass);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	// Thêm người dùng mới
    public boolean addUser(UserDTO user) {
        String sql = "INSERT INTO users ( userName, userEmail, userPassword, userFullName, isAdmin) VALUES (?, ?, ?, ?, 0)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, user.getUserName());
            st.setString(2, user.getUserEmail());
            st.setString(3, user.getUserPassword());
            st.setString(4, user.getUserFullName());

            return st.executeUpdate() > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Cập nhật thông tin người dùng
    public boolean updateUser(UserDTO user) {
        String sql = "UPDATE users SET userName = ?, userEmail = ?, userPassword = ?, userFullName = ?, isAdmin = ? WHERE userID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, user.getUserName());
            st.setString(2, user.getUserEmail());
            st.setString(3, user.getUserPassword());
            st.setString(4, user.getUserFullName());
            st.setBoolean(5, user.isAdmin());
            st.setInt(6, user.getUserID());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Xóa người dùng theo ID
    public boolean deleteUser(int userID) throws IOException {
        String sql = "DELETE FROM users WHERE userID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userID);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
        	if (e instanceof SQLException sqlEx) {
                if (sqlEx.getSQLState().startsWith("23")) {
                    throw new IOException("Không thể xóa! Người dùng này đang liên kết với dữ liệu khác.");
                }
            }
        }
        return false;
    }

    // Tìm người dùng theo ID
    public UserDTO getUserByID(int userID) {
        String sql = "SELECT * FROM users WHERE userID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userID);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new UserDTO(
                        rs.getInt("userID"),
                        rs.getString("userName"),
                        rs.getString("userEmail"),
                        rs.getString("userPassword"),
                        rs.getString("userFullName"),
                        rs.getBoolean("isAdmin")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
 // Lấy danh sách tất cả người dùng theo username
 // Tìm người dùng theo ID
    public UserDTO getUserByUserName(String username) {
        String sql = "SELECT TOP 1 * FROM users WHERE userName = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new UserDTO(
                        rs.getInt("userID"),
                        rs.getString("userName"),
                        rs.getString("userEmail"),
                        rs.getString("userPassword"),
                        rs.getString("userFullName"),
                        rs.getBoolean("isAdmin")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return null;
    }

    // Lấy danh sách tất cả người dùng
    public ArrayList<UserDTO> getAllUsers() {
    	ArrayList<UserDTO> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                userList.add(new UserDTO(
                    rs.getInt("userID"),
                    rs.getString("userName"),
                    rs.getString("userEmail"),
                    rs.getString("userPassword"),
                    rs.getString("userFullName"),
                    rs.getBoolean("isAdmin")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // Kiểm tra đăng nhập bằng username và password
    public UserDTO login(String username, String password) {
        String sql = "SELECT * FROM users WHERE userName = ?  AND userPassword = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, password);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new UserDTO(
                        rs.getInt("userID"),
                        rs.getString("userName"),
                        rs.getString("userEmail"),
                        rs.getString("userPassword"),
                        rs.getString("userFullName"),
                        rs.getBoolean("isAdmin")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //reset password
    public boolean resetPass(int userID, String newPass) {
    	String sql = "UPDATE users SET userPassword = ? WHERE userID = ?";
    	try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, newPass);
			ps.setInt(2, userID);
			int n = ps.executeUpdate();
			if(n > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println(e);
		}
    	return false;
    }
    // kiem tra tồn tại
    public boolean kiemTraTonTai(String username) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS Count FROM users WHERE userName = ?");
            ps.setString(1, username);
            ResultSet rsExist = ps.executeQuery();
            if (rsExist.next()) {
                int count = rsExist.getInt("Count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
