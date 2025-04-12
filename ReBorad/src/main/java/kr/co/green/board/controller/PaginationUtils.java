package kr.co.green.board.controller;

import org.springframework.data.domain.Page;

public class PaginationUtils {
	
	 public static PaginationInfo getPaginationInfo(Page<?> page) {
         int currentPage = page.getNumber(); // 0-based
         int totalPages = page.getTotalPages();

         int blockLimit = 10;
         int startPage = (currentPage / blockLimit) * blockLimit;
         int endPage = Math.min(startPage + blockLimit - 1, totalPages - 1);

         return new PaginationInfo(currentPage, totalPages, startPage, endPage);
     }
    
}
