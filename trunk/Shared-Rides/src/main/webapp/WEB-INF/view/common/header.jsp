<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.shared.rides.domain.User"%>

<%	
	User u = (User) request.getSession().getAttribute("user");
%>

<!-----------------------------------------
[Menu]
------------------------------------------>
<div class="sr-nav">
    <ul>       
        <li><a class="" 			href="main.do"> 		<span class="logo"> Shared Rides </span> 	</a></li>
        <li><a class="highlight"	href="myProfile.do">	<spring:message code="lbl.profile"	/>		</a></li>
        <li><a class="highlight"	href="people.do">       <spring:message code="lbl.people"	/>		</a></li>
        <li><a class="highlight"	href="contact.do">     	<spring:message code="lbl.contact"	/>		</a></li>
        <li><a class="highlight"	href="about.do">        <spring:message code="lbl.about"	/>		</a></li>
    </ul>
	<span style="position: fixed; top: 0; right: 6%"><%= u.getName() + " " + u.getSurname() %></span>
	<img class="btn-alert" src="resources/images/bell.png"/>
	
	<a href="logout.do">
		<img class="btn-logout" src="resources/images/logout.png">
	</a>
</div>

<!-----------------------------------------
[Notifications]
------------------------------------------>
<div id="notifications-box" class="notifications">
	<h5> <spring:message code="lbl.notifications"/> </h5>

	<ul class="notifications-list">
		<script id="temp-notifications" type="text/x-handlebars-template">
			<li class="notification-item">
				<a href='people.do'>
					{{#isRequest type}}
						Has recibido una peticion de <b> {{name}} {{date}} </b>
					{{else}}
						<b>{{name}}</b> ha respondido a tu peticion {{date}}
					{{/isRequest}}
				</a>
			</li>
		</script>
	</ul>	
</div>