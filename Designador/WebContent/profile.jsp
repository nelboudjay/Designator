<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="css/homePage.css" />

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
			<form action="profile" method="post" enctype="multipart/form-data">
			
				<h3 class="title-1">Nombre</h3>
				
				<div class="row">
					<div>
						<label><strong>Nombre</strong></label> <input id="firstName"
							type="text" class="text-input-2 required-field" name="firstName" value="${session.user.userProfile.firstName}">
						<div class="error-field">Nombre no puede estar en blanco.</div>
							
					</div>
					  
					  	<div>
						<label><strong>Primer Apellido</strong></label> <input id="lastName1"
							type="text" class="text-input-2 required-field" name="lastName1" value="${session.user.userProfile.lastName1}">
						<div class="error-field">Primer Apellido no puede estar en blanco.</div>
							
					</div>
					
					<div>
						<label><strong>Segundo Apellido</strong></label> <input id="lastName2"
							type="text" class="text-input-2" name="lastName2" value="${session.user.userProfile.lastName2}">
					</div> 
				</div>
								
				<h3 class="title-1">Dirección Postal</h3>
				
				<div class="row">
					<div>
						<label><strong>Dirección Linea 1</strong></label> <input id="address1"
							type="text" class="text-input-2" name="address1" value="${session.user.userProfile.address.address1}">	
					</div>
					
					<div>
						<label><strong>Dirección Linea 2</strong></label> <input id="address2"
							type="text" class="text-input-2" name="address2" value="${session.user.userProfile.address.address2}">
					</div>
				</div>
				
				<div class="row">
					<div>
						<label><strong>Población</strong></label> <input id="city"
							type="text" class="text-input-2" name="city" value="${session.user.userProfile.address.city}">
					</div>
					
					<div>
						<label><strong>Provincia</strong></label> <input id="province"
							type="text" class="text-input-2" name="province" value="${session.user.userProfile.address.province}">
							
					</div>
					
					<div>
						<label><strong>Código Postal</strong></label> <input id="zipcode"
							type="text" class="text-input-2" name="zipcode" size="5" maxlength="5" value="${session.user.userProfile.address.zipcode}">
						<div class="error-field">Código postal incorrecto.</div>
					</div>
				</div>
				
				<h3 class="title-1">Teléfono</h3>
				
				<div class="row">
					<div>
						<label><strong>Fijo</strong></label> <input id="homePhone"
							type="text" class="text-input-2" name="homePhone" value="${session.user.userProfile.homePhone}">	
							<div class="error-field">Número de teléfono no válido.</div>
					</div>
					
					<div>
						<label><strong>Móvil</strong></label> <input id="mobilePhone"
							type="text" class="text-input-2" name="mobilePhone" value="${session.user.userProfile.mobilePhone}">
						<div class="error-field">Número de teléfono no válido.</div>				
					</div>
				</div>
				
				<h3 class="title-1">Correo Electrónico</h3>
				
				<div class="row">
					<div>
						<label><strong>Correo Electrónico Principal</strong></label> <input id="email"
							type="text" class="text-input-2 required-field email" name="email" value="${session.user.email}">	
							<div class="error-field"></div>
					</div>
					
					<div>
						<label><strong>Correo Electrónico Secundario</strong></label> <input id="email2"
							type="text" class="text-input-2 email" name="email2" value="${session.user.userProfile.email2}">			
							<div class="error-field"></div>
					
					</div>
					
				</div>
				
			
				<h3 class="title-1">Usuario y Contraseña</h3>
				 
			    <div class="row">
				
					<div>
						<label><strong>Usuario</strong></label> <input id="userName"
							type="text" class="text-input-2 required-field" name="userName" value="${session.user.userName}">	
							<div class="error-field">Usuario no puede estar en blanco.</div>
					</div>
					
					
					<div>
						<label><strong>Contraseña</strong></label> <input id="password"
							type="password" class="text-input-2 required-field identical-field" name="password" value="${session.user.password}">	
						<div class="error-field">Introduce tu contraseña.</div>
					
					</div>
					
					<div>
						<label><strong>Confirmar Contraseña</strong></label> <input id="repassword"
							type="password" class="text-input-2 required-field identical-field" name="repassword">	
						<div class="error-field">Las contraseñas deben ser iguales.</div>
					</div>
					
				</div> 
			
				<h3 class="title-1">Foto de Perfil</h3>
				
				<div class="row">
				  	<div>
						<label><strong>Elige una foto de Perfil</strong></label> <input id="picture"
							type="file" class="file-input" name="picture">
							File : <s:property value="picture"/>	
					</div>					
				</div>
				
				<div class="row">
					<div>
						<input type="submit" class="btn" value="Actualizar Perfil" name="method:editProfile"> o 
						<a href="homePage">Cancelar</a>
					</div>
					
				</div>
				
			</form>
			
		</div>
	</div>

</body>
</html>