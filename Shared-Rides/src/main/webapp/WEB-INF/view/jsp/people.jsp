<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<body>
	<div id="theContent" class="theBox">	
		
		<section title="List Data" class="tupper">
				<div class="theBoard theLightBox" style="margin-top: 10px"> 		
					<div id="listData">				<!-- Listas				-->	
										
						<div id="pending" class="blockHalf blockRight"> 		<!-- Lista Solicitudes 	--> 
							<h2> <spring:message code="label.pending"/> </h2>
							<table id="tablePending"	class="tableUsers" style="margin-top: 10px">

							</table>
						</div> 
						
						<!-- <div class="vrs"></div> -->
							
						<div id="associated" class="blockHalf blockLeft">		<!-- Lista Asociados 	-->
							<h2> <spring:message code="label.associated"/> </h2>
							<table id="tableAssociated"	class="tableUsers" style="margin-top: 10px">

							</table>
						</div>		
					
						<div class="clearer"></div>
					</div>	
				</div>
		</section>
		
	
		<section title="Schedule Data" class="tupper" style="margin-top: 20px">
			<div class="theBoard theLightBox"> 		
					<div id="scheduleData">				
						<div id="associatedSchedule" style="display: none">				 		
							<div> 						<!-- Horario	-->
								<table id="">

								</table>					
							</div>		 
						</div>
										
						<div id="pendingSchedule">		 
							<div class="blockLeft blockHalf">		
								<h2> <spring:message code="label.requested"/> </h2>
							
								<div> 						<!-- Horario	-->
									<table id="" class="theSchedule">
										<tr>
											<th></th>
											<th><spring:message code="label.tuesday"/></th>
											<th><spring:message code="label.thursday"/></th>
										</tr>
										<tr>
											<td><spring:message code="label.arrival"/></td>
											<td>15 <img src="resources/images/cancel.png" width="15px"/></td>
											<td>13 <img src="resources/images/cancel.png" width="15px"/></td>
										</tr>
										<tr>
											<td><spring:message code="label.departure"/></td>
											<td>20 <img src="resources/images/cancel.png" width="15px"/></td>
											<td>18 <img src="resources/images/cancel.png" width="15px"/></td>
										</tr>										
									</table>					
								</div>	
							</div>
							
							<div class="blockRight blockHalf">
								<h2> <spring:message code="label.offered"/> </h2>	
							
								<div> 						<!-- Horario	-->
									<table id="" class="theSchedule">
										<tr>
											<th></th>
											<th><spring:message code="label.monday"/></th>
											<th><spring:message code="label.wednesday"/></th>
											<th><spring:message code="label.friday"/></th>
										</tr>
									</table>					
								</div>
							</div>	
						</div>
					</div>	
			</div>
		</section>
	</div>
</body>


<script>
	function createTables(){
		$( "#tablePending" ).append("<tr><th> Usuario  </th></tr>");
		$( "#tableAssociated" ).append("<tr><th> Usuario  </th></tr>");		
	}
	
	createTables();

	$.post( 'loadAssociations.do', 
		function(json){
		/*
		Aca va a traer la informacion de las personas, como un json.
		Es un JsonArray que tiene adentro dos JsonArray: uno con las asociaciones pendientes y otro con las
		asociaciones aceptadas (o sea mis amigos)
		Dentro de cada uno de estos; tengo el id de la asociacion y el nombre completo de la persona
		Ese id de la asociacion no hace falta mostrarlo, pero sirve para dps buscar la info de esa asoc para mostrar
		abajo
		*/

		var jsonNew = $.parseJSON(json);
		var side;
		
		$.each(jsonNew[0], function(i, data){
			if(data.side=="supplier")
				side = "Ajena";
			else
				side = "Propia";
		
			var applicant = "<div>" +
								"<a style='margin-right: 10px' href='/Shared-Rides/profile.do?user="+ data.userId+"'>"+
									"<img src='resources/profilePic/"+ data.pic +"'>"+
								"</a>"+
								"<span style='vertical-align: top; margin-right: 10px'>"+ data.name + "</span>"+
								"<input type='hidden' id="+ data.userId +">"+ 																
								"<button onclick='viewPendingSchedule(this)' style='vertical-align: top'><spring:message code='label.request'/></button>"+
							"</div>";
				
			$( "#tablePending" ).append("<tr><td>"+ applicant +"</td></tr>");
		});		
		
		$.each(jsonNew[1], function(i, data){
			var applicant = "<div id='pic'>" +
								"<a style='margin-right: 10px' href='/Shared-Rides/profile.do?user="+ data.userId +"'>"+
									"<img src='resources/profilePic/"+ data.pic +"'>"+
								"</a>"+
								"<span style='vertical-align: top; margin-right: 10px'>"+ data.name +"</span>"+
								"<input type='hidden' id="+ data.userId +">"+ 								
								"<button onclick='viewAssociatedSchedule(this)' style='vertical-align: top'><spring:message code='label.association'/></button>"+
							"</div>";
			
			$( "#tableAssociated" ).append("<tr><td>"+ applicant +"</td></tr>");
		});				
	});
	
	function viewPendingSchedule(target)
	{
		var $userId = $(target).parent().find("input").attr("id");

		$.post( "viewSchedule.do", { "user": $userId , "type": 0}, 
			function(json){
				var jsonNew = $.parseJSON(json);
				$.each(jsonNew, function(i, data){
					$( "#" ).append("<tr><td>" + data.name + "</td><td>" + data.surname + "</td><td><a href='/Shared-Rides/profile.do?user="+ data.id +"'><img src='resources/profilePic/" + data.picture + "'/></a></td></tr>");
				});
		}); 
	}
	
	function viewAssociatedSchedule(target)
	{
		var $userId = $(target).parent().find("input").attr("id");

		$.post( "viewSchedule.do", { "user": $userId , "type": 1}, 
			function(json){
				var jsonNew = $.parseJSON(json);
				$.each(jsonNew, function(i, data){
					$( "#" ).append("<tr><td>" + data.name + "</td><td>" + data.surname + "</td><td><a href='/Shared-Rides/profile.do?user="+ data.id +"'><img src='resources/profilePic/" + data.picture + "'/></a></td></tr>");
				});
		}); 
		
	}	
	
</script>


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
