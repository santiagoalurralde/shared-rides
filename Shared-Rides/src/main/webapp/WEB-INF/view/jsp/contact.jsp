<%@	taglib uri="http://www.springframework.org/tags" 	prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 		prefix="c" %>

<body onLoad="stepNext();">

	<!----	Content	---->
	<div id="theContent">	
		<section title="Contact" class="tupper">
			<div id="navSteps" class="lightBorder"> 
        		<ul>
        			<li id="stepSignUp1"><b>1. </b>Datos Personales</li>
        			<li id="stepSignUp2"><b>2. </b>Información de Perfil</li>
        			<li id="stepSignUp3"><b>3. </b>Horarios y Ubicaciones</li>
        		</ul>
        	</div>

			<form name="" method="POST" class="theBoard lightBorder">
       		    	        		    	
       		    <div id="firstStep" class="steps">
         			<span>
           				<span class="blockLeft">
			               	Organizacion :          
			            </span> 
         				<select id="organization" class="blockRight theInputs">
           					<option value="ucc" selected>Universidad Católica de Córdoba</option>	            					            					
           				</select>       
         			</span>	
         			
         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			               	Identificación Personal :          
			            </span> 
           				
           				<input type="text" id="personalId" class="blockRight theInputs" onChange="checkIt(this)"/>
         			</span> 
         				
         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			              	Nombre :          
			        	</span> 
           				
           				<input type="text" id="name" class="blockRight theInputs" onChange="checkIt(this)" onKeyPress="checkAlphabetic(event);"/>
         			</span> 
         				
         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			               	Apellido :          
			            </span> 
           				
           				<input type="text" id="surname" class="blockRight theInputs" onChange="checkIt(this)" onKeyPress="checkAlphabetic(event);"/>
          			</span> 
         				
         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			               	E-mail :          
			            </span> 
           				
           				<input type="text" id="email" class="blockRight theInputs" onChange="checkIt(this)"/>
          			</span> 
         				
         			<div class="split"></div>
         				          				
       				<span>
			            <span class="blockLeft">
               				Contraseña :          
            			</span> 
        				
        				<input type="password" id="password-first" class="blockRight theInputs" onChange="checkIt(this); checkPassword();"/>
       				</span>

         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			               Repita su contraseña :          
			            </span> 
           				
           				<input type="password" id="password-check" class="blockRight theInputs" onChange="checkIt(this); checkPassword();"/>
         			</span> 
         				
         			<div class="split"></div>

         			<span>
           				<span class="blockLeft">
			               Teléfono Celular :          
			        	</span> 
           				
           				<input type="text" id="cellphone" class="blockRight theInputs" onChange="checkIt(this);" onKeyUp="checkNumeric(this);"/>
         			</span> 
         			
         			<div class="split"></div>        								
       		    </div>
       		    <!-------------------------------------------------- END of FIRST STEP -------------------------------------------------->
       		    
       		    <div id="secondStep" class="steps" style="display: none">
          				
          			<span>
	           			<span class="blockLeft">
				         	Dirección :          				              	 
				        </span> 
	           		
	           			<input type="number" 	id="number" class="blockRight theInputs" style="width: 4em" 	onChange="checkIt(this);" onKeyUp="checkNumeric(this);" min="0" max="99999"/>
	           			<input type="text" 		id="street" class="blockRight theInputs" style="width: 8.8em" 	onChange="checkIt(this);" />
          			</span> 
          				
          			<div class="split"></div>
          				
          			<span>
	           			<span class="blockLeft">
				        	Barrio :          				              	 
				        </span> 
	           				
	           			<select id="neighborhood" class="blockRight theInputs" onChange="checkIt(this);">
	           				<option value="0"></option>	           			
	           				<option>Arguello</option>
	           			</select>
          			</span> 

          			<div class="split"></div>
          				
          			<span>
           				<span class="blockLeft">
			               	Turno :          				              	 
			            </span> 
           				
           				<select id="shift" class="blockRight theInputs" onChange="checkIt(this);">
           					<option value="0" selected></option>	            					           				
           					<option value="morning">Mañana</option>	            				
           					<option value="afternoon">Tarde</option>
           				</select>            				
           			</span> 
          				
          			<div class="split"></div>
          				
         			<span>
 				        <span class="blockLeft">
		            		Tipo de Usuario :          			              	 
		           	 	</span> 
          				
           				<select id="userType" name="userType" onChange="checkIt(this); userTypeChanged(this);" class="blockRight theInputs">
           				    <option value="0" selected> </option>
           					<option value="pedestrian">Peatón</option>	            				
           					<option value="driver">Conductor</option>
           					<option value="driver-pedestrian">Peatón / Conductor</option>	            					
           				</select>               				
           			</span>

          			<div class="split"></div>
          				          				
          			<div id="drives" style="display:none">
       					<hr class="hrs">
       					
       					<h3>Información de Vehículo</h3>
       					
       					<div class="split"></div>
         					
           				<span>
            				<span class="blockLeft">
				               Marca del Vehículo :          					               
				            </span> 
            				
            				<select id="brand" class="blockRight theInputs" onChange="checkIt(this);">
	            				<option value="0" selected></option>	            					            				            				
            					<option>Renault</option>	            				
            					<option>Fiat</option>
            					<option>Ford</option>	            					
            				</select>         	            				
            			</span> 
	            			
            			<div class="split"></div>
	            			
           				<span>
            				<span class="blockLeft">
				               Modelo del Vehículo :          					               
				            </span> 
            				
            				<input type="text" id="model" class="blockRight theInputs" onChange="checkIt(this);">
   						</span> 
	      						
	            			<div class="split"></div>
	            			
	           				<span>
	            				<span class="blockLeft">
					               Patente del Vehículo :          					             
					            </span> 
	            				
	            				<input type="number"	id="plateNumbers" class="blockRight theInputs" style="width: 4em" onChange="checkIt(this);" min="000" max="999" />	            				
	            				<input type="text" 		id="plateLetters" class="blockRight theInputs" style="width: 4em" onChange="checkIt(this);" onKeyPress="checkAlphabetic(event);" maxlength="3" />
	      					</span>         	
	      						
	            			<div class="split"></div>
	      						
	           				<span>
	            				<span class="blockLeft">
					               Cantidad de Asientos Libres :          					           
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
	            			
	            			<div class="split"></div>        								         													            			
          				</div>
       		    </div>
       		    <!-------------------------------------------------- END of SECOND STEP -------------------------------------------------->


       		    <div id="thirdStep" class="steps" style="display: none">
					<table id="tableSignUp" class="theSchedule">
						<tr>
							<th>
								<!-- Empty -->
							</th>	
							<th>
								Lunes
							</th>
							<th>
								Martes
							</th>
							<th>
								Miércoles
							</th>
							<th>
								Jueves
							</th>
							<th>
								Viernes
							</th>				
						</tr>
						<tr id="userTypeRow">
			
						</tr>
						<tr id="inRow">
							<td>
								Entrada
							</td>
						</tr>
						<tr id="outRow">
							<td>
								Salida
							</td>									
						</tr>
					</table>
				</div>

				<div id="alert" class="alerts"></div>          				
				
        		<!----	Buttons	---->
        		<div id="buttonsSteps">
        			<input type="button" class="btn" id="btnBack" 	value="Anterior" 	onClick="stepBack();"	/>	
           	   		<input type="button" class="btn" id="btnNext" 	value="Siguiente"	onClick="stepNext();"	/>		
        			<input type="button" class="btn" id="btnOK" 	value="Confirmar" 	style="display: none" 	/>  
        		</div> 	  
           	
       		</form>	  
			<div class="clearer"></div>
		</section>
	
	</div>
</body>

<script src="resources/scripts/Register.js" 	type="text/javascript"></script>

