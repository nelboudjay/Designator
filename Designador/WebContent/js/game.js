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
	
	$(function () {
		$.datepicker.setDefaults($.datepicker.regional["es"]);
		
		$("#datepicker").datepicker();
	});
	
	
});
