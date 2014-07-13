function fillTablePed(schPed){
	var tPed = $("#tablePed");
	
	tPed.append("<tr id='rDay'></tr><tr id='rIn'></tr><tr id='rOut'></tr>");

	$( "#tablePed #rDay" ).append("<th></th>"); 
	$( "#tablePed #rIn" ).append('<td>'+ $('#lblArrival').val() +'</td>');
	$( "#tablePed #rOut" ).append('<td>'+ $('#lblDeparture').val() +'</td>');
	
	for(var i=0; i<schPed.length; i++)
	{
		var btnRequest	= '<button class="btnRequestAssoc" style="margin-left: 3px"><img src="resources/images/steering.png" width="25px"/></button>'; 
		var hdnDay		= '<input id="hdnDay"	type="hidden" value="'+ schPed[i][0] +'"/>'; 
		var hdnIn		= '<input class="hdnInOut" 	type="hidden" value="1"/>'; 
		var hdnOut		= '<input class="hdnInOut"	type="hidden" value="2"/>';
		
		$( "#tablePed #rDay" ).append("<th>"+ getDayLabel(schPed[i][0]) +"</th>");
		$( "#tablePed #rIn" ).append("<td id='day'"+ schPed[i][0] +"-1'>"+ schPed[i][1] + btnRequest + hdnDay + hdnIn +"</td>");
		$( "#tablePed #rOut" ).append("<td id='day'"+ schPed[i][0] +"-2'>"+ schPed[i][2] + btnRequest + hdnDay + hdnOut +"</td>");
	}
}

function fillTableDriver(schDriver){
	
	var tDriver = $("#tableDriver");
	
	tDriver.append("<tr id='rDay'></tr><tr id='rIn'></tr><tr id='rOut'></tr>");
	$( "#tableDriver #rDay" ).append("<th></th>");
	$( "#tablePed #rIn" ).append('<td>'+ $('#lblArrival').val() +'</td>');
	$( "#tablePed #rOut" ).append('<td>'+ $('#lblDeparture').val() +'</td>');
	
	for(var i=0; i<schDriver.length; i++)
	{		
		var btnRequest	= '<button class="btnRequestAssoc" style="margin-left: 3px"><img src="resources/images/seat.png" width="25px"/></button>'; 
		var hdnDay		= '<input id="hdnDay"		type="hidden" value="'+ schDriver[i][0] +'"/>'; 
		var hdnIn		= '<input class="hdnInOut" 	type="hidden" value="1"/>'; 
		var hdnOut		= '<input class="hdnInOut"	type="hidden" value="2"/>';
		
		$( "#tableDriver #rDay" ).append("<th>"+ getDayLabel(schDriver[i][0]) +"</th>");
		$( "#tableDriver #rIn" ).append("<td id='day'"+ schDriver[i][0] +"-1'>"+ schDriver[i][1] + btnRequest + hdnDay + hdnIn +"</td>");
		$( "#tableDriver #rOut" ).append("<td id='day'"+ schDriver[i][0] +"-2'>"+ schDriver[i][2] + btnRequest + hdnDay + hdnOut +"</td>");
	}
}