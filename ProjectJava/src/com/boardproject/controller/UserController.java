package com.boardproject.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boardproject.beans.UserBean;
import com.boardproject.service.UserService;
import com.boardproject.validator.UserValidator;



@Controller
@RequestMapping("/user")
public class UserController {
	
	//싱글톤이기 때문에 주입받을때마다 객체 생성되는것이 아니다.
	@Autowired
	private UserService userService;
	
	//session영역에 있는 userbean 주입받음
	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;
	
	@GetMapping("/login")
	public String login(@ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean,
						@RequestParam(value = "fail", defaultValue = "false") boolean fail,//fail(로그인 화면의 로그인 실패 메시지)이라는 파라미터의 기본값은 false(처음에는 파라미터 값이 없기 때문에)이고 
						Model model) {//이것을 model에 담는다.
		
		model.addAttribute("fail",fail);
		
		return "user/login";
	}
	
	@PostMapping("/login_pro")
	public String login_pro(@Valid @ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean, BindingResult result) {
		
		if(result.hasErrors()) {//유효성검사가 잘못됐다면
			return "user/login";
		}
		
		userService.getLoginUserInfo(tempLoginUserBean);//아이디 정보 전달
		
		if(loginUserBean.isUserLogin() == true) {
			return "user/login_success";
		} else {
			return "user/login_fail";
		}
	}
	
	@GetMapping("/join")
	public String join(@ModelAttribute("joinUserBean") UserBean joinUserBean) {
		return "user/join";
	}
	
	//회원가입
	@PostMapping("/join_pro")//@Valid 파라미터로 넘어오는 값 유효성 검사 해야함. BindingResult 검사결과 담을 객체
	public String join_pro(@Valid @ModelAttribute("joinUserBean") UserBean joinUserBean, BindingResult result) {
		//아이디 중복검사
		if(result.hasErrors()) {//유효성검사에서 문제가 있다면
			return "user/join";
		}
		//회원가입 정보저장
		userService.addUserInfo(joinUserBean);//주입받은 bean을 셋팅함.
		
		return "user/join_success";//유효성검사가 정상이라면 user/join_success.jsp로 페이지 이동
	}
	
	@GetMapping("/modify")
	public String modify(@ModelAttribute("modifyUserBean") UserBean modifyUserBean) {//여기서 주입받은 객체주소값(UserBean modifyUserBean)을
		
		userService.getModifyUserInfo(modifyUserBean);//여기서 이 메소드를 호출할 때 넘기도록 한다.
		
		return "user/modify";
	}
	
	
	@PostMapping("/modify_pro") 
	public String modify_pro(@Valid @ModelAttribute("modifyUserBean") UserBean modifyUserBean, BindingResult result) {
		
		if(result.hasErrors()) {
			return "user/modify";
		}
		
		userService.modifyUserInfo(modifyUserBean);
		
		return "user/modify_success";
	}
	  
	
	@GetMapping("/logout")
	public String logout() {
		
		loginUserBean.setUserLogin(false);
		
		return "user/logout";
	}
	
	@GetMapping("/not_login")
	public String not_login() {
		return "user/not_login";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		UserValidator validator1 = new UserValidator();
		binder.addValidators(validator1);
	}


}
