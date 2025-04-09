package kr.co.green.contact.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.green.contact.model.dto.ContactDTO;
import kr.co.green.contact.model.mapper.ContactMapper;

@Service
public class ContactServiceImpl implements ContactService {
	private final ContactMapper contactMapper;
	
	public ContactServiceImpl(ContactMapper contactmapper) {
		this.contactMapper = contactmapper;
	}
	
	@Override
	public int enroll(ContactDTO contactDTO) {
		return contactMapper.enroll(contactDTO);
	}
	
//	@Override
//	public List<ContactDTO> getAllPosts() {
//		return contactMapper.getAllPosts();
//	}
//	
//	@Override
//	public ContactDTO detail(int no) {
//		return contactMapper.detail(no);
//	}
	
	@Override
	public List<ContactDTO> findAll() {
		return contactMapper.findAll();
	}
	
	
}