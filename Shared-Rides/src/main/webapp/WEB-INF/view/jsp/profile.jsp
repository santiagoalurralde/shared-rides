<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%  String id = request.getParameter("user"); %>

<body>
	<div id="theContent">	
		
		<section title="" class="tupper">
			<h2>${name}	${surname}</h2>
							
			<div id="userData" class="theBoard lightBorder">	<!-- Datos 	-->
				<div id="pictureData">							<!-- Foto 	-->
					<img id="thePic" src="resources/profilePic/${picture}" />  
				</div>
				
				<div id="publicData"> 		<!-- Datos Publicos	--> 
					<p><b><spring:message code="label.basicInfo"/></b></p><br>
					<p><img class="helpIcon" src="resources/images/location.png" />${address}		</p><br>
					<p><img class="helpIcon" src="resources/images/house.png" 	 />${neighborhood}	</p>		
				</div> 
				 		
				<div id="privateData"> 		<!-- Datos Privados	-->
					<p><b><spring:message code="label.contactInfo"/></b></p><br>
					<p><img class="helpIcon" src="resources/images/phone.png"	/>${telephone}		</p><br>
					<p><img class="helpIcon" src="resources/images/email.png" 	/>${email} 			</p>
				</div>		
			</div>	
		</section>
		
		<section title="" class="tupper" style="margin-top: 50px">
			<div class="theBoard lightBorder"> 		
				<div id="profileData">											<!-- Resto del Perfil --> 
					<div id="driverData" class="blockLeft blockHalf">			<!-- Perfil conductor -->		 		
						<h2><spring:message code="label.driver"/></h2>	 	
						
						<div class="theRating"> 	<!-- Puntuacion -->	
							<a class="ifancybox" href="rate.do">
								<img class="star" src="resources/images/star.png" />
							</a>
							
							<span style="position: relative; top: 12px">
								${ratingDriver}
							</span>	
						</div>	 	
														 							 		
						<div> 	<!-- Horario --> 	
							<table id="tableDriver" class="theSchedule">
							</table>					
						</div>	
						
						<div class="mapContainer" style="margin-top: 45px; margin-left: 17px">	<!-- Mapa	-->	
							<p style="text-align: left"><b>
								<spring:message code="label.track"/>
							</b></p><br>
							<div id="map3" class="mapStatic"></div>
						</div>
					</div>
					
					<div id="line" class="vrs"></div>
					
					<div id="pedestrianData" class="blockRight blockHalf">		<!-- Perfil peaton 	-->		 		
						<h2>
							<spring:message code="label.pedestrian"/>
						</h2>
							
						<div class="theRating"> 	<!-- Puntuacion -->	
							<span style="position: relative; top: 12px">
								${ratingPedestrian}
							</span>
							<a class="ifancybox" href="rate.do">
								<img class="star" src="resources/images/star.png" width="50px" style="float:right; margin-left:2%"/>
							</a>
						</div>	 	
						 		
						<div> 	<!-- Horario --> 	
							<table id="tablePed" class="theSchedule">
							</table>	
						</div>	
						
						<div class="mapContainer" style="margin-top: 45px; margin-left: 40px"> 	<!-- Mapa -->		
							<p style="text-align: left"><b>
								<spring:message code="label.location"/>
							</b></p><br>
							<div id="map2" class="mapStatic"></div>
						</div>
							 
					</div>
				</div>	
			</div>
		</section>
	</div>
	
	<!-- HIDDEN -->
	<section>
		<!-- LABELS -->
		<c:set var="lblArrival"> 	<spring:message code="label.arrival" 	/></c:set>	
		<c:set var="lblDeparture"> 	<spring:message code="label.departure" 	/></c:set>	
		<c:set var="lblMonday"> 	<spring:message code="label.monday" 	/></c:set>	
		<c:set var="lblTuesday"> 	<spring:message code="label.tuesday" 	/></c:set>	
		<c:set var="lblWednesday"> 	<spring:message code="label.wednesday" 	/></c:set>	
		<c:set var="lblThursday"> 	<spring:message code="label.thursday" 	/></c:set>	
		<c:set var="lblFriday"> 	<spring:message code="label.friday"		/></c:set>	
		
		<input type="hidden" id="lblArrival" 	value="${lblArrival}" 	/>
		<input type="hidden" id="lblDeparture" 	value="${lblDeparture}" />
		<input type="hidden" id="lblMonday" 	value="${lblMonday}" 	/>
		<input type="hidden" id="lblTuesday" 	value="${lblTuesday}" 	/>
		<input type="hidden" id="lblWednesday" 	value="${lblWednesday}" />
		<input type="hidden" id="lblThursday" 	value="${lblThursday}" 	/>
		<input type="hidden" id="lblFriday" 	value="${lblFriday}"	/>
		
		<!-- VARIABLES -->
		<input type="hidden" id="valDriver" 	value="${driver}"		/>
		<input type="hidden" id="valPedestrian" value="${pedestrian}"	/>
		<input type="hidden" id="valId" 		value="${id}"			/>
		<input type="hidden" id="valMine" 		value="${mine}"			/>
		
	</section>
