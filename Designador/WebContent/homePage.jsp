<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="css/MyStyle.css" />
<link rel="stylesheet" type="text/css" href="css/idle-timeout.css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/homePageScript.js"></script>

<title>Designador</title>

<s:head />
</head>
<body>

	<nav>
		<ul class="logo">
			<li><a href="homePage"><img src="images/Logo.png"
					width="12%" height="90%"></a></li>
		</ul>
		<ul class="navbar-right">
			<li id="userName"><span><img class="profile-icon"
					src="images/profile-icon.png">${session.user.userFullName}
					(${session.user.userName}) <span class="arrow"></span> </span></li>
			<li id="profile" class="dropdown-menu"><a>Perfil</a></li>
			<li id="messages" class="dropdown-menu"><a>Mensajes</a></li>
			<li id="logout" class="dropdown-menu"><a href="logout">Cerrar
					sesión</a></li>

		</ul>
	</nav>

	<div class="sidebar-background"></div>
	<div id="leftMenu">
		<ul>
			<li class="active"><span class="glow"></span><a href="homePage"><img
					class="home-icon" src="images/home-icon.png">Inicio</a></li>
			<li><span class="glow"></span><a><img class="home-icon"
					src="images/games-icon.png">Partidos<span class="arrow"></span></a></li>
			<li><span class="glow"></span><a><img class="home-icon"
					src="images/referee-icon.png">Árbitros</a></li>
		</ul>
	</div>

	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="home-black-icon" src="images/home-black-icon.png">
				Inicio
			</h3>
			<span>Todos los partidos</span>
		</div>

		<s:if test="hasActionErrors()">
			<div class="errors">
				<button class="close" type="button">×</button>
				<s:actionerror />
			</div>
		</s:if>
		<s:if test="hasFieldErrors()">
			<div class="errors">
				<button class="close" type="button">×</button>
				<s:fielderror />
			</div>
		</s:if>
		<s:if test="hasActionMessages()">
			<div class="boxMessage">
				<button class="close" type="button">×</button>
				<s:actionmessage />
			</div>
		</s:if>


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
					class="com.myproject.calendar.CurrentCalendar" />
				<c:forEach var="row" items="${currentCalendar.currentCalendar}"
					varStatus="rowStatus">
					<tr>
						<c:forEach var="column" items="${row}" varStatus="columnStatus">
							<td
								${column.today == 0 ? 'class=\"active\"' : column.today > 0 ? 'class=\"future\"' : ''}>${column.day}
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

		<div id="comments">
			<h4 class="comments">Comentarios</h4>
			<s:if test="#session.user.isAdmin()">
				<textarea rows="1" cols="100" placeholder="Añadir comentario..."
					id="commentBody" name="commentBody"></textarea>
				<div class="required-field "></div>
				<p class="add-comment-submit" style="display: none;">
					<button id="addComment" class="btn">Añadir Comentario</button>
					<a id="cancelCommentLink">Cancelar</a>
				</p>
			</s:if>

			<ul class="comments-list" style="margin-top: 20px;">
				<s:iterator value="#session.comments" var="comment" step="-1"
					begin="#session.comments.size -1" status="status">
					<li><s:div id="comment_%{#status.count}">
							<img src="images/profile-icon.png" class="avatar">
							<div class="info">
								<span class="name"><s:property
										value="#comment.user.userFullName" /></span> <span class="time">
									<img src="images/clock-icon.png" class="clock"> <s:date
										name="%{new java.util.Date()}" format="dd/MM/yyyy" var="today" />
									<s:date
										name="%{new java.util.Date(new java.util.Date().getTime() - 60*60*24*1000)}"
										format="dd/MM/yyyy" var="yesterday" /> <s:date
										name="#comment.commentDate" format="dd/MM/yyyy"
										var="commentDate" /> <s:if test="#commentDate == #today">
										Hoy, 
									</s:if> <s:elseif test="#commentDate == #yesterday">
										Ayer, 
									</s:elseif> <s:else>
										El <s:date name="#comment.commentDate" format="d" /> de <s:date
											name="#comment.commentDate" format="MMM" /> 
									 de <s:date name="#comment.commentDate" format="yyyy" />,
									</s:else> a las <s:date name="#comment.commentDate" format="HH:mm:ss" />
									horas
								</span>
							</div>
							<div id="${comment.commentId}"  class="content">
								<div class="comment-body">
									<s:property value="#comment.commentBody" escape="false" />
								</div>

								<s:if test="#session.user.idUser == #comment.user.idUser">
									<div class="edit-comment">
										<span class="modify-comment">Modificar</span> | <span
											class="delete-comment"> Eliminar</span>
										<div class="confirm-box">
											<span class="message">¿Estás seguro que quieres
												eliminar este mensaje? </span> <span class="btn yes">Sí</span> <span
												class="btn no">No</span>
										</div>
									</div>
								</s:if>
							</div>
						</s:div></li>
				</s:iterator>
			</ul>

		</div>

	</div>


</body>
</html>
