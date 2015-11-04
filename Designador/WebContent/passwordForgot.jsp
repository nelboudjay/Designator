<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="css/passwordForgot.css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/commonScript.js"></script>


<title>Restablecer contraseña</title>
<s:head />
</head>
<body>

	<jsp:include page="header.jsp"/>

	<jsp:include page="errorMessages.jsp"/>


	<s:if test="#session.user != null">
		<div class="panel-info">
			<div class="panel-heading">
				<h3>¡Tu sesión ya está iniciada!</h3>
			</div>
			<div class="panel-body">
				<p>
					Está página se utiliza para restablecer una contraseña cuando no
					tienes la sesión iniciada. <b>Ahora mismo tienes la sesión
						iniciada</b> como <b>
					<s:property value="#session.user.userName" /></b>.
				</p>
				<p>
					<a class="link" href="changePassword">Cambiar la contraseña,</a> o <br /> <a
						class="link" href="homePage">Volver a la Página de Inicio</a>.
				</p>
			</div>
		</div>


	</s:if>

	<div class="reset-password-box">
		<h3 class="title-1">Restablecer contraseña</h3>
		<form action="passwordForgot" method="post">
			<label> <strong>Nombre de usuario o E-Mail</strong></label> <div><input id="resetPasswordLoginField" type="text"
				class="text-input-2 required-field" name="resetPasswordLoginField" value="${resetPasswordLoginField}">
			<div class="error-field">Introduce tu nombre de usuario o E-Mail.</div></div>
			<span>Tu contraseña se puede restablecer utilizando una
				dirección de correo electrónico o nombre de usuario.</span> <input
				type="submit" class="btn" value="Restablecer mi contraseña"
				name="method:resetPassword">
		</form>
	</div>
	<s:if test="#session.user != null">

 		<jsp:include page="footer.jsp"/>
 	</s:if>
</body>
</html>