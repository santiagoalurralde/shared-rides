<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html> 
	<head> 
		<!---- Specific ---->
		<meta http-equiv="Content-Type" 			content="text/html; charset=UTF-8"													/>
		<meta name='viewport'						content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'	/>
    	<meta name="apple-mobile-web-app-capable" 	content="yes"																		/>
	
		<title>
			<tiles:getAsString name="title"/>
		</title>

		<!---- Styles ---->
		<link  	rel="stylesheet" 	type="text/css" 	href="resources/jquery-ui-theme/jquery-ui.css"						/>
		<link 	rel="stylesheet" 	type="text/css" 	href="resources/styles/style.css" 									/>
		<link 	rel="stylesheet"	type="text/css" 	href="resources/styles/<tiles:getAsString name="style"/>"			/>
		<link 	rel="stylesheet"	type="text/css" 	href="http://fonts.googleapis.com/css?family=Raleway:500"  			/>
		<link 	rel="shortcut icon" type="image/x-icon"	href="resources/images/favLogo.ico" 								/>

		<!---- Scripts ---->
		<script src="resources/scripts/jquery-1.10.2.js">	</script>
		<script src="resources/scripts/jquery-ui.js">		</script>	
	</head> 
	<body> 
    	<tiles:insertAttribute name="header"/> 

		<tiles:insertAttribute name="body"/>

    	<tiles:insertAttribute name="footer"/> 
	</body> 
</html>
