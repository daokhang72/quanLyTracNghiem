package DTO;

public class AnswerDTO {
    private int awID;
    private int qID;
    private String awContent;
    private String awPictures;
    private boolean isRight;
    private int awStatus;

    public AnswerDTO() {}

    public AnswerDTO(int awID, int qID, String awContent, String awPictures, boolean isRight, int awStatus) {
        this.awID = awID;
        this.qID = qID;
        this.awContent = awContent;
        this.awPictures = awPictures;
        this.isRight = isRight;
        this.awStatus = awStatus;
    }

    public int getAwID() {
        return awID;
    }

    public void setAwID(int awID) {
        this.awID = awID;
    }

    public int getQID() {
        return qID;
    }

    public void setQID(int qID) {
        this.qID = qID;
    }

    public String getAwContent() {
        return awContent;
    }

    public void setAwContent(String awContent) {
        this.awContent = awContent;
    }

    public String getAwPictures() {
        return awPictures;
    }

    public void setAwPictures(String awPictures) {
        this.awPictures = awPictures;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean isRight) {
        this.isRight = isRight;
    }

    public int getAwStatus() {
        return awStatus;
    }

    public void setAwStatus(int awStatus) {
        this.awStatus = awStatus;
    }
}
