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
	
	$(".assign-2").click(function(){
		$(this).parent().hide();
		$(".save-assignment-2, .conflicts-3").show();
		
		$("select").prevAll().hide();
		$("select").show();
		
	});
	
	
	$(".cancel-2, .save-2").click(function(){
		
		if($(this).hasClass("save-2")){
			var idGame = $(this).attr("id");
			var refereeTypes = [];
			var idUsers = [];
			var select;
			
			$(".game-info").find(".row").each(function(i){
				
				select = $(this).find("select");
				if(select.length){
					
					refereeTypes[i] = true; 
					idUsers[i] = $("option:selected",this).val();

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
					idGame:	idGame,
					refereeTypes: refereeTypes,
					idUsers: idUsers,
					dateStr:  $(".game-menu").data("date")
				},
			    traditional: true,
			    success: function(result){
			 
			    	$("#leftMenu").replaceWith($(result).filter("div#leftMenu"));

			    	$(".content-title").nextAll(".errors, .boxMessage").remove();

			    	var actionMessage =  $(result).find(".errors, .boxMessage");
			    	
			    	if(actionMessage.hasClass("boxMessage")){
			    		$(".content-title").after(actionMessage[1]);	
						$(".game-info").find(".row").each(function(i){
							
							select = $(this).find("select");
							
							select.prevAll().remove();
							if(select.length){
								
								if($("option:selected",this).val() == 0)
									select.before("<div class='not-assigned'>No Designado</div>");			
								else
									select.replaceWith($(result).find("#" + idGame).find("td:nth-child(5) > div:nth(" + i + ")").children());
								
							}
							select.prevAll().show();
							
						});
			    	}
			    	else{
			    		$(".content-title").after(actionMessage);			  
			    		$("select").prevAll().show();
			    	}
					$("select").hide();
			    }	
			});
		}
		else{
			$("select").prevAll().show();
			$("select").hide();
		}

		$(".save-assignment-2").prev().show();
		$(".save-assignment-2, .conflicts-3").hide();
		
		
	});
	
	
	$(document).on("click", ".assign", function(){
		
		$(this).prev().show();
		$(this).nextAll().hide();
		$(this).hide();
		
		var gameRow = $(this).closest("tr");
		
		gameRow.find("select").each(function (){
			$(this).prevAll().hide();
			$(this).show();
				
		});
		
		gameRow.nextAll(".save-assignment:first").show();
		
	});
	
	$(document).on("click", ".cancel, .save", function(){
		
		var saveAssigmentRow =  $(this).closest("tr");
		var gameRow = $(this).closest("tr").prev().prev();
				
		if($(this).hasClass("save")){
			var refereeTypes = [];
			var idUsers = [];
			var selectDiv;
			gameRow.find("td:nth-child(5) > div").each(function(i){
				
				select = $(this).find("select");
				if(select.length){
					
					refereeTypes[i] = true; 
					idUsers[i] = $("option:selected",this).val();
	
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
					idUsers: idUsers,
					is: is,
					dateStr: dateStr,
					idUser: idUser
				},
			    traditional: true,
			    success: function(result){
			    	
			    	$(".content-title").nextAll(".errors, .boxMessage").remove();

			    	$(".container").replaceWith($(result).filter("div.main-content").children(".container"));
			    	$("#leftMenu").replaceWith($(result).filter("div#leftMenu"));
			    	
			    	 var actionMessage =  $(result).find(".errors, .boxMessage");
			    	
			    	if(actionMessage.hasClass("boxMessage"))
			    		$(".content-title").after(actionMessage[1]);	
			    	else
			    		$(".content-title").after(actionMessage);
			    	
			     }
			});
			
		}
		else{
			saveAssigmentRow.hide();
			gameRow.find("select").each(function(){	
				$(this).hide();
				$(this).prevAll().show();
			});
			
			var assignDiv = gameRow.find(".conflicts");
			assignDiv.nextAll().show();
			assignDiv.hide();
			
		}
		
		
	});
	
	
	$(document).on("mouseover", '[class^="conflicts"]', function(){
		$(this).find(".conflicts-types").show();
	});
	
	$(document).on("mouseout", '[class^="conflicts"]', function(){
		$(this).find(".conflicts-types").hide();		
	});
	
	
	$(document).on("click", ".confirm, .decline", function(){

		var idGame = $(this).closest("tr").attr("id");
		if(!idGame)
			idGame = $(this).closest("table").attr("id");
		
		var refereeType = $(this).parent().parent().index() + 1;
		var confirmed = $(this).hasClass("confirm") ? true : false;
			
		$.ajax({
			type : "POST",
			url : "confirmGame",
			data : {
				idGame:	idGame,
				refereeType: refereeType,
				confirmed: confirmed
			},
			success: function(result){
		    	$("#leftMenu").replaceWith($(result).filter("div#leftMenu"));

		    	$(".content-title").nextAll(".errors, .boxMessage").remove();
		    	
		    	 var actionMessage =  $(result).find(".errors, .boxMessage");
		    	
		    	if(actionMessage.hasClass("boxMessage"))
		    		$(".content-title").after(actionMessage[1]);	
		    	else
		    		$(".content-title").after(actionMessage);
		     }
		});
		
		
		if($(this).hasClass("confirm")){
			if($(this).prev().hasClass("declined")){
				$(this).prev().replaceWith("<span class='confirmed' title='Has confirmado tu designación'>✓ </span>");
				$(this).replaceWith("<span title='Rechazar tu designación' class='btn decline'>✖︎</span>");
			}
			else
				$(this).replaceWith( "<span class='confirmed' title='Has confirmado tu designación'>✓ </span>");
		}else{
			if($(this).prev().hasClass("confirmed")){
				$(this).prev().replaceWith("<span class='declined' title='Has rechazado tu designación'>✖︎ </span>");
				$(this).replaceWith("<span title='Confirmar tu designación' class='btn confirm'>✓</span>");
			}
			else{
				$(this).prev().before( "<span class='declined' title='Has rechazado tu designación'>✖︎ </span>");
				$(this).remove();
			}
			
		}
		
	});
	
	$(document).on("click",".request, .cancel-request", function(){
		var clickedButton = $(this);
		var idGame = $(this).closest("tr").attr("id");
		if(!idGame)
			idGame = $(this).closest("table").attr("id");
		
		var refereeType =  $(this).closest(".row").index() + 1 ;
		var requested = $(this).hasClass("request") ? true : false;
		
		console.log(refereeType);
		$.ajax({
			type : "POST",
			url : "requestGame",
			data : {
				idGame:	idGame,
				refereeType: refereeType,
				requested: requested
			},
			success: function(result){
		    	
		    	$("#leftMenu").replaceWith($(result).filter("div#leftMenu"));

		    	$(".content-title").nextAll(".errors, .boxMessage").remove();
		    	
	    		var newData = $(result).filter("div.main-content").find("#" + idGame).
    			find("td:nth-child(5) > div:nth(" + (refereeType - 1) + ")");
	    		if(clickedButton.hasClass("request"))
	    			clickedButton.parent().parent().replaceWith(newData);
	    		else
	    			clickedButton.parent().replaceWith(newData);

		    	var actionMessage =  $(result).find(".errors, .boxMessage");
		    	
		    	if(actionMessage.hasClass("boxMessage"))
		    		$(".content-title").after(actionMessage[1]);	
		    	else
		    		$(".content-title").after(actionMessage);
		    	
		     }
		});
				
	});


	
	$(function () {
		$.datepicker.setDefaults($.datepicker.regional["es"]);
		
		$("#datepicker").datepicker();
		
		$("#datepicker").click(function(){
			$('#ui-datepicker-div').css("top",$(this).position().top);

		});
	});
	
	
});
