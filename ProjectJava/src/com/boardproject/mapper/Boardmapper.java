package com.boardproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.boardproject.beans.ContentBean;


public interface Boardmapper {
	
	
	@SelectKey(statement = "select content_seq.nextval from dual", keyProperty = "content_idx", before = true, resultType = int.class)//오라클에서 dual은 가상의 테이블(임시테이블). mysql에서는 생략이 가능
	//오라클에서 nextval은 원래 10이라고 되어있었다면 +1된 값을 반환시킨다.
	//쿼리문 "select content_seq.nextval from daul"을 실행시켜서 반환된 값(결과값 즉, 새로운 시퀀스(+1된))을
	//아래에 글 저장하는 insert 쿼리문을 반환하는  writeContentBean에 저장된 content_idx 변수에 넣어준다
	//writeContentBean은 boardcontroller에서 주입받은 객체. 이 객체는 service dao..로 계속 전달된다.
	//즉 이 값은 boardcontroller에서도 사용이 가능하다
	//before = true는 다른 쿼리문보다 먼저 실행하겠다는 뜻
	//resultType = int.class은 변수타입
	
	//저장
	@Insert("insert into content_table(content_idx, content_subject, content_text, " +
			"content_file, content_writer_idx, content_board_idx, content_date) " +
			"values (#{content_idx}, #{content_subject}, #{content_text}, #{content_file, jdbcType=VARCHAR}, " +
			"#{content_writer_idx}, #{content_board_idx}, sysdate)")
	//content_idx는 시퀀스 사용(content_seq.nextval) - 원래 이렇게 작성했었는데 삭제하고 위에 selectkey에 추가
	//#{}는 주입받는 것들 제목, 내용, 파일이름, 작성자번호, 게시판 번호, 날짜
	//sysdate는 현재날짜 (오라클)
	//content_file(컬럼)은 null을 허용하도록 만들었다 근데 파일을 선택하지 않고 업로드 하면 부적합한 열 유형: 1111이라는 mybatis 오류가 난다
	//이를 방지하기 위해 jdbcType=VARCHAR 추가로 작성 (어떤 타입의 컬럼인지를 명시)
	
	void addContentInfo(ContentBean writeContentBean);
	
	//게시판 이름 보기
	@Select("select board_info_name " + 
			"from board_info_table " + 
			"where board_info_idx = #{board_info_idx}")
	
	String getBoardInfoName(int board_info_idx);
	
	//글 목록 보기
	@Select("select a1.content_idx, a1.content_subject, a2.user_name as content_writer_name, " + 
			"        to_char(a1.content_date, 'YYYY-MM-DD') as content_date " + 
			"from content_table a1, user_table a2 " + 
			"where a1.content_writer_idx = a2.user_idx " + 
			"    and a1.content_board_idx = #{board_info_idx} " + 
			"order by a1.content_idx desc")
	
	List<ContentBean> getContentList(int board_info_idx, RowBounds rowBounds);
	
	//글 읽기
	@Select("select a2.user_name as content_writer_name, " + 
			"        to_char(a1.content_date, 'YYYY-MM-DD') as content_date, " + 
			"        a1.content_subject, a1.content_text, a1.content_file, a1.content_writer_idx " + 
			"from content_table a1, user_table a2 " + 
			"where a1.content_writer_idx = a2.user_idx " + 
			"        and content_idx = #{content_idx}")
	
	ContentBean getContentInfo(int content_idx);//글 하나의 정보를 반환하는 것이라서 ContentBean을 반환한다
	
	//글 수정
	@Update("update content_table " + 
			"set content_subject = #{content_subject}, content_text=#{content_text}, " +
			"content_file = #{content_file, jdbcType=VARCHAR}" + 
			"where content_idx = #{content_idx}")//null 허용이므로 jdbcType=VARCHAR 꼭 추가
	
	void modifyContentInfo(ContentBean modifyContentBean);
	
	//글 삭제
	@Delete("delete from content_table where content_idx = #{content_idx}")
	void deleteContentInfo(int content_idx);
	
	//전체 글 개수
	@Select("select count(*) from content_table where content_board_idx = #{content_board_idx}")
	int getContentCnt(int content_board_idx);
}
