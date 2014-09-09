<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  String id = request.getParameter("user"); %>

<body>
	<div class="sr-content">	
				
		<!-----------------------------------------
		[Data]
		------------------------------------------>			
		<section class="sr-tupper">
			<h2>${name}	${surname}</h2>

			<div class="user-data sr-board border-light">
				<div class="picture-data block-left">
					<img class="picture-profile" src="printImgFile.do?pic=${picture}"/>
				</div>

				<!-----------------------------------------
				[Public Data]
				------------------------------------------>	
				<div class="public-data block-left">
					<p class="text-bold"> <spring:message code="lbl.basicInfo"/>                    </p><br>
					<p><img class="help-icon" src="resources/images/location.png"/>${address}		</p><br>
					<p><img class="help-icon" src="resources/images/house.png"	/>${neighborhood}	</p>
				</div> 
				
				<!-----------------------------------------
				[Private Data]
				------------------------------------------>				 	
				<div class="private-data block-right">
					<p class="text-bold"> <spring:message code="lbl.contactInfo"/>			    </p><br>
					<p><img class="help-icon" src="resources/images/phone.png"/>${telephone}	</p><br>
					<p><img class="help-icon" src="resources/images/email.png"/>${email}		</p>
				</div>		
			</div>	
		</section>
		
		<!----------------------------------------- 
		[Profile]
		------------------------------------------>
		<section class="sr-tupper sr-tupper-bottom">
			<div class="sr-board border-light"> 		
				<div class="profile-data">

					<!-----------------------------------------
					[Driver Profile]
					------------------------------------------>															
					<div class="driver-data block-half block-left">				 		
						<h2><spring:message code="lbl.driver"/></h2>	 	

						<div class="rating"> 
							<a class="ifancybox" href="rate.do">
								<img class="star star-left" src="resources/images/star.png" />
							</a>
							
							<span class="rating-grade">
								${ratingDriver}
							</span>	
						</div>	 	
												 							 
						<div> 	
							<table class="sr-schedule table-driver">
								<tr class="row-day">
									<th></th>
								</tr>
								<tr class="row-in">
									<td><spring:message code="lbl.arrival"/></td>
								</tr>
								<tr class="row-out">
									<td><spring:message code="lbl.departure"/></td>								
								</tr>
							</table>					
						</div>	
						
						<div class="map-container">	
							<p class="text-left text-bold"><spring:message code="lbl.track"/></p><br>
							<div id="map-driver" class="map-static"></div>
						</div>
					</div>
					
					<div id="line" class="vrs"></div>

					<!-----------------------------------------
					[Pedestrian Profile]
					------------------------------------------>							
					<div class="pedestrian-data block-half block-right">				 		
						<h2> <spring:message code="lbl.pedestrian"/> </h2>
											
						<div class="rating"> 		
							<span class="rating-grade">
								${ratingPedestrian}
							</span>
							<a class="ifancybox" href="rate.do">
								<img class="star star-right" src="resources/images/star.png"/>
							</a>
						</div>	 	
						 
						<div> 		
							<table class="sr-schedule table-ped">
								<tr class="row-day">
									<th></th>
								</tr>
								<tr class="row-in">
									<td><spring:message code="lbl.arrival"/></td>
								</tr>
								<tr class="row-out">
									<td><spring:message code="lbl.departure"/></td>								
								</tr>
							</table>	
						</div>	

						<div class="map-container"> 	
							<p class="text-left text-bold"><spring:message code="lbl.location"/></p><br>
							<div id="map-pedestrian" class="map-static"></div>
						</div>
					</div>
				</div>	
			</div>
		</section>
	</div>
	
	<!-----------------------------------------
	[Hidden]
	------------------------------------------>	
	<section>

		<!-----------------------------------------
		[Variables]
		------------------------------------------>	
		<input type="hidden" id="val-driver" 		value="${driver}"		/>
		<input type="hidden" id="val-pedestrian"	value="${pedestrian}"	/>
		<input type="hidden" id="val-id" 			value="${id}"			/>
		<input type="hidden" id="val-mine" 			value="${mine}"			/>
		
	</section>
</body>

<!-----------------------------------------
[Links]
------------------------------------------>	
<link 	type="text/css" rel="stylesheet" href="resources/fancybox2/source/jquery.fancybox.css?v=2.1.5"/>

<!-----------------------------------------
[Scripts]
------------------------------------------>	
<script type="text/javascript" src="resources/maps/OpenLayers.js"	></script>    
<script type="text/javascript" src="resources/maps/OpenStreetMap.js"></script>
<script type="text/javascript" src="resources/maps/osmapStatic.js"	></script>	
<script type="text/javascript" src="resources/scripts/Profile.js"	></script>
<script type="text/javascript" src="resources/fancybox2/source/jquery.fancybox.pack.js?v=2.1.5" ></script>

<script type="text/javascript">
			
	if("${visible}" == "true") {
		$(".private-data").show();
	}
	else {
		$(".star").unwrap();
	}
		
	initMapPedestrian();
	initMapDriver(); 	
	
	<c:forEach var="day" items="${schPed}">
		var	detSchPed			  = new DetailSchedulePedestrian();	
		detSchPed.day             = "${day.dayPed}";
		detSchPed.allowIn         = "${day.allowIn}";
		detSchPed.allowOut        = "${day.allowOut}";		
		detSchPed.hourIn          = "${day.hourInPed}";
		detSchPed.hourOut         = "${day.hourOutPed}";
		detSchPed.hasDriverIn     = "${day.hasDriverIn}";
		detSchPed.hasDriverOut    = "${day.hasDriverOut}";
		detSchPed.latIn           = "${day.stopLatIn}";
		detSchPed.latOut          = "${day.stopLatOut}";
		detSchPed.lonIn           = "${day.stopLonIn}";
		detSchPed.lonOut          = "${day.stopLonOut}";		
		_schPed.push(detSchPed);
	</c:forEach>
	
	<c:forEach var="day" items="${schDriver}">
		var detSchDriver          = new DetailScheduleDriver();	
		detSchDriver.day          = "${day.dayDriver}";
		detSchDriver.allowIn      = "${day.allowIn}";
		detSchDriver.allowOut     = "${day.allowOut}";	
		detSchDriver.sentIn		  = "${day.sentIn}";	
		detSchDriver.sentOut	  = "${day.sentOut}";			
		detSchDriver.hourIn       = "${day.hourInDriver}";
		detSchDriver.hourOut      = "${day.hourOutDriver}";
		detSchDriver.freeSeatsIn  = "${day.freeSeatsIn}";
		detSchDriver.freeSeatsOut = "${day.freeSeatsOut}";
		detSchDriver.pathIn       = "${day.trackIn}"; 
		detSchDriver.pathOut      = "${day.trackOut}";
		_schDriver.push(detSchDriver);
	</c:forEach>

    var createLabels = (function (){
        var labels = {
            //Labels' name
            lblAlertConfirm : '<spring:message code="lbl.alert-confirm" />',
            lblTipCheckMap  : '<spring:message code="lbl.tip-checkmap"  />',
            lblMonday       : '<spring:message code="lbl.monday"        />',
            lblTuesday      : '<spring:message code="lbl.tuesday"       />',
            lblWednesday    : '<spring:message code="lbl.wednesday"     />',
            lblThursday     : '<spring:message code="lbl.thursday"      />',
            lblFriday       : '<spring:message code="lbl.friday"        />'
        };
        return function () { return labels; };
    })();
</script>

<script type="text/javascript" src="resources/scripts/Utils.js"></script>

