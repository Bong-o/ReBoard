package kr.co.green.member.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.green.jwt.JwtUtil;
import kr.co.green.member.model.dto.SigninReqDto;
import kr.co.green.member.model.dto.SigninResDto;
import kr.co.green.member.model.dto.SignupReqDto;
import kr.co.green.member.model.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberServiceImpl memberService;
	private final JwtUtil jwtUtil;
//	private static final Logger logger = LogManager.getLogger(MemberController.class);

	@GetMapping("/signup/form")
	public String signupForm() {
		return "/member/signup";
	}

	@GetMapping("/checkId/{id}")
	@ResponseBody
	public boolean checkId(@PathVariable("id") String memberId) {
		return memberService.checkId(memberId);
	}

	@PostMapping("/signup")
	public String signup(SignupReqDto memberReqDto) {

		memberService.signup(memberReqDto);

		return "/member/signin";
	}

	@GetMapping("/signin/form")
	public String signinForm() {
		return "/member/signin";
	}

	@PostMapping("/signin")
	public String signin(SigninReqDto signinReqDto, HttpServletResponse response) {

		SigninResDto signinResDto = memberService.signin(signinReqDto);

		if (signinResDto != null) {
			String accessToken = jwtUtil.generateAccessToken(signinResDto.getId());
	        String refreshToken = jwtUtil.generateRefreshToken(signinResDto.getId());

			Cookie accessCookie = new Cookie("access_token", accessToken);
			accessCookie.setHttpOnly(true); // JS에서 접근 못하게
			accessCookie.setPath("/"); // 전체 경로에 적용
			accessCookie.setMaxAge(60 * 60); // 15분
			
			Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
	        refreshCookie.setHttpOnly(true);
	        refreshCookie.setPath("/");
	        refreshCookie.setMaxAge(60 * 60 * 24 * 7); // 7일
			
	        response.addCookie(accessCookie);
	        response.addCookie(refreshCookie);
	        
			return "redirect:/";
		} else {
			return "redirect:/member/signin?error=true";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletResponse response) {
		Cookie accessCookie = new Cookie("access_token", null);
	    accessCookie.setMaxAge(0); // 쿠키 삭제
	    accessCookie.setHttpOnly(true); // 처음 설정했던 속성 유지
	    accessCookie.setPath("/");      // 루트 경로로 지정했으니 여기서도 똑같이
	    response.addCookie(accessCookie);
	    
	    Cookie refreshCookie = new Cookie("refresh_token", null);
	    refreshCookie.setMaxAge(0);
	    refreshCookie.setHttpOnly(true);
	    refreshCookie.setPath("/");
	    response.addCookie(refreshCookie);
	    System.out.println("test");
	    return "redirect:/";
	}
	
//	@PostMapping("/refresh")
//	public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
//	    Cookie[] cookies = request.getCookies();
//
//	    if (cookies != null) {
//	        for (Cookie cookie : cookies) {
//	            if ("refresh_token".equals(cookie.getName())) {
//	                String refreshToken = cookie.getValue();
//	                if (jwtUtil.validateToken(refreshToken)) {
//	                    String userId = jwtUtil.getUserIdFromToken(refreshToken);
//	                    String newAccessToken = jwtUtil.generateAccessToken(userId);
//
//	                    Cookie newAccessCookie = new Cookie("access_token", newAccessToken);
//	                    newAccessCookie.setHttpOnly(true);
//	                    newAccessCookie.setPath("/");
//	                    response.addCookie(newAccessCookie);
//
//	                    return ResponseEntity.ok("Access token refreshed");
//	                }
//	            }
//	        }
//	    }
//
//	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
//	}

}
