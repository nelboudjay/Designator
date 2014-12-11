<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/MyStyle.css" /> 

	<title>Página Principal - Inicar Sesión </title>
<s:head/>
</head>
<body>
	
	<h1>Iniciar sesión</h1>

	<s:if test="hasActionErrors()">
		<div class = "errors">	
			<s:actionerror/>
		</div>
	</s:if>
	
	<s:if test="hasActionMessages()">
   		<div class="boxMessage">
      		<s:actionmessage/>
  		 </div>
	</s:if>
	



<s:form action="login" >
	<s:textfield name="loginField" label="Nombre de usuario o E-Mail"/>
	<s:password name="password" label="Contraseña"/>
	<s:submit value="Iniciar sesión" method="login" />
	<s:checkbox name="rememberMe" label="Recordarme" />
</s:form>
<a href="passwordForgot">¿Has olvidado tu contraseña?</a>

</body>
</html>
