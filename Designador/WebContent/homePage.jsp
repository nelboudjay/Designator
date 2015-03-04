<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/MyStyle.css" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.js"></script>


<script type="text/javascript">
	function doConfirm(confirmBox, msg, yesFn, noFn) {

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

		$("[id^=deleteComment_]").click(
				function(e) {
					var comment = $(this).parent().parent();
					var link = $(this);
					var confirmBox = link.next();
					e.preventDefault();
					doConfirm(confirmBox,
							"¿Estás seguro que quieres eliminar este mensaje?",
							function yes() {
								$.ajax({
									url : link.attr("href"),
								});
								comment.remove();
							});
				});

		$("#cancelCommentLink").click(function() {

			$("#addCommentSubmit").css("display", "none");
			$("#cancelCommentLink").css("display", "none");
			$('#commentBody').animate({
				height : "2em"
			});
		});

		$('#commentBody').focus(function() {
			$(this).animate({
				height : "4em"
			});
			$("#addCommentSubmit").css("display", "inline");
			$("#cancelCommentLink").css("display", "inline");

		});

	});
</script>

<title>Página de Inicio</title>
<s:head />
<sx:head />

</head>
<body>

	<%@ include file="idle-timeout.html"%>

	<nav>
		<ul class="utility">
			<li><a class="userName" href="homePage">${session.userFullName}
					(${session.user.userName}) <span class="arrow"></span>
			</a>
			<ul>
			<li class="tito"><a>Profile</a></li>
			</ul>
			</li>
		</ul>
		<!--   | <a href="logout">Cerrar sesión</a> | <a
			href="passwordForgot">Mensajes</a> | <a
			href="passwordResetInstructions">Ayuda</a> -->
		<br />
	</nav>
	<s:if test="hasActionErrors()">
		<br />
		<div class="errors">
			<s:actionerror />
		</div>
	</s:if>
	<s:if test="hasActionMessages()">
		<div class="boxMessage">
			<s:actionmessage />
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
		<h2 id="comments">Comentarios</h2>
		<s:if test="#session.user.isAdmin()">

			<s:form action="addComment">
				<s:textarea id="commentBody" rows="1" cols="100"
					placeholder="Añadir comentario..." name="commentBody" />
				<s:submit id="addCommentSubmit" align="left"
					value="Añadir Comentario">
					<s:a href="" id="cancelCommentLink">Cancelar</s:a>
				</s:submit>

			</s:form>

		</s:if>

		<ol>
			<s:iterator value="#session.comments" var="comment" step="-1"
				begin="#session.comments.size -1" status="status">
				<s:div id="comment_%{#status.count}">
					<li><s:property value="#comment.user.userFullName" />
						<p>
							<s:property value="#comment.commentBody" escape="false" />
						</p> <s:date name="%{new java.util.Date()}" format="dd/MM/yyyy"
							var="today" /> <s:date name="#comment.commentDate"
							format="dd/MM/yyyy" var="commentDate" /> <s:date
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
							<a href="editComment">Modificar</a> | <s:a
								id="deleteComment_%{#status.count}"
								href="deleteComment?idComment=%{#comment.idComment}">Eliminar</s:a>
						</s:if> <s:div id="confirmBox_%{#status.count}">
							<span class="message"></span>
							<span class="button yes">Sí</span>
							<span class="button no">No</span>
						</s:div></li>
				</s:div>
				<br />
			</s:iterator>
		</ol>

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
