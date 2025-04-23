package kr.co.green.contact.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.green.contact.entity.ChatMessageEntity;
import kr.co.green.contact.entity.ChatRoomEntity;
import kr.co.green.contact.model.dto.ChatMessage;
import kr.co.green.contact.model.service.ChatService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
	
//	private final SimpMessagingTemplate messagingTemplate;
//	
//	public ChatController(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//	// 클라이언트가 /app/chat으로 메시지를 보내면
//	@MessageMapping("/chat.sendMessage")
//	public void sendMessage(@Payload ChatMessage message) {
//		messagingTemplate.convertAndSend("/topic/chatroom." + message.getRoomId(), message);
//	}
//
//	// 해당 메시지를 /topic/messages로 구독중인 클라이언트에게 전송
//	@SendTo("/topic/messages")
//	public ChatMessage send(ChatMessage message) {
//		return message; // 그대로 전달
//	}
	
	 private final ChatService chatService;

    // 메시지 전송 (WebSocket)
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage messageDto) {
        chatService.sendMessage(messageDto);
    }

    // 특정 채팅방의 메시지 조회 (REST)
    @GetMapping("/room/{roomId}/messages")
    public List<ChatMessageEntity> getMessages(@PathVariable String roomId) {
        return chatService.getMessagesByRoomId(roomId);
    }

    // 사용자 채팅방 참여
    @PostMapping("/room/{roomId}/join")
    public ResponseEntity<String> joinRoom(@PathVariable String roomId, @RequestParam String userId) {
        chatService.joinRoom(roomId, userId);
        return ResponseEntity.ok("참여 완료");
    }

    // 채팅방 생성
    @PostMapping("/room")
    public ResponseEntity<ChatRoomEntity> createRoom(@RequestParam String name) {
        return ResponseEntity.ok(chatService.createRoom(name));
    }
}
