<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="css/availability.css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/commonScript.js"></script>

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
			<h3 class="title-2"><s:property value="selectedMonth"/> <s:property value="selectedYear"/></h3>
			
			<p>	
				<s:iterator value="monthsList" status="status" var="month">
				
					<s:if test="value == selectedMonth">
						<s:property value="value"/>
					</s:if>
					<s:else>
						<a class="link" href="availability?idUser=${idUser}&dateStr=${key}"><s:property value="value"/></a>
					</s:else>
				</s:iterator>
			</p>
			
			<table class="month-calendar">
				<thead>
					<tr>
						<s:iterator
							value="{'Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo'}">
							<th><s:property value="top" /></th>
						</s:iterator>
					</tr>
				</thead>
				<tbody>
					<jsp:useBean id="currentCalendar" class="com.myproject.calendar.CurrentCalendar" scope="session">
						<jsp:setProperty name="currentCalendar" property="monthCalendar" value="2015-10" />
					</jsp:useBean>
					
					<!--<c:forEach var="row" items="${currentCalendar.monthCalendar}"
						varStatus="rowStatus">
						<tr>
							<c:forEach var="column" items="${row}" varStatus="columnStatus">
								<td>${row}
									  ${column.today == 0 ? 'class=\"active\"' : column.today > 0 ? 'class=\"future\"' : ''}>${column.day}
									<c:if
										test="${rowStatus.index == 0 && columnStatus.index == 0
														|| column.day == 1}">
										<span>de </span>${column.month} 
									</c:if>
								</td>
							</c:forEach>
						</tr>
					</c:forEach>-->
				</tbody>
			</table>
			
			
			
			
			
		</div>
	</div>

</body>
</html>