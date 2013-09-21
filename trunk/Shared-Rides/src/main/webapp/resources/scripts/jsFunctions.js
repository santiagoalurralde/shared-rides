
/*
i = -1;
function stepsUpdate(value) {
	
	i	= i + value;
	var steeringImg		= '<img id="imgSteering" 	src="resources/images/steering.png" width="175">';
	var bootImg			= '<img id="imgBoot"		src="resources/images/boot.png" 	width="175" style="margin-right: 100px">';
	var sunImg 			= '<img id="imgSun" 		src="resources/images/sun.png" 		width="175" style="margin-right: 100px">';
	var moonImg			= '<img id="imgMoon"		src="resources/images/moon.png" 	width="175">';
	var stepsContent;
	
	
	//BUTTONS DISAPPEAR OR APPEAR
	if(i >= 1)
		document.getElementById("nextBtn").style.visibility="hidden";
	else
	{
		document.getElementById("nextBtn").style.visibility="visible";
		document.getElementById("backBtn").style.visibility="visible";
	}
	if(i<0)
		document.getElementById("backBtn").style.visibility="hidden";

	
	//CONTENT UPDATE
	switch(i){
		case (-1):
			stepsContent 	= bootImg 	+ 	steeringImg; 
			break;
		case 0:
			stepsContent	= sunImg	+	moonImg;
			break;
		case 1:
			stepsContent 	= '<iframe width="425" height="350" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://maps.google.com.ar/?ie=UTF8&amp;ll=-31.394821,-64.201698&amp;spn=0.074584,0.169086&amp;t=h&amp;z=13&amp;output=embed"></iframe>'; 		
			break;			
	}
	
	
	//MODIFY
	var stepsChange = '<div id="step"'+i+' style="margin: 10px 0px 0px 250px">' + stepsContent + '</div>';
	document.getElementById("steps1").innerHTML = stepsChange;
}
*/