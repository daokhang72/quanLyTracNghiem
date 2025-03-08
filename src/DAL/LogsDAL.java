package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.LogDTO;

public class LogsDAL {
	private Connection con;
	private String url = "jdbc:sqlserver://localhost:1433;DatabaseName=tracnghiem;encrypt=true;trustServerCertificate=true";
	private String username = "sa";
	private String pass = "1234";
	
	public LogsDAL() {
		try {
			con = DriverManager.getConnection(url,username,pass);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	// Thêm log mới
    public boolean addLog(LogDTO log) {
        String sql = "INSERT INTO logs (logContent, logUserID, logExID, logDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, log.getLogContent());
            st.setInt(2, log.getLogUserID());
            st.setInt(3, log.getLogExID());
            st.setTimestamp(4, log.getLogDate());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Lấy log theo ID
    public LogDTO getLogByID(int logID) {
        String sql = "SELECT * FROM logs WHERE logID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, logID);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new LogDTO(
                        rs.getInt("logID"),
                        rs.getString("logContent"),
                        rs.getInt("logUserID"),
                        rs.getInt("logExID"),
                        rs.getTimestamp("logDate")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
 // Lấy tất cả log
    public ArrayList<LogDTO> getAllLogs() {
    	ArrayList<LogDTO> logList = new ArrayList<>();
        String sql = "SELECT * FROM logs ORDER BY logDate DESC";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                logList.add(new LogDTO(
                    rs.getInt("logID"),
                    rs.getString("logContent"),
                    rs.getInt("logUserID"),
                    rs.getInt("logExID"),
                    rs.getTimestamp("logDate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logList;
    }
    //  Xóa log theo ID
    public boolean deleteLog(int logID) {
        String sql = "DELETE FROM logs WHERE logID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, logID);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy log theo userID
    public ArrayList<LogDTO> getLogsByUserID(int userID) {
    	ArrayList<LogDTO> logList = new ArrayList<>();
        String sql = "SELECT * FROM logs WHERE logUserID = ? ORDER BY logDate DESC";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userID);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    logList.add(new LogDTO(
                        rs.getInt("logID"),
                        rs.getString("logContent"),
                        rs.getInt("logUserID"),
                        rs.getInt("logExID"),
                        rs.getTimestamp("logDate")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logList;
    }

    // Lấy log theo mã bài thi (exCode)
    public ArrayList<LogDTO> getLogsByExID(int exCode) {
    	ArrayList<LogDTO> logList = new ArrayList<>();
        String sql = "SELECT * FROM logs WHERE logExID = ? ORDER BY logDate DESC";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, exCode);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    logList.add(new LogDTO(
                        rs.getInt("logID"),
                        rs.getString("logContent"),
                        rs.getInt("logUserID"),
                        rs.getInt("logExID"),
                        rs.getTimestamp("logDate")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logList;
    }
}
