<%@	taglib uri="http://www.springframework.org/tags" 	prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 		prefix="c" %>

<body onLoad="stepNext();">

	<!----	Content	---->
	<div id="theContent">	
		<section title="Contact" class="tupper">
			<div id="navSteps" class="lightBorder"> 
        		<ul>
        			<li id="stepSignUp1"><b>1. </b><spring:message code="label.suStep1"/></li>
        			<li id="stepSignUp2"><b>2. </b><spring:message code="label.suStep2"/></li>
        			<li id="stepSignUp3"><b>3. </b><spring:message code="label.suStep3"/></li>
        		</ul>
        	</div>
        	
			<div class="theBoard lightBorder">
       		    	        		    	
       		    <div id="firstStep" class="steps">
         			<span>
           				<span class="blockLeft">
			               	<spring:message code="label.organization"/>	          
			            </span> 
         				<select id="organization" class="blockRight theInputs">
         					<c:forEach var="org" items="${organizations}">
           						<option value="${org.orgId}" selected>${org.orgName}</option>
							</c:forEach>			            					
           				</select>       
         			</span>	
         			
         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			               	<spring:message code="label.personalId"/>	          
			            </span> 
           				
           				<input 	type="text" id="personalId" class="blockRight theInputs" 
           						onChange="checkIt(this);"/>
         			</span> 
         				
         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			               <spring:message code="label.firstName"/>	         
			        	</span> 
           				
           				<input 	type="text" id="name" class="blockRight theInputs" 
           						onChange="checkIt(this)" onKeyPress="checkAlphabetic(event);"/>
         			</span> 
         				
         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			               <spring:message code="label.lastName"/>	         
			            </span> 
           				
           				<input 	type="text" id="surname" class="blockRight theInputs" 
           						onChange="checkIt(this)" onKeyPress="checkAlphabetic(event);"/>
          			</span> 
         				
         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			               <spring:message code="label.email"/>	         
			            </span> 
           				
           				<input 	type="text" id="email" class="blockRight theInputs" 
           						onChange="checkIt(this)"/>
          			</span> 
         				
         			<div class="split"></div>
         				          				
       				<span>
			            <span class="blockLeft">
			               <spring:message code="label.password"/>	
            			</span> 
        				
        				<input 	type="password" id="password-first" class="blockRight theInputs" 
        						onChange="checkIt(this);"/>
       				</span>

         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			               <spring:message code="label.passwordIdem"/>	
			            </span> 
           				
           				<input 	type="password" id="password-check" class="blockRight theInputs" 
           						onChange="checkIt(this); checkPassword();"/>
         			</span> 
         				
         			<div class="split"></div>

         			<span>
           				<span class="blockLeft">
			               <spring:message code="label.telephone"/>
						</span> 
           				
           				<input 	type="text" id="cellphone" class="blockRight theInputs" 
           						onChange="checkIt(this);" onKeyUp="checkNumeric(this);"/>
         			</span> 
       		    </div>
       		    <!-------------------------------------------------- END of FIRST STEP -------------------------------------------------->
       		    
       		    <div id="secondStep" class="steps" style="display: none">

         			<form method="POST" action="uploadFile.do" target="uploaded"
						onSubmit="uploaded = window.open('','uploaded', 'width=300 height=200, status=no scrollbars=no, location=no, resizable=no, manu=no');"
         				enctype="multipart/form-data"> 
         				
	          			<span class="blockLeft">
			               <spring:message code="label.picture"/>     
			        	</span>     
						<input 	type="file" name="picture"  
								class="blockRight" accept="image/x-png, image/jpeg">
	         			<input 	type="submit" class="btn" value='<spring:message code="label.send"/>'>
         			</form>
          			
          			<div class="split"></div>         			
          			
       				<span>
	           			<span class="blockLeft">
				         	<spring:message code="label.address"/>          				              	 
				        </span> 
	           		
	           			<input 	type="number" id="number" class="blockRight theInputs" 
	           				   	onChange="checkIt(this);" onKeyUp="checkNumeric(this);" min="0" max="99999"/>
	           			<input	type="text" id="street" class="blockRight theInputs" 
	           				   	onChange="checkIt(this);" />
          			</span> 
          				
          			<div class="split"></div>
          				
          			<span>
	           			<span class="blockLeft">
				        	<spring:message code="label.neighborhood"/>          				              	 
				        </span> 
	           				
	           			<select id="neighborhood" class="blockRight theInputs" 
	           					onChange="checkIt(this);">
	           				<option value="0" selected></option>	           			
	           			</select>
          			</span> 

          			<div class="split"></div>
          				
          			<span>
           				<span class="blockLeft">
			               	<spring:message code="label.shift"/>          				              	 
			            </span> 
           				
           				<select id="shift" class="blockRight theInputs" 
           						onChange="checkIt(this);">
           					<option value="0" selected></option>	            					           				
           					<option value="morning">	<spring:message code="label.morning"/> 		</option>	            				
           					<option value="afternoon">	<spring:message code="label.afternoon"/>	</option>
           				</select>            				
           			</span> 
          				
          			<div class="split"></div>
          				
         			<span>
 				        <span class="blockLeft">
		            		<spring:message code="label.userType"/>          			              	 
		           	 	</span> 
          				
           				<select id="userType" class="blockRight theInputs"
           						onChange="checkIt(this); userTypeChanged(this);">
           				    <option value="0" selected></option>
           					<option value="pedestrian">			<spring:message code="label.pedestrian"/>	</option>	            				
           					<option value="driver">				<spring:message code="label.driver"/>		</option>
           					<option value="driver-pedestrian">	<spring:message code="label.mixed"/>		</option>	            					
           				</select>               				
           			</span>
          				
          			<div id="drives" style="display:none">
          				<div class="split"></div>
          			
       					<hr class="hrs">
       					
       					<h4><spring:message code="label.vehicleInfo"/></h4>
       					
       					<div class="split"></div>
         					
           				<span>
            				<span class="blockLeft">
				               <spring:message code="label.brand"/>          					               
				            </span> 
            				
            				<select id="brand" class="blockRight theInputs" onChange="checkIt(this);">
	            				<option value="0" selected></option>	            					            				            				           					
            				</select>         	            				
            			</span> 
	            			
            			<div class="split"></div>
	            			
           				<span>
            				<span class="blockLeft">
				               <spring:message code="label.modelVehicle"/>          					               
				            </span> 
            				
            				<input 	type="text" id="model" class="blockRight theInputs" 
            						onChange="checkIt(this);">
   						</span> 
	      						
            			<div class="split"></div>
	            			
           				<span>
            				<span class="blockLeft">
				            	<spring:message code="label.licensePlate"/>         					             
				            </span> 
            				
            				<input 	type="number" id="plateNumbers" class="blockRight theInputs" 
            						onChange="checkIt(this);" min="000" max="999" />	            				
            				<input 	type="text" id="plateLetters" class="blockRight theInputs" 
            						onChange="checkIt(this);" onKeyPress="checkAlphabetic(event);" 
            						maxlength="3"/>
      					</span>         	
	      						
            			<div class="split"></div>
	      						
           				<span>
            				<span class="blockLeft">
				               <spring:message code="label.numberSeats"/>          					           
				            </span> 
		            				
   	        				<select id="numberSeats" class="blockRight theInputs" onChange="checkIt(this);">
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


       		    <div id="thirdStep" class="steps" style="display: none">
					<table id="tableSignUp" class="theSchedule">
						<tr>
							<th> <!-- Empty --> </th>	
							<th> <spring:message code="label.monday"/> 		</th>
							<th> <spring:message code="label.tuesday"/> 	</th>
							<th> <spring:message code="label.wednesday"/>	</th>
							<th> <spring:message code="label.thursday"/>	</th>
							<th> <spring:message code="label.friday"/>		</th>				
						</tr>
						<tr id="userTypeRow">
						</tr>
						<tr id="in">
							<td> <spring:message code="label.arrival"/> 	</td>
						</tr>
						<tr id="out">
							<td> <spring:message code="label.departure"/> 	</td>									
						</tr>
					</table>
					
					<div class="split"></div>
	      						
          			<span id="applyMapDefinition">
           				<input 	type="button" id="btnMap" class="btn" 
           						value='<spring:message code="label.ready"/>'/>  
           						
           				<input type="hidden" id="hdnInOut" 		/>  
             			<input type="hidden" id="hdnDay" 		/>
              			<input type="hidden" id="hdnUserTypeDay"/>
             			  	            						            						
           			</span>  	
				</div>
				<!---------------------------------------- END of THIRD STEP ---------------------------------------->
				
				
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

				<div class="split"></div>        								         													            			
	
				<!----	Red Alerts 	----->
				<div id="alert" class="alerts"></div>          				
				
        		<!----	Buttons	---->
        		<div id="buttonsSteps">
        			<input 	type="button" class="btn" id="btnBack" 
        					value='<spring:message code="label.previous"/>'	
        					onClick="stepBack();"/>	
           	   		<input 	type="button" class="btn" id="btnNext" 	
							value='<spring:message code="label.next"/>'		
							onClick="stepNext();"/>	
        			<input 	type="button" class="btn" id="btnOK" 	
        					value='<spring:message code="label.confirm"/>'/>  
        		</div> 	  
       		</div>	  
			<div class="clearer"></div>
		</section>
	
	</div>
</body>

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


