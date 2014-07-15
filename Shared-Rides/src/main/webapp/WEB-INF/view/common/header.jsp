<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!--------------------------------------------------------------------
[Menu]
--------------------------------------------------------------------->
<div id="nav" class="theBox">
    <ul>       
        <li>					<a class="" 			href="main.do"> 		<span class="logo"> Shared Rides  </span> 	</a></li>
        <li>					<a class="highlight"	href="myProfile.do">  	<spring:message code="label.profile"	/></a></li>
        <li>					<a class="highlight"	href="people.do">       <spring:message code="label.people"		/></a></li>
        <li>					<a class="highlight"	href="contact.do">     	<spring:message code="label.contact"	/></a></li>
        <li>					<a class="highlight"	href="about.do">        <spring:message code="label.about"		/></a></li>
    </ul>
	
	<a href="">
		<img id="btnAlert" src="resources/images/bell.png" width="1.75%"/>
	</a>
	
	<a href="logout.do">
		<img id="btnLogout" src="resources/images/logout.png" width="1.75%">
	</a>
</div>


<script>
	$.post( 'hasAssociation.do', 
			function(json)
			{
				var jsonNew = $.parseJSON(json);
				if (jsonNew.hasAssoc === false)
				{
					$( '#btnAlert' ).hide( 0 );
					$( '#btnAlert' ).parent().attr("href", "people.do");
				}
			}
	);
</script>