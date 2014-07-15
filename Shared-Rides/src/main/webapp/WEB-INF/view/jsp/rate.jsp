<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
		<link 	type="text/css"				rel="stylesheet" href="resources/styles/profile.css">
	</head>
	
	<body>
	 
		<div style="margin: 5% 0 8% 0"> 	<!-- Puntuacion	-->	
		
			${ratingDriver}	
			<span class="star-rating">
				<input type="radio" name="rating" value="1"><i></i>
				<input type="radio" name="rating" value="2"><i></i>
				<input type="radio" name="rating" value="3"><i></i>
				<input type="radio" name="rating" value="4"><i></i>
				<input type="radio" name="rating" value="5"><i></i>
				<input type="radio" name="rating" value="6"><i></i>
				<input type="radio" name="rating" value="7"><i></i>
				<input type="radio" name="rating" value="8"><i></i>
				<input type="radio" name="rating" value="9"><i></i>
				<input type="radio" name="rating" value="10"><i></i>
			</span>
			
			<strong class="choice"></strong>
						
			<input type="hidden" id="ratingValue" />								
		</div>
	
	</body>
</html>