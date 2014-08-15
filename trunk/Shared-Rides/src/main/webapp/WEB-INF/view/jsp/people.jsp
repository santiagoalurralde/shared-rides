<%@	taglib uri="http://www.springframework.org/tags" 	prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 		prefix="c" %>

<body>
	<div id="theContent">	
		
		<section title="List Data" class="tupper">
				<div id="listData" class="theBoard lightBorder"> 	
										
					<div id="pending" class="blockHalf blockRight"> 		<!-- Lista Solicitudes 	--> 
						<h2> <spring:message code="label.pending"/> </h2>
						<table id="tablePending"	class="tableUsers">
						</table>	
					</div> 
					
					<!-- <div class="vrs"></div> -->
						
					<div id="associated" class="blockHalf blockLeft">		<!-- Lista Asociados 	-->
					
						<h2> <spring:message code="label.associated"/> </h2>
						<table id="tableAssociated"	class="tableUsers">
						</table>
						
					</div>		
				
					<div class="clearer"></div>
					
				</div>
		</section>
		
		<section title="Schedule Data" class="tupper" style="margin-top: 20px">
			<div id="scheduleData" class="theBoard lightBorder"> 		
				<div id="requested" class="blockLeft blockHalf">		
					<h2> <spring:message code="label.requested"/> </h2>
				
					<div> 			<!-- Horario	-->
						<table id="tableRequested" class="theSchedule">
						</table>					
					</div>	
				</div>
				
				<div id="offered" class="blockRight blockHalf">
					<h2> <spring:message code="label.offered"/> </h2>	
				
					<div> 			<!-- Horario	-->
						<table id="tableOffered" class="theSchedule">
						</table>					
					</div>
				</div>	
				
				<div class="clearer"></div>
			</div>	
		</section>
	</div>
	
	<!--  LABELS  -->
	
	<c:set var="labelRequest"><spring:message code="label.request"/></c:set>	
	<input id="lblRequest" type="hidden" value="${labelRequest}"/>
	<c:set var="labelAssociation"><spring:message code="label.association"/></c:set>
	<input id="lblAssociation" type="hidden" value="${labelAssociation}"/>	
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

	<!--  SCRIPTS  -->

	<script type="text/javascript" src="resources/scripts/Utils.js">	</script>
	<script type="text/javascript" src="resources/scripts/People.js">	</script>

</body>














<!-- 
<tr>
	<td> Steve	 		</td>
	<td> Jobs			</td>
	<td> <img src="http://www.igdigital.com/wp-content/uploads/2013/03/steve_jobs_apple1-1.jpeg"/> </td>
</tr>
<tr>
	<td> Clint			</td>
	<td> Eastwood		</td>
	<td><img src="http://2.bp.blogspot.com/-Pleua1JUrJg/UajruKT0gaI/AAAAAAAABy4/TDbntFwudPM/s640/Clint-Eastwood-.jpg"/>	</td>		
</tr>
<tr>
	<td> Pablo 			</td>
	<td> Picasso		</td>
	<td><img src="http://upload.wikimedia.org/wikipedia/commons/thumb/9/98/Pablo_picasso_1.jpg/192px-Pablo_picasso_1.jpg"/>				</td>						
</tr>
-->
