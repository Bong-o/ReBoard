package kr.co.green.handler;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.co.green.member.exception.MemberException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MemberException.class)
	public String handleMemberException(MemberException me, Model model) {

		model.addAttribute("message", me.getMessage());
		
		return me.getPath();
	}
	
	@ExceptionHandler(Exception.class)
	public String handleGlobalException(Exception e, Model model) {
		
		model.addAttribute("message", e.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		return "error/error";
	}
	
}
