package kr.co.green.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoDTO {

	private int listCount;     // 전체 게시글 수
	private int currentPage;   // 현재 페이지
	private int pageLimit;     // 하단에 보여질 페이지 버튼 수
	private int boardLimit;    // 한 페이지에 보여질 게시글 수
	private int maxPage;       // 전체 페이지 수
	private int startPage;     // 현재 페이지 기준 시작 페이지 버튼
	private int endPage;       // 현재 페이지 기준 끝 페이지 버튼
	private int offset;        // 게시글 조회 시작 위치

}

