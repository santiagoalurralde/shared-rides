<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<body>		
    <div class="sr-content">
    	
        <section class="sr-tupper sr-mini">
        	<h2><spring:message code="label.contactus"/></h2>
        
        	<div class="sr-board border-light">
        		<!--  style="background: url(resources/images/Contact.png); background-size: cover" -->

				<div class="block-half block-left">
					<h3><spring:message code="label.email"/></h3><br>	
					<span class="text-spaced">
						leandro.bagur@gmail.com			<br>
						santiago.alurralde@gmail.com	<br>
					</span>
					
					<div class="split"></div>

					<h3><spring:message code="label.telephone"/></h3><br>
					<span class="text-spaced">
						+549-351-3262650	<br>
						+549-357-1418179	<br>
					</span>	

					<div class="split"></div>
				
					<h3><spring:message code="label.address"/></h3><br>
					<span class="text-spaced">
						Universidad Católica de Córdoba		<br>
						Obispo Trejo 323 - Bº Centro.		<br>
						Córdoba, Argentina. 			
					</span>						
				</div>
				
				<div class="block-half block-right">
					<input class="theInputs" type="text" name="name"	placeholder="Martin Paganni">	<br>
					<input class="theInputs" type="text" name="email"	placeholder="mike@email.com">	<br>			
					<textarea rows="5" cols="30" class="theInputs" placeholder="Mensaje"></textarea>	<br>
					<input class="btn" type="submit" value="<spring:message code="label.send"/>">
				</div> 
				
				<div class="clearer"></div> 
      		</div>
        </section>        
	</div>
</body>
