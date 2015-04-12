<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/MyStyle.css" />

<script type="text/javascript" src="js/jquery.js"></script>


<script type="text/javascript">
	
	$(function() {

		$('#userName').click(function(evt) {
			evt.stopPropagation();
			$('.dropdown-menu').slideToggle("drop");
			$(this).toggleClass("userName");
		});

		$(document).click(function() {
			$('.dropdown-menu').slideUp(function() {
				$('#userName').removeClass("userName");
			});
		});

		$('#leftMenu li').click(function() {
			$('#leftMenu li').removeClass("active");
			$(this).addClass("active");
		});

	});
</script>
<title>Restablecer contraseña</title>
<s:head />
</head>
<body>


	<s:if test="#session.user != null">
		<%@ include file="idle-timeout.html"%>
	</s:if>
	<nav>
		<ul class="logo">
			<li><img src="images/Logo.png" width="12%" height="90%"></li>
		</ul>
		<ul class="navbar-right">
			<s:if test="#session.user != null">

				<li id="userName"><span><img class="profile-icon"
						src="images/profile-icon.png">${session.userFullName}
						(${session.user.userName}) <span class="arrow"></span> </span></li>
				<li id="profile" class="dropdown-menu"><a>Perfil</a></li>
				<li id="messages" class="dropdown-menu"><a>Mensajes</a></li>
				<li id="logout" class="dropdown-menu"><a href="logout">Cerrar
						sesión</a></li>
			</s:if>

			<s:else>

				<li id="login"><a href="login">login</a></li>

			</s:else>
		</ul>

	</nav>

	<s:if test="#session.user != null">
		<div id="leftMenu">
			<ul>
				<li class="active"><span class="glow"></span><a href="homePage"><img
						class="home-icon" src="images/home-icon.png">Inicio</a></li>
				<li><span class="glow"></span><a><img class="home-icon"
						src="images/games-icon.png">Partidos<span class="arrow"></span></a></li>
				<li><span class="glow"></span><a><img class="home-icon"
						src="images/referee-icon.png">Árbitros</a></li>
			</ul>
		</div>

		<br />
		<h2>Restablecer contraseña</h2>
		<h3>Tu sesion ya está iniciada</h3>
		<p>
			Está página se utiliza para restablecer una contraseña cuando no
			tienes la sesión iniciada. <b>Ahora mismo tienes la sesión
				iniciada</b> como
			<s:property value="#session.userFullName" />
			.
		</p>

		<p>
			<a href="changePassword">Cambiar la contraseña,</a> o <br /> <a
				href="homePage">Volver a la Página de Inicio</a>.
		</p>
	</s:if>
	<s:else>
		<h2>Restablecer contraseña</h2>
	</s:else>

	<br />

	<s:if test="hasActionErrors()">
		<div class="errors">
			<s:actionerror />
		</div>
	</s:if>


	<p>
		Su contraseña se puede restablecer utilizando una dirección de correo
		electrónico o nombre de usuario.<br /> Por favor, introduzca esta
		información.
	</p>

	<s:form action="passwordForgot">
		<s:textfield name="resetPasswordLoginField"
			label="Nombre de usuario o E-Mail" />
		<s:submit value="Restablecer mi contraseña" method="resetPassword" />
	</s:form>
</body>
</html>