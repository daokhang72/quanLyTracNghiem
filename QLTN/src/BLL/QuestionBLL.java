package BLL;

import java.util.ArrayList;
import java.util.List;

import DAL.QuestionsDAL;
import DTO.QuestionDTO;

public class QuestionBLL {
	private QuestionsDAL dalquestion;
	
	public QuestionBLL() {
		dalquestion = new QuestionsDAL();
	}
	// lau danh sach tat ca cau hoi
	public ArrayList<QuestionDTO> getAllQuestion(){
		return dalquestion.dsQuestion();
	}
	//Lay cau hoi theo id
	public QuestionDTO getQuestionByID(int qID) {
		return dalquestion.getQuestionByID(qID);
	}
	//tim kiem cau hoi
	public ArrayList<QuestionDTO> searchQuestions(String keyword, int questionID, int topicID){
		return dalquestion.searchQuestions(keyword,questionID,topicID);
	}
	//them 1 cau hoi moi
	public boolean addQuestion(QuestionDTO q) {
		if(q.getQContent() == null || q.getQContent().trim().isEmpty()) {
			return false;
		}
		return dalquestion.addQuestion(q);
	}
	//cap nhat thong tin cau hoi
	public boolean updateQuestion(QuestionDTO q) {
		if(q.getQID() <= 0) {
			return false;
		}
		return dalquestion.updateQuestion(q);
	}
	//xoa cau hoi theo id
	public boolean deleteQuestion(int qID) {
		return dalquestion.deleteQuestion(qID);
	}


}	

