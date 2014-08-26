<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
		<link 	type="text/css"				rel="stylesheet" href="resources/styles/profile.css">
		<script src="resources/scripts/jquery-1.10.2.js">	</script>
		<script>
			$(document).ready(function() {
				$( "input[name='rating']" ).change(function() {
					$('.choice').html(this.value);
					$('#val-rate').val(this.value);				
				});
				
				$(".btn-rate").click(function(){
					// TODO: Post method that sends valueRate
					// Need to specify if this is a driver or ped.
					// Shouldn't rate if it's us.
					var stars = $('#val-rate').val();
					var userType;
					var id = window.parent.document.getElementById("val-id").value;
					
					if(window.parent.document.getElementById("val-pedestrian").value)
						userType = "0";	//Pedestrian
					else
						userType = "1";	//Driver

					$.post( 'updateRating.do', 
						{
							userId: id, 
							prof: 	userType, 
							rating: stars 
						}, 
						function(msg) {
							alert(msg);
						});
				});	
			});

		</script>		
	</head>
	
	<body>
	 
		<div class="rating" id="wrapper-rating"> 	<!-- Puntuacion	-->	
			<h2>Puntuación de Usuarios</h2>
			
			<!-- TODO MESSAGE -->
			<p> Seleccione la puntuación que otorgará al usuario con el que está asociado. </p>
			<span class="star-rating">
				<input type="radio" name="rating" value="1"><i></i>
				<input type="radio" name="rating" value="2"><i></i>
				<input type="radio" name="rating" value="3"><i></i>
				<input type="radio" name="rating" value="4"><i></i>
				<input type="radio" name="rating" value="5"><i></i>
			</span>
			<br>
			<p>
			
			Has asignado <strong class="choice"></strong> estrellas.
			
			<p>			
			<input type="hidden" id="val-rate"/>	
			<input type="button" class="btn btn-rate" value="Aceptar"/>							
		</div>
	
	</body>
	

</html>