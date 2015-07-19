<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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

	<jsp:include page="errorMessages.jsp" />

	<div class="change-password-box">
		<h3 class="title-1">Cambiar contraseña</h3>
		<div>Tu nombre de usuario es <strong>  <s:property value="#attr.user.userName"/> </strong>.
		</div>
		<form action="changePassword" method="post">
			<div>
				<label><strong>Nueva contraseña</strong></label> <input id="password"
					type="password" class="text-input-2 required-field identical-field" name="password">
				<div class="error-field">Introduce tu nueva contraseña.</div>
					
			</div>
			<div>
			<label><strong>Vuelve a escribir la contraseña</strong></label> <input id="rePassword"
				type="password" class="text-input-2 required-field identical-field" name="repassword">
				<div class="error-field error-field-2">Las contraseñas deben ser iguales.</div>
				
			</div>
			<input type="submit" class="btn"
				value="Cambiar contraseña y Iniciar sesión"
				name="method:changePassword">
		</form>
	</div>

</body>
</html>