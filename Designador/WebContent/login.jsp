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
			$('#loginField').css("border-style", "solid");
			$('#password').css("border-style", "solid");
			
			$('.required-field ').text("");

			if ($('#loginField').val() == '') {
				$('.required-field ').css("display", "block");
				$('.required-field ').text("Introduce tu nombre de usuario o E-Mail.");
				$('#loginField').css("border-color", "#b94a48");
				$('#loginField').css("border-style", "solid");

				return false;
			} else if ($('#password').val() == '') {
				$('.required-field ').css("display", "block");
				$('.required-field ').text("Introduce tu contraseña.");
				$('#password').css("border-color", "#b94a48");
				$('#password').css("border-style", "solid");

				return false;
			}
			
		});

	
		$('.close').click(function() {
			$(".boxMessage, .error, .errors").remove();
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
				<div class="required-field" ></div>
				<label class="checkbox"> <input type="checkbox"
					name="rememberMe" value="true"> Recordarme
				</label> <input type="submit" class="loginButton"
					value="Iniciar sesión" name="method:login">
			</form>
			<a href="passwordForgot">¿Has olvidado tu contraseña?</a>
		</div>
	</div>

</body>
</html>
