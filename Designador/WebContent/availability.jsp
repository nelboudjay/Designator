<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<link rel="stylesheet" type="text/css" href="css/availability.css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/commonScript.js"></script>
<script type="text/javascript" src="js/availability.js"></script>

<title>Disponibilidad</title>

<s:head />
</head>
<body>
	<jsp:include page="header.jsp"/>

	<jsp:include page="leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="images/profile-black-icon.png">
				<s:property value="userFullName"/>
			</h3>
			<span>Disponibilidad</span>
		</div>
		
		<jsp:include page="errorMessages.jsp"/>
		
		<br/>
		
		<div class="container">
				
				<s:set var="currentIdUser" value="idUser"/>

				<s:iterator value="#attr.users" status="status" >
					<s:if test="idUser == #currentIdUser">
						<s:if test="#status.count > 1">
							<s:set var="previousUserFullName" value="#attr.users[#status.index-1].userFullName"/>
							<s:set var="previousIdUser" value="#attr.users[#status.index-1].idUser"/>	
						</s:if>
												
						<s:if test="#status.count < #attr.users.size">
							<s:set var="nextUserFullName" value="#attr.users[#status.index+1].userFullName"/>	
							<s:set var="nextIdUser" value="#attr.users[#status.index+1].idUser"/>									
						</s:if>
					</s:if>
				</s:iterator>

			<div class="user-menu">
				<img  src="getImage?idUser=${idUser}" >
				<span><s:property value="userFullName"/></span>
				<ul>
					<li><a>Partidos</a></li>
					<li><a>Disponibilidad</a></li>
					<li><a>Conflictos</a></li>
				</ul>
			</div>
			<div class="user-paginate">
				
				<s:if test="#previousUserFullName != null ">
					<a class="btn" href="availability?idUser=${previousIdUser}&dateStr=${dateStr}"><img src="images/back-icon.png"><s:property value="#previousUserFullName"/></a>						
				</s:if>
				
				<s:if test="#nextUserFullName != null ">
					<a class="btn" href="availability?idUser=${nextIdUser}&dateStr=${dateStr}"><s:property value="#nextUserFullName"/><img src="images/forward-icon.png"></a>						
				</s:if>
				
			</div>
			
			<br/>
			<div class="month-calendar">
			<h3 class="title-2"><s:property value="selectedMonthName"/> <s:property value="selectedYear"/></h3>
			
			<p>	
				<s:iterator value="monthsList" status="status" var="month">
				
					<s:if test="value == selectedMonthName">
						<s:property value="value"/>
					</s:if>
					<s:else>
						<a class="link" href="availability?idUser=${idUser}&dateStr=${key}"><s:property value="value"/></a>
					</s:else>
				</s:iterator>
			</p>
			
			<table >
				<thead>
					<tr>
						<s:set var="weekDays" value="{'Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo'}"/>
						
						<s:iterator value="#weekDays">
							<th><s:property/></th>
						</s:iterator>
					</tr>
				</thead>
				<tbody>
					<jsp:useBean id="monthCalendar" class="com.myproject.calendar.MonthCalendar" >
						<jsp:setProperty property="yearMonth" name="monthCalendar" value="${dateStr}"/>
					</jsp:useBean>
					<c:forEach var="week" items="${monthCalendar.monthCalendar}" varStatus="weekStatus">
						<tr>
						<c:forEach var="column" items="${week}" varStatus="columnStatus">
							<td>
							<c:if test="${column != null}">
								<span class="${column.today == 0 ? 'bold' : ''}">${column.day}</span>
								<c:if test="${column.today >= 0}">
									<br><span class="link-2 ${column.today == 0 ? 'bold' : ''}" data-date="${selectedYear}-${column.month}-${column.day}" 
															data-day="${column.day}" data-dayName="${weekDays[columnStatus.index]}" 
															data-month="${selectedMonthName}" data-availability="0">Activar</span>
								</c:if>
							</c:if>
							</td>
						</c:forEach>
						</tr>
					</c:forEach>		
				
					
				</tbody>
			</table>
			</div>
			
			<div id="availableDates">
				<h3 class="title-2">Fechas Disponibles</h3>
				<div class="no-dates" >No tienes fechas disponibles este mes.</div>
			</div>
				
												
		</div>
	</div>

</body>
</html>