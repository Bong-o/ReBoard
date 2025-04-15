package kr.co.green.board.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.green.board.entity.BoardEntity;
import kr.co.green.board.entity.CommentEntity;
import kr.co.green.board.exception.BoardException;
import kr.co.green.board.model.dto.BoardReqDto;
import kr.co.green.board.model.dto.BoardResDto;
import kr.co.green.board.model.dto.CommentResDto;
import kr.co.green.board.model.mapper.BoardMapper;
import kr.co.green.board.model.mapper.BoardRepository;
import kr.co.green.board.model.mapper.CommentRepository;
import kr.co.green.board.util.FileUpload;
import kr.co.green.member.entity.MemberEntity;

@Service	
public  class FreeBoardServiceImpl implements BoardService {
	private final BoardRepository boardRepository;
	private final CommentRepository commentRepository;
	private final BoardMapper boardMapper;
	private final FileUpload fu;
	
	public FreeBoardServiceImpl(BoardMapper boardmapper, FileUpload fu, 
								BoardRepository boardRepository,
								CommentRepository commentRepository) {
		this.boardMapper = boardmapper;
		this.fu = fu;
		this.boardRepository = boardRepository;
		this.commentRepository = commentRepository;
	}
	
//	@Override
//	public List<BoardResDto> getBoardList() {
//        return boardRepository.findAllByOrderByIdDesc().stream()
//                .map(BoardResDto::new)
//                .collect(Collectors.toList());
//    }
	
	public Page<BoardResDto> getBoards(Pageable pageable) {
		 Page<BoardEntity> entityPage = boardRepository.findAll(pageable);
		    return entityPage.map(BoardResDto::new); // 각각의 엔티티를 DTO로 변환
    }

    // 검색 기능 추가 버전
//    public Page<BoardEntity> searchBoards(String keyword, Pageable pageable) {
//        return boardRepository.findByTitleContaining(keyword, pageable);
//    }
	
	
	@Override
	public void enroll(BoardReqDto boardReqDto) {
		
		BoardEntity entity = BoardEntity.builder()
		        .title(boardReqDto.getTitle())
		        .content(boardReqDto.getContent())
		        .author(MemberEntity.builder().id(boardReqDto.getAuthorName()).build()) // 중요!
		        .createdAt(LocalDateTime.now())
		        .build();

		    boardRepository.save(entity); 
	}
	
	@Override
	public BoardResDto detail(Long no) {
		
		boardRepository.updateViewCount(no);
		
		BoardEntity board = boardRepository.findById(no)
			    .orElseThrow(() -> new BoardException(no + "번 게시글이 존재하지 않습니다.", "/error/boardError", HttpStatus.BAD_GATEWAY));
		
		return new BoardResDto(board);
	}
	
	@Override
	public List<CommentResDto> comment(Long no) {
		
	    List<CommentEntity> comments = commentRepository.findByPostNo(no);
	    
	    return comments.stream()
	        .map(comment -> new CommentResDto(
	            comment.getContent(),
	            comment.getAuthor().getId(),
	            comment.getCreatedAt()))
	        .collect(Collectors.toList());
	}
	
	@Override
	public BoardResDto updateForm(Long no) {
		
		BoardEntity board = boardRepository.findById(no)
			    .orElseThrow(() -> new BoardException(no + "번 게시글이 존재하지 않습니다.", "/error/boardError", HttpStatus.NOT_FOUND));
		
		return new BoardResDto(board);
	}
	
	@Override
	@Transactional
	public void update(BoardReqDto boardReqDto) {
		
	    BoardEntity board = boardRepository.findById((long) boardReqDto.getNo())
	        .orElseThrow(() -> new BoardException("게시글이 존재하지 않습니다.", "/error/boardError", HttpStatus.NOT_FOUND));
	    
	    board.update(boardReqDto.getTitle(), boardReqDto.getContent(), LocalDateTime.now());
	}
	
	@Override
	@Transactional
	public void delete(Long no) {
		
		BoardEntity board = boardRepository.findById(no)
		        .orElseThrow(() -> new BoardException("게시글이 존재하지 않습니다.", "/error/boardError", HttpStatus.NOT_FOUND));
		    
		boardRepository.delete(board);
	}
		


}

//	@Override
//	public int enroll(BoardDTO boardDTO, MultipartFile file) {
//		
//		int result = 0;
//		
//		result = boardMapper.enroll(boardDTO);
//		
//		if(result == 1 && file != null && !file.isEmpty()) {
//			try {
//				fu.uploadFile(file, boardDTO.getFileDTO(), "free");
//				boardMapper.enrollFile(boardDTO);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		
//		return result;
//	}
//	@Override
//	public int update(BoardDTO boardDTO, int memberNo, MultipartFile file) {
//		// boardDTO : 제목, 내용, 게시글 no
//		// memeberNo : 로그인한 사용자의 no
//		
//		// 1. 게시글 no로 조회(SELECT)해서 글 작성자 no(author_no)를 가져오기
//		int getAuthorNo = boardMapper.getAuthorNo(boardDTO.getNo());
////		System.out.println("글 작성자 : "+getAuthorNo.getAuthorNo());
////		System.out.println("로그인 사용자 : "+memberNo);
//		
//		// 2. 글 작성자 no(author_no)와 로그인한 사용자의 no(memberNo)가 같은 지 확인
//		if(memberNo == getAuthorNo) {
//			// update가 수행 되게 (제목, 내용을 변경)
//			if(file != null && !file.isEmpty()) {
//				BoardDTO fileCheck = boardMapper.getFileInfo(boardDTO.getNo());
//				String fileName = fileCheck.getFileDTO().getChangeName();
//				String localPath = fileCheck.getFileDTO().getLOCAL_PATH();
//				
//				boardMapper.deleteFile(fileName);
//				
//				try {
//					fu.deleteFile(localPath, "free", fileName);
//					
//					// 새로운 파일 업로드 및 insert(update)
//					fu.uploadFile(file, boardDTO.getFileDTO(), "free");
//					boardMapper.enrollFile(boardDTO);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			
//		}
//		int result = boardMapper.update(boardDTO);
//		return result;
//	}
//	@Override
//	public Map<String, Object> getAllPosts(Pagination pagination,
//										   int currentPage, 
//										   int postCount,
//										   int pageLimit,
//										   int boardLimit,
//										   SearchDTO searchDTO) {
//		// 페이징 처리
//		PageInfoDTO pi = pagination.getPageInfo(postCount, 
//												currentPage,
//				   								pageLimit,
//				   								boardLimit);
//		
//		// 페이지에 따라서 필요한 게시글들만 SELECT
//		List<BoardDTO> posts = boardMapper.getAllPosts(pi, searchDTO);
//		
//		Map<String, Object> result = new HashMap<>();
//		result.put("pi", pi);
//		result.put("posts", posts);
//		
//		return result;
//	}
//
//	@Override
//	public int getTotalCount(SearchDTO searchDTO) {
//		return boardMapper.getTotalCount(searchDTO);
//	}
//@Override
//public int delete(int no, int memberNo, String fileName) {
//	int requestAuthorNo = boardMapper.getAuthorNo(no);
//	
//	if(requestAuthorNo == memberNo) {
//		// 1. 서버에 저장된 파일 삭제
//		BoardDTO boardDTO = new BoardDTO();
//		
//		try {
//			fu.deleteFile(boardDTO.getFileDTO().getLOCAL_PATH(), "free", fileName);
//			boardMapper.deleteFile(fileName);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		// 2. DB 파일 테이블에서 삭제
//		int result = boardMapper.delete(no);
//		return result;
//	}
//	return 0;
//}