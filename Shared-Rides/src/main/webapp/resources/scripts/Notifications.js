/*******************************************************************************
 * EVENTS
 ******************************************************************************/

$( "#btnAlert" ).click(function() {
	if ($("#boxNotifications").is(":visible"))
		$( "#boxNotifications" ).hide();
	else
		$( "#boxNotifications" ).show();	
});

/*******************************************************************************
 * AJAX CALLS
 ******************************************************************************/

$.post( 'hasAssociation.do', 
	function(json) {
		var jsonArray = $.parseJSON(json);
		$.each(jsonArray, function(i, notification) {
			if (notification != "") {
				$( '#btnAlert' ).show();
				var divNotif = "<div class='divNotif'><li>Has recibido una peticion de <b>"+ notification.name +"</b></li></div>";
				$("#listNotifications").append(divNotif);
			}				
		});
	}
);

$.post( 'hasResponse.do', 
	function(json) {
		var jsonArray = $.parseJSON(json);
		$.each(jsonArray, function(i, notification) {
			if (notification != "") {
				$( '#btnAlert' ).show();
				var divNotif = "<div class='divNotif'><li><b>"+ notification.name +"</b> Ha respondido a tu peticion</li></div>";
				$("#listNotifications").append(divNotif);
			}				
		});
	}
);