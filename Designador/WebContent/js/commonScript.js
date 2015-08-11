function warn(e){
	
	e.next().css("display", "block");
	e.css({"border-color" : "#b94a48", "border-style" : "solid"});								
	return false;
}

function validate(){
		var noEmpty = true;
		$('.required-field').css("border-color" , "");
		$('.error-field').css("display","none");
		$('.required-field').each(function(){
			if ($.trim($(this).val()) == '') 
				noEmpty = warn($(this));
		});
		
		if(noEmpty){
			var fieldValue = "";
			$(".identical-field").each(function(){
				if(fieldValue == "")
					fieldValue = $(this).val();
				else if (fieldValue != $(this).val())
					noEmpty = warn($(this));
					
			});
		}
		
		if ( $("#zipcode").length && !($("#zipcode").val()).match(/^(\d{5}|)$/)) 
			noEmpty =  warn($("#zipcode"));
		
		if ( $(".email").length) {
			$('.email').each(function(){
				console.log($.trim($(this).val()));
				if (!$.trim($(this).val()).match(/^[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}$/)) 
					noEmpty = warn($(this));
			});
		}

		
		return noEmpty;
}

$(document).ready(function() {
	
	$('.close').click(function() {
		$(".boxMessage, .error, .errors").remove();
	});
	
	
	$('.userName').click(function(evt) {
		evt.stopPropagation();
		$('.dropdown-menu').slideToggle("drop");
		$(this).toggleClass("shine");
	});

	$(document).click(function() {
		$('.dropdown-menu').slideUp(function() {
			$('.userName').removeClass("shine");
		});
	});
	
	$('#leftMenu li').click(function() {
		if(!$(this).hasClass("active")){
			
			var imageName = $('#leftMenu .active').find("img").attr("src");
			$('#leftMenu .active').find("img").attr("src",imageName.slice(0,imageName.length - 13) + ".png");	
			
			$('#leftMenu .active').removeClass("active");
			$(this).addClass("active");
		}
	});
	
	$('#leftMenu li, .dropdown-menu').hover(
			function() {
				if(!$(this).hasClass("active")){
					var imageName = $(this).find("img").attr("src");
					$(this).find("img").attr("src",imageName.slice(0,imageName.length - 4) + "-selected.png");
				}
			},
			function() {
				if(!$(this).hasClass("active")){
					var imageName = $(this).find("img").attr("src");
					$(this).find("img").attr("src",imageName.slice(0,imageName.length - 13) + ".png");
				}
			}
	);
	
	
	$('form').on('submit',validate);	
	
	$(document).on("keyup", "textarea", function() {
		$(this).css('height', 'auto');
		$(this).height(this.scrollHeight);

	});
	
	$(document).find("textarea").keyup();


});