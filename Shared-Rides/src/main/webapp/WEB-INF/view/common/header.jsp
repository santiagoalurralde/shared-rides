<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!--------------------------------------------------------------------
[Menu]
--------------------------------------------------------------------->
<div id="nav" class="theBox">
    <div class="tupper">
    <ul>       
        <li class="current">	<a href="main.do">					<img width=120px src="resources/images/logoFin1.png" style="position: relative; left: -20px;"	/></a></li>
        <li>					<a href="myProfile.do">  			<spring:message code="label.profile"															/></a></li>
        <li>					<a href="#">       					<spring:message code="label.people"																/></a> <img id="alertIcon" src="resources/images/bell.png" width="18px" style="position: absolute; left: 54%; top: 12px "/></li>
        <li>					<a href="#">     					<spring:message code="label.contact"															/></a></li>
        <li>					<a href="#">        				<spring:message code="label.about"																/></a></li>
    </ul>
    </div>
    <div id="btnLogout">
    	<a href="logout.do"><img src="resources/images/logout.png" width="25px"></a>
    </div>
    
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