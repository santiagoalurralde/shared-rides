<body onLoad="stepsUpdate(1);">		

		<script src="resources/maps/OpenLayers.js">		</script>    
		<script src="resources/maps/OpenStreetMap.js">	</script>
		<script src="resources/maps/proj4js.js">		</script>

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
			        		<img id="imgBoot" 	 	class="imagesSteps"	src="resources/images/boot.png" 	style="margin-right: 100px">
							<img id="imgSteering"	class="imagesSteps"	src="resources/images/steering.png" >
							<img id="imgSun" 		class="imagesSteps"	src="resources/images/sun.png" 		style="margin-right: 100px; display: none">
							<img id="imgMoon"		class="imagesSteps"	src="resources/images/moon.png" 	style="display: none">
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
	        			<div id="map2"></div>
	        		</div>
	        	
	        		<!---- Results List		---->     
	        		<div id="listFound" style="width: 400px; margin-left: 325px">
	        					<table id="tableFound">
									<tr>
										<th colspan="4"> Usuarios Encontrados </th>
									</tr>
								</table>
	        		</div>
	        		
	        		
	        		<!---- Data		
	        		<span id="dataSent">
						
	        		</span>
	        		---->
	        		
	        		<!----	Buttons	---->
	        		<div id="buttonsSteps">
	        			<input type="button" class="but" id="butBack" 	value="Anterior" 	onClick="stepsUpdate(-1);"	/>	
	           	   		<input type="button" class="but" id="butNext" 	value="Siguiente"	onClick="stepsUpdate(1);"	/>		
	        			<!--
	        			<input type="button" class="but" id="butOK" 	value="Confirmar" 	style="display: none" />  
	        			-->
	        	   		 
	        	   		<input type="submit" class="but" id="butOK"	  	value="Confirmar"	style="display: none"/>    
	        	   		
	        		</div> 	  
	        		
	        		<script type="text/javascript" src="resources/maps/osmap.js"></script>
    				<script type="text/javascript" src="resources/maps/track.js"></script>
		
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
