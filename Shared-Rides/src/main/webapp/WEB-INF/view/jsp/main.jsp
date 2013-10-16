<body onLoad="stepsUpdate(1);">		
		
		<!----	Content	---->
	    <div id="theContent" class="theBox">
	        <div class="tupper"> 
	       		<h4>
	       			Encuentre con quién asociarse!
	        	</h4>
	        
	        	<!----	Steps	---->
	        	<div id="navSteps" class="theBox"> 
	        		<ul>
	        			<li><h4>1. Que tipo de usuario busca?</h4></li>
	        			<li><h4>2. Seleccione un turno</h4></li>
	        			<li><h4>3. Seleccione su ubicacion</h4></li>
	        		</ul>
	        	</div>
	        	
	        	<!----	Form	---->
	        	<form name="updateSearchForm" id="updateForm" class="theBox" method="POST" action="">
	        	
	        		<!----	Images	---->
	        		<div id="steps" style="width: 300px; margin: 10px 0px 0px 250px">
			        		<img id="imgBoot" 	 	class="imagesSteps"	src="resources/images/boot.png" 	style="margin-right: 100px">
							<img id="imgSteering"	class="imagesSteps"	src="resources/images/steering.png" >
							<img id="imgSun" 		class="imagesSteps"	src="resources/images/sun.png" 		style="margin-right: 100px; display: none">
							<img id="imgMoon"		class="imagesSteps"	src="resources/images/moon.png" 	style="display: none">
	        		</div>
	        			
	        		<!----	Map		---->
	        		<div id="mapWrap"  style="margin-left: 180px">
				    	<div id="map" class="smallmap">	</div>	    	
					</div>   	
	        		
	        		<!---- List ---->     
	        		<div id="listFound" style="width: 400px; margin-left: 325px">
	        					<table id="tableFound">
									<tr>
										<th colspan="4"> Usuarios Encontrados </th>
									</tr>
								</table>
	        		</div>
	        		 	
	        		<!----	Buttons	---->
	        		<div id="buttonsSteps">
	        			<input type="button" class="but" id="butBack" 	value="Volver" 		onClick="stepsUpdate(-1);"/>	
	        			<input type="button" class="but" id="butNext"	value="Siguiente" 	onClick="stepsUpdate(1);" />	        		
	        	   		<input type="button" class="but" id="butOK" 	value="Confirmar" 	style="display: none" /> 
	        	   		<!--  <input type="submit" class="but" id="butOK"	  	value="Confirmar"	style="display: none"/>  -->  
	        	   		
	        		</div> 	  
	            		
	        	</form>	        	
	        	
            </div>
		</div>
</body>
