<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="css/profile.css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/commonScript.js"></script>

<title>Miembros</title>

<s:head />
</head>
<body>
	<jsp:include page="header.jsp"/>

	<jsp:include page="leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="images/members-black-icon.png">
				Miembros
			</h3>
			<span>Todos los miembros</span>
		</div>
		
		<jsp:include page="errorMessages.jsp"/>
		
		<div class="container">
			<br>
			<h4 class="new-record"><a><img src="images/add-icon.png" class="small-icon">Añadir un nuevo miembro</a></h4>
			<br>
			<br>
			<table>
				<tr>
					<th>Nombre</th>
					<th>Mostrar</th>
					<th>Contactar</th>	
				</tr>
				<s:iterator  value="users">
				<tr>
					<td><a class="link"><s:property value="userFullName"/></a></td>
					<td>Partidos</td>
					<td><a class="link">Por Correo</a></td>
				</tr>
				
			</s:iterator>
				
			</table>
			
			
		</div>
	</div>

</body>
</html>