package kr.co.green.board.data.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;

@Getter
public class CommentResDto {

	private String content;
	private String author;
	private String createdAt;
	
	public CommentResDto(String content, String author, LocalDateTime createdAt) {
        this.content = content;
        this.author = author;
        this.createdAt = formatDateTime(createdAt);  // 생성 시 포맷팅
    }

    private String formatDateTime(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
        return createdAt.format(formatter);
    }
	
}
