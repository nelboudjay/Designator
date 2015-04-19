<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
<link rel="stylesheet" type="text/css" href="css/MyStyle.css" />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,800">

<script type="text/javascript" src="js/jquery.js"></script>


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

	function minimazeTextarea() {
		$('#commentBody').animate({
			height : "2em"
		}, "normal");
		$(".addCommentSubmit").fadeOut("slow");
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

		$("#cancelCommentLink").click(minimazeTextarea);

		$("#commentBody").blur(minimazeTextarea);

		$('#commentBody').focus(function() {
			$(this).animate({
				height : "4em"
			}, "normal");
			$(".addCommentSubmit").fadeIn("slow");

		});

		$('#userName').click(function(evt) {
			evt.stopPropagation();
			$('.dropdown-menu').slideToggle("drop");
			$(this).toggleClass("userName");
		});

		$(document).click(function() {
			$('.dropdown-menu').slideUp(function() {
				$('#userName').removeClass("userName");
			});
		});

		$('#leftMenu li').click(function() {
			$('#leftMenu li').removeClass("active");
			$(this).addClass("active");
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
		<ul class="logo">
			<li><a href="homePage"><img src="images/Logo.png"
					width="12%" height="90%"></a></li>
		</ul>
		<ul class="navbar-right">
			<li id="userName"><span><img class="profile-icon"
					src="images/profile-icon.png">${session.userFullName}
					(${session.user.userName}) <span class="arrow"></span> </span></li>
			<li id="profile" class="dropdown-menu"><a>Perfil</a></li>
			<li id="messages" class="dropdown-menu"><a>Mensajes</a></li>
			<li id="logout" class="dropdown-menu"><a href="logout">Cerrar
					sesión</a></li>

		</ul>
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

		<div class="comments">
			<h4 id="comments">Comentarios</h4>

			<s:if test="#session.user.isAdmin()">

				<form action="/Designador/addComment" method="post">
					<s:textarea id="commentBody" class="placeholder" rows="1"
						cols="100" placeholder="Añadir comentario..." name="commentBody" />

					<p class="addCommentSubmit">
						<input type="submit" class="btn" value="Añadir Comentario">
						<a class="cancelCommentLink">Cancelar</a>
					</p>
				</form>
			</s:if>

				<ul class="comments-list">
					<s:iterator value="#session.comments" var="comment" step="-1"
						begin="#session.comments.size -1" status="status">
						<li><s:div id="comment_%{#status.count}">
									<img src="images/profile-icon.png" class="avatar">
									<span class="arrow-left"></span>
									<div class="info">
										<span class="name"><s:property
												value="#comment.user.userFullName" /></span> <span class="time">
											now</span>
									</div>
								<p>
									<s:property value="#comment.commentBody" escape="false" />
								</p>
								<s:date name="%{new java.util.Date()}" format="dd/MM/yyyy"
									var="today" />
								<s:date name="#comment.commentDate" format="dd/MM/yyyy"
									var="commentDate" />
								<s:date
									name="%{new java.util.Date(new java.util.Date().getTime() - 60*60*24*1000)}"
									format="dd/MM/yyyy" var="yesterday" />
								<s:if test="#commentDate == #today">
						hoy -
					</s:if>
								<s:elseif test="#commentDate == #yesterday">
						ayer -
					</s:elseif>
								<s:else>
									<s:date name="#comment.commentDate" format="dd/MM/yyyy" /> -
					</s:else>
								<s:date name="#comment.commentDate" format="HH:mm:ss" />
							h
							<s:if test="#session.user.idUser == #comment.user.idUser">
									<a href="editComment">Modificar</a> | <s:a
										id="deleteComment_%{#status.count}"
										href="deleteComment?idComment=%{#comment.idComment}">Eliminar</s:a>
								</s:if>
								<s:div id="confirmBox_%{#status.count}">
									<span class="message"></span>
									<span class="button yes">Sí</span>
									<span class="button no">No</span>
								</s:div>
							</s:div></li>

						<br />
					</s:iterator>
				</ul>

		</div>

	</div>


</body>
</html>
