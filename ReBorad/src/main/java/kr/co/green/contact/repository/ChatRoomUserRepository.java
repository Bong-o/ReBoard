package kr.co.green.contact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.green.contact.entity.ChatRoomUserEntity;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUserEntity, Long> {
	List<ChatRoomUserEntity> findByChatRoom_RoomId(String roomId);
    List<ChatRoomUserEntity> findByUser_Id(String userId);
}