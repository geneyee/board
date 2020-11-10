package com.boardproject.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.boardproject.beans.ContentBean;
import com.boardproject.mapper.Boardmapper;

@Repository
public class BoardDao {
	
	@Autowired//매퍼주입
	private Boardmapper boardMapper;
	
	//글 저장
	public void addContentInfo(ContentBean writeContentBean) {
			boardMapper.addContentInfo(writeContentBean);
	}
	
	//게시판 이름 보기
	public String getBoardInfoName(int board_info_idx) {
		return boardMapper.getBoardInfoName(board_info_idx);
	}
	
	//글 목록 보기
	public List<ContentBean> getContentList(int board_info_idx, RowBounds rowBounds){
		return boardMapper.getContentList(board_info_idx, rowBounds);
	}
	
	//작성한 글 읽기
	public ContentBean getContentInfo(int content_idx) {
		return boardMapper.getContentInfo(content_idx);
	}
	
	//글 수정
	public void modifyContentBean(ContentBean modifyContentBean) {
		boardMapper.modifyContentInfo(modifyContentBean);
	}
	//글 삭제
	public void deleteContentInfo(int content_idx) {
		boardMapper.deleteContentInfo(content_idx);
	}
	
	//전체 글 개수
	public int getContentCnt(int content_board_idx) {
		return boardMapper.getContentCnt(content_board_idx);
	}


}
