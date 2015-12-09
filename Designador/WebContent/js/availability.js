$(function() {
	
	$(".month-calendar .link-2").click(function(){
		
		var calendarCell = $(this);

		if($(this).attr("data-available") == 0 ){
		
			$.ajax({
				type : "POST",
				url : "addAvailability",
				data : {
					idUser	: $(".user-menu").attr("id"),
					dateStr : $(this).data("date")
				},
				success : function(result) {
					
					if($("#availableDates .no-dates").length)
						$("#availableDates .no-dates").hide();
					
					calendarCell.text("Desactivar");
					calendarCell.prev().removeClass("cross");
					calendarCell.prev().addClass("check");
					calendarCell.attr("data-available",1);
					$("#availableDates .no-dates").after("<div data-day='" + calendarCell.data("day") + "' data-date='" + calendarCell.data("date") + 
							"'><img title='Eliminar esta fecha' class='garbage' src='../images/garbage-icon.png'>"
							+ calendarCell.data("dayname") + " " + calendarCell.data("day")
							 				+ " de " + calendarCell.data("month") + "</div>");
					
					sortAvailableDates();
					
				}
			});
			
		}
		else{
			
			$.ajax({
				type : "POST",
				url : "deleteAvailability",
				data : {
					idUser	: $(".user-menu").attr("id"),
					dateStr : $(this).data("date")
				},
				success : function(result) {

					calendarCell.text("Activar");
					calendarCell.prev().removeClass("check");
					calendarCell.prev().addClass("cross")
					calendarCell.attr("data-available",0);
					$("#availableDates div[data-day='" + calendarCell.attr("data-day") + "']").remove();
					
					if($("#availableDates").children().length == 2)
						$("#availableDates .no-dates").show();
					else
						sortAvailableDates();
				}
			});
		}
	});
	
	
	$(document).on("click",".garbage", function(){
		
		var  availableDate = $(this).parent();
		$.ajax({
			type : "POST",
			url : "deleteAvailability",
			data : {
				idUser	: $(".user-menu").attr("id"),
				dateStr : availableDate.data("date")
			},
			success : function(result) {

				availableDate.remove();
				
				var calendarCell = $(".month-calendar").find("[data-day='" + availableDate.data("day") + "']");
				calendarCell.text("Activar");
				calendarCell.attr("data-available",0);
				calendarCell.prev().removeClass("check");
				calendarCell.prev().addClass("cross");
				
				if($("#availableDates").children().length == 2)
					$("#availableDates .no-dates").show();
			}
		});
		
		
		
	});
	
	
	$(".show-available-referees").click(function(){
		$(this).hide();
		$(".show-all-referees").show();
		
		$(".available-referees > tbody > tr").each(function(){
			
			if($("td:nth-child(2)",this).hasClass("cross"))
				$(this).hide();
		})
		
		$(".available-referees > tbody  tr:visible").filter(":odd").css("background","inherit");
		$(".available-referees > tbody  tr:visible").filter(":even").css("background","#e8e7e6 none repeat scroll 0 0");
	});
	
	$(".show-all-referees").click(function(){
		$(this).hide();
		$(".show-available-referees").show();

		$(".available-referees > tbody > tr").each(function(){
			
			if($("td:nth-child(2)",this).hasClass("cross"))
				$(this).show();
		})
		$(".available-referees > tbody  tr").filter(":odd").css("background","inherit");
		$(".available-referees > tbody  tr").filter(":even").css("background","#e8e7e6 none repeat scroll 0 0");
	});
	
	$(function () {
		$.datepicker.setDefaults($.datepicker.regional["es"]);
		
		$("#datepicker").datepicker();
		
		$("#datepicker").click(function(){
			$('#ui-datepicker-div').css("top",$(this).position().top);

		})
	});
	
	function sortAvailableDates(){
		
		var availableDatesList = $("#availableDates").children('*[data-day]');
		availableDatesList.sort(function(a,b){
		
			if( parseInt(a.dataset.day) < parseInt(b.dataset.day) )
				return -1;
			else
				return 1;
		}); 
		
		$("#availableDates").children('*[data-day]').remove();
		$("#availableDates .no-dates").after(availableDatesList);
	}
	
});
