$(document).ready(function(){
	$.get(nsjs.ctx + "/mainList", function(data) {
		appendNotFollowingsList(data.notFollowings);
		appendMessagesTr(data.messages);
	});

	function appendNotFollowingsList(notFollowings) {
		//
		var html = '';
		
		for(var i=0, length = notFollowings.length; i< length; i++) {
			//
			var notFollowing = notFollowings[i];
			html += "<i class='glyphicon glyphicon-user'></i>";
			html += "<p>" + notFollowing.name + "&nbsp;&nbsp;&nbsp;" + notFollowing.userId + "</p>";
			html += "&nbsp;<input type='button' onclick='location.href=" + "'" + nsjs.ctx + "/follow/" + notFollowing.userId + "'" + "'" + " value='팔로우' class='btn btn-default' /><br/>";
		}
		$("#recommendFollowing").append(html);
	}
	
	function appendMessagesTr(messages) {
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
		$("#allMessages").append(html);
	}
});