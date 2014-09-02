<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 
<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html> 
	<head> 
		<!-----------------------------------------
		[Specific]
		------------------------------------------>
		<meta http-equiv="Content-Type" 
			content="text/html; charset=UTF-8"/>
		<meta name="viewport" 
			content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
    	<meta name="apple-mobile-web-app-capable" 
    		content="yes"/>
	
		<title>
			<tiles:getAsString name="title"/>
		</title>

		<!-----------------------------------------
		[Styles]
		------------------------------------------>
		<link  	rel="stylesheet" 	type="text/css" 	href="resources/jquery-ui-theme/jquery-ui.css"				/>
		<link 	rel="stylesheet" 	type="text/css" 	href="resources/styles/style.css" 							/>
		<link 	rel="stylesheet"	type="text/css" 	href="resources/styles/<tiles:getAsString name="style"/>"	/>
		<link 	rel="shortcut icon" type="image/x-icon"	href="resources/images/favLogo.ico" 						/>

		<!-----------------------------------------
		[Scripts]
		------------------------------------------>
		<script src="resources/jquery/jquery-1.10.2.js">		</script>
		<script src="resources/jquery/jquery-ui.js">			</script>
		<script src="resources/handlebars/handlebars-1.3.0.js">	</script>			
		<script src="resources/scripts/Notifications.js">		</script>
	</head> 

	<body> 
    	<tiles:insertAttribute name="header"/> 
		<tiles:insertAttribute name="body"	/>
    	<tiles:insertAttribute name="footer"/> 
	</body> 
</html>


