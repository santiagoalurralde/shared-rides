<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!--------------------------------------------------------------------
[Menu]
--------------------------------------------------------------------->
<div id="nav" class="theBox">
    <ul>       
        <li>					<a class="" 			href="main.do"> 		<span class="logo"> Shared Rides  </span> 	</a></li>
        <li>					<a class="highlight"	href="myProfile.do">  	<spring:message code="label.profile"	/></a></li>
        <li>					<a class="highlight"	href="people.do">       <spring:message code="label.people"		/></a> <img id="alertIcon" src="resources/images/bell.png" width="1.5%" style="position: absolute; left: 55%; top: 25% "/></li>
        <li>					<a class="highlight"	href="#">     			<spring:message code="label.contact"	/></a></li>
        <li>					<a class="highlight"	href="#">        		<spring:message code="label.about"		/></a></li>
    </ul>

	<img id="btnLogout" src="resources/images/logout.png" width="1.75%">
</div>


<script>
	$.post( 'hasAssociation.do', 
			function(json)
			{
				var jsonNew = $.parseJSON(json);
				if (jsonNew.hasAssoc === false)
				{
					$( '#alertIcon' ).hide( 0 );
				}
			});
</script>