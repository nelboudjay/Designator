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
				<s:property value="#attr.userFullName"/>
			</h3>
			<span>Perfil</span>
		</div>
		
		<jsp:include page="errorMessages.jsp"/>
		
		<br/>
		
		<div class="container">
				
				<s:set var="currentIdUser" value="idUser"/>

				<s:iterator value="#attr.users" status="status" >
					<s:if test="idUser == #currentIdUser">
						<s:if test="#status.count > 1">
							<s:set var="previousUserFullName" value="#attr.users[#status.index-1].userFullName"/>
							<s:set var="previousIdUser" value="#attr.users[#status.index-1].idUser"/>	
						</s:if>
												
						<s:if test="#status.count < #attr.users.size">
							<s:set var="nextUserFullName" value="#attr.users[#status.index+1].userFullName"/>	
							<s:set var="nextIdUser" value="#attr.users[#status.index+1].idUser"/>									
						</s:if>
					</s:if>
				</s:iterator>

			<div class="user-menu">
				<img  src="getImage?idUser=${idUser}" >
				<span><s:property value="#attr.userFullName"/></span>
				<ul>
					<li><a>Partidos</a></li>
					<li><a>Disponibilidad</a></li>
					<li><a>Conflictos</a></li>
				</ul>
			</div>
			<div class="user-paginate">
				
				<s:if test="#previousUserFullName != null ">
					<a class="btn" href="user?idUser=${previousIdUser}"><img src="images/back-icon.png"><s:property value="#previousUserFullName"/></a>						
				</s:if>
				
				<s:if test="#nextUserFullName != null ">
					<a class="btn" href="user?idUser=${nextIdUser}"><s:property value="#nextUserFullName"/><img src="images/forward-icon.png"></a>						
				</s:if>
				
			</div>
			
			<br/>
			<h3 class="title-2">Información Personal</h3>
			<table class="perso-info">
				<tr>
					<td>Correo Electrónico Principal</td>
					<td><s:property value="#attr.email"/></td>
				</tr>
				<tr>
					<td>Estado</td>
					<td style="text-decoration:underline;">
						<s:if test="#attr.confirmed">
							<span title="Usuario ha confirmado su registro.">Confirmado</span>
						</s:if>	
						<s:else>
							<span title="Usuario sin confirmar su registro.">No Confirmado</span>
						</s:else>
					</td>
				</tr>
				<tr>
					<td>Previlegios</td>
					<td>
						<s:if test="#attr.userRoleName == 'Admin'">
							Designador
						</s:if>	
						<s:elseif test="#attr.userRoleName == 'Referee'">
							Árbitro
						</s:elseif>
						<s:else>
							Designador y Árbitro
						</s:else>
					</td>
				</tr>
			</table>
			<p><a href="users" class="link">« Todos los Miembros</a> · 
			
				<s:if test="idUser == #session.user.idUser">
					<a class="link" href="editUser?idUser=${idUser}">Editar</a> · 
					<a class="link" href="changePassword">Cambiar contraseña</a> 
				</s:if>
				<s:elseif test="#session.user.isAdmin()">
					<a class="link" href="editUser?idUser=${idUser}">Editar</a> · 
					<a class="link delete" href="deleteUser?idUser=${idUser}">Eliminar</a>
					<span class="confirm-box">
						<span class="message">¿Estás seguro que quieres
							eliminar este miembro? </span> <span class="btn yes">Sí</span> 
							<span class="btn no">No</span>
					</span>	 
					
				</s:elseif>
				
			</p>
		</div>
	</div>

</body>
</html>