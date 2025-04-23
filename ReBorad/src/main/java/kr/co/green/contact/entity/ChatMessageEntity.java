package kr.co.green.contact.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co.green.member.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CHAT_MESSAGES", schema = "REBOARD")
@Getter
@Setter
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_ID")
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
    private ChatRoomEntity chatRoom;

    @ManyToOne
    @JoinColumn(name = "SENDER_ID", referencedColumnName = "ID")
    private	MemberEntity sender;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    
}
