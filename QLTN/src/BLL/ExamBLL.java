package BLL;

import java.util.ArrayList;

import DAL.ExamsDAL;
import DTO.ExamDTO;

public class ExamBLL {
	private ExamsDAL dalexam;
	
	public ExamBLL() {
		dalexam = new ExamsDAL();
	}
	//lay danh sach tat ca bai thi
	public ArrayList<ExamDTO> getAllExam(){
		return dalexam.getAllExams();
	}
	// lay thong tin bai thi theo examCode
	public ExamDTO getAllExamByExCode(String exCode){
		if(exCode == null || exCode.trim().isEmpty()) {
			return null;
		}
		return dalexam.getExamByCode(exCode);
	}
	//lay danh sach de thi theo test code
	public ArrayList<ExamDTO> getExamsByTestCode(String testCode){
		return dalexam.getExamsByTestCode(testCode);
	}
	//them mot de thi moi 
	public boolean addExam(ExamDTO e) {
		if(e.getExCode() == null || e.getExCode().trim().isEmpty()) {
			return false;
		}
		return dalexam.addExam(e);
	}
	// Cập nhật thông tin đề thi
    public boolean updateExam(ExamDTO exam) {
        if (exam.getExCode() == null || exam.getExCode().trim().isEmpty()) {
            return false;
        }
        return dalexam.updateExam(exam);
    }

    // Xóa đề thi theo mã examCode
    public boolean deleteExam(String examCode) {
        if (examCode == null || examCode.trim().isEmpty()) {
            return false;
        }
        return dalexam.deleteExam(examCode);
    }
}
