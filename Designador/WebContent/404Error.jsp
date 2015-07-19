<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="css/404Error.css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/commonScript.js"></script>

<title>Error 404</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="error-404">
		<h1>Lo sentimos</h1>
	
		<h3>La página que has solicitado no existe.</h3>
	
		<p>Puede ser que hayas usado un enlace antiguo o hayas introducido mal la dirección.</p>
		
		<div >
			<h3 class="title-1">Enlaces principales</h3>
			<ul class="main-links">
				<li>
					<s:if test="#session.user != null">
						<a href="homePage">Inicio</a>
					</s:if>
					<s:else>
						<a href="login">Iniciar sesión</a>
					</s:else>
				</li>
				<li><a href="homePage">Perfil</a></li>
				<li><a href="homePage">Mensajes</a></li>
			</ul>
		</div>
	</div>
</body>
</html>