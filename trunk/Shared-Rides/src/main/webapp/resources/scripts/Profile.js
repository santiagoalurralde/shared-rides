function fillTable(schedule, type){
	
	var $table, image;
	
	if(type == "Driver")
	{
		$table = $("#tableDriver");
		image = "seat.png";
	}
	else
	{
		$table = $("#tablePed");
		image = "steering.png";
	}
	
	$table.append("<tr id='rDay'></tr><tr id='rIn'></tr><tr id='rOut'></tr>");
	$table.find("#rDay").append("<th></th>"); 
	$table.find("#rIn" ).append('<td>'+ $('#lblArrival').val() +'</td>');
	$table.find("#rOut" ).append('<td>'+ $('#lblDeparture').val() +'</td>');
	
	for(var i=0; i<schedule.length; i++)
	{
		var btnRequest;		
		if($( "#valMine" ).val())
			btnRequest	= '';
		else
			btnRequest	= '<button 	class="btnRequestAssoc" style="margin-left: 3px"><img src="resources/images/'+ image +'" width="25px"/></button>';
		
		var hdnDay		= '<input 	class="hdnDay"		type="hidden" value="'+ schedule[i][0] +'"/>'; 
		var hdnIn		= '<input 	class="hdnInOut" 	type="hidden" value="1"/>'; 
		var hdnOut		= '<input 	class="hdnInOut"	type="hidden" value="2"/>';
		
		$table.find("#rDay" ).append("<th>"+ getDayLabel(schedule[i][0]) +"</th>");
		$table.find("#rIn" ).append("<td>"+ schedule[i][1] + btnRequest + hdnDay + hdnIn +"</td>");
		$table.find("#rOut" ).append("<td>"+ schedule[i][2] + btnRequest + hdnDay + hdnOut +"</td>");
	}
}

if( $( "#valDriver" ).val() === 'false' )		//It's not a driver
	fixView($( '#pedestrianData' ), $( '#driverData' ));

if( $( "#valPedestrian" ).val() === 'false' )	//It's not a pedestrian
	fixView($( '#driverData' ), $( '#pedestrianData' ));

function fixView($targetThis, $targetOther){
	$targetOther.css("display", "none");
	$targetThis.css("float", "none").css("width", "100%").css("text-align","left");
	$( '.star' ).css("float", "left").css("margin-right", "1%").css("margin-left", "0%");
	$( '.theRating' ).css("margin", "2% 0% 0% 0%");	
	$( '#profileData' ).css("padding-right", "70px").css("padding-left", "70px");		
	$( '#line' ).css("display", "none");			
	$( '.mapStatic' ).css("height", "400px").css("width", "760px");
	$( '.mapContainer' ).css("margin-left", "0px");
}

$(document).ready(function(){
	$( '.btnRequestAssoc' ).click(function(){
		
		var $day	= $( this ).parent().find(".hdnDay").val();
		var $inOut	= $( this ).parent().find(".hdnInOut").val();			
		var $idUser	= $( "#valId" ).val();
		
		$.post( 'requestAssoc.do', { "day":  $day, "inout": $inOut, "idUser": $idUser },
			function(msg)
			{
				if (msg != '')
					window.alert(msg);
			}); 			
	});
});



