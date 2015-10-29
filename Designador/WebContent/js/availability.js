$(function() {
	
	$(".month-calendar .link-2").click(function(){
		
		if($(this).attr("data-availability") == 0 ){
		
		
			if($("#availableDates .no-dates").length)
				$("#availableDates .no-dates").hide();
			
			$(this).text("Desactivar");
			$(this).attr("data-availability",1);
			$("#availableDates h3").after("<div data-date='" + $(this).attr("data-date") + "'><img title='Eliminar esta fecha' class='garbage' src='images/garbage-icon.png'>" + $(this).attr("data-dayName") + " " + $(this).attr("data-day")
					 								+ " de " + $(this).attr("data-month") + "</div>");
		}
		else{
			
			$(this).text("Activar");
			$(this).attr("data-availability",0);
			$("#availableDates div[data-date='" + $(this).attr("data-date") + "']").remove();
			
			if($("#availableDates").children().length == 2)
				$("#availableDates .no-dates").show();
		}
	});
	
	
	$(document).on("click",".garbage", function(){
		$(this).parent().remove();
		if($("#availableDates").children().length == 2)
			$("#availableDates .no-dates").show();
	});
	
});
