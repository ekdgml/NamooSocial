$(document).ready(function(){
	$('#show').click(function(){
		var loginId = $('#id').val();
		var password = $('#password').val();
		
		$.ajax({
		type : "POST",
		url : nsjs.ctx + "/login",
		data : "loginId=" + loginId + "&password=" + password, 
		success : function(data){
			if (data) {
				window.location = nsjs.ctx + "/main";
			} else {
				$('#fail').text("실패");
			}
		}
		});
		return false;
	});
});