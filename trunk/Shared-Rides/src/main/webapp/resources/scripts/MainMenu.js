var	_step	= -1,   //Contador de Pasos
   	_user	= 0,    //Tipo de Usuario
	_shift	= 0;	//Turno


/***************************************************************************************
 * STEPS
 ***************************************************************************************/

$(document).ready(function() {    
    initMap();
   
    start();
           
    //Acciones al presionar las imagenes
    $("#imgSun").click(function() {
        changeShift(1);
    });
   
    $("#imgMoon").click(function() {
        changeShift(2);
    });
   
    $("#imgBoot").click(function() {
        changeUserType(1);
    });
   
    $("#imgSteering").click(function() {
        changeUserType(2);
    });

    //Pressing next or back
    $("#btnNext, #btnBack").click(function() {
        update(_step);
    });
   
    $("#btnOK").click(function() {
        customSearch();
    });
    
    $("#btnDefault").click(function() {
        defaultSearch();
    });    
});

/**
 * Checks if we can add 1 step
 */
function stepNext() {
    if(_step == 0 && _user == 0)
        $("#dlgChooseUserType").dialog({dialogClass: 'no-close', modal: true, draggable: false});
    else if(_step == 1 && _shift == 0)
        $("#dlgChooseShift").dialog({dialogClass: 'no-close', modal: true, draggable: false});    
    else
        _step++;
}

/**
 * Moves back 1 step
 */
function stepBack() {
    _step--;
}


/***************************************************************************************
 * FUNCTIONS
 ***************************************************************************************/

/**
 * Selects type of shift and highlights related icon.
 */
function changeShift(s) {
    _shift = s;
   
    if(_shift == 1) {
        //Morning
        $("#imgMoon").css("opacity", ".05");  
        $("#imgSun").css("opacity", "1");
    }
    else {
        //Afternoon
        $("#imgMoon").css("opacity", "1");
        $("#imgSun").css("opacity", ".05");  
    }      
}

/**
 * Selects type of user and highlights related icon.
 */
function changeUserType(u) {
    _user = u;
   
    if(_user == 1) {
        //Pedestrian
        $("#imgBoot").css("opacity", "1");
        $("#imgSteering").css("opacity", ".05");    
    }
    else {
        //Driver
        $("#imgSteering").css("opacity", "1");
        $("#imgBoot").css("opacity", ".05");
    }
}

function defaultSearch() {
	$.post("defaultFind.do", {"user": _user , "shift": _shift},
	        function(json) {
	            var peopleFound = $.parseJSON(json);
	            if(peopleFound == "") {
                    $(".alerts").show();  
                    $("#tableFound").hide();                              
	            }
	            else {
	                $.each(peopleFound, function(i, data) {
	                    var distance = Math.ceil(data.distance);
	                    $("#tableFound").show();      
	                    $(".alerts").hide();  
	                    $("#tableFound").append(
	                        "<tr>"+
	                            "<td>"+ data.name +" "+ data.surname +"</td>"+
	                            "<td>"+
	                                "<a href='/Shared-Rides/profile.do?user="+ data.id +"'>"+
	                                    "<img src='printImgFile.do?pic="+ data.picture +"'/>"+
	                                "</a>"+
	                            "</td>"+
	                            "<td>"+ $("#lblBlocks1").val() +" "+ distance +" "+ $("#lblBlocks2").val() +"</td>"+
	                        "</tr>");
	                });    
	            }
	    });
	   
    //Brings the list
    $("#mapDriver, #mapPedestrian, #btnOK, #btnDefault").hide();       
    $("#listFound").show("fast");	
}

/**
 * Sends all data collected through post, and shows results
 */
function customSearch() {
    var coordsJs;                           //Datos de coordenadas

    if(_user == 2)
        coordsJs = "[{lon=" + _lon.toString() + " , lat=" + _lat.toString() + "}]";
    else
        coordsJs = gpxTrack.confirm();
                   
    $.post("find.do", {"user": _user , "shift": _shift, "mapData": coordsJs},
        function(json) {
            var peopleFound = $.parseJSON(json);
            if(peopleFound == "") {
                $(".alerts").show();  
                $("#tableFound").hide();                              
            }
            else {
                $.each(peopleFound, function(i, data) {
                    var distance = Math.ceil(data.distance);
                    $("#tableFound").show();      
                    $(".alerts").hide();  
                    $("#tableFound").append(
                        "<tr>"+
                            "<td>"+ data.name +" "+ data.surname +"</td>"+
                            "<td>"+
                                "<a href='/Shared-Rides/profile.do?user="+ data.id +"'>"+
                                    "<img src='printImgFile.do?pic="+ data.picture +"'/>"+
                                "</a>"+
                            "</td>"+
                            "<td>"+ $("#lblBlocks1").val() +" "+ distance +" "+ $("#lblBlocks2").val() +"</td>"+
                        "</tr>");
                });    
            }
    });
   
    //Brings the list
    $("#mapDriver, #mapPedestrian, #btnOK").hide();       
    $("#listFound").show("fast");
}

/**
 * Establishes the starting environment
 */
function start() {      
    highlightStep(_step);          
    $("#mapDriver, #mapPedestrian").hide();       
}

/**
 * Manages GUI according to current step of form.
 *
 * @param {Number} step - current step
 */
function update(step) {
    switch(step) {
        case 0:
            highlightStep(step);
            $("#imgSun, #imgMoon").hide();
            $("#imgBoot, #imgSteering").show("fast");
            $("#btnBack, #btnDefault").hide("fast");
            $("#btnNext").css("margin-left", "0");
            break;
        case 1:
            highlightStep(step);                    
            $("#mapDriver, #mapPedestrian, #imgBoot, #imgSteering, #btnOK, #listFound").hide();
            $("#imgSun, #imgMoon, #btnBack, #btnNext").show("fast");
            if($("#hdnValidate").val() == "true")
            	$("#btnDefault").show("fast");
            $("#btnNext").css("margin-left", "60px");
            $("#tableFound td").remove();
            break;
        case 2:
            highlightStep(step);                    
            $("#imgSun, #imgMoon, #btnNext, #btnDefault").hide();
            _user == 2 ? $("#btnOK, #mapPedestrian").show("slow") : $("#btnOK, #mapDriver").show("slow");
            break;
    }      
}

/**
 * Highlights current step
 *
 * @param {Number} step - current step
 */
function highlightStep(step) {
    switch(step) {
        case -1:
        case 0:
            $("#step2, #step3").css("opacity", ".2");
            $("#step1").css("opacity", "1");
            break;
        case 1:
            $("#step1, #step3").css("opacity", ".2");
            $("#step2").css("opacity", "1");
            break;
        case 2:
            $("#step1, #step2").css("opacity", ".2");
            $("#step3").css("opacity", "1");
            break;
    }
}


