<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<body>
	<div id="theContent" class="theBox">	
		
		<section title="List Data" class="tupper">
				<div class="theBoard theLightBox" style="margin-top: 10px"> 		
					<div id="listData">				<!-- Listas				-->	
										
						<div id="pending" class="blockRight"> 		<!-- Lista Solicitudes 	--> 
							<h2>	Pendientes	</h2>	
							<table style="margin-top: 10px" class="tableUsers">
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
							</table>
						</div> 
						 		
						<div id="associated" class="blockLeft"> 		<!-- Lista Asociados 	-->
							<h2>	Asociados		</h2>
							<table style="margin-top: 10px" class="tableUsers">
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
							</table>
						</div>		
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
										
						<div id="applicantsSchedule">		 
							<div class="blockRight blockHalf">		
								<h2> 	Actuales		</h2>	
							
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
							
							<div class="blockLeft blockHalf">
								<h2> 	Solicitó	</h2>	
							
								<div> 						<!-- Horario	-->
									<table id="" class="theSchedule">
										<tr>
											<th></th>
											<th><spring:message code="label.monday"/></th>
											<th><spring:message code="label.wednesday"/></th>
											<th><spring:message code="label.friday"/></th>
										</tr>
										<tr>
											<td><spring:message code="label.arrival"/></td>
											<td>13 <img src="resources/images/accept.png" width="15px"/> <img src="resources/images/cancel.png" width="15px"/></td>
											<td>14 <img src="resources/images/accept.png" width="15px"/> <img src="resources/images/cancel.png" width="15px"/></td>
											<td>14 <img src="resources/images/accept.png" width="15px"/> <img src="resources/images/cancel.png" width="15px"/></td>
										</tr>
										<tr>
											<td><spring:message code="label.departure"/></td>
											<td>19 <img src="resources/images/accept.png" width="15px"/> <img src="resources/images/cancel.png" width="15px"/></td>
											<td>20 <img src="resources/images/accept.png" width="15px"/> <img src="resources/images/cancel.png" width="15px"/></td>
											<td>20 <img src="resources/images/accept.png" width="15px"/> <img src="resources/images/cancel.png" width="15px"/></td>
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
