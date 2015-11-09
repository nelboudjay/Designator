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

<title>Miembros</title>

<s:head />
</head>
<body>
	<jsp:include page="../header.jsp"/>

	<jsp:include page="../leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="${pageContext.request.contextPath}/images/users-black-icon.png">
				Miembros
			</h3>
			<span>Todos los miembros</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<div class="container">
			<br>	
			<s:if test="#session.user.isAdmin()">
				<h4 class="new-record"><a href="addUser"><img src="${pageContext.request.contextPath}/images/add-icon.png" class="small-icon">Añadir un nuevo miembro</a></h4>
			</s:if>
			<br>
			<table class="members">
				<tr>
					<th>Nombre</th>
					<th>Mostrar</th>
					<th>Teléfono fijo</th>
					<th>Teléfono móvil</th>
					<th>Contactar</th>	
				</tr>
				
				<s:iterator value="#attr.users" >
					<tr>
						<td><a class="link" href="user?idUser=${idUser}"><s:property value="userFullName"/></a></td>
						<td>
						<div class="btn-group"><a class="btn btn-link" href="${pageContext.request.contextPath}/game/games?idUser=${idUser}">Partidos</a>
							<button class="btn">
								<img class="small-icon" src="${pageContext.request.contextPath}/images/settings-dropdown-icon.png">
								<img class="very-small-icon" src="${pageContext.request.contextPath}/images/arrowhead-icon.png">
							</button>
							<ul	style="display:none;">
								<li><a href="${pageContext.request.contextPath}/user/user?idUser=${idUser}">Info</a></li>
								<li><a href="${pageContext.request.contextPath}/availability/availability?idUser=${idUser}">Disponibilidad</a></li>
								<li><a href="${pageContext.request.contextPath}/user/user?idUser=${idUser}">Conflictos</a></li>
							</ul>
						</div>
							
						</td>
						<td><s:property value="userProfile.homePhone"/></td>
						<td><s:property value="userProfile.mobilePhone"/></td>
						<td>
							<s:if test="password == ''">
								<span class="unconfirmed">Sin confirmar</span>
							</s:if>
							<s:else>
								<a class="link">Por Correo</a>
							</s:else>	
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
		<jsp:include page="../footer.jsp"/>
		
	</div>
</body>
</html>