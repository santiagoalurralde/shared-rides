<body onLoad="stepsUpdate(1);">		
		<!--------------------------------------------------------------------
		[Content]
		--------------------------------------------------------------------->  
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
	        	<form name="updateSearchForm" id="updateForm" class="theBox" method="POST" action="find.do">
	        	
	        		<!----	Images	---->
	        		<div id="steps" style="margin: 10px 0px 0px 250px">
			        		<img id="imgBoot" 		src="resources/images/boot.png" 	width="175" style="margin-right: 100px">
							<img id="imgSteering"	src="resources/images/steering.png" width="175">
							<img id="imgSun" 		src="resources/images/sun.png" 		width="175" style="margin-right: 100px; display: none">
							<img id="imgMoon"		src="resources/images/moon.png" 	width="175" style="display: none">
	        		</div>
	        		
	        		<!----	Map		---->
	        		<div id="mapWrap"  style="margin-left: 180px">
				    	<div id="map" class="smallmap">	</div>
					</div>   	

	        		<!----	Buttons	---->
	        		<div id="buttonsSteps">
	        			<input type="button" class="but" id="butBack" value="Volver" 	onClick="stepsUpdate(-1);"/>	
	        			<input type="button" class="but" id="butNext" value="Siguiente" onClick="stepsUpdate(1);" />
	        			<input type="submit" class="but" id="butOK"	  value="Confirmar"	style="display: none"/>
	        		</div>       		  
	        	</form>
	        	
	        	<input type="button" class="but" value="resultados" onClick="printResults()" />
	        	
            </div>
		</div>
</body>