package kr.co.green.board.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationInfoDto {
	
	private int currentPage;
    private int totalPages;
    private int startPage;
    private int endPage;

}
