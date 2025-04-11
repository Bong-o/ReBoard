package kr.co.green.board.model.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.green.board.controller.BoardResDto;
import kr.co.green.board.model.dto.BoardDTO;
import kr.co.green.board.model.dto.PageInfoDTO;
import kr.co.green.board.model.dto.SearchDTO;
import kr.co.green.board.model.mapper.BoardMapper;
import kr.co.green.board.model.mapper.BoardRepository;
import kr.co.green.board.util.FileUpload;

@Service	
public  class FreeBoardServiceImpl implements BoardService {
	private final BoardRepository boardRepository;
	private final BoardMapper boardMapper;
	private final FileUpload fu;
	
	public FreeBoardServiceImpl(BoardMapper boardmapper, FileUpload fu, BoardRepository boardRepository) {
		this.boardMapper = boardmapper;
		this.fu = fu;
		this.boardRepository = boardRepository;
	}
	
	@Override
	public List<BoardResDto> getBoardList() {
        return boardRepository.findAllByOrderByIdDesc().stream()
                .map(BoardResDto::new)
                .collect(Collectors.toList());
    }
	
	
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
//	
	@Override
	public int enroll(BoardDTO boardDTO, MultipartFile file) {
		
		int result = 0;
		
		result = boardMapper.enroll(boardDTO);
		
		if(result == 1 && file != null && !file.isEmpty()) {
			try {
				fu.uploadFile(file, boardDTO.getFileDTO(), "free");
				boardMapper.enrollFile(boardDTO);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		return result;
	}
	
	@Override
	public BoardDTO detail(int no) {
		// 조회수 1 증가
		int addViewCount = boardMapper.addViewCount(no);
		
		if(addViewCount == 1) {
			// no가지고 file 테이블에 데이터가 있는지
			// 데이터가 있으면 boardDTO.fileDTO에 넣고
			BoardDTO fileCheck = boardMapper.getFileInfo(no);
			BoardDTO result;
			
			if(fileCheck != null) {
				fileCheck.setNo(no);
				result = boardMapper.detailFile(fileCheck);
				result.setFileDTO(fileCheck.getFileDTO());
			} else {
				result = boardMapper.detail(no);
			}
			
			// free_board 테이블 다시 SELECT해서 제목, 내용 가져오기
			return result;
		} else {
			return null;
		}
	}
	
	@Override
	public BoardDTO updateForm(int no) {
		return boardMapper.updateForm(no);
	}
	
	@Override
	public int update(BoardDTO boardDTO, int memberNo, MultipartFile file) {
		// boardDTO : 제목, 내용, 게시글 no
		// memeberNo : 로그인한 사용자의 no
		
		// 1. 게시글 no로 조회(SELECT)해서 글 작성자 no(author_no)를 가져오기
		int getAuthorNo = boardMapper.getAuthorNo(boardDTO.getNo());
//		System.out.println("글 작성자 : "+getAuthorNo.getAuthorNo());
//		System.out.println("로그인 사용자 : "+memberNo);
		
		// 2. 글 작성자 no(author_no)와 로그인한 사용자의 no(memberNo)가 같은 지 확인
		if(memberNo == getAuthorNo) {
			// update가 수행 되게 (제목, 내용을 변경)
			if(file != null && !file.isEmpty()) {
				BoardDTO fileCheck = boardMapper.getFileInfo(boardDTO.getNo());
				String fileName = fileCheck.getFileDTO().getChangeName();
				String localPath = fileCheck.getFileDTO().getLOCAL_PATH();
				
				boardMapper.deleteFile(fileName);
				
				try {
					fu.deleteFile(localPath, "free", fileName);
					
					// 새로운 파일 업로드 및 insert(update)
					fu.uploadFile(file, boardDTO.getFileDTO(), "free");
					boardMapper.enrollFile(boardDTO);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		int result = boardMapper.update(boardDTO);
		return result;
	}
	
	@Override
	public int delete(int no, int memberNo, String fileName) {
		int requestAuthorNo = boardMapper.getAuthorNo(no);
		
		if(requestAuthorNo == memberNo) {
			// 1. 서버에 저장된 파일 삭제
			BoardDTO boardDTO = new BoardDTO();
			
			try {
				fu.deleteFile(boardDTO.getFileDTO().getLOCAL_PATH(), "free", fileName);
				boardMapper.deleteFile(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// 2. DB 파일 테이블에서 삭제
			int result = boardMapper.delete(no);
			return result;
		}
		return 0;
	}


}
