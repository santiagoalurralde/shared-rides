var i 				= -1; 	//Contador de Pasos
var previous       	= "";
var previous2      	= "";
var previousTarget 	= "";
var _userType;
var _tracks			= new Array();
var _markers		= new Array();
var _matrices		= new Array();


for (var loop=0; loop<5; loop++)
{
    _tracks.push(null);
    _markers.push(null);
    _matrices.push(null);    
}

/***************************************************************************************
 ***************************************************************************************/

/**
 * Checks if we can add 1 step
 */
function stepNext() {
	if((i == 0 && !checkValues0()) || (i == 0 && $( "#alert.passwordMatch" ).is(":visible")))
		console.log("mal");
	else if(i == 1 && !checkValues1())
		console.log("mal");
	else
		i++;
}

/**
 * Moves back 1 step
 */
function stepBack() {
	$( "#alert" ).hide();
	$( "#alert" ).removeClass("passwordMatch");
	i--;
}

/**
 * Checks if values in step 1 are filled.
 * 
 * @return {Boolean} TRUE values are OK
 */
function checkValues0()
{		
	var flag = true;
	
	var lastElement = $( "#firstStep input" ).index( $("#cellphone") );
	
	for(var i=0;i<lastElement+1;i++)
		if($($( "#firstStep input" )[ i ]).val().length == 0)
		{
			flag = false;
			paint($($( "#firstStep input" )[ i ]), true);
		}
	
	if(!flag)
	{
		$( "#alert" ).html("<p>Los campos señalados están incompletos, debe llenarlos para proceder.");
		$( "#alert" ).show( 'fast' );		
	}

	return flag;
}

/**
 * Checks if values in step 2 are filled.
 * 
 * @return {Boolean} TRUE values are OK
 */
function checkValues1()
{
	var flag 	= true;
	var max 	= 3;
	
	if(_userType != "pedestrian")		//Is not a pedestrian
	{
		max = 4;
				
		if($( "#plateLetters").val().length == 0)
		{
			flag = false;
			paint($( "#plateLetters" ), true);		
		}
		
		if($( "#plateNumbers").val().length == 0)
		{
			flag = false;
			paint($( "#plateNumbers" ), true);		
		}
		
		if($( "#model").val().length == 0)
		{
			flag = false;
			paint($( "#model" ), true);		
		}
		
		if($( "#numberSeats").val() == "0")
		{
			flag = false;
			paint($( "#numberSeats"), true);
		}
	}
	
	for(var i=0;i<max;i++)
		if($($( "#secondStep select" )[ i ]).val() == "0")
		{
			flag = false;
			paint($($( "#secondStep select" )[ i ]), true);
		}
	
	if($( "#street").val().length == 0)
	{
		flag = false;
		paint($( "#street" ), true);		
	}
	if($( "#number").val().length == 0)
	{
		flag = false;
		paint($( "#number" ), true);		
	}
	
	for(var i=0;i<3;i++)
		if($($( "#secondStep select" )[ i ]).val() == "0")
		{
			flag = false;
			paint($($( "#secondStep select" )[ i ]), true);
		}
	
	if(!flag)
	{
		$( "#alert" ).html("<p>Los campos señalados están incompletos, debe llenarlos para proceder.");
		$( "#alert" ).show( 'fast' );		
	}

	return flag;
}

/**
 * Checks if values in step 3 are filled.
 * 
 * @return {Boolean} TRUE values are OK
 */
function checkValues2()
{
}

/**
 * Checks if passwords are the same
 * 
 */
function checkPassword(){
	$( "#password-first" ).on("change", function(event) { 
	     checkPassword();
	});

	if($( "#password-first" ).val() != $( "#password-check" ).val())
	{
		paint($( "#password-first" ), true);	
		paint($( "#password-check" ), true);
		$( "#alert" ).html("<p>Las contraseñas deben coincidir");
		$( "#alert" ).show( 'fast' );	
		$( "#alert" ).addClass( 'passwordMatch' );	
		return false;
	}		
	else
	{
		paint($( "#password-first" ), false);	
		paint($( "#password-check" ), false);	
		$( "#alert" ).removeClass( 'passwordMatch' );	
		$( "#alert" ).hide( 'fast' );	
		return true;
	}
}

/**
 * Checks if element is not null and hides alerts
 * 
 * @param {element} 
 */
function checkIt(target){
	if(!$( "#alert" ).hasClass( 'passwordMatch' ))
		$( "#alert" ).hide( 'fast' );		
	
	if($(target).val().length == 0 || $(target).val() == "0")
		paint($(target), true);
	else
		paint($(target), false);
}

/**
 * Paints element or not.
 * 
 * @param {Element} 
 * @param {Bool} flag - TRUE for painting element
 */
function paint($target, flag)
{
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
function checkBounds(target, val)
{
	if(target.value > val)
	{
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
function checkAlphabetic(event)
{
    var inputValue = event.charCode;
    if((inputValue > 47 && inputValue < 58) && (inputValue != 32))
    {
        event.preventDefault();
    }
}

/**
 * Checks if value in element is numeric
 * 
 * @param {element} 
 */
function checkNumeric(target)
{
	if(target.value.length > 0 && isNaN(target.value))
	{
		//alert("Este campo no admite letras, sólo números");
		if(previousTarget != target)
			previous = "";
		target.value = previous;
	}
	else
	{
		previous = target.value;
		previousTarget = target;
	}
}

/***************************************************************************************
 ***************************************************************************************/

$( document ).ready(function() {

	//Iniciar Mapa Simple
	initMap(); 
	
	//Esconder elementos
	start();

	//Acciones al presionar Siguiente
	$( "#btnNext" ).click(function(){
		update(i);
	});
	
	//Acciones al presionar Anterior
	$( "#btnBack" ).click(function(){
		update(i);
	});  
	
	$( "#btnMap" ).click(function(){
		var d 			= $( "#hdnDay" ).val();
		var io			= $( "#hdnInOut" ).val();
		var userTypeDay	= $( "#hdnUserTypeDay" ).val();
		var applyTo		= $( "#selectApply" ).find("option:selected").val();
		
		//alert(userTypeDay + " " + d + " " + io);
		
		//REVISAR
		if(_matrices[d] == null)
			_matrices[d] = new Matrix();
		if(_tracks[d] == null)
			_tracks[d] = new Track();
		if(_markers[d] == null)
			_markers[d] = new Marker();
		
		
		if(userTypeDay == "driver")  
		{
			if(io == "in"){
				_matrices[d].matrixIn = gpxTrack.matrixify();				
				_tracks[d].trackIn = gpxTrack.confirm();
			}	
			else{
				_matrices[d].matrixOut = gpxTrack.matrixify();
				_tracks[d].trackOut = gpxTrack.confirm();
			}
			gpxTrack.clear();		
		}
		else
		{
			/*
			if(io == "in"){
				_markers[d].markerIn;
				alert(_markers[d].markerIn);
			}
			else{
				_markers[d].markerOut;
				alert(_markers[d].markerOut);
			}*/
		}
			
		$( "#mapDriver" ).hide();
		$( "#mapPedestrian" ).hide();
	});
});

/**
 * Inserts available hours in selected row.
 * 
 * @param {String} type - in
 */
function defineMap(target)
{	
	var d				= $( target ).parent().index();
	var io 				= $( target ).parent().parent().attr('id');
	var userTypeDay 	= $( "#userType"+ d ).find("option:selected").val();
	var selectCommon	= 	'<option value="0" selected></option>' +
           				    '<option value="onlythis">Sólo la hora seleccionada</option>';
	
	if(_userType != "driver-pedestrian")
		userTypeDay = _userType;
	
	$( "#hdnDay" ).val(d);
	$( "#hdnInOut" ).val(io);
	$( "#hdnUserTypeDay" ).val(userTypeDay);
	
	if(userTypeDay == "pedestrian")
	{
		$( "#mapDriver" ).hide();		
		$( "#mapPedestrian" ).show( 'slow' );
		$( "#selectApply" ).html(	selectCommon +
									'<option value="allOut">Todas las salidas como peaton</option>'+
									'<option value="allIn">Todas las entradas como peaton</option>'+
									'<option value="allSchedule">Todo el horario</option>');	
	}
	else
	{
		$( "#mapPedestrian" ).hide();
		$( "#mapDriver" ).show( 'slow' );
		$( "#selectApply" ).html(	selectCommon +
									'<option value="allOut">Todas las salidas como peaton</option>'+
									'<option value="allIn">Todas las entradas como peaton</option>');		
	}
	
	$( "#applyMapDefinition" ).show( 'fast' );	
		
	if(userTypeDay == "driver")  
	{
		gpxTrack.clear();
		if(io == "in" && _tracks[d] != null)
			initMapCoords(lonlat, zoom, map, _matrices[d].matrixIn);
		if(io == "out" && _tracks[d] != null)
			initMapCoords(lonlat, zoom, map, _matrices[d].matrixOut);
	}
	else
	{

	}
}

/**
 * Establishes the starting environment
 */
function start()
{
	$( "#mapDriver" ).css( 'display', 'none' );
	$( "#mapPedestrian" ).css( 'display', 'none' );
	
	highlightStep(0);
	
	$( "#btnBack" ).hide( 0 );		
	
	fillRowsInOut("in");		
	fillRowsInOut("out");		
} 

/**
 * Inserts available hours in selected row.
 * 
 * @param {String} type - in
 */
function fillRowsInOut(type){
	var button = "<button id='btn"+ type +"' onClick='defineMap(this); return false;'>Definir</button>";;
		
	for(var i=1;i<6;i++)
	{
		//Select for hours
		
		var select 	= "<select id='"+ i + type + "'></select>"; 

		$( "#tableSignUp #"+ type ).append("<td>"+ select + button + "</td>");
		
		for(var j=0;j<24;j++)
		{
			$( "#"+ i + type ).append("<option value='"+j+":00'>"+j+":00 hs</option>");			
			$( "#"+ i + type ).append("<option value='"+j+":30'>"+j+":30 hs</option>");
		}
	}
}

/**
 * Inserts type of user in first row
 * 
 */
function fillRowType()
{
	$( "#tableSignUp #userTypeRow" ).html("<td>	Tipo de Usuario </td>");
	
	for(var i=1; i<6; i++)
	{
		var content = "";
		if(_userType == "driver-pedestrian")
		{
			//Select for user type each day
			
			content = 	"<select id='userType"+ i +"'>"						+
							"<option value='0'>Seleccionar</option>" 		+
							"<option value='pedestrian'>Peaton</option>" 	+	
							"<option value='driver'>Conductor</option>" 	+
						"</select>"; 
		}
		else if(_userType == "driver")
		{
			$( "#hdnUserTypeDay" ).val(_userType);
			content = "Conductor";
		}
		else //_userType == "pedestrian"
		{
			$( "#hdnUserTypeDay" ).val(_userType);			
			content = "Peaton";
		}
		$( "#tableSignUp #userTypeRow" ).append("<td>"+ content +"</td>");			 
	}
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
			$( "#btnBack" ).hide( 'fast' );
			$( "#btnNext" ).show( 'slow' );
			break;
		case 1:
			highlightStep(step);	
			$( "#mapDriver" ).hide();
			$( "#mapPedestrian" ).hide();
			$( "#firstStep" ).hide();
			$( "#secondStep" ).show();
			$( "#thirdStep" ).hide();
			$( "#btnBack" ).show( 'fast' );
			$( "#btnNext" ).show( 'fast' );			
			$( "#btnOK" ).hide( 'fast' );
			break;
		case 2:
			highlightStep(step);	
			fillRowType();
			$( "#secondStep" ).hide();
			$( "#thirdStep" ).show();
			
			if(_userType == "pedestrian")
			{
				$( "#mapPedestrian" ).show( 'slow' );
				$( "#btnMap" ).show( 'fast' );
			}
			if(_userType == "driver")
			{
				$( "#mapDriver" ).show( 'slow' );				
				$( "#btnMap" ).show( 'fast' );				
			}
			$( "#btnNext" ).hide( 'fast' );
			$( "#btnOK" ).show( 'slow' );
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
			$( "#stepSignUp2" ).css('opacity', '0.2');
			$( "#stepSignUp3" ).css('opacity', '0.2');
			break;
		case 1:
			$( "#stepSignUp1" ).css('opacity', '0.2');
			$( "#stepSignUp2" ).css('opacity', '1');
			$( "#stepSignUp3" ).css('opacity', '0.2');
			break;
		case 2:
			$( "#stepSignUp1" ).css('opacity', '0.2');
			$( "#stepSignUp2" ).css('opacity', '0.2');
			$( "#stepSignUp3" ).css('opacity', '1');
			break;
	}
}

/**
 * Checks whether or not showing the drivers data, and sets global variable.
 * 
 * @param {Element} target
 */
function userTypeChanged(target)
{
	_userType = $(target).find("option:selected").val();
		
	if(_userType == "pedestrian" || _userType == "0")
		$("#drives").hide( 'slow' );
	else
		$("#drives").show( 'slow' );
}

function Track()
{ 
	// Constructor

	this.trackIn	= "";
	this.trackOut	= "";		
}

function Marker()
{
	// Constructor

	this.markerIn	= "";
	this.markerOut	= "";			
}

function Matrix()
{
	// Constructor

	this.matrixIn	= "";
	this.matrixOut	= "";			
}

