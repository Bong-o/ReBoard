package kr.co.green.board.util;

import org.springframework.data.domain.Page;

import kr.co.green.board.data.dto.PaginationInfoDto;

public class PaginationUtils {
	
	 public static PaginationInfoDto getPaginationInfo(Page<?> page) {
         int currentPage = page.getNumber(); // 0-based
         int totalPages = page.getTotalPages();

         int blockLimit = 10;
         int startPage = (currentPage / blockLimit) * blockLimit;
         int endPage = Math.min(startPage + blockLimit - 1, totalPages - 1);

         return new PaginationInfoDto(currentPage, totalPages, startPage, endPage);
     }
    
}
