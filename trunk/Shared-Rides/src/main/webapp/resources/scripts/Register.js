var _step			= -1; 	//Contador de Pasos
var _previous     	= "";
var _previous2  	= "";
var _previousTarget	= "";
var _userType;
var _days			= new Array();
var _matrices		= new Array();
var _projections	= new Array();

for (var loop=0; loop<5; loop++) {
    _days[loop] 		= null;
    _matrices[loop] 	= null;
    _projections[loop] 	= null;    
}

/***************************************************************************************
 * STEPS
 ***************************************************************************************/

$( document ).ready(function(){
	//Start pedestrians map.
	initMap(); 
	
	start();

	//Pressing next or back
	$( "#btnNext, #btnBack" ).click(function(){
		update(_step);
	});
	
	$( "#btnMap" ).click(function(){
		saveMap();		
	});
	
	$( "#btnOK" ).click(function(){
		signUp();
	});
});

/**
 * Establishes the starting environment
 */
function start() {
	$( "#mapDriver" ).css( 'display', 'none' );
	$( "#mapPedestrian" ).css( 'display', 'none' );
	
	highlightStep(0);
	
	fillRowsInOut("in");		
	fillRowsInOut("out");
	
	var neighborhoods = ["Alta Córdoba","Alto Alberdi","Alto Verde","Argüello","Bella Vista","Centro","Cerro Chico","Cerro de las Rosas","Cerveceros","Chateau Carreras","Cofico","Colinas del Cerro","Country Jockey Club","Country Las Delicias","Country Lomas de la Carolina","Crisol","Dean Funes","Ejército Argentino","El Quebracho","Empalme","Ferreyra","General Bustos","General Paz","General Pueyrredon","Granja De Funes","Güemes","Ituzaingo","Jardín","Juniors","La France","Las Flores","Las Palmas","Las Violetas","Los Boulevares","Los Paraísos","Marques De Sobremonte","Nueva Córdoba","Observatorio","Palermo Bajo","Patricios","Poeta Lugones","San Martín","San Vicente","Urca","Villa Belgrano","Villa Cabrera","Villa Centenario","Villa Warcalde","Yapeyu","Yofre"];
	
	for(var loop=0; loop<neighborhoods.length; loop++)
		$( "#neighborhood" ).append("<option value='"+ neighborhoods[loop] +"'>"
										+ neighborhoods[loop] +
									"</option>");	
	
	var brands = ["Alfa-Romeo", "Audi", "BMW", "Citroen", "Chery", "Chevrolet", "Chrysler", "Daihatsu", "Dodge", "Fiat", "Ford", "Honda", "Hyundai", "Jeep", "Kia", "Land Rover", "Mazda", "Mercedes Benz", "Mini", "Mitsubishi", "Nissan", "Peugeot", "Renault", "Seat",  "Subaru", "Suzuki", "Toyota", "Volkswagen", "Volvo"];
	
	for(var loop=0; loop<brands.length; loop++)
		$( "#brand" ).append("<option value='"+ brands[loop] +"'>"
							 	+ brands[loop] +
							 "</option>");	
} 

/**
 * Checks if we can add 1 step
 */
function stepNext() {	
	if((_step == 0 && !checkValues0()) || (_step == 0 && $( "#alert.passwordMatch" ).is(":visible")) || (_step == 0 && checkUserExists()) )
		console.log("No es posible avanzar al segundo paso");
	else if(_step == 1 && !checkValues1())
		console.log("No es posible avanzar al terccer paso");
	else
		_step++;
}

/**
 * Moves back 1 step
 */
function stepBack() {
	$( "#alert" ).hide();
	$( "#alert" ).removeClass( "passwordMatch" );
	_step--;
}

/**
 * Manages GUI according to current step of form.
 * 
 * @param {Number} step - current step
 */
function update(step){
	
	switch(step)
	{
		case 0:
			highlightStep(step);
			$( "#firstStep" ).show();
			$( "#secondStep" ).hide();
			$( "#btnBack" ).hide( "fast" );
			$( "#btnNext" ).show( "slow" );
			break;
		case 1:
			highlightStep(step);	
			$( "#mapDriver" ).hide();
			$( "#mapPedestrian" ).hide();
			$( "#firstStep" ).hide();
			$( "#secondStep" ).show();
			$( "#thirdStep" ).hide();
			$( "#btnBack" ).show( "fast" );
			$( "#btnNext" ).show( "fast" );			
			$( "#btnOK" ).hide( "fast" );
			break;
		case 2:
			highlightStep(step);	
			fillRowType();
			$( "#secondStep" ).hide();
			$( "#thirdStep" ).show();
			$( "#btnNext" ).hide( "fast" );
			$( "#btnOK" ).show( "slow" );
			break;
	}
}

