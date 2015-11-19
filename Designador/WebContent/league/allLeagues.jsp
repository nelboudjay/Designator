<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/maintenance.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>

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
			<h4 class="new-record"><a href="addEditLeague"><img src="${pageContext.request.contextPath}/images/add-icon.png" class="small-icon">Añadir una competición</a></h4>
			<br>
			
			<s:if test="#attr.leagues.size() == 0">
			
			
				<div class="panel-info">
					<div class="panel-body">
						No hay ninguna competición disponible en el sistema. ¿Quieres <a class="link" href="addEditLeague">Añadir</a> una competición?
					</div>
				</div>
			</s:if>
			<s:else>
				<table id="leagues">
					<tr><th colspan="2">Nombre de Competición</th></tr>	
					<s:iterator value="#attr.leagues">
						<tr>
							<td><a class="link" href="addEditLeague?idLeague=${idLeague}">${leagueName}</a></td>
							<td><a class="link delete" href="deleteLeague?idLeague=${idLeague}">Eliminar</a>
								<span class="confirm-box">
									<span class="message">¿Estás seguro que quieres
										eliminar esta competición? </span> <span class="btn yes">Sí</span> 
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