<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="css/changePassword.css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/commonScript.js"></script>

<title>Cambiar contraseña</title>
<s:head />
</head>
<body>

	<jsp:include page="header.jsp" />
	
	<s:if test="#session.user != null">
		<jsp:include page="leftMenu.jsp"/>
	</s:if>	

	<div ${session.user != null ? "class='main-content'" : ""}> 
	<jsp:include page="errorMessages.jsp" />

	<div class="change-password-box">
		<s:if test="#attr.user.password == ''">
			<h3 class="title-1">Crear Mi Cuenta</h3>
			<div>Bienvenido a <b><a class="link" href="login">Designador</a></b>, una aplicación web que se utiliza para ayudar a los arbitros y a los designadores para la gestión de la designación de árbitros en competiciones de baloncesto.</div>
			<div>Para empzar, por favor crea una contraseña:</div>
		</s:if>
		<s:else>
			<h3 class="title-1">Cambiar Mi Contraseña</h3>
		</s:else>
		
		<div>Tu nombre de usuario es <b><s:property value="#attr.user.userName"/></b>.
		</div>
		<form action="changePassword" method="post">
			<div>
				<label><strong>Nueva contraseña</strong></label> <input id="password"
					type="password" class="text-input-2 required-field identical-field" name="password" value="${password}">
				<div class="error-field">Introduce tu nueva contraseña.</div>
					
			</div>
			<div>
			<label><strong>Vuelve a escribir la contraseña</strong></label> <input id="repassword"
				type="password" class="text-input-2 required-field identical-field" name="repassword" value="${repassword}">
				<div class="error-field">Las contraseñas deben ser iguales.</div>
				
			</div>
			<input type="submit" class="btn"
				value="Actualizar contraseña y Iniciar sesión"
				name="method:changePassword">
		</form>
	</div>
	<jsp:include page="footer.jsp"/>
	
	</div>
</body>
</html>