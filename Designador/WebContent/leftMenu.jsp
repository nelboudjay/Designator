<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="sidebar-background"></div>
<div id="leftMenu">
		<ul>
		<li class="${context['struts.actionMapping'].name == 'homePage' ? 'active' : '' }">
			<span class="glow"></span>
			<a href="homePage"><img class="left-menu-icon" src="images/home-icon.png">Inicio</a>
		</li>
		<li class="${context['struts.actionMapping'].name == 'games' ? 'active' : '' }"><span class="glow"></span>
			<a href="games"><img class="left-menu-icon" src="images/games-icon.png">
				Partidos<span class="arrow"></span></a>
		</li>
		
		<s:if test="#session.user.userRole == 3">
			<li class="dark-nav ${context['struts.actionMapping'].name == 'availability' ? 'active dark-nav-active' : '' }">
				<span class="glow"></span>
		
			<a><img class="left-menu-icon" src="images/calendar-icon.png">Disponibilidad
			<span class="arrow"></span>
			</a>
			<ul style="display:${context['struts.actionMapping'].name == 'availability' ? 'block' : 'none' };">
				<li><a href="allAvailability">Toda La Disponibilidad</a></li>
				<li><a href="availability?idUser=${session.user.idUser}">Mi Disponibilidad</a></li>
			</ul>
			
		</s:if>
		<s:elseif test="#session.user.userRole == 1">
			<li class="${context['struts.actionMapping'].name == 'availability' ? 'active' : '' }">
				<span class="glow"></span>
				<a href="allAvailability"><img class="left-menu-icon"
				src="images/calendar-icon.png">Disponibilidad
				</a>
			</li>
		</s:elseif>
		<s:else>
			<li class="${context['struts.actionMapping'].name == 'availability' ? 'active' : '' }">
				<span class="glow"></span>
				<a href="availability?idUser=${session.user.idUser}"><img class="left-menu-icon"
				src="images/calendar-icon.png">Disponibilidad
				</a>
			</li>
			
		</s:else>
		<li class="${context['struts.actionMapping'].name == 'users' ? 'active' : '' }"><span class="glow"></span><a href="users"><img class="left-menu-icon"
			src="images/users-icon.png">Miembros</a></li>
		
		<s:if test="#session.user.userRole != 2">
			<li class="dark-nav ${context['struts.actionMapping'].name == 'leagues' ? 'active dark-nav-active' : '' }">
				<span class="glow"></span>
		
			<a><img class="left-menu-icon" src="images/calendar-icon.png">Mantenimiento
			<span class="arrow"></span>
			</a>
			<ul style="display:${context['struts.actionMapping'].name == 'leagues' ? 'block' : 'none' };">
				<li><a href="leagues">Competiciones</a></li>
				<li><a href="categories">Categorías</a></li>
			</ul>
			
		</s:if>
		
	</ul>
</div>