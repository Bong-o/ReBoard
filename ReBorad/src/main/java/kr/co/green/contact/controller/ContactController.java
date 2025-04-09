package kr.co.green.contact.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.green.contact.model.dto.ContactDTO;
import kr.co.green.contact.model.service.ContactService;

@Controller
@RequestMapping("/contact")
public class ContactController {
	private final ContactService contactService;
	
	public ContactController(ContactService contactService) {
		this.contactService = contactService; 
	}
	
	@GetMapping("/list")
	public String list(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
					   Model model) {
		
//		int postCount = 
		int pageLimit = 5;
		int boardLimit = 7;
		
		
		
		return "/contact/list";
	}
	
	@GetMapping("/enrollForm")
	public String enrollForm() {
		return "/contact/enroll";
	}
	
	@PostMapping("/enroll")
	public String enroll(ContactDTO contactDTO) {
		contactService.enroll(contactDTO);
		
		return "redirect:/";
	}
		
	
	
	
	
//	@GetMapping("/list")
//	public String list(Model model, SearchDTO searchDTO,
//					   @RequestParam(value="currentPage", defaultValue="1") int currentPage) {
//		
//		int postPage ;
//		int pageLimit = 5;
//		int boardLimit = 7;
//		
//		List<ContactDTO> posts = contactService.getAllPosts();
//			
//		model.addAttribute("posts", posts);
//		
//		return "/contact/list";
//	}
	
//	@GetMapping("/detail")
//	public String detail(@RequestParam(value="no") int no,
//						 Model model) {
//		
//		ContactDTO result = contactService.detail(no);
//		
//		model.addAttribute("post", result);
//		
//		return "/contact/detail";
//	}
	
//	@GetMapping("/answerForm")
//	public String answerForm(@RequestParam(value="no") int no,
//			 				 Model model) { 
//		
//		ContactDTO result = contactService.detail(no);
//		
//		model.addAttribute("post", result);
//		
//		return "/contact/answer";
//		
//	}
	
}
