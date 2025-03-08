	package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.ExamDTO;

public class ExamsDAL {
	private Connection con;
	private String url = "jdbc:sqlserver://localhost:1433;DatabaseName=tracnghiem;encrypt=true;trustServerCertificate=true";
	private String username = "sa";
	private String pass = "1234";
	
	public ExamsDAL() {
		try {
			con = DriverManager.getConnection(url,username,pass);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	private boolean kiemTraTonTai(String maExam) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS Count FROM test WHERE exCode = ?");
            ps.setString(1, maExam);
            ResultSet rsExist = ps.executeQuery();
            if (rsExist.next()) {
                int count = rsExist.getInt("Count");
                return count > 0;
            }
        } catch (SQLException e) {
        }
        return false;
    }
	// tao ma test chua ton tai
	public String taoMaExam() {
		String sql = "SELECT MAX(exCode) AS MaxMaExam FROM exams";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			String maxMa = "EX001";
			if(rs.next()) {
				String maxMaFromDB = rs.getString("MaxMaExam");
				if(maxMaFromDB != null) {
					int nextMa = Integer.parseInt(maxMaFromDB.substring(2)) + 1;
					String newMa;
					boolean flag = false;
					while(!flag) {
						newMa = String.format("EX%03d", nextMa);
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
	
	// Lấy danh sách tất cả các đề thi
    public ArrayList<ExamDTO> getAllExams() {
    	ArrayList<ExamDTO> examsList = new ArrayList<>();
        String sql = "SELECT * FROM exams";
        try (PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                examsList.add(new ExamDTO(
                        rs.getString("testCode"),
                        rs.getString("exOrder"),
                        rs.getString("exCode"),
                        rs.getString("ex_quesIDs")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return examsList;
    }
    
 // Tìm đề thi theo mã đề thi 
    public ExamDTO getExamByCode(String exCode) {
        String sql = "SELECT * FROM exams WHERE exCode = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, exCode);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new ExamDTO(
                            rs.getString("testCode"),
                            rs.getString("exOrder"),
                            rs.getString("exCode"),
                            rs.getString("ex_quesIDs")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
 // Thêm một đề thi mới
    public boolean addExam(ExamDTO exam) {
    	String maExam = taoMaExam();
        String sql = "INSERT INTO exams (testCode, exOrder, exCode, ex_quesIDs) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, exam.getTestCode());
            st.setString(2, exam.getExOrder());
            st.setString(3, maExam);
            st.setString(4, exam.getExQuesIDs());
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin đề thi
    public boolean updateExam(ExamDTO exam) {
        String sql = "UPDATE exams SET testCode = ?, exOrder = ?, ex_quesIDs = ? WHERE exCode = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, exam.getTestCode());
            st.setString(2, exam.getExOrder());
            st.setString(3, exam.getExQuesIDs());
            st.setString(4, exam.getExCode());
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa đề thi theo mã đề thi (exCode)
    public boolean deleteExam(String exCode) {
        String sql = "DELETE FROM exams WHERE exCode = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, exCode);
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //lay danh sach de thi theo testCode
    public ArrayList<ExamDTO> getExamsByTestCode(String testCode){
    	ArrayList<ExamDTO> ds = new ArrayList<ExamDTO>();
    	String sql = "SELECT * FROM exams WHERE testCode = ?";
    	try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, testCode);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ds.add(new ExamDTO(
                        rs.getString("testCode"),
                        rs.getString("exOrder"),
                        rs.getString("exCode"),
                        rs.getString("ex_quesIDs")
                ));
			}
		} catch (Exception e) {
			System.err.println(e);
		}
    	return ds;
    }
}