</body>

<!-- LINKS -->

<link 	type="text/css" rel="stylesheet" href="resources/fancybox2/source/jquery.fancybox.css?v=2.1.5"	/>

<!-- SCRIPTS -->

<script type="text/javascript" src="resources/maps/OpenLayers.js"	></script>    
<script type="text/javascript" src="resources/maps/OpenStreetMap.js"></script>
<script type="text/javascript" src="resources/maps/osmapStatic.js"	></script>	
<script type="text/javascript" src="resources/scripts/Utils.js"		></script>
<script type="text/javascript" src="resources/scripts/Profile.js"	></script>
<script type="text/javascript" src="resources/fancybox2/source/jquery.fancybox.pack.js?v=2.1.5" ></script>

<script type="text/javascript">
			
	if("${visible}" == 'false')
		$( "#privateData" ).hide();
	
	initMapPedestrian();
	initMapDriver(); 	

	<c:forEach var="day" items="${schPed}">
		var detSchPed 			= new DetailSchedulePedestrian();
		detSchPed.day 			= "${day.dayPed}";
		detSchPed.allowIn 		= "${day.allowIn}";
		detSchPed.allowOut 		= "${day.allowOut}";
		detSchPed.hourIn 		= "${day.hourInPed}";
		detSchPed.hourOut 		= "${day.hourOutPed}";
		detSchPed.hasDriverIn 	= "${day.hasDriverIn}";
		detSchPed.hasDriverOut	= "${day.hasDriverOut}";
		detSchPed.latIn 		= "${day.stopLatIn}";
		detSchPed.latOut 		= "${day.stopLatOut}";
		detSchPed.lonIn 		= "${day.stopLonIn}";
		detSchPed.lonOut 		= "${day.stopLonOut}";		
		_schPed.push(detSchPed);
	</c:forEach>
	
	<c:forEach var="day" items="${schDriver}">
		var detSchDriver 			= new DetailScheduleDriver();
		detSchDriver.day 			= "${day.dayDriver}";
		detSchDriver.allowIn 		= "${day.allowIn}";
		detSchDriver.allowOut 		= "${day.allowOut}";	
		detSchDriver.hourIn 		= "${day.hourInDriver}";
		detSchDriver.hourOut 		= "${day.hourOutDriver}";
		detSchDriver.freeSeatsIn 	= "${day.freeSeatsIn}";
		detSchDriver.freeSeatsOut 	= "${day.freeSeatsOut}";
		detSchDriver.pathIn 		= "${day.trackIn}";
		detSchDriver.pathOut 		= "${day.trackOut}";
		
		_schDriver.push(detSchDriver);
	</c:forEach>
</script>
