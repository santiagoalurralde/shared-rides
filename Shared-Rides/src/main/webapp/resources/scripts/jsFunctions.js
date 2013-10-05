
var i 		= -1; 	//Contador de Pasos
var user	= 0;	//Tipo de Usuario
var shift	= 0;	//Turno

function stepsUpdate(value) {
	if(i===0 && user===0)
		alert("Seleccione un tipo de Usuario!");
	else if(i===1 && shift===0)
		alert("Seleccione un turno!");
	else
		i = i + value;
}


	
function printResults(){
	console.log("shift: ", shift);
	console.log("user: ", user);
}

$( document ).ready(function() {
	initMap();
	
	//Esconder el boton Anterior y el Mapa
	$( "#butBack" ).hide( 0 );
	$( "#mapWrap" ).hide( 0 );

	//Acciones al presionar Siguiente
	$( "#butNext" ).click(function(){
		switch(i)
		{
		case 1:		//De TipoUsuario a Turno
			$( this ).css('marginLeft', '60px');
			$( "#imgBoot" ).hide( 'fast' );
			$( "#imgSteering" ).hide( 'fast' );
			$( "#imgSun" ).show( 'fast' );
			$( "#imgMoon" ).show( 'fast' );
			$( "#butBack" ).show( 'slow' );	
			break;
		case 2:		//De Turno a Mapa
			$( "#imgSun" ).hide( 'fast' );
			$( "#imgMoon" ).hide( 'fast' );
			$( "#mapWrap" ).show( 'slow' );
			$( this ).hide('fast');
			$( "#butOK" ).css('marginLeft', '60px');
			$( "#butOK" ).show(' fast ');
			break;	
		}
	});
	
	//Acciones al presionar Anterior
	$( "#butBack" ).click(function(){
		switch(i)
		{
		case 1:		//De Mapa a Turno
			$( "#mapWrap" ).hide( 0 );
			$( "#imgSun" ).show( "slow" );
			$( "#imgMoon" ).show( "slow" );
			$( "#butOK" ).hide( 'slow' );
			$( "#butNext" ).show( "slow" );
			break;
		case 0:		//De Turno a TipoUsuario
			$( "#imgSun" ).hide( 0 );
			$( "#imgMoon" ).hide( 0 );
			$( "#imgBoot" ).show( 'fast' );
			$( "#imgSteering" ).show( 'fast' );

			$( this ).hide( 'slow' );
			$( "#butNext" ).css('marginLeft', '0px');
			break;
		}
	});
	

	$( "#imgSun" ).click(function(){
		shift	= 1;
	});
	$( "#imgMoon" ).click(function(){
		shift 	= 2;
	});
	$( "#imgBoot" ).click(function(){
		user 	= 1;
	});
	$( "#imgSteering" ).click(function(){
		user 	= 2;
	});
});




/*
 */


