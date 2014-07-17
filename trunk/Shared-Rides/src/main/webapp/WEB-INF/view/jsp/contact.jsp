<%@	taglib uri="http://www.springframework.org/tags" 	prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 		prefix="c" %>

<body onLoad="stepsUpdate(1);">
	<div id="theContent">	
		
		<section title="Contact" class="tupper">
				<div class="theBoard lightBorder" style="margin-top: 10px"> 							

					<form name="" method="" action="">
	        		    
	        		    <h2>Datos Personales</h2>
	        		    	
	        		    <div id="firstStep" class="steps">
            				
            				<span>
	            				<span class="blockLeft">
					              <strong>Identificación Personal</strong>:          
					              <span class="red">*</span>
					            </span> 
	            				
	            				<input type="text" id="personalId" class="blockRight theInputs" required/>
            				</span> 
            				
            				<div class="split"></div>
            				
            				<span>
	            				<span class="blockLeft">
					              <strong>Nombre</strong>:          
					              <span class="red">*</span>
					            </span> 
	            				
	            				<input type="text" id="name" class="blockRight theInputs" required/>
            				</span> 
            				
            				<div class="split"></div>
            				
            				<span>
	            				<span class="blockLeft">
					              <strong>Apellido</strong>:          
					              <span class="red">*</span>
					            </span> 
	            				
	            				<input type="text" id="surname" class="blockRight theInputs" required/>
            				</span> 
            				
            				<div class="split"></div>
            				
            				<span>
	   				            <span class="blockLeft">
					              <strong>Contraseña</strong>:          
					              <span class="red">*</span>
					            </span> 
	            				
	            				<input type="password" id="password-first" class="blockRight theInputs" required/>
            				</span>

            				<div class="split"></div>
            				
            				<span>
	            				<span class="blockLeft">
					              <strong>Repita su contraseña</strong>:          
					              <span class="red">*</span>
					            </span> 
	            				
	            				<input type="password" id="password-check" class="blockRight theInputs" required/>
            				</span> 
            				
            				<div class="split"></div>

            				<span>
	            				<span class="blockLeft">
					              <strong>Teléfono Celular</strong>:          
					              <span class="red">*</span>
					            </span> 
	            				
	            				<input type="text" id="cellphone" class="blockRight theInputs" required/>
            				</span> 
	        		    </div>

		        		<!----	Buttons	---->
		        		<div id="buttonsSteps">
		        			<input type="button" class="btn" id="btnBack" 	value="Anterior" 	onClick="stepsUpdate(-1);"	/>	
		           	   		<input type="button" class="btn" id="btnNext" 	value="Siguiente"	onClick="stepsUpdate(1);"	/>		
		        			<input type="button" class="btn" id="btnOK" 	value="Confirmar" 	style="display: none" 		/>  
		        		</div> 	  
	            	
	        		</form>	  
					<div class="clearer"></div>
				</div>
		</section>
	
	</div>
</body>

<script src="resources/scripts/Register.js" 	type="text/javascript"></script>

