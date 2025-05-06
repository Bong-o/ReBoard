package kr.co.green.contact.data.dto;

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
