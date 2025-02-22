package DTO;

import java.sql.Date;

public class TestDTO {
    private int testID;
    private String testCode;
    private String testTitle;
    private int testTime;
    private int tpID;
    private int numEasy;
    private int numMedium;
    private int numDiff;
    private int testLimit;
    private Date testDate;
    private int testStatus;

    public TestDTO() {}

    public TestDTO(int testID, String testCode, String testTitle, int testTime, int tpID, int numEasy, int numMedium, int numDiff, int testLimit, Date testDate, int testStatus) {
        this.testID = testID;
        this.testCode = testCode;
        this.testTitle = testTitle;
        this.testTime = testTime;
        this.tpID = tpID;
        this.numEasy = numEasy;
        this.numMedium = numMedium;
        this.numDiff = numDiff;
        this.testLimit = testLimit;
        this.testDate = testDate;
        this.testStatus = testStatus;
    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    public int getTpID() {
        return tpID;
    }

    public void setTpID(int tpID) {
        this.tpID = tpID;
    }

    public int getNumEasy() {
        return numEasy;
    }

    public void setNumEasy(int numEasy) {
        this.numEasy = numEasy;
    }

    public int getNumMedium() {
        return numMedium;
    }

    public void setNumMedium(int numMedium) {
        this.numMedium = numMedium;
    }

    public int getNumDiff() {
        return numDiff;
    }

    public void setNumDiff(int numDiff) {
        this.numDiff = numDiff;
    }

    public int getTestLimit() {
        return testLimit;
    }

    public void setTestLimit(int testLimit) {
        this.testLimit = testLimit;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public int getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(int testStatus) {
        this.testStatus = testStatus;
    }
}
