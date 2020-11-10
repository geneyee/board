package com.boardproject.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.HandlerInterceptor;

import com.boardproject.beans.ContentBean;
import com.boardproject.beans.UserBean;
import com.boardproject.service.BoardService;

public class CheckWriterInterceptor implements HandlerInterceptor{
	
	@Resource(name = "loginUserBean")
	@Lazy
	private UserBean loginUserBean;//로그인 여부 알아야 하므로 필요
	
	@Autowired
	private BoardService boardService;//getContentInfo 메소드 필요하므로

	//검사하기
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		
		//수정과 삭제만 가능하도록 반응하는 interceptor
		//read.jsp에서 수정과 삭제버튼에 content_idx 파라미터 설정해 둠 그 idx를 불러온다
		//여기서는 주입 받을 수가 없으므로 servletcontext에서 처럼 설정해준다.
		String str1 = request.getParameter("content_idx");
		int content_idx = Integer.parseInt(str1);//content_idx 정수형으로 바꿈
		
		//현재 글 정보 가져오기
		ContentBean currentContentBean = boardService.getContentInfo(content_idx);
		
		if(currentContentBean.getContent_writer_idx() != loginUserBean.getUser_idx()) {//글 작성자idx와 로그인 한 유저의 idx가 다르다면
			
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/board/not_writer");
			return false;
		}
		
		
		return true;
	}

}
