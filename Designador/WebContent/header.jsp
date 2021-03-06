<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url includeContext="false" var="currentAction" />

<nav>
	<ul class="logo">
		<li><s:if test="#session.user == null">
				<img src="${pageContext.request.contextPath}/images/Logo.png" width="12%" height="90%">
			</s:if> <s:else>
				<a href="${pageContext.request.contextPath}/homePage"><img src="${pageContext.request.contextPath}/images/Logo.png" width="12%"
					height="90%"></a>
			</s:else></li>
	</ul>
	<s:if test="#session.user != null">

		<ul class="navbar-right">
			<li class="userName">
				<span>
					<img class="profile-icon" src="getImage?idUser=${session.user.idUser}">
					 ${session.user.userFullName} <span class="arrow"></span> 
				</span>
			</li>
			<li id="profile" class="dropdown-menu"><a href="${pageContext.request.contextPath}/user/user?idUser=${session.user.idUser}"><img class="small-icon" src="${pageContext.request.contextPath}/images/profile-icon.png"><span>Perfil</span></a></li>
			<li id="logout" class="dropdown-menu"><a href="${pageContext.request.contextPath}/logout"><img class="small-icon" src="${pageContext.request.contextPath}/images/logout-icon.png"><span>Cerrar
					sesi�n</span></a></li>

		</ul>
	</s:if>
	<s:elseif test="#currentAction != '/login'">
		<ul class="navbar-right">
			<li id="login"><a href="login">Login</a></li>
		</ul>
	</s:elseif>	

</nav>
