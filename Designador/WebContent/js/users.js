$(function() {
	
	$('.btn-group button').click(function(e){
		e.stopPropagation();
		$(this).next("ul").show();
	});
	
	$(document).click(function() {
		$('.btn-group ul').hide();
	});
	
	$('input:radio[name="userRole"]').change(function (){
	if($(this).val() == 1)
		$('.referee-types').hide();
	else
		$('.referee-types').show();
	});
	
});
