<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="css/homePage.css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/commonScript.js"></script>
<script type="text/javascript" src="js/homePage.js"></script>

<title>Designador</title>

<s:head />
</head>
<body>

	<jsp:include page="header.jsp"/>

	<jsp:include page="leftMenu.jsp"/>

	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="images/home-black-icon.png">
				Inicio
			</h3>
			<span>Todos los partidos</span>
		</div>

		<jsp:include page="errorMessages.jsp"/>

		<div class="container">
		
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
				<h4 class="comments">Avisos</h4>
				<s:if test="#session.user.isAdmin()">
					<div>
						<textarea rows="1" cols="100" placeholder="Añadir aviso..."
							id="commentBody" name="commentBody"  class="required-field"></textarea>
						<div class="error-field">El aviso no puede estar en blanco.</div>
					</div>
					<p class="add-comment-submit" style="display: none;">
						<button id="addComment" class="btn">Añadir Aviso</button>
						<a id="cancelCommentLink">Cancelar</a>
					</p>
				</s:if>
	
				<ul class="comments-list" style="margin-top: 20px;">
					<s:iterator value="#session.comments" var="comment" step="-1"
						begin="#session.comments.size -1" status="status">
						<li><s:div id="comment_%{#status.count}">
								<img src="images/avatar-icon.png" class="avatar">
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
											<span class="modify-comment link" class="modify-comment">Modificar</span> | <span
												class="delete-comment link"> Eliminar</span>
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
	</div>


</body>
</html>
