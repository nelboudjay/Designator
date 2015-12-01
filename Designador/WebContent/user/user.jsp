<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/user.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>

<title>Perfil</title>

<s:head />
</head>
<body>
	<jsp:include page="../header.jsp"/>

	<jsp:include page="../leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="${pageContext.request.contextPath}/images/profile-black-icon.png">
				<s:property value="userFullName"/>
			</h3>
			<span>Perfil</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<br/>
		
		<div class="container">
			<s:if test="!#session.user.isAdmin() && idUser != #session.user.idUser && privacy">
				<div class="panel-info">
					<div class="panel-body">	
						No hay información disponible para este miembro.		
					</div>
				</div>
			</s:if>
			<s:else>
			
				<s:if test="#session.user.isAdmin()">
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
				</s:if>
				<div class="user-menu">
					<img  src="getImage?idUser=${idUser}" >
					<a href="user?idUser=${idUser}"><s:property value="#attr.userFullName"/></a>
					<s:if test="#session.user.isAdmin() && userRole > 1">
						<ul>
							<li><a href="${pageContext.request.contextPath}/game/games?idUser=${idUser}">Partidos</a></li>
							<li><a href="${pageContext.request.contextPath}/availability/availability?idUser=${idUser}">Disponibilidad</a></li>
							<li><a>Conflictos</a></li>
						</ul>
					</s:if>
				</div>
				<s:if test="#session.user.isAdmin()">
					<div class="user-paginate">
						
						<s:if test="#previousUserFullName != null ">
							<a class="btn" href="user?idUser=${previousIdUser}"><img src="${pageContext.request.contextPath}/images/back-icon.png"><s:property value="#previousUserFullName"/></a>						
						</s:if>
						
						<s:if test="#nextUserFullName != null ">
							<a class="btn" href="user?idUser=${nextIdUser}"><s:property value="#nextUserFullName"/><img src="${pageContext.request.contextPath}/images/forward-icon.png"></a>						
						</s:if>
						
					</div>
				</s:if>
				<br/>
				<h3 class="title-2">Información Personal 
					<s:if test="privacy"><span class="private" title="El perfil está oculto para los demás árbitros">Privada</span></s:if>
				</h3>
				<table class="perso-info">
					<s:if test="address != null">
						<tr>
							<td>Dirección</td>
							<td><s:property value="address.fullAddress"/> </td>
						</tr>
					</s:if>
					<s:if test="homePhone != null && homePhone != ''">
						<tr>
							<td>Teléfono fijo</td>
							<td><s:property value="homePhone"/></td>
						</tr>
					</s:if>
					<s:if test="mobilePhone != null && mobilePhone != ''">
						<tr>
							<td>Teléfono móvil</td>
							<td><s:property value="mobilePhone"/></td>
						</tr>
					</s:if>
					<tr>
						<td>Correo Electrónico Principal</td>
						<td><s:property value="email"/></td>
					</tr>
					<s:if test="#session.user.isAdmin()">
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
					</s:if>
				</table>
			</s:else>
			<p><a href="users" class="link">« Todos los Miembros</a>
			
				<s:if test="idUser == #session.user.idUser">
					· <a class="link" href="editUser?idUser=${idUser}">Editar</a> · 
					<a class="link" href="changePassword">Cambiar contraseña</a> 
				</s:if>
				<s:elseif test="#session.user.isAdmin()">
					. <a class="link" href="editUser?idUser=${idUser}">Editar</a> · 
					<a class="link delete" href="deleteUser?idUser=${idUser}">Eliminar</a>
					<span class="confirm-box important-2">
						<span class="message">
						IMPORTANTE: Si este miembro tiene partidos designados, habrá que volver a designarlos a otro miembro
						¿Estás seguro que quieres eliminar este miembro? </span> <span class="btn yes">Sí</span> 
							<span class="btn no">No</span>
					</span>	 
					
				</s:elseif>
				
			</p>
			
			
		</div>
		<jsp:include page="../footer.jsp"/>
		
	</div>

</body>
</html>