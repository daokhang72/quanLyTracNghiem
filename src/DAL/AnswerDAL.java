package DAL;

import DTO.AnswerDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAL {
    private Connection conn;

    public AnswerDAL(Connection conn) {
        this.conn = conn;
    }

    public List<AnswerDTO> getAllAnswers() {
        List<AnswerDTO> answers = new ArrayList<>();
        String query = "SELECT * FROM answers";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                answers.add(new AnswerDTO(
                    rs.getInt("awID"),
                    rs.getInt("qID"),
                    rs.getString("awContent"),
                    rs.getString("awPictures"),
                    rs.getInt("isRight"),
                    rs.getInt("awStatus")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }

    public boolean addAnswer(AnswerDTO answer) {
        String query = "INSERT INTO answers (qID, awContent, awPictures, isRight, awStatus) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, answer.getQID());
            pstmt.setString(2, answer.getAwContent());
            pstmt.setString(3, answer.getAwPictures());
            pstmt.setInt(4, answer.getIsRight());
            pstmt.setInt(5, answer.getAwStatus());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAnswer(AnswerDTO answer) {
        String query = "UPDATE answers SET qID = ?, awContent = ?, awPictures = ?, isRight = ?, awStatus = ? WHERE awID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, answer.getQID());
            pstmt.setString(2, answer.getAwContent());
            pstmt.setString(3, answer.getAwPictures());
            pstmt.setInt(4, answer.getIsRight());
            pstmt.setInt(5, answer.getAwStatus());
            pstmt.setInt(6, answer.getAwID());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAnswer(int awID) {
        String query = "DELETE FROM answers WHERE awID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, awID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
