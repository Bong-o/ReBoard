package kr.co.green.config;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.co.green.jwt.JwtUtil;

@Component
@ControllerAdvice
public class GlobalModelAttributeAdvice {

	private final JwtUtil jwtUtil;

	public GlobalModelAttributeAdvice(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@ModelAttribute
	public void addCommonAttributes(Model model,
									@CookieValue(value = "access_token", required = false) String accessToken) {

		if (accessToken != null && jwtUtil.validateToken(accessToken)) {
			String memberId = jwtUtil.getUserIdFromToken(accessToken);
			model.addAttribute("memberName", memberId);
		}
	}
}
