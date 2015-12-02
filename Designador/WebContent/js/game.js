$(function() {
	
	$(".publish-game").click(function(){
	
		var unpublishedGame = $(this);
		unpublishedGame.hide();
		$.ajax({
			type : "GET",
			url : "publishGame",
			data : {
				idGame	: $(this).parents("tr").attr("id")
			},
			success : function(result) {
				unpublishedGame.next(".published").show();	
				
				if($(".published-num").length)
					$(".published-num span").text(Number($(".published-num span").text()) + 1);

				else
					$(".unpublished-num").before("<li class='published-num'><a href='games?is=published'>" 
								+ "Publicados<span  class='success'>1</span></a></li>");
					
				if($(".unpublished-num span").text() == 1)
					$(".unpublished-num").remove();
				else
					$(".unpublished-num span").text(Number($(".unpublished-num span").text()) - 1);
					
			}
		});
	});
	
	$(".select-div").click(function(){

		if($("option:selected",this).val() == 0){
			$(this).next().show();
			$(this).nextAll(".select-div").show();
		}else{
			$(this).next().hide();
			$(this).nextAll(".select-div").hide();
		}
	});
	
	$("[id^=refereeType]").click(function(){
		if($(this).is(":checked"))
			$(this).nextAll(".select-div").css("display","");
		else
			$(this).nextAll(".select-div").css("display","none");

	});

	
	$("#homeTeam, #awayTeam").click(function(){
		$(this).parent().nextAll(".error-field").css("display", "none");
		$(this).parent().next().css({"border-color" : ""});
	});
	
	
	$('form').on('submit',function(){
		
		var noWarn = true;
		
		if($("#homeTeam").length){
			if($("#homeTeam").val() != 0){
				
				if($("#homeTeam").val() == $("#awayTeam").val() )
					noWarn = warnEqualTeams();
					
				else if($("#homeTeam option:selected").text().toLowerCase() == $.trim($("#awayTeamName").val().toLowerCase()))
					noWarn = warnEqualTeams();
			}
			else{
				
				if($("#awayTeam").val() == 0){
				
					if($.trim($("#homeTeamName").val()).length && $.trim($("#awayTeamName").val()).length  &&
							($.trim($("#homeTeamName").val().toLowerCase()) == $.trim($("#awayTeamName").val().toLowerCase())))
						noWarn = warnEqualTeams();
				}
				else if($.trim($("#homeTeamName").val().toLowerCase()) == $("#awayTeam option:selected").text().toLowerCase())
					noWarn = warnEqualTeams();
			}
		}
		
		$("[id^=refereeType]").each(function(){
			if($(this).is(":checked"))
				$(this).next().val(true);
			else
				$(this).next().val(false);
		});
		
		return noWarn;
	});	

	function warnEqualTeams(){
		
		$("#awayTeam").parent().nextAll(".error-field-2").css("display", "block");
		$("#awayTeam").parent().css({
					"border-color" : "#b94a48",
					"border-style" : "solid",
					"border-width" : "1px"});
		
		return false;
	}
	
	$(".assign").click(function(){
		
		$(this).prev().show();
		$(this).nextAll().hide();
		$(this).hide();
		
		var gameRow = $(this).closest("tr");
		
		var selectDivWidth = 0;
		var selectDiv = gameRow.find(".select-div:first").show();
		
		gameRow.find(".select-div:first").find("option").each(function(){
			if($(this).width() > selectDivWidth)
				selectDivWidth = $(this).width();
		});
		
		gameRow.find(".select-div").each(function (){
			$(this).prev().hide();
			$(this).show();
			$(this).css("width",selectDivWidth + 35);
				
		});
		
		gameRow.nextAll(".save-assignment:first").show();
		
		$(".games > tbody").children("tr:nth-child(3n + 2)").each(function(){
			$(this).children("td:nth-child(2)").children("span").html("<br>vs<br>");
		});
		
	});
	
	$(".cancel, .save").click(function(){
		
		$(this).closest("tr").hide();
		var gameRow = $(this).closest("tr").prev().prev();
				
		if($(this).hasClass("save")){
			var refereeTypes = [];
			var idUsers = [];
			var selectDiv;
			gameRow.find(".row").each(function(i){
				
				selectDiv = $(this).find(".select-div");
				if(selectDiv.length){
					
					refereeTypes[i] = true; 
					idUsers[i] = $("option:selected",this).val();

					selectDiv.css("width","");
					selectDiv.hide();
					
					selectDiv.prev().remove();
					if(selectDiv.find("option:selected").val() == 0){
						selectDiv.before("<div class='not-assigned'>No Designado</div>");
					}
					else{
						selectDiv.before("<div style='white-space:nowrap;'><a class='link' href='/Designador/user/user?idUser=" + 
								selectDiv.find("option:selected").val() + "'>" +
								selectDiv.find("option:selected").text().split(",",1) +	"</a>"
								+ " <img src='/Designator/images/warning-icon.png'"  +
								"title='El árbitro aún no ha confirmado su designación a este partido' class='confirmation'></div>");
					}				
				}
				else{
					refereeTypes[i] = false
					idUsers[i] = 0;
				}
			});
			
			$.ajax({
				type : "POST",
				url : "assignGame",
				data : {
					idGame:	gameRow.attr("id"),
					refereeTypes: refereeTypes,
					idUsers: idUsers
				},
			    traditional: true,
			    success: function(result){
			    //	console.log(result);
			    	document.open();
			    	document.write(result);
			    	document.close();
			       // $('.answer').html(result);
			     }
			});
		}
		else{
			
			gameRow.find(".select-div").each(function(){	
				$(this).css("width","");
				$(this).hide();
				$(this).prev().show();

			});
		}
		
		
		var assignDiv = gameRow.find(".conflicts");
		assignDiv.nextAll().show();
		assignDiv.hide();
		
		if($(".save-assignment:visible").length == 0)
			$(".games > tbody").children("tr:nth-child(3n + 2)").each(function(){
				$(this).children("td:nth-child(2)").children("span").html("vs");
			});
		
	});
	
	function showAssigned(e){
		
		e.closest("tr").hide();
		var gameRow = e.closest("tr").prev().prev();
				
		gameRow.find(".select-div").each(function(){
			$(this).css("width","");
			$(this).hide();
			$(this).prevAll().remove();
			if($(this).find("option:selected").val() == 0){
				$(this).before("<div class='not-assigned'>No Designado</div>");
			}
			else{
				$(this).before("<a class='link' href='/Designador/user/user?idUser=" + 
						$(this).find("option:selected").val() + "'>" +
						$(this).find("option:selected").text().split(",",1) +	"</a>"
						+ " <img src='/Designator/images/warning-icon.png'"  +
						"title='El árbitro aún no ha confirmado su designación a este partido' class='confirmation'>");
			}

		});
		
		var assignDiv = gameRow.find(".conflicts");
		assignDiv.nextAll().show();
		assignDiv.hide();
		
		if($(".save-assignment:visible").length == 0)
			$(".games > tbody").children("tr:nth-child(3n + 2)").each(function(){
				$(this).children("td:nth-child(2)").children("span").html("vs");
			});
	}
	
	$(document).on("mouseover", ".conflicts", function(){
		$(this).find(".conflicts-types").show();
	});
	

	$(document).on("mouseout", ".conflicts", function(){
		$(this).find(".conflicts-types").hide();		
	});
	
	/*$(function () {
		$.datepicker.setDefaults($.datepicker.regional["es"]);
		
		$("#datepicker").datepicker();
		
		$("#datepicker").click(function(){
			$('#ui-datepicker-div').css("top",$(this).position().top);

		});
	});*/
	
	
});
