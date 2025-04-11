package kr.co.green.board.model.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.green.board.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	
	List<BoardEntity> findAllByOrderByIdDesc();

}
