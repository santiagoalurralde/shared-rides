<%@	taglib uri="http://www.springframework.org/tags" 	prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 		prefix="c" %>

<body onLoad="stepsUpdate(1);">

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
			              <strong>Identificación Personal</strong>:          
			              <span class="red">*</span>
			            </span> 
           				
           				<input type="text" id="personalId" class="blockRight theInputs" required/>
          				</span> 
          				
          				<div class="split"></div>
          				
          				<span>
           				<span class="blockLeft">
			              <strong>Nombre</strong>:          
			              <span class="red">*</span>
			            </span> 
           				
           				<input type="text" id="name" class="blockRight theInputs" required/>
          				</span> 
          				
          				<div class="split"></div>
          				
          				<span>
           				<span class="blockLeft">
			              <strong>Apellido</strong>:          
			              <span class="red">*</span>
			            </span> 
           				
           				<input type="text" id="surname" class="blockRight theInputs" required/>
          				</span> 
          				
          				<div class="split"></div>
          				
          				<span>
  				            <span class="blockLeft">
			              <strong>Contraseña</strong>:          
			              <span class="red">*</span>
			            </span> 
           				
           				<input type="password" id="password-first" class="blockRight theInputs" required/>
          				</span>

          				<div class="split"></div>
          				
          				<span>
           				<span class="blockLeft">
			              <strong>Repita su contraseña</strong>:          
			              <span class="red">*</span>
			            </span> 
           				
           				<input type="password" id="password-check" class="blockRight theInputs" required/>
          				</span> 
          				
          				<div class="split"></div>

          				<span>
           				<span class="blockLeft">
			              <strong>Teléfono Celular</strong>:          
			              <span class="red">*</span>
			            </span> 
           				
           				<input type="text" id="cellphone" class="blockRight theInputs" required/>
          				</span> 
       		    </div>
       		    
       		    <div id="secondStep" class="steps" style="display: none">
          				
          				<span>
           				<span class="blockLeft">
			              <strong>Dirección</strong>:          
			              <span class="red">*</span>
			            </span> 
           				
           				<input type="text" id="number" class="blockRight theInputs" style="width: 4em" required/>
           				<input type="text" id="street" class="blockRight theInputs" style="width: 8.8em" required/>
           				
          				</span> 
          				
          				<div class="split"></div>
          				
          				<span>
           				<span class="blockLeft">
			              <strong>Barrio</strong>:          
			              <span class="red">*</span>
			            </span> 
           				
           				<select id="neighborhood" class="blockRight theInputs" required>
           					<option>Arguello</option>
           				</select>
          				</span> 

          				<div class="split"></div>
          				
          				<span>
           				<span class="blockLeft">
			              <strong>Turno</strong>:          
			              <span class="red">*</span>
			            </span> 
           				
           				<select id="shift" class="blockRight theInputs">
           					<option value="">Mañana</option>	            				
           					<option value="">Tarde</option>
           				</select>            				
           			</span> 
          				
          				<div class="split"></div>
          				
          				<span>
  				            <span class="blockLeft">
			              <strong>Tipo de Usuario</strong>:          
			              <span class="red">*</span>
			            </span> 
           				
           				<select id="userType" name="userType" onChange="userTypeChanged(this);" class="blockRight theInputs">
           				    <option value="0" selected=""> </option>
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
					              <strong>Marca del Vehículo</strong>:          
					              <span class="red">*</span>
					            </span> 
	            				
	            				<select id="brand" class="blockRight theInputs" required>
	            					<option>Renault</option>	            				
	            					<option>Fiat</option>
	            					<option>Ford</option>	            					
	            				</select>         	            				
	            			</span> 
	            			
	            			<div class="split"></div>
	            			
	           				<span>
	            				<span class="blockLeft">
					              <strong>Modelo del Vehículo</strong>:          
					              <span class="red">*</span>
					            </span> 
	            				
	            				<input type="text" id="model" class="blockRight theInputs" required>
	      						</span> 
	      						
	            			<div class="split"></div>
	            			
	           				<span>
	            				<span class="blockLeft">
					              <strong>Patente del Vehículo</strong>:          
					              <span class="red">*</span>
					            </span> 
	            				
	            				<input type="text" id="plateNumbers" class="blockRight " required>	            				
	            				<input type="text" id="plateLetters" class="blockRight " required>
	      					</span>         	
	      						
	            			<div class="split"></div>
	      						
	           				<span>
	            				<span class="blockLeft">
					              <strong>Cantidad de Asientos Libres</strong>:          
					              <span class="red">*</span>
					            </span> 
	            				
	            				<select id="brand" class="blockRight theInputs" required>
	            					<option>1</option>	            				
	            					<option>2</option>
	            					<option>3</option>	 
	            					<option>4</option>	            					
	            					<option>5</option>	            					
	            					<option>6</option>	            					
	            					<option>7</option>	            					  					
	            				</select>          						
	            			</span>           													            			
          				</div>
          				
       		    </div>

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
						<tr>
							<td>
								Tipo de Usuario
							</td>
							<td>
							</td>
							<td>
							</td>
							<td>
							</td>
							<td>
							</td>
							<td>
							</td>				
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

        		<!----	Buttons	---->
        		<div id="buttonsSteps">
        			<input type="button" class="btn" id="btnBack" 	value="Anterior" 	onClick="stepsUpdate(-1);"	/>	
           	   		<input type="button" class="btn" id="btnNext" 	value="Siguiente"	onClick="stepsUpdate(1);"	/>		
        			<input type="button" class="btn" id="btnOK" 	value="Confirmar" 	style="display: none" 		/>  
        		</div> 	  
           	
       		</form>	  
			<div class="clearer"></div>
		</section>
	
	</div>
</body>

<script src="resources/scripts/Register.js" 	type="text/javascript"></script>

