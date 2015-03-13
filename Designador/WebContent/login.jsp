<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="css/MyStyle.css" />

<title>Página Principal - Inicar Sesión</title>
<s:head />
</head>
<body>

	<!--  <h1>Iniciar sesión</h1> -->

	<nav>
		<ul class="logo">
			<li><img src="images/Logo.png" width="12%" height="90%"></li>
		</ul>
	</nav>
	<s:if test="hasActionErrors()">
		<div class="errors">
			<s:actionerror />
		</div>
	</s:if>

	<s:if test="hasActionMessages()">
		<div class="boxMessage">
			<s:actionmessage />
		</div>
	</s:if>



	<div class="loginBox">
		<div class="boxHeader">Iniciar sesión</div>
		<div class="boxContent">
			<form action="/Designador/login" method="post">
				<input type="text" name="loginField" placeholder="Nombre de usuario o E-Mail">
			
			</form>
			<s:form action="login">
				<s:textfield name="loginField"
					placeholder="Nombre de usuario o E-Mail" />
				<s:password name="password" placeholder="Contraseña" />
				<s:submit value="Iniciar sesión" method="login" />
				<s:checkbox name="rememberMe" label="Recordarme" />
			</s:form>
			<a href="passwordForgot">¿Has olvidado tu contraseña?</a>
		</div>
	</div>
</body>
</html>
