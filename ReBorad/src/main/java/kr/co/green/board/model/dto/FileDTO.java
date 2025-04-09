package kr.co.green.board.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileDTO {
	private final String LOCAL_PATH = "C:\\Dev\\spring_work_sapce\\SpringProject-2\\src\\main\\resources\\static\\uploads\\board";
	private final String RESOURCES_PATH = "/uploads/board";
	
	private int no;
	private String changeName;
	private String originalName;
	private String uploadDate;
	private String folderNamePath;
	private String extension;
	private long size;
}
