package kr.co.green.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import kr.co.green.board.model.dto.BoardDTO;
import kr.co.green.board.model.dto.BoardReqDto;
import kr.co.green.board.model.dto.BoardResDto;
import kr.co.green.board.model.dto.CommentResDto;

public interface BoardService {
	
	//public List<BoardResDto> getBoardList();
	
	Page<BoardResDto> getBoards(Pageable pageable);
	
	public BoardResDto detail(Long no);
	
	public void enroll(BoardReqDto boardReqDto);
	
	public BoardResDto updateForm(Long no);
	
	public void update(BoardReqDto boardReqDto);
	
	public void delete(Long no);
	
	public List<CommentResDto> comment(Long no);
	
	//public int enroll(BoardDTO boardDTO, MultipartFile file);
	
//	public Map<String, Object> getAllPosts(TestPagination pagination,
//										   int currentPage, 
//										   int postCount,
//										   int pageLimit,
//										   int boardLimit,
//										   SearchDTO searchDTO);
//	public int getTotalCount(SearchDTO searchDTO); // 전체 게시글 수
	//public int update(BoardDTO boardDTO, int memberNo, MultipartFile file);
}
