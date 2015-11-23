<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/maintenance.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commonScript.js"></script>

<title>Añadir Categorías</title>

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
			<span>Nueva Categoría</span>
		</div>
		
		<jsp:include page="../errorMessages.jsp"/>
		
		<div class="container">
			<br/>
			<form action="addEditCategory" method="post" >
							
				<div class="row">
					<div>
						<label class="required"><strong>Nombre de Categoría</strong></label> <input id="categoryName"
							type="text" class="text-input-2 required-field" name="categoryName" 
								value="${categoryName}">
						<div class="error-field">Nombre de categoría no puede estar en blanco.</div>	
					</div>
					<div>
						<label class="required"><strong>Sexo</strong></label> 
						<div class="select-div">
							<select id="categoryGender" class="required-field" name="categoryGender">
							    <option selected disabled>Elige El sexo</option>
								<option value="1" ${categoryGender == 1 ? 'selected' : ''}>Masculino</option>
								<option value="2" ${categoryGender == 2 ? 'selected' : ''}>Femenino</option>
								<option value="3" ${categoryGender == 3 ? 'selected' : ''}>Mixto</option>
							</select>
						</div>
						<div class="error-field">El sexo de la categoría no puede estar en blanco.</div>	
					</div>
					
					<div><input name="idCategory" value="${idCategory}" type="hidden"></div>
					   
				</div>
				
				<div>
					<div>				
						<input type="submit" class="btn" value="${categoryName == null || categoryName == '' ? 'Crear' : 'Actualizar'} Competición" name="method:addEditCategory"> o 
						<a href="allCategories">Cancelar</a>						
						
					</div>
				</div>
				
			</form>	
			
		</div>
		<jsp:include page="../footer.jsp"/>
	</div> 

</body>
</html>