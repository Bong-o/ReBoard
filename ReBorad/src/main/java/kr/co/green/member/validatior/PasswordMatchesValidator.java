package kr.co.green.member.validatior;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.co.green.member.dto.req.SignupReqDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, SignupReqDto> {
	
	@Override
	public void initialize(PasswordMatches constraintAnnotation) {}
	
	@Override
	public boolean isValid(SignupReqDto memberDTO, ConstraintValidatorContext context) {
		if(memberDTO.getPassword() == null || memberDTO.getConfirmPassword() == null) {
			return false;
		}
		
		boolean isValid = memberDTO.getPassword().equals(memberDTO.getConfirmPassword());
		System.out.println(isValid);
		
		return isValid;
	}
}
