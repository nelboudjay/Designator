<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	
<html>
<head>

<link rel="stylesheet" type="text/css" href="css/homePage.css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/commonScript.js"></script>

<title>Mi perfil</title>

<s:head />
</head>
<body>
	<jsp:include page="header.jsp"/>

	<jsp:include page="leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="home-black-icon" src="images/home-black-icon.png">
				${session.user.userFullName}
			</h3>
			<span>Perfil</span>
		</div>
		<div class="container">
			<h3 class="title-1">Nombre</h3>
		</div>
	</div>

</body>
</html>