package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.TestDTO;

public class TestDAL {
	private Connection conn;
	private String url = "jdbc:sqlserver://localhost:1433;DatabaseName=tracnghiem;encrypt=true;trustServerCertificate=true";
	private String username = "sa";
	private String pass = "1234";
	
	public TestDAL() {
		try {
			conn = DriverManager.getConnection(url,username,pass);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	private boolean kiemTraTonTai(String maTest) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS Count FROM test WHERE testCode = ?");
            ps.setString(1, maTest);
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
	// tao ma test chua ton tai
	public String taoMaTest() {
		String sql = "SELECT MAX(testCode) AS MaxMaTest FROM test";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			String maxMa = "TST001";
			if(rs.next()) {
				String maxMaFromDB = rs.getString("MaxMaTest");
				if(maxMaFromDB != null) {
					int nextMa = Integer.parseInt(maxMaFromDB.substring(2)) + 1;
					String newMa;
					boolean flag = false;
					while(!flag) {
						newMa = String.format("TST%03", nextMa);
						if(!kiemTraTonTai(newMa)) {
							maxMa = newMa;
							flag = true;
						}
						nextMa++;
					}
				}
			}
			return maxMa;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	// Thêm một bài thi mới
    public boolean addTest(TestDTO test) {
    	String maTest = taoMaTest();
        String sql = "INSERT INTO test (testCode, testTitle, testTime, tpid, num_easy, num_medium, num_diff, testLimit, testDate, testStatus) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maTest);
            stmt.setString(2, test.getTestTitle());
            stmt.setInt(3, test.getTestTime());
            stmt.setInt(4, test.getTpID());
            stmt.setInt(5, test.getNumEasy());
            stmt.setInt(6, test.getNumMedium());
            stmt.setInt(7, test.getNumDiff());
            stmt.setInt(8, test.getTestLimit());
            stmt.setDate(9, test.getTestDate());
            stmt.setInt(10, test.getTestStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Lấy danh sách tất cả bài thi
    public ArrayList<TestDTO> getAllTests() {
        ArrayList<TestDTO> tests = new ArrayList<>();
        String sql = "SELECT * FROM test";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TestDTO test = new TestDTO(
                    rs.getInt("testID"),
                    rs.getString("testCode"),
                    rs.getString("testTitle"),
                    rs.getInt("testTime"),
                    rs.getInt("tpid"),
                    rs.getInt("num_easy"),
                    rs.getInt("num_medium"),
                    rs.getInt("num_diff"),
                    rs.getInt("testLimit"),
                    rs.getDate("testDate"),
                    rs.getInt("testStatus")
                );
                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }
    
    // lay bai thi theo chu de
    public ArrayList<TestDTO> getTestsByTopicID(int tpID){
    	ArrayList<TestDTO> ds = new ArrayList<TestDTO>();
    	String sql = "SELECT * FROM test WHERE tpID = ?";
    	try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, tpID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ds.add(new TestDTO(
	                    rs.getInt("testID"),
	                    rs.getString("testCode"),
	                    rs.getString("testTitle"),
	                    rs.getInt("testTime"),
	                    rs.getInt("tpid"),
	                    rs.getInt("num_easy"),
	                    rs.getInt("num_medium"),
	                    rs.getInt("num_diff"),
	                    rs.getInt("testLimit"),
	                    rs.getDate("testDate"),
	                    rs.getInt("testStatus")
	                ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return ds;
    }
    
    // Lấy bài thi theo ID
    public TestDTO getTestByID(int testID) {
        String sql = "SELECT * FROM test WHERE testID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, testID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TestDTO(
                    rs.getInt("testID"),
                    rs.getString("testCode"),
                    rs.getString("testTitle"),
                    rs.getInt("testTime"),
                    rs.getInt("tpid"),
                    rs.getInt("num_easy"),
                    rs.getInt("num_medium"),
                    rs.getInt("num_diff"),
                    rs.getInt("testLimit"),
                    rs.getDate("testDate"),
                    rs.getInt("testStatus")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Cập nhật thông tin bài thi
    public boolean updateTest(TestDTO test) {
        String sql = "UPDATE test SET testCode = ?, testTitle = ?, testTime = ?, tpid = ?, num_easy = ?, num_medium = ?, num_diff = ?, testLimit = ?, testDate = ?, testStatus = ? WHERE testID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, test.getTestCode());
            stmt.setString(2, test.getTestTitle());
            stmt.setInt(3, test.getTestTime());
            stmt.setInt(4, test.getTpID());
            stmt.setInt(5, test.getNumEasy());
            stmt.setInt(6, test.getNumMedium());
            stmt.setInt(7, test.getNumDiff());
            stmt.setInt(8, test.getTestLimit());
            stmt.setDate(9, test.getTestDate());
            stmt.setInt(10, test.getTestStatus());
            stmt.setInt(11, test.getTestID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Xóa bài thi theo ID
    public boolean deleteTest(int testID) {
        String sql = "DELETE FROM test WHERE testID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, testID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
