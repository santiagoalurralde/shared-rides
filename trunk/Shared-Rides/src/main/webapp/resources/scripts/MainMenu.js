
var i 		=  -1; 	//Contador de Pasos
var userJs	= 	0;	//Tipo de Usuario
var shiftJs	= 	0;	//Turno

/**
 * Checks if we can add 1 step
 */
function stepNext() {
	if(i===0 && userJs===0)
		alert("Seleccione un tipo de Usuario!");
	else if(i===1 && shiftJs===0)
		alert("Seleccione un turno!");
	else
		i++;
}

/**
 * Moves back 1 step
 */
function stepBack() {
	i--;
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
	$( "#btnNext" ).click(function(){
		update(i);
	});
	
	//Acciones al presionar Anterior
	$( "#btnBack" ).click(function(){
		update(i);
	});
	
	function start(){
		//	Establece las propiedades iniciales
				
		$( "#mapDriver" ).css( 'display', 'none' );
		$( "#mapPedestrian" ).css( 'display', 'none' );
		$( "#btnBack" ).hide( 0 );							
		$( "#listFound" ).hide( 0 );
	} 
		
	$( "#btnOK" ).click(function(){
		var coordsJs;				//Datos de coordenadas
		
		if(userJs === 2)
			coordsJs = "[{lon=" + lonJs.toString() + " , lat=" + latJs.toString() + "}]";
		else
			coordsJs = gpxTrack.confirm();
				
		$.post( "find.do", { "user": userJs , "shift": shiftJs, "mapData": coordsJs }, 
				function(json)
				{
					var jsonNew = $.parseJSON(json);
					$.each(jsonNew, function(i, data){
						var distance = Math.ceil(data.distance);
						$( "#tableFound" ).append("<tr><td>" + data.name + " " + data.surname + "</td><td><a href='/Shared-Rides/profile.do?user="+ data.id +"'><img src='resources/profilePic/" + data.picture + "'/></a></td><td>A "+ distance +" cuadras aproximadamente</td></tr>");
					});
				}); 
		
		//Traer la lista
		$( "#mapDriver" ).css('display', 'none');
		$( "#mapPedestrian" ).css('display', 'none');
		
		$( "#listFound" ).show( 'fast' );
		$( "#btnOK"	).hide( 'fast' );
	});
	
});

function update(step)
{
	switch(step)
	{
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
			
			if(userJs === 2)
				$( "#mapPedestrian" ).show( 'slow' );
			else
				$( "#mapDriver" ).show( 'slow' );
		
			$( "#btnNext" ).hide();
			$( "#btnOK" ).css('marginLeft', '60px');
			$( "#btnOK" ).show( 'slow' );			
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


