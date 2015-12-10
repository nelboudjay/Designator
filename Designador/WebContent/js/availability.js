$(function() {
	
	$(document).on("click",".month-calendar .link-2", function(){
		
		var calendarCell = $(this);

		if($(this).attr("data-available") == 0 ){
		
			$.ajax({
				type : "POST",
				url : "addAvailability",
				data : {
					idUser	: $(".user-menu").attr("id"),
					dateStr : $(this).data("date"),
					startTime : "",
					endTime : ""
				},
				success : function(result) {
					$(".month-calendar").replaceWith($(result).filter("div.main-content").find(".month-calendar"));
					$("#availableDates").replaceWith($(result).filter("div.main-content").find("#availableDates"));
					
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

					$(".month-calendar").replaceWith($(result).filter("div.main-content").find(".month-calendar"));
					$("#availableDates").replaceWith($(result).filter("div.main-content").find("#availableDates"));
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

				$(".month-calendar").replaceWith($(result).filter("div.main-content").find(".month-calendar"));
				$("#availableDates").replaceWith($(result).filter("div.main-content").find("#availableDates"));
			}
		});
		
	});
	
	
	$(document).on("click",".show-available-referees",function(){
		$(this).hide();
		$(".show-all-referees").show();
		
		$(".available-referees > tbody > tr").each(function(){
			
			if($("td:nth-child(2)",this).hasClass("cross"))
				$(this).hide();
		})
		
		$(".available-referees > tbody  tr:visible").filter(":odd").css("background","inherit");
		$(".available-referees > tbody  tr:visible").filter(":even").css("background","#e8e7e6 none repeat scroll 0 0");
	});
	
	$(document).on("click",".show-all-referees",function(){
		$(this).hide();
		$(".show-available-referees").show();

		$(".available-referees > tbody > tr").each(function(){
			
			if($("td:nth-child(2)",this).hasClass("cross"))
				$(this).show();
		})
		$(".available-referees > tbody  tr").filter(":odd").css("background","inherit");
		$(".available-referees > tbody  tr").filter(":even").css("background","#e8e7e6 none repeat scroll 0 0");
	});
	
	$(document).on("click",".editAvailability > .link, .add-availability > a",function(){
		$(this).parent().next().css("display","inline-block");
		$(this).parent().hide();
	});
	
	$(document).on("click",".availability-form > .cancel",function(){
		$(this).parent().prev().show();
		$(this).parent().hide();
		
		$(this).prevAll("[id^=datepicker]").css({"border-color" : "", "border-style" : ""});
		if($(this).nextAll(".error-field").length)
			$(this).nextAll(".error-field").hide();
		else
			$(this).parent().nextAll(".error-field").hide();

	});
	
	
	$(document).on("click",".availability-form > .save",function(){
		
		var  dateStr = $(this).prevAll("[id^=datepicker]");
		var  startTime = $(this).prevAll("#startTime").val();
		var  endTime = $(this).prevAll("#endTime").val();
		
		if(dateStr.val() == ""){
			if($(this).nextAll(".error-field").length)
				$(this).nextAll(".error-field").show();
			else
				$(this).parent().nextAll(".error-field").show();

			dateStr.css({"border-color" : "#b94a48", "border-style" : "solid"});	

		}
		else{
			
			$.ajax({
				type : "POST",
				url : "addAvailability",
				data : {
					idUser	: $(".user-menu").attr("id"),
					dateStr : dateStr.val(),
					startTime : startTime,
					endTime : endTime
				},
				success : function(result) {
					$(".month-calendar").replaceWith($(result).filter("div.main-content").find(".month-calendar"));
					$("#availableDates").replaceWith($(result).filter("div.main-content").find("#availableDates"));
					
				}
			});
		}
	
	});
	
	
	$(function () {
		$.datepicker.setDefaults($.datepicker.regional["es"]);
		
		$(document).on("focus","[id^=datepicker]",function(){
			$(this).datepicker();
		});
		
		
		$(document).on("click", "[id^=datepicker]", function(){
			console.log("hi");
			$('#ui-datepicker-div').css("top",$(this).position().top);

		})
	});
	
});
