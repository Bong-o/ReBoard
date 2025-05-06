package kr.co.green.contact.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreateDto {
    private String name;
    private String password; // null 허용
}
