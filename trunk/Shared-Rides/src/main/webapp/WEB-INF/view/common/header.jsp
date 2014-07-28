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
	
	<img id="btnAlert" src="resources/images/bell.png" width="1.75%"/>
	
	<a href="logout.do">
		<img id="btnLogout" src="resources/images/logout.png" width="1.75%">
	</a>
</div>

 
<div id="notifications" class="notif">
	<iframe id="theFrame" src='notifications.do' ></iframe>
</div>

<script>
	$( "#btnAlert" ).click(function(){
		if ($("#notifications").is(":visible"))
			$( "#notifications" ).hide();
		else
			$( "#notifications" ).show();	
	});
	

	$.post( 'hasAssociation.do', 
		function(json)
		{
			var jsonNew = $.parseJSON(json);
			if (jsonNew.hasAssoc === false)
			{
				$( '#btnAlert' ).hide();
				$('#theFrame').contents().find("#listNotifications").append("<li>Has recibido una peticion</li>");
			}
		}
	);
</script>