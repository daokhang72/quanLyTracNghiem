package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.ResultDTO;

public class ResultDAL {
	private Connection con;
	private String url = "jdbc:sqlserver://localhost:1433;DatabaseName=tracnghiem;encrypt=true;trustServerCertificate=true";
	private String username = "sa";
	private String pass = "1234";
	
	public ResultDAL() {
		try {
			con = DriverManager.getConnection(url,username,pass);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	// Lấy danh sách tất cả kết quả thi
    public ArrayList<ResultDTO> getAllResults() {
        ArrayList<ResultDTO> resultList = new ArrayList<>();
        String sql = "SELECT * FROM result";
        try (PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                resultList.add(new ResultDTO(
                        rs.getInt("rs_num"),
                        rs.getInt("userID"),
                        rs.getString("exCode"),
                        rs.getString("rs_answers"),
                        rs.getDouble("rs_mark"),
                        rs.getTimestamp("rs_date")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
    
    // Lấy kết quả thi theo mã người dùng (userID)
    public ArrayList<ResultDTO> getResultsByUserID(int userID) {
    	ArrayList<ResultDTO> resultList = new ArrayList<>();
        String sql = "SELECT * FROM result WHERE userID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userID);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    resultList.add(new ResultDTO(
                            rs.getInt("rs_num"),
                            rs.getInt("userID"),
                            rs.getString("exCode"),
                            rs.getString("rs_answers"),
                            rs.getDouble("rs_mark"),
                            rs.getTimestamp("rs_date")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
    
    // Lấy kết quả thi theo mã bài thi (exCode)
    public ArrayList<ResultDTO> getResultsByExamCode(String exCode) {
    	ArrayList<ResultDTO> resultList = new ArrayList<>();
        String sql = "SELECT * FROM result WHERE exCode = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, exCode);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    resultList.add(new ResultDTO(
                            rs.getInt("rs_num"),
                            rs.getInt("userID"),
                            rs.getString("exCode"),
                            rs.getString("rs_answers"),
                            rs.getDouble("rs_mark"),
                            rs.getTimestamp("rs_date")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
 // Thêm một kết quả thi mới
    public boolean addResult(ResultDTO result) {
        String sql = "INSERT INTO result (userID, exCode, rs_answers, rs_mark, rs_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, result.getUserID());
            st.setString(2, result.getExCode());
            st.setString(3, result.getRsAnswers());
            st.setDouble(4, result.getRsMark());
            st.setTimestamp(5, result.getRsDate());
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật kết quả thi
    public boolean updateResult(ResultDTO result) {
        String sql = "UPDATE result SET userID = ?, exCode = ?, rs_answers = ?, rs_mark = ?, rs_date = ? WHERE rs_num = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, result.getUserID());
            st.setString(2, result.getExCode());
            st.setString(3, result.getRsAnswers());
            st.setDouble(4, result.getRsMark());
            st.setTimestamp(5, result.getRsDate());
            st.setInt(6, result.getRsNum());
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateResultMark(int rsNum, double rsMark) {
        String sql = "UPDATE result SET rs_mark = ? WHERE rs_num = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setDouble(1, rsMark);
            st.setInt(2, rsNum);
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    
    // Xóa kết quả thi theo rs_num
    public boolean deleteResult(int rsNum) {
        String sql = "DELETE FROM result WHERE rs_num = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, rsNum);
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 // Thống kê số lượng người dự thi theo bài thi
    public int countParticipantsByTest(String testCode) {
        String sql = "SELECT COUNT(DISTINCT userID) FROM result WHERE exCode IN (SELECT exCode FROM exams WHERE testCode = ?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, testCode);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    // Thống kê số lượng người đạt (>=50%) theo bài thi
    public int countPassedParticipantsByTest(String testCode) {
        String sql = "SELECT COUNT(DISTINCT userID) FROM result WHERE rs_mark >= 50 AND exCode IN (SELECT exCode FROM exams WHERE testCode = ?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, testCode);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Thống kê số lượng người không đạt (<50%) theo bài thi
    public int countFailedParticipantsByTest(String testCode) {
        String sql = "SELECT COUNT(DISTINCT userID) FROM result WHERE rs_mark < 50 AND exCode IN (SELECT exCode FROM exams WHERE testCode = ?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, testCode);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
 // Đếm tổng số thí sinh dự thi
    public int countTotalStudents() {
        String sql = "SELECT COUNT(DISTINCT userID) FROM result";
        try (PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
}
