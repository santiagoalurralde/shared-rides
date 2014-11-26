<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.shared.rides.domain.User"%>

<% User u = (User) request.getSession().getAttribute("user"); %>

<!-----------------------------------------
[Menu]
------------------------------------------>
<div class="sr-nav">
    <ul>    
    <!--<li><a class="highlight"	href="main.do">			<spring:message code="lbl.search"	/>		</a></li>-->
        <li><a class="highlight"	href="myProfile.do">	<spring:message code="lbl.profile"	/>		</a></li>
        <li><a class="highlight"	href="people.do">       <spring:message code="lbl.people"	/>		</a></li>
        <li><a class="highlight"	href="contact.do">     	<spring:message code="lbl.contact"	/>		</a></li>
        <li><a class="highlight"	href="about.do">        <spring:message code="lbl.about"	/>		</a></li>
    </ul>
    
    <a href="main.do">
		<span class="text-logo">
			Shared Rides 
		</span>
	</a>
	
    <a href="myProfile.do">
		<span class="text-logged">
			<%= u.getName() + " " + u.getSurname() %>
		</span>
	</a>
	
	<img class="btn-alert" src="resources/images/bell.png"/>
	<div class="notification-bubble"></div>
	
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
			<li class='notification-item'>
				<a onclick='updateLoginDate();'>
					{{#isRequest type}}
						<spring:message code="lbl.notif-request"/> <b> {{name}} - {{date}} </b>
					{{else}}
						<b>{{name}}</b> <spring:message code="lbl.notif-responded"/> - {{date}}
					{{/isRequest}}
				</a>
			</li>
		</script>
	</ul>	
</div>