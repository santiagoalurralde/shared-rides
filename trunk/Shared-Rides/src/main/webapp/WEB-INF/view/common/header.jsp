<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!--------------------------------------------------------------------
[Menu]
--------------------------------------------------------------------->
<div id="nav"> <!-- class="border" -->
    <ul>       
        <li>					<a class="" 			href="main.do"> 		<span class="logo"> Shared Rides  </span> 	</a></li>
        <li>					<a class="highlight"	href="myProfile.do">  	<spring:message code="label.profile"	/></a></li>
        <li>					<a class="highlight"	href="people.do">       <spring:message code="label.people"		/></a></li>
        <li>					<a class="highlight"	href="contact.do">     	<spring:message code="label.contact"	/></a></li>
        <li>					<a class="highlight"	href="about.do">        <spring:message code="label.about"		/></a></li>
    </ul>
	
	<img id="btnAlert" src="resources/images/bell.png" style="display:none" width="1.75%"/>
	
	<a href="logout.do">
		<img id="btnLogout" src="resources/images/logout.png" width="1.75%">
	</a>
</div>

 
<div id="boxNotifications" class="notif">
	<h5>Notificaciones</h5>
	
	<ul id="listNotifications">
	</ul>
</div>

<script>
	$( "#btnAlert" ).click(function() {
		if ($("#boxNotifications").is(":visible"))
			$( "#boxNotifications" ).hide();
		else
			$( "#boxNotifications" ).show();	
	});
	
	$.post( 'hasAssociation.do', 
		function(json) {
			var jsonArray = $.parseJSON(json);
			$.each(jsonArray, function(i, notification){
				if (notification != "") {
					$( '#btnAlert' ).show();
					var divNotif = "<div class='divNotif'><li>Has recibido una peticion de <b>"+ notification.name +"</b></li><div>";
					$("#listNotifications").append(divNotif);
				}				
			});
		}
	);
	
	$.post( 'hasResponse.do', 
			function(json) {
				var notification = $.parseJSON(json);
				if (notification != "") {
					$( '#btnAlert' ).show();
					var divNotif = "<div class='divNotif'><li><b>"+ notification.name +"</b> Ha respondido a tu peticion</li><div>";
					$("#listNotifications").append(divNotif);
				}
			}
		);
</script>