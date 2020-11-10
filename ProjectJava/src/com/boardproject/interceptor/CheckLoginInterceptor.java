package com.boardproject.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.boardproject.beans.UserBean;


public class CheckLoginInterceptor implements HandlerInterceptor{
	
	//로그인 여부값을 가지고 따져야 함
	private UserBean loginUserBean;
	
	//자바설정에서는 직접주입 받지 못하므로 생성자로 받는다.
	public CheckLoginInterceptor(UserBean loginUserBean) {
		// TODO Auto-generated constructor stub
		this.loginUserBean = loginUserBean;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		
		if(loginUserBean.isUserLogin() == false) {//로그인 하지 않았다면
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/user/not_login"); 
			//웹 브라우저에 해당사항을 요청하도록 함 - not_login에서 jsp로 응답결과 만들고 jsp에서는 로그인요청 화면 보여주고 로그인 페이지로 이동
			
			return false;//Intercepter의 preHandle에서 return false하면 다음단계로 이동하지 않고 여기서 끝내도록 한다.
		}
		
		return true;//로그인 했다면 다음단계로 이동(컨트롤러)
	}

}
