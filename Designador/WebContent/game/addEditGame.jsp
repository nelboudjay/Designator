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


<title>
	<s:if test="idGame != null && idGame != ''">
		<s:property value="homeTeamName"/> vs <s:property value="awayTeamName"/>
	</s:if>
	<s:else>
		Añadir Partidos
	</s:else>
</title>

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
				<s:if test="idGame != null && idGame != ''">
					<b><s:property value="homeTeamName"/></b> vs <b><s:property value="awayTeamName"/></b>
				</s:if>
				<s:else>
					Nuevo Partido
				</s:else>
			</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<div class="container">
			<br/>
			<form id="gameForm" action="addEditGame" method="post" >
			
				<div><input name="idGame" value="${idGame}" type="hidden"></div>
			
				<div class="row">
					<div>
						<label class="required"><strong>Fecha</strong></label> 
						<input id="datepicker" type="text" class="text-input-2 required-field" name="dateStr" 
								value="${dateStr}">
						<div class="error-field">La fecha no puede estar en blanco.</div>	
					</div>
					<div>
						<label class="required"><strong>Hora</strong></label> 
						<input id="gameTime" placeholder="Introduce la hora con el siguiente formato HH:MM" 
							type="text" class="text-input-2 required-field22" name="timeStr" 
								value="${timeStr}">
						<div class="error-field">La hora no puede estar en blanco.</div>	
					</div>
					 <div></div> 
				</div>
				
				<div class="row">
					
					<div>
						<label class="required"><strong>Equipo Local</strong></label> 
						<div class="select-div">
							<select id="homeTeam" class="required-field" name="idHomeTeam">
								<option selected disabled>Elige el equipo local</option>
								<s:iterator value="teams">
									<option value="${idTeam}" ${idHomeTeam == idTeam ? 'selected' : ''}>${teamName}</option>
								</s:iterator>
								<option value="0" ${idHomeTeam == '0' ? 'selected' : ''}>Nuevo Equipo</option>
							</select>
						</div>
						<input style="display:${idHomeTeam != '0' ? 'none' : ''};" id="homeTeamName" placeholder="Introduce aquí el nombre del equipo"
							type="text" class="text-input-2 required-field" name="homeTeamName" value="${homeTeamName}">
						<div class="error-field">El equipo local no puede estar en blanco</div>	
								
					</div>
					
					<div>
						<label class="required"><strong>Equipo Visitante</strong></label> 
						<div class="select-div">
							<select id="awayTeam" class="required-field" name="idAwayTeam">
								<option selected disabled>Elige el equipo visitante</option>
								<s:iterator value="teams">
									<option value="${idTeam}" ${idAwayTeam == idTeam ? 'selected' : ''}>${teamName}</option>
								</s:iterator>
								<option value="0" ${idAwayTeam == '0' ? 'selected' : ''}>Nuevo Equipo</option>
							</select>
						</div>
						<input style="display:${idAwayTeam != '0' ? 'none' : ''};" id="awayTeamName" placeholder="Introduce aquí el nombre del equipo"
							type="text" class="text-input-2 required-field" name="awayTeamName" value="${awayTeamName}">
						<div class="error-field">El equipo visitante no puede estar en blanco</div>	
						<div class="error-field-2">Los dos equipos no deben ser iguales</div>		
							
					</div>
				
					<div></div>
				</div>
				
				<div class="row">	
					<div>
						<label><strong>Categoría</strong></label> 
						<div class="select-div">
							<select id="category" name="idCategory">
								<option selected >Elige la categoría</option>
								<s:set var="currentIdCategory" value="idCategory"/>
								<s:iterator value="categories" >
									<option value="${idCategory}" ${idCategory == currentIdCategory ? 'selected' : ''}>${categoryName} ${categoryGenderName}</option>
								</s:iterator>
								<option value="0" ${idCategory == '0' ? 'selected' : ''}>Nueva Categoría</option>
							</select>
						</div>
						<input style="display:${idCategory != '0' ? 'none' : ''};" id="categoryName" placeholder="Introduce aquí el nombre de la categoría"
							type="text" class="text-input-2" name="categoryName" value="${categoryName}">
						<div style="display:${idCategory != '0' ? 'none' : ''};" class="select-div">
							<select id="categoryGender" name="categoryGender">
							    <option selected value="0">Elige el sexo</option>
								<option value="1" ${categoryGender == 1 ? 'selected' : ''}>Masculino</option>
								<option value="2" ${categoryGender == 2 ? 'selected' : ''}>Femenino</option>
								<option value="3" ${categoryGender == 3 ? 'selected' : ''}>Mixto</option>
							</select>
						</div>	
						
					</div>
					
					<div>
						<label><strong>Competición</strong></label> 
						<div class="select-div">
							<select id="league" name="idLeague">
								<option selected >Elige la competición</option>
								<s:set var="currentIdLeague" value="idLeague"/>
								<s:iterator value="leagues">
									<option value="${idLeague}" ${idLeague == currentIdLeague ? 'selected' : ''}>${leagueName}</option>
								</s:iterator>
								<option value="0" ${idLeague == '0' ? 'selected' : ''}>Nueva Competición</option>
							</select>
						</div>
						<input style="display:${idLeague != '0' ? 'none' : ''};" id="leagueName" placeholder="Introduce aquí el nombre de la competición"
							type="text" class="text-input-2" name="leagueName" value="${leagueName}">
						<div class="error-field"></div>			
					</div>
					
					 <div>
						<label><strong>Pista</strong></label> 
						<div class="select-div">
							<select id="venueName" name="idVenue">
								<option selected >Elige la pista</option>
								<s:set var="currentIdVenue" value="idVenue"/>								
								<s:iterator value="venues">
									<option value="${idVenue}" ${idVenue == currentIdVenue ? 'selected' : ''}>${venueName}</option>
								</s:iterator>
								<option value="0" ${idVenue == '0' ? 'selected' : ''}>Nueva Pista</option>
							</select>
						</div>
						<input style="display:${idVenue != '0' ? 'none' : ''};" id="venueName" placeholder="Introduce aquí el nombre de la pista"
							type="text" class="text-input-2" name="venueName" value="${venueName}">
						<div class="error-field"></div>			
					</div>
				</div>
			
				<div class="row">
					<div>
						<label><b>¿Publicado?</b></label>
						<input id="gameStatus" type="checkbox" name="gameStatus" value="true" ${gameStatus ? 'checked' : ''}>
					</div>		
				</div>
				
				<h3 class="title-1">Equipo Arbitral </h3>
				
				<div class="row">
				<s:iterator value="{'Principal','Auxiliar','Anotador','Cronometrador','Operador 30\"','Coche'}"  
					status="status">
					${status.index == 3 ? '<div>
						</div>
						</div>
						<div class="row">' : '' }
					
					<div>
						<label><strong><s:property/></strong></label> 
						<input id="refereeType${status.index + 1}" type="checkbox" 
									${refereeTypes[status.index] ? 'checked' : '' }>
						<input type="hidden" value="${refereeTypes[status.index]}"  name="refereeTypes">
						<div class="select-div" style="display:${!refereeTypes[status.index] ? 'none' : ''}">
							<select id="refereeName${status.index + 1}" name="idUsers">
								<option value="0" selected >Elige un árbitro</option>	
								<s:iterator value="referees" var="referee">
									<option value="${idUser}" 
										${idUsers[status.index] == idUser ? 'selected' : ''}>
										${userProfile.firstName}
										${userProfile.lastName1} 
										
										<s:if test="idGame != null && idGame != ''">
											,
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
												*OK*
											</s:else>
										</s:if>
									</option>
								</s:iterator>
							</select>
						</div>						
					</div>
				</s:iterator>
					<div>
						<div  class="conflicts-2">
							Conflictos
							<div class="conflicts-types">
							<div><strong>Tipos de Conflictos</strong></div>
							<div>*OK*: No hay conflictos</div>
							<div>N/A: No disponible</div>
							<div>O.P: Otro partido</div>
							<div>H: Habilidad</div>
							</div>
						</div>
					</div>
				</div>
				<div>
					<div>
						<s:if test="idGame == null || idGame == ''">
							<input type="submit" class="btn" value="Crear Partido" name="method:addEditGame"> o 
							<a href="games">Cancelar</a>
						
						</s:if>
						<s:else>
							<input type="submit" class="btn" value="Actualizar Partido" name="method:addEditGame"> o 
							<a href="game?idGame=${idGame}">Cancelar</a>
						</s:else>			
						
					</div>
				</div>
				
			</form>	
			
		</div>
		<jsp:include page="../footer.jsp"/>
	</div> 

</body>
</html>