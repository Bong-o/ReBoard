package kr.co.green.board.data.dto;

import java.time.format.DateTimeFormatter;

import kr.co.green.board.data.entity.BoardEntity;
import lombok.Getter;

@Getter
public class BoardResDto {
	
	private Long no;
	private String title;
	private String content;
	private String authorName;
	private String createdAt;
	private int views;

	public BoardResDto(BoardEntity entity) {
		this.no = entity.getNo();
	    this.title = entity.getTitle();
	    this.content = entity.getContent();
	    this.authorName = entity.getAuthor().getUsername();
	    this.views = entity.getViews();
	    
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
	    this.createdAt = entity.getCreatedAt().format(formatter);
	}
	
}
