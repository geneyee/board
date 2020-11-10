package com.boardproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import com.boardproject.beans.UserBean;



// 프로젝트 작업시 사용할 bean을 정의하는 클래스
@Configuration
public class RootAppContext {//데이터를 저장하거나 관리할때
	
	@Bean("loginUserBean")//UserBean이 쓰이는 곳이 많기 때문에 로그인시 쓰일때만 구분짓기 위해 따로 이름설정해준다.
	@SessionScope
	public UserBean loginUserBean() {
		return new UserBean();
	}
}
