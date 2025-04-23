package kr.co.green.contact.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CHAT_ROOMS", schema = "REBOARD")
@Getter
@Setter
public class ChatRoomEntity {

    @Id
    @Column(name = "ROOM_ID")
    private String roomId;

    @Column(name = "ROOM_NAME", nullable = false)
    private String roomName;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

//    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
//    private List<ChatMessageEntity> messages = new ArrayList<>();
//
//    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
//    private List<ChatRoomUserEntity> users = new ArrayList<>();

}
