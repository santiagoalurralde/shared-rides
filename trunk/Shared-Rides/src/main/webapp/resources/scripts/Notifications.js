/*******************************************************************************
 * EVENTS
 ******************************************************************************/

$(document).ready(function() {
    var $notificationsBox = $("#notifications-box");

    $(".btn-alert").click(function() {
    	$notificationsBox.toggle();
    });

    $(".notification-item").click(function() {
        alert("click");
        window.location.href = "people.do";
    });     
});


/*******************************************************************************
 * AJAX CALLS
 ******************************************************************************/
//TODO MESSAGES
$.post("getNotifications.do", 
    function(json) {
        var jsonArray = $.parseJSON(json);
        $.each(jsonArray.notifications, function(i, notification) {
            if (notification != "") {
                if (notification.type == "request") {
                	$("<div></div>", {
                		html: "<li>Has recibido una peticion de <b>"+ notification.name +" "+ notification.date +"</b></li>",
                		class: "notification-item"
                	}).appendTo(".notifications-list");
                }
                else {
                	$("<div></div>", {
                		html: "<li><b>"+ notification.name +"</b> ha respondido a tu peticion "+ notification.date +"</li>",
                		class: "notification-item"
                	}).appendTo(".notifications-list");                	
                }
            }                               
        });
    }
);