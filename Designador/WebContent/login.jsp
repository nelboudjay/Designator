<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="css/MyStyle.css" />

<title>Página Principal - Inicar Sesión</title>
<s:head />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$('#form').on('submit', function(e) {
			
			$('#loginField').css("border-color", "");
			$('#password').css("border-color", "");
			$('.requiredField').text("");

			if ($('#loginField').val() == '') {
				$('.requiredField').css("display", "block");
				$('.requiredField').text("Introduce tu nombre de usuario o E-Mail.");
				$('#loginField').css("border-color", "#b94a48");
				return false;
			} else if ($('#password').val() == '') {
				$('.requiredField').css("display", "block");
				$('.requiredField').text("Introduce tu contraseña.");
				$('#password').css("border-color", "#b94a48");
				return false;
			}
			
		});

		$('#submit').click(function() {

		});

		$('.close').click(function() {
			$(".boxMessage").css("display", "none");
			$(".error").css("display", "none");
		});
	});
</script>
</head>
<body>

	<nav>
		<ul class="logo">
			<li><img src="images/Logo.png" width="12%" height="90%"></li>
		</ul>
	</nav>
	<s:if test="hasActionErrors()">
		<div class="error">
			<button class="close" type="button">×</button>
			<s:actionerror />
		</div>
	</s:if>

	<s:if test="hasActionMessages()">
		<div class="boxMessage">
			<button class="close" type="button">×</button>
			<s:actionmessage />
		</div>
	</s:if>

	<div class="loginBox">
		<div class="boxHeader">Iniciar sesión</div>
		<div class="boxContent">
			<form id="form" action="/Designador/login" method="post">
				<input id="loginField" type="text" name="loginField"
					placeholder="Nombre de usuario o E-Mail" class="loginFields">
				<input id="password" type="password" name="password"
					placeholder="Contraseña" class="loginFields">
				<div class="requiredField" ></div>
				<label class="checkbox"> <input type="checkbox"
					name="rememberMe" value="true"> Recordarme
				</label> <input id="submit" type="submit" class="loginButton"
					value="Iniciar sesión" name="method:login">
			</form>
			<a href="passwordForgot">¿Has olvidado tu contraseña?</a>
		</div>
	</div>

</body>
</html>
