<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/allAvailability.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/datepicker-es.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/availability.js"></script>

<title>Partidos y Disponibilidad</title>

<s:head />
</head>
<body>
	<jsp:include page="../header.jsp"/>

	<jsp:include page="../leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="${pageContext.request.contextPath}/images/calendar-black-icon.png">
				Partidos y Disponibilidad
			</h3>
			<span><s:date name="date" format="d" /> de 	
				<s:date name="date" format="MMMM" var="monthName"/>
				<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#monthName)"/> de
				<s:date name="date" format="yyyy" />
				
			</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<br/>
		
		<div class="container">
			
			
			<div>
				<a class="link-2" href="allAvailability?dateStr=<s:date name="dateBefore" format="yyyy-MM-dd"/>"> 
					<< <s:date name="dateBefore" format="d"/> de
					<s:date name="dateBefore" format="MMM" var="shortMonth"/>
					<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#shortMonth)"/></a>
				
				<s:date name="date" format="d"/> de
				<s:date name="date" format="MMM" var="shortMonth"/>
				<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#shortMonth)"/>
								
				<a class="link-2" href="allAvailability?dateStr=<s:date name="dateAfter" format="yyyy-MM-dd"/>"> 
					<s:date name="dateAfter" format="d"/> de
					<s:date name="dateAfter" format="MMM" var="shortMonth"/>
					<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#shortMonth)"/> >>
				</a>
				
			</div>
			
			<p>
				<b class="link-2 dark show-available-referees"> Mostrar Sólo Los Árbitros Disponibles </b>
				<b class="link-2 dark show-all-referees"> Mostrar Todos Los Árbitros Disponibles </b>
			</p>
			
			<form action="allAvailability" method="get" >
			
				<div>
					<label><b>Fecha: </b></label>
					<input type="text" id="datepicker" name="dateStr" value="${dateStr}">
					<input type="submit" class="btn" value="Mostrar Disponibilidad" >
				</div>
			</form>
			<table class="available-referees">
				<tr>
					<th>Árbitro</th>
					<th>Disponibilidad</th>
				</tr>
				<s:iterator value="availableReferees">
					<tr>
						<td><a class="link" href="availability?idUser=${key.idUser}"><s:property value="key.userFullName"/> </a></td>
						<s:if test="value">
							<td class="check" title="${key.userFullName} está disponible para esta fecha."></td>
						</s:if>
						<s:else>
							<td class="cross" title="${key.userFullName} no está disponible para esta fecha."></td>
						</s:else>
					</tr>
				</s:iterator>
			</table>
											
		</div>
		
		<jsp:include page="../footer.jsp"/>
		
	</div>

</body>
</html>