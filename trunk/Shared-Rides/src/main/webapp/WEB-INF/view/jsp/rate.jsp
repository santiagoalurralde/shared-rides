<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link type="text/css" rel="stylesheet" href="resources/styles/profile.css">	
	</head>
	
	<body>
	 	<!-----------------------------------------
	 	[Rating]
	 	------------------------------------------>
		<div class="rating" id="wrapper-rating"> 	

			<h2> <spring:message code="lbl.rate"/> </h2>
			
			<p>	<spring:message code="lbl.help-rate"/> </p>
			<span class="star-rating">
				<input type="radio" name="rating" value="1"><i></i>
				<input type="radio" name="rating" value="2"><i></i>
				<input type="radio" name="rating" value="3"><i></i>
				<input type="radio" name="rating" value="4"><i></i>
				<input type="radio" name="rating" value="5"><i></i>
			</span>
			<br>

			<p>
			<spring:message code="lbl.help-assigned"/> 
			<strong class="choice"></strong>
			<spring:message code="lbl.help-stars"/>
			
			<p>			
			<input type="hidden" id="val-rate"/>	
			<input type="button" class="btn btn-rate" value="<spring:message code="lbl.button-accept"/>"/>							
		</div>
	

		<script src="resources/scripts/jquery-1.10.2.js"></script>
		<script>
			(function(){
				$("input[name='rating']").change(function() {
					$(".choice").html(this.value);
					$("#val-rate").val(this.value);				
				});
				
				$(".btn-rate").click(function(){
					var stars = $("#val-rate").val(),
						id = window.parent.document.getElementById("val-id").value,
						userType;

					userType = (window.parent.document.getElementById("val-pedestrian").value == "true") ? "0" : "1";
					//0 - Pedestrian
					//1 - Driver

					$.post("updateRating.do", 
						{
							userId	: id, 
							prof 	: userType, 
							rating 	: stars 
						}, 
						function(msg) {
							alert(msg);
					});
				});	
			})();
		</script>	
	</body>
</html>