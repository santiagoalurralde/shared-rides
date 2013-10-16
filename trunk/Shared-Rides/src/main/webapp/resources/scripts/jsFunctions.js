
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
	initMap();
});

$( document ).ready(function() {
	//Esconder el boton Anterior y el Mapa
	$( "#butBack" ).hide( 0 );
	$( "#mapWrap" ).hide( 0 );
	$( "#listFound" ).hide( 0 );

	
	//Acciones al presionar las imagenes
	$( "#imgSun" ).click(function(){
		shiftJs	= 1;
		$( this ).css('opacity', '1');
		$( "#imgMoon" ).css('opacity', '0.05');
	});
	$( "#imgMoon" ).click(function(){
		shiftJs 	= 2;
		$( this ).css('opacity', '1');
		$( "#imgSun" ).css('opacity', '0.05');
	});
	$( "#imgBoot" ).click(function(){
		userJs 	= 1;
		$( this ).css('opacity', '1');
		$( "#imgSteering" ).css('opacity', '0.05');
	});
	$( "#imgSteering" ).click(function(){
		userJs 	= 2;
		$( this ).css('opacity', '1');
		$( "#imgBoot" ).css('opacity', '0.05');
	});

	//Acciones al presionar Siguiente
	$( "#butNext" ).click(function(){
		switch(i)
		{
		case 1:		//De TipoUsuario a Turno
			$( this ).css('marginLeft', '60px');
			$( "#imgBoot" ).hide( 0 );
			$( "#imgSteering" ).hide( 0 );
			$( "#imgSun" ).show( 'fast' );
			$( "#imgMoon" ).show( 'fast' );
			
			$( "#butBack" ).show( 'fast' );	
			break;
		case 2:		//De Turno a Mapa
			$( "#imgSun" ).hide( 0 );
			$( "#imgMoon" ).hide( 0 );
			$( "#mapWrap" ).show( 'slow' );
			
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
		case 0:		//De Turno a TipoUsuario
			$( "#imgSun" ).hide( 0 );
			$( "#imgMoon" ).hide( 0 );
			$( "#imgBoot" ).show( 'fast' );
			$( "#imgSteering" ).show( 'fast' );

			$( this ).hide( 'fast' );
			$( "#butNext" ).css('marginLeft', '0px');
			break;
		case 1:		//De Mapa a Turno
			$( "#mapWrap" ).hide( 0 );
			$( "#imgSun" ).show( 'fast' );
			$( "#imgMoon" ).show( 'fast' );
			
			$( "#butOK" ).hide( 'fast' );
			$( "#butNext" ).show( 'slow' );
			break;
		}
	});
	
	/*
	$( "#updateForm" ).submit(function(){
		var $form = $( this ),
			url = $form.attr( "action" );
		
		$.post( url, { user: userJs, shift: shiftJs, lon: lonJs, lat: latJs} );
	});
	*/
	
	$( "#butOK" ).click(function(){
		$( "#mapWrap" ).hide( 0 );
		
		$.getJSON( "/Shared-Rides/prueba.do", function( json ) {
			$.each(json, function(i, data){
				$( "#tableFound" ).append("<tr><td>" + data.name + "</td><td>" + data.surname + "</td><td><a href='/profile/"+ data.id +"'><img src='" + data.pic + "'/></a></td></tr>");
			});
		});
		
		$( "#listFound" ).show( 'fast' );
		$( "#butOK"	).hide( 'fast' );
	});
	
});




