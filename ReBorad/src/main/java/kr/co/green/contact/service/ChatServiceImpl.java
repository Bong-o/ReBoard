package kr.co.green.contact.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import kr.co.green.contact.entity.ChatMessageEntity;
import kr.co.green.contact.entity.ChatRoomEntity;
import kr.co.green.contact.entity.ChatRoomUserEntity;
import kr.co.green.contact.model.dto.ChatMessage;
import kr.co.green.contact.repository.ChatMessageRepository;
import kr.co.green.contact.repository.ChatRoomRepository;
import kr.co.green.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
	
	private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memeberRepository;
    // 메시지 전송 및 저장
    public void sendMessage(ChatMessage messageDto) {
    	
    	if ("ENTER".equals(messageDto.getType())) {
            messageDto.setContent(messageDto.getSender() + "님이 입장했습니다.");
        } else if ("EXIT".equals(messageDto.getType())) {
            messageDto.setContent(messageDto.getSender() + "님이 퇴장했습니다.");
        }
    	
    	if ("TALK".equals(messageDto.getType())) {
    		ChatMessageEntity message = new ChatMessageEntity();
    		message.setChatRoom(chatRoomRepository.findById(messageDto.getRoomId()).orElseThrow());
    		message.setSender(memeberRepository.findById(messageDto.getSender()).orElseThrow());
    		message.setContent(messageDto.getContent());
    		message.setCreatedAt(LocalDateTime.now());
    		
    		chatMessageRepository.save(message);
    	}

        // WebSocket을 통해 메시지 전송
        messagingTemplate.convertAndSend("/topic/chatroom." + messageDto.getRoomId(), messageDto);
    }

    // 채팅방의 메시지 목록 조회
    public List<ChatMessageEntity> getMessagesByRoomId(String roomId) {
        return chatMessageRepository.findByChatRoom_RoomId(roomId);
    }

    // 사용자 채팅방 참여
    public void joinRoom(String roomId, String userId) {
    	ChatRoomEntity room = chatRoomRepository.findById(roomId)
    		    .orElseThrow(() -> new RuntimeException("Room not found"));

		ChatRoomUserEntity roomUser = new ChatRoomUserEntity();
		roomUser.setChatRoom(room); // 여기서 roomId가 아니라 ChatRoomEntity를 넘겨줘야 함
    }

    // 채팅방 생성
    public ChatRoomEntity createRoom(String name) {
    	ChatRoomEntity room = new ChatRoomEntity();
        room.setRoomId(UUID.randomUUID().toString());
        room.setRoomName(name);
        room.setCreatedAt(LocalDateTime.now());

        return chatRoomRepository.save(room);
    }

}
