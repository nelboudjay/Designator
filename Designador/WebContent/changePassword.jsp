<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/MyStyle.css" />

<title>Cambiar contraseña</title>
<s:head />
</head>
<body>
	<h1>Cambiar contraseña</h1>

	<s:if test="hasActionErrors()">
		<div class="errors">
			<s:actionerror />
		</div>
	</s:if>
	<s:else>

		<p>
			Crea una contraseña para tu cuenta.
		</p>
		<br />
		<s:form action="changePassword">
			<s:hidden name="id" ></s:hidden>
			<s:password name="password" label="Nueva Contraseña" />
			<s:password name="repassword" label="Repetir Contraseña" />
			<s:submit value="Cambiar contraseña y Iniciar sesión" method="changePassword" />
		</s:form>
	</s:else>

</body>
</html>