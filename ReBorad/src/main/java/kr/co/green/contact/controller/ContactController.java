package kr.co.green.contact.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.green.contact.data.entity.ChatRoomEntity;
import kr.co.green.contact.service.ChatService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {
	
	private final ChatService chatService;

	@GetMapping("/chatForm")
	public String chat(Model model) {
		
		List<ChatRoomEntity> rooms = chatService.getAllRooms();
		
		for (ChatRoomEntity room : rooms) {
			System.out.println(room.getRoomId());
			System.out.println(room.getRoomName());
			System.out.println(room.getRoomPassword());
			System.out.println(room.getCreatedAt());
	    }
		
		model.addAttribute("rooms", rooms);
		
		return "/contact/chat";
	}
		
}
