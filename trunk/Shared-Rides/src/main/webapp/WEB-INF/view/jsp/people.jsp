<%@	taglib uri="http://www.springframework.org/tags" 	prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 		prefix="c" %>

<body>
	<div class="sr-content">	

		<!-----------------------------------------
		[List Data]
		------------------------------------------>
		<section class="sr-tupper">

			<div class="list-data sr-board border-light "> 	

				<div class="pending block-half block-right"> 		
					<h2> <spring:message code="lbl.pending"/></h2>
					<table class="table-pending sr-table-users"></table>	
				</div> 
				
				<!-- <div class="vrs"></div> -->
					
				<div class="associated block-half block-left">		
					<h2> <spring:message code="lbl.associated"/></h2>
					<table class="table-associated sr-table-users"></table>
				</div>		
			
				<div class="clearer"></div>
				
			</div>
			
		</section>

		<!-----------------------------------------
		[Schedule Data]
		------------------------------------------>		
		<section class="sr-tupper" style="margin-top: 20px">

			<div class="sr-board border-light schedule-data">

				<div class="requested block-half block-left">		
					<h2> <spring:message code="lbl.requested"/> </h2>
					<table class="table-requested sr-schedule"></table>					
				</div>
				
				<div class="offered block-half block-right">
					<h2> <spring:message code="lbl.offered"/> </h2>	
					<table class="table-offered sr-schedule"></table>					
				</div>	
				
				<div class="clearer"></div>
			</div>	

		</section>
	</div>
	
	<!-----------------------------------------
	[Labels]
	------------------------------------------>		
	<c:set var="lblRequest">		<spring:message code="lbl.request"			/></c:set>	
	<c:set var="lblAssociation">	<spring:message code="lbl.association"		/></c:set>
	<c:set var="lblArrival"> 		<spring:message code="lbl.arrival" 			/></c:set>	
	<c:set var="lblDeparture"> 		<spring:message code="lbl.departure" 		/></c:set>	
	<c:set var="lblMonday"> 		<spring:message code="lbl.monday" 			/></c:set>	
	<c:set var="lblTuesday"> 		<spring:message code="lbl.tuesday" 			/></c:set>	
	<c:set var="lblWednesday"> 		<spring:message code="lbl.wednesday" 		/></c:set>	
	<c:set var="lblThursday"> 		<spring:message code="lbl.thursday" 		/></c:set>	
	<c:set var="lblFriday"> 		<spring:message code="lbl.friday"			/></c:set>	
	<c:set var="lblNoRequests"> 	<spring:message code="lbl.alert-norequests"	/></c:set>	
	<c:set var="lblNoAssocs"> 		<spring:message code="lbl.alert-noassocs"	/></c:set>	
	<c:set var="lblUser"> 			<spring:message code="lbl.user"				/></c:set>	
	<c:set var="lblAction"> 		<spring:message code="lbl.alert-action"		/></c:set>	

	<input type="hidden" id="lbl-request" 		value="${lblRequest}" 		/>
	<input type="hidden" id="lbl-association"	value="${lblAssociation}"	/>
	<input type="hidden" id="lbl-arrival" 		value="${lblArrival}" 		/>
	<input type="hidden" id="lbl-departure"		value="${lblDeparture}"	 	/>
	<input type="hidden" id="lbl-monday" 		value="${lblMonday}" 		/>
	<input type="hidden" id="lbl-tuesday" 		value="${lblTuesday}" 		/>
	<input type="hidden" id="lbl-wednesday"		value="${lblWednesday}" 	/>
	<input type="hidden" id="lbl-thursday" 		value="${lblThursday}" 		/>
	<input type="hidden" id="lbl-friday" 		value="${lblFriday}"		/>
	<input type="hidden" id="lbl-norequests" 	value="${lblNoRequests}" 	/>
	<input type="hidden" id="lbl-noassocs" 		value="${lblNoAssocs}"		/>
	<input type="hidden" id="lbl-user" 			value="${lblUser}"			/>
	<input type="hidden" id="lbl-action" 		value="${lblAction}"		/>

	<!-----------------------------------------
	[Scripts]
	------------------------------------------>		
	<script type="text/javascript" src="resources/scripts/Utils.js">	</script>
	<script type="text/javascript" src="resources/scripts/People.js">	</script>

</body>
