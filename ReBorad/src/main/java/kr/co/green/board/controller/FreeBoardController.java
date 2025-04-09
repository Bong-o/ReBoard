//package kr.co.green.board.controller;
//
//import java.util.List;
//
//import java.util.Map;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.SessionAttribute;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import kr.co.green.board.model.dto.BoardDTO;
//import kr.co.green.board.model.dto.FileDTO;
//import kr.co.green.board.model.dto.PageInfoDTO;
//import kr.co.green.board.model.dto.SearchDTO;
//import kr.co.green.board.model.service.BoardService;
//import kr.co.green.board.util.Pagination;
//
//
//
//@Controller
//@RequestMapping("/board/free")
//public class FreeBoardController {
//	private final BoardService boardService;
//	private final Pagination pagination;
//	
//	public FreeBoardController(BoardService boardService, Pagination pagination) {
//		this.boardService = boardService;
//		this.pagination = pagination;
//	}
//	
//	// 요청 URL : //board/free/list?currentPage=3
//	// 요청 URL : //board/free/list
//	@GetMapping("/list")
//	public String list(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
//			           @ModelAttribute SearchDTO searchDTO,
//					   Model model) {
//		
//		int postCount = boardService.getTotalCount(searchDTO);
//		int pageLimit = 10; // 버튼
//		int boardLimit = 5; // 보여질 게시글 수
//		
//		// 1. pageInfoDTO 대신 currentPage, postCount, pageLimit, boardLimit 다 인자로 작성
//		// 2. 배열로 만들어서 넣어주는 방법
//		Map<String, Object> result = boardService.getAllPosts(pagination,
//															  currentPage, 
//															  postCount,
//															  pageLimit,
//															  boardLimit,
//															  searchDTO);
//		PageInfoDTO piResult = (PageInfoDTO) result.get("pi");
//		List<BoardDTO> postsResult = (List<BoardDTO>) result.get("posts");
//		
//		model.addAttribute("posts", postsResult);
//		model.addAttribute("pi", piResult);
//		
//		return "/board/free/list";
//	}
//	
//	@GetMapping("enrollForm")
//	public String enrollForm(@ModelAttribute("boardDTO") BoardDTO boardDTO) {
//		return "/board/free/enroll";
//	}
//	
//	@PostMapping("/enroll")
//	public String enroll(BoardDTO boardDTO,
//						 @RequestParam("file") MultipartFile file,
//						 @SessionAttribute("memberNo") int memberNo) {
//		boardDTO.setFileDTO(new FileDTO());
////		boardDTO.setAuthorDTO(new AuthorDTO());
//		boardDTO.getAuthorDTO().setAuthorNo(memberNo);
//		int result = boardService.enroll(boardDTO, file);
//		
//		return "redirect:/board/free/list";
//	}
//	
//	@GetMapping("detail")
//	public String detail(@RequestParam(value="no") int no,
//					     Model model) {
//		// 선택한 게시글에 대한 정보를 불러와야 함
//		// 제목, 내용, 작성자, 작성일, 조회수
//		
//		BoardDTO result = boardService.detail(no);
//		
//		model.addAttribute("post", result);
//		
//		return "/board/free/detail";
//	}
//	
//	@GetMapping("/updateForm")
//	public String updateForm(@RequestParam(value="no") int no,
//							 Model model) {
//		
//		BoardDTO result = boardService.updateForm(no);
//		
//		model.addAttribute("post", result);	
//		
//		return "/board/free/update";
//	}
//	
//	@PostMapping("/update")
//	public String update(BoardDTO boardDTO,
//						 @SessionAttribute("memberNo") int memberNo,
//						 @RequestParam("file") MultipartFile file,
//						 RedirectAttributes redirectAttributes) {
//		// 글 작성자만 수정 가능 - 검증이니까 서비스 계층
//		// 1. 게시글 no로 조회해서 글 작성자 no 가져오기
//		// 2. 글 작성자 no와 로그인한 사용자의 no(세션에 있는 값)가 같을 경우 update 수행
//		int result = boardService.update(boardDTO, memberNo, file);
//		System.out.println(result);
//		
//		// 컨트롤러에서 컨트롤러로 데이터 넘기는 방법
//		// 1. redirectAtrributes
//		//	  - 리다이렉트로 데이터 전달할 때 주로 사용
//		//    - 일회성 데이터(한번 전달하고 사라짐, 데이터가 유지되지 않음)
//		// 2. model-forward 사용
//		// 3. Get요청일 경우 쿼리스트링 사용
//		// 4. 세션 사용
//		
//		// redirectAttribute.addAttribute("no", boardDTO.getNo());
//		// - 쿼리스트링으로 요청
//		// - 리다이렉트할 경로가 GET 요청을 받을 때 (int, String)
//		// - @RequestParam
//		
//		// redirectAttribute.addFlashAttribute("no", boardDTO.getNo());
//		// - 세션에 임시로 저장
//		// - 객체를 전달시켜줘야 할 때
//		// - model 객체
//		
//		redirectAttributes.addAttribute("no", boardDTO.getNo());
////		redirectAttributes.addFlashAttribute("no", boardDTO.getNo());
//		
//		return "redirect:/board/free/detail";
//	}				     
//			
//	@PostMapping("/delete")
//	public String delete(@RequestParam(value="no") int no,
//						 @RequestParam(value="fileName", defaultValue="none") String fileName,
//					     @SessionAttribute("memberNo") int memberNo) {
//		
//		int result = boardService.delete(no, memberNo, fileName);
//		
//		return "redirect:/board/free/list";
//	}
//	
//	
//}
////build.gradle에 추가 implementation 'org.springframework.boot:spring-boot-starter-security:3.4.1'
