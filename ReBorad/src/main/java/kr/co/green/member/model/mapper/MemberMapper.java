package kr.co.green.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.co.green.member.model.dto.SigninReqDto;
import kr.co.green.member.model.dto.SignupReqDto;

@Mapper
public interface MemberMapper {
	int signup(SignupReqDto memberDTO);
	SignupReqDto signin(SigninReqDto memberReqDto);
	int checkId(String memberId);
}
