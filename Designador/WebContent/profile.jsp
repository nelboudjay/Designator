<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="css/profile.css" />

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
				<img class="black-icon" src="images/profile-black-icon.png">
				${session.user.userFullName}
			</h3>
			<span>Perfil</span>
		</div>
		
		<jsp:include page="errorMessages.jsp"/>
		
		<div class="container">
			
			<div class="user-menu">
				<img  src="<s:url value="getImage?profileImage=true" />" >
				<span>${session.user.userFullName}</span>
				<ul>
					<li><a>Partidos</a></li>
					<li><a>Disponibilidad</a></li>
					<li><a>Conflictos</a></li>
				</ul>
			</div>
			<div class="user-paginate">
				<s:set var="currentIdUser" value="idUser"/>			
				<s:iterator value="users" status="status" >
					<s:if test="#currentIdUser == idUser">
				
						<s:if test="#status.count > 1">
							<a class="btn"><s:property value="users[#status.index-1].userFullName"/></a>						
						</s:if>
						
						<s:if test="#status.count < users.size">
							<a class="btn"><s:property value="users[#status.index+1].userFullName"/></a>						
						</s:if>
					</s:if>
				</s:iterator>
				
			</div>
			<br/><br/>
			
		</div>
	</div>

</body>
</html>