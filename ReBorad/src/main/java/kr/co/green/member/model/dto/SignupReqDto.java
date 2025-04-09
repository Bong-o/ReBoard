
package kr.co.green.member.model.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupReqDto {
	
	private String id;
	private String name;
	private String email;
	
	@Size(min = 6, max = 48, message = "6자 이상 48자 이하입니다.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$")
	private String password;
	private String confirmPassword;

}
