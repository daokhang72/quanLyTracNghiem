package DTO;

import java.sql.Date;
import java.sql.Timestamp;

public class LogDTO {
	private int logID;
    private String logContent;
    private int logUserID;
    private int logExID;
    private Timestamp logDate;
	public LogDTO() {}
	public LogDTO(int logID, String logContent, int logUserID, int logExID, Timestamp logDate) {
		this.logID = logID;
		this.logContent = logContent;
		this.logUserID = logUserID;
		this.logExID = logExID;
		this.logDate = logDate;
	}
	public int getLogID() {
		return logID;
	}
	public void setLogID(int logID) {
		this.logID = logID;
	}
	public String getLogContent() {
		return logContent;
	}
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	public int getLogUserID() {
		return logUserID;
	}
	public void setLogUserID(int logUserID) {
		this.logUserID = logUserID;
	}
	public int getLogExID() {
		return logExID;
	}
	public void setLogExID(int logExID) {
		this.logExID = logExID;
	}
	public Timestamp getLogDate() {
		return logDate;
	}
	public void setLogDate(Timestamp logDate) {
		this.logDate = logDate;
	}
    
    
}
