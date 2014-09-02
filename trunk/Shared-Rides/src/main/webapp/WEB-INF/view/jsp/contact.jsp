<body>		
    <div class="sr-content">
    	
        <section class="sr-tupper sr-mini">
        	<h2><spring:message code="lbl.contactus"/></h2>
        
        	<div class="sr-board border-light">
        		<!--  style="background: url(resources/images/Contact.png); background-size: cover" -->

				<div class="block-half block-left">
					<h3><spring:message code="lbl.email"/></h3><br>	
					<span class="text-spaced">
						leandro.bagur@gmail.com			<br>
						santiago.alurralde@gmail.com	<br>
					</span>
					
					<div class="split"></div>

					<h3><spring:message code="lbl.telephone"/></h3><br>
					<span class="text-spaced">
						+549-351-3262650	<br>
						+549-357-1418179	<br>
					</span>	

					<div class="split"></div>
				
					<h3><spring:message code="lbl.address"/></h3><br>
					<span class="text-spaced">
						Universidad Católica de Córdoba		<br>
						Obispo Trejo 323 - B° Centro.		<br>
						Córdoba, Argentina. 			
					</span>						
				</div>
				
				<div class="block-half block-right">
					<input class="sr-inputs" type="text" name="name"	placeholder="Martin Paganni">	<br>
					<input class="sr-inputs" type="text" name="email"   placeholder="mike@email.com">	<br>
					<textarea rows="5" cols="30" class="sr-inputs" placeholder="Mensaje"></textarea>	<br>
					<input class="btn" type="submit" value='<spring:message code="lbl.send"/>'>
				</div> 
				
				<div class="clearer"></div> 
      		</div>
        </section>        
	</div>
</body>
