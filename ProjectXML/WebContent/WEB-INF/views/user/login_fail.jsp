<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var='root' value='${pageContext.request.contextPath }/' />
<script>
	alert('로그인에 실패하였습니다')
	location.href="${root}user/login?fail=true"
	/* 파라미터값 fail=true줘서 controller에서 이 값을 담아서 login.jsp에 보내고 
		c:if test="${fail == true }"이므로 로그인실패 아이디비밀번호 확인해달라는 문구 띄운다.
	*/
</script>