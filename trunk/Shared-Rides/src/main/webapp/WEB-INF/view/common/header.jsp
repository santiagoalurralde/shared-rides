<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!--------------------------------------------------------------------
[Menu]
--------------------------------------------------------------------->

<div id="nav">
    <ul>       
        <li><a class="" 			href="main.do"> 		<span class="logo"> Shared Rides </span> 	</a></li>
        <li><a class="highlight"	href="myProfile.do">  	<spring:message code="label.profile"	/>	</a></li>
        <li><a class="highlight"	href="people.do">       <spring:message code="label.people"		/>	</a></li>
        <li><a class="highlight"	href="contact.do">     	<spring:message code="label.contact"	/>	</a></li>
        <li><a class="highlight"	href="about.do">        <spring:message code="label.about"		/>	</a></li>
    </ul>
	
	<img class="btnAlert" src="resources/images/bell.png"/>
	
	<a href="logout.do">
		<img class="btnLogout" src="resources/images/logout.png">
	</a>
</div>

<!--------------------------------------------------------------------
[Notifications]
--------------------------------------------------------------------->

<div id="boxNotifications" class="notif">
	<h5>Notificaciones</h5>
	
	<ul class="listNotifications"></ul>
</div>