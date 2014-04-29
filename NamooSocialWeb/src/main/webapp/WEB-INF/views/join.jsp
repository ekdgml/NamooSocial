<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>NamooSocial</title>
<meta name="generator" content="Bootply" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<%@ include file="/WEB-INF/views/common/common.jsp"%>
<style type="text/css">
@import url('http://fonts.googleapis.com/css?family=Open+Sans:300,400')
</style>
</head>
<!-- HTML code from Bootply.com editor -->
<body>
	<!-- Begin Navbar -->
	<div class="navbar navbar-static">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="${ctx}/main"><b>NamooSocial</b></a>
				<a class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse"> <span
					class="glyphicon glyphicon-chevron-down"></span>
				</a>
			</div>
		</div>
	</div>
	<!-- /.navbar -->

	<header class="masthead">
		<div class="container">
			<div class="row">
				<div class="col-md-6">
					<h1>
						<a href="#" title="Scroll down for your viewing pleasure">NamooSocial</a>
						<p class="lead">NamooSocial로 친구들과 소통해보세요.</p>
					</h1>
				</div>
			</div>
		</div>
	</header>

	<!-- Begin Body -->
	<div class="container">
		<div class="no-gutter row">
<!-- right content column-->
      		<div class="col-md-7" id="content">
            	<div class="panel">
    			<div class="panel-heading" style="background-color:#111;color:#fff;">회원가입</div>   
              	<div class="panel-body">
                  <p>회원가입을 위해 아래 내용들을 작성해 주세요.</p>
					<form class="form-horizontal" action="${ctx}/join" method="post" enctype="multipart/form-data">
						<fieldset>
							<div class="form-group">
								<label class="col-lg-2 control-label">사용자ID</label>

								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="사용자ID" name="userId">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">이름</label>

								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="이름" name="name">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">이메일</label>

								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="이메일" name="email">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">비밀번호</label>

								<div class="col-lg-10">
									<input type="password" class="form-control" placeholder="비밀번호" name="password">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">프로필사진</label>

								<div class="col-lg-10">
									<input type="file" class="image" name="imageFile" placeholder="프로필 사진"/>
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-10 col-lg-offset-2">
									<button type="submit" class="btn btn-primary">확인</button>
									<button class="btn btn-primary" onclick="history.back(); return false;">취소</button>
								</div>
							</div>
						</fieldset>
					</form>
                  </div><!--/panel-body-->
                </div><!--/panel-->
              	<!--/end right column-->
				</div>
			</div>
		</div>
	<footer>
			<div class="container">
				<div class="row">
					<div class="col-md-6">©NamooSocial 2014</div>
				</div>
			</div>
		</footer>
</body>
</html>