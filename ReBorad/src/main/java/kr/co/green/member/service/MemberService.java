package kr.co.green.member.service;

import kr.co.green.member.dto.req.SigninReqDto;
import kr.co.green.member.dto.req.SignupReqDto;
import kr.co.green.member.dto.res.SigninResDto;

public interface MemberService {
	
	public boolean checkId(String memberId);
	
	public void signup(SignupReqDto memberReqDto);

	SigninResDto signin(SigninReqDto signinReqDto);

}
