<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="refresh" content="300;url=loginAction" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/MyStyle.css" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.js"></script>


<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/tinymce/tinymce.min.js"></script>
<script type="text/javascript">
	tinymce
			.init({
				selector : "textarea",
				menubar : "insert format table tools",
				language : 'es',
				plugins : [
						"advlist autolink autoresize charmap code emoticons fullscreen hr link",
						"lists preview searchreplace spellchecker table textcolor wordcount " ],
				toolbar : "undo redo | styleselect | bold italic | forecolor backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | fullscreen | emoticons | preview | searchreplace"
			});
</script>

<script type="text/javascript">
	function doConfirm(msg, yesFn, noFn) {
		var confirmBox = $("#confirmBox");
		confirmBox.css("display", "inline");

		confirmBox.attr("text-align", "center");
		confirmBox.find(".message").text(msg);
		confirmBox.find(".yes,.no").unbind().click(function() {
			confirmBox.hide();
		});
		confirmBox.find(".yes").click(yesFn);
		confirmBox.find(".no").click(noFn);
		confirmBox.show();
	}

	$(function() {
		$("#deleteComment").click(
				function(e) {
					var link = $(this);
					e.preventDefault();
					doConfirm(
							"¿Estás seguro que quieres eliminar este mensaje?",
							function yes() {
								window.location = link.attr("href");
							});
				});
		$("#addCommentLink").click(function() {

			$("#addComment").show();
		});
	});
</script>

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
	<s:if test="hasActionErrors()">
		<br />
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
					class="com.myproject.calendar.CurrentCalendar" />
				<c:forEach var="row" items="${currentCalendar.currentCalendar}"
					varStatus="rowStatus">
					<tr>
						<c:forEach var="column" items="${row}" varStatus="columnStatus">
							<td class="${column.today ? 'active' : ''}">${column.day}<c:if
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
				<li><s:property value="#comment.user.userFullName" /> <s:property
						value="#comment.commentBody" escape="false" /> <s:date
						name="%{new java.util.Date()}" format="dd/MM/yyyy" var="today" />
					<s:date name="#comment.commentDate" format="dd/MM/yyyy"
						var="commentDate" /> <s:date
						name="%{new java.util.Date(new java.util.Date().getTime() - 60*60*24*1000)}"
						format="dd/MM/yyyy" var="yesterday" /> <s:if
						test="#commentDate == #today">
						hoy -
					</s:if> <s:elseif test="#commentDate == #yesterday">
						ayer -
					</s:elseif> <s:else>
						<s:date name="#comment.commentDate" format="dd/MM/yyyy" /> -
					</s:else> <s:date name="#comment.commentDate" format="HH:mm:ss" /> h <s:if
						test="#session.user.idUser == #comment.user.idUser">
						<a href="editComment">Modificar</a> | <s:a id="deleteComment"
							href="deleteComment?idComment=%{#comment.idComment}">Eliminar</s:a>
					</s:if> <span id="confirmBox"> <span class="message"></span> <span
						class="button yes">Sí</span> <span class="button no">No</span>
				</span></li>
				<br />
			</s:iterator>
		</ol>
		<s:if test="#session.user.isAdmin()">
			<s:form action="addComment" class="addComment">
				<s:textarea name="commentBody" />
				<s:submit value="Añadir Comentario" method="addComment" />
			</s:form>
			<a href="" class="addCommentLink">Añadir comentario</a>
		</s:if>

	</sx:div>


	<br />
	<s:div>
		<s:if test="#session.user.userRole.userRoleName == 'Admin'">
		</s:if>
		<s:elseif test="#session.user.userRole.userRoleName == 'Referee'">
		</s:elseif>
	</s:div>

</body>
</html>
