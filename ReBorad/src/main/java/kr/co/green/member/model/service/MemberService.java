package kr.co.green.member.model.service;

import kr.co.green.member.model.dto.SigninReqDto;
import kr.co.green.member.model.dto.SigninResDto;
import kr.co.green.member.model.dto.SignupReqDto;

public interface MemberService {
	
	public boolean checkId(String memberId);
	
	public void signup(SignupReqDto memberReqDto);

	SigninResDto signin(SigninReqDto signinReqDto);

}
