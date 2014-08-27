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
	        if(notifArray != "") {
		        $.each(notifArray.notifications, function(i, not) {
	                var notifFinal = templateNotif(not);
	                $(".notifications-list").append(notifFinal);
		        });	            	
	        }
	});	
	
    var $notificationsBox = $("#notifications-box");
    
    $(".btn-alert").click(function() {
    	$notificationsBox.toggle();
    });
});


/*******************************************************************************
 * AJAX CALLS
 ******************************************************************************/

(function(){

})();

