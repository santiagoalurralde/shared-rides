<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body>
	<div id="theContent" class="theBox">	
	
		<section title="User Data" class="tupper">
				<h2>${name}	${surname}</h2>
		
				<div class="theBoard theLightBox" style="margin-top: 10px"> 		
					<div id="userData">				<!-- Datos 				-->
						<div id="pictureData">		<!-- Foto 	-->
							<img id="thePic" src="resources/profilePic/${picture}"  width="150px"/>  
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
									<tr>
										<th>				</th>
										<th> 	Lunes		</th>
										<th> 	Miercoles	</th>
										<th> 	Viernes		</th>
									</tr>
									<tr>
										<td>  	Entrada	</td>
										<td>	8:00	</td>
										<td>	8:00	</td>
										<td>	8:00	</td>
									</tr>

									<tr>
										<td>	Salida	</td>
										<td>	20:00	</td>
										<td>	20:00	</td>
										<td>	20:00	</td>
									</tr>
								</table>					
							</div>	

							<div> 	<!--Mapa				-->		</div>
								 
						</div>
						
						<div id="line" class="vrs"></div>
						
						<div id="pedestrianData">		<!-- Perfil peaton 	-->		 		
							<h2> 	Peaton		</h2>	
							<div> 	<!--Puntuacion			-->		
							
								<img src="resources/images/star.png" width="50px" style="float:left; margin-left:2%"/>
							</div>	 	
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
							
							<div> 	<!--Mapa				-->		</div>	 
						</div>
					</div>	
			</div>
		</section>


	</div>
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
				
	<c:forEach var="sch" items="${schPed}">
		<c:forEach var="day" items="${sch}">
			<c:out value="${day}"/>
		</c:forEach>
	</c:forEach>

	<h1>${lat}</h1>
-->	
