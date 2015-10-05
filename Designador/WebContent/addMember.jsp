<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="css/profile.css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/commonScript.js"></script>

<title>Nuevo Miembro</title>

<s:head />
</head>
<body>
	<jsp:include page="header.jsp"/>

	<jsp:include page="leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="images/profile-black-icon.png">
				Nuevo Miembro
			</h3>
		</div>
		
		<jsp:include page="errorMessages.jsp"/>
		
		<div class="container">
			<form action="addMember" method="post" enctype="multipart/form-data">
			
				<h3 class="title-1">Nombre</h3>
				
				<div class="row">
					<div>
						<label><strong>Nombre</strong></label> <input id="firstName"
							type="text" class="text-input-2 required-field" name="firstName" value="">
						<div class="error-field">Nombre no puede estar en blanco.</div>
							
					</div>
					  
					  	<div>
						<label><strong>Primer Apellido</strong></label> <input id="lastName1"
							type="text" class="text-input-2 required-field" name="lastName1" value="">
						<div class="error-field">Primer Apellido no puede estar en blanco.</div>
							
					</div>
					
					<div>
						<label><strong>Segundo Apellido</strong></label> <input id="lastName2"
							type="text" class="text-input-2" name="lastName2" value="">
					</div> 
				</div>
								
				<h3 class="title-1">Dirección Postal</h3>
				
				<div class="row">
					<div>
						<label><strong>Dirección Linea 1</strong></label> <input id="address1"
							type="text" class="text-input-2" name="address1" value="">	
					</div>
					
					<div>
						<label><strong>Dirección Linea 2</strong></label> <input id="address2"
							type="text" class="text-input-2" name="address2" value="">
					</div>
				</div>
				
				<div class="row">
					<div>
						<label><strong>Población</strong></label> <input id="city"
							type="text" class="text-input-2" name="city" value="">
					</div>
					
					<div>
						<label><strong>Provincia</strong></label> <input id="province"
							type="text" class="text-input-2" name="province" value="">
							
					</div>
					
					<div>
						<label><strong>Código Postal</strong></label> <input id="zipcode"
							type="text" class="text-input-2" name="zipcode" size="5" maxlength="5" value="">
						<div class="error-field">Código postal incorrecto.</div>
					</div>
				</div>
				
				<h3 class="title-1">Teléfono</h3>
				
				<div class="row">
					<div>
						<label><strong>Fijo</strong></label> <input id="homePhone"
							type="text" class="text-input-2" name="homePhone" value="">	
							<div class="error-field">Número de teléfono no válido.</div>
					</div>
					
					<div>
						<label><strong>Móvil</strong></label> <input id="mobilePhone"
							type="text" class="text-input-2" name="mobilePhone" value="">
						<div class="error-field">Número de teléfono no válido.</div>				
					</div>
				</div>
				
				<h3 class="title-1">Correo Electrónico</h3>
				
				<div class="row">
					<div>
						<label><strong>Correo Electrónico Principal</strong></label> <input id="email"
							type="text" class="text-input-2 required-field email" name="email" value="">	
							<div class="error-field"></div>
					</div>
					
					<div>
						<label><strong>Correo Electrónico Secundario</strong></label> <input id="email2"
							type="text" class="text-input-2 email" name="email2" value="">			
							<div class="error-field"></div>
					
					</div>
					
				</div>
				
			
				<h3 class="title-1">Usuario y Contraseña</h3>
				 
			    <div class="row">
				
					<div>
						<label><strong>Usuario</strong></label> <input id="userName"
							type="text" class="text-input-2 required-field" name="userName" value="">	
							<div class="error-field">Usuario no puede estar en blanco.</div>
					</div>
					
					
					<div>
						<label><strong>Contraseña</strong></label> <input id="password"
							type="password" class="text-input-2 required-field identical-field" name="password" value="">	
						<div class="error-field">Introduce tu contraseña.</div>
					
					</div>
					
					<div>
						<label><strong>Confirmar Contraseña</strong></label> <input id="repassword"
							type="password" class="text-input-2 required-field identical-field" name="repassword">	
						<div class="error-field">Las contraseñas deben ser iguales.</div>
					</div>
					
				</div> 
			
				<h3 class="title-1">Foto de Perfil 
					<span  id="profileImage">
						<button type="button" class="close2" title="Eliminar foto">×</button>
						<img  src=""  width="24px">
						<input type="hidden" name="currentPicture"  value="false">
					</span>
				</h3>
						
				<div class="row">
				  	<div>
						<label><strong>Elige una foto de Perfil</strong></label> 
						<input id="picture" type="file" class="file-input" name="picture">
						<div class="error-field">Formato de foto no válido. (Formatos válidos: png, gif, jpeg y jpg)</div>
							
					</div>					
				</div>
				
				<h3 class="title-1">Privilegios</h3>
				
				<div class="row">
					<div>
						<label><strong>Designador</strong></label> <input id="isAdmin"
							type="checkbox"  name="isAdmin" value="">	
					</div>
				</div>
				
				<div class="row">
					<div>
						<input type="submit" class="btn" value="Añadir Miembro" name=""> o 
						<a href="homePage">Cancelar</a>
					</div>
					
				</div>
				
			</form>
			
		</div>
	</div>

</body>
</html>