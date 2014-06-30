<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<body> 
<!-- style="background: url(resources/images/introPic.jpg); background-size: 1500px" -->
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

    <div class="tupper mini">
        <!----	Login	---->
		<div id="theLogin">
			<img 	width=275px src="resources/images/logo1.png"/>
			<hr		class="hrs"/>
				
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
		
		<!----	Picture	---->
		<img id="picIntro"src="resources/images/intropic.png"/>		
	</div>
</body>


<!--
	http://i43.tinypic.com/15840oi.png  #LOGO
	http://i43.tinypic.com/28hmrf8.png	#FOTO		
-->
<!--  
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
-->
		
