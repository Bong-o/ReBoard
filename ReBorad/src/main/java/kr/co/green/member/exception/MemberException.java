package kr.co.green.member.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private final HttpStatus status;
	private final String path;
	
	public MemberException(String message, String path, HttpStatus status) {
		super(message);
		this.path = path;
		this.status = status;
	}
	
}
