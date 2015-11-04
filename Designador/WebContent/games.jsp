<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="css/users.css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/commonScript.js"></script>
<script type="text/javascript" src="js/users.js"></script>

<title>Partidos</title>

<s:head />
</head>
<body>
	<jsp:include page="header.jsp"/>

	<jsp:include page="leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="images/game-black-icon.png">
				Partidos
			</h3>
			<span>Todos los partidos</span>
		</div>
		
		<jsp:include page="errorMessages.jsp"/>
		
		<div class="container">
			<br>	
			<h4 class="new-record"><a href="addGame"><img src="images/add-icon.png" class="small-icon">Añadir un partido</a></h4>
			<br>
			
			<div class="panel-info">
				<div class="panel-body">
					No hay partidos programados todavía. ¿Quieres <a class="link" href="addGame">Añadir</a> partidos?
				</div>
			</div>
			
			<table class="members">
				<tr>
					<th>Nombre</th>
					<th>Mostrar</th>
					<th>Teléfono fijo</th>
					<th>Teléfono móvil</th>
					<th>Contactar</th>	
				</tr>
				
				<s:set var="users" value="users"/>
				<s:if test="#users == null">
					<s:set var="users" value="#attr.users"/>
				</s:if>
				<s:iterator value="users" >
					<tr>
						<td><a class="link" href="user?idUser=${idUser}"><s:property value="userFullName"/></a></td>
						<td>
						<div class="btn-group"><a class="btn btn-link" href="games?idUser=${idUser}">Partidos</a>
							<button class="btn">
								<img class="small-icon" src="images/setting-icon.png">
								<img class="very-small-icon" src="images/arrowhead-icon.png">
							</button>
							<ul	style="display:none;">
								<li><a href="user?idUser=${idUser}">Info</a></li>
								<li><a href="availability?idUser=${idUser}">Disponibilidad</a></li>
								<li><a href="user?idUser=${idUser}">Conflictos</a></li>
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
		<jsp:include page="footer.jsp"/>
		
	</div>
</body>
</html>