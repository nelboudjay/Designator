<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/maintenance.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>

<title>Añadir Competición</title>

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
			<span>Nueva Competición</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<div class="container">
			<br/>
			<form action="addEditLeague" method="post" >
							
				<div>
					<div>
						<label class="required"><strong>Nombre de Competición</strong></label> <input id="leagueName"
							type="text" class="text-input-2 required-field" name="leagueName" 
								value="${leagueName}">
						<div class="error-field">Nombre de competición no puede estar en blanco.</div>	
					</div>
					 <div><input name="idLeague" value="${idLeague}" type="hidden"></div>
					   
				</div>
				
				<div>
					<div>				
						<input type="submit" class="btn" value="${leagueName == null || leagueName == '' ? 'Crear' : 'Actualizar'} Competición" name="method:addEditLeague"> o 
						<a href="allLeagues">Cancelar</a>						
						
					</div>
				</div>
				
			</form>	
			
		</div>
	</div> 
	<jsp:include page="../footer.jsp"/>

</body>
</html>