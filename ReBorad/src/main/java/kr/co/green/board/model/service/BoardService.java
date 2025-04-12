package kr.co.green.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import kr.co.green.board.controller.BoardResDto;
import kr.co.green.board.entity.BoardEntity;
import kr.co.green.board.model.dto.BoardDTO;

public interface BoardService {
	
	//public List<BoardResDto> getBoardList();
	
	Page<BoardResDto> getBoards(Pageable pageable);
	
	public BoardResDto detail(Long no);
	
	
	
//	public Map<String, Object> getAllPosts(TestPagination pagination,
//										   int currentPage, 
//										   int postCount,
//										   int pageLimit,
//										   int boardLimit,
//										   SearchDTO searchDTO);
//	public int getTotalCount(SearchDTO searchDTO); // 전체 게시글 수
	public int enroll(BoardDTO boardDTO, MultipartFile file);
	public BoardDTO updateForm(int no);
	public int update(BoardDTO boardDTO, int memberNo, MultipartFile file);
	public int delete(int no, int memberNo, String fileName);
}
