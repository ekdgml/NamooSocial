$(document).ready(function(){
	$.get(nsjs.ctx + "/myMessagesList", function(data) {
		appendNotFollowingsList(data.notFollowings);
		appendTr(data.messages);
	});
	
	function appendNotFollowingsList(notFollowings) {
		//
		var html = '';
		
		for(var i = 0, length = notFollowings.length; i < length; i++) {
			//
			var notFollowing = notFollowings[i];
			
			html += "<h5><i class='glyphicon glyphicon-user'></i></h5>&nbsp;";
			html += "<p><h4>" + notFollowing.name + "</h4>&nbsp;";
			html += "<h5>" + notFollowing.userId + "</h5></p>";
			html += "<input type='button' onclick='location.href=" + "'" + nsjs.ctx + "/follow/" + notFollowing.userId + "'" + "'" + " value='팔로우' class='btn btn-default' />";
		}
		$("#recommendFollowing").append(html);
	}
	
	function appendTr(messages) {
		//
		var html = '';
		
		for(var i=0, length = messages.length; i< length; i++) {
			//
			var message = messages[i];
			html += "<div class='row'><div class='col-md-8'>";
			html += "<h4>" + message.writer.name + "</h4>";
			html += "<h5>" + message.writer.userId + "&nbsp;" + message.reg_dt + "</h5>";
			html += "<p>" + message.contents + "</p>";
			html += "<br><br>";
			html += "<button class='btn btn-default'>More</button>&nbsp;&nbsp<input type='checkbox'/>";
			html += "</div></div><hr>";
		}
		$("#myMessages").append(html);
	}
});