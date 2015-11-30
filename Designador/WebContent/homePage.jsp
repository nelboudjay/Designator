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

<title>Designator</title>

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
			<s:if test="#session.user.isAdmin()">
				<p>¿Quieres <a class="link" href="game/addEditGame">Añadir</a> nuevos partidos?</p>
			</s:if>
			
	
			<table class="calendar">
				<thead>
					<tr>
						<s:iterator
							value="{'Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo'}">
							<th><s:property/></th>
						</s:iterator>
					</tr>
				</thead>
				<tbody>
					<jsp:useBean id="currentCalendar"
						class="com.myproject.calendar.CurrentCalendar" />
					<c:forEach var="row" items="${currentCalendar.currentCalendar}" varStatus="rowStatus">
						<tr>
							<c:forEach var="column" items="${row}" varStatus="columnStatus">
								<td
									${column.today == 0 ? 'class=\"active\"' : column.today > 0 ? 'class=\"future\"' : ''}>
									<div>${column.day}
										<c:if
											test="${rowStatus.index == 0 && columnStatus.index == 0
															|| column.day == 1}">
											de ${column.monthName} 
										</c:if>
									</div>
									
									<s:set var="counter" value="0"/>
									<s:set var="currentDate" >${column.year}-${column.month}-${column.day}</s:set>
									<s:iterator value="#session.futureGames">
										<s:set var="gameDate"><s:date name="gameDate" format="yyyy-MM-d" /></s:set>
										<s:if test='#currentDate == #gameDate'>			
											<s:if test="#counter < 2">
												<div>
													<a class="link-${column.today == 0 ? '4' : '3' }" 
														href="${pageContext.request.contextPath}/game/game?idGame=${idGame}">
												 	
														<s:property value="homeTeam.TeamShortName"/> vs 
														<s:property value="awayTeam.TeamShortName"/> - 
														<s:date name="gameDate" format="HH:mm"/>
											 		</a>
											 	</div>
										 	</s:if>
										 	<s:set var="counter" value="%{#counter+1}"/>
										 </s:if>	
									</s:iterator>
									<s:if test="#counter > 2">
										<div>
											<a class="link-${column.today == 0 ? '4' : '3' }" 
												href="${pageContext.request.contextPath}/game/games?dateStr=${gameDate}">
										 		${counter - 2} más...
									 		</a>
									 	</div>
									</s:if>
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
										<s:property value="#comment.commentBody.replaceAll('(\r\n|\n)', '<br/>')" escape="false" />
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
		
		<jsp:include page="footer.jsp"/>
		
	</div>


</body>
</html>
