<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/MyStyle.css" /> 
	
<title>Restablecer contraseña</title>
<s:head/>
</head>
<body>
	<h1>Restablecer contraseña</h1>
	
	<s:if test="hasActionErrors()">
		<div class = "errors">	
			<s:actionerror/>
		</div>
	</s:if>

	<p>
		Su contraseña se puede restablecer utilizando una dirección de correo
		electrónico o nombre de usuario.<br /> Por favor, introduzca esta
		información.
	</p>
	<br />
		
	<s:form action="resetPassword">
		<s:textfield name="resetPasswordLoginField" label="Nombre de usuario o E-Mail"/>
		<s:submit value="Restablecer mi contraseña" />
	</s:form>
</body>
</html>