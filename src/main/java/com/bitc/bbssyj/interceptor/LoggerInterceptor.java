package com.bitc.bbssyj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {
	/*
	인터셉터는 HandlerInterceptorAdapter 클래스를 상속받아 구현
	HandlerInterceptorAdapter 클래스는 
	preHandle(컨트롤러 수행 전 실행),
	postHandle(컨트롤러 수행 후 뷰로 보내기 전 수행),
	afterCompletion(뷰의 작업까지 완료된 후 수행) 메서드를 제공함. 
	**springboot 2.4, spring 5.3부터 HandlerInterceptorAdapter 클래스가 제거됨
	HandlerInterceptor 인터페이스를 사용해서 구현해야함
	*/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		log.debug("==================== 시작 ====================");
		log.debug("Request URI \t: "+ request.getRequestURI());
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception{
		log.debug("==================== 종료 ==================== \n");
		
	}
}