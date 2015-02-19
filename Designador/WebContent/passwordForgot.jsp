<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/MyStyle.css" />

<title>Restablecer contraseña</title>
<s:head />
</head>
<body>

	<s:if test="#session.user != null">
		<%@ include file="idle-timeout.html"%>

		<div class="utility">
			<a href="passwordForgot">${session.userFullName}
				(${session.user.userName}) </a> | <a href="logout">Cerrar sesión</a> | <a
				href="passwordForgot">Mensajes</a> | <a
				href="passwordResetInstructions">Ayuda</a>
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
		<s:submit value="Restablecer mi contraseña" method="resetPassword"/>
	</s:form>
</body>
</html>