<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/users.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/users.js"></script>

<title>Competiciones</title>

<s:head />
</head>
<body>
	<jsp:include page="../header.jsp"/>

	<jsp:include page="../leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="${pageContext.request.contextPath}/images/league-black-icon.png">
				Competiciones
			</h3>
			<span>Todas las competiciones</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<div class="container">
			<br>	
			<h4 class="new-record"><a href="addLeague"><img src="${pageContext.request.contextPath}/images/add-icon.png" class="small-icon">Añadir una competición</a></h4>
			<br>
			
			<div class="panel-info">
				<div class="panel-body">
					No hay ninguna competición disponible en el sistema. ¿Quieres <a class="link" href="addLeague">Añadir</a> una competición?
				</div>
			</div>
			
		</div>
		<jsp:include page="../footer.jsp"/>
		
	</div>
</body>
</html>