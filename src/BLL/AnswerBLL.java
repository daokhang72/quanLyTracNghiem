package BLL;

import DAL.AnswerDAL;
import DTO.AnswerDTO;
import java.sql.Connection;
import java.util.List;

public class AnswerBLL {
    private AnswerDAL answerDAL;

    public AnswerBLL(Connection conn) {
        this.answerDAL = new AnswerDAL(conn);
    }

    public List<AnswerDTO> getAllAnswers() {
        return answerDAL.getAllAnswers();
    }

    public boolean addAnswer(AnswerDTO answer) {
        return answerDAL.addAnswer(answer);
    }

    public boolean updateAnswer(AnswerDTO answer) {
        return answerDAL.updateAnswer(answer);
    }

    public boolean deleteAnswer(int awID) {
        return answerDAL.deleteAnswer(awID);
    }
}