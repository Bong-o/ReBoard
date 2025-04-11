package kr.co.green.board.controller;

import java.time.format.DateTimeFormatter;

import kr.co.green.board.entity.BoardEntity;
import lombok.Getter;

@Getter
public class BoardResDto {
	
	private Long id;
	private String title;
	private String authorName;
	private String createdAt;
	private int views;
	
	public BoardResDto(BoardEntity entity) {
		this.id = entity.getId();
	    this.title = entity.getTitle();
	    this.authorName = entity.getAuthor().getUsername();
	    this.views = entity.getViews();
	    
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
	    this.createdAt = entity.getCreatedAt().format(formatter);

	}
	
}
