/***************************************************************************************
 * EVENTS
 ***************************************************************************************/

$(document).ready(function(){
	
	if($( "#valMine" ).val())				//If it's my profile, it shouldn't rate
		$( ".ifancybox" ).attr( "href", "");	
	
	//Request Association
	$( '.btnRequestAssoc' ).click(function(){
		requestAssociation(this);	
	});
	
	//Show Current Map
	$( ".cellCheckMap" ).click(function(){
		showMap(this);		
	});

	//Mouse over
	$('.cellCheckMap').mouseover(function() {
		  $('.txtHelp').show();
			$(document).bind('mousemove',function(e){ 
		        $(".txtHelp").css({
		            left:  e.pageX - 150,
		            top:   e.pageY - 75
		         });
			}); 		  
	});
	$('.cellCheckMap').mouseout(function() {
		  $('.txtHelp').hide();
	});
});


if( $( "#valDriver" ).val() === 'false' )		//It's not a driver
	fixView($( '#pedestrianData' ), $( '#driverData' ));

if( $( "#valPedestrian" ).val() === 'false' )	//It's not a pedestrian
	fixView($( '#driverData' ), $( '#pedestrianData' ));


/***************************************************************************************
 * FUNCTIONS
 ***************************************************************************************/

/**
 * Completes individual schedule table.
 * 
 * @param {array} 	schedule - array that contains each day
 * @param {string} type - userType (side of schedule)
 */
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
	
	//Create Rows
	$table.append("<tr id='rDay'></tr><tr id='rIn'></tr><tr id='rOut'></tr>");
	$table.find("#rDay").append("<th></th>"); 
	$table.find("#rIn" ).append('<td>'+ $('#lblArrival').val() +'</td>');
	$table.find("#rOut" ).append('<td>'+ $('#lblDeparture').val() +'</td>');
	
	for(var i=0; i<schedule.length; i++)
	{
		var btnRequest;		
		if($( "#valMine" ).val())	//If it's my profile can't invite myself
			btnRequest	= '';
		else
			btnRequest	= '<button 	class="btnRequestAssoc" style="margin-left: 3px"><img src="resources/images/'+ image +'" width="25px"/></button>';

		/*
		var btnCheckMap;		
		btnCheckMap	= '<button 	class="" style="margin-left: 3px">Ver Mapa</button>';
		*/
		
		var hdnDay		= '<input 	class="hdnDay"		type="hidden" value="'+ schedule[i][0] +'"/>'; 
		var hdnIn		= '<input 	class="hdnInOut" 	type="hidden" value="1"/>'; 
		var hdnOut		= '<input 	class="hdnInOut"	type="hidden" value="2"/>';
		/*
		var hdnLat 		= '<input 	class="hdnInOut"	type="hidden" value=""/>';
		var hdnLon 		= '<input 	class="hdnInOut"	type="hidden" value=""/>';
		*/
	
		$table.find("#rDay" ).append("<th>"+ getDayLabel(schedule[i][0]) +"</th>");
		$table.find("#rIn" ).append("<td class='cellCheckMap'>"+ schedule[i][1] + btnRequest + hdnDay + hdnIn +"</td>");
		$table.find("#rOut" ).append("<td class='cellCheckMap'>"+ schedule[i][2] + btnRequest + hdnDay + hdnOut +"</td>");
	}
}

/**
 * Fixes profile display according to user type.
 * 
 * @param {jquery} $targetThis - div that's gonna stay.
 * @param {jquery} $targetOther - div that's gonna be hidden.
 */
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

/**
 * Shows current map according to selected cell.
 * 
 * @param {javascript} target - clicked cell.
 */
function showMap(target){
	if($( target ).closest( "table" ).attr( "id" ) == "tablePed") 
		setMapPedestrian(_lonPed, _latPed);	//$("this #hdnDay").val()  ----	$("this #hdnInOut").val() 
	else	
		setMapDriver("caro1in.gpx"); 		//	
}

/**
 * Requests an association according to selected cell.
 * 
 * @param {javascript} target - clicked button.
 */
function requestAssociation(target){
	var $day	= $( target ).parent().find(".hdnDay").val();
	var $inOut	= $( target ).parent().find(".hdnInOut").val();			
	var $idUser	= $( "#valId" ).val();
	
	$.post( "requestAssoc.do", { "day":  $day, "inout": $inOut, "idUser": $idUser },
		function(msg)
		{
			if (msg != '')
				window.alert(msg);
		}); 
}
