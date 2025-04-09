package kr.co.green.contact.model.service;

import java.util.List;

import java.util.Map;

import kr.co.green.contact.model.dto.ContactDTO;

public interface ContactService {
	int enroll(ContactDTO contactDTO);

//	public List<ContactDTO> getAllPosts();
//
//	public ContactDTO detail(int no);

	List<ContactDTO> findAll();

	
	
	
//	public ContactDTO answerForm(ContactDTO contactDTO, int no);
	
//	public Map<String, Object> getAllPosts2(ContactPagination contactPagination,
//											int currentPage,
//											int postCount,
//											int pageLimit,
//											int contactLimit);
											
	
 
}
