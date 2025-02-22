package BLL;

import java.util.ArrayList;

import DAL.TopicsDAL;
import DTO.TopicDTO;

public class TopicBLL {
	private TopicsDAL daltopic;
	
	public TopicBLL() {
		daltopic = new TopicsDAL();
	}
	// lấy danh sách tất cả các chủ đề
	public ArrayList<TopicDTO> getAllTopic(){
		return daltopic.dsTopic();
	}
	// lấy chủ đề theo id
	public TopicDTO getTopicByID(int tpId){
		return daltopic.getTopicByID(tpId);
	}
	// thêm 1 chủ đề mới
	public boolean addTopic(TopicDTO t) {
		if(t.getTpTitle() == null || t.getTpTitle().trim().isEmpty()) {
			return false;
		}
		return daltopic.addTopic(t);
	}
	// cập nhật thông tin chủ đề
	public boolean updateTopic(TopicDTO t) {
		if(t.getTpID() <= 0) {
			return false;
		}
		return daltopic.updateTopic(t);
	}
	// xóa chủ đề theo id
	public boolean deleteTopic(int tpID) {
		return daltopic.deleteTopic(tpID);
	}
}
