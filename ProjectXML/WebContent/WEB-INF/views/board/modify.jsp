<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<c:set var='root' value='${pageContext.request.contextPath }/'/>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>미니 프로젝트</title>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>

<c:import url="/WEB-INF/views/include/top_menu.jsp" />


<div class="container" style="margin-top:100px">
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6">
			<div class="card shadow">
				<div class="card-body">
					<form:form action='${root }board/modify_pro' method='post' modelAttribute="modifyContentBean" enctype="multipart/form-data">
					<!-- modelAttribute="modifyContentBean" 컨트롤러에 커맨드 객체를  modifyContentBean라는 이름으로 전달-->
						<form:hidden path="content_idx"/><!-- 글 수정시 어떤 글 수정하는지 알아야 하므로 히든으로  글 번호-->
						<form:hidden path="content_board_idx"/><!-- 글 수정 후 게시판으로 돌아와야 하므로 글의 게시판 번호도 알아야 함 -->
						<input type='hidden' name='page' value='${page }'/>
						<div class="form-group">
							<form:label path="content_writer_name">작성자</form:label>
							<form:input path="content_writer_name" class="form-control" readonly="true"/>
							<!-- 작성자는 수정할 수 없으므로 에러 셋팅 x -->
						</div>
						<div class="form-group">
							<form:label path="content_date">작성날짜</form:label>
							<form:input path="content_date" class="form-control" readonly="true"/>
							<!-- 작성날짜는 수정할 수 없으므로 에러 셋팅 x -->
						</div>
						<div class="form-group">
							<form:label path="content_subject">제목</form:label>
							<form:input path="content_subject" class="form-control"/>
							<form:errors path="content_subject" style="color:red"/>
						</div>
						<div class="form-group">
							<form:label path="content_text">내용</form:label>
							<form:textarea path="content_text" class="form-control" rows="10" style="resize:none"/>
							<form:errors path="content_text" style="color:red"/>
						</div>
						<div class="form-group">
							<form:label path="content_file">첨부이미지</form:label>
							<c:if test="${modifyContentBean.content_file != null }">
							<img src="${root }/upload/${modifyContentBean.content_file }" width="100%">
							<form:hidden path="content_file"/><!-- 기존에 첨부했던 파일의 이름 --><!-- 수정 눌렀을 때 첨부파일 고대로 뜨도록 혹은 수정 시 기존에 있던 파일 변경 하지 않아도 그대로 이미지 뜰 수 있도록  -->
							</c:if>
							<form:input path="upload_file" type="file" class="form-control" accept="image/*"/>
						</div>
						<div class="form-group">
							<div class="text-right">
								<form:button class="btn btn-primary">수정완료</form:button>
								<a href="${root }board/read?board_info_idx=${board_info_idx}&content_idx=${content_idx}&page=${page}" class="btn btn-info">취소</a>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
		<div class="col-sm-3"></div>
	</div>
</div>

</body>
</html>
