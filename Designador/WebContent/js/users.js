$(function() {
	
	$('.btn-group button').click(function(e){
		e.stopPropagation();
		$(this).next("ul").show();
	});
	
	$(document).click(function() {
		$('.btn-group ul').hide();
	});
	
});
