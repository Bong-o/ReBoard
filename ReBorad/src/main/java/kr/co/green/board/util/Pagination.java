package kr.co.green.board.util;

import org.springframework.stereotype.Component;
import kr.co.green.board.model.dto.PageInfoDTO;

@Component
public class Pagination {
	
	public PageInfoDTO getPageInfo(int listCount, int currentPage, int pageLimit, int boardLimit) {

        int maxPage = (int) Math.ceil((double) listCount / boardLimit);
        int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
        int endPage = startPage + pageLimit - 1;
        if (endPage > maxPage) endPage = maxPage;

        int offset = (currentPage - 1) * boardLimit;

        return new PageInfoDTO(
                listCount,
                currentPage,
                pageLimit,
                boardLimit,
                maxPage,
                startPage,
                endPage,
                offset
        );
    }
	
}
