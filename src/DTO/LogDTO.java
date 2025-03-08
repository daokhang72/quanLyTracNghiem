package DTO;

import java.util.Date;

public class LogDTO {
    private int logID;
    private String logContent;
    private int logUserID;
    private int logExID;
    private Date logDate;

    public LogDTO(int logID, String logContent, int logUserID, int logExID, Date logDate) {
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

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }
}