<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="css/login.css" />

<title>Página Principal - Inicar Sesión</title>
<s:head />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/commonScript.js"></script>

</head>
<body>

	<jsp:include page="header.jsp" />

	<jsp:include page="errorMessages.jsp" />

	<div class="loginBox">
		<div class="boxHeader">Iniciar sesión</div>
		<div class="boxContent">
			<form action="login" method="post">
				<div>
					<input type="text" name="loginField"
						placeholder="Nombre de usuario o E-Mail" class="text-input required-field">
					<div class="error-field">Introduce tu nombre de usuario o E-Mail.</div>
				</div>
				<div>
					<input type="password" name="password"
						placeholder="Contraseña" class="text-input required-field">
					<div class="error-field">Introduce tu contraseña.</div>
				</div>
				<label class="checkbox"> <input type="checkbox"
					name="rememberMe" value="true"> Recordarme
				</label> <input type="submit" class="loginButton" value="Iniciar sesión"
					name="method:login">
			</form>
			<a href="passwordForgot">¿Has olvidado tu contraseña?</a>
		</div>
	</div>

</body>
</html>
