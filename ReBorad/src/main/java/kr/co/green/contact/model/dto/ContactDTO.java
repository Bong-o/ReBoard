package kr.co.green.contact.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactDTO {
	private int no;
	private String name;
	private String email;
	private String content;
	private String createDate;
}
