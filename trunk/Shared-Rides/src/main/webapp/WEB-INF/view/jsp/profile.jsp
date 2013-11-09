<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body>
	<div id="theContent" class="theBox">	
		
		<section title="User Data" class="tupper">
				<h2>${name}	${surname}</h2>
		
				<div class="theBoard theLightBox" style="margin-top: 10px"> 		
					<div id="userData">				<!-- Datos 				-->
						<div id="pictureData">		<!-- Foto 	-->
							<img id="thePic" src="resources/profilePic/${picture}"  width="150px" height="160px"/>  
						</div>
						<div id="publicData"> 		<!-- Datos Publicos 	--> 
							<p>${address}		</p>	
							<p>${neighborhood}	</p>		
						</div>  		
						<div id="privateData"> 		<!-- Datos Privados 	-->
							<p>${telephone}				</p>
							<p>${email} 				</p>
						</div>		
					</div>	
				</div>
		</section>
		
	
		<section title="Profile Data" class="tupper" style="margin-top: 20px">
			<div class="theBoard theLightBox"> 		
					<div id="profileData">				<!-- Resto del Perfil	--> 
						<div id="driverData">			<!-- Perfil conductor 	-->		 		
							<h2> 	Conductor	</h2>	 	
							<div> 	<!--Puntuacion			-->		
								<img src="resources/images/star.png" width="50px" style="float:right; margin-right: 2%"/>	
							</div>	 	
							<p> 	<!--Resto de datos		-->		</p>	
							 		
							<div> 	<!--Horario				--> 	
								<table id="theSchedule">

								</table>					
							</div>	
							
							<div style="margin-top: 45px">	<!--Mapa				-->		
								<div id="map2" class="mapStatic"></div>
							</div>
								 
						</div>
						
						<div id="line" class="vrs"></div>
						
						<div id="pedestrianData">		<!-- Perfil peaton 	-->		 		
							<h2> 	Peaton		</h2>	
							<div> 	<!--Puntuacion			-->		
							
								<img src="resources/images/star.png" width="50px" style="float:left; margin-left:2%"/>
							</div>	 	
												
							<c:forEach var="sch" items="${lista}">
								<c:forEach var="day" items="${hola}">
									<c:out value="${day.name}" />
								</c:forEach>
							</c:forEach>
							
							<p> 	<!--Resto de datos		-->		</p>	
							 		
							<div> 	<!--Horario				--> 	
								<table id="theSchedule">
									<tr>
										<th>				</th>
										<th> 	Martes		</th>
										<th> 	Jueves		</th>
									</tr>
									<tr>
										<td>  	Entrada	</td>
										<td>	8:00	</td>
										<td>	8:00	</td>
									</tr>

									<tr>
										<td>	Salida	</td>
										<td>	20:00	</td>
										<td>	20:00	</td>
									</tr>
								</table>					
							</div>	
							
							<div  style="margin-top: 45px"> 	<!--Mapa				-->		
								<div id="map3" class="mapStatic"></div>
							</div>
								 
						</div>
					</div>	
			</div>
		</section>
	</div>
	
	<script src="resources/maps/OpenLayers.js"		type="text/javascript"></script>    
	<script src="resources/maps/OpenStreetMap.js"  	type="text/javascript"></script>
	<script src="resources/maps/osmapStatic.js"		type="text/javascript"></script>	
	<script src="resources/scripts/jsProfile.js"	type="text/javascript"></script>




</body>




















<!--	Datos	-->
<!--   
	<h1>
		Perfil de contactos
	</h1>

	<h1>${id}</h1>
	<h1>${name}</h1>
	<h1>${surname}</h1>
	<h1>${address}</h1>
	<h1>${neighborhood}</h1>
	<h1>${telephone}</h1>
	<h1>${email}</h1>
	<h1>${shift}</h1>
	<h1>${idPedestrian}</h1>
	<h1>${ratingPedestrian}</h1>		
				

	<h1>${lat}</h1>
-->	
