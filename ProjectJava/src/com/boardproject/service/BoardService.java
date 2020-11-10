package com.boardproject.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.boardproject.beans.ContentBean;
import com.boardproject.beans.PageBean;
import com.boardproject.beans.UserBean;
import com.boardproject.dao.BoardDao;

@Service
@PropertySource("/WEB-INF/properties/option.properties")
public class BoardService {
	
	@Value("${path.upload}")//경로주입
	private String path_upload;
	
	@Value("${page.listcnt}")//옵션프로퍼티에 설정한 페이지 당 글 개수 주입(10)
	private int page_listcnt;
	
	@Value("${page.paginationcnt}")//페이지 버튼의 개수 주입(10)
	private int page_paginationcnt;
	
	@Autowired
	private BoardDao boardDao;
	
	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;
	
	//파일 저장 메소드
	private String saveUploadFile(MultipartFile upload_file) {
		
		String file_name = System.currentTimeMillis() + "_" + upload_file.getOriginalFilename();
		//업로드 된 파일의 이름을 구할  수 있다
		//중복을 피하기 위해 파일이름 앞에 현재시간 붙임
		
		//파일저장
		try {
			upload_file.transferTo(new File(path_upload + "/" + file_name));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return file_name;
	}
	
	//글 저장
	public void addContentInfo(ContentBean writeContentBean) {
		
		MultipartFile upload_file = writeContentBean.getUpload_file();
		
		if(upload_file.getSize() > 0) {
			String file_name = saveUploadFile(upload_file);
			//System.out.println(file_name);
			
			writeContentBean.setContent_file(file_name);
		}
		
		writeContentBean.setContent_writer_idx(loginUserBean.getUser_idx());
		
		//직접 입력한 데이터만 넘어오기 때문에 파일이름, 작성자idx 설정 해줌
		//게시판 idx는 hidden으로 이미 설정해놔서 빈에 자동으로 주입되어 있는 상태
		
		boardDao.addContentInfo(writeContentBean);
		
		/*
		 * System.out.println(writeContentBean.getContent_subject());
		 * System.out.println(writeContentBean.getContent_text());
		 * //System.out.println(writeContentBean.getUpload_file()); 파일이 첨부안되도 multipart
		 * 객체 생성되서 넘어온다 System.out.println(writeContentBean.getUpload_file().getSize());
		 * //업로드 된 데이터의 용량을 구할 수 있어서 용량을 보고 파일첨부 되었는지 확인가능
		 */		
		
	}
	
	//게시판 이름 보기
	public String getBoardInfoName(int board_info_idx) {
		return boardDao.getBoardInfoName(board_info_idx);
	}
	
	//글 목록 보기
	public List<ContentBean> getContentList(int board_info_idx, int page){
		
		/*쿼리문에서 rowbounds를 이용해 페이징 작업을 하려고 하는데
		rowbounds는 인덱스 번호가 0번부터 시작이다.
		0, 10 이라고 가정하면 첫 페이지에서 10개
		10, 10은 11번째부터 10개 .. 
		
		즉, 1페이지는 0(인덱스 0부터 10개)
		 	2페이지는 10(전 페이지에서 0부터 9까지 10개 출력했으므로 인덱스 10개부터 19까지 10개)
		 	3페이지는 20(2페이지 인덱스 19까지 출력했으므로 20부터 10개)... */
		
		int start = (page - 1) * page_listcnt; //rowbounds에 넣어줄 첫 시작 인덱스번호 구하기 위해
		//현재 page번호에서 1을 빼고 page_listcnt를 곱한다(x10)
		//1페이지-1=0, 2페이지-1=1, 3페이지-1=2... 여기에 각각 x10 하면
		//(1페이지-1)x10 -> 인덱스 0부터 /  (2페이지-1)x10 -> 인덱스 10부터 / (3페이지-1)x10 -> 인덱스 20부터
		
		RowBounds rowBounds = new RowBounds(start, page_listcnt);//(현재페이지-1)x10, 페이지 당 글 개수
		/* 현재 페이지 2페이지라 가정하면 
		 * page = 2 
		 * start = (2-1)*10 
		 * 		 = 10
		 * 인덱스 10번(start)부터 10개(page_listcnt) 출력한다는 뜻*/
		
		return boardDao.getContentList(board_info_idx, rowBounds);
	}

	
	//작성한 글 읽기
	public ContentBean getContentInfo(int content_idx) {
		return boardDao.getContentInfo(content_idx);
	}
	
	//글 수정
	public void modifyContentBean(ContentBean modifyContentBean) {
		
		MultipartFile upload_file = modifyContentBean.getUpload_file();
		
		if(upload_file.getSize() > 0) {
			String file_name = saveUploadFile(upload_file);
			modifyContentBean.setContent_file(file_name);//수정한 파일명으로 재설정
		}
		
		boardDao.modifyContentBean(modifyContentBean);
	}
	
	//글 삭제
	public void deleteContentInfo(int content_idx) {
		boardDao.deleteContentInfo(content_idx);
	}
	
	//전체 글 개수
	public PageBean getContentCnt(int content_board_idx, int currentPage) {
		
		//전체 글 개수
		int content_cnt = boardDao.getContentCnt(content_board_idx);
		
		PageBean pageBean = new PageBean(content_cnt, currentPage, page_listcnt, page_paginationcnt);
		//생성자로 값 넘기고
		
		return pageBean;//PageBean 반환
	}


}
