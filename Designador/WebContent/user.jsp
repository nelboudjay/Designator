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
				<s:property value="user.userFullName"/>
			</h3>
			<span>Perfil</span>
		</div>
		
		<jsp:include page="errorMessages.jsp"/>
		
		<br/>
		
		<div class="container">
						
				<s:property value="users.size"/>
				<s:iterator value="users" status="status" >
					<s:if test="user.idUser == idUser">

						<s:if test="#status.count > 1">
							<s:set var="previousUserFullName" value="users[#status.index-1].userFullName"/>
						</s:if>
						
						<s:if test="#status.count < users.size">
							<s:set var="nextUserFullName" value="users[#status.index+1].userFullName"/>						
						</s:if>
					</s:if>
				</s:iterator>
			
			
			<div class="user-menu">
				<img  src="getImage?idUser=${user.idUser}" >
				<span><s:property value="user.userFullName"/></span>
				<ul>
					<li><a>Partidos</a></li>
					<li><a>Disponibilidad</a></li>
					<li><a>Conflictos</a></li>
				</ul>
			</div>
			<div class="user-paginate">
				
				<s:if test="#previousUserFullName != null ">
					<a class="btn"><img src="images/back-icon.png"><s:property value="#previousUserFullName"/></a>						
				</s:if>
				
				<s:if test="#nextUserFullName != null ">
					<a class="btn"><s:property value="#nextUserFullName"/><img src="images/forward-icon.png"></a>						
				</s:if>
				
			</div>
			
			<br/>
			<h3 class="title-2">Información Personal</h3>
			<table class="perso-info">
				<tr>
					<td>Correo Electrónico Principal</td>
					<td><s:property value="user.email"/></td>
				</tr>
				<tr>
					<td>Estado</td>
					<td style="text-decoration:underline;">
						<s:if test="user.password == ''">
							<span title="Usuario sin confirmar su registro.">No Confirmado</span>
						</s:if>	
						<s:else>
							<span title="Usuario ha confirmado su registro.">Confirmado</span>
						</s:else>
					</td>
				</tr>
				<tr>
					<td>Previlegios</td>
					<td>
						<s:if test="user.userRole.userRoleName == 'Admin'">
							Designador
						</s:if>	
						<s:elseif test="user.userRole.userRoleName == 'Referee'">
							Árbitro
						</s:elseif>
						<s:else>
							Designador y Árbitro
						</s:else>
					</td>
				</tr>
			</table>
			<p><a href="users" class="link">« Todos los Miembros</a> · 
			
				<s:if test="user.idUser == #session.user.idUser">
					<a class="link" href="editUser?idUser=${user.idUser}">Editar</a> · 
					<a class="link" href="changePassword">Cambiar contraseña</a> 
				</s:if>
				<s:elseif test="#session.user.isAdmin()">
					<a class="link" href="editUser?idUser=${user.idUser}">Editar</a> · 
					<a class="link delete" href="deleteUser?idUser=${user.idUser}">Eliminar</a>
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