/**
 * Highlights current step
 * 
 * @param {Number} step - current step
 */
function highlightStep(step){
		
	switch(step)
	{
		case 0:
			$( "#stepSignUp1" ).css('opacity', '1');
			$( "#stepSignUp2" ).css('opacity', '.2');
			$( "#stepSignUp3" ).css('opacity', '.2');
			break;
		case 1:
			$( "#stepSignUp1" ).css('opacity', '.2');
			$( "#stepSignUp2" ).css('opacity', '1');
			$( "#stepSignUp3" ).css('opacity', '.2');
			break;
		case 2:
			$( "#stepSignUp1" ).css('opacity', '.2');
			$( "#stepSignUp2" ).css('opacity', '.2');
			$( "#stepSignUp3" ).css('opacity', '1');
			break;
	}
}


/***************************************************************************************
 * CHECKERS
 ***************************************************************************************/

/**
 * Checks if values in step 1 are filled.
 * 
 * @return {Boolean} TRUE values are OK
 */
function checkValues0()
{		
	var flag = true;

	var lastElement = $( "#firstStep input" ).index( $( "#cellphone" ) );
	
	for(var loop=0; loop<lastElement+1; loop++)
		if($($( "#firstStep input" )[ loop ]).val().length == 0) {
			flag = false;
			paint($($( "#firstStep input" )[ loop ]), true);
		}
	
	if(!flag) {
		$( "#alert" ).html("<p>Los campos señalados están incompletos, debe llenarlos para proceder.");
		$( "#alert" ).show( "fast" );		
	}
	
	return flag;
}

/**
 * Checks if values in step 2 are filled.
 * 
 * @return {Boolean} TRUE values are OK
 */
function checkValues1() {
	var flag 	= true;
	var max 	= 3;
	
	//Is not a pedestrian
	if(_userType != "pedestrian") {
		max = 4;
				
		if($( "#plateLetters" ).val().length == 0) {
			flag = false;
			paint($( "#plateLetters" ), true);		
		}
		
		if($( "#plateNumbers" ).val().length == 0) {
			flag = false;
			paint($( "#plateNumbers" ), true);		
		}
		
		if($( "#model" ).val().length == 0) {
			flag = false;
			paint($( "#model" ), true);		
		}
		
		if($( "#numberSeats").val() == "0") {
			flag = false;
			paint($( "#numberSeats" ), true);
		}
	}
	
	for(var loop=0; loop<max; loop++)
		if($($( "#secondStep select" )[ loop ]).val() == "0") {
			flag = false;
			paint($($( "#secondStep select" )[ loop ]), true);
		}
	
	if($( "#street").val().length == 0) {
		flag = false;
		paint($( "#street" ), true);		
	}
	if($( "#number").val().length == 0) {
		flag = false;
		paint($( "#number" ), true);		
	}
	
	for(var loop=0; loop<3; loop++)
		if($($( "#secondStep select" )[ loop ]).val() == "0") {
			flag = false;
			paint($($( "#secondStep select" )[ loop ]), true);
		}
	
	if(!flag) {
		$( "#alert" ).html("<p>Los campos señalados están incompletos, debe llenarlos para proceder.");
		$( "#alert" ).show( "fast" );		
	}

	return flag;
}

/**
 * Checks if values in step 3 are filled.
 * 
 * @return {Boolean} TRUE values are OK
 */
function checkValues2() {	
	
	for (var d=0; d<5; d++) {
		aux = d+1;
		if($( ".chkActive"+aux ).prop("checked"))
			_days[d] = "Unsuscribed";
		else	
			if(_days[d] == null) {
				alert("Quedan dias por completar");
				return false;
			}
	}
	return true;
}

/**
 * Checks if passwords are the same
 * 
 */
function checkPassword() {
	$( "#password-first" ).on( "change", function(event) { 
	     checkPassword();
	});

	if($( "#password-first" ).val() != $( "#password-check" ).val()) {
		paint($( "#password-first" ), true);	
		paint($( "#password-check" ), true);
		$( "#alert" ).html( "<p>Las contraseñas deben coincidir" );
		$( "#alert" ).show( "fast" );	
		$( "#alert" ).addClass( 'passwordMatch' );	
		return false;
	}		
	else {
		paint($( "#password-first" ), false);	
		paint($( "#password-check" ), false);	
		$( "#alert" ).removeClass( "passwordMatch" );	
		$( "#alert" ).hide( "fast" );	
		return true;
	}
}

