/*******************************************************************************
 * EVENTS
 ******************************************************************************/

$(document).ready(function() {
	var templateNotif = Handlebars.compile($("#temp-notifications").html());
	
	Handlebars.registerHelper("isRequest", function(val, opts) {
	    return (val == "request") ? opts.fn(this) : opts.inverse(this);
	});
	
	$.post("getNotifications.do", 
	    function(json) {
	        var notifArray = $.parseJSON(json);
	        
	        if(notifArray.notifications.length != 0) {
		        $.each(notifArray.notifications, function(i, not) {
	                var notifFinal = templateNotif(not);
	                $(".notifications-list").append(notifFinal);
		        });	            	
	        }
	        else {
                $(".notifications-list").append("<h5 style='font-weight:lighter; text-align:center'>No tienes notificaciones.</h5>");
	        }
	        
        	if(notifArray.newNotification) {
        		$(".notification-bubble").html(notifArray.notifications.length).show();
        	} 
        	else {
	        	$(".notification-bubble").hide();
	        }
	});	
	
    var $notificationsBox = $("#notifications-box");
    
    $(".btn-alert").click(function() {
    	$notificationsBox.toggle();
    });
});

function updateLoginDate() {
	$.post("updateLoginDate.do", function() {
		console.log("success");
	});
};