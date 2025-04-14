package kr.co.green.config;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.co.green.jwt.JwtUtil;
import kr.co.green.member.entity.MemberEntity;
import kr.co.green.member.model.dto.SigninResDto;
import kr.co.green.member.model.mapper.MemberRepository;

@Component
@ControllerAdvice
public class GlobalModelAttributeAdvice {

	private final JwtUtil jwtUtil;
	private final MemberRepository memberRepository;

	public GlobalModelAttributeAdvice(JwtUtil jwtUtil, MemberRepository memberRepository) {
		this.jwtUtil = jwtUtil;
		this.memberRepository = memberRepository;
	}

	@ModelAttribute
	public void addCommonAttributes(Model model,
									@CookieValue(value = "access_token", required = false) String accessToken) {

		if (accessToken != null && jwtUtil.validateToken(accessToken)) {
			String memberId = jwtUtil.getUserIdFromToken(accessToken);
			
			Optional<MemberEntity> memberOpt = memberRepository.findById(memberId);
			
			if (memberOpt.isPresent()) {
				SigninResDto memberResDto = new SigninResDto(memberOpt.get());
			    model.addAttribute("memberName", memberResDto.getName());
			}
		}
	}
	
}
