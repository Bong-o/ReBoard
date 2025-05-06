package kr.co.green.contact.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import kr.co.green.contact.data.dto.ChatMessage;
import kr.co.green.contact.data.entity.ChatMessageEntity;
import kr.co.green.contact.data.entity.ChatRoomEntity;
import kr.co.green.contact.data.entity.ChatRoomUserEntity;
import kr.co.green.contact.repository.ChatMessageRepository;
import kr.co.green.contact.repository.ChatRoomRepository;
import kr.co.green.contact.repository.ChatRoomUserRepository;
import kr.co.green.member.data.entity.MemberEntity;
import kr.co.green.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
	
	private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final MemberRepository memberRepository;
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
    		message.setSender(memberRepository.findById(messageDto.getSender()).orElseThrow());
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
//    	ChatRoomEntity room = chatRoomRepository.findById(roomId)
//    		    .orElseThrow(() -> new RuntimeException("Room not found"));
//
//		ChatRoomUserEntity roomUser = new ChatRoomUserEntity();
//		roomUser.setChatRoom(room); // 여기서 roomId가 아니라 ChatRoomEntity를 넘겨줘야 함
    	ChatRoomEntity room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        MemberEntity user = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean exists = chatRoomUserRepository.existsByChatRoomAndUser(room, user);
        if (!exists) {
            ChatRoomUserEntity roomUser = new ChatRoomUserEntity();
            roomUser.setChatRoom(room);
            roomUser.setUser(user);
            roomUser.setJoinAt(LocalDateTime.now());

            chatRoomUserRepository.save(roomUser);
        } else {
            // 이미 있으면 무시하거나 로깅
            System.out.println("User already in room, skipping insert.");
        }
    }

    // 채팅방 생성
    public ChatRoomEntity createRoom(String name, String password) {
    	ChatRoomEntity room = new ChatRoomEntity();
        room.setRoomId(UUID.randomUUID().toString());
        room.setRoomName(name);
        room.setCreatedAt(LocalDateTime.now());
        room.setRoomPassword(password);

        return chatRoomRepository.save(room);
    }
    
    // 방 목록 조회
    public List<ChatRoomEntity> getAllRooms() {
        return chatRoomRepository.findAll();  // 방 목록을 DB에서 가져오기
    }

}
