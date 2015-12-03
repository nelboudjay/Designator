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
<!--<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script> 
<script src="${pageContext.request.contextPath}/js/datepicker-es.js"></script>-->

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
				<s:if test="#session.user.isAdmin()">
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
				</s:if>
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
						<td colspan="2"><s:property value="game.GameVenue.venueName"/></td>
					</tr>
					
					<s:if test="game.GameVenue.venueAddress != null">
						<tr>
							<td>Dirección</td>
							<td colspan="2"><s:property value="game.GameVenue.venueAddress.fullAddress"/></td>
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
								<s:else>
									<s:if test="game.getRefereeGame(#status.index + 1).user== null">
										<div class="not-assigned">No Designado</div>
									</s:if>
									<s:else>
										<s:if test="!#session.user.isAdmin() && game.getRefereeGame(#status.index + 1).user.idUser != #session.user.idUser 
																	&& game.getRefereeGame(#status.index + 1).user.privacy">
											<s:property value="game.getRefereeGame(#status.index + 1).user.userProfile.firstName"/>
											<s:property value="game.getRefereeGame(#status.index + 1).user.userProfile.lastName1"/>		
										</s:if>
										<s:else>
											<a class="link" href="${pageContext.request.contextPath}/user/user?idUser=<s:property value="game.getRefereeGame(#status.index + 1).user.idUser"/>"> 
												<s:property value="game.getRefereeGame(#status.index + 1).user.userProfile.firstName"/>
												<s:property value="game.getRefereeGame(#status.index + 1).user.userProfile.lastName1"/></a>
										</s:else>
										
										<s:if test="game.getRefereeGame(#status.index + 1).isConfirmed()">
											<img  class="confirmation" title="El árbitro ha confirmado su designación a este partido"
												src="${pageContext.request.contextPath}/images/check-icon.png">
										</s:if>
										<s:else>
											<img  class="confirmation" title="El árbitro aún no ha confirmado su designación para este partido"
												src="${pageContext.request.contextPath}/images/warning-icon.png">
										</s:else>
									</s:else>
									
									<s:if test="#session.user.isAdmin()">
										<div class="select-div" style="display:none">
											<select id="refereeName${status.index + 1}" name="idUsers">
												<option value="0" selected >Elige un árbitro</option>	
												<s:iterator value="referees"  var="referee">
													<option value="${idUser}" 
														<s:if test="game.getRefereeGame(#status.index + 1).user.idUser == idUser">selected</s:if>
													>
														${userProfile.firstName}
														${userProfile.lastName1},
														<s:if test="!game.isAvailable(#referee)">
															N/A
														</s:if>
														<s:elseif test="!#referee.getUserRefereeType(#status.index + 1)">
															H
														</s:elseif>
														<s:elseif test="hasOtherGame(game)">
															O.P
														</s:elseif>
														<s:else>
															OK
														</s:else>
													</option>
												</s:iterator>
											</select>
										</div>	
									</s:if>
								</s:else>
							</div>
							</div>
							
						</s:iterator>
					</td>
					<td>
						<s:if test="#session.user.isAdmin()">
							<div class="conflicts-3">
								Conflictos
								<div class="conflicts-types">
									<div><strong>Tipos de Conflictos</strong></div>
									<div>OK: No hay conflictos</div>
									<div>N/A: No disponible</div>
									<div>O.P: Otro partido</div>
									<div>H: Habilidad</div>
								</div>
							</div>
						</s:if>
					</td>
					
				</tr>
				<s:if test="#session.user.isAdmin()">
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
						<td colspan="2">
							<a class="link-2" href="${pageContext.request.contextPath}/user/user?idUser=${game.lastUpdaterUser.idUser}">
								<s:property value="game.lastUpdaterUser.userProfile.firstName"/>
								<s:property value="game.lastUpdaterUser.userProfile.lastName1"/>
							</a>
						</td>
					</tr>
				</s:if>
				
			</table>
			<p><a href="games" class="link">« Todos los Partidos</a>
				<s:if test="#session.user.idUser != '1'">
					· <a class="link" href="games?idUser=${session.user.idUser}">Mis Partidos</a>		
				</s:if>
				<s:if test="#session.user.isAdmin()">
					· <a class="link assign-2">Designar</a> 
					· <a class="link" href="addEditGame?idGame=${idGame}">Editar</a> 
					. <a class="link delete" href="deleteGame?idGame=${idGame}">Eliminar</a>
					<span class="confirm-box">
						<span class="message">¿Estás seguro que quieres
							eliminar este partido? </span> <span class="btn yes">Sí</span> 
							<span class="btn no">No</span>
					</span>	 
				</s:if>
			</p>
			<p class="save-assignment-2">
				
						<input type="submit" value="Guardar" class="btn save">
						<span><a class="link-2 cancel-2">Cancelar</a></span>
			
			</p>
		</div>
		<jsp:include page="../footer.jsp"/>
		
	</div>

</body>
</html>