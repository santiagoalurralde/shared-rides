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
	        			<li id="step1"><h4>1. Que tipo de usuario busca?</h4></li>
	        			<li id="step2"><h4>2. Seleccione un turno</h4></li>
	        			<li id="step3"><h4>3. Seleccione su ubicacion</h4></li>
	        		</ul>
	        	</div>
	        	
	        	<!----	Form	---->
	        	<form name="updateSearchForm" id="updateForm" class="theBox" method="POST" action="">
	        	
	        		<!----	Images			---->
	        		<div id="steps" style="width: 300px; margin: 10px 0px 0px 250px">
			        	<img id="imgBoot" 	 	title="peaton"		class="imagesSteps"	src="resources/images/boot.png" 	style="margin-right: 100px"					/>
						<img id="imgSteering"	title="conductor"	class="imagesSteps"	src="resources/images/steering.png" 											/>
						<img id="imgSun" 		title="dia"			class="imagesSteps"	src="resources/images/sun.png" 		style="display: none; margin-right: 100px" 	/>
						<img id="imgMoon"		title="tarde/noche"	class="imagesSteps"	src="resources/images/moon.png" 	style="display: none"						/>
	        		</div>
	        		    
	        		  			
	        		<!----	Map Driver		---->		
					<div id="mapDriver">
						<div id="map"></div>
						<span class="t1" style = "visibility:hidden">
							<a id="permalink" href=""></a>
						</span>

						<span id='mapinfo'>
							<span id='currentscale' style="display:none"></span>
						</span>						
					</div>
					
					<!----	Map Pedestrian	---->	
	        		<div id="mapPedestrian">
	        			<div id="map2" class="mapSimple"></div>
	        		</div>
	        	
	        		<!---- Results List		---->     
	        		<div id="listFound" style="width: 400px; margin-left: 325px">
	        			<table id="tableFound">
								<tr>
								<th colspan="4"> Usuarios Encontrados </th>
							</tr>
						</table>
	        		</div>
	        		
	        		<!----	Buttons	---->
	        		<div id="buttonsSteps">
	        			<input type="button" class="but" id="butBack" 	value="Anterior" 	onClick="stepsUpdate(-1);"	/>	
	           	   		<input type="button" class="but" id="butNext" 	value="Siguiente"	onClick="stepsUpdate(1);"	/>		
	        			<input type="button" class="but" id="butOK" 	value="Confirmar" 	style="display: none" 		/>  
	        		</div> 	  
	        		
	        		
	        		<script src="resources/scripts/jsFunctions.js" 	type="text/javascript"></script>
	        		<script src="resources/maps/OpenLayers.js"		type="text/javascript"></script>    
					<script src="resources/maps/OpenStreetMap.js"  	type="text/javascript"></script>
					<script src="resources/maps/proj4js.js"			type="text/javascript"></script>
	        		<script src="resources/maps/osmap.js" 			type="text/javascript"></script>
    				<script src="resources/maps/track.js" 			type="text/javascript"></script>
    				<script src="resources/maps/osmapSimple.js" 	type="text/javascript"></script>
		
    				<script >
     					if(typeof(a)!="undefined")
      					{
        					a = null;
      					}
      					else 
      					{		
        					initMapCoords(lonlat, zoom, map);
      					}
    				</script>
	            		
	        	</form>	        	
	        	
            </div>
		</div>
</body>
