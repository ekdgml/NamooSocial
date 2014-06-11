<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html lang="ko">
<head>
<title>나무커뮤니티</title>
<%@ include file="/WEB-INF/views/common/common.jsp"%>
<style type="text/css">
body {
	padding-top: 100px;
	padding-bottom: 40px;
	background-color: #ecf0f1;
}

.info-header {
	max-width: 500px;
	padding: 15px 29px 25px;
	margin: 0 auto;
	background-color: #18bc9c;
	color: white;
	text-align: left;
	-webkit-border-radius: 15px 15px 0px 0px;
	-moz-border-radius: 15px 15px 0px 0px;
	border-radius: 15px 15px 0px 0px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.info-footer {
	max-width: 500px;
	margin: 0 auto 20px;
	padding-left: 10px;
}

.info-body {
	max-width: 500px;
	padding: 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	-webkit-border-radius: 0px 0px 15px 15px;
	-moz-border-radius: 0px 0px 15px 15px;
	border-radius: 0px 0px 15px 15px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.info-heading {
	margin-bottom: 15px;
}

.info-btn {
	text-align: center;
	padding-top: 20px;
}
</style>
</head>
<body>
	<div class="container">

		<!-- header -->
		<div class="info-header">
			<h2 class="info-heading">안내</h2>
		</div>

		<!-- body -->
		<div class="info-body">
			<form action="${ctx}/withdrawal" method="post">
				<h3>탈퇴 안내</h3>
				<p>정말로 탈퇴 하시겠습니까??</p>
				<input type="submit" value="확인" />
				<button onclick="history.back(); return false;">취소</button>
			</form>
			<div class="row info-btn">
				<button class="btn btn-large btn-default" onclick="location.href='${ctx}/main'">홈으로 이동</button>
			</div>
		</div>

		<footer>
			<div class="container">
				<div class="row">
					<div class="col-md-6">©NamooSocial 2014</div>
				</div>
			</div>
		</footer>
	</div>
</body>
</html>