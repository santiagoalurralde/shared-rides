/*******************************************************************************
 * EVENTS
 ******************************************************************************/

$(document).ready(function() {
	var $notificationsBox = $("#notifications-box");

	$(".btn-alert").click(function() {
		$notificationsBox.is(":visible") ? $notificationsBox.hide() : $notificationsBox.show();
	});

	$(".notification-item").click(function() {
		window.location.href = "people.do";
	});	
});

/*******************************************************************************
 * AJAX CALLS
 ******************************************************************************/

$.post("getNotifications.do", 
	function(json) {
		var jsonArray = $.parseJSON(json);
		$.each(jsonArray, function(i, notification) {
			if (notification != "") {
				var notifItem = "<div class='notification-item'><li>Has recibido una peticion de <b>"+ notification.name +"</b></li></div>";
				$(".notifications-list").append(notifItem);
			}				
		});
	}
);

$.post("hasResponse.do", 
	function(json) {
		var jsonArray = $.parseJSON(json);
		$.each(jsonArray, function(i, notification) {
			if (notification != "") {
				var notifItem = "<div class='notification-item'><li><b>"+ notification.name +"</b> Ha respondido a tu peticion</li></div>";
				$(".notifications-list").append(notifItem);
			}				
		});
	}
);