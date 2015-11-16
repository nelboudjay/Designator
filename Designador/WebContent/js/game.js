$(function() {
	
	$(".publish-game").click(function(){
	
		var unpublishedGame = $(this);
		unpublishedGame.hide();
		$.ajax({
			type : "GET",
			url : "publishGame?method:publishGame",
			data : {
				idGame	: $(this).parents("tr").attr("id")
			},
			success : function(result) {
				unpublishedGame.next(".published").show();	
			}
		});
	});
	
	
	$(".show-all-agmes").click(function(){
		$(this).hide();
		$(".show-future-games").show();
		
	/*	$(".available-referees > tbody > tr").each(function(){
			
			if($("td:nth-child(2)",this).hasClass("cross"))
				$(this).hide();
		})
		
		$(".available-referees > tbody  tr:visible").filter(":odd").css("background","inherit");
		$(".available-referees > tbody  tr:visible").filter(":even").css("background","#e8e7e6 none repeat scroll 0 0");*/
	});
	
	$(".show-future-games").click(function(){
		$(this).hide();
		$(".show-all-agmes").show();

		/*$(".available-referees > tbody > tr").each(function(){
			
			if($("td:nth-child(2)",this).hasClass("cross"))
				$(this).show();
		})
		$(".available-referees > tbody  tr").filter(":odd").css("background","inherit");
		$(".available-referees > tbody  tr").filter(":even").css("background","#e8e7e6 none repeat scroll 0 0");*/
	});
	
	
});
