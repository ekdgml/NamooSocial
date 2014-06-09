$(document).ready(function(){
	$.get(nsjs.ctx + "/flollowings2", function(data) {
		appendNotFollowingsList(data.notFollowings);
		appendFollowingsList(data.followings);
	});

	function appendNotFollowingsList(notFollowings) {
		//
		var html = '';
		
		for(var i=0, length = notFollowings.length; i< length; i++) {
			//
			var notFollowing= notFollowings[i];
			
			html += "<h5><i class='glyphicon glyphicon-user'></i></h5>&nbsp;";
			html += "<p><h4>" + notFollowing.name + "</h4>&nbsp;<h5>" + notFollowing.userId + "</h5></p>";
			html += "<input type='button' onclick='location.href=" + "'" + nsjs.ctx + "/follow/" + notFollowing.userId +"'" + "'" + " value='팔로우' class='btn btn-default' />";
		}
		$("#recommendFollowing").append(html);
	}
	
	function appendFollowingsList(followings) {
		//
		var html = '';
		
		for(var i=0, length = followings.length; i< length; i++) {
			//
			var following= followings[i];
			
			html += "<tr><td>" + following.userId + "</td>td>" + following.name +"</td>";
			html += "<td><button type='button' onclick='location.href=" + "'" + nsjs.ctx + "/unfollow/" + following.userId + "'" + "'" + " class='btn btn-default btn-sm'>언팔로우</button></td>";
			html += "</tr>";
		}
		$("#followingList").append(html);
	}
});