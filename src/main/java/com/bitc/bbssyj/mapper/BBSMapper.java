package com.bitc.bbssyj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bitc.bbssyj.dto.BBSDto;
import com.bitc.bbssyj.dto.BBSFileDto;

@Mapper
public interface BBSMapper {
	//전체 목록 불러오기
	List<BBSDto> selectBBSList() throws Exception;

	//지정한 글의 전체내용 불러오기
	BBSDto selectBBSDetail(int boardIdx) throws Exception;

	//지정한 글의 조회수 올리기
	void updateViews(int boardIdx) throws Exception;

	//글 작성, 등록하기
	void insertBBS(BBSDto bbs) throws Exception;
	
	//글에 포함된 파일 정보 작성하기
	void insertBBSFileList(List<BBSFileDto> fileList)  throws Exception;

	//글에 포함된 파일 정보 조회하기
	List<BBSFileDto> selectBBSFileList(int boardIdx) throws Exception;
	
	//글 수정하기
	void updateBBS(BBSDto bbs);
	
	//글 삭제하기
	void deleteBBS(int boardIdx);
	
	//파일 정보 가져오기
	BBSFileDto selectBBSFileInformation(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
	
}
