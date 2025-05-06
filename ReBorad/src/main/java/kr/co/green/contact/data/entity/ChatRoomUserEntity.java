package kr.co.green.contact.data.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.co.green.member.data.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CHAT_ROOM_USERS", schema = "REBOARD",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"ROOM_ID", "USER_ID"}))
@Getter
@Setter
public class ChatRoomUserEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
    private ChatRoomEntity chatRoom;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private MemberEntity user;

    @Column(name = "JOIN_AT")
    private LocalDateTime joinAt = LocalDateTime.now();
    
}