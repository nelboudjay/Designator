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
		<s:property value="game.HomeTeam.TeamName"/> vs <s:property value="game.AwayTeam.TeamName"/>
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
					<b><s:property value="game.HomeTeam.TeamName"/></b> vs <b><s:property value="game.AwayTeam.TeamName"/></b>
				</s:if>
				<s:else>
					Nuevo Partido
				</s:else>
			</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<div class="container">
			<br/>
			<form id="gameForm" action="addEditCategory" method="post" >
			
				<div><input name="idGame" value="${idGame}" type="hidden"></div>
			
				<div class="row">
					<div>
						<label class="required"><strong>Fecha</strong></label> 
						<input id="datepicker" type="text" class="text-input-2 required-field" name="gameDate" 
								value="${dateStr}">
						<div class="error-field">La fecha no puede estar en blanco.</div>	
					</div>
					<div>
						<label class="required"><strong>Hora</strong></label> 
						<input id="datepicker" placeholder="Introduce la hora con el siguiente formato HH:MM" type="text" class="text-input-2 required-field" name="gameTime" 
								value="${timeStr}">
						<div class="error-field">La hora no puede estar en blanco.</div>	
					</div>
					 <div></div> 
				</div>
				
				<div class="row">
					
					<div>
						<label class="required"><strong>Equipo Local</strong></label> 
						<div class="select-div">
							<select id="homeTeam" class="required-field" name="homeTeam">
								<option selected disabled>Elige el equipo local</option>
								<s:iterator value="teams">
									<option value="${idTeam}" ${game.homeTeam.idTeam == idTeam ? 'selected' : ''}>${teamName}</option>
								</s:iterator>
								<option value="0">Nuevo Equipo</option>
							</select>
						</div>
						<input style="display:none;" id="homeTeam" placeholder="Introduce aquí el nombre del equipo"
							type="text" class="text-input-2 required-field" name="homeTeam" value="">
						<div class="error-field">El equipo local no puede estar en blanco</div>			
					</div>
					
					<div>
						<label class="required"><strong>Equipo Visitante</strong></label> 
						<div class="select-div">
							<select id="awayTeam" class="required-field" name="awayTeam">
								<option selected disabled>Elige el equipo visitante</option>
								<s:iterator value="teams">
									<option value="${idTeam}" ${game.awayTeam.idTeam == idTeam ? 'selected' : ''}>${teamName}</option>
								</s:iterator>
								<option value="0">Nuevo Equipo</option>
							</select>
						</div>
						<input style="display:none;" id="awayTeam" placeholder="Introduce aquí el nombre del equipo"
							type="text" class="text-input-2 required-field" name="awayTeam" value="">
						<div class="error-field">El equipo visitante no puede estar en blanco</div>		
					</div>
				
					<div></div>
				</div>
				
				<div class="row">	
					<div>
						<label><strong>Categoría</strong></label> 
						<div class="select-div">
							<select id="categoryName" name="categoryName">
								<option selected disabled>Elige la categoría</option>
								<s:iterator value="categories">
									<option value="${idCategory}" ${game.gameCategory.idCategory == idCategory ? 'selected' : ''}>${categoryName} ${categoryGenderName}</option>
								</s:iterator>
								<option value="0">Nueva Categoría</option>
							</select>
						</div>
						<input style="display:none;" id="categoryName" placeholder="Introduce aquí el nombre de la categoría"
							type="text" class="text-input-2" name="categoryName" value="">
						<div class="error-field">El nombre de la categoría no puede estar en blanco</div>	
						<div style="display:none;" class="select-div">
							<select id="categoryGender" class="required-field" name="categoryGender">
							    <option selected disabled>Elige el sexo</option>
								<option value="1">Masculino</option>
								<option value="2">Femenino</option>
								<option value="3">Mixto</option>
							</select>
						</div>	
						<div class="error-field">El sexo de la categoría no puede estar en blanco.</div>		
						
					</div>
					
					<div>
						<label><strong>Competición</strong></label> 
						<div class="select-div">
							<select id="leagueName" name="leagueName">
								<option selected disabled>Elige la competición</option>
								<s:iterator value="leagues">
									<option value="${idLeague}" ${game.gameLeague.idLeague == idLeague ? 'selected' : ''}>${leagueName}</option>
								</s:iterator>
								<option value="0">Nueva Competición</option>
							</select>
						</div>
						<input style="display:none;" id="leagueName" placeholder="Introduce aquí el nombre de la competición"
							type="text" class="text-input-2" name="leagueName" value="">
						<div class="error-field"></div>			
					</div>
					
					 <div>
						<label><strong>Pista</strong></label> 
						<div class="select-div">
							<select id="venueName" name="venueName">
								<option selected disabled>Elige la pista</option>								
								<s:iterator value="venues">
									<option value="${idVenue}" ${game.gameVenue.idVenue == idVenue ? 'selected' : ''}>${venueName}</option>
								</s:iterator>
								<option value="0">Nueva Pista</option>
							</select>
						</div>
						<input style="display:none;" id="leagueName" placeholder="Introduce aquí el nombre de la pista"
							type="text" class="text-input-2" name="leagueName" value="">
						<div class="error-field"></div>			
					</div>
				</div>
			
				<div class="row">
					<div>
						<label><b>¿Publicado?</b></label>
						<input id="gameStatus" type="checkbox" ${game.gameStatus ? 'checked' : ''}>
						<input  type="hidden" value="${game.gameStatus}" name="gameStatus">
					</div>		
				</div>
				
				<h3 class="title-1 required">Equipo Arbitral</h3>
				
				<div class="row">
				<s:iterator value="{'Principal','Auxiliar','Anotador','Cronometrador','Operador 30\"','Coche'}"  
					status="status">
					<div>
						<label><strong><s:property/></strong></label> 
						<input id="refereeType${status.index + 1}" type="checkbox" 
							<s:if test="game.getRefereeGame(#status.index + 1) != null">
									checked
							</s:if>
						>
						<input  type="hidden" value="${game.getRefereeGame(status.index + 1) != null}" name="refereeTypes">
						<div class="select-div">
							<select id="refereeName${status.index + 1}" name="refereesGame">
								<option selected disabled>Elige un árbitro</option>	
								<s:iterator value="referees">
									<option value="${idUser}" 
										${game.getRefereeGame(status.index + 1).userRefereeType.user.idUser == idUser ? 'selected' : ''}>
										${userRefereeType.user.userProfile.firstName}
										${userRefereeType.user.userProfile.lastName1}
									</option>
								</s:iterator>
							</select>
						</div>						
					</div>
				</s:iterator>
				</div>
				
				<div>
					<div>				
						<input type="submit" class="btn" value="${idGame == null || idGame == '' ? 'Crear' : 'Actualizar'} Equipo" name="method:addEditGame"> o 
						<a href="games">Cancelar</a>						
						
					</div>
				</div>
				
			</form>	
			
		</div>
		<jsp:include page="../footer.jsp"/>
	</div> 

</body>
</html>