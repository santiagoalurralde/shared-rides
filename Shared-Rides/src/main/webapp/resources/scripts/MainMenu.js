var _step		=  -1; 	//Contador de Pasos
var _user		= 	0;	//Tipo de Usuario
var _shift		= 	0;	//Turno


/***************************************************************************************
 * STEPS
 ***************************************************************************************/

$( document ).ready(function() {
	
	initMap(); 
	
	start();
	
	//Acciones al presionar las imagenes
	$( "#imgSun" ).click(function() {
		changeShift(1);
	});
	
	$( "#imgMoon" ).click(function() {
		changeShift(2);
	});
	
	$( "#imgBoot" ).click(function() {
		changeUserType(1);
	});
	
	$( "#imgSteering" ).click(function() {
		changeUserType(2);
	});

	//Pressing next or back
	$( "#btnNext, #btnBack" ).click(function() {
		update(_step);
	});
	
	$( "#btnOK" ).click(function() {
		findUsers();
	});
});

/**
 * Checks if we can add 1 step
 */
function stepNext() {
	if(_step==0 && _user==0)
		alert("Seleccione un tipo de Usuario!");
	else if(_step==1 && _shift==0)
		alert("Seleccione un turno!");
	else
		_step++;
}

/**
 * Moves back 1 step
 */
function stepBack() {
	_step--;
}


/***************************************************************************************
 * FUNCTIONS
 ***************************************************************************************/

/**
 * Selects type of shift and highlights related icon.
 */
function changeShift(s) {
	_shift = s;
	
	if(_shift == 1)	{
		//Morning
		$( "#imgMoon" ).css('opacity', '0.05');
		$( "#imgSun" ).css('opacity', '1');
	}
	else {
		//Afternoon
		$( "#imgMoon" ).css('opacity', '1');
		$( "#imgSun" ).css('opacity', '0.05');
	}	
}

/**
 * Selects type of user and highlights related icon.
 */
function changeUserType(u) {
	_user = u;
	
	if(_user == 1) {
		//Pedestrian
		$( "#imgBoot" ).css('opacity', '1');
		$( "#imgSteering" ).css('opacity', '0.05');	
	}
	else {
		//Driver
		$( "#imgSteering" ).css('opacity', '1');
		$( "#imgBoot" ).css('opacity', '0.05');
	}
}

/**
 * Sends all data collected through post, and shows results
 */
function findUsers() {
	var coordsJs;				//Datos de coordenadas
	
	if(_user == 2)
		coordsJs = "[{lon=" + _lon.toString() + " , lat=" + _lat.toString() + "}]";
	else
		coordsJs = gpxTrack.confirm();
			
	$.post( "find.do", { "user": _user , "shift": _shift, "mapData": coordsJs }, 
		function(json) {
			var peopleFound = $.parseJSON(json);
			if(peopleFound == null) {
				$( ".alerts" ).show();	
				$( "#tableFound" ).hide();				
			}
			else {
				$.each(peopleFound, function(i, data) {
					var distance = Math.ceil(data.distance);
					$( "#tableFound" ).show();	
					$( ".alerts" ).hide();	
					$( "#tableFound" ).append(
							"<tr>"+
								"<td>"+ data.name +" "+ data.surname +"</td>"+
								"<td>"+
									"<a href='/Shared-Rides/profile.do?user="+ data.id +"'><img src='resources/profilePic/" + data.picture + "'/></a>" +
								"</td>"+
								"<td>"+ $( "#lblBlocks1" ).val() +" "+ distance +" "+ $( "#lblBlocks2" ).val() +"</td>"+
							"</tr>");
				});	
			}
		}); 
	
	//Brings the list
	$( "#mapDriver" ).css('display', 'none');
	$( "#mapPedestrian" ).css('display', 'none');
	
	$( "#listFound" ).show( 'fast' );
	$( "#btnOK"	).hide( 'fast' );
}

/**
 * Establishes the starting environment
 */
function start() {	
	highlightStep(_step);		
	$( "#mapDriver" ).css( 'display', 'none' );
	$( "#mapPedestrian" ).css( 'display', 'none' );
	$( "#btnBack" ).hide();							
	$( "#listFound" ).hide();
}

/**
 * Manages GUI according to current step of form.
 * 
 * @param {Number} step - current step
 */
function update(step) {
	switch(step) {
		case 0:
			highlightStep(step);
			$( "#imgSun" ).hide();
			$( "#imgMoon" ).hide();
			$( "#imgBoot" ).show( 'fast' );
			$( "#imgSteering" ).show( 'fast' );
			$( "#btnBack" ).hide( 'fast' );
			$( "#btnNext" ).css('marginLeft', '0px');
			break;
		case 1:
			highlightStep(step);			
			$( "#mapDriver" ).hide();
			$( "#mapPedestrian" ).hide();
			$( "#imgBoot" ).hide();
			$( "#imgSteering" ).hide();
			$( "#imgSun" ).show( 'fast' );
			$( "#imgMoon" ).show( 'fast' );
			$( "#btnOK" ).hide( 'fast' );
			$( "#btnNext" ).show( 'slow' );
			$( "#btnNext" ).css('marginLeft', '60px');
			$( "#btnBack" ).show( 'fast' );	
			$( "#listFound" ).hide( 'fast' );
			$( "#tableFound td" ).remove();
			break;
		case 2:
			highlightStep(step);			
			$( "#imgSun" ).hide();
			$( "#imgMoon" ).hide();
			
			if(_user == 2)
				$( "#mapPedestrian" ).show( 'slow' );
			else
				$( "#mapDriver" ).show( 'slow' );
		
			$( "#btnNext" ).hide();
			$( "#btnOK" ).css('marginLeft', '60px');
			$( "#btnOK" ).show( 'slow' );			
			break;
	}	
}

/**
 * Highlights current step
 * 
 * @param {Number} step - current step
 */
function highlightStep(step) {
	switch(step) {
		case -1:
		case 0:
			$( "#step1" ).css('opacity', '1');
			$( "#step2" ).css('opacity', '0.2');
			$( "#step3" ).css('opacity', '0.2');
			break;
		case 1:
			$( "#step1" ).css('opacity', '0.2');
			$( "#step2" ).css('opacity', '1');
			$( "#step3" ).css('opacity', '0.2');
			break;
		case 2:
			$( "#step1" ).css('opacity', '0.2');
			$( "#step2" ).css('opacity', '0.2');
			$( "#step3" ).css('opacity', '1');
			break;
	}
}


