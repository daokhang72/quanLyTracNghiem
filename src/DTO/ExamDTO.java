package DTO;

public class ExamDTO {
	private String testCode;
    private String exOrder;
    private String exCode;
    private String exQuesIDs;
    
	public ExamDTO() {}

	public ExamDTO(String testCode, String exOrder, String exCode, String exQuesIDs) {
		this.testCode = testCode;
		this.exOrder = exOrder;
		this.exCode = exCode;
		this.exQuesIDs = exQuesIDs;
	}

	public String getTestCode() {
		return testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public String getExOrder() {
		return exOrder;
	}

	public void setExOrder(String exOrder) {
		this.exOrder = exOrder;
	}

	public String getExCode() {
		return exCode;
	}

	public void setExCode(String exCode) {
		this.exCode = exCode;
	}

	public String getExQuesIDs() {
		return exQuesIDs;
	}

	public void setExQuesIDs(String exQuesIDs) {
		this.exQuesIDs = exQuesIDs;
	}
    
	
}
