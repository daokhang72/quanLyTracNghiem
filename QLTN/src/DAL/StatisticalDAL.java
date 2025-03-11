package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StatisticalDAL {
	private Connection conn;
	private String url = "jdbc:sqlserver://localhost:1433;DatabaseName=tracnghiem;encrypt=true;trustServerCertificate=true";
	private String username = "sa";
	private String pass = "1234";
	
	public StatisticalDAL() {
		try {
			conn = DriverManager.getConnection(url,username,pass);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	public ArrayList<Object[]> getAllUsers() {
	    ArrayList<Object[]> userList = new ArrayList<>();
	    String sql = "SELECT userid, userName, userEmail, userFullName, isAdmin FROM users";
	    
	    try (
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	         
	        while (rs.next()) {
	            userList.add(new Object[]{
	                rs.getInt("userID"),
	                rs.getString("userName"),
	                rs.getString("userEmail"),
	                rs.getString("userFullName"),
	                rs.getInt("isAdmin")
	            });
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return userList;
	}

	public ArrayList<Object[]> getAllTests() {
	    ArrayList<Object[]> testList = new ArrayList<>();
	    String sql = "SELECT t.testID, t.testTitle, " +
                "(t.num_easy + t.num_medium + t.num_diff) AS total_questions, " +
                "t.testTime, COUNT(r.exCode) AS total_participants " +
                "FROM test t " +
                "LEFT JOIN exams e ON t.testCode = e.testCode " +
                "LEFT JOIN result r ON e.exCode = r.exCode " +
                "GROUP BY t.testID, t.testTitle, t.num_easy, t.num_medium, t.num_diff, t.testTime";

	    try (
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery()
	    ) {
	    	 while (rs.next()) {
	             testList.add(new Object[]{
	                 rs.getInt("testID"),
	                 rs.getString("testTitle"),
	                 rs.getInt("total_questions"),
	                 rs.getInt("testTime") + " phút",
	                 rs.getInt("total_participants")
	             });
	         }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return testList;
	}


 // Đếm tổng số thí sinh dự thi
    public int countTotalStudents() {
        String sql = "SELECT COUNT(DISTINCT userID) FROM result";
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public ArrayList<Object[]> getPassFailParticipants(String testCode) {
    	ArrayList<Object[]> resultList = new ArrayList<>();
        String sql = "SELECT u.userID, u.userFullName, t.testTitle, r.rs_mark, " +
                     "CASE WHEN r.rs_mark >= 5 THEN 'Pass' ELSE 'Fail' END AS result " +
                     "FROM result r " +
                     "JOIN users u ON r.userID = u.userID " +
                     "JOIN exams e ON r.exCode = e.exCode " +
                     "JOIN test t ON e.testCode = t.testCode " +
                     "WHERE e.testCode = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, testCode);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("userID"),
                    rs.getString("userFullName"),
                    rs.getString("testTitle"),
                    rs.getDouble("rs_mark"),
                    rs.getString("result")
                };
                resultList.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }



 // Lấy điểm trung bình
    public double getAverageScore() {
        double avgScore = 0.0;
        try {
            String query = "SELECT AVG(rs_mark) FROM result";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                avgScore = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avgScore;
    }
    //Lấy tổng số lượt truy cập
    public int getLogStatistics() {
        String sql = "SELECT COUNT(*) FROM logs";
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
