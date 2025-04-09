package kr.co.green.board.util;

import org.springframework.stereotype.Component;

import kr.co.green.board.model.dto.PageInfoDTO;

@Component
public class Pagination {
	
	public PageInfoDTO getPageInfo(int listCount, int currentPage,
									   int pageLimit, int boardLimit) {
		// <전체 페이지 수>
		// - listCount: 301, boardLimit: 5
		// - 나눈 값: 60.2 Math.ceil에 의해서 소수점 올림 처리를 하면 61
		// - 최종적으로 전체 페이지 수는 61 페이지다.
		int maxPage = (int)(Math.ceil((double)listCount/boardLimit));

		// <현재 페이지가 속한 범위의 시작 페이지>
		// - currentPage: 16, pageLimit: 10
		// 1. (currentPage-1) = 15
		// 2. (currentPage-1) / PageLimit = 1
		//            15      /     10    = 1 (int로 계산하고 있기 때문에 소수점 생략)
		// 3. (currentPage-1) / pageLimit * pageLimit = 10
		//                          1     *     10    = 10
		// 4. (currentPage-1) / pageLimit * pageLimit + 1 = 11
		//                                       10   + 1 = 11
		int startPage = (currentPage-1) / pageLimit * pageLimit + 1;
		
		
		// <현재 페이지가 속한 범위의 끝 페이지>
		// - startPage : 11, pageLimit: 10
		// - 11+10-1 = 20;
		int endPage = startPage+pageLimit-1;
		
		// 300
		int row = listCount-(currentPage-1)*boardLimit;
		
		// 사용자가 1페이지를 보고 있다
		// offset: 1, limit: 10
		// offset: 11, limit: 20
		// offset: 21, limit: 30
		int offset = (currentPage-1)*boardLimit+1;
		int limit = offset+boardLimit-1; 
		
		if(endPage>maxPage) {
			endPage = maxPage;
		}
		
		return new PageInfoDTO(listCount, currentPage, pageLimit, boardLimit,
								   maxPage, startPage, endPage, row, offset, limit);
		
		
		
	}
}
