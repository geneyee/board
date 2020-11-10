package com.boardproject.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boardproject.beans.ContentBean;
import com.boardproject.beans.PageBean;
import com.boardproject.beans.UserBean;
import com.boardproject.service.BoardService;


@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;
	
	@GetMapping("/main")
	public String main(@RequestParam("board_info_idx") int board_info_idx,
						@RequestParam(value = "page", defaultValue = "1") int page, //page란 이름으로 파라미터 받고 없다면 1로 설정 즉 1페이지
						Model model ) {//model - requestparam으로 받은 idx를 글쓰기 버튼 링크에 달아줘야 하므로 model 주입
		
		model.addAttribute("board_info_idx", board_info_idx);
		
		String boardInfoName = boardService.getBoardInfoName(board_info_idx);
		model.addAttribute("boardInfoName", boardInfoName);
		
		List<ContentBean> contentList = boardService.getContentList(board_info_idx, page);//contentlist를 호출할 때 글 목록 알기 위해 board_info_id 넘기고, 페이징 처리를 위해 page를 넘긴다
		model.addAttribute("contentList", contentList);//main.jsp items="${contentList }"
		
		PageBean pageBean = boardService.getContentCnt(board_info_idx, page);
		model.addAttribute("pageBean", pageBean);
		
		//글 읽고 목록 버튼 눌렀을 때 해당 글이 있던 목록으로 가기 위해
		model.addAttribute("page", page);
		
		return "board/main";
	}
	
	@GetMapping("/read")
	public String read(@RequestParam("board_info_idx") int board_info_idx,
						@RequestParam("content_idx") int content_idx,
						@RequestParam("page") int page,//넘겨받은 page번호 추출
						Model model) {
		
		model.addAttribute("board_info_idx", board_info_idx);//글 목록보기 때문에 모델에 담는것
		model.addAttribute("content_idx", content_idx);//글 번호 담기(수정 삭제 시 필요)
		
		ContentBean readContentBean = boardService.getContentInfo(content_idx);
		model.addAttribute("readContentBean", readContentBean);
		
		model.addAttribute("loginUserBean", loginUserBean);
		
		model.addAttribute("page", page);
		
		
		return "board/read";
	}
	
	@GetMapping("/write")
	public String write(@ModelAttribute("writeContentBean") ContentBean writeContentBean,
						@RequestParam("board_info_idx") int board_int_idx) {//board_int_idx는 어떤 게시판인지 나타내는 idx
		
		writeContentBean.setContent_board_idx(board_int_idx);
		//form 태그들이 writeContentBean 안에 들어 있는 데이터를 가지고 세팅하기 때문에 board_int_idx도 writeContentBean에 담음 
		
		return "board/write";
	}
	
	@PostMapping("/write_pro")
	public String write_pro(@Valid @ModelAttribute("writeContentBean") ContentBean writeContentBean, BindingResult result) {
		//BindingResult은 유효성검사 결과 담는 객체
		
		if(result.hasErrors()) {//유효성 검사에 문제가 있다면
			return "board/write";
		}
		
		boardService.addContentInfo(writeContentBean);
		
		return "board/write_success";//문제없이 작성완료 되면
	}
	
	@GetMapping("/modify")
	public String modify(@RequestParam("board_info_idx") int board_info_idx,
						@RequestParam("content_idx") int content_idx,
						@ModelAttribute("modifyContentBean") ContentBean modifyContentBean,//ContentBean 객체의 속성명을 modifyContentBean로 정의
						@RequestParam("page") int page,
						Model model) {
		
		model.addAttribute("board_info_idx", board_info_idx);
		model.addAttribute("content_idx", content_idx);
		//modify.jsp 취소버튼에 필요함
		
		model.addAttribute("page", page);
		
		//수정버튼 눌렀을 시 현재 글 떠야하므로 서비스에서 글 읽는 메소드 가져온다.
		ContentBean tempContentBean = boardService.getContentInfo(content_idx);
		
		//jsp에서 필요하므로 modifyContentBean에 셋팅
		modifyContentBean.setContent_writer_name(tempContentBean.getContent_writer_name());
		modifyContentBean.setContent_date(tempContentBean.getContent_date());
		modifyContentBean.setContent_subject(tempContentBean.getContent_subject());
		modifyContentBean.setContent_text(tempContentBean.getContent_text());
		modifyContentBean.setContent_file(tempContentBean.getContent_file());
		modifyContentBean.setContent_writer_idx(tempContentBean.getContent_writer_idx());
		modifyContentBean.setContent_board_idx(board_info_idx);
		modifyContentBean.setContent_idx(content_idx);
		
		
		return "board/modify";
	}
	
	@PostMapping("/modify_pro")
	public String modify_pro(@Valid @ModelAttribute("modifyContentBean") ContentBean modifyContentBean,
							BindingResult result,
							@RequestParam("page") int page,
							Model model) {
		
		model.addAttribute("page", page);
		
		if(result.hasErrors()) {//만약 유효성 검사에 문제가 있다면
			return "board/modify";//수정페이지로 이동
		}
		
		boardService.modifyContentBean(modifyContentBean);
		
		return "board/modify_success";//문제 없으면 수정 성공페이지로 이동
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("board_info_idx") int board_info_idx, @RequestParam("content_idx") int content_idx,
						Model model) {
		
		boardService.deleteContentInfo(content_idx);
		
		//삭제 후 글 목록 페이지로 이동하도록 설정하기 위해 그때 필요한 글목록 idx 모델에 담기
		model.addAttribute("board_info_idx", board_info_idx);
		
		return "board/delete";
	}
	
	@GetMapping("/not_writer")
	public String not_writer() {
		return "board/not_writer";
	}
	

}
