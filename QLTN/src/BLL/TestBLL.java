package BLL;

import java.util.ArrayList;

import DAL.TestDAL;
import DTO.TestDTO;

public class TestBLL {
	private TestDAL daltest;
	
	public TestBLL() {
		daltest = new TestDAL();
	}
	// lay bai thi theo id
	public TestDTO getTestByID(int testID) {
		return daltest.getTestByID(testID);
	}
	//lay danh sach tat ca bai thi
	public ArrayList<TestDTO> getAllTest(){
		return daltest.getAllTests();
	}
	//lay danh sach bai thi theo chu de
	public ArrayList<TestDTO> getTestsByTopicID(int tpID){
		return daltest.getTestsByTopicID(tpID);
	}
	//them mot bai thi moi
	public boolean addTest(TestDTO t) {
		if(t.getTestTitle() == null || t.getTestTitle().trim().isEmpty()) {
			return false;
		}
		return daltest.addTest(t);
	}
	//cap nhat thong tin bai thi
	public boolean updateTest(TestDTO t) {
		if(t.getTestID() <= 0) {
			return false;
		}
		return daltest.updateTest(t);
	}
	//xoa bai thi theo id
	public boolean deleteTest(int testID) {
		return daltest.deleteTest(testID);
	}
}
