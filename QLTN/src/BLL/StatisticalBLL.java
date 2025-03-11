package BLL;

import java.util.ArrayList;

import DAL.StatisticalDAL;

public class StatisticalBLL {
	private StatisticalDAL thongke;
	
	public StatisticalBLL() {
		thongke = new StatisticalDAL();
	}
	public ArrayList<Object[]> getAllUsers(){
		return thongke.getAllUsers();
	}
	public ArrayList<Object[]> getAllTests(){
		return thongke.getAllTests();
	}
	public int countTotalStudents() {
		return thongke.countTotalStudents();
	}
	public double getAverageScore() {
		return thongke.getAverageScore();
	}
	public int getLogStatistics() {
		return thongke.getLogStatistics();
	}
	public ArrayList<Object[]> getPassFailParticipants(String testCode){
		return thongke.getPassFailParticipants(testCode);
	}
}
