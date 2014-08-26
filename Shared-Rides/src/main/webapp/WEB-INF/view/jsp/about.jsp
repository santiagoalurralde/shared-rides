<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<body>		

    <div class="sr-content">

 		<!-----------------------------------------
		[Who we are]
		------------------------------------------>
        <section class="sr-tupper sr-mini">
        	<h3><spring:message code="lbl.whoweare"/></h3>
        	
			<div class="block-half block-left">
				<img class="about-pic" src="resources/images/santi.jpg"/> <br>
				<h4 class="about-name"> SANTIAGO ALURRALDE </h4>		
				<span><spring:message code="lbl.frontenddev"/> / <spring:message code="lbl.uidesigner"/></span>		
			</div>
			
			<div class="block-half block-right">
				<img class="about-pic" src="resources/images/leandro.jpg"/> <br>
				<h4 class="about-name"> LEANDRO BAGUR </h4>		
				<span><spring:message code="lbl.backenddev"/> / <spring:message code="lbl.dbadmin"/></span>
			</div> 
			
			<div class="clearer"></div>
		</section>

 		<!-----------------------------------------
		[Why Shared Rides]
		------------------------------------------>
        <div class="advantages">	
        	<h3>Por qu&eacute; Shared Rides?</h3>
        	
        	<div>
				<div class="block-third" >
					<img class="advan-pic" src="resources/images/green.png"/><br>
					<span>Cuidar el medioambiente</span>
				</div>
				<div class="block-third">
					<img class="advan-pic" src="resources/images/save.png"/><br>
					<span>Ahorrar</span>				
				</div>
				<div class="block-third">
					<img class="advan-pic" src="resources/images/traffic.png"/><br>
					<span>Reducir el trafico</span>
				</div>
				<div class="clearer"></div>			
			</div>
        </div>        
	</div>
</body>
