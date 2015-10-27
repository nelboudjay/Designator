<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="css/user.css" />

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
				<s:if test="idUser == null || idUser == ''">
					Nuevo Miembro
				</s:if>
				<s:else>				
					${attr.userFullName}
				</s:else>
			</h3>
			<span>Perfil</span>
		</div>
		
		<jsp:include page="errorMessages.jsp"/>
		
		<div class="container">
			<form action="${idUser == null || idUser == '' ? 'addUser' : 'editUser' }" method="post" enctype="multipart/form-data">
			
				<h3 class="title-1">Nombre</h3>
				
				<div class="row">
					<div>
						<label class="required"><strong>Nombre</strong></label> <input id="firstName"
							type="text" class="text-input-2 required-field" name="firstName" 
								value="${firstName}">
						<div class="error-field">Nombre no puede estar en blanco.</div>
							
					</div>
					  
					<div>
						<label class="required"><strong>Primer Apellido</strong></label> <input id="lastName1"
							type="text" class="text-input-2 required-field" name="lastName1" 
								value="${lastName1}">
						<div class="error-field">Primer Apellido no puede estar en blanco.</div>
							
					</div>
					
					<div>
						<label><strong>Segundo Apellido</strong></label> <input id="lastName2"
							type="text" class="text-input-2" name="lastName2" 
							value="${lastName2}">
					</div> 
				</div>
								
				<h3 class="title-1">Dirección Postal</h3>
				
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
				
				<h3 class="title-1">Teléfono</h3>
				
				<div class="row">
					<div>
						<label><strong>Fijo</strong></label> <input id="homePhone"
							type="text" class="text-input-2" name="homePhone" 
							value="${homePhone}">
							<div class="error-field">Número de teléfono no válido.</div>
					</div>
					
					<div>
						<label><strong>Móvil</strong></label> <input id="mobilePhone"
							type="text" class="text-input-2" name="mobilePhone" 
							value="${mobilePhone}">
						<div class="error-field">Número de teléfono no válido.</div>				
					</div>
				</div>
				
				<h3 class="title-1">Correo Electrónico</h3>
				
				<div class="row">
					<div>
						<label class="required"><strong>Correo Electrónico Principal</strong></label>  <input id="email"
							type="text" class="text-input-2 required-field email" name="email" 
							value="${email}">
							<div class="error-field"></div>
							
					</div>
					
					
					<div>
						<label><strong>Correo Electrónico Secundario</strong></label> <input id="email2"
							type="text" class="text-input-2 email" name="email2" 
							value="${email2}">
							<div class="error-field"></div>
					
					</div>
					
				</div>
				
				<s:if test="idUser == #session.user.idUser">
				
			
					<h3 class="title-1">
						Usuario
						<s:if test="idUser == #session.user.idUser">
							y Contraseña
						</s:if>
					 </h3>
					 
				    <div class="row">
					
						<div>
							<label class="required"><strong>Usuario</strong></label> <input id="userName"
								type="text" class="text-input-2 required-field" name="userName" 
								value="${userName}">
								<div class="error-field">Usuario no puede estar en blanco.</div>
						</div>
						
							<div>
								<label class="required"><strong>Contraseña</strong></label> <input id="password"
									type="password" class="text-input-2 required-field identical-field" name="password" 
									value="${password}">
								<div class="error-field">Introduce tu contraseña.</div>
							
							</div>
							
							<div>
								<label class="required"><strong>Confirmar Contraseña</strong></label> <input id="repassword"
									type="password" class="text-input-2 required-field identical-field" name="repassword">	
								<div class="error-field">Las contraseñas deben ser iguales.</div>
							</div>
						
					</div> 
				</s:if>
			
				<h3 class="title-1">Foto de Perfil 
					<span  id="profileImage">
						<button type="button" class="close2" title="Eliminar foto">×</button>
						<img  src="getImage?idUser=${idUser}"   width="24px" height="24px"> 
						<s:if test="picture != null">
							<input type="hidden" name="currentPicture"  value="true">
						</s:if>		
						<s:else>
							<input type="hidden" name="currentPicture"  value="false">
						</s:else>
					</span>
				</h3>
					
				<div class="row">
				  	<div>
						<label><strong>Elige una foto de Perfil</strong></label> 
						<input id="picture" type="file" class="file-input" name="picture">
						<div class="error-field"></div>
							
					</div>					
				</div>
				
				<s:if test="#session.user.isAdmin()">
					<h3 class="title-1">Privilegios</h3>
					
					<s:if test="idUserRole != null">
						<s:set var="currentIdUserRole" value="idUserRole"/>
					</s:if>
					<s:else>
						<s:set var="currentIdUserRole" value="1"/>
					</s:else>
									
					<div class="row">
						<div>
							<label><b>Designador</b></label>
							<input type="radio" value="1" name="idUserRole" ${currentIdUserRole == '1' ? 'checked' : ''}>
							
							<label><b>Árbitro</b></label>
							<input type="radio" value="2" name="idUserRole" ${currentIdUserRole == '2' ? 'checked' : ''}>
							
							<label><b>Designador y Árbitro</b></label>
							<input  type="radio" value="3" name="idUserRole" ${currentIdUserRole == '3' ? 'checked' : ''}>
						</div>
					</div>
				
				</s:if>
				
				<input name="idUser" value="${idUser}" type="hidden">
				<div class="row">
					<div>				
						<s:if test="idUser == null || idUser == ''">
							<input type="submit" class="btn" value="Añadir Miembro" name="method:addUser"> o 
							<a href="users">Cancelar</a>
						</s:if>
						<s:else>
							<input type="submit" class="btn" value="Actualizar Perfil" name="method:editUser"> o 
							<a href="user?idUser=${idUser}">Cancelar</a>
						</s:else>						
						
					</div>
				</div>
				
			</form>	
			
		</div>
	</div> 

</body>
</html>