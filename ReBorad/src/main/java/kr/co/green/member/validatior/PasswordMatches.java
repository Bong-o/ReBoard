package kr.co.green.member.validatior;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
public @interface PasswordMatches {
	String message() default "비밀번호와 비밀번호 확인이 일치하지 않습니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};

}







