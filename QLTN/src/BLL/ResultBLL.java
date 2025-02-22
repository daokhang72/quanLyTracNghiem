package BLL;

import java.util.ArrayList;

import DAL.ResultDAL;
import DTO.ResultDTO;

public class ResultBLL {
	private ResultDAL dalresult;
	
	public ResultBLL() {
		dalresult = new ResultDAL();
	}
	// lay danh sach tat ca ket qua thi
	public ArrayList<ResultDTO> getAllResult(){
		return dalresult.getAllResults();
	}
	//lay ket qua thi theo ma exCode
	public ArrayList<ResultDTO> getResultsByExamCode(String exCode){
		return dalresult.getResultsByExamCode(exCode);
	}
	// Lấy danh sách kết quả thi theo userID
    public ArrayList<ResultDTO> getResultsByUserID(int userID) {
        return dalresult.getResultsByUserID(userID);
    }
    // Thêm một kết quả thi mới
    public boolean addResult(ResultDTO result) {
        return dalresult.addResult(result);
    }

    // Cập nhật kết quả thi
    public boolean updateResult(ResultDTO result) {
        return dalresult.updateResult(result);
    }

    // Xóa kết quả thi theo rs_num
    public boolean deleteResult(int rsNum) {
        return dalresult.deleteResult(rsNum);
    }

    // Đếm số lượng người dự thi theo mã bài thi
    public int countParticipantsByTest(String testCode) {
        return dalresult.countParticipantsByTest(testCode);
    }

    // Đếm số lượng người đạt >= 50 điểm theo mã bài thi
    public int countPassedParticipantsByTest(String testCode) {
        return dalresult.countPassedParticipantsByTest(testCode);
    }

    // Đếm số lượng người trượt < 50 điểm theo mã bài thi
    public int countFailedParticipantsByTest(String testCode) {
        return dalresult.countFailedParticipantsByTest(testCode);
    }
    // Đếm tổng số lượng dự thi
    public int countTotalStudents() {
    	return dalresult.countTotalStudents();
    }
}

