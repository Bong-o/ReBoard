package kr.co.green.contact.data.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
	
	private String sender;
    private String content;
    private String roomId;
    private String timestamp;
    private LocalDateTime createdAt;
    private String type;
    
}
