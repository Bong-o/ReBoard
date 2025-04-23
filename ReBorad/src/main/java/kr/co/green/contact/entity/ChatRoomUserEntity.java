package kr.co.green.contact.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co.green.member.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CHAT_ROOM_USERS", schema = "REBOARD")
@Getter
@Setter
public class ChatRoomUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
    private ChatRoomEntity chatRoom;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private MemberEntity user;
    
}