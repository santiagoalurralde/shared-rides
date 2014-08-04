<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
		<link 	type="text/css"				rel="stylesheet" href="resources/styles/profile.css">
		<script src="resources/scripts/jquery-1.10.2.js">	</script>
		<script>
			$(document).ready(function(){
				$( "input[name='rating']" ).change(function(){
					$('.choice').html( this.value );
					$('#valRate').val( this.value );				
				});
				
				$( '#btnRate' ).click(function(){
					// TODO: Post method that sends valueRate
					// Need to specify if this is a driver or ped.
					// Shouldn't rate if it's us.
					var stars = $('#valRate').val();
					var userType;
					var id = window.parent.document.getElementById("valId").value;
					
					if(window.parent.document.getElementById("valPedestrian").value)
						userType = "0";	//Pedestrian
					else
						userType = "1";	//Driver

					$.post( 'updateRating.do', { userId: id, prof: userType, rating: stars }, 
						function(msg)
						{
							alert(msg);
						});
				});	
			});

		</script>		
	</head>
	
	<body>
	 
		<div class="theRating" id="wrapperRate"> 	<!-- Puntuacion	-->	
			<h2>Puntuación de Usuarios</h2>
			
			<p>
			Seleccione la puntuación que otorgará al usuario con el que está asociado.
			
			<p>
			<span class="star-rating">
				<input type="radio" name="rating" value="1"><i></i>
				<input type="radio" name="rating" value="2"><i></i>
				<input type="radio" name="rating" value="3"><i></i>
				<input type="radio" name="rating" value="4"><i></i>
				<input type="radio" name="rating" value="5"><i></i>
			</span>
			<!-- 
			@RequestMapping(value = "/updateRating.do", method = RequestMethod.POST)
				public @ResponseBody String updateRating(@RequestParam("userId") long userId,
										@RequestParam("prof") int profile,
										@RequestParam("rating") int rating,
										HttpServletRequest request){
			 -->
			<br>
			<p>
			
			Has asignado <strong class="choice"></strong> estrellas.
			
			<p>			
			<input type="hidden" id="valRate" />	
			<input type="button" class="btn" id="btnRate" value="Aceptar"/>							
		</div>
	
	</body>
	

</html>