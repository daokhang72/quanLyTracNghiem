package DAL;

import DTO.QuestionDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private Connection conn;

    public QuestionDAO(Connection conn) {
        this.conn = conn;
    }

    public List<QuestionDTO> getAllQuestions() {
        List<QuestionDTO> questions = new ArrayList<>();
        String query = "SELECT * FROM questions";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                questions.add(new QuestionDTO(
                    rs.getInt("qID"),
                    rs.getString("qContent"),
                    rs.getString("qPictures"),
                    rs.getInt("qTopicID"),
                    rs.getString("qLevel"),
                    rs.getInt("qStatus")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public QuestionDTO getQuestionByID(int qID) {
        String query = "SELECT * FROM questions WHERE qID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, qID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new QuestionDTO(
                    rs.getInt("qID"),
                    rs.getString("qContent"),
                    rs.getString("qPictures"),
                    rs.getInt("qTopicID"),
                    rs.getString("qLevel"),
                    rs.getInt("qStatus")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addQuestion(QuestionDTO question) {
        String query = "INSERT INTO questions (qContent, qPictures, qTopicID, qLevel, qStatus) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, question.getQContent());
            stmt.setString(2, question.getQPictures());
            stmt.setInt(3, question.getQTopicID());
            stmt.setString(4, question.getQLevel());
            stmt.setInt(5, question.getQStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateQuestion(QuestionDTO question) {
        String query = "UPDATE questions SET qContent = ?, qPictures = ?, qTopicID = ?, qLevel = ?, qStatus = ? WHERE qID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, question.getQContent());
            stmt.setString(2, question.getQPictures());
            stmt.setInt(3, question.getQTopicID());
            stmt.setString(4, question.getQLevel());
            stmt.setInt(5, question.getQStatus());
            stmt.setInt(6, question.getQID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteQuestion(int qID) {
        String sql = "DELETE FROM questions WHERE qID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, qID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<QuestionDTO> getQuestionsByID(int qID) {
        List<QuestionDTO> questions = new ArrayList<>();
        String query = "SELECT * FROM questions WHERE qID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, qID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questions.add(new QuestionDTO(
                    rs.getInt("qID"),
                    rs.getString("qContent"),
                    rs.getString("qPictures"),
                    rs.getInt("qTopicID"),
                    rs.getString("qLevel"),
                    rs.getInt("qStatus")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public List<QuestionDTO> getQuestionsByContent(String content) {
        List<QuestionDTO> questions = new ArrayList<>();
        String query = "SELECT * FROM questions WHERE qContent LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + content + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questions.add(new QuestionDTO(
                    rs.getInt("qID"),
                    rs.getString("qContent"),
                    rs.getString("qPictures"),
                    rs.getInt("qTopicID"),
                    rs.getString("qLevel"),
                    rs.getInt("qStatus")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public List<QuestionDTO> getQuestionsByTopic(String topic) {
        List<QuestionDTO> questions = new ArrayList<>();
        String query = "SELECT * FROM questions WHERE qTopicID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, topic);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questions.add(new QuestionDTO(
                    rs.getInt("qID"),
                    rs.getString("qContent"),
                    rs.getString("qPictures"),
                    rs.getInt("qTopicID"),
                    rs.getString("qLevel"),
                    rs.getInt("qStatus")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

}
