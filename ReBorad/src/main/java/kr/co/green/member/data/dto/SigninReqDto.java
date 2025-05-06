package kr.co.green.member.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SigninReqDto {
	
	private String id;
	private String password;

}