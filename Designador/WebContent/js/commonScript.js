function warn(e){
	if(e.parent(".select-div").length){
		e.parent(".select-div").next(".error-field").css("display", "block");
		e.parent(".select-div").css({
				"border-color" : "#b94a48",
				"border-style" : "solid",
				"border-width" : "1px"});
	}
	else{
		e.next().css("display", "block");
		e.css({"border-color" : "#b94a48", "border-style" : "solid"});	
	}
	
	return false;
}

function validate(){
		
		var noEmpty = true;
		$("input", this).css("border-color" , "");
		$('.error-field').css("display","none");
		$('.required-field').each(function(){
			if ($.trim($(this).val()) == '' && $(this).is(":visible")) {
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
		

		if ( $("#zipcode").length && !($("#zipcode").val()).match(/^(\d{5}|)$/)) 
			noEmpty =  warn($("#zipcode"));
		
		if ( $("#homePhone").length && !($("#homePhone").val()).match(/^\+?(\d{9,}|)$/))
			noEmpty =  warn($("#homePhone"));
		
		if ($("#mobilePhone").length && !($("#mobilePhone").val()).match(/^\+?(\d{9,}|)$/))
			noEmpty =  warn($("#mobilePhone"));

		$('.email').each(function(){
			if (!$.trim($(this).val()).match(/^(([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)|)$/i)) {
				$(this).next().text("Correo electrónico no válido.");	
				noEmpty = warn($(this));
			}
		});

		
		if($("#picture").length && $("#picture")[0].files && $("#picture")[0].files[0] ){
			 
			if($.inArray($("#picture")[0].files[0].type, ["image/png","image/gif","image/jpeg","image/jpg"]) == -1 ) {
			
				$("#picture").next().text("Formato de foto no válido. (Formatos válidos: png, gif, jpeg y jpg).");
				noEmpty =  warn($("#picture"));
			}
			else if ($("#picture")[0].files[0].size > 2700000){
				$("#picture").next().text("El tamaño de la imagen excede el tamaño permitido: 2.5Mb ");
				noEmpty =  warn($("#picture"));
			}
		}
		
		if($(".referee-types [id^=refereeType]").length){
			if(!$("[id^=refereeType]").is(":checked")){
				$(".referee-types  .error-field").css("display", "block");
				noEmpty = false;
			}
			else{
				$("[id^=refereeType]").each(function(){
					if($(this).is(":checked"))
						$(this).next().val(true);
					else
						$(this).next().val(false);
				});
			}
		
		}
		
		return noEmpty;
}

$(document).ready(function() {
	
	$(document).on("click", ".close", function() {
		$(".boxMessage, .error, .errors").remove();
	});
	
	$('.close2').click(function() {
		$(this).closest("h3").removeClass("profile-image");
		$(this).parent().hide();
		$("#picture")[0].value = "";
		$("#profileImage > input").val(false);
		
		$('#picture').css({"border-color" : "", "border-style" : ""});
		$("#picture").next().css("display","none");

		
	});
	
	$("#picture").change(function(){
		
		$('#picture').css({"border-color" : "", "border-style" : ""});
		$("#picture").next().css("display","none");
		
		var input = $(this)[0];
		if (input.files && input.files[0]) {
			
			if(input.files[0].size > 2700000){
				$("#picture").next().text("El tamaño de la imagen excede el tamaño permitido: 2.5Mb ");
				warn($("#picture"));
			}

			if($.inArray(input.files[0].type, ["image/png","image/gif","image/jpeg","image/jpg"]) == -1 ) {
				$("#picture").next().text("Formato de foto no válido. (Formatos válidos: png, gif, jpeg y jpg).");
				warn($("#picture"));
			}
			
			var reader = new FileReader();
	        reader.onload = function (e) {
	        	$('#profileImage').parent().addClass("profile-image");
	        	$('#profileImage').show();
	            $('#profileImage > img').attr('src', e.target.result);
	            $('#profileImage > input').val(true);
            }
	        reader.readAsDataURL(input.files[0]);
		
        }
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
		
		$("div.games-list").css("direction","");
	});
	
	$('.new-record > a').hover(
			function(){
				var imageName = $(this).find("img").attr("src");
				$(this).find("img").attr("src",imageName.slice(0,imageName.length - 4) + "-selected.png");
			},
			function() {
					var imageName = $(this).find("img").attr("src");
					$(this).find("img").attr("src",imageName.slice(0,imageName.length - 13) + ".png");
			}	
	);
	
	
	
	$(document).on("mouseover",'#leftMenu li, .dropdown-menu',function() {
		var imageName = $(this).find("img").first().attr("src");
		if(imageName != null)
			$(this).find("img").first().attr("src",imageName.slice(0,imageName.length - 4) + "-selected.png");
	});
	
	$(document).on("mouseout",'#leftMenu li, .dropdown-menu',function() {
		var imageName = $(this).find("img").first().attr("src");
		if(imageName != null && imageName.slice(imageName.length - 12,imageName.length - 4) == "selected")
			$(this).find("img").first().attr("src",imageName.slice(0,imageName.length - 13) + ".png");
	});
	
	$(document).on("click",'#leftMenu > ul > .dark-nav > a',function(){
		$(this).parent().toggleClass("dark-nav-active");
		$(this).next('ul').slideToggle("drop");

	});
	
	$('form').on('submit',validate);	
	
	$(document).on("keyup", "textarea", function() {
		$(this).css('height', 'auto');
		$(this).height(this.scrollHeight);

	});
	
	$(document).find("textarea").keyup();

	if($(".errorMessage").children().length > 1)
		$(".errorMessage li").css({"list-style-type":"initial","margin-left":"10px"});
	
	if($("#profileImage").length && $("#profileImage input").val() == "true"){
		$("#profileImage").show();
		$("#profileImage").parent().addClass("profile-image");
	}	
	else
		$("#profileImage").hide();
	
	$(document).on("click",".delete", function(e){
		$(".confirm-box").hide();
		$(".delete").show();
        e.preventDefault();
		e.stopPropagation();

		if($(this).closest("div.games-list").length)
			$(this).closest("div.games-list").css("direction","rtl");
		
		$(this).hide();
		
		if($(this).next(".confirm-box").hasClass("important"))
			$(this).next(".confirm-box").css("display", "inline-block");
		else if($(this).next(".confirm-box").hasClass("important-2"))
			$(this).next(".confirm-box").css("display", "inline-table");
		else
			$(this).next(".confirm-box").css("display", "inline");

	});
	

	$(document).click(function() {
		$(".confirm-box").hide();
		$(".delete").show();
	});
	
	$(".confirm-box > .no, .confirm-box > .yes").click(function() {

		if(!$(this).parents(".comments-list").length){
			if ($(this).hasClass("no")) {
				$(".confirm-box").hide();
				$(".delete").show();
				if($(this).closest("div.games-list").length)
					$(this).closest("div.games-list").css("direction","");
			} else if ($(this).hasClass("yes")) {
				var deleteLink = $(this).parent().prev().attr("href");
				console.log(deleteLink);
				window.location = deleteLink;
			}
		}r
	
	});
	
	
});