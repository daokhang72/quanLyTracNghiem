package BLL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import DAL.DatabaseHelper;
import DAL.QuestionDAO;
import DTO.QuestionDTO;

public class QuestionBLL {
    private QuestionDAO questionDAL;

    // Constructor mặc định (Sửa lỗi)
    public QuestionBLL() {
        try {
            Connection conn = DatabaseHelper.getConnection();
            this.questionDAL = new QuestionDAO(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi kết nối CSDL trong QuestionBLL");
        }
    }

    // Constructor có tham số
    public QuestionBLL(Connection conn) {
        this.questionDAL = new QuestionDAO(conn);
    }

    public List<QuestionDTO> getAllQuestions() {
        return questionDAL.getAllQuestions();
    }

    public boolean addQuestion(QuestionDTO question) {
        if (question.getQContent() == null || question.getQContent().trim().isEmpty()) {
            return false;
        }
        return questionDAL.addQuestion(question);
    }

    public boolean updateQuestion(QuestionDTO question) {
        if (question.getQID() <= 0) {
            return false;
        }
        return questionDAL.updateQuestion(question);
    }

    public boolean deleteQuestion(int qID) {
        return questionDAL.deleteQuestion(qID);
    }
    public List<QuestionDTO> getQuestionsByID(int id) {
        return questionDAL.getQuestionsByID(id);
    }

    public List<QuestionDTO> getQuestionsByContent(String content) {
        return questionDAL.getQuestionsByContent(content);
    }

    public List<QuestionDTO> getQuestionsByTopic(String topic) {
        return questionDAL.getQuestionsByTopic(topic);
    }


}
