<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%-- <%@taglib  prefix="spring" uri="http://www.springframework.org/tags" %> --%> 
<c:set var='root' value="${pageContext.request.contextPath }/" />

<!-- contextpath를 포함한 절대경로 즉 /MiniProjectJava -->
<%-- <c:url var='root' value='/' /> 근데 이렇게 설정할 경우 url에 jsessionid가 생길 수 있다. 그래서 위의 방법 사용함.--%>

<!-- 상단 메뉴 부분 -->
<nav class="navbar navbar-expand-md bg-dark navbar-dark fixed-top shadow-lg">
	<a class="navbar-brand" href="${root }main">BOARD</a><!-- href="main"는 상대경로 주소 맨끝에 붙는 main만 찾아감. BoardController.java에서 @RequestMapping("/board")를 해주었으므로.. -->
	<!-- <img src="<spring:url value='/resources/image/profile.jpg'/>"> -->
	<button class="navbar-toggler" type="button" data-toggle="collapse"
	        data-target="#navMenu">
		<span class="navbar-toggler-icon"></span>        
	</button>
	<div class="collapse navbar-collapse" id="navMenu">
		<ul class="navbar-nav">
			<c:forEach var='obj' items='${topMenuList }'><!-- items='${topMenuList } topMenuList의 수만큼 반복하면서 li태그 생성  -->
			<li class="nav-item">
				<a href="${root }board/main?board_info_idx=${obj.board_info_idx}" class="nav-link">${obj.board_info_name }</a>
			</li>
			</c:forEach>
		</ul>
		
		<ul class="navbar-nav ml-auto">
			<c:choose>
				<c:when test="${loginUserBean.userLogin == true }"><!-- 로그인 했을 때 -->
					<li class="nav-item">
						<a href="${root }user/modify" class="nav-link" title="정보수정"><img src="${root }resources/image/profile.png" class="rounded-circle" width="35"></a>
					</li>
					<li class="nav-item">
						<a href="${root }user/modify" class="nav-link" style="margin-top:3px">정보수정</a>
					</li>
					<li class="nav-item">
						<a href="${root }user/logout" class="nav-link" style="margin-top:3px">로그아웃</a>
					</li>
				</c:when>
				<c:otherwise><!-- 로그인 하지 않았을 때 -->
					<li class="nav-item">
						<a href="${root }user/login" class="nav-link">로그인</a>
					</li>
					<li class="nav-item">
						<a href="${root }user/join" class="nav-link">회원가입</a>
					</li>
				</c:otherwise>
			</c:choose>


		</ul>
	</div>
</nav>
