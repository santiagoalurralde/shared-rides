<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<% 
	String id = request.getParameter("user");
%>


<body>
	<div id="theContent" class="theBox">	
		
		<section title="User Data" class="tupper">
			<h2>${name}	${surname}</h2>
	
			<div class="theBoard theLightBox" style="margin-top: 10px"> 		
				<div id="userData">				<!-- Datos 	-->
					<div id="pictureData">		<!-- Foto 	-->
						<img id="thePic" src="resources/profilePic/${picture}"  width="140px" height="150px"/>  
					</div>
					
					<div id="publicData"> 		<!-- Datos Publicos	--> 
						<p><b>
							<spring:message code="label.basicInfo"/>
						</b></p><br>
						<p><img class="helpIcon" src="resources/images/location.png" 	/>${address}		</p><br>
						<p><img class="helpIcon" src="resources/images/house.png" 		/>${neighborhood}	</p>		
					</div> 
					 		
					<div id="privateData"> 		<!-- Datos Privados	-->
						<p><b>
							<spring:message code="label.contactInfo"/>
						</b></p><br>
						<p><img class="helpIcon" src="resources/images/phone.png"		/>${telephone}		</p><br>
						<p><img class="helpIcon" src="resources/images/email.png" 		/>${email} 			</p>
					</div>		
				</div>	
			</div>
		</section>
		
		<section title="Profile Data" class="tupper" style="margin-top: 50px">
			<div class="theBoard theLightBox"> 		
				<div id="profileData">											<!-- Resto del Perfil --> 
					<div id="driverData" class="blockLeft blockHalf">			<!-- Perfil conductor -->		 		
						<h2>
							<spring:message code="label.driver"/>
						</h2>	 	
						
						<div class="theRating"> 	<!-- Puntuacion -->	
							<a class="ifancybox" href="resources/rate.html">
								<img class="star" src="resources/images/star.png" width="50px" style="float:left; margin-right:2%"/>
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
							<a class="ifancybox" href="resources/rate.html">
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
	
	<!-- LABELS -->
	
	<c:set var="labelArrival"><spring:message code="label.arrival"/></c:set>	
	<input id="lblArrival" type="hidden" value="${labelArrival}"/>
	<c:set var="labelDeparture"><spring:message code="label.departure"/></c:set>	
	<input id="lblDeparture" type="hidden" value="${labelDeparture}"/>
	<c:set var="labelMonday"><spring:message code="label.monday"/></c:set>	
	<input id="lblMonday" type="hidden" value="${labelMonday}"/>
	<c:set var="labelTuesday"><spring:message code="label.tuesday"/></c:set>	
	<input id="lblTuesday" type="hidden" value="${labelTuesday}"/>
	<c:set var="labelWednesday"><spring:message code="label.wednesday"/></c:set>	
	<input id="lblWednesday" type="hidden" value="${labelWednesday}"/>
	<c:set var="labelThursday"><spring:message code="label.thursday"/></c:set>	
	<input id="lblThursday" type="hidden" value="${labelThursday}"/>
	<c:set var="labelFriday"><spring:message code="label.friday"/></c:set>	
	<input id="lblFriday" type="hidden" value="${labelFriday}"/>
	
	<input id="valDriver" 		type="hidden" value="${driver}"/>
	<input id="valPedestrian" 	type="hidden" value="${pedestrian}"/>
	<input id="valId" 			type="hidden" value="${id}"/>
	
</body>

<!-- SCRIPTS -->

<script src="resources/maps/OpenLayers.js"		type="text/javascript"></script>    
<script src="resources/maps/OpenStreetMap.js"  	type="text/javascript"></script>
<script src="resources/maps/osmapStatic.js"		type="text/javascript"></script>	
<script src="resources/scripts/Utils.js"		type="text/javascript"></script>
<script src="resources/scripts/Profile.js"		type="text/javascript"></script>

<script type="text/javascript">

	var schPed = [];							
	var schDriver = [];
	
	if("${visible}" === 'false')
		$( '#privateData' ).hide( 0 );
	
	initMap("${lonPed}", "${latPed}");	
	initMap1("santiago.gpx");
	
	<c:forEach var="day" items="${schPed}">
		schPed.push(["${day.dayPed}","${day.hourInPed}","${day.hourOutPed}","${day.haveDriverIn}", "${day.haveDriverOut}"]);
	</c:forEach>
	
	<c:forEach var="day" items="${schDriver}">
		schDriver.push(["${day.dayDriver}","${day.hourInDriver}","${day.hourOutDriver}","${day.freeSeatsIn}", "${day.freeSeatsOut}"]);
	</c:forEach>

	fillTable(schPed, "Pedestrian");
	fillTable(schDriver, "Driver");	
</script>

<!-- FANCY BOX -->

<link 	type="text/css"			rel="stylesheet" href="resources/fancybox2/source/jquery.fancybox.css?v=2.1.5"/>
<script type="text/javascript" 	src="resources/fancybox2/source/jquery.fancybox.pack.js?v=2.1.5"></script>

<script type="text/javascript">
	$(document).ready(function(){
	    $(".ifancybox").fancybox({
	            'width'                 :       500,
	            'height'                :       330,
	            'autoSize'              :       false,
	            'fitToView'             :       false,
	            'type'                  :       'iframe'
	    });
	});
</script>






<!--	Datos

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
