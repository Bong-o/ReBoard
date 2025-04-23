package kr.co.green.contact.model.service;

import java.util.List;

import kr.co.green.contact.entity.ChatMessageEntity;
import kr.co.green.contact.entity.ChatRoomEntity;
import kr.co.green.contact.model.dto.ChatMessage;

public interface ChatService {
	
	public void sendMessage(ChatMessage messageDto);
	
	public List<ChatMessageEntity> getMessagesByRoomId(String roomId);
	
	public void joinRoom(String roomId, String userId);
	
	public ChatRoomEntity createRoom(String name);
}
