function validate(){
		var noEmpty = true;
		$('.required-field').css("border-color" , "");
		$('.error-field').css("display","none");
		$('.required-field').each(function(){
			if ($.trim($(this).val()) == '') {
				$(this).next().css("display", "block");
				$(this).css({"border-color" : "#b94a48", "border-style" : "solid"});								
				noEmpty =  false;
			}
		});
		
		if(noEmpty){
			var fieldValue = "";
			$(".identical-field").each(function(){
				if(fieldValue == "")
					fieldValue = $(this).val();
				else if (fieldValue != $(this).val()){
					$(this).next().css("display", "block");
					$(this).css({"border-color" : "#b94a48", "border-style" : "solid"});	
					noEmpty =  false;
				}
					
			});
		}
		
		return noEmpty;
}

$(document).ready(function() {
	
	$('.close').click(function() {
		$(".boxMessage, .error, .errors").remove();
	});
	
	
	$('#userName').click(function(evt) {
		evt.stopPropagation();
		$('.dropdown-menu').slideToggle("drop");
		$(this).toggleClass("userName");
	});

	$(document).click(function() {
		$('.dropdown-menu').slideUp(function() {
			$('#userName').removeClass("userName");
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