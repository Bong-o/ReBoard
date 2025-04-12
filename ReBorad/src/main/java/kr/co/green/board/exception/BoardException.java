package kr.co.green.board.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BoardException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private final HttpStatus status;
	private final String path;
	
	public BoardException(String message, String path, HttpStatus status) {
		super(message);
		this.path = path;
		this.status = status;
	}
	
}