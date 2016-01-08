<%@ taglib prefix="s" uri="/struts-tags"%>


<div class="footer">
	
	<div class="site-map">
		<ul>
			<li>Designator</li>
			<li><a href="${pageContext.request.contextPath}/homePage" class="link-1">Inicio</a></li>
			<li><a href="${pageContext.request.contextPath}/user/user?idUser=${session.user.idUser}" class="link-1">Perfil</a></li>
			<li><a href="${pageContext.request.contextPath}/logout" class="link-1">Cerrar sesión</a></li>
			
		</ul>
		<ul>
			<li>Partidos</li>
			<s:if test="#session.user.isAdmin()">
				<li><a href="${pageContext.request.contextPath}/game/games" class="link-1">Todos los partidos</a></li>
			</s:if>
			<s:if test="#session.user.userRole > 1">
				<li><a href="${pageContext.request.contextPath}/game/games?idUser=${session.user.idUser}" class="link-1">Mis partidos</a></li>
			</s:if>
			
		</ul>
		<ul>
			<li>Disponibilidad</li>
			<s:if test="#session.user.isAdmin()">
				<li><a href="${pageContext.request.contextPath}/availability/allAvailability" class="link-1">Toda la disponibilidad</a></li>
			</s:if>
			<s:if test="#session.user.userRole > 1">
				<li><a href="${pageContext.request.contextPath}/availability/availability?idUser=${session.user.idUser}" class="link-1">Mi disponibilidad</a></li>
			</s:if>
		</ul>
		<ul>
			<li>Miembros</li>
			<li><a href="${pageContext.request.contextPath}/user/users" class="link-1">Todos los miembros</a></li>
		</ul>
		<ul>
		
		<s:if test="#session.user.isAdmin()">
			<li>Mantenimiento</li>
			<li><a href="${pageContext.request.contextPath}/league/allLeagues" class="link-1">Competiciones</a></li>
			<li><a href="${pageContext.request.contextPath}/category/allCategories" class="link-1">Categorías</a></li>
			<li><a href="${pageContext.request.contextPath}/team/allTeams" class="link-1">Equipos</a></li>
			<li><a href="${pageContext.request.contextPath}/venue/allVenues" class="link-1">Pistas</a></li>
		</s:if>
		<s:else>
			<li>Pistas</li>
			<li><a href="${pageContext.request.contextPath}/venue/allVenues" class="link-1">Todas las pistas</a></li>
		</s:else>
			
		</ul>
	</div>
	<div class="developer">
		<img src="${pageContext.request.contextPath}/images/Logo.png" width="10%">  © <s:date name="%{new java.util.Date()}" format="yyyy"/> by Nabil El Boudjay
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
</div>