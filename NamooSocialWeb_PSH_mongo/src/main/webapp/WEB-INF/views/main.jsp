<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>NamooSocial</title>
<meta name="generator" content="Bootply" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<!-- <script src="${ctx}/resources/js/main.js"></script> -->
<%@ include file="/WEB-INF/views/common/common.jsp"%>
<style type="text/css">
@import url('http://fonts.googleapis.com/css?family=Open+Sans:300,400');
</style>
</head>
<!-- HTML code from Bootply.com editor -->
<body>
	<!-- Begin Navbar -->
	<div class="navbar navbar-static">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="${ctx}/main"><b>NamooSocial</b></a> <a
					class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse"> <span
					class="glyphicon glyphicon-chevron-down"></span>
				</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="${ctx}/main"><i
							class="glyphicon glyphicon-home"></i></a></li>
					<li><a href="${ctx}/myMessages"><i
							class="glyphicon glyphicon-user"></i></a></li>
				</ul>
				<ul class="nav pull-right navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><i class="glyphicon glyphicon-search"></i></a>
						<ul class="dropdown-menu" style="padding: 12px;">
							<form class="form-inline">
								<button type="submit" class="btn btn-default pull-right">
									<i class="glyphicon glyphicon-search"></i>
								</button>
								<input type="text" class="form-control pull-left"
									placeholder="Search">
							</form>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i>
							<i class="glyphicon glyphicon-chevron-down"></i></a>
						<ul class="dropdown-menu">
							<li><a href="${ctx}/logout">로그아웃</a></li>
							<li><a href="${ctx}/myInfo">정보수정</a></li>
							<li><a href="${ctx}/withdrawal">회원탈퇴</a></li>
						</ul></li>
				</ul>
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
			<!-- left side column -->
			<div class="col-md-2">
				<div class="panel panel-default" id="sidebar">
					<div class="panel-heading"
						style="background-color: #888; color: #fff;">My</div>
					<div class="panel-body">
						<div class="well">
							<img class="img-responsive" src="${ctx}/main/image" />
							<h3>${user.name}</h3>
							<h5>${user.userId}</h5>
							<p>트윗수 : ${user.messages.size()}</p>
							<br />
							<p id="userRelation">
							
								<a href="${ctx}/followings">팔로잉수 :${user.followings.size()}</a><br />
								<a href="${ctx}/followers">팔로워수 :${user.followers.size()}</a>
							
							</p>
						</div>

						<hr>
						<h3 id ="recommendFollowing">팔로우 추천</h3>
						 
						<c:forEach var="notFollowing" items="${notFollowings}">
							<i class="glyphicon glyphicon-user"></i>
							<p>${notFollowing.name}&nbsp;&nbsp;&nbsp;${notFollowing.userId}</p>&nbsp;
							<input type="button" onclick="location.href='${ctx}/follow/${notFollowing.userId}'"
							value="팔로우" class="btn btn-default" /><br/>
						</c:forEach>
						 
						<hr>
					</div>
				</div>
				<!--/panel-->
			</div>
			<!--/end left column-->
			<!--mid column-->
			<div class="col-md-3">
				<div class="panel" id="midCol">
					<div class="panel-heading" style="background-color: #555; color: #eee;">New Stories</div>
			<div class="panel-body">
				<div class="well"> 
                     <form class="form-horizontal" role="form" action="${ctx}/posting" method="get">
                      <h4>공유하기</h4>
                       <div class="form-group" style="padding:14px;" >
                        <textarea class="form-control" placeholder="Update your status" rows="15" cols="30" name="contents"></textarea>
                      </div>
                       <button class="btn btn-default" type="submit">공유</button>
                    </form>
                </div>
					</div>
				</div>
				<!--/panel-->
			</div>
			<!--/end mid column-->

			<!-- right content column-->
			<div class="col-md-7" id="content">
				<div class="panel">
					<div class="panel-heading"
						style="background-color: #111; color: #fff;">Top Stories</div>
					<div class="panel-body" id="allMessages">
						
						<c:forEach var="message" items="${messages}">
							<div class="row">
								<div class="col-md-8">
									<img src="${ctx}/main/image" class="img-responsive">
									<h4>${message.writer.name}</h4>
									&nbsp;
									<h5>${message.writer.userId}&nbsp;${message.reg_dt}</h5>
									<p>${message.contents}</p>
									<br>
									<br>
									<button class="btn btn-default">More</button>
								</div>
							</div>
							<hr>
						</c:forEach>
						
					</div>
					<!--/panel-body-->
						
				</div>
				<!--/panel-->
				<!--/end right column-->

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