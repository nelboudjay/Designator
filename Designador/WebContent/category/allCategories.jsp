<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/category.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/users.js"></script>

<title>Categorías</title>

<s:head />
</head>
<body>
	<jsp:include page="../header.jsp"/>

	<jsp:include page="../leftMenu.jsp"/>
	
	<div class="main-content">
		<div class="content-title">
			<h3>
				<img class="black-icon" src="${pageContext.request.contextPath}/images/category-black-icon.png">
				Categorías
			</h3>
			<span>Todas las Categorías</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<div class="container">
			<br>	
			<h4 class="new-record"><a href="addEditCategory">
				<img src="${pageContext.request.contextPath}/images/add-icon.png" class="small-icon">
					Añadir una categoría</a>
			</h4>
			<br>
			
			<s:if test="#attr.categories.size() == 0">
				<div class="panel-info">
					<div class="panel-body">
						No hay ninguna categoría disponible en el sistema. ¿Quieres <a class="link" href="addEditCategory">Añadir</a> una categoría?
					</div>
				</div>
			</s:if>
			<s:else>
				<table id="categories">
					<tr><th>Nombre de Categoría</th>
						<th>Sexo<th>
					</tr>	
					<s:iterator value="#attr.categories">
						<tr>
							<td><a class="link" href="addEditCategory?idCategory=${idCategory}">${categoryName}</a></td>
							<td>
								<s:if test="categoryGender == 2">
									Femenino
								</s:if>
								<s:elseif test="categoryGender == 3">
									Mixto
								</s:elseif>
								<s:else>
									Masculino
								</s:else>
							</td>
							<td><a class="link delete" href="deleteCategory?idCategory=${idCategory}&method:deleteCategory">Eliminar</a>
								<span class="confirm-box">
									<span class="message">¿Estás seguro que quieres
										eliminar esta categoría? </span> <span class="btn yes">Sí</span> 
										<span class="btn no">No</span>
								</span>	
							</td>
						</tr>	
					</s:iterator>
				</table>
			</s:else>
			
		</div>
		<jsp:include page="../footer.jsp"/>
		
	</div>
</body>
</html>