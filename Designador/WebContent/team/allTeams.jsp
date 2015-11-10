<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/maintenance.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/users.js"></script>

<title>Equipos</title>

<s:head />
</head>
<body>
	<jsp:include page="../header.jsp"/>

	<jsp:include page="../leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="${pageContext.request.contextPath}/images/users-black-icon.png">
				Equipos
			</h3>
			<span>Todos los Equipos</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<div class="container">
			<br>	
			<h4 class="new-record"><a href="addEditTeam">
				<img src="${pageContext.request.contextPath}/images/add-icon.png" class="small-icon">
					Añadir un Equipo</a>
			</h4>
			<br>
			
			<s:if test="#attr.teams.size() == 0">
				<div class="panel-info">
					<div class="panel-body">
						No hay ningun equipo disponible en el sistema. ¿Quieres <a class="link" href="addEditTeam">Añadir</a> un equipo?
					</div>
				</div>
			</s:if>
			<s:else>
				<table id="teams">
					<tr><th>Nombre del Equipo</th>
						<th>Localidad</th>
						<th></th>
					</tr>	
					<s:iterator value="#attr.teams">
						<tr>
							<td><a class="link" href="addEditTeam?idTeam=${idTeam}">${teamName}</a></td>
							<td>${teamLocation}</td>
							<td><a class="link delete" href="deleteTeam?idTeam=${idTeam}&method:deleteTeam">Eliminar</a>
								<span class="confirm-box">
									<span class="message">¿Estás seguro que quieres
										eliminar este equipo? </span> <span class="btn yes">Sí</span> 
										<span class="btn no">No</span>
								</span>	
							</td>
						</tr>	
					</s:iterator>
				</table>
			</s:else>
			
		</div>
		<jsp:include page="../footer.jsp"/>
		
	</div>
</body>
</html>