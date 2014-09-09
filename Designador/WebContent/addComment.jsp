<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/MyStyle.css" />
<title>Add Announcement</title>
<s:head />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/tinymce/tinymce.min.js"></script>
<script type="text/javascript">
	tinymce
			.init({
				selector : "textarea",
				menubar : "insert format table tools",
				language : 'es',
				plugins : [
						"advlist autolink autoresize charmap code emoticons fullscreen hr link",
						"lists preview searchreplace spellchecker table textcolor wordcount " ],
				toolbar : "undo redo | styleselect | bold italic | forecolor backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | fullscreen | emoticons | preview | searchreplace"
			});
</script>
</head>
<body>
	<%@ include file="idle-timeout.html"%>

	<div class="utility">

		<a href="passwordForgot">${session.userFullName}
			(${session.user.userName}) </a> | <a href="logout">Cerrar sesión</a> | <a
			href="passwordForgot">Mensajes</a> | <a
			href="passwordResetInstructions">Ayuda</a>
	</div>
	<br />


	<h1>Nuevo Comentario</h1>

	<s:if test="hasActionErrors()">
		<div class="errors">
			<s:actionerror />
		</div>
	</s:if>

	<s:form action="addComment">
		<s:textarea name="commentBody" />
		<s:submit value="Añadir Comentario" method="addComment"/>
	</s:form>
</body>
</html>