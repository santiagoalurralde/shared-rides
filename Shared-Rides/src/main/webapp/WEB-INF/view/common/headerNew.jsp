<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!--------------------------------------------------------------------
[Menu]
--------------------------------------------------------------------->
<div id="nav"> <!-- class="border" -->
    <ul>       
        <li><a class="" 			href="login.do"> 	<span class="logo"> Shared Rides  </span> 	</a></li>
		<li><a class="highlight"	href="contact.do">  <spring:message code="label.contact"/>		</a></li>
        <li><a class="highlight"	href="about.do">    <spring:message code="label.about"	/>		</a></li>
    </ul>
</div>
