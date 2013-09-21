i = -1;

function stepsUpdate(value) {
	
	i= i + value;
	
	var bootImg 	= '<img id="imgBoot" 		src="resources/images/boot.png" 	width="175" style="margin-right: 100px">';
	var steeringImg	= '<img id="imgSteering"	src="resources/images/steering.png" width="175">';
	var sunImg 		= '<img id="imgSun" 		src="resources/images/sun.png" 		width="175" style="margin-right: 100px">';
	var moonImg		= '<img id="imgMoon"		src="resources/images/moon.png" 	width="175">';
	var stepsContent;
	
	
	//BUTTONS DISAPPEAR OR APPEAR
	if(i >= 2)
		document.getElementById("nextBtn").style.display="none";
	else
		document.getElementById("nextBtn").style.display="inline";
	
	if(i <= 0)
		document.getElementById("backBtn").style.display="none";
	else
		document.getElementById("backBtn").style.display="inline";
	
	
	//CONTENT UPDATE
	switch(i){
		case 0: 
			stepsContent 	= bootImg 	+ 	steeringImg;
			break;
			
		case 1:
			stepsContent	= sunImg	+	moonImg;
			break;
		case 2:
			stepsContent 	= "Mapa"; 		
			break;			
	}
	
	var stepsAdd 		= '<div id="step"'+i+ ' style="margin: 10px 0px 0px 250px">' + stepsContent + '</div>';
	document.getElementById("steps1").innerHTML = stepsAdd;
}
