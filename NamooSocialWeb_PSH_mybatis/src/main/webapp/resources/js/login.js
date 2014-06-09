$(document).ready(function(){
	$('#login').click(function (){
		var loginId = $('#userId').val();
		var password = $('#password').val();
		
		$.ajax({
		type : "POST",
		url : nsjs.ctx + "/login",
		data : "loginId=" + loginId + "&password=" + password, 
		success : function(data){
			if (data) {
				window.location = nsjs.ctx + "/main";
			} else {
				$('#show').text("실패");
			}
		}
		});
		return false;
	});
});