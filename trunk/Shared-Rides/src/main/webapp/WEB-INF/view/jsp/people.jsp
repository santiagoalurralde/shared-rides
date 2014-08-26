<%@	taglib uri="http://www.springframework.org/tags" 	prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 		prefix="c" %>

<body>
	<div class="sr-content">	

		<!-----------------------------------------
		[List Data]
		------------------------------------------>
		<section class="sr-tupper">
			<div id="listData" class="sr-board border-light"> 	
									
				<div id="pending" class="block-half block-right"> 		<!-- Lista Solicitudes 	--> 
					<h2> <spring:message code="label.pending"/> </h2>
					<table id="tablePending"	class="tableUsers">
					</table>	
				</div> 
				
				<!-- <div class="vrs"></div> -->
					
				<div id="associated" class="block-half block-left">		<!-- Lista Asociados 	-->
					<h2> <spring:message code="label.associated"/> </h2>
					<table id="tableAssociated"	class="tableUsers">
					</table>
				</div>		
			
				<div class="clearer"></div>
			</div>
		</section>

		<!-----------------------------------------
		[Schedule Data]
		------------------------------------------>		
		<section class="sr-tupper" style="margin-top: 20px">

			<div id="scheduleData" class="sr-board border-light">

				<div id="requested" class="block-half block-left">		
					<h2> <spring:message code="label.requested"/> </h2>
				
					<div> 			<!-- Horario	-->
						<table id="tableRequested" class="sr-schedule">
						</table>					
					</div>	
				</div>
				
				<div id="offered" class="block-half block-right">
					<h2> <spring:message code="label.offered"/> </h2>	
				
					<div> 			<!-- Horario	-->
						<table id="tableOffered" class="sr-schedule">
						</table>					
					</div>
				</div>	
				
				<div class="clearer"></div>
			</div>	

		</section>
	</div>
	
	<!-----------------------------------------
	[Labels]
	------------------------------------------>		
	<c:set var="lblRequest">		<spring:message code="label.request"	/></c:set>	
	<c:set var="lblAssociation">	<spring:message code="label.association"/></c:set>
	<c:set var="lblArrival"> 		<spring:message code="label.arrival" 	/></c:set>	
	<c:set var="lblDeparture"> 		<spring:message code="label.departure" 	/></c:set>	
	<c:set var="lblMonday"> 		<spring:message code="label.monday" 	/></c:set>	
	<c:set var="lblTuesday"> 		<spring:message code="label.tuesday" 	/></c:set>	
	<c:set var="lblWednesday"> 		<spring:message code="label.wednesday" 	/></c:set>	
	<c:set var="lblThursday"> 		<spring:message code="label.thursday" 	/></c:set>	
	<c:set var="lblFriday"> 		<spring:message code="label.friday"		/></c:set>	

	<input type="hidden" id="lbl-arrival" 	value="${lblRequest}" 		/>
	<input type="hidden" id="lbl-departure"	value="${lblAssociation}" 	/>
	<input type="hidden" id="lbl-arrival" 	value="${lblArrival}" 		/>
	<input type="hidden" id="lbl-departure"	value="${lblDeparture}"	 	/>
	<input type="hidden" id="lbl-monday" 	value="${lblMonday}" 		/>
	<input type="hidden" id="lbl-tuesday" 	value="${lblTuesday}" 		/>
	<input type="hidden" id="lbl-wednesday"	value="${lblWednesday}" 	/>
	<input type="hidden" id="lbl-thursday" 	value="${lblThursday}" 		/>
	<input type="hidden" id="lbl-friday" 	value="${lblFriday}"		/>

	<!-----------------------------------------
	[Scripts]
	------------------------------------------>		
	<script type="text/javascript" src="resources/scripts/Utils.js">	</script>
	<script type="text/javascript" src="resources/scripts/People.js">	</script>

</body>
