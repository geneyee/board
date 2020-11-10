package com.boardproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.boardproject.beans.BoardInfoBean;
import com.boardproject.beans.ContentBean;
import com.boardproject.service.MainService;
import com.boardproject.service.TopMenuService;


@Controller
public class MainController {
	
	@Autowired
	private MainService mainService;
	
	@Autowired
	private TopMenuService topMenuService;
	
	@GetMapping("/main")
	public String main(Model model) {
		
		ArrayList<List<ContentBean>> list = new ArrayList<List<ContentBean>>();//밑에서 만든 4개의 리스트 담은 리스트
		
		for(int i=1; i<=4; i++) {
			List<ContentBean> list1 = mainService.getMainList(i);//게시판 1개에 대한 글 목록을 담을 리스트
			list.add(list1);
		}
		
		model.addAttribute("list", list);
		
		List<BoardInfoBean> board_list = topMenuService.getTopMenuList();
		model.addAttribute("board_list", board_list);
		
		return "main";
	}

}
