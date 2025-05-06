package kr.co.green.member.data.dto;
import kr.co.green.member.data.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SigninResDto {
	
	private String id;
	private String name;
	
	public SigninResDto(MemberEntity member) {
		this.name = member.getUsername();
	}

}
