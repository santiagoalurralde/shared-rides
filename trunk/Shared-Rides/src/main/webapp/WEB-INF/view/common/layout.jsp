<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html> 

	<head> 
		<!----	Specific	---->
		<meta http-equiv="Content-Type" 				content="text/html; charset=UTF-8"													/>
		<meta name		='viewport'						content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'	/>
    	<meta name		="apple-mobile-web-app-capable" content="yes"																		/>
	
		<title>
			<tiles:getAsString name="title"/>
		</title>
		 
		<!----	Styles		---->
		<link 	rel		="stylesheet" 		href="resources/styles/style.css" 							type="text/css"/>
		<link 	rel		="stylesheet"		href="http://fonts.googleapis.com/css?family=Raleway:500"  	type="text/css"/> 
		<link	rel		="stylesheet"		href="resources/styles/olStyle.css"							type="text/css"/>
		
		<!----	Scripts		---->
		<script src		="resources/scripts/jquery-1.10.2.js" 		type=	"text/javascript">		</script>
		<script src		="http://code.jquery.com/ui/1.10.3/jquery-ui.js">							</script>
		
	</head> 


	<body> 
    	<tiles:insertAttribute name="header"/> 

		<tiles:insertAttribute name="body"/>

    	<tiles:insertAttribute name="footer"/> 
	</body> 
</html>
