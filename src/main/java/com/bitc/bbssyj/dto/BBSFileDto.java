package com.bitc.bbssyj.dto;

import lombok.Data;

@Data
public class BBSFileDto {
	private int idx;
	private int boardIdx;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;
	private String writer;
	private String cDate;
}
