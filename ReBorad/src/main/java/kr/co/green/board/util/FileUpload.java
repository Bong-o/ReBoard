package kr.co.green.board.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import kr.co.green.board.model.dto.FileDTO;

@Component
public class FileUpload {
	
	public void uploadFile(MultipartFile file, FileDTO fileDTO, String folderName) throws IOException {
		
		// 원본 파일 이름
		String originalFileName = file.getOriginalFilename(); 
	
		// 새로운 파일 이름
		String changeName = UUID.randomUUID().toString() + "." + getFileExtension(originalFileName);
		
		// 파일이 서버에 저장될 위치(경로)	
		Path path = Paths.get(fileDTO.getLOCAL_PATH() + "\\" + folderName + "\\" + changeName);
		
		// 파일 저장
		Files.write(path, file.getBytes());
		
		fileDTO.setOriginalName(originalFileName);
		fileDTO.setChangeName(changeName);
		fileDTO.setExtension(getFileExtension(originalFileName));
		fileDTO.setSize(file.getSize());
		fileDTO.setFolderNamePath(folderName);
		
	}
	
	private String getFileExtension(String fileName) {
		// fileName : 제못없음.png
		// dotIndex : 4
		
		
		int dotIndex = fileName.lastIndexOf('.');
		return dotIndex == 1 ? "" : fileName.substring(dotIndex+1);
	}

	public void deleteFile(String localPath, String folderName, String fileName) throws IOException {
		Path path = Paths.get(localPath + "\\" + folderName + "\\" + fileName);
		Files.delete(path);
	}
	
	
}
