package kr.co.green.board.model.dto;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class SearchDTO {
	private String category = "title";
	private String searchText = "";
	
}
