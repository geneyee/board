package com.boardproject.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.boardproject.beans.UserBean;


public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return UserBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		UserBean userBean = (UserBean)target;
		
		String beanName = errors.getObjectName();//유효성 검사를 하는 빈의 이름을 출력해 볼 수 있다.
		
		if(beanName.equals("joinUserBean") || beanName.equals("modifyUserBean")) {
			if(userBean.getUser_pw().equals(userBean.getUser_pw2())==false) {
				errors.rejectValue("user_pw", "NotEquals");
				errors.rejectValue("user_pw2", "NotEquals");
			}
		}
		
		if(beanName.equals("joinUserBean")) {	
			if(userBean.isUserIdExist() == false) {//boolean은 set() get()에 is가 붙는다.그래서 isUserIdExist()
				errors.rejectValue("user_id", "DontCheckUserIdExist");
			}
		}
	}
	
}
