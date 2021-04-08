package com.bitc.bbssyj.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.bbssyj.dto.BBSFileDto;

//직접 클래스를 생성하여 등록
@Component
public class FileUtil {
	public List<BBSFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest uploadFiles) throws Exception {
		//업로드된 파일의 존재 확인
		if (ObjectUtils.isEmpty(uploadFiles)) {
			return null;
		}
		
		//파일 정보 리스트
		List<BBSFileDto> fileList = new ArrayList<>();
		
		/*
		서버에 파일을 저장할 디렉토리 생성하기
		*/
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd"); // 날짜 형식 지정
		ZonedDateTime current = ZonedDateTime.now(); // 현재 날짜시간 가져오기
		
		//이미지 저장 폴더명
		String path = "images/" + current.format(format);
		
		//File 클래스로 실제 폴더 생성
		File file = new File(path);
		//동일한 폴더 존재 확인
		if (file.exists() == false) {
			file.mkdirs(); // 폴더 생성
		}
		
		/*
		실제 이미지 저장
		*/
		
		Iterator<String> iterator = uploadFiles.getFileNames();
		
		String newFileName, oriFileExtension, contentType;
		
		while (iterator.hasNext()) {
			List<MultipartFile> list = uploadFiles.getFiles(iterator.next());
			
			for (MultipartFile multiFile : list) {
				if (multiFile.isEmpty() == false) {
					contentType = multiFile.getContentType();
					
//					파일 확장자 확인하기
					if (ObjectUtils.isEmpty(contentType)) {
						break;
					}
					else {
						if (contentType.contains("image/jpeg")) {
							oriFileExtension = ".jpg";
						}
						else if (contentType.contains("image/png")) {
							oriFileExtension = ".png";
						}
						else if (contentType.contains("image/gif")) {
							oriFileExtension = ".gif";
						}
						else {
							break;
						}
					}
					
//					업로드된 파일의 이름을 변경 / 서버에 여러 사람이 접속하여 동시에 파일을 업로드할 경우 파일명이 겹칠 수 있기 때문에 날짜, 시간을 사용하여 파일을 이름을 겹치지 않도록 함
					newFileName = Long.toString(System.nanoTime()) + oriFileExtension;
					BBSFileDto bbsFile = new BBSFileDto();
					bbsFile.setBoardIdx(boardIdx);
					bbsFile.setFileSize(multiFile.getSize());
					bbsFile.setOriginalFileName(multiFile.getOriginalFilename());
					bbsFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(bbsFile);
					
//					서버에 업로드된 파일을 실제로 저장
					file = new File(path + "/" + newFileName);
					multiFile.transferTo(file);
				}
			}
		}
		
		return fileList;
	}
}
