$(document).ready(function(){
	$.get(nsjs.ctx + "/myInfo2", function(data) {
		appendNotFollowingsList(data.notFollowings);
	});

	function appendNotFollowingsList(notFollowings) {
		//
		var html = '';
		
		for(var i=0, length = notFollowings.length; i< length; i++) {
			//
			var notFollowing= notFollowings[i];
			
			html += "<h5><i class='glyphicon glyphicon-user'></i></h5>&nbsp;";
			html += "<p><h4>" + notFollowing.name + "</h4>&nbsp;<h5>" + notFollowing.userId + "</h5></p>";
			html += "<input type='button' onclick='location.href=" + "'" + nsjs.ctx + "/follow/" + notFollowing.userId + "'"+ "'" + " value='팔로우' class='btn btn-default' />";
		}
		$("#recommendFollowing").append(html);
	}
});