package kr.co.green.contact.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.green.contact.model.dto.ContactDTO;

@Mapper
public interface ContactMapper {

	int enroll(ContactDTO contactDTO);
	
	List<ContactDTO> findAll();
	
//	List<ContactDTO> getAllPosts();
//
//	ContactDTO detail(int no);
//	
//	ContactDTO answerForm(int no);
}
