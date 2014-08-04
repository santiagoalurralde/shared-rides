<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<body>		
		  
	<!----	Content	---->
    <div id="theContent">
        <section class="tupper mini">
        	<h3><spring:message code="label.whoweare"/></h3>
        	
			<div class="blockLeft blockHalf">
				<img class="aboutPic" src="resources/profilePic/santi.jpg" >
				<br>
				<h4 class="aboutName"> SANTIAGO ALURRALDE </h4>		
				<span><spring:message code="label.frontenddev"/> / <spring:message code="label.uidesigner"/></span>

						
			</div>
			
			<div class="blockRight blockHalf">
				<img class="aboutPic" src="resources/profilePic/leandro.jpg" >
				<br>
				<h4 class="aboutName"> LEANDRO BAGUR </h4>		
				<span><spring:message code="label.backenddev"/> / <spring:message code="label.dbadmin"/></span>
			</div> 
			
			<div class="clearer"></div>
		</section>
        <div id="advantages">	
        	<h3>Por qu&eacute; Shared Rides?</h3>
        	
        	<div>
				<div class="blockThird" >
					<img class="advanPic" src="resources/images/green.png" >
					<br>
					<span>Cuidar el medioambiente</span>
				</div>
				<div class="blockThird">
					<img class="advanPic" src="resources/images/save.png" >
					<br>
					<span>Ahorrar</span>				
				</div>
				<div class="blockThird">
					<img class="advanPic" src="resources/images/traffic.png" >
					<br>
					<span>Reducir el trafico</span>
				</div>
				<div class="clearer"></div>			
			</div>
        </div>        
	</div>
</body>
