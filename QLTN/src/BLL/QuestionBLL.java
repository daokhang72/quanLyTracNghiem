package BLL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import DAL.QuestionsDAL;
import DAL.TestDAL;
import DTO.QuestionDTO;
import DTO.TestDTO;

public class QuestionBLL {
	private QuestionsDAL dalquestion;
	private TestDAL daltest;
	
	public QuestionBLL() {
		dalquestion = new QuestionsDAL();
		daltest = new TestDAL();
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

	public List<QuestionDTO> getRandomQuestionsByTestID(int testID) {
	    TestDTO test = daltest.getTestByID(testID);
	    if (test == null) {
	        throw new IllegalArgumentException("Test không tồn tại!");
	    }

	    int tpID = test.getTpID();  
	    int numEasy = test.getNumEasy();
	    int numMedium = test.getNumMedium();
	    int numDiff = test.getNumDiff();

	    // Lấy danh sách câu hỏi theo chủ đề
	    List<QuestionDTO> allQuestions = searchQuestions("", 0, tpID);

	    // Phân loại câu hỏi theo độ khó
	    List<QuestionDTO> easyQuestions = allQuestions.stream()
	            .filter(q -> "easy".equalsIgnoreCase(q.getQLevel()))
	            .collect(Collectors.toList());

	    List<QuestionDTO> mediumQuestions = allQuestions.stream()
	            .filter(q -> "medium".equalsIgnoreCase(q.getQLevel()))
	            .collect(Collectors.toList());

	    List<QuestionDTO> difficultQuestions = allQuestions.stream()
	            .filter(q -> "difficult".equalsIgnoreCase(q.getQLevel()))
	            .collect(Collectors.toList());

	    // Chọn ngẫu nhiên câu hỏi theo số lượng yêu cầu
	    Collections.shuffle(easyQuestions);
	    Collections.shuffle(mediumQuestions);
	    Collections.shuffle(difficultQuestions);

	    List<QuestionDTO> selectedQuestions = new ArrayList<>();
	    selectedQuestions.addAll(easyQuestions.stream().limit(numEasy).collect(Collectors.toList()));
	    selectedQuestions.addAll(mediumQuestions.stream().limit(numMedium).collect(Collectors.toList()));
	    selectedQuestions.addAll(difficultQuestions.stream().limit(numDiff).collect(Collectors.toList()));

	    return selectedQuestions;
	}
}	

