<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/maintenance.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>

<title>Añadir Equipos</title>

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
			<span>Nuevo Equipo</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<div class="container">
			<br/>
			<form action="addEditTeam" method="post" >
							
				<div class="row">
					<div>
						<label class="required"><strong>Nombre del Equipo</strong></label> <input id="teamName"
							type="text" class="text-input-2 required-field" name="teamName" 
								value="${teamName}">
						<div class="error-field">Nombre del equipo no puede estar en blanco.</div>	
					</div>
					<div>
						<label><strong>Nombre Corto</strong>
						</label> <input id="teamShortName" type="text" class="text-input-2" name="teamShortName" value="${teamShortName}">
					</div>
					<div>
						<label><strong>Localidad</strong>
						</label> <input id="teamLocation" type="text" class="text-input-2" name="teamLocation" value="${teamLocation}">
					</div>
					
					<div><input name="idTeam" value="${idTeam}" type="hidden"></div>
					   
				</div>
				
				<div>
					<div>				
						<input type="submit" class="btn" value="${teamName == null || teamName == '' ? 'Crear' : 'Actualizar'} Equipo" name="method:addEditTeam"> o 
						<a href="allTeams">Cancelar</a>						
						
					</div>
				</div>
				
			</form>	
			
		</div>
	</div> 
	<jsp:include page="../footer.jsp"/>

</body>
</html>