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
		$(this).nextAll().hide();
		$(this).children("a").replaceWith("Conflictos");
		$(this).removeClass("assign");
		$(this).addClass("conflicts");
		$(this).css("position","relative");
	});
	
	$(document).on("mouseover", ".conflicts", function(){
		$(this).children(".conflicts-types").show();
	});
	

	$(document).on("mouseout", ".conflicts", function(){
		$(this).children(".conflicts-types").hide();		
	});
	
	$(function () {
		$.datepicker.setDefaults($.datepicker.regional["es"]);
		
		$("#datepicker").datepicker();
		
		$("#datepicker").click(function(){
			$('#ui-datepicker-div').css("top",$(this).position().top);

		});
	});
	
	
});
