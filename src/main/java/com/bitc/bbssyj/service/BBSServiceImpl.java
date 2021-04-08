package com.bitc.bbssyj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.bbssyj.common.FileUtil;
import com.bitc.bbssyj.dto.BBSDto;
import com.bitc.bbssyj.dto.BBSFileDto;
import com.bitc.bbssyj.mapper.BBSMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BBSServiceImpl implements BBSService {
	@Autowired
	private BBSMapper bbsMapper;
	
	@Autowired
	private FileUtil fileUtil;
	
	@Override
	public List<BBSDto> selectBBSList() throws Exception {
		return bbsMapper.selectBBSList();
	}

	@Override
	public BBSDto selectBBSDetail(int boardIdx) throws Exception {
		//조회수
		bbsMapper.updateViews(boardIdx);
		
		/*지정한 게시물의 상세 정보 가져오기
		게시물의 상세 정보를 가져오는 시점에는 첨부된 파일에 대한 정보가 없음
		fileList 멤버 변수의 값은 null 임*/
		BBSDto bbs = bbsMapper.selectBBSDetail(boardIdx);
		
		//지정한 게시물에 첨부된 파일 리스트 가져오기
		List<BBSFileDto> fileList = bbsMapper.selectBBSFileList(boardIdx);
		//가져온 첨부 파일 리스트를 기존의 게시물 정보에 추가하기
		bbs.setFileList(fileList);
		
		return bbs;
	}

	@Override
	public void updateBBS(BBSDto bbs) throws Exception {
		bbsMapper.updateBBS(bbs);
	}

	@Override
	public void deleteBBS(int boardIdx) throws Exception {
		bbsMapper.deleteBBS(boardIdx);
	}

	@Override
	public void insertBBS(BBSDto bbs, MultipartHttpServletRequest uploadFiles) throws Exception {
		log.debug("----- BBSServer의 insertBBS 실행 이전 -----");
		log.debug("출력값 : " + Integer.toString(bbs.getBoardIdx()));
		
//		기존의 게시물 등록
		bbsMapper.insertBBS(bbs);
		
		log.debug("----- service insertBBS 이후 -----");
		log.debug("출력값 : " + Integer.toString(bbs.getBoardIdx()));
		
//		FileUtil 클래스를 통해서 생성한 파일 정보 받아오기(서버에 파일 저장 기능 포함)
		List<BBSFileDto> fileList = fileUtil.parseFileInfo(bbs.getBoardIdx(), uploadFiles);
		
//		데이터 베이스에 업로드된 파일 정보 저장
//		CollectionUtils 클래스는 스프링 프레임워크에서 지원하는 유틸 중 하나임, 여기서는 fileList.isEmpty() 를 사용해도 상관없음
		if (CollectionUtils.isEmpty(fileList) == false) {
			bbsMapper.insertBBSFileList(fileList);
		}
		
	}

	@Override
	public BBSFileDto selectBBSFileInformation(int idx, int boardIdx) throws Exception {
		return bbsMapper.selectBBSFileInformation(idx, boardIdx);
	}

}
