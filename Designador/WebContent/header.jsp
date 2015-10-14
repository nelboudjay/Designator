<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url includeContext="false" var="currentAction" />


<s:if test="#session.user != null">
	<jsp:include page="idle-timeout.html"/>
</s:if>

<nav>
	<ul class="logo">
		<li><s:if test="#session.user == null">
				<img src="images/Logo.png" width="12%" height="90%">
			</s:if> <s:else>
				<a href="homePage"><img src="images/Logo.png" width="12%"
					height="90%"></a>
			</s:else></li>
	</ul>
	<s:if test="#session.user != null">

		<ul class="navbar-right">
			<li class="userName"><span>
				<s:if test="#session.user.userProfile.picture == null">
						<img class="profile-icon" src="images/avatar-icon.png">
				</s:if>
				<s:else>
						<img class="profile-icon" src="getImage?profileImage=true">
				</s:else>
					 ${session.user.userFullName}
					(${session.user.userName}) <span class="arrow"></span> </span></li>
			<li id="profile" class="dropdown-menu"><a href="user?idUser=${session.user.idUser}"><img class="small-icon" src="images/profile-icon.png"><span>Perfil</span></a></li>
			<li id="messages" class="dropdown-menu"><a><img class="small-icon" src="images/messages-icon.png"><span>Mensajes</span></a></li>
			<li id="logout" class="dropdown-menu"><a href="logout"><img class="small-icon" src="images/logout-icon.png"><span>Cerrar
					sesión</span></a></li>

		</ul>
	</s:if>
	<s:elseif test="#currentAction != '/login'">
		<ul class="navbar-right">
			<li id="login"><a href="login">Login</a></li>
		</ul>
	</s:elseif>	

</nav>
