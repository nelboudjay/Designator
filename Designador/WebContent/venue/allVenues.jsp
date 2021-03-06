<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/maintenance.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>

<title>Pistas</title>

<s:head />
</head>
<body>
	<jsp:include page="../header.jsp"/>

	<jsp:include page="../leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="${pageContext.request.contextPath}/images/venue-black-icon.png">
				Pistas
			</h3>
			<span>Todas las Pistas</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<div class="container">
			<br>	
			<s:if test="#session.user.isAdmin()">
				<h4 class="new-record"><a href="addEditVenue">
					<img src="${pageContext.request.contextPath}/images/add-icon.png" class="small-icon">
						Añadir una Pista</a>
				</h4>
				<br>
			</s:if>
			
			<s:if test="#attr.venues.size() == 0">
				<div class="panel-info">
					<div class="panel-body">
						No hay ninguna pista disponible en el sistema. 
						<s:if test="#session.user.isAdmin()">
							¿Quieres <a class="link" href="addEditVenue">Añadir</a> una pista?
						</s:if>
					</div>
				</div>
			</s:if>
			<s:else>
				<table id="venues">
					<tr>
						<th>Nombre de Pista</th>
						<th>Dirección</th>
						<th>Nombre de Responsable</th>
						<th>Teléfono de Contacto</th>
						<s:if test="#session.user.isAdmin()">
							<th></th>
						</s:if>
					</tr>	
					<s:iterator value="#attr.venues">
						<tr>
							<td>
								<s:if test="#session.user.isAdmin()">
									<a class="link" href="addEditVenue?idVenue=${idVenue}">${venueName}</a>
								</s:if>
								<s:else>
									${venueName}
								</s:else>
							
							</td>
							<td>${venueAddress != null ? venueAddress.getFullAddress() : ''}</td>
							<td>${venueContactName}</td>
							<td>${venueContactPhone}</td>
							<s:if test="#session.user.isAdmin()">
								<td style="direction:rtl;"> <a class="link delete" href="deleteVenue?idVenue=${idVenue}">Eliminar</a>
								
									<span class="confirm-box" >
										<span class="message"> Estás seguro que quieres
											eliminar esta pista? </span> <span class="btn yes">Sí</span> 
											<span class="btn no">No</span>
									</span>	
								</td>
							</s:if>
							
						</tr>	
					</s:iterator>
				</table>
			</s:else>
			
		</div>		
	</div>
	<jsp:include page="../footer.jsp"/>

</body>
</html>