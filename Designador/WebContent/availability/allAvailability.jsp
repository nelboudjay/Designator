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
				<a class="link-2" href="allAvailability?dateStr=<s:date name="dayBefore" format="yyyy-MM-dd"/>"> 
					<< <s:date name="dayBefore" format="d"/> de
					<s:date name="dayBefore" format="MMM" var="shortMonth"/>
					<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#shortMonth)"/></a>
				
				<s:date name="date" format="d"/> de
				<s:date name="date" format="MMM" var="shortMonth"/>
				<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#shortMonth)"/>
								
				<a class="link-2" href="allAvailability?dateStr=<s:date name="dayAfter" format="yyyy-MM-dd"/>"> 
					<s:date name="dayAfter" format="d"/> de
					<s:date name="dayAfter" format="MMM" var="shortMonth"/>
					<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#shortMonth)"/> >>
				</a>
				
			</div>
			
			<s:if test="availableReferees.size() > 0">
			
				<p>
					<b class="link-2 dark show-available-referees"> Mostrar Sólo Los Árbitros Disponibles </b>
					<b class="link-2 dark show-all-referees"> Mostrar Todos Los Árbitros Disponibles </b>
				</p>
			</s:if>
			
			<form action="allAvailability" method="get" >
			
				<div>
					<label><b>Fecha: </b></label>
					<input type="text" id="datepicker" name="dateStr" value="${dateStr}">
					<input type="submit" class="btn" value="Mostrar Disponibilidad" >
				</div>
			</form>
			<s:if test="availableReferees.size() == 0">
				<div class="panel-info">
					<div class="panel-body">
						No hay ningun árbitro disponible para esta fecha.
					</div>
				</div>
			</s:if>
			<s:else>
				<table class="available-referees">
					<tr>
						<s:iterator value="{'00:00','01:00','02:00','03:00','04:00','05:00','06:00','07:00',
						'08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00'
						,'16:00','17:00','18:00','19:00','20:00','21:00','22:00','23:00'}" status="status">
							<th <s:if test="#status.index == 0">style="border-left:1px solid #ddd; padding-left:2px;" </s:if>><s:property/></th>
						</s:iterator>
					</tr>
					<s:iterator value="availableReferees">
						<tr>
							<td colspan="24">
								<a class="link" href="availability?idUser=${key.idUser}"><s:property value="key.userFullName"/> </a>
							
								<s:iterator value="value">
									<div style="margin-left:${marginLeftPer}%; width:${widthPer}%;">&nbsp;</div>
								</s:iterator>
							
							</td>
						</tr>
					</s:iterator>
				</table>
			</s:else>
											
		</div>
		
		<jsp:include page="../footer.jsp"/>
		
	</div>

</body>
</html>