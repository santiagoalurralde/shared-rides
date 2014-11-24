<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
				<h1> <spring:message code="lbl.schedule"/> </h1>
				
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
	[Scripts]
	------------------------------------------>		
    <script>
        var createLabels = (function (){
            var labels = {
                //Labels' name
                lblRequest      : '<spring:message code="lbl.request"           />',
                lblAssociation  : '<spring:message code="lbl.association"       />',
                lblArrival      : '<spring:message code="lbl.arrival"           />',
                lblDeparture    : '<spring:message code="lbl.departure"         />',
                lblMonday       : '<spring:message code="lbl.monday"            />',
                lblTuesday      : '<spring:message code="lbl.tuesday"           />',
                lblWednesday    : '<spring:message code="lbl.wednesday"         />',
                lblThursday     : '<spring:message code="lbl.thursday"          />',
                lblFriday       : '<spring:message code="lbl.friday"            />',
                lblNoRequests   : '<spring:message code="lbl.alert-norequests"  />',
                lblNoAssocs     : '<spring:message code="lbl.alert-noassocs"    />',
                lblUser         : '<spring:message code="lbl.user"              />',
                lblAction       : '<spring:message code="lbl.alert-action"      />'
            }
            return function () { return labels };
        })();
    </script>
    <script type="text/javascript" src="resources/scripts/Utils.js">	</script>
	<script type="text/javascript" src="resources/scripts/People.js">	</script>    

</body>
