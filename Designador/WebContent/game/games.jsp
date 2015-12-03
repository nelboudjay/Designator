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
<script type="text/javascript">
	var is = "${is}";
	var dateStr = "${dateStr}";
	var idUser = "${idUser}";
</script>
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
					Partidos No Designados
				</s:if>
				<s:elseif test="is == 'assigned'">
					Partidos Dessignados
				</s:elseif>
				<s:elseif test="is == 'unpublished'">
					Partidos No Publicados
				</s:elseif>
				<s:elseif test="is == 'published'">
					Partidos Publicados
				</s:elseif>
				<s:elseif test="is == 'unconfirmed'">
					Partidos No Confirmados
				</s:elseif>
				<s:elseif test="is == 'confirmed'">
					Partidos Confirmados
				</s:elseif>
				<s:else>
					Todos los partidos
				</s:else>
			</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<div class="container">
			<br>	
			
			<s:if test="#session.user.isAdmin()">
				<h4 class="new-record"><a href="addEditGame"><img src="${pageContext.request.contextPath}/images/add-icon.png" class="small-icon">Añadir un partido</a></h4>
			</s:if>
			<s:if test="#attr.allGames.size() == 0 && (is == null || is == '')">
				<br>
				<div class="panel-info">
					<div class="panel-body">
						<s:if test="idUser != null">
							No hay partidos designados todavía para  ${idUser == session.user.idUser ? 'tí' : 'este árbitro'} 
							<s:if test="#session.user.isAdmin()">
								¿Quieres <a class="link" href="addEditGame">Añadir</a> nuevos partidos?
							</s:if>
						</s:if>
						<s:else>
							No hay partidos programdos todavía 
							<s:if test="#session.user.isAdmin()">
								¿Quieres <a class="link" href="addEditGame">Añadir</a> nuevos partidos?
							</s:if>
						</s:else>
					</div>
				</div>
			</s:if>
			<s:else>
				<p>
					<b>
					<s:if test="dateStr != 'all'">
						<s:set var="usedGames" value="#attr.games"/>
						<a href="games?dateStr=all&is=${is}<s:if test="idUser != null">&idUser=${idUser}</s:if>" 
								class="link dark"> Mostrar Todos Los Partidos (<s:property value="#attr.allGames.size()"/>)</a>
					</s:if>
					
					<s:else>
						<s:set var="usedGames" value="#attr.allGames"/>
						<s:if test="#attr.games.size() > 0">
							<a href="games?is=${is}<s:if test="idUser != null">&idUser=${idUser}</s:if>" class="link-2 dark"> Mostrar Sólo Los Futuros Partidos (<s:property value="#attr.games.size()"/>)</a>
						</s:if>
					</s:else>
					</b>
				</p>
				<form action="games" method="get" >
					<div>
						<label><b>Fecha: </b></label>
						<input type="text" id="datepicker" name="dateStr" value="${dateStr != 'all' ? dateStr : ''}">
						<input type="hidden" name="is" value="${is}">
						<s:if test="idUser != null">
							<input type="hidden" name="idUser" value="${idUser}">
						</s:if>
						<input type="submit" class="btn" value="Mostrar Partidos" >
					</div>
				</form>
				<s:if test="#usedGames.size() == 0">
					<div class="panel-info">
						<div class="panel-body">
							<s:if test="idUser != null">
								No hay partidos designados para ${idUser == session.user.idUser ? 'tí' : 'este árbitro'} 
								que coinciden con tus criterios. 
								<s:if test="#session.user.isAdmin()">	
									¿Quieres <a class="link" href="addEditGame">Añadir</a> nuevos partidos?
								</s:if>
							</s:if>
							<s:else>
								No hay partidos programados que coinciden con tus criterios. 
								<s:if test="#session.user.isAdmin()">	
									¿Quieres <a class="link" href="addEditGame">Añadir</a> nuevos partidos?
								</s:if>							
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
					<s:iterator value="#usedGames" var="game">
						<tr id="${idGame}">
							<td>
								<s:if test="#session.user.isAdmin()">
									<a class="link publish-game" style="display:${gameStatus ? 'none' : 'block'};">Publicar Partido</a>
									<div class="published" style="display:${gameStatus ? 'block' : 'none'};">Publicado</div>
								</s:if>
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
							<div class="table">
							<s:iterator value="{'Principal','Auxiliar','Anotador','Cronometrador','Operador 30\"','Coche'}"  status="status">
								
								<s:if test="#status.index == 3">
									<td rowspan="2">
									 <div class="table">
								</s:if>
									<div class="row">
									<div><b><s:property/>:</b></div>									
									<div>
										<s:if test="getRefereeGame(#status.index + 1) == null">
											<div class="not-required">No Requerido</div>
										</s:if>
										<s:elseif test="getRefereeGame(#status.index + 1).user == null">
											<div class="not-assigned">No Designado</div>
											<s:if test="#session.user.isAdmin()">
												<div class="select-div" style="display:none">
													<select id="refereeName${status.index + 1}" name="idUsers">
														<option value="0" selected >Elige un árbitro</option>	
														<s:iterator value="referees" var="referee">
															<option value="${idUser}">
																${userProfile.firstName}
																${userProfile.lastName1},
																<s:if test="!isAvailable(#referee)">
																	N/A
																</s:if>
																<s:elseif test="!#referee.getUserRefereeType(#status.index + 1)">
																	H
																</s:elseif>
																<s:elseif test="hasOtherGame(#game)">
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
										</s:elseif>
										<s:else>
											<div style="white-space:nowrap;">
											<s:if test="!#session.user.isAdmin() && getRefereeGame(#status.index + 1).user.idUser != #session.user.idUser 
																&& getRefereeGame(#status.index + 1).user.privacy">
												<s:property value="getRefereeGame(#status.index + 1).user.userProfile.firstName"/>
												<s:property value="getRefereeGame(#status.index + 1).user.userProfile.lastName1"/>
											</s:if>
											<s:else>
												<a class="link" href="${pageContext.request.contextPath}/user/user?idUser=<s:property value="getRefereeGame(#status.index + 1).user.idUser"/>"> 
													<s:property value="getRefereeGame(#status.index + 1).user.userProfile.firstName"/>
													<s:property value="getRefereeGame(#status.index + 1).user.userProfile.lastName1"/></a>				
											</s:else>
											<s:if test="getRefereeGame(#status.index + 1).isConfirmed()">
												<img  class="confirmation" title="El árbitro ha confirmado su designación para este partido"
													src="${pageContext.request.contextPath}/images/check-icon.png">
											</s:if>
											<s:else>
												<img  class="confirmation" title="El árbitro aún no ha confirmado su designación a este partido"
													src="${pageContext.request.contextPath}/images/warning-icon.png">
											</s:else>
											</div>
											<s:if test="#session.user.isAdmin()">
												<div class="select-div" style="display:none">
													<select id="refereeName${status.index + 1}" name="idUsers">
														<option value="0" selected >Elige un árbitro</option>	
														<s:iterator value="referees">
															<option value="${idUser}" 
																<s:if test="getRefereeGame(#status.index + 1).user.idUser == idUser">selected</s:if>
															>
																${userProfile.firstName}
																${userProfile.lastName1}, 
																<s:if test="!isAvailable(#referee)">
																	N/A
																</s:if>
																<s:elseif test="!#referee.getUserRefereeType(#status.index + 1)">
																	H
																</s:elseif>
																<s:elseif test="hasOtherGame(#game)">
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
								</div>
							</td>
							<td rowspan="2">
								<s:if test="#session.user.isAdmin()">
									<div class="conflicts">
										Conflicts
										<div class="conflicts-types">
											<div><strong>Tipos de Conflictos</strong></div>
											<div>OK: No hay conflictos</div>
											<div>N/A: No disponible</div>
											<div>O.P: Otro partido</div>
											<div>H: Habilidad</div>
										</div>
									</div>
									<div class="assign">
										<a class="link-2">Designar</a>
									</div>
								</s:if>
								<div><a href="game?idGame=${idGame}" class="link">Mostrar</a></div>
								<s:if test="#session.user.isAdmin()">
									<div><a class="link" href="addEditGame?idGame=${idGame}">Editar</a></div>
									<div class="games-list">
										<a class="link delete" href="deleteGame?idGame=${idGame}">Eliminar</a>
										
										<span class="confirm-box" >
											<span class="message"> Estás seguro que quieres
												eliminar este partido? </span> <span class="btn yes">Sí</span> 
												<span class="btn no">No</span>
										</span>	
									</div>
								</s:if>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div>	
									<b>Competición:</b> <s:property value="gameLeague.leagueName"/>
									<br>
									<b>Localidad:</b> <s:property value="gameVenue.venueAddress.city"/>
								</div>
							</td>
							<td colspan="2">
								<div>
									<b>Categoría:</b> <s:property value="gameCategory.categoryName"/> <s:property value="gameCategory.getCategoryGenderShortName()"/>
									<br>
									<b>Pista:</b> <s:property value="gameVenue.venueName"/>
								</div>
							</td>
						</tr>
						<tr class="save-assignment">
							<td colspan="4"></td>
							<td colspan="3">
								<input type="submit" value="Guardar" class="btn save">
								<span><a class="link-2 cancel">Cancelar</a></span>
							</td>
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