package kr.co.green.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import kr.co.green.board.model.dto.BoardDTO;
import kr.co.green.board.model.dto.PageInfoDTO;
import kr.co.green.board.model.dto.SearchDTO;

@Mapper
public interface BoardMapper {
	List<BoardDTO> getAllPosts(@Param("pi") PageInfoDTO pi, 
							   @Param("searchDTO") SearchDTO searchDTO);
	
	int getTotalCount(SearchDTO searchDTO);
	
	int enroll(BoardDTO boardDTO);

	BoardDTO detail(int no);
	
	int addViewCount(int no);

	BoardDTO updateForm(int no);

	int update(BoardDTO boardDTO);

	int delete(int no);

	int getAuthorNo(int no);

	int enrollFile(BoardDTO boardDTO);

	BoardDTO getFileInfo(int no);

	BoardDTO detailFile(BoardDTO fileCheck);

	void deleteFile(String fileName);
	
	

}
