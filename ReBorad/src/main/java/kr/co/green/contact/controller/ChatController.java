package kr.co.green.contact.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.green.contact.data.dto.ChatMessage;
import kr.co.green.contact.data.dto.ChatRoomCreateDto;
import kr.co.green.contact.data.entity.ChatMessageEntity;
import kr.co.green.contact.data.entity.ChatRoomEntity;
import kr.co.green.contact.service.ChatService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
	
	private final ChatService chatService;

    // 메시지 전송 (WebSocket)
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage messageDto) {
        chatService.sendMessage(messageDto);
    }

    // 채팅방 메시지 조회
    @GetMapping("/room/{roomId}/messages")
    public List<ChatMessageEntity> getMessages(@PathVariable(name = "roomId") String roomId) {
        return chatService.getMessagesByRoomId(roomId);
    }

    // 사용자 채팅방 참여
    @PostMapping("/room/join")
    public ResponseEntity<String> joinRoom(@RequestParam("userId") String userId, 
    									   @RequestParam("roomId") String roomId) {
        
    	chatService.joinRoom(roomId, userId);
        
        return ResponseEntity.ok("참여 완료");
    }

    // 채팅방 생성
    @PostMapping("/createRoom")
    public ResponseEntity<ChatRoomEntity> createRoom(@RequestBody ChatRoomCreateDto createDto) {
    	ChatRoomEntity savedRoom = chatService.createRoom(createDto.getName(), createDto.getPassword());
        return ResponseEntity.ok(savedRoom);
    }

}
