<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="sidebar-background"></div>
<div id="leftMenu">
		<ul>
		<li class="${context['struts.actionMapping'].name == 'homePage' ? 'active' : '' }">
			<span class="glow"></span>
			<a href="${pageContext.request.contextPath}/homePage"><img class="left-menu-icon" src="${pageContext.request.contextPath}/images/home-icon.png">Inicio</a>
		</li>
			<li class="dark-nav
				<s:if test="#context['struts.actionMapping'].name in 
					{'games', 'game', 'addEditGame'}">active dark-nav-active
				</s:if>
			">
				<span class="glow"></span>
				<a><img class="left-menu-icon" src="${pageContext.request.contextPath}/images/game-icon.png">Partidos
				<span class="arrow"></span>
				</a>
				<ul style="display:
					<s:if test="#context['struts.actionMapping'].name in 
						{'games', 'game', 'addEditGame'}">block</s:if>
					<s:else>none</s:else>
				">
					<s:if test="#session.user.userRole != 2">
						<li><a href="${pageContext.request.contextPath}/game/games">Todos Los Partidos
								<span class="normal"><s:property value="#session.futureGames"/></span>
							</a>
						</li>
					</s:if>
					<s:if test="#session.user.userRole != 1">
						<li><a href="${pageContext.request.contextPath}/game/myGames?idUser=${session.user.idUser}">Mis Partidos</a></li>
						<li><a href="${pageContext.request.contextPath}/game/myUnconfirmedGames=${session.user.idUser}">Mis Partidos no Confirmados</a></li>
					</s:if>
					<s:if test="#session.user.userRole != 2">
						<s:if test="#session.unassignedGames != null && #session.unassignedGames > 0">
							<li><a href="${pageContext.request.contextPath}/game/games?is=unassigned">No Designados
									<span class="warning"><s:property value="#session.unassignedGames"/></span>
								</a>
							</li>
						</s:if>
						<s:if test="#session.confirmedGames != null && #session.confirmedGames > 0">
							<li><a href="${pageContext.request.contextPath}/game/games?is=confirmed">Confirmados
									<span  class="success">
										<s:property value="#session.confirmedGames"/>
									</span>
								</a>
							</li>
						</s:if>
						<s:if test="#session.unconfirmedGames != null && #session.unconfirmedGames > 0">
							<li><a href="${pageContext.request.contextPath}/game/games?is=unconfirmed">No Confirmados
									<span class="warning"><s:property value="#session.unconfirmedGames"/></span>
								</a>
							</li>
						</s:if>
						<s:if test="#session.unpublishedGames != null 
											&& #session.futureGames - #session.unpublishedGames > 0">
							<li class="published-num"><a href="${pageContext.request.contextPath}/game/games?is=published">Publicados
									<span  class="success">
										<s:property value="#session.futureGames - #session.unpublishedGames"/>
									</span>
								</a>
							</li>
						</s:if>
						<s:if test="#session.unpublishedGames != null && #session.unpublishedGames > 0">
							<li class="unpublished-num"><a href="${pageContext.request.contextPath}/game/games?is=unpublished">No Publicados
									<span  class="warning"><s:property value="#session.unpublishedGames"/></span>
								</a>
							</li>
						</s:if>
					</s:if>
				</ul>
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
		<li class="
				<s:if test="#context['struts.actionMapping'].name in  {'users','user','editUser','addUser'}">
						active
				</s:if>		
		">
			<span class="glow"></span>
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