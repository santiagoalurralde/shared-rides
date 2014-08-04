<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<body style="background: url(resources/images/pic1.jpg); background-size: cover"> 

	<div class="tupper mini">
		<!----	Internationalization	---->
		<span id="i18n">
    		<a href="?lang=es">
    			<img src="resources/images/ar.png"/>
    		</a> 
    		 
    		<a href="?lang=en">
    			<img src="resources/images/us.png"/>
    		</a>
		</span>
	</div>

	<!----	Logo	---->
	<div style="width: 14%; text-align: center; margin: 2% auto">
		<img src="resources/images/Logo2.png" width="80%">
	</div>

    <!----	Login	---->
	<div id="theLogin">
			
		<h3 	style="text-align:center"> 
			<spring:message code="label.login"/>
		</h3>
		
		<form method="POST" action="validate.do">
			<!-- <spring:message code="label.email"/>	<br> --> 
			<input class="theInputs" type="text"		name="email"	placeholder="mike@email.com">	<br>			
			<!-- <spring:message code="label.password"/>	<br> -->
			<input class="theInputs" type="password"	name="pwd"		placeholder="Password">			<br>
			
			<input class="btn" id="btnLogin" type="submit" value="<spring:message code="label.enter"/>">
		</form>
	</div>
		
</body>
