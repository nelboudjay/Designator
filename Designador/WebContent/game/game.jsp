<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/game.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/game.js"></script>

<title>Partidos</title>

<s:head />
</head>
<body>
	<jsp:include page="../header.jsp"/>

	<jsp:include page="../leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="${pageContext.request.contextPath}/images/game-black-icon.png">
				Partidos
			</h3>
			<span>Barça vs Madrid</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<br/>
		
		<div class="container">
				

			<div class="user-menu">
				<a href="">Fecha</a>
				
			</div>
			
			<br/>
			<h3 class="title-2">Información Personal</h3>
			<table class="perso-info">
				<s:if test="homePhone != ''">
					<tr>
						<td>Teléfono fijo</td>
						<td><s:property value="homePhone"/></td>
					</tr>
				</s:if>
				<s:if test="mobilePhone != ''">
					<tr>
						<td>Teléfono móvil</td>
						<td><s:property value="mobilePhone"/></td>
					</tr>
				</s:if>
				<tr>
					<td>Correo Electrónico Principal</td>
					<td><s:property value="email"/></td>
				</tr>
				<tr>
					<td>Estado</td>
					<td style="text-decoration:underline;">
						<s:if test="confirmed">
							<span title="Usuario ha confirmado su registro.">Confirmado</span>
						</s:if>	
						<s:else>
							<span title="Usuario sin confirmar su registro.">No Confirmado</span>
						</s:else>
					</td>
				</tr>
				<tr>
					<td>Previlegios </td>
					<td>
						<s:property value="userRoleName"/>
						<s:if test="userRole != 1">
							(<s:iterator value="userRefereeTypesNames" status="status">
								<s:if test="#status.count > 1">
									${status.count < userRefereeTypesNames.size() ? ', ' : ' y '}
									
								</s:if>
								<s:property/>
							</s:iterator>)
						</s:if>						
					</td>
				</tr>
			</table>
			<p><a href="users" class="link">« Todos los Miembros</a>
			
				<s:if test="idUser == #session.user.idUser">
					· <a class="link" href="editUser?idUser=${idUser}">Editar</a> · 
					<a class="link" href="changePassword">Cambiar contraseña</a> 
				</s:if>
				<s:elseif test="#session.user.isAdmin()">
					. <a class="link" href="editUser?idUser=${idUser}">Editar</a> · 
					<a class="link delete" href="deleteUser?idUser=${idUser}">Eliminar</a>
					<span class="confirm-box">
						<span class="message">¿Estás seguro que quieres
							eliminar este miembro? </span> <span class="btn yes">Sí</span> 
							<span class="btn no">No</span>
					</span>	 
					
				</s:elseif>
				
			</p>
		</div>
		<jsp:include page="../footer.jsp"/>
		
	</div>

</body>
</html>