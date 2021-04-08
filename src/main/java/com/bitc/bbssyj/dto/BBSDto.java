package com.bitc.bbssyj.dto;

import java.util.List;

import lombok.Data;

@Data
public class BBSDto {
	private int boardIdx;
	private String title;
	private String contents;
	private String writer;
	private String cDate;
	private int views;
	private int likes;
	private List<BBSFileDto> fileList;
}
