package DTO;

public class TopicDTO {
	private int tpID;
	private String tpTitle;
	private int tpParent;
	private int tpStatus;
	
	public TopicDTO() {}

	public TopicDTO(int tpID, String tpTitle, int tpParent, int tpStatus) {
		super();
		this.tpID = tpID;
		this.tpTitle = tpTitle;
		this.tpParent = tpParent;
		this.tpStatus = tpStatus;
	}

	public int getTpID() {
		return tpID;
	}

	public void setTpID(int tpID) {
		this.tpID = tpID;
	}

	public String getTpTitle() {
		return tpTitle;
	}

	public void setTpTitle(String tpTitle) {
		this.tpTitle = tpTitle;
	}

	public int getTpParent() {
		return tpParent;
	}

	public void setTpParent(int tpParent) {
		this.tpParent = tpParent;
	}

	public int getTpStatus() {
		return tpStatus;
	}

	public void setTpStatus(int tpStatus) {
		this.tpStatus = tpStatus;
	}
	
	@Override
	public String toString() {
	    return tpTitle;
	}

}
