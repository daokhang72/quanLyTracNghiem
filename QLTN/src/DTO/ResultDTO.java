package DTO;

import java.sql.Date;
import java.sql.Timestamp;

public class ResultDTO {
	private int rsNum;
    private int userID;
    private String exCode;
    private String rsAnswers;
    private double rsMark;
    private Timestamp rsDate;
	public ResultDTO() {}
	public ResultDTO(int rsNum, int userID, String exCode, String rsAnswers, double rsMark, Timestamp rsDate) {
		this.rsNum = rsNum;
		this.userID = userID;
		this.exCode = exCode;
		this.rsAnswers = rsAnswers;
		this.rsMark = rsMark;
		this.rsDate = rsDate;
	}
	public int getRsNum() {
		return rsNum;
	}
	public void setRsNum(int rsNum) {
		this.rsNum = rsNum;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getExCode() {
		return exCode;
	}
	public void setExCode(String exCode) {
		this.exCode = exCode;
	}
	public String getRsAnswers() {
		return rsAnswers;
	}
	public void setRsAnswers(String rsAnswers) {
		this.rsAnswers = rsAnswers;
	}
	public double getRsMark() {
		return rsMark;
	}
	public void setRsMark(double rsMark) {
		this.rsMark = rsMark;
	}
	public Timestamp getRsDate() {
		return rsDate;
	}
	public void setRsDate(Timestamp rsDate) {
		this.rsDate = rsDate;
	}
    
    
}
