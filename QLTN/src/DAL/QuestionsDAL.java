package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.QuestionDTO;

public class QuestionsDAL {
	private Connection con;
	private String url = "jdbc:sqlserver://localhost:1433;DatabaseName=tracnghiem;encrypt=true;trustServerCertificate=true";
	private String username = "sa";
	private String pass = "1234";
	
	public QuestionsDAL() {
		try {
			con = DriverManager.getConnection(url,username,pass);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	//Thêm 1 câu hỏi mới
	public boolean addQuestion(QuestionDTO q) {
		String sql = "INSERT INTO questions (qContent, qPictures, qTopicID, qLevel, qStatus) VALUES (?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, q.getQContent());
			ps.setString(2, q.getQPictures());
			ps.setInt(3, q.getQTopicID());
			ps.setString(4, q.getQLevel());
			ps.setInt(5, q.getQStatus());
			int n = ps.executeUpdate();
			if(n > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return false;
	}
	
	//Lấy danh sách tất cả câu hỏi
	public ArrayList<QuestionDTO> dsQuestion(){
		ArrayList<QuestionDTO> ds = new ArrayList<>();
		String sql = "SELECT * FROM questions";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				ds.add(new QuestionDTO(
						rs.getInt("qID"),
	                    rs.getString("qContent"),
	                    rs.getString("qPictures"),
	                    rs.getInt("qTopicID"),
	                    rs.getString("qLevel"),
	                    rs.getInt("qStatus")
						));
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return ds;
	}
	
	//Tìm kiém câu hỏi
	public ArrayList<QuestionDTO> searchQuestions(String keyword, int questionID, int topicID) {
	    ArrayList<QuestionDTO> questionList = new ArrayList<>();
	    String sql = "SELECT * FROM questions WHERE 1=1";

	    if (questionID > 0) {
	        sql += " AND qID = ?";
	    }
	    if (keyword != null && !keyword.trim().isEmpty()) {
	        sql += " AND qContent LIKE ?";
	    }
	    if (topicID > 0) {
	        sql += " AND qTopicID = ?";
	    }

	    try (PreparedStatement st = con.prepareStatement(sql)) {
	        int index = 1;
	        if (questionID > 0) {
	            st.setInt(index++, questionID);
	        }
	        if (keyword != null && !keyword.trim().isEmpty()) {
	            st.setString(index++, "%" + keyword + "%");
	        }
	        if (topicID > 0) {
	            st.setInt(index++, topicID);
	        }

	        try (ResultSet rs = st.executeQuery()) {
	            while (rs.next()) {
	                questionList.add(new QuestionDTO(
	                    rs.getInt("qID"),
	                    rs.getString("qContent"),
	                    rs.getString("qPictures"),
	                    rs.getInt("qTopicID"),
	                    rs.getString("qLevel"),
	                    rs.getInt("qStatus")
	                ));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return questionList;
	}

	
	//Cập nhất thông tin câu hỏi
	public boolean updateQuestion(QuestionDTO q) {
		String sql = "UPDATE questions SET qContent = ?, qPictures = ?, qTopicID = ?, qLevel = ?, qStatus = ? WHERE qID = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, q.getQContent());
			ps.setString(2, q.getQPictures());
			ps.setInt(3, q.getQTopicID());
			ps.setString(4, q.getQLevel());
			ps.setInt(5, q.getQStatus());
			ps.setInt(6, q.getQID());
			int n = ps.executeUpdate();
			if(n > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return false;
	}
	
	//Xóa câu hỏi
	public boolean deleteQuestion(int qID) {
		String sql = "DELETE FROM questions WHERE qID = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, qID);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			System.err.println(e);
		}
		return false;
	}
	// lay cau hoi theo ID
	public QuestionDTO getQuestionByID(int qID) {
		String sql = "SELECT * FROM questions WHERE qID = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, qID);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return new QuestionDTO(
						rs.getInt("qID"),
						rs.getString("qContent"),
						rs.getString("qPictures"),
						rs.getInt("qTopicID"),
						rs.getString("qLevel"),
						rs.getInt("qStatus")
						);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}
}
