<%@	taglib uri="http://www.springframework.org/tags" 	prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 		prefix="c" %>

<body onLoad="stepNext();">

	<div class="sr-content">	

		<section title="Contact" class="sr-tupper">

			<div class="nav-steps border-light"> 
        		<ul>
        			<li class="step-signup1"><b>1. </b><spring:message code="label.suStep1"/></li>
        			<li class="step-signup2"><b>2. </b><spring:message code="label.suStep2"/></li>
        			<li class="step-signup3"><b>3. </b><spring:message code="label.suStep3"/></li>
        		</ul>
        	</div>
        	
			<div class="sr-board border-light">
       		    	        		    	
       		    <div class="step step-first">
         			<span>
           				<span class="block-left">
			               	<spring:message code="label.organization"/>	          
			            </span> 
         				<select id="organization" class="block-right sr-inputs">
         					<c:forEach var="org" items="${organizations}">
           						<option value="${org.orgId}" selected>${org.orgName}</option>
							</c:forEach>			            					
           				</select>       
         			</span>	
         			
         			<div class="split"></div>
         				
         			<span>
           				<span class="block-left">
			               	<spring:message code="label.personalId"/>	          
			            </span> 
           				
           				<input 	type="text" id="personal-id" class="block-right sr-inputs" 
           						onChange="checkIt(this);"/>
         			</span> 
         				
         			<div class="split"></div>
         				
         			<span>
           				<span class="block-left">
			               <spring:message code="label.firstName"/>	         
			        	</span> 
           				
           				<input 	type="text" id="name" class="block-right sr-inputs" 
           						onChange="checkIt(this)" onKeyPress="checkAlphabetic(event);"/>
         			</span> 
         				
         			<div class="split"></div>
         				
         			<span>
           				<span class="block-left">
			               <spring:message code="label.lastName"/>	         
			            </span> 
           				
           				<input 	type="text" id="surname" class="block-right sr-inputs" 
           						onChange="checkIt(this)" onKeyPress="checkAlphabetic(event);"/>
          			</span> 
         				
         			<div class="split"></div>
         				
         			<span>
           				<span class="block-left">
			               <spring:message code="label.email"/>	         
			            </span> 
           				
           				<input 	type="text" id="email" class="block-right sr-inputs" 
           						onChange="checkIt(this); checkEmail(this)"/>
          			</span> 
         				
         			<div class="split"></div>
         				          				
       				<span>
			            <span class="block-left">
			               <spring:message code="label.password"/>	
            			</span> 
        				
        				<input 	type="password" id="password-first" class="block-right sr-inputs" 
        						onChange="checkIt(this);"/>
       				</span>

         			<div class="split"></div>
         				
         			<span>
           				<span class="block-left">
			               <spring:message code="label.passwordIdem"/>	
			            </span> 
           				
           				<input 	type="password" id="password-check" class="block-right sr-inputs" 
           						onChange="checkIt(this); checkPassword();"/>
         			</span> 
         				
         			<div class="split"></div>

         			<span>
           				<span class="block-left">
			               <spring:message code="label.telephone"/>
						</span> 
           				
           				<input 	type="text" id="cellphone" class="block-right sr-inputs" 
           						onChange="checkIt(this);" onKeyUp="checkNumeric(this);"/>
         			</span> 
       		    </div>
       		    <!-------------------------------------------------- END of FIRST STEP -------------------------------------------------->
       		    
       		    <div class="step step-second">

         			<form method="POST" action="uploadFile.do" target="uploaded"
						onSubmit="uploaded = window.open('','uploaded', 'width=300 height=200, status=no scrollbars=no, location=no, resizable=no, manu=no');"
         				enctype="multipart/form-data"> 
         				
	          			<span class="block-left">
			               <spring:message code="label.picture"/>     
			        	</span>     
						<input 	type="file" name="picture"  
								class="block-right" accept="image/x-png, image/jpeg">
	         			<input 	type="submit" class="btn" value='<spring:message code="label.send"/>'>
         			</form>
          			
          			<div class="split"></div>         			
          			
       				<span>
	           			<span class="block-left">
				         	<spring:message code="label.address"/>          				              	 
				        </span> 
	           		
	           			<input 	type="number" id="number" class="block-right sr-inputs" 
	           				   	onChange="checkIt(this);" onKeyUp="checkNumeric(this);" min="0" max="99999"/>
	           			<input	type="text" id="street" class="block-right sr-inputs" 
	           				   	onChange="checkIt(this);" />
          			</span> 
          				
          			<div class="split"></div>
          				
          			<span>
	           			<span class="block-left">
				        	<spring:message code="label.neighborhood"/>          				              	 
				        </span> 
	           				
	           			<select id="neighborhood" class="block-right sr-inputs" 
	           					onChange="checkIt(this);">
	           				<option value="0" selected></option>	           			
	           			</select>
          			</span> 

          			<div class="split"></div>
          				
          			<span>
           				<span class="block-left">
			               	<spring:message code="label.shift"/>          				              	 
			            </span> 
           				
           				<select id="shift" class="block-right sr-inputs" 
           						onChange="checkIt(this);">
           					<option value="0" selected></option>	            					           				
           					<option value="morning">	<spring:message code="label.morning"/> 		</option>	            				
           					<option value="afternoon">	<spring:message code="label.afternoon"/>	</option>
           				</select>            				
           			</span> 
          				
          			<div class="split"></div>
          				
         			<span>
 				        <span class="block-left">
		            		<spring:message code="label.userType"/>          			              	 
		           	 	</span> 
          				
           				<select id="userType" class="block-right sr-inputs"
           						onChange="checkIt(this); userTypeChanged(this);">

           				    <option value="0" selected></option>
           					<option value="pedestrian">			<spring:message code="label.pedestrian"/>	</option>	            			
           					<option value="driver">				<spring:message code="label.driver"/>		</option>
           					<option value="driver-pedestrian">	<spring:message code="label.mixed"/>		</option>	            					
           				</select>               				
           			</span>
          				
          			<div class="drives">
          				<div class="split"></div>
          			
       					<hr class="hrs">
       					
       					<h4><spring:message code="label.vehicleInfo"/></h4>
       					
       					<div class="split"></div>
         					
           				<span>
            				<span class="block-left">
				               <spring:message code="label.brand"/>          					               
				            </span> 
            				
            				<select id="brand" class="block-right sr-inputs" onChange="checkIt(this);">
	            				<option value="0" selected></option>          					
            				</select>         	            				
            			</span> 
	            			
            			<div class="split"></div>
	            			
           				<span>
            				<span class="block-left">
				               <spring:message code="label.modelVehicle"/>          					               
				            </span> 
            				
            				<input 	type="text" id="model" class="block-right sr-inputs" 
            						onChange="checkIt(this);">
   						</span> 
	      						
            			<div class="split"></div>
	            			
           				<span>
            				<span class="block-left">
				            	<spring:message code="label.licensePlate"/>         					             
				            </span> 
            				
            				<input 	type="number" id="plate-numbers" class="block-right sr-inputs" 
            						onChange="checkIt(this);" min="000" max="999" />	            				
            				<input 	type="text" id="plate-letters" class="block-right sr-inputs" 
            						onChange="checkIt(this);" onKeyPress="checkAlphabetic(event);" 
            						maxlength="3"/>
      					</span>         	
	      						
            			<div class="split"></div>
	      						
           				<span>
            				<span class="block-left">
				               <spring:message code="label.numberSeats"/>          					           
				            </span> 
		            				
   	        				<select id="number-seats" class="block-right sr-inputs" onChange="checkIt(this);">
            					<option value="0" selected></option>	            					            				
            					<option value="1">1</option>	            				
            					<option value="2">2</option>
            					<option value="3">3</option>	 
            					<option value="4">4</option>	            					
            					<option value="5">5</option>	            					
            					<option value="6">6</option>	            					
            					<option value="7">7</option>	            					  					
            				</select>          						
            			</span>  	
   					</div>
       		    </div>
				<!---------------------------------------- END of SECOND STEP ---------------------------------------->

       		    <div class="step step-third">

	       		    <!-----------------------------------------
					[Table for Definition]
					------------------------------------------> 

					<table class="table-signup sr-schedule">
						<tr>
							<th> <!-- Empty --> </th>	
							<th> <spring:message code="label.monday"/> 		</th>
							<th> <spring:message code="label.tuesday"/> 	</th>
							<th> <spring:message code="label.wednesday"/>	</th>
							<th> <spring:message code="label.thursday"/>	</th>
							<th> <spring:message code="label.friday"/>		</th>				
						</tr>
						<tr class="usertype-row">
						</tr>
						<tr id="in">
							<td> <spring:message code="label.arrival"/> </td>
						</tr>
						<tr id="out">
							<td> <spring:message code="label.departure"/> </td>									
						</tr>
					</table>	
					
					<div class="split split-table"></div>        								         													            			      				
					
					<span class="map-definition">
	       				<input	type="button" class="btn btn-map" 
	       						value='<spring:message code="label.button-ready"/>'/>      
	       						 						
	       				<input 	type="hidden" id="hdn-inout"		/>  
	         			<input 	type="hidden" id="hdn-day"			/>
	          			<input 	type="hidden" id="hdn-usertype-day"	/>  	    
        			</span> 
				</div>
				
				<div class="split split-beginning"></div> 
				       								         													            			      				
				<!-----------------------------------------
				[Alerts]
				------------------------------------------>   
				<div class="alerts"></div>
				
				<!---------------------------------------- END of THIRD STEP ---------------------------------------->
				
				<!-----------------------------------------
				[Maps]
				------------------------------------------>   	
				<div class="map-driver">
					<div id="map"></div>
					<span class="t1" style = "visibility:hidden">
						<a id="permalink" href=""></a>
					</span>

					<span id='mapinfo'>
						<span id='currentscale' style="display:none"></span>
					</span>						
				</div>
				
	    		<div class="map-pedestrian">
	    			<div id="map2" class="mapSimple"></div>
	    		</div>
        		        		
				<!-----------------------------------------
				[Buttons]
				------------------------------------------>   
        		<div class="buttons-steps">
        			<input 	type="button" class="btn btn-back"  
        					value='<spring:message code="label.button-previous"/>'	
        					onClick="stepBack();"/>	

           	   		<input 	type="button" class="btn btn-next"  	
							value='<spring:message code="label.button-next"/>'		
							onClick="stepNext();"/>	

        			<input 	type="button" class="btn btn-OK"
        					value='<spring:message code="label.button-confirm"/>'/>  
        		</div> 	  
       		</div>	  
			<div class="clearer"></div>
		</section>
	
	</div>

	<!-----------------------------------------
	[Labels]
	------------------------------------------>	
	<c:set var="lblUnsuscribe"><spring:message code="label.unsuscribe" /></c:set>	
	<input type="hidden" id="lbl-unsuscribe" value="${lblUnsuscribe}" />
</body>

<!-----------------------------------------
[Scripts]
------------------------------------------>	
<script src="resources/scripts/Register.js" 	type="text/javascript"></script>
<script src="resources/maps/OpenLayers.js"		type="text/javascript"></script>    
<script src="resources/maps/OpenStreetMap.js"  	type="text/javascript"></script>
<script src="resources/maps/proj4js.js"			type="text/javascript"></script>
<script src="resources/maps/osmap.js" 			type="text/javascript"></script>
<script src="resources/maps/track.js" 			type="text/javascript"></script>
<script src="resources/maps/osmapSimple.js" 	type="text/javascript"></script>

<script>
	initMapCoords(lonlat, zoom, map, null);
</script>