/**
 * Checks if element is not null and hides alerts
 * 
 * @param {element} 
 */
function checkIt(target) {
	if(!$( "#alert" ).hasClass( "passwordMatch" ))
		$( "#alert" ).hide( "fast" );		
	
	if($(target).val().length == 0 || $(target).val() == "0")
		paint($(target), true);
	else
		paint($(target), false);
}

/**
 * Paints or Unpaints element.
 * 
 * @param {Element} 
 * @param {Bool} flag - TRUE for painting element
 */
function paint($target, flag) {
	if(flag)
		$target.addClass('painted');
	else
		$target.removeClass('painted');
}

/**
 * Checks if value in element is smaller than limit
 * 
 * @param {element}
 * @param {val} Minimum limit
 */
function checkBounds(target, val) {
	if(target.value > val) {
		alert("Este campo no admite valores mayores a " + val);
		target.value = previous2;
	}
	else
		previous2 = target.value;
}

/**
 * Checks if key pressed is in the alphabet
 * 
 * @param {element}
 * @param {val} Minimum limit
 */
function checkAlphabetic(event) {
    var inputValue = event.charCode;
    if((inputValue > 47 && inputValue < 58) && (inputValue != 32)) {
        event.preventDefault();
    }
}

/**
 * Checks if value in element is numeric
 * 
 * @param {element} 
 */
function checkNumeric(target) {
	if(target.value.length > 0 && isNaN(target.value)) {						
		if(previousTarget != target)
			previous = "";
		target.value = previous;
	}
	else {
		previous = target.value;
		previousTarget = target;
	}
}

/**
 * Checks if user with a personalId was already created. Returns true if exists.
 */
function checkUserExists() {
	var pId = $( '#personalId' ).val().toString();
	var flag = false;
	
	$.ajax({
		url: "validateNewUser.do",		
		type: "GET",
		data: { personalId: pId }
		}).done(function(isValidate) {
			if(!isValidate) {
				alert("El usuario con el ID especificado ya existe");
				flag = true;
			}
		});
	
	return flag;
}

/* Disables day if unsuscribed */
function suscribes($target){
	var d = $target.parent().index() + 1;
	if($target.prop( "checked" )){
		$( "#in td:nth-child("+ d +") #btnin" ).prop( "disabled", true ).removeClass( "btnDefine" ).addClass( "unsuscribed" );
		$( "#out td:nth-child("+ d +") #btnout" ).prop( "disabled", true ).removeClass( "btnDefine" ).addClass( "unsuscribed" );
	}
	else {
		$( "#in td:nth-child("+ d +") #btnin" ).prop( "disabled", false ).removeClass( "unsuscribed" ).addClass( "btnDefine" );
		$( "#out td:nth-child("+ d +") #btnout" ).prop( "disabled", false ).removeClass( "unsuscribed" ).addClass( "btnDefine" );		
	}
}

/***************************************************************************************
 * FUNCTIONS
 ***************************************************************************************/

/**
 * Sends everything to server.
 */
function signUp() {	
	if(checkValues2()) {
		var org 		= $( "#organization" ).find("option:selected").val();
		var pId 		= $( "#personalId" ).val();
		var pw 			= $( "#password-first" ).val();
		var name		= $( "#name" ).val();
		var surname 	= $( "#surname" ).val();
		var email 		= $( "#email" ).val();
		var phone		= $( "#cellphone" ).val();
		var number		= $( "#number" ).val();
		var street		= $( "#street" ).val();
		var nbh			= $( "#neighborhood" ).val();
		var shift		= $( "#shift" ).val();
		var usType 		= $( "#userType" ).find("option:selected").val();
		var brand 		= $( "#brand" ).find("option:selected").val();
		var modelVehicle= $( "#model" ).val(); 
		var plNumb		= $( "#plateNumbers" ).val();
		var plLett		= $( "#plateLetters" ).val();
		var nSeats		= $( "#numberSeats" ).find("option:selected").val();
		
		$.post( "register.do", { "organization": org , 
								"personalId": 	pId, 
								"pw": 			pw, 
								"name": 		name, 
								"surname": 		surname, 
								"email": 		email, 									
								"phone": 		phone,  
								"street": 		street, 
								"number": 		number,
								"neighborhood": nbh,
								"shift": 		shift,
								"userType": 	usType,
								"brand": 		brand,
								"modelVehicle":	modelVehicle,
								"plateLetters": plNumb,
								"plateNumbers": plLett,
								"numberSeats": 	nSeats,
								"days":			JSON.stringify(_days)
					 			},  
				function(str) {
					 alert("Alta de Usuario Completa");
				}); 
	}
}

