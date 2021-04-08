package com.bitc.bbssyj.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.bbssyj.dto.BBSDto;
import com.bitc.bbssyj.dto.BBSFileDto;
import com.bitc.bbssyj.service.BBSService;

import lombok.extern.slf4j.Slf4j;

//logback 사용
@Slf4j
@Controller
public class BBSController {
	@Autowired
	private BBSService bbsService;
	
	//전체 목록 페이지
	@RequestMapping(value="/bbs", method=RequestMethod.GET)
	public ModelAndView bbsList() throws Exception{
		ModelAndView mv = new ModelAndView("/bbs/bbsList");
		
		log.debug("openBBSList");
		
		List<BBSDto> list = bbsService.selectBBSList();
		mv.addObject("data",list);
		
		return mv;
	}
	
	//지정한 게시판 글 확인
	@RequestMapping(value="/bbs/{boardIdx}", method=RequestMethod.GET)
	public ModelAndView bbsDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
		ModelAndView mv = new ModelAndView("/bbs/bbsDetail");
		
		BBSDto bbs = bbsService.selectBBSDetail(boardIdx);
		mv.addObject("bbs", bbs);
		
		return mv;
	}
	
	//새게시글 작성 페이지 호출
	@RequestMapping(value="/bbs/bbsWrite", method=RequestMethod.GET)
	public String bbsWrite() throws Exception {
		return "/bbs/bbsWrite";
	}
	
	//새게시글 작성
	@RequestMapping(value="/bbs/bbsWrite",method=RequestMethod.POST)
	public String insertBBS(BBSDto bbs, MultipartHttpServletRequest uploadFiles) throws Exception {
		bbsService.insertBBS(bbs, uploadFiles);
		
		return "redirect:/bbs";
	}
	
	//게시글 수정
	@RequestMapping(value="/bbs/{boardIdx}",method=RequestMethod.PUT)
	public String updateBBS(BBSDto bbs) throws Exception {
		bbsService.updateBBS(bbs);
		
		return "redirect:/bbs";
	}
	
	//게시글 삭제
	@RequestMapping(value="/bbs/{boardIdx}",method=RequestMethod.DELETE)
	public String deleteBBS(int boardIdx) throws Exception {
		bbsService.deleteBBS(boardIdx);
		
		return "redirect:/bbs";
	}
	
	//파일 다운로드
	@RequestMapping(value="/bbs/file", method=RequestMethod.GET)
	public void downloadBBSFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception {
		BBSFileDto bbsFile = bbsService.selectBBSFileInformation(idx, boardIdx); //db에서 파일 정보 검색
		
		//db에서 가져온 정보가 비어있는지 확인
		if (ObjectUtils.isEmpty(bbsFile) == false) {
			//원본 파일명
			String fileName = bbsFile.getOriginalFileName();
			
			//실제 저장된 파일 위치에서 파일에 대한 정보를 읽어온 후 byte 타입으로 변환
			//apache의 commons.io 패키지의 FileUtils를 이용하여 byte 타입으로 변경
			byte[] files = FileUtils.readFileToByteArray(new File(bbsFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\""+URLEncoder.encode(fileName,"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
}
