<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="sidebar-background"></div>
<div id="leftMenu">
		<ul>
		<li class="${context['struts.actionMapping'].name == 'homePage' ? 'active' : '' }">
			<span class="glow"></span>
			<a href="${pageContext.request.contextPath}/homePage"><img class="left-menu-icon" src="${pageContext.request.contextPath}/images/home-icon.png">Inicio</a>
		</li>
		<li class="${context['struts.actionMapping'].name == 'games' ? 'active' : '' }"><span class="glow"></span>
			<a href="${pageContext.request.contextPath}/games"><img class="left-menu-icon" src="${pageContext.request.contextPath}/images/game-icon.png">
				Partidos<span class="arrow"></span></a>
		</li>
		
		<s:if test="#session.user.userRole == 3">
			<li class="dark-nav 
				<s:if test="#context['struts.actionMapping'].name in {'availability','allAvailability'}">active dark-nav-active
				</s:if>
			">
				<span class="glow"></span>
				<a><img class="left-menu-icon" src="${pageContext.request.contextPath}/images/calendar-icon.png">Disponibilidad
				<span class="arrow"></span>
				</a>
				<ul style="display:
					<s:if test="#context['struts.actionMapping'].name in {'availability','allAvailability'}">block</s:if>
					<s:else>none</s:else>
				">
					<li><a href="${pageContext.request.contextPath}/availability/allAvailability">Toda La Disponibilidad</a></li>
					<li><a href="${pageContext.request.contextPath}/availability/availability?idUser=${session.user.idUser}">Mi Disponibilidad</a></li>
				</ul>
			</li>
		</s:if>
		<s:elseif test="#session.user.userRole == 1">
			<li class="${context['struts.actionMapping'].name == 'allAvailability' ? 'active' : '' }">
				<span class="glow"></span>
				<a href="${pageContext.request.contextPath}/availability/allAvailability"><img class="left-menu-icon"
				src="${pageContext.request.contextPath}/images/calendar-icon.png">Disponibilidad
				</a>
			</li>
		</s:elseif>
		<s:else>
			<li class="${context['struts.actionMapping'].name == 'availability' ? 'active' : '' }">
				<span class="glow"></span>
				<a href="${pageContext.request.contextPath}/availability/availability?idUser=${session.user.idUser}"><img class="left-menu-icon"
				src="${pageContext.request.contextPath}/images/calendar-icon.png">Disponibilidad
				</a>
			</li>
		</s:else>
		<li class="${context['struts.actionMapping'].name == 'users' ? 'active' : '' }"><span class="glow"></span>
			<a href="${pageContext.request.contextPath}/user/users"><img class="left-menu-icon"
			src="${pageContext.request.contextPath}/images/users-icon.png">Miembros</a></li>
		
		<s:if test="#session.user.userRole != 2">
			<li class="dark-nav 
				<s:if test="#context['struts.actionMapping'].name in 
						{'allLeagues','addEditLeague','allCategories','addEditCategory', 
						'allTeams','addEditTeam', 'allVenues','addEditVenue'}">
						active dark-nav-active
				</s:if>
			">
				<span class="glow"></span>
				<a><img class="left-menu-icon" src="${pageContext.request.contextPath}/images/settings-icon.png">Mantenimiento
				<span class="arrow"></span>
				</a>
				<ul style="display:
					<s:if test="#context['struts.actionMapping'].name in 
						{'allLeagues','addEditLeague','allCategories','addEditCategory', 
						'allTeams','addEditTeam', 'allVenues','addEditVenue'}">
						block
					</s:if>
					<s:else>none</s:else>
				">
					<li><a href="${pageContext.request.contextPath}/league/allLeagues">
						<img class="left-menu-icon-small" src="${pageContext.request.contextPath}/images/league-icon.png">
							Competiciones
						</a>
					</li>
					<li><a href="${pageContext.request.contextPath}/category/allCategories">
						<img class="left-menu-icon-small" src="${pageContext.request.contextPath}/images/category-icon.png">
							Categorías
						</a>
					</li>
					<li><a href="${pageContext.request.contextPath}/team/allTeams">
						<img class="left-menu-icon-small" src="${pageContext.request.contextPath}/images/users-icon.png">
							Equipos
						</a>
					</li>
					<li><a href="${pageContext.request.contextPath}/venue/allVenues">
						<img class="left-menu-icon-small" src="${pageContext.request.contextPath}/images/venue-icon.png">
							Pistas
						</a>
					</li>
				</ul>
			</li>
		</s:if>
		
	</ul>
</div>