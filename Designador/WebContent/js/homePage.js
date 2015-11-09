$(function() {
	
	$(".comments-list").on(
			"click",
			".modify-comment",
			function(e) {
	
				minimazeAddCommentForm();
				var commentContent = $(this).parents(".content");
				var currentCommentBodyText = $.trim($("> .comment-body", commentContent).html()).
						replace(/<br>/g,"\n").replace(/&lt;/g,"<").replace(/&gt;/g,">").replace(/&amp;/g,"&");
				
				var currentCommentContent = commentContent.children();
				
				var addCommentSubmit = $(".comments").nextUntil(".comments-list");
				
				$("#commentBody").val(currentCommentBodyText);
				$(".add-comment-submit").toggleClass("add-comment-submit modify-comment-submit");
				$(".modify-comment-submit").css("display","");
				$("#addComment").text("Modificar aviso");
				$(".comments-list").css("margin-top","0");
				commentContent.html(addCommentSubmit);
				$("#commentBody").keyup();

				$("#cancelCommentLink, .delete-comment, .modify-comment").click( function(){

					//Move back the text area
					$(".modify-comment-submit").toggleClass("modify-comment-submit add-comment-submit");
					$(".add-comment-submit").hide();
					$("#commentBody").val("");
					$("#addComment").text("Añadir aviso");
					$(".comments-list").css("margin-top","20px");
					$(".comments").after(addCommentSubmit);
				
					//Retrieve the current comment
					commentContent.html(currentCommentContent);	
					
				});

			});
	
	$(".comments-list").on(
			"click",
			".delete-comment",
			function(e) {
				$(".confirm-box").hide();
				$(".delete-comment").show();
				var deleteComment = $(this);
				var confirmBox = deleteComment.next();
				var commentId = deleteComment.parents(".content").attr("id");
				var removeCommentlink = "comment/deleteComment?commentId=" + commentId;
				var comment = deleteComment.parents("li");
				$("> .message", confirmBox).text(
						"¿Estás seguro que quieres eliminar este mensaje?");
				deleteComment.hide();
				confirmBox.css("display", "inline");

				e.preventDefault();
				e.stopPropagation();

				$(document).click(function() {
					confirmBox.hide();
					deleteComment.show();
				});
				
				$("> .no, .yes", confirmBox).click(function() {
					if ($(this).hasClass("no")) {
						confirmBox.hide();
						deleteComment.show();
					} else if ($(this).hasClass("yes")) {
						$.ajax({
							url : removeCommentlink,
						});
						comment.remove();
					}
				});
			});


	$("#comments").on("click","#addComment",function(){

		if(validate()){
		
			var addComment = $(this);
	
			$.ajax({
				type : "POST",
				url : "comment/addComment",
				dataType : "html",
				data : {
					commentBody : $("#commentBody").val().trim(),
					commentId : $("#commentBody").parents(".content").attr("id")
				},
				success : function(result) {
					var newCommentsList = $(result).find(".comments-list")
							.html();
	
					if(addComment.parent().attr("class") == "modify-comment-submit"){
						$(".modify-comment-submit").toggleClass("modify-comment-submit add-comment-submit");
						$("#addComment").text("Añadir aviso");
						$(".comments-list").css("margin-top","20px");
						$(".comments").after(addComment.closest(".content").children());
					}
					$("#commentBody").val("");
					$(".comments-list").html(newCommentsList);
					
					minimazeAddCommentForm();
				}
			});
		}
		
	});

	function minimazeAddCommentForm() {
		$(".add-comment-submit").hide();
		$(".error-field").css("display", "none");
		$('.required-field').css("border-color", "");
		$('.required-field').css("border-style", "");
		
		$("#commentBody").keyup();


	}	
	
	$("#comments").on("click","#cancelCommentLink", function(){
		minimazeAddCommentForm();
		
	});

	$("#comments").on("focus", "#commentBody", function() {
		if (!$(".add-comment-submit").is(':visible')) 
			$(".add-comment-submit").fadeIn("slow");
	});

});