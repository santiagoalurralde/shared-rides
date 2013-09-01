<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html> 

<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<title><tiles:getAsString name="title"/></title> 
<link rel="stylesheet" href="css/master.css" type="text/css" /> 
</head> 

<body> 
<div id="mask"> 
    <div id="header"> 
        <tiles:insertAttribute name="header"/> 
    </div> 
    <div id="content-wrapper"> 
        <div id="leftcol-wrapper"> 
            <div id="leftcol"><tiles:insertAttribute name="menu"/></div> 
            <div id="content"> 
                <tiles:insertAttribute name="body"/> 
            </div> 
        </div> 
    </div> 
    <div id="clearfooter"></div> 
</div> 

<div id="footer" align="center"> 
    <tiles:insertAttribute name="footer"/> 
</div> 
</body> 
</html>