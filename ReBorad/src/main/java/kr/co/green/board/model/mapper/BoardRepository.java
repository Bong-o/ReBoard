package kr.co.green.board.model.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.green.board.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	
	//List<BoardEntity> findAllByOrderByIdDesc();
	
	Page<BoardEntity> findAll(Pageable pageable);

    // 검색이 필요한 경우 이런 식으로도 가능
    //Page<BoardEntity> findByTitleContaining(String keyword, Pageable pageable);

}
