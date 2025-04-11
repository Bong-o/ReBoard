package kr.co.green.member.model.service;

import java.time.LocalDateTime;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.green.member.entity.MemberEntity;
import kr.co.green.member.exception.MemberException;
import kr.co.green.member.model.dto.SigninReqDto;
import kr.co.green.member.model.dto.SigninResDto;
import kr.co.green.member.model.dto.SignupReqDto;
import kr.co.green.member.model.mapper.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	//private static final Logger logger = LogManager.getLogger(MemberServiceImpl.class);
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public boolean checkId(String memberId) {
		return memberRepository.existsById(memberId);
	}
	
	@Override
	public void signup(SignupReqDto memberReqDto) {
				
		String encodedPassword = passwordEncoder.encode(memberReqDto.getPassword());
		
		MemberEntity memberEntity = MemberEntity.builder()
				.id(memberReqDto.getId())
                .username(memberReqDto.getName())
                .password(encodedPassword)
                .email(memberReqDto.getEmail()) 
                .createdAt(LocalDateTime.now())
                .build();
		
		 memberRepository.save(memberEntity);
	}
	
	@Override
	public SigninResDto signin(SigninReqDto signinReqDto) {
		
		Optional<MemberEntity> optional = memberRepository.findById(signinReqDto.getId());

		if(optional.isEmpty()) {
			throw new MemberException("존재하지 않는 아이디입니다.", "/member/signin", HttpStatus.BAD_REQUEST);
		}

		MemberEntity member = optional.get();
		
		if(!passwordEncoder.matches(signinReqDto.getPassword(), member.getPassword())) {
			throw new MemberException("비밀번호가 일치하지 않습니다.", "/member/signin", HttpStatus.BAD_REQUEST);
		}
		
		return new SigninResDto(member.getId());
	}	
	
}
