package BLL;

import java.util.ArrayList;

import DAL.LogsDAL;
import DTO.LogDTO;

public class LogBLL {
	private LogsDAL dallog;
	
	public LogBLL() {
		dallog = new LogsDAL();
	}
	// them log moi
	public boolean addLog(LogDTO l) {
		return dallog.addLog(l);
	}
	//lay log theo id
	public LogDTO getLogByID(int logID) {
		return dallog.getLogByID(logID);
	}
	//lay tat ca log
	public ArrayList<LogDTO> getAllLogs(){
		return dallog.getAllLogs();
	}
	// xoa log theo id
	public boolean deleteLog(int logID) {
		return dallog.deleteLog(logID);
	}
	// lay log theo userID
	public ArrayList<LogDTO> getLogsByUserID(int exCode){
		return dallog.getLogsByExID(exCode);
	}
}
