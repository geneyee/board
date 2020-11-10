package com.boardproject.beans;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserBean {
	
	private int user_idx;
	//유효성검사
	@Size(min=2, max=4)
	@Pattern(regexp = "[가-힣]*")//한글만 입력할 수 있는 정규식
	private String user_name;
	
	@Size(min=2, max=20)
	@Pattern(regexp = "[a-zA-Z0-9]*")//영대소문자랑 숫자만
	private String user_id;
	
	@Size(min=2, max=20)
	@Pattern(regexp = "[a-zA-Z0-9]*")
	private String user_pw;
	
	@Size(min=2, max=20)
	@Pattern(regexp = "[a-zA-Z0-9]*")
	private String user_pw2;
	
	//아이디 중복검사 값을 담을 변수
	private boolean userIdExist;
	
	//로그인 여부값을 담고 있을 변수
	private boolean userLogin;
	
	public UserBean() { 
		this.userIdExist = false;//처음에는 false값으로 기본설정. 왜냐하면 처음에 이 bean이 만들어졌을때는 중복검사 안한 상태이므로
		this.userLogin = false;//초기값 false로 설정. 만약 로그인에 성공하게 되면 해당 변수에 true값 셋팅.
	}
	
	public int getUser_idx() {
		return user_idx;
	}
	public void setUser_idx(int user_idx) {
		this.user_idx = user_idx;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getUser_pw2() {
		return user_pw2;
	}
	public void setUser_pw2(String user_pw2) {
		this.user_pw2 = user_pw2;
	}

	public boolean isUserIdExist() {
		return userIdExist;
	}

	public void setUserIdExist(boolean userIdExist) {
		this.userIdExist = userIdExist;
	}

	public boolean isUserLogin() {
		return userLogin;
	}

	public void setUserLogin(boolean userLogin) {
		this.userLogin = userLogin;
	}

	
}
