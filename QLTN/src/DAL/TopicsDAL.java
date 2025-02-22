package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.TopicDTO;

public class TopicsDAL {
	private Connection con;
	private String url = "jdbc:sqlserver://localhost:1433;DatabaseName=tracnghiem;encrypt=true;trustServerCertificate=true";
	private String username = "sa";
	private String pass = "1234";
	
	public TopicsDAL() {
		try {
			con = DriverManager.getConnection(url,username,pass);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	// Thêm 1 chủ đề mới
	public boolean addTopic(TopicDTO t) {
		String sql = "INSERT INTO topics (tpTitle,tpParent,tpStatus) VALUES (?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getTpTitle());
			ps.setInt(2, t.getTpParent());
			ps.setInt(3, t.getTpStatus());
			int n = ps.executeUpdate();
			if(n > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return false;
	}
	
	// Xóa chủ đề theo ID
	public boolean deleteTopic(int tpID) {
		String sql = "DELETE FROM topics WHERE tpID = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, tpID);
			int n = ps.executeUpdate();
			if(n > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return false;
	}
	
	//Cập nhật thông tin chủ đề
	public boolean updateTopic(TopicDTO t) {
		String sql = "UPDATE topics SET tpTitle = ?, tpParent = ?, tpStatus = ? WHERE tpID = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getTpTitle());
			ps.setInt(2, t.getTpParent());
			ps.setInt(3, t.getTpStatus());
			ps.setInt(4, t.getTpID());
			int n = ps.executeUpdate();
			if(n > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return false;
	}
	
	//Lấy danh sách tất cả các chủ đề
	public ArrayList<TopicDTO> dsTopic(){
		ArrayList<TopicDTO> ds = new ArrayList<>();
		String sql = "SELECT * FROM topics";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				ds.add(new TopicDTO(
						rs.getInt("tpID"),
						rs.getString("tpTitle"),
						rs.getInt("tpParent"),
						rs.getInt("tpStatus")));
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return ds;
	}
	
	//Tìm chủ đề theo ID
	public TopicDTO getTopicByID(int tpID) {
        String sql = "SELECT * FROM topics WHERE tpID = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, tpID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TopicDTO(
                        rs.getInt("tpID"),
                        rs.getString("tpTitle"),
                        rs.getInt("tpParent"),
                        rs.getInt("tpStatus")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
