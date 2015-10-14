<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="css/profile.css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/commonScript.js"></script>

<title>Mi perfil</title>

<s:head />
</head>
<body>
	<jsp:include page="header.jsp"/>

	<jsp:include page="leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="images/profile-black-icon.png">
				${session.user.userFullName}
			</h3>
			<span>Perfil</span>
		</div>
		
		<jsp:include page="errorMessages.jsp"/>
		
		<div class="container">
			
			<s:set var="currentIdUser" value="idUser"/>
			<table class="members">
				<tr>
					<th>Nombre</th>
				
				</tr>
				<s:iterator value="users" status="status" >
				<tr>
					<td><a class="link">
					
					<s:property value="users.size"/>
					<s:if test="#currentIdUser == idUser">
						<s:if test="#status.count == 1">
							First: <s:property value="userFullName"/>
							<s:if test="#status.count < users.size">
							</s:if>
						</s:if>
						
					</s:if>
					
					</a></td>
					
				</tr>
				
			</s:iterator>
			</table>
		</div>
	</div>

</body>
</html>