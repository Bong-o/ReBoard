package kr.co.green.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.green.jwt.JwtUtil;

@Controller
public class HomeController {
	
	private final JwtUtil jwtUtil;

    public HomeController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
	
	@RequestMapping("/")
	public String home(Model model,
					   @CookieValue(value = "access_token", required = false) String accessToken) {
		
		if (accessToken != null && jwtUtil.validateToken(accessToken)) {
            String memberId = jwtUtil.getUserIdFromToken(accessToken); 
            model.addAttribute("memberName", memberId);
        }
		
		return "/home";
	}
	
	@GetMapping("/rest/test")
	public String test() {
		return "rest";
	}
	
	@GetMapping("/book/form")
	public String bookForm() {
		return "book/book_manage";
	}
	
	
}