/**
 * Saves map previously defined.
 */
function saveMap() {
	var d 			= $( "#hdnDay" ).val();
	var io			= $( "#hdnInOut" ).val();
	var userTypeDay	= $( "#hdnUserTypeDay" ).val();
	var h 			= $( "#"+ d + io ).find("option:selected").val();		
	var nthChild 	= Number(d) + 1;
	var index 		= Number(d) - 1;
	//var applyTo		= $( "#selectApply" ).find("option:selected").val();

	if(h == "none") {
		alert("Selecciona la hora a definir");
		return false;
	}
	
	if(userTypeDay == "driver") {
		if(_matrices[index] == null)
			_matrices[index] = new Matrix();
		if(_days[index] == null)
			_days[index] = new Track();
		
		if(io == "in") {
			_matrices[index].matrixIn = gpxTrack.matrixify();				
			_days[index].trackIn = gpxTrack.myConfirm();
			if(_days[index].trackIn == "")		//Hasn't defined a route.
				return; 			
			_days[index].hourIn = h;
		}	
		else {
			_matrices[index].matrixOut = gpxTrack.matrixify();
			_days[index].trackOut = gpxTrack.myConfirm();
			if(_days[index].trackOut == "")		//Hasn't defined a route.
				return; 
			_days[index].hourOut = h;			
		}
		gpxTrack.clear();	
		$( "#"+ io + " td:nth-child("+ nthChild +")" ).find("#btn" + io).html("Modificar");
	}
	else {
		if(_lon != "" && _lat != "") {
			if(_projections[index] == null)
				_projections[index] = new Projection();
			if(_days[index] == null)
				_days[index] = new Stop();
			
			if(io == "in") {
				var lonlatCurrent 			= new OpenLayers.LonLat( _lon , _lat );
				_days[index].stopIn 		= new OpenLayers.LonLat( _lon , _lat );
				_days[index].hourIn 		= h;	
				_projections[index].projIn 	= lonlatCurrent.transform(proj4326, map.getProjectionObject());							

			}
			else {
				var lonlatCurrent 			= new OpenLayers.LonLat( _lon , _lat );
				_days[index].stopOut		= new OpenLayers.LonLat( _lon , _lat );
				_days[index].hourOut 		= h;	
				_projections[index].projOut = lonlatCurrent.transform(proj4326, map.getProjectionObject());
			}
			$( "#"+ io + " td:nth-child("+ nthChild +")" ).find("#btn" + io).html("Modificar");
		}
		else
		{
			alert("Selecciona un punto en el mapa");
			return false;
		}
	}
	
	$( ".btnDefine" ).prop( "disabled", false );
	$( "input[name='chkActive']" ).prop( "disabled", false );		
	$( "#"+ d + io ).prop( "disabled", true );	
	$( "#mapDriver" ).hide();
	$( "#mapPedestrian" ).hide();
	$( "#applyMapDefinition" ).hide();	
}

/**
 * Opens type of map according to user type in day; and shows previously saved maps.
 */
