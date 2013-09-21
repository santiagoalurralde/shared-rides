<body>  
	
	    <!----------------  MENU      -------------->
        <div id="nav" class="aBox">
            <div class="tupper">
	        <ul>       
		        <li><a href="#">		<img width=120px src="resources/images/logoFin1.png" style="position: relative; left: -20px;"/></a></li>
		        <li><a href="#">     	Mi Perfil   </a></li>
		        <li><a href="#">        Personas    </a></li>
		        <li><a href="#">     	Contacto    </a></li>
		        <li><a href="#">        Acerca 		</a></li>
            </ul>
            </div>
	    </div>
	

        <!----------------  CONTENT      -------------->
	    <div id="contentMain">
	        <div class="tupper"> 
	       		<h4>
	       			Encuentre con quién asociarse!
	        	</h4>
	        	
	        	<div id="navSteps" class="aBox"> 
	        		<ul>
	        			<li><h4>1. Que tipo de usuario busca?</h4></li>
	        			<li><h4>2. Seleccione un turno</h4></li>
	        			<li><h4>3. Seleccione su ubicacion</h4></li>
	        		</ul>
	        	</div>
	        	
	        	<form name="updateSearchForm" id="updateForm" class="aBox">
  		
	        		
	        		<div id="steps1">
	        			<div id="step1" style="margin: 10px 0px 0px 250px">
			        		<img id="imgBoot" 		src="resources/images/boot.png" 	width="175" style="margin-right: 100px">
							<img id="imgSteering"	src="resources/images/steering.png" width="175">
						</div>
	        		</div>
	        		
	        		<div id="updateButtons">
	        			<input type="button" class="but" id="backBtn" value="Volver" onClick="stepsUpdate(-1);" style="visibility:hidden" />	
	        			<input type="button" class="but" id="nextBtn" value="Siguiente" onClick="stepsUpdate(1);" />
	        		</div>
	        			
	        		<script>

	    				$( "#nextBtn" ).click(function() {
	    					$( "#imgBoot" ).attr('src','resources/images/sun.png'); 
	   					});

					</script>	        		
	        	</form>
            </div>
		</div>
</body>