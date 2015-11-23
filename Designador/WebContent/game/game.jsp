<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/game.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>

<title><s:property value="game.HomeTeam.TeamName"/> vs <s:property value="game.AwayTeam.TeamName"/></title>

<s:head />
</head>
<body>
	<jsp:include page="../header.jsp"/>

	<jsp:include page="../leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="${pageContext.request.contextPath}/images/game-black-icon.png">
				Partidos
			</h3>
			<span><b><s:property value="game.HomeTeam.TeamName"/></b> vs <b><s:property value="game.AwayTeam.TeamName"/></b></span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<br/>
		
		<div class="container">
				

			<div class="game-menu">
				<s:date name="game.GameDate" format="EEEE" var="gameDay"/>
				<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#gameDay)"/>, 
				<s:date name="game.GameDate" format="d"/> de 
				<s:date name="game.GameDate" format="MMMM" var="gameMonth"/>
				<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#gameMonth)"/> de 
				<s:date name="game.GameDate" format="yyyy"/> a las
				<s:date name="game.GameDate" format="HH:mm"/> horas en
				<s:property value="game.gameVenue.venueName"/>
			</div>
			
			<br/>
			
			<table class="game-info">
				<tr>
					<th colspan="3">
						<a href="${pageContext.request.contextPath}/team/addEditTeam?idTeam=${game.homeTeam.idTeam}"><b class="title-2"><s:property value="game.HomeTeam.TeamName"/></b></a>
						 vs 
						<a href="${pageContext.request.contextPath}/team/addEditTeam?idTeam=${game.awayTeam.idTeam}"><b class="title-2"><s:property value="game.AwayTeam.TeamName"/></b></a>
					</th>
				</tr>
				
				<tr>
					<td>Estado</td>
					<td colspan="2">
						<s:if test="game.isGameStatus()">
							<span class="success" title="El partido ya está publicado.">Publicado</span>
						</s:if>
						<s:else>
							<span class="warning" title="El partido aún no está publicado.">No Publicado</span>
						</s:else>
					</td>
				</tr>
				<s:if test="game.GameCategory != null">
					<tr>
						<td>Categoría</td>
						<td colspan="2"><s:property value="game.GameCategory.CategoryName"/> <s:property value="game.GameCategory.CategoryGenderName"/></td>
					</tr>
				</s:if>
				<s:if test="game.GameLeague != null">
					<tr>
						<td>Competición</td>
						<td colspan="2"><s:property value="game.GameLeague.LeagueName"/></td>
					</tr>
				</s:if>
				<s:if test="game.GameVenue != null">
					<tr>
						<td>Pista</td>
						<td colspan="2"><a class="link-2" href="${pageContext.request.contextPath}/venue/addEditVenue?idVenue=${game.gameVenue.idVenue}"><s:property value="game.GameVenue.venueName"/></a></td>
					</tr>
					
					<s:if test="game.GameVenue.venueAddress.city != ''">
						<tr>
							<td>Localidad</td>
							<td colspan="2"><s:property value="game.GameVenue.venueAddress.City"/></td>
						</tr>
					</s:if>
				</s:if>
				<tr>
					<td>Equipo Arbitral</td>
					<td>
						<s:iterator value="{'Principal','Auxiliar','Anotador','Cronometrador','Operador 30\"','Coche'}"  status="status">
									
							<div class="row">
							<div><b><s:property/>:</b></div>
							<div>
								<s:if test="game.getRefereeGame(#status.index + 1) == null">
									<div class="not-required">No Requerido</div>
								</s:if>
								<s:elseif test="game.getRefereeGame(#status.index + 1).userRefereeType == null">
									<div class="not-assigned">No Designado</div>
								</s:elseif>
								<s:else>
									<a class="link" href="${pageContext.request.contextPath}/user/user?idUser=<s:property value="game.getRefereeGame(#status.index + 1).userRefereeType.user.idUser"/>"> 
										<s:property value="game.getRefereeGame(#status.index + 1).userRefereeType.user.userProfile.firstName"/>
										<s:property value="game.getRefereeGame(#status.index + 1).userRefereeType.user.userProfile.lastName1"/></a>
									<s:if test="game.getRefereeGame(#status.index + 1).isConfirmed()">
										<img  class="confirmation" title="El árbitro ha confirmado su designación a este partido"
											src="${pageContext.request.contextPath}/images/check-icon.png">
									</s:if>
									<s:else>
										<img  class="confirmation" title="El árbitro aún no ha confirmado su designación a este partido"
											src="${pageContext.request.contextPath}/images/warning-icon.png">
									</s:else>
								</s:else>
							</div>
							</div>
							
						</s:iterator>
					</td>
				</tr>
				<tr>
					<td>Última Actualización</td>
					<td colspan="2">
						<s:date name="game.lastUpdatedDate" format="EEE" var="lastUpdatedDay"/>
						<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#lastUpdatedDay)"/>, 
						<s:date name="game.lastUpdatedDate" format="d"/> de 
						<s:date name="game.lastUpdatedDate" format="MMM" var="lastUpdatedDayMonth"/>
						<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#lastUpdatedDayMonth)"/> de 
						<s:date name="game.lastUpdatedDate" format="yyyy"/> a las
						<s:date name="game.lastUpdatedDate" format="HH:mm"/> horas 
										
					</td>
				</tr>
				<tr>
					<td>Autor</td>
					<td>
						<a class="link-2" href="${pageContext.request.contextPath}/user/user?idUser=${game.lastUpdaterUser.idUser}">
							<s:property value="game.lastUpdaterUser.userProfile.firstName"/>
							<s:property value="game.lastUpdaterUser.userProfile.lastName1"/>
						</a>
					</td>
				</tr>
			</table>
			<p><a href="games" class="link">« Todos los Partidos</a>
					· <a class="link" href="addEditGame?idGame=${idGame}">Editar</a> · 
					<a class="link delete" href="deleteGame?idGame=${idGame}">Eliminar</a>
					<span class="confirm-box">
						<span class="message">¿Estás seguro que quieres
							eliminar este partido? </span> <span class="btn yes">Sí</span> 
							<span class="btn no">No</span>
					</span>	 
					
				
			</p>
		</div>
		<jsp:include page="../footer.jsp"/>
		
	</div>

</body>
</html>