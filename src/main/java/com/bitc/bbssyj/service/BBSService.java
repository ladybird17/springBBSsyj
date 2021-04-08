package com.bitc.bbssyj.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.bbssyj.dto.BBSDto;
import com.bitc.bbssyj.dto.BBSFileDto;


public interface BBSService {
	List<BBSDto> selectBBSList() throws Exception;
	BBSDto selectBBSDetail(int boardIdx) throws Exception;
	void updateBBS(BBSDto board) throws Exception;
	void deleteBBS(int boardIdx) throws Exception;
	void insertBBS(BBSDto bbs, MultipartHttpServletRequest uploadFiles) throws Exception;
	BBSFileDto selectBBSFileInformation(int idx, int boardIdx) throws Exception;
}
