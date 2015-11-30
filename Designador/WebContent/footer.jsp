<%@ taglib prefix="s" uri="/struts-tags"%>


<div class="footer">
	<h3 class="title-2">Designator</h3> © <s:date name="%{new java.util.Date()}" format="yyyy"/> by Nabil El Boudjay
	<div> <s:date name="%{new java.util.Date()}" format="EEEE" var="todayDay"/>
		<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#todayDay)"/>,
		<s:date name="%{new java.util.Date()}" format="d"/> de 
		<s:date name="%{new java.util.Date()}" format="MMMM" var="todayMonth"/>
		<s:property value="@com.opensymphony.xwork2.inject.util.Strings@capitalize(#todayMonth)"/> de
		<s:date name="%{new java.util.Date()}" format="yyyy"/> a las 
		<s:date name="%{new java.util.Date()}" format="HH:mm:ss" /> horas 
		<s:date name="%{new java.util.Date()}" format="z" />
	</div>
</div>