<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 

<body>
    <div class="littleTupper">
    
        <!----------------  CONTENT     -------------->
		<div class="content">
			<h3>
				<spring:message code="label.welcome"/>
			</h3>
 
			<span style="float: right">
    			<a href="?lang=es">es</a> 
    			| 
    			<a href="?lang=en">en</a>
			</span>
			
			
			<img 	width=275px src="resources/images/logo1.png"/>
			<hr		class="hrs"/>
			
			<h3 	style="text-align:center"> 
				<spring:message code="label.login"/>
			</h3>
	
	
			<form method="POST" action="validate.htm">
				E-mail:		<br> <input class="ipt" type="text"		name="email"	value="" placeholder="mike@email.com">	<br>
				Password:	<br> <input class="ipt" type="password"	name="pwd"		value="" placeholder="Password">		<br>
				
				<input class="font" id="but" type="submit" value="Ingresar!">
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
 
