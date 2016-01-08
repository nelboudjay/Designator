<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/maintenance.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>

<title>Añadir Pistas</title>

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
			<span>Nueva Pista</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<div class="container">
			<br/>
			<form action="addEditVenue" method="post" >
				<div class="venue-form">			
				<div class="row">
					<div>
						<label class="required"><strong>Nombre de Pista</strong></label> <input id="venueName"
							type="text" class="text-input-2 required-field" name="venueName" 
								value="${venueName}">
						<div class="error-field">Nombre de pista no puede estar en blanco.</div>	
					</div>
					
					<div>
						<label><strong>Nombre de Responsable</strong>
						</label> <input id="venueContactName" type="text" class="text-input-2" name="venueContactName" value="${venueContactName}">
					</div>
					
					<div>
						<label><strong>Teléfono de Contacto</strong></label> <input id="venueContactPhone"
							type="text" class="text-input-2" name="venueContactPhone" 
							value="${venueContactPhone}">
							<div class="error-field">Número de teléfono no válido.</div>
					</div>
					
					<div class="hidden"><input name="idVenue" value="${idVenue}" type="hidden"></div>
					   
				</div>
				
				<div class="row">
					<div>
						<label><strong>Dirección Linea 1</strong></label> <input id="address1"
							type="text" class="text-input-2" name="address1" 
							value="${address1}">
					</div>
					
					<div>
						<label><strong>Dirección Linea 2</strong></label> <input id="address2"
							type="text" class="text-input-2" name="address2" 
							value="${address2}">
					</div>
				</div>
				
				<div class="row">
					<div>
						<label><strong>Población</strong></label> <input id="city"
							type="text" class="text-input-2" name="city" 
							value="${city}">
					</div>
					
					<div>
						<label><strong>Provincia</strong></label> <input id="province"
							type="text" class="text-input-2" name="province" 
							value="${province}">
							
					</div>
					
					<div>
						<label><strong>Código Postal</strong></label> <input id="zipcode"
							type="text" class="text-input-2" name="zipcode" size="5" maxlength="5" 
							value="${zipcode}">
						<div class="error-field">Código postal incorrecto.</div>
					</div>
				</div>
				
				<div>
					<div>				
						<input type="submit" class="btn" value="${venueName == null || venueName == '' ? 'Crear' : 'Actualizar'} Pista" name="method:addEditVenue"> o 
						<a href="allVenues">Cancelar</a>						
						
					</div>
				</div>
				</div>
			</form>	
			
		</div>
	</div> 
	<jsp:include page="../footer.jsp"/>

</body>
</html>