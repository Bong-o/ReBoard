package kr.co.green.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.green.contact.data.entity.ChatRoomEntity;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, String> {
}
