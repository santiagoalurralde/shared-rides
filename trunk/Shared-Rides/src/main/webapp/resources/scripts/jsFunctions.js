
var i 		= -1; 	//Contador de Pasos
var userJs	= 0;	//Tipo de Usuario
var shiftJs	= 0;	//Turno

function stepsUpdate(value) {
	if(value === 1)
		if(i===0 && userJs===0)
			alert("Seleccione un tipo de Usuario!");
		else if(i===1 && shiftJs===0)
			alert("Seleccione un turno!");
		else
			i = i + value;
	else
		i = i + value;
}

$( document ).ready(function() {
	
	//Iniciar Mapa Simple
	initMap();

	//Esconder elementos
	start();

	//Resaltar Primer Paso
	highlightStep(i);
	
	//Acciones al presionar las imagenes
	$( "#imgSun" ).click(function(){
		shiftJs		= 1;
		$( "#imgSun" ).css('opacity', '1');
		$( "#imgMoon" ).css('opacity', '0.05');
	});
	$( "#imgMoon" ).click(function(){
		shiftJs 	= 2;
		$( this ).css('opacity', '1');
		$( "#imgSun" ).css('opacity', '0.05');
	});
	$( "#imgBoot" ).click(function(){
		userJs		= 1;
		$( this ).css('opacity', '1');
		$( "#imgSteering" ).css('opacity', '0.05');
	});
	$( "#imgSteering" ).click(function(){
		userJs		= 2;
		$( this ).css('opacity', '1');
		$( "#imgBoot" ).css('opacity', '0.05');
	});

	//Acciones al presionar Siguiente
	$( "#butNext" ).click(function(){
		switch(i)
		{
		case 1:
			//	De TipoUsuario a Turno
			
			$( this ).css('marginLeft', '60px');
			
			highlightStep(i);
			nextStep(i);
			
			$( "#butBack" ).show( 'fast' );	
			break;
		case 2:		
			//	De Turno a Mapa
			
			highlightStep(i);
			nextStep(i);
			
			if(userJs === 2)
				$( "#mapDriver" ).show( 'slow' );
			else
				$( "#mapPedestrian" ).show( 'slow' );
		
			$( this ).hide( 0 );
			$( "#butOK" ).css('marginLeft', '60px');
			$( "#butOK" ).show( 'slow' );
			break;	
		}
	});
	
	//Acciones al presionar Anterior
	$( "#butBack" ).click(function(){
		switch(i)
		{
		case 0:		
			//	De Turno a TipoUsuario
			
			highlightStep(i);
			backStep(i);

			$( this ).hide( 'fast' );
			$( "#butNext" ).css('marginLeft', '0px');
			break;
		case 1:		
			//	De Mapa a Turno
			
			highlightStep(i);
			backStep(i);
			
			$( "#butOK" ).hide( 'fast' );
			$( "#butNext" ).show( 'slow' );
			break;
		}
	});
	
	function start(){
		//	Establece las propiedades iniciales
		
		$( "#mapDriver" ).css('display', 'none');
		$( "#mapPedestrian" ).css('display', 'none');
		$( "#butBack" ).hide( 0 );							
		$( "#listFound" ).hide( 0 );
	} 
	
	
	function nextStep(step){
		//	Inserta elementos del paso actual 
		//	del formulario al avanzar
		//	step: numero de paso actual.
		
		switch(step)
		{
		case 1:
			$( "#imgBoot" ).hide( 0 );
			$( "#imgSteering" ).hide( 0 );
			$( "#imgSun" ).show( 'fast' );
			$( "#imgMoon" ).show( 'fast' );
			break;
		case 2:
			$( "#imgSun" ).hide( 0 );
			$( "#imgMoon" ).hide( 0 );
			break;
		}
	}
	
	
	function backStep(step){
		//	Inserta elementos del paso actual 
		//	del formulario al retroceder
		//	step: numero de paso actual.
		
		switch(step)
		{
		case 0:
			$( "#imgSun" ).hide( 0 );
			$( "#imgMoon" ).hide( 0 );
			$( "#imgBoot" ).show( 'fast' );
			$( "#imgSteering" ).show( 'fast' );
			break;
		case 1:
			$( "#mapDriver" ).hide( 0 );
			$( "#mapPedestrian" ).hide( 0 );
			$( "#imgSun" ).show( 'fast' );
			$( "#imgMoon" ).show( 'fast' );
			break;
		}
	}

	
	function highlightStep(step){
		//	Resalta el paso actual del formulario
		//	step: numero de paso actual.
			
		switch(step)
		{
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
	
	
	$( "#butOK" ).click(function(){
		var mapDataJs;
		
		if(userJs === 2)
			mapDataJs = gpxTrack.confirm();
		else
			mapDataJs = "{" + lonJs.toString() + " " + latJs.toString() + "}";
		
		$.post( "find.do", { "user": userJs , "shift": shiftJs, "mapData": mapDataJs } );
	});
		
});



//CODIGO VIEJO

/*
$( "#updateForm" ).submit(function(e){
	e.preventDefault();
	var $form = $( this ),
		url = $form.attr( "action" );
	
		
	
	$.post( url, { user: userJs, shift: shiftJs, lon: lonJs, lat: latJs} );
});



function fillData(){
	alert("filled");
	document.getElementById("dataSent").innerHTML = 
		"<input type='hidden' id='userData' 	value='"	+ userJs 	+ 		"'/> " +
		"<input type='hidden' id='shiftData'	value='"	+ shiftJs 	+ 		"'/> " ;	
}



$( "#butOK" ).click(function(){
	$( "#mapDriver" ).hide( 0 );
	
	$.getJSON( "/Shared-Rides/prueba.do", function( json ) {
		$.each(json, function(i, data){
			$( "#tableFound" ).append("<tr><td>" + data.name + "</td><td>" + data.surname + "</td><td><a href='/profile/"+ data.id +"'><img src='" + data.pic + "'/></a></td></tr>");
		});
	});
	
	$( "#listFound" ).show( 'fast' );
	$( "#butOK"	).hide( 'fast' );
});
*/

