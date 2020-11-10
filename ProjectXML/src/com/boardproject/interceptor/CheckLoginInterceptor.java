package com.boardproject.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.HandlerInterceptor;

import com.boardproject.beans.UserBean;


public class CheckLoginInterceptor implements HandlerInterceptor{
	
	@Resource(name = "loginUserBean")//xml설정에서는 빈 주입 가능함
	@Lazy 
	private UserBean loginUserBean;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		
		if(loginUserBean.isUserLogin() == false) {//로그인 하지 않았다면
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/user/not_login");
			return false;//preHandle에서 return false하면 다음단계로 이동하지 않고 여기서 끝내도록 한다.
		}
		
		return true;//로그인 했다면 다음단계로 이동(컨트롤러)
	}

}
