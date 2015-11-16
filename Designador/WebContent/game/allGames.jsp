<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/game.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
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
			<span>Todos los partidos</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<div class="container">
			<br>	
			<h4 class="new-record"><a href="addGame"><img src="${pageContext.request.contextPath}/images/add-icon.png" class="small-icon">Añadir un partido</a></h4>
			
			<s:if test="#attr.games.size() == 0">
				<br>
				<div class="panel-info">
					<div class="panel-body">
						No hay partidos programados todavía. ¿Quieres <a class="link" href="addGame">Añadir</a> partidos?
					</div>
				</div>
			</s:if>
			<s:else>
				<p>
					<b class="link-2 dark show-all-agmes"> Mostrar Todos Los Partidos (<s:property value="#attr.games.size()"/>)</b>
					<b class="link-2 dark show-future-games"> Mostrar Sólo Los Futuros Partidos  </b>
				</p>
				
				<table class="games">
					<tr>
						<th></th>
						<th colspan="2">Partido</th>
						<th>Fecha</th>	
						<th colspan="4">Grupo Arbitral</th>
						<th></th>
					</tr>
					<s:iterator value="#attr.games">
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
								<div class="row">
									<div><b>Principal:</b></div>
									<div>
										<s:if test="getRefereeGame(1) != null">
											<a class="link" href="${pageContext.request.contextPath}/user/user?idUser=<s:property value="getRefereeGame(1).idUser"/>"> 
												<s:property value="getRefereeGame(1).userProfile.firstName"/>
												<s:property value="getRefereeGame(1).userProfile.lastName1"/>
											</a>
										</s:if>
										<s:else>
											<div class="not-assigned">No designado</div>
										</s:else>
									</div>
								</div>
								<div class="row">
									<div><b>Auxiliar:</b></div>
									<div>
										<s:if test="getRefereeGame(2) != null">
											<a class="link" href="${pageContext.request.contextPath}/user/user?idUser=<s:property value="getRefereeGame(2).idUser"/>">
												<s:property value="getRefereeGame(2).userProfile.firstName"/>
												<s:property value="getRefereeGame(2).userProfile.lastName1"/>
											</a>
										</s:if>
										<s:else>
											<div class="not-assigned" style="display:inline;">No designado</div>
										</s:else>
									</div>
								</div> 
								<div class="row">
									<div><b>Anotador:</b></div>
									<div>
										<s:if test="getRefereeGame(3) != null">
											<a class="link" href="${pageContext.request.contextPath}/user/user?idUser=<s:property value="getRefereeGame(3).idUser"/>"> 
												<s:property value="getRefereeGame(3).userProfile.firstName"/>
												<s:property value="getRefereeGame(3).userProfile.lastName1"/>
											</a>
										</s:if>
										<s:else>
											<div class="not-assigned" style="display:inline;">No designado</div>
										</s:else>
									</div>
								</div>
							</td>
							<td rowspan="2">
								<div class="row">
									<div><b>Cronometrador:</b></div>
									<div>
										<s:if test="getRefereeGame(4) != null">
											<a class="link" href="${pageContext.request.contextPath}/user/user?idUser=<s:property value="getRefereeGame(4).idUser"/>"> 
												<s:property value="getRefereeGame(4).userProfile.firstName"/>
												<s:property value="getRefereeGame(4).userProfile.lastName1"/>
											</a>
										</s:if>
										<s:else>
											<div class="not-assigned" style="display:inline;">No designado</div>
										</s:else>
									</div>
								</div>
								<div class="row">
									<div><b>Operador 30":</b></div>
									<div>
										<s:if test="getRefereeGame(5) != null">
											<a class="link" href="${pageContext.request.contextPath}/user/user?idUser=<s:property value="getRefereeGame(5).idUser"/>"> 
												<s:property value="getRefereeGame(5).userProfile.firstName"/>
												<s:property value="getRefereeGame(5).userProfile.lastName1"/>
											</a>
										</s:if>
										<s:else>
											<div class="not-assigned" style="display:inline;">No designado</div>
										</s:else>
									</div>
								</div>
								<div class="row">
									<div><b>Coche:</b></div>
									<div>
										<s:if test="getRefereeGame(6) != null">
											<a class="link" href="${pageContext.request.contextPath}/user/user?idUser=<s:property value="getRefereeGame(6).idUser"/>"> 
												<s:property value="getRefereeGame(6).userProfile.firstName"/>
												<s:property value="getRefereeGame(6).userProfile.lastName1"/>
											</a>
										</s:if>
										<s:else>
											<div class="not-assigned" style="display:inline;">No designado</div>
										</s:else>
									</div>
								</div>
							</td>
							<td rowspan="2">
								<div><a class="link">Designar</a></div>
								<div><a class="link">Mostrar</a></div>
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
							<td colspan="7"></td>
						</tr>
					</s:iterator>
					
				</table>
			
			</s:else>
			
		</div>
		<jsp:include page="../footer.jsp"/>
		
	</div>
</body>
</html>