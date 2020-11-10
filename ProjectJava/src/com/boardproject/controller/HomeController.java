package com.boardproject.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomeController {
	
	/*
	 * @Resource(name="loginUserBean")//이름통해 주입받을 때는 @Resource private UserBean
	 * loginUserBean;
	 */
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(HttpServletRequest request) {
//		System.out.println(loginUserBean);
		
		//System.out.println(request.getServletContext().getRealPath("/"));
		//프로젝트가 실행되는 물리적 위치(경로) 확인
		//파일 업로드 시 절대경로 확인을 위해 물리적위치 확인한다
		//실제 서비스 할 때는 서버상의 경로를 적어주면 된다
		//이클립스에서 작업할 때에는 프로젝트 폴더와 실제로 실행되는 위치가 다르기 때문에 확인해준 것
		
		return "redirect:/main";
	}
	
	
}
