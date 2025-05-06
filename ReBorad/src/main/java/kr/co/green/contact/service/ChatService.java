package kr.co.green.contact.service;

import java.util.List;

import kr.co.green.contact.data.dto.ChatMessage;
import kr.co.green.contact.data.entity.ChatMessageEntity;
import kr.co.green.contact.data.entity.ChatRoomEntity;

public interface ChatService {
	
	public void sendMessage(ChatMessage messageDto);
	
	public List<ChatMessageEntity> getMessagesByRoomId(String roomId);
	
	public void joinRoom(String roomId, String userId);
	
	public ChatRoomEntity createRoom(String name, String password);
	
	public List<ChatRoomEntity> getAllRooms();
}
