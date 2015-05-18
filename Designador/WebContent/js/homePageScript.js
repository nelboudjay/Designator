$(function() {
	
	$(".close").click(function() {
		$(".boxMessage, .error, .errors").remove();
	});
	
	$(".comments-list").on(
			"click",
			".modify-comment",
			function(e) {
				
				var commentContent = $(this).parents(".content");
				var currentCommentBodyText = $.trim($("> .comment-body", commentContent).text());
				var currentCommentContent = commentContent.children();
				
				var addCommentSubmit = $(".comments").nextUntil(".comments-list");
				$("#commentBody").val(currentCommentBodyText);
				$(".add-comment-submit").toggleClass("add-comment-submit modify-comment-submit");
				$(".modify-comment-submit").css("display","");
				$("#addComment").text("Modificar comentario");
				$(".comments-list").css("margin-top","0");
				commentContent.html(addCommentSubmit);
				
				$("#cancelCommentLink, #addComment, .delete-comment, .modify-comment").click(function(){
					
					/*Move back the text area*/
					$(".modify-comment-submit").toggleClass("modify-comment-submit add-comment-submit");
					$(".add-comment-submit").css("display","none");
					$("#commentBody").val("");
					$("#addComment").text("Añadir comentario");
					$(".comments-list").css("margin-top","20px");
					$(".comments").after(addCommentSubmit);
					
					/*Retrieve the current comment*/
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
				var removeCommentlink = "deleteComment?commentId=" + commentId;
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

	$("#addComment").click(
			function(e) {

				if ($("#commentBody").val().trim() == "") {
					$(".required-field").css("display", "block");
					$(".required-field").text(
							"El comentario no puede estar en blanco.");
					$('#commentBody').css("border-color", "#b94a48");
					$('#commentBody').css("border-style", "solid");

					return false;
				}

				var commentId = $("#commentBody").parent(".content").attr("id");
				console.log(commentId);
				$.ajax({
					type : "POST",
					url : "addComment",
					dataType : "html",
					data : {
						commentBody : $("#commentBody").val().trim(),
						commentId : $("#commentBody").parents(".content").attr("id")
					},
					success : function(result) {
						var newCommentsList = $(result).find(".comments-list")
								.html();
						$(".comments-list").html(newCommentsList);
						$("#commentBody").val("");
						minimazeAddCommentForm();
					}
				});
			});

	function minimazeAddCommentForm() {
		$(".add-comment-submit").fadeOut("slow");
		$(".comments").find("textarea").keyup();
		$(".required-field").css("display", "none");
		$(".required-field").text("");
		$('#commentBody').css("border-color", "");
		$('#commentBody').css("border-style", "");

	}

	$("#comments").on("click","#cancelCommentLink", minimazeAddCommentForm);

	$("#comments").on("keyup", "textarea", function() {
		$(this).css('height', 'auto');
		$(this).height(this.scrollHeight);

	});
	$("#comments").find("textarea").keyup();

	$("#comments").on("focus", "#commentBody", function() {
		if (!$(".add-comment-submit").is(':visible')) 
			$(".add-comment-submit").fadeIn("slow");
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
		$('#leftMenu li').removeClass("active");
		$(this).addClass("active");
	});

});