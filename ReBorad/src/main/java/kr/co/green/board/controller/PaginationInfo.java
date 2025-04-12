package kr.co.green.board.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationInfo {
	
	private int currentPage;
    private int totalPages;
    private int startPage;
    private int endPage;

}
