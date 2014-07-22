<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Date" %>



<html>
<head>
<meta http-equiv="refresh" content="300;url=loginAction" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/MyStyle.css" />

<title>Página de Inicio</title>
<s:head />
<sx:head />
</head>
<body>

	<%@ include file="idle-timeout.html"%>

	<div class="utility">

		<a href="passwordForgot">${session.userFullName}
			(${session.user.userName}) </a> | <a href="logout">Cerrar sesión</a> | <a
			href="passwordForgot">Mensajes</a> | <a
			href="passwordResetInstructions">Ayuda</a>
	</div>
	<br />

	<sx:tabbedpanel id="tabContainer">
		<s:if test="hasActionErrors()">
			<br/>
			<div class="errors">
				<s:actionerror />
			</div>
		</s:if>	
		<sx:div label="Inicio">
			<h2>Todos los partidos</h2>
			<p>¿Quieres añadir partidos?</p>
			<table class="calendar">
				<thead>
					<tr>
						<s:iterator
							value="{'Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo'}">
							<th><s:property value="top" /></th>
						</s:iterator>
					</tr>
				</thead>
				<tbody>
					<jsp:useBean id="currentCalendar"
						class="com.myproject.calendar.CurrentCalendar"/>
						<c:forEach var="row" items="${currentCalendar.currentCalendar}"
							varStatus="rowStatus">
							<tr>
								<c:forEach var="column" items="${row}" varStatus="columnStatus">
									<c:choose>
										<c:when test="${column.today}">
											<td class="active">
										</c:when>
										<c:otherwise>
											<td>
										</c:otherwise>
									</c:choose>
										${column.day} 
										<c:if
										test="${rowStatus.index == 0 && columnStatus.index == 0
													|| column.day == 1}">
										<span>de </span>${column.month} 
										</c:if>
									</td>
								</c:forEach>
							</tr>
						</c:forEach>
				</tbody>
			</table>
			<h2>Comentarios</h2>
			<ol>
			
				<s:iterator value="#session.comments" var="comment">
					<li>
					<s:property value="#comment.user.userFullName" />
					<s:property value="#comment.commentBody" escape="false"/> 
					
					<jsp:useBean id="now" class="java.util.Date" />
					<fmt:formatDate pattern="dd/MM/yyyy"  value="${now}" var="today"/>
					<fmt:formatDate pattern="dd/MM/yyyy"  value="${comment.commentDate}" var="commentDate"/>
					<fmt:formatDate pattern="dd/MM/yyyy" value="<%=new Date(new Date().getTime() - 60*60*24*1000)%>" var="yesterday"/>
									
					<c:choose> 
						<c:when test="${today == commentDate}">
							Hoy -
							<s:date name="#comment.commentDate" format="HH:mm:ss" />
						</c:when>
						<c:when test="${yesterday == commentDate}">
							Ayer -
							<s:date name="#comment.commentDate" format="HH:mm:ss" />
						</c:when>
						<c:otherwise>
							<s:date name="#comment.commentDate" format="dd/MM/yyyy - HH:mm:ss" />
						</c:otherwise>
					</c:choose>
					h
					<s:if
							test="#session.user.idUser == #comment.user.idUser">
							<a href="editComment">Modificar</a> | <s:a href="deleteComment?idComment=%{#comment.commentDate}">Eliminar</s:a>
						</s:if></li>
					<br/>
				</s:iterator>
			</ol>
			<s:if test="#session.user.isAdmin()">
				<a href="addComment">Añadir comentario</a>
			</s:if>

		</sx:div>
		<sx:div label="Tab 2">Tab 2</sx:div>
	</sx:tabbedpanel>

	<br />
	<s:div>
		<s:if test="#session.user.userRole.userRoleName == 'Admin'">
		</s:if>
		<s:elseif test="#session.user.userRole.userRoleName == 'Referee'">
		</s:elseif>
	</s:div>
</body>
</html>
