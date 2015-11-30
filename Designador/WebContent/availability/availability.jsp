<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/availability.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/datepicker-es.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/availability.js"></script>

<title>Disponibilidad</title>

<s:head />
</head>
<body>
	<jsp:include page="../header.jsp"/>

	<jsp:include page="../leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="${pageContext.request.contextPath}/images/profile-black-icon.png">
				<s:property value="#attr.userFullName"/>
			</h3>
			<span>Disponibilidad</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<br/>
		
		<div class="container">
				
			<s:if test="#session.user.isAdmin()">
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
			</s:if>	
			<div id="${idUser}" class="user-menu">
				<img  src="getImage?idUser=${idUser}" >
				<a href="${pageContext.request.contextPath}/user/user?idUser=${idUser}"><s:property value="#attr.userFullName"/></a>
				<ul>
					<li><a href="${pageContext.request.contextPath}/game/games?idUser=${idUser}">Partidos</a></li>
					<li><a href="availability?idUser=${idUser}">Disponibilidad</a></li>
					<li><a>Conflictos</a></li>
				</ul>
			</div>
			<div class="user-paginate">
				
				<s:if test="#previousUserFullName != null ">
					<a class="btn" href="availability?idUser=${previousIdUser}&dateStr=${dateStr}"><img src="${pageContext.request.contextPath}/images/back-icon.png"><s:property value="#previousUserFullName"/></a>						
				</s:if>
				
				<s:if test="#nextUserFullName != null ">
					<a class="btn" href="availability?idUser=${nextIdUser}&dateStr=${dateStr}"><s:property value="#nextUserFullName"/><img src="${pageContext.request.contextPath}/images/forward-icon.png"></a>						
				</s:if>
				
			</div>
			
			<br/>
			<div class="month-calendar">
			<h3 class="title-2"><s:property value="#attr.selectedMonthName"/> <s:property value="#attr.selectedYear"/></h3>
			
			<p>	
			
				<s:iterator value="#attr.monthsList" status="status">	
					
					<s:if test="key < (#attr.selectedYear + '-' + #attr.selectedMonth)">
						<a class="link" href="availability?idUser=${idUser}&dateStr=${key}">« <s:property value="value"/></a>
					</s:if>
					<s:elseif test="key == (#attr.selectedYear + '-' + #attr.selectedMonth)">
						<s:property value="value"/>
					</s:elseif>
					<s:else>
						<a class="link" href="availability?idUser=${idUser}&dateStr=${key}"><s:property value="value"/> »</a>
					</s:else>
				</s:iterator>
			</p>
			
			<table >
				<thead>
					<tr>
						<s:set var="weekDays" value="{'Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo'}"/>
						
						<s:iterator value="#weekDays" >
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
								<s:set var="dataDate" >${selectedYear}-${column.month}-${column.day}</s:set>								
								<div class="${column.today == 0 ? 'bold' : ''}
									<s:if test="%{#dataDate  in #attr.availableStartDates}">
											check
									</s:if>
									<s:else>
											cross
									</s:else>
									">
									${column.day}
								</div>
								<c:choose>
									<c:when test="${column.today >= 0}">
										
										<s:if test="%{#dataDate  in #attr.availableStartDates}">
											<span class="link-2 ${column.today == 0 ? 'bold' : ''}" data-date="${dataDate}" 
												data-day="${column.day}" data-dayName="${weekDays[columnStatus.index]}" 	
												data-month="${selectedMonthName}" 
												
												data-available="1">Desactivar									
											</span>
										</s:if>
										<s:else>
											<span class="link-2 ${column.today == 0 ? 'bold' : ''}" data-date="${dataDate}" 
												data-day="${column.day}" data-dayName="${weekDays[columnStatus.index]}" 	
												data-month="${selectedMonthName}" 
												
												data-available="0">Activar									
											</span>
										</s:else>																									
									</c:when>
									<c:otherwise>
										<span>&nbsp;</span>
									</c:otherwise>
								</c:choose>
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

				<div class="no-dates" style="${availableDates.size() > 0 ? 'display:none' : ''}" >No tienes fechas disponibles este mes.</div>
				
				<s:iterator value="#attr.availableDates">
					<div data-day="<s:date name="startDate" format="d" />" data-date="<s:date name="startDate" format="yyyy-MM-d" />">
						<img src="${pageContext.request.contextPath}/images/garbage-icon.png" class="garbage" title="Eliminar esta fecha">
						<s:date name="startDate" format="EEEE" var="dayName"/>
						<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#dayName)"/>
						<s:date name="startDate" format="d" /> de 
						<s:date name="startDate" format="MMMM" var="monthName"/>
						<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#monthName)"/>
					</div>
				</s:iterator>	
				
			</div>
							
		<!--<div class="box" id="addDates">
				<h3 class="title-2">Añadir Fechas (Una o más)</h3>
				
				<p>Añade tu disponibilidad para un rango de fechas, o añade un rango de tiempo para un día.</p>
				
				<div>
					<label><b>Estoy disponible</b></label>
					<select>
						<option value="el">El</option>
						<option value="cada">Cada</option>
					</select>
				</div>
			</div>-->
											
		</div>
		
		<jsp:include page="../footer.jsp"/>
		
	</div>

</body>
</html>