<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="/WEB-INF/views/common/common.jsp"%>

<!-- <script type="text/javascript" src ="${ctx}/resources/js/login.js"></script> -->

<title>로그인하기</title>
<style type="text/css">
.background {
background-image: url('${ctx}/resources/images/namoosocial.jpg');
background-position: 100% 100%;
}
</style>
</head>

<body class="background">
	<div class="container">
			<form class="form-signin" action="${ctx}/login" method="post" >
				<input type="text" class="form-control" placeholder="User Id" name="id" autofocus>
				<input type="password"	class="form-control" placeholder="Password" name="password">
				<h5 id="fail"></h5>
				<label class="checkbox"> </label>
				<div class="form-group">
					<div class="col-lg-10 col-lg-offset-2">
						<button type="submit" class="btn btn-primary" id="show">로그인</button>
						<button type="button" class="btn btn-primary"
							onclick="location.href='${ctx}/join'; return false;">회원가입</button>
					</div>
				</div>
			</form>
		</div>
</body>
</html>
