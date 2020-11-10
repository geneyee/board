package com.boardproject.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.boardproject.beans.UserBean;
import com.boardproject.dao.UserDao;

@Service
public class UserService {//넘겨받은 값이 null인지 확인해서 사용할 수 있는지를 판단
	
	@Autowired
	private UserDao userDao;
	
	@Resource(name="loginUserBean")
	@Lazy
	private UserBean loginUserBean;
	
	public boolean checkUserIdExist(String user_id) {
		
		String user_name = userDao.checkUserIdExist(user_id);
		//usermapper에서 select로 사용자 아이디가 있는지 확인 있다면 그 데이터가 반환되고 없으면 null값을 반환한다. 
		//아이디 중복검사에 쓸 것이기 때문에 null이면 해당 아이디 사용가능
		
		if(user_name == null) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public void addUserInfo(UserBean joinUserBean) {
		userDao.addUserInfo(joinUserBean);
	}
	
	//로그인성공
	public void getLoginUserInfo(UserBean tempLoginUserBean) {
		
		//먼저 userdao에 있는 정보를 가져온다.
		UserBean tempLoginUserBean2 = userDao.getLoginUserInfo(tempLoginUserBean);
		
		if(tempLoginUserBean2 != null) {//select에서 가져온 정보가 있다면 
			loginUserBean.setUser_idx(tempLoginUserBean2.getUser_idx());
			loginUserBean.setUser_name(tempLoginUserBean2.getUser_name());
			loginUserBean.setUserLogin(true);//session영역에 저장되어져 있는 이 bean에서 userlogin값이 true라면 login되어있음 
		}
	}
	
	//정보수정
	public void getModifyUserInfo(UserBean modifyUserBean) {
		
		UserBean tempModifyUserBean = userDao.getModifyUserInfo(loginUserBean.getUser_idx());
		
		modifyUserBean.setUser_id(tempModifyUserBean.getUser_id());
		modifyUserBean.setUser_name(tempModifyUserBean.getUser_name());
		modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
		
	}
	
	//정보수정 - 비밀번호 수정
	public void modifyUserInfo(UserBean modifyUserBean) {
		
		modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
		
		userDao.modifyUserInfo(modifyUserBean);
		
	}


}