function defineMap(target) {	
	var d				= $( target ).parent().index();
	var io 				= $( target ).parent().parent().attr( "id" );
	var userTypeDay 	= $( "#userType"+ d ).find( "option:selected" ).val();				
	var index 			= Number(d) - 1;

	//No se ha seleccionado ningun tipo de usuario
	if(userTypeDay == "0") {	
		alert("Seleccione el tipo de usuario de este día");
		return false;
	}

	$( ".btnDefine" ).prop( "disabled", true );
	$( "input[name='chkActive']" ).prop( "disabled", true );	
	$( "#"+ d + io ).prop( "disabled", false );
	
	if(_userType != "driver-pedestrian")	//Si no es mixto, todos los días es el mismo usertType
		userTypeDay = _userType;
	
	/* Save in Hidden fields */
	$( "#hdnDay" ).val(d);
	$( "#hdnInOut" ).val(io);
	$( "#hdnUserTypeDay" ).val(userTypeDay);
	
	if(userTypeDay == "pedestrian") {
		$( "#mapDriver" ).hide();		
		$( "#mapPedestrian" ).show( "slow" );				
	}
	else {																	
		$( "#mapPedestrian" ).hide();	
		$( "#mapDriver" ).show( "slow" );
	}
	
	$( "#applyMapDefinition" ).show( 'fast' );				
		
	if(userTypeDay == "driver") {
		gpxTrack.clear();
		if(io == "in" && _days[index] != null)
			initMapCoords(lonlat, zoom, map, _matrices[index].matrixIn);
		if(io == "out" && _days[index] != null)
			initMapCoords(lonlat, zoom, map, _matrices[index].matrixOut);
	}
	else {
		clearMarkers();
		_lon = "";
		_lat = "";
		if(io == "in" && _days[index] != null)
			if( _days[index].stopIn != "")
				drawPreviousMarkers(_projections[index].projIn);
		
		if(io == "out" && _days[index] != null)
			if( _days[index].stopOut != "")		
				drawPreviousMarkers(_projections[index].projOut);
	}
}

/**
 * Inserts available hours in selected row.
 * 
 * @param {String} io - in/out
 */
function fillRowsInOut(io) {
	var button = "<button id='btn"+ io +"' class='btnDefine' onClick='defineMap(this); return false;'>Definir</button>";
		
	for(var d=1;d<6;d++) {
		var select 	= "<select id='"+ d + io + "'></select>"; 

		$( "#tableSignUp #"+ io ).append("<td>"+ select + button + "</td>");
		$( "#"+ d + io ).append("<option value='none'>Seleccionar</option>");					
		
		//Hours
		for(var j=0;j<24;j++) { 
			$( "#"+ d + io ).append("<option value='"+j+":00'>"+j+":00 hs</option>");			
			$( "#"+ d + io ).append("<option value='"+j+":30'>"+j+":30 hs</option>");
		}
		
		$( "#"+ d + io ).prop( "disabled", true );		/* Disable hour selects -> Enabled when defining map */
	}
}

/**
 * Inserts type of user in first row
 */
function fillRowType() {
	$( "#tableSignUp #userTypeRow" ).html("<td>	Tipo de Usuario </td>");
	
	for(var d=1; d<6; d++) {
		var content = "";
		if(_userType == "driver-pedestrian") {
			//Select for user type each day
			content = 	"<select id='userType"+ d +"'>"						+
							"<option value='0'>Seleccionar</option>" 		+
							"<option value='pedestrian'>Peaton</option>" 	+	
							"<option value='driver'>Conductor</option>" 	+
						"</select>"; 
		}
		else if(_userType == "driver") {
			$( "#hdnUserTypeDay" ).val(_userType);
			content =	"Conductor";
		}
		else {
			//_userType == "pedestrian"
			$( "#hdnUserTypeDay" ).val(_userType);			
			content =	"Peaton";
		}
		content += 	"<br><br>";
		content += 	"<input type='checkbox' name='chkActive' class='chkActive"+ d +"'" +
				 		"onclick='suscribes($(this));'>"+ $( "#lblActive" ).val();
		$( "#tableSignUp #userTypeRow" ).append("<td>"+ content +"</td>");			 
	}
}

/**
 * Checks whether or not showing the drivers data, and sets global variable.
 * 
 * @param {Element} target
 */
function userTypeChanged(target) {
	_userType = $(target).find("option:selected").val();
		
	if(_userType == "pedestrian" || _userType == "0")
		$("#drives").hide( 'slow' );
	else
		$("#drives").show( 'slow' );
}


/***************************************************************************************
 * CONSTRUCTORS
 ***************************************************************************************/

/**
 * Constructor
 */
function Track() { 
	this.isPedestrian 	= false;
	this.trackIn		= "";
	this.hourIn			= "";	
	this.trackOut		= "";	
	this.hourOut		= "";		
}

/**
 * Constructor
 */
function Stop() {
	this.isPedestrian 	= true;
	this.stopIn			= "";
	this.hourIn			= "";	
	this.stopOut		= "";
	this.hourOut		= "";		
}


/**
 * Constructor
 */
function Matrix() {
	this.matrixIn	= "";
	this.matrixOut	= "";			
}

/**
 * Constructor
 */
function Projection() {
	this.projIn		= "";
	this.projOut	= "";			
}




