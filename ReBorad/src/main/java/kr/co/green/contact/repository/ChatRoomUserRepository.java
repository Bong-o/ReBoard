package kr.co.green.contact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.green.contact.data.entity.ChatRoomEntity;
import kr.co.green.contact.data.entity.ChatRoomUserEntity;
import kr.co.green.member.data.entity.MemberEntity;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUserEntity, Long> {
	List<ChatRoomUserEntity> findByChatRoom_RoomId(String roomId);
    List<ChatRoomUserEntity> findByUser_Id(String userId);
    boolean existsByChatRoomAndUser(ChatRoomEntity chatRoom, MemberEntity user);
}