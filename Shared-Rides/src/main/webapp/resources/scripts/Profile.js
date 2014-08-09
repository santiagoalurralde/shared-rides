var _schPed = [];
var _schDriver = [];

/***************************************************************************************
 * FIXES
 ***************************************************************************************/

if( $( "#valDriver" ).val() === 'false' )		//It's not a driver
	fixView($( '#pedestrianData' ), $( '#driverData' ));

if( $( "#valPedestrian" ).val() === 'false' )	//It's not a pedestrian
	fixView($( '#driverData' ), $( '#pedestrianData' ));


/***************************************************************************************
 * EVENTS
 ***************************************************************************************/

$(function() {
	$( document ).tooltip({
		position: {
			my: "center bottom-20",
			at: "center top",
			using: function( position, feedback ) {
				$( this ).css( position );
				$( "<div>" )
				.addClass( "arrow" )
				.addClass( feedback.vertical )
				.addClass( feedback.horizontal )
				.appendTo( this );
			}
		}
	});
});

$( document ).ready(function(){
	
	fillTable(_schDriver, "Driver");
	fillTable(_schPed, "Pedestrian");

	//Fancybox
    $(".ifancybox").fancybox({
        'width'                 :       500,
        'height'                :       330,
        'autoSize'              :       false,
        'fitToView'             :       false,
        'type'                  :       'iframe'
    });
	
	//If it's my profile, it shouldn't rate
	if($( "#valMine" ).val()) {
		$( ".star" ).unwrap();
	}
	
	//Request Association
	$( ".btnRequestAssoc" ).click(function() {
		requestAssociation(this);	
		$( this ).prop( "disabled", true );		
	});
	
	//Show Current Map
	$( ".cellCheckMap" ).click(function() {
		showMap(this);		
	});
});


/***************************************************************************************
 * FUNCTIONS
 ***************************************************************************************/

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
 * Completes individual schedule table.
 * 
 * @param {array} 	schedule - array that contains each day
 * @param {string} type - userType (side of schedule)
 */
function fillTable(schedule, type) {
	
	var $table, image, btnReqIn = "", btnReqOut = "";
	
	if(type == "Driver") {
		$table = $("#tableDriver");
		image = "seat.png";
	}
	else {
		$table = $("#tablePed");
		image = "steering.png";
	}
	
	//Create Rows
	$table.append("<tr id='rDay'></tr><tr id='rIn'></tr><tr id='rOut'></tr>");
	$table.find("#rDay").append("<th></th>"); 
	$table.find("#rIn" ).append('<td>'+ $('#lblArrival').val() +'</td>');
	$table.find("#rOut" ).append('<td>'+ $('#lblDeparture').val() +'</td>');
	
	for(var i=0; i<schedule.length; i++) {
		var hdnMapIn, hdnMapOut;
		var hdnDay	= '<input class="hdnDay"	type="hidden" value="'+ schedule[i].day +'"/>'; 
		var hdnIn	= '<input class="hdnInOut" 	type="hidden" value="1"/>'; 
		var hdnOut	= '<input class="hdnInOut"	type="hidden" value="2"/>';
		
		if(!$( "#valMine" ).val()) {
			//If it's my profile can't invite myself

			btnReqIn	= '<button 	class="btnRequestAssoc" ><img src="resources/images/'+ image +'"/></button>';
			btnReqOut	= '<button 	class="btnRequestAssoc" ><img src="resources/images/'+ image +'"/></button>';
		}
				
		if(type == "Pedestrian") {	
			//If Pedestrian has driver, can't invite him.
			
			if(schedule.hasDriverIn == true || schedule[i].allowIn)
				btnReqIn	= "";	//DISABLE!!!!!!!!!!!
			if(schedule.hasDriverOut == true || schedule[i].allowOut)
				btnReqOut 	= "";
			
			hdnMapIn	=	'<input class="hdnLat"	type="hidden" value="'+ schedule[i].latIn +'"/>'+
							'<input class="hdnLon"	type="hidden" value="'+ schedule[i].lonIn +'"/>';						
			hdnMapOut	=	'<input class="hdnLat"	type="hidden" value="'+ schedule[i].latOut +'"/>'+
							'<input class="hdnLon"	type="hidden" value="'+ schedule[i].lonOut +'"/>';	
		}
		else{
			if(schedule.freeSeatsIn == 0 || schedule[i].allowIn)
				btnReqIn	= "";	//DISABLE!!!!!!!!!!!
			if(schedule.freeSeatsOut == 0 || schedule[i].allowOut)
				btnReqOut 	= "";
			
			hdnsMapOut 	=	'<input class="hdnPath"		type="hidden" value="'+ schedule[i].pathIn +'"/>';
			hdnsMapIn  	=	'<input class="hdnPath"		type="hidden" value="'+ schedule[i].pathOut +'"/>';						
		}
		
		$table.find("#rDay" ).append("<th>"+ getDayLabel(schedule[i].day) +"</th>");
		$table.find("#rIn" ).append("<td class='cellCheckMap'>"+ schedule[i].hourIn + btnReqIn + hdnDay + hdnIn + hdnsMapIn + "</td>");
		$table.find("#rOut" ).append("<td class='cellCheckMap'>"+ schedule[i].hourOut + btnReqOut + hdnDay + hdnOut + hdnsMapOut + "</td>");
		
		delete hdnMapIn, hdnMapOut, hdnDay, hdnIn, hdnOut;
	}
	
	$( ".cellCheckMap" ).prop( "title", "Ver mapa en este horario");
}

/**
 * Shows current map according to selected cell.
 * 
 * @param {javascript} target - clicked cell.
 */
function showMap(target){
	if($( target ).closest( "table" ).attr( "id" ) == "tablePed") 
		setMapPedestrian($( target ).find(".hdnLat").val(), $( target ).find(".hdnLon").val());					
	else
		setMapDriver($( target ).find(".hdnPath").val()); 	
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

/**
 * Constructor
 */
function DetailSchedulePedestrian(){
	this.day 			= "";
	this.allowIn 		= "";
	this.allowOut 		= "";		
	this.hourIn 		= "";
	this.hourOut 		= "";		
	this.hasDriverIn 	= false;
	this.hasDriverOut 	= false;
	this.pathIn 		= "";
	this.pathOut		= "";
}

/**
 * Constructor
 */
function DetailScheduleDriver(){
	this.day 			= "";
	this.allowIn 		= "";
	this.allowOut 		= "";	
	this.hourIn 		= "";
	this.hourOut 		= "";		
	this.freeSeatsIn 	= "";
	this.freeSeatsOut 	= "";
	this.latIn 			= "";
	this.latOut			= "";
	this.lonIn 			= "";
	this.lonOut			= "";	
}