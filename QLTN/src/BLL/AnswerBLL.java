package BLL;

import java.sql.SQLException;
import java.util.ArrayList;

import DAL.AnswersDAL;
import DTO.AnswerDTO;

public class AnswerBLL {
	private AnswersDAL dalanswer;
	
	public AnswerBLL() {
		dalanswer = new AnswersDAL();
	}
	
	//Them cau tra loi
	public boolean addAnswer(AnswerDTO a) {
		if(a == null || a.getAwContent().isEmpty()) {
			return false;
		}
		return dalanswer.addAnswer(a);
	} 
	
	// lay danh sach cau tra loi
	public ArrayList<AnswerDTO> dsAnswer(){
		return dalanswer.getAllAnswers();
	}
	// lay danh sach cau tra loi theo ID
	public ArrayList<AnswerDTO> dsIDAnswer(int qID){
		return dalanswer.getAnswersByQuestionID(qID);
	}
	// cap nhat cau tra loi
	public boolean updateAnser(AnswerDTO a) {
		if(a == null || a.getAwID() <= 0) {
			return false;
		}
		return dalanswer.updateAnswer(a);
	}
	// xoa cau tra loi theo ID
	public boolean deleteAnswer(int awID) {
		if(awID <= 0) {
			return false;
		}
		return dalanswer.deleteAnswer(awID);
	}
	public AnswerDTO getAnswerByID(int awID) {
	    return dalanswer.getAnswerByID(awID);
	}
	//xác định đáp án đúng
	public ArrayList<AnswerDTO> getRightAnswers(){
		return dalanswer.getRightAnswers();
	}
}
