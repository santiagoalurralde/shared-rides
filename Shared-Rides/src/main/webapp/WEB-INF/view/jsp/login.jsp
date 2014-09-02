<body> 

	<!-----------------------------------------
	[Internationalization]
	------------------------------------------>
	<div class="sr-tupper sr-mini">
		<span id="i18n">
    		<a href="?lang=es">
    			<img src="resources/images/ar.png"/>
    		</a> 
    		 
    		<a href="?lang=en">
    			<img src="resources/images/us.png"/>
    		</a>
		</span>
	</div>

	<!-----------------------------------------
	[Logo]
	------------------------------------------>
	<div class="sr-logo">
		<img src="resources/images/Logo2.png" width="80%">
	</div>

	<!-----------------------------------------
	[Login]
	------------------------------------------>
	<div class="sr-login">
			
		<h3 style="text-align:center"> 
			<spring:message code="lbl.login"/>
		</h3>
		
		<form method="POST" action="validate.do">
			<input 	class="sr-inputs" type="text"		name="email"	placeholder="mike@email.com">	<br>			
			<input 	class="sr-inputs" type="password"	name="pwd"		placeholder="Password">			<br>
			
			<input 	class="btn btn-login" type="submit" value="<spring:message code="lbl.enter"/>">
			<button class="btn btn-register" onclick="window.location.href='signup.do'; return false;">
				<spring:message code="lbl.signup"/>
			</button>
		</form>
		
	</div>	
</body>
