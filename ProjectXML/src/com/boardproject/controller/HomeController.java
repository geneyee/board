package com.boardproject.controller;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomeController {
	
//	@Resource(name="loginUserBean")
//	@Lazy//서버가 가동될 때 주입하지 않고 나중에 주입하겠다는 의미 //여기서는 브라우저가 가동될 때 주입함. sessionScope는 브라우저가 가동될 때 최초로 주입하기 때문에
//	private UserBean loginUserBean;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
//		System.out.println(loginUserBean);
		return "redirect:/main";
	}
}
