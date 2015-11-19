<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/game.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/datepicker-es.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/game.js"></script>

<title>Partidos</title>

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
			<span>
				<s:if test="is == 'unassigned'">
					Partidos No Asignados
				</s:if>
				<s:elseif test="is == 'unpublished'">
					Partidos No Publicados
				</s:elseif>
				<s:else>
					Todos los partidos
				</s:else>
			</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<div class="container">
			<br>	
			<h4 class="new-record"><a href="addGame"><img src="${pageContext.request.contextPath}/images/add-icon.png" class="small-icon">Añadir un partido</a></h4>
			
			<s:if test="#attr.allGames.size() == 0">
				<br>
				<div class="panel-info">
					<div class="panel-body">
						No hay partidos programados todavía. ¿Quieres <a class="link" href="addGame">Añadir</a> nuevos partidos?
					</div>
				</div>
			</s:if>
			<s:else>
				<p>
					<b>
					<s:if test="dateStr != 'all'">
						<s:set var="usedGames" value="#attr.games"/>
						<a href="games?dateStr=all&is=${is}" class="link dark"> Mostrar Todos Los Partidos (<s:property value="#attr.allGames.size()"/>)</a>
					</s:if>
					<s:else>
						<s:set var="usedGames" value="#attr.allGames"/>
						<s:if test="#attr.games.size() > 0">
							<a href="games?is=${is}" class="link-2 dark"> Mostrar Sólo Los Futuros Partidos (<s:property value="#attr.games.size()"/>)</a>
						</s:if>
					</s:else>
					</b>
				</p>
				<form action="games" method="get" >
					<div>
						<label><b>Fecha: </b></label>
						<input type="text" id="datepicker" name="dateStr" value="${dateStr != 'all' ? dateStr : ''}">
						<input type="hidden" name="is" value="${is}">
						<input type="submit" class="btn" value="Mostrar Partidos" >
					</div>
				</form>
				<s:if test="#usedGames.size() == 0">
					<div class="panel-info">
						<div class="panel-body">
							<s:if test="dateStr == null || dateStr == ''">
								No hay partidos programados que coinciden con tus criterios. ¿Quieres <a class="link" href="addGame">Añadir</a> nuevos partidos?
							</s:if>
							<s:else>
								No hay partidos programados para esta fecha.
							</s:else>
						</div>
					</div>
					
				</s:if>
				<s:else>
				<table class="games">
					<tr>
						<th></th>
						<th colspan="2">Partido</th>
						<th>Fecha</th>	
						<th colspan="2">Equipo Arbitral</th>
						<th></th>
					</tr>
					<s:iterator value="#usedGames">
						<tr id="${idGame}">
							<td>
								<a class="link publish-game" style="display:${gameStatus ? 'none' : 'block'};">Publicar Partido</a>
								<div class="published" style="display:${gameStatus ? 'block' : 'none'};">Publicado</div>
							</td>
							<td colspan="2">
								<div><s:property value="homeTeam.teamName"/></div> 
								<span>vs</span> 
								<div><s:property value="awayTeam.teamName"/></div>
							</td>
							<td>
								<div>
									<s:date name="GameDate" format="EEE" var="gameDay"/>
									<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#gameDay)"/>, 
									<s:date name="GameDate" format="d"/> de 
									<s:date name="GameDate" format="MMM" var="gameMonth"/>
									<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#gameMonth)"/> de 
									<s:date name="GameDate" format="yyyy"/>
									<br>
									<b>Hora:</b> <s:date name="GameDate" format="HH:mm"/>
								</div>
							</td>
							<td rowspan="2">
							<s:iterator value="{'Principal','Auxiliar','Anotador','Cronometrador','Operador 30\"','Coche'}"  status="status">
								
								<s:if test="#status.index == 3">
									<td rowspan="2">
								</s:if>
									<div class="row">
									<div><b><s:property/>:</b></div>
									<div>
										<s:if test="getRefereeGame(#status.index + 1) == null">
											<div class="not-required">No Requerido</div>
										</s:if>
										<s:elseif test="getRefereeGame(#status.index + 1).userRefereeType == null">
											<div class="not-assigned">No Designado</div>
										</s:elseif>
										<s:else>
											<a class="link" href="${pageContext.request.contextPath}/user/user?idUser=<s:property value="getRefereeGame(#status.index + 1).userRefereeType.user.idUser"/>"> 
												<s:property value="getRefereeGame(#status.index + 1).userRefereeType.user.userProfile.firstName"/>
												<s:property value="getRefereeGame(#status.index + 1).userRefereeType.user.userProfile.lastName1"/></a>
											<s:if test="getRefereeGame(#status.index + 1).isConfirmed()">
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
							<td rowspan="2">
								<div><a class="link">Designar</a></div>
								<div><a href="game?idGame=${idGame}" class="link">Mostrar</a></div>
								<div><a class="link">Editar</a></div>
								<div><a class="link">Eliminar</a></div>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<div>	
									<b>Competición:</b> <s:property value="gameLeague.leagueName"/>
									<br>
									<b>Localidad:</b> <s:property value="gameVenue.venueAddress.city"/>
								</div>
							</td>
							<td>
								<div>
									<b>Categoría:</b> <s:property value="gameCategory.categoryName"/> <s:property value="gameCategory.getCategoryGenderShortName()"/>
									<br>
									<b>Pista:</b> <a  href="${pageContext.request.contextPath}/venue/addEditVenue?idVenue=${gameVenue.idVenue}" class="link"><s:property value="gameVenue.venueName"/></a>
								</div>
							</td>
							<td colspan="4"></td>
						</tr>
					</s:iterator>
					
				</table>
				</s:else>
			</s:else>
			
		</div>
		<jsp:include page="../footer.jsp"/>
		
	</div>
</body>
</html>