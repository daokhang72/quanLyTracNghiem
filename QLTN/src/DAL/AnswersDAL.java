package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.AnswerDTO;

public class AnswersDAL {
	private Connection con;
	private String url = "jdbc:sqlserver://localhost:1433;DatabaseName=tracnghiem;encrypt=true;trustServerCertificate=true";
	private String username = "sa";
	private String pass = "1234";
	
	public AnswersDAL() {
		try {
			con = DriverManager.getConnection(url,username,pass);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	// Thêm một câu trả lời mới
    public boolean addAnswer(AnswerDTO answer) {
        String sql = "INSERT INTO answers (qID, awContent, awPictures, isRight, awStatus) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, answer.getQID());
            stmt.setString(2, answer.getAwContent());
            stmt.setString(3, answer.getAwPictures());
            stmt.setBoolean(4, answer.isRight()); 
            stmt.setInt(5, answer.getAwStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Lấy danh sách tất cả câu trả lời
    public ArrayList<AnswerDTO> getAllAnswers() {
        ArrayList<AnswerDTO> answers = new ArrayList<>();
        String sql = "SELECT * FROM answers";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                AnswerDTO answer = new AnswerDTO(
                    rs.getInt("awID"),
                    rs.getInt("qID"),
                    rs.getString("awContent"),
                    rs.getString("awPictures"),
                    rs.getBoolean("isRight"),
                    rs.getInt("awStatus")
                );
                answers.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }
    
 // Lấy danh sách câu trả lời theo ID câu hỏi
    public ArrayList<AnswerDTO> getAnswersByQuestionID(int qID) {
        ArrayList<AnswerDTO> answers = new ArrayList<>();
        String sql = "SELECT * FROM answers WHERE qID = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, qID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AnswerDTO answer = new AnswerDTO(
                    rs.getInt("awID"),
                    rs.getInt("qID"),
                    rs.getString("awContent"),
                    rs.getString("awPictures"),
                    rs.getBoolean("isRight"),
                    rs.getInt("awStatus")
                );
                answers.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }
    
 // Cập nhật thông tin câu trả lời
    public boolean updateAnswer(AnswerDTO answer) {
        String sql = "UPDATE answers SET awContent = ?, awPictures = ?, isRight = ?, awStatus = ? WHERE awID = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, answer.getAwContent());
            stmt.setString(2, answer.getAwPictures());
            stmt.setBoolean(3, answer.isRight());
            stmt.setInt(4, answer.getAwStatus());
            stmt.setInt(5, answer.getAwID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // Xóa câu trả lời theo ID
    public boolean deleteAnswer(int awID) {
        String sql = "DELETE FROM answers WHERE awID = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, awID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // xác định đáp án đúng
    public String getRightAnswer(int qid) {
        String sql = "SELECT awcontent FROM answers WHERE qid = ? AND isRight = 1"; 
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, qid);  
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("awcontent");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    public AnswerDTO getAnswerByID(int awID) {
        String query = "SELECT * FROM Answers WHERE awID = ?";
        try (
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, awID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new AnswerDTO(
                    rs.getInt("awID"),
                    rs.getInt("qID"),
                    rs.getString("awContent"),
                    rs.getString("awPictures"),
                    rs.getBoolean("isRight"),
                    rs.getInt("awStatus")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
