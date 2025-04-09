package kr.co.green.board.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
	private int no;
	private String title;
	private String content;
	private String createDate;
	private String updateDate;
	private int viewCount;
	private AuthorDTO authorDTO = new AuthorDTO();
	private FileDTO fileDTO = new FileDTO();
 }

