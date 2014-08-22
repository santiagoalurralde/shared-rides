<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body onLoad="stepNext();">		
		  
	<!----	Content	---->
    <div id="theContent">
        <section title="" class="tupper"> 
       		<h4>
       			<spring:message code="label.messageFind"/>
        	</h4>
        
        	<!----	Steps	---->
        	<div id="navSteps" class="lightBorder"> 
        		<ul>
        			<li id="step1"><b>1. </b><spring:message code="label.step1"/></li>
        			<li id="step2"><b>2. </b><spring:message code="label.step2"/></li>
        			<li id="step3"><b>3. </b><spring:message code="label.step3"/></li>
        		</ul>
        	</div>
        	
        	<!----	Form	---->
        	<form name="updateSearchForm" class="theBoard lightBorder" method="POST" action="">
        	
        		<!----	Images			---->
        		<div id="steps">
		        	<img id="imgBoot" 	 	title="Peaton"		class="imagesSteps"	src="resources/images/boot.png" 	/>
					<img id="imgSteering"	title="Conductor"	class="imagesSteps"	src="resources/images/steering.png" />
					<img id="imgSun" 		title="Dia"			class="imagesSteps"	src="resources/images/sun.png" 		/>
					<img id="imgMoon"		title="Tarde/Noche"	class="imagesSteps"	src="resources/images/moon.png" 	/>
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
        	
        		<!---- Results List ---->     
        		<div id="listFound">
        			<div class="alerts"><img src="resources/images/message.png"> 
        				<p><br> <spring:message code="label.foundNone"/>
        			</div>
        			
        			<table id="tableFound" class="tableUsers">
						<tr>
							<th colspan="4"><spring:message code="label.foundUsers"/></th>
						</tr>													
					</table>
        		</div>
        		
        		<!---- Buttons ---->
        		<div id="buttonsSteps">
        			<input type="button" class="btn" id="btnBack" 	value="Anterior" 	onClick="stepBack();"	/>	
           	   		<input type="button" class="btn" id="btnNext" 	value="Siguiente"	onClick="stepNext();"	/>		
        			<input type="button" class="btn" id="btnOK" 	value="Confirmar" 	/>  
        		</div> 	  
       		</form>	        	
        </section>        
	</div>
	
	<div id='dlgChooseUserType' title="Oops!" class='theDialog'>Selecciona un tipo de usuario! <br><p>(Presiona Esc. para volver)</div>
	<div id='dlgChooseShift' 	title="Oops!" class='theDialog'>Selecciona un turno! <br><p>(Presiona Esc. para volver)</div>
	
	<c:set var="lblBlocks1"><spring:message code="label.blocks1"/></c:set>
	<c:set var="lblBlocks2"><spring:message code="label.blocks2"/></c:set>	
	<input type="hidden" id="lblBlocks1" value="${lblBlocks1}"/>
	<input type="hidden" id="lblBlocks2" value="${lblBlocks2}"/>
		
	<section>
		<script src="resources/scripts/MainMenu.js" 	type="text/javascript"></script>
		<script src="resources/maps/OpenLayers.js"		type="text/javascript"></script>    
		<script src="resources/maps/OpenStreetMap.js"  	type="text/javascript"></script>
		<script src="resources/maps/proj4js.js"			type="text/javascript"></script>
		<script src="resources/maps/osmap.js" 			type="text/javascript"></script>
		<script src="resources/maps/track.js" 			type="text/javascript"></script>
		<script src="resources/maps/osmapSimple.js" 	type="text/javascript"></script>
	</section>
</body>



<script>
	initMapCoords(lonlat, zoom, map);
	
	$(function() {
		$(document).tooltip({
			track: true,
			position: {
				my: "center top+60",
				using: function(position, feedback) {
					$(this).css(position);
				}
			}
		 });
	 });
</script>

