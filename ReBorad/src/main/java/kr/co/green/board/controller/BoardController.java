package kr.co.green.board.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.green.board.data.dto.BoardReqDto;
import kr.co.green.board.data.dto.BoardResDto;
import kr.co.green.board.data.dto.CommentResDto;
import kr.co.green.board.data.dto.PaginationInfoDto;
import kr.co.green.board.service.BoardService;
import kr.co.green.board.util.PaginationUtils;
import kr.co.green.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/board/free")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	private final JwtUtil jwtUtil;
	
	@GetMapping("/list")
	public String list(@RequestParam(name = "page", defaultValue = "1") int page,
            	       @RequestParam(name = "size", defaultValue = "10") int size,
            	       Model model) {
		
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("no").descending());
		Page<BoardResDto> boardPage = boardService.getBoards(pageable);
		
		PaginationInfoDto paginationInfo = PaginationUtils.getPaginationInfo(boardPage);
	    
		model.addAttribute("boards", boardPage.getContent());
		model.addAttribute("page", boardPage);
		model.addAttribute("pagination", paginationInfo);
		
		return "/board/free/list";
	}
	
	@GetMapping("enrollForm")
	public String enrollForm() {
		return "/board/free/enroll";
	}
	
	@PostMapping("/enroll")
	public String enroll(@CookieValue(name = "access_token", required = false) String accessToken,
						 BoardReqDto boardReqDto) {
		
		if (accessToken == null || accessToken.isEmpty() 
								|| boardReqDto.getTitle() == null
								|| boardReqDto.getContent() == null) {

			return "redirect:/member/signin";
	    }
		
		boardReqDto.setAuthorName(jwtUtil.getUserIdFromToken(accessToken));
		
		boardService.enroll(boardReqDto);
		
		return "redirect:/board/free/list";
	}
	
	
	@GetMapping("detail")
	public String detail(@RequestParam(value="no") Long no, Model model) {
		
		BoardResDto boards = boardService.detail(no);
		List<CommentResDto> comments = boardService.comment(no);
		
		model.addAttribute("board", boards);
		model.addAttribute("comments", comments);
		
		return "/board/free/detail";
	}
	
	@GetMapping("/updateForm")
	public String updateForm(@RequestParam(value="no") Long no, Model model) {
		
		BoardResDto board = boardService.updateForm(no);
		
		model.addAttribute("board", board);
		
		return "/board/free/update";
	}
	
	@PostMapping("/update")
	public String update(BoardReqDto boardReqDto) {
		
		boardService.update(boardReqDto);
		
		return "redirect:/board/free/detail?no=" + boardReqDto.getNo();
	}		
	
			
	@PostMapping("/delete")
	public String delete(@RequestParam(value="no") Long no) {
		
		boardService.delete(no);
		
		return "redirect:/board/free/list";
	}
	
}
