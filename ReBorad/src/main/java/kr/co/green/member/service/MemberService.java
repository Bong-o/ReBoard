package kr.co.green.member.service;

import kr.co.green.member.data.dto.SigninReqDto;
import kr.co.green.member.data.dto.SigninResDto;
import kr.co.green.member.data.dto.SignupReqDto;

public interface MemberService {
	
	public boolean checkId(String memberId);
	
	public void signup(SignupReqDto memberReqDto);

	SigninResDto signin(SigninReqDto signinReqDto);

}
