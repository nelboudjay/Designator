<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="sidebar-background"></div>
<div id="leftMenu">
	<ul>
		<li class="active"><span class="glow"></span><a href="homePage"><img
				class="left-menu-icon" src="images/home-icon-selected.png">Inicio</a></li>
		<li><span class="glow"></span><a><img class="left-menu-icon"
				src="images/games-icon.png">Partidos<span class="arrow"></span></a></li>
		<li><span class="glow"></span><a href="availability?idUser=${session.user.idUser}"><img class="left-menu-icon"
				src="images/calendar-icon.png">Disponibilidad
				<s:if test="#session.user.isAdmin() && #session.user.userRole.userRoleName == 'All'">
						<span class="arrow"></span>
				</s:if>
		</a></li>
		<li><span class="glow"></span><a href="users"><img class="left-menu-icon"
			src="images/users-icon.png">Miembros</a></li>
		
		
	</ul>
</div>