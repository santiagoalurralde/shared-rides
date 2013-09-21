<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 

<body>
	<span id="intl">
    		<a href="?lang=es">
    			<img src="resources/images/ar.png"/>
    		</a> 
    		 
    		<a href="?lang=en">
    			<img src="resources/images/us.png"/>
    		</a>
	</span>
    
    <div class="littleTupper">
    
        <!----------------  CONTENT     -------------->
		<div class="content">
		

			
			
			<img 	width=275px src="resources/images/logo1.png"/>
			<hr		class="hrs"/>
			
			<h3 	style="text-align:center"> 
				<spring:message code="label.login"/>
			</h3>
	
	
			<form method="POST" action="validate.htm">

				<spring:message code="label.email"/>	<br> 
				<input class="ipt" type="text"		name="email"	placeholder="mike@email.com">	<br>			
				<spring:message code="label.password"/>	<br> 
				<input class="ipt" type="password"	name="pwd"		placeholder="Password">			<br>
				
				
				<input class="font, but" id="loginBtn" type="submit" value="<spring:message code="label.enter"/>">
			</form>
		</div>
		
		
		<!----------------  PICTURE     -------------->
		<div>
			<img id="introPic" width=325px src="resources/images/intropic.png"/>		
		</div>
	</div>
</body>


<!--
	http://i43.tinypic.com/15840oi.png  #LOGO
	http://i43.tinypic.com/28hmrf8.png	#FOTO		
-->
 
