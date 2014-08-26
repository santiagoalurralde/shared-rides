<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body onLoad="stepNext();">		
		  
    <div class="sr-content">	
        <section class="sr-tupper"> 
       		<h4> <spring:message code="lbl.messageFind"/> </h4>
        
			<!-----------------------------------------
			[Steps]
			------------------------------------------>
        	<div class="nav-steps border-light"> 
        		<ul>
        			<li class="step1"><b>1. </b><spring:message code="lbl.step1"/></li>
        			<li class="step2"><b>2. </b><spring:message code="lbl.step2"/></li>
        			<li class="step3"><b>3. </b><spring:message code="lbl.step3"/></li>
        		</ul>
        	</div>
        	
			<!-----------------------------------------
			[Search Form]
			------------------------------------------>
        	<form name="form-search" class="sr-board border-light" method="POST" action="">
        	
				<!-----------------------------------------
				[Images]
				------------------------------------------>
        		<div class="steps step-usertype">
        			<ul>
	        			<li class="choices choice-pedestrian">
				        	<img src="resources/images/boot.png"/>
				        	<label><spring:message code="lbl.pedestrian"/></label>
				        </li>
			        	<li class="choices choice-driver">
							<img src="resources/images/steering.png"/>
				        	<label><spring:message code="lbl.driver"/></label>
			        	</li>	
		        	</ul>		
        		</div>

        		<div class="steps step-shift">
        			<ul>
	        			<li class="choices choice-morning">
							<img src="resources/images/sun.png"/>
				        	<label><spring:message code="lbl.morning"/></label>
				        </li>
			        	<li class="choices choice-afternoon">
							<img src="resources/images/moon.png"/>
				        	<label><spring:message code="lbl.afternoon"/></label>	
			        	</li>	
		        	</ul>	        		
        		</div>
				
				<!-----------------------------------------
				[Maps]
				------------------------------------------>	
				<div class="map-driver">
					<div id="map"></div>
					<span class="t1" style = "visibility:hidden">
						<a id="permalink" href=""></a>
					</span>

					<span id="mapinfo">
						<span id="currentscale" style="display:none"></span>
					</span>						
				</div>
					
        		<div class="map-pedestrian">
        			<div id="map2" class="map-simple"></div>
        		</div>
        	
				<!-----------------------------------------
				[Results List]
				------------------------------------------>   
        		<div class="search-results">
        			<div class="alerts"><img src="resources/images/message.png"> 
        				<p><br> <spring:message code="lbl.foundNone"/>
        			</div>
        			
        			<table class="sr-table-users table-found">
						<tr>
							<th colspan="4"><spring:message code="lbl.foundUsers"/></th>
						</tr>													
					</table>
        		</div>
        		
        		<div class="buttons-steps">
        			<input type="button" class="btn btn-back" 		
        				value="<spring:message code="lbl.button-previous"	/>" onClick="stepBack();"/>	
        			<input type="button" class="btn btn-default"  	
        				value="<spring:message code="lbl.button-search"		/>"/>         			
           	   		<input type="button" class="btn btn-next" 		
           	   			value="<spring:message code="lbl.button-next"		/>" onClick="stepNext();"/>		
        			<input type="button" class="btn btn-OK"	
        				value="<spring:message code="lbl.button-confirm" 	/>"/>		
        		</div> 	 
       		</form>	        	
        </section>        
	</div>
	
	<div id="dlg-choose-type"	title="Oops!" class="sr-dialog"><spring:message code="lbl.alert-choose-type"/></div>
	<div id="dlg-choose-shift"	title="Oops!" class="sr-dialog"><spring:message code="lbl.alert-choose-shift"/></div>

	<!-----------------------------------------
	[Labels]
	------------------------------------------>
	<c:set var="lblBlocks1"><spring:message code="lbl.blocks1"/></c:set>
	<c:set var="lblBlocks2"><spring:message code="lbl.blocks2"/></c:set>	
	<input type="hidden" id="lbl-blocks1" 	value="${lblBlocks1}"/>
	<input type="hidden" id="lbl-blocks2" 	value="${lblBlocks2}"/>

	<!-----------------------------------------
	[Variables]
	------------------------------------------>
	<input type="hidden" id="hdn-validate"	value="${validate}"/>	
</body>


<!-----------------------------------------
[Scripts]
------------------------------------------>		
<script src="resources/scripts/MainMenu.js" 	type="text/javascript"></script>
<script src="resources/maps/OpenLayers.js"		type="text/javascript"></script>    
<script src="resources/maps/OpenStreetMap.js"  	type="text/javascript"></script>
<script src="resources/maps/proj4js.js"			type="text/javascript"></script>
<script src="resources/maps/osmap.js" 			type="text/javascript"></script>
<script src="resources/maps/track.js" 			type="text/javascript"></script>
<script src="resources/maps/osmapSimple.js" 	type="text/javascript"></script>

<script>
	initMapCoords(lonlat, zoom, map);
</script>

