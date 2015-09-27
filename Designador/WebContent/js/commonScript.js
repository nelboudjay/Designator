function warn(e){
	e.next().css("display", "block");
	e.css({"border-color" : "#b94a48", "border-style" : "solid"});								
	return false;
}

function validate(){
		var noEmpty = true;
		$("input", this).css("border-color" , "");
		$('.error-field').css("display","none");
		$('.required-field').each(function(){
			if ($.trim($(this).val()) == '') {
				if($(this).hasClass("email"))
					$(this).next().text("Correo Electrónico principal no puede estar en blanco.");
				noEmpty = warn($(this));
			}
		});
		
		if(noEmpty){
			var fieldValue = "";
			$(".identical-field").each(function(){
				if(fieldValue == "")
					fieldValue = $(this).val();
				else if (fieldValue != $(this).val()){
					noEmpty = warn($(this));	
				}
			});
		}
		
		if (!($("#zipcode").val()).match(/^(\d{5}|)$/)) 
			noEmpty =  warn($("#zipcode"));
		
		if (!($("#homePhone").val()).match(/^\+?(\d{9,}|)$/))
			noEmpty =  warn($("#homePhone"));
		
		if (!($("#mobilePhone").val()).match(/^\+?(\d{9,}|)$/))
			noEmpty =  warn($("#mobilePhone"));

		$('.email').each(function(){
			if (!$.trim($(this).val()).match(/^(([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)|)$/i)) {
				$(this).next().text("Correo electrónico no válido.");	
				noEmpty = warn($(this));
			}
		});
		

		
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

	if($(".errorMessage").children().length > 1)
		$(".errorMessage li").css({"list-style-type":"initial","margin-left":"10px"});
	
});