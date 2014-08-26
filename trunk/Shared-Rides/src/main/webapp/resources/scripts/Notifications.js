/*******************************************************************************
 * EVENTS
 ******************************************************************************/

$(document).ready(function(){
        $(".btnAlert").click(function() {
                if ($("#boxNotifications").is(":visible"))
                        $("#boxNotifications").hide();
                else
                        $("#boxNotifications").show();  
        });

        $(".divNotif").click(function() {
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
                                if (notification.type == "request"){
                                        var divNotif = "<div class='divNotif'><li>Has recibido una peticion de <b>"+ notification.name +"</b></li></div>";
                                        $(".listNotifications").append(divNotif);
                                }
                                else{
                                        var divNotif = "<div class='divNotif'><li><b>"+ notification.name +"</b> ha respondido a tu peticion</li></div>";
                                        $(".listNotifications").append(divNotif);
                                }
                        }                               
                });
        }
);