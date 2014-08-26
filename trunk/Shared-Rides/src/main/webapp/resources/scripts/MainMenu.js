var _step   = -1,   //Contador de Pasos
    _user   = 0,    //Tipo de Usuario
    _shift  = 0;    //Turno


/*******************************************************
 * STEPS
 *******************************************************/

$(document).ready(function() {    
    initMap();
   
    start();

    //Acciones al presionar las imagenes
    $(".choices").click(function() {
        highlightImage($(this));
        changeDecision($(this));
    });
    
    //Pressing next or back
    $(".btn-next, .btn-back").click(function() {
        update(_step);
    });
   
    $(".btn-OK").click(function() {
        customSearch();
    });
    
    $(".btn-default").click(function() {
        defaultSearch();
    });    
});

/**
 * Checks if we can add 1 step
 */
function stepNext() {
    if(_step == 0 && _user == 0)
        $("#dlg-choose-type").dialog({modal: true, draggable: false});    
    else if(_step == 1 && _shift == 0)
        $("#dlg-choose-shift").dialog({modal: true, draggable: false});    
    else
        _step++;
}

/**
 * Moves back 1 step
 */
function stepBack() {
    _step--;
}


/*******************************************************
 * FUNCTIONS
 *******************************************************/

/**
 * Performs search with previously selected maps or markers.
 */
function defaultSearch() {
    var $tableFound = $(".table-found");

    $.post("defaultFind.do", {"user": _user , "shift": _shift},
            function(json) {
                var peopleFound = $.parseJSON(json);
                if(peopleFound == "") {
                    $(".alerts").show();  
                    $tableFound.hide();                              
                }
                else {
                    $.each(peopleFound, function(i, data) {
                        var distance = Math.ceil(data.distance);
                        $tableFound.show();      
                        $(".alerts").hide();  
                        $tableFound.append(
                            "<tr>"+
                                "<td>"+ data.name +" "+ data.surname +"</td>"+
                                "<td>"+
                                    "<a href='/Shared-Rides/profile.do?user="+ data.id +"'>"+
                                        "<img src='printImgFile.do?pic="+ data.picture +"'/>"+
                                    "</a>"+
                                "</td>"+
                                "<td>"+ $("#lbl-blocks1").val() +" "+ distance +" "+ $("#lbl-blocks2").val() +"</td>"+
                            "</tr>");
                    });    
                }
        });
       
    //Brings the list
    $(".map-driver, .map-pedestrian, .btn-OK, .btn-default").hide();       
    $(".search-results").show("fast");   
}

/**
 * Sends all data collected through post, and shows results
 */
function customSearch() {
    var coordsJs,                           //Datos de coordenadas
        $tableFound = $(".table-found");

    if(_user == 2)
        coordsJs = "[{lon=" + _lon.toString() + " , lat=" + _lat.toString() + "}]";
    else
        coordsJs = gpxTrack.confirm();
                   
    $.post("find.do", {"user": _user , "shift": _shift, "mapData": coordsJs},
        function(json) {
            var peopleFound = $.parseJSON(json);
            if(peopleFound == "") {
                $(".alerts").show();  
                $tableFound.hide();                              
            }
            else {
                $.each(peopleFound, function(i, data) {
                    var distance = Math.ceil(data.distance);
                    $tableFound.show();      
                    $(".alerts").hide();  
                    $tableFound.append(
                        "<tr>"+
                            "<td>"+ data.name +" "+ data.surname +"</td>"+
                            "<td>"+
                                "<a href='/Shared-Rides/profile.do?user="+ data.id +"'>"+
                                    "<img src='printImgFile.do?pic="+ data.picture +"'/>"+
                                "</a>"+
                            "</td>"+
                            "<td>"+ $("#lbl-blocks1").val() +" "+ distance +" "+ $("#lbl-blocks2").val() +"</td>"+
                        "</tr>");
                });    
            }
    });
   
    //Brings the list
    $(".map-driver, .map-pedestrian, .btn-OK").hide();       
    $(".search-results").show("fast");
}

/**
 * Establishes the starting environment
 */
function start() {      
    highlightStep(_step);          
    $(".map-driver, .map-pedestrian").hide();       
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
            $(".step-shift").hide();
            $(".step-usertype").show("fast");
            $(".btn-back, .btn-default").hide("fast");
            $(".btn-next").css("margin-left", "0");
            break;
        case 1:
            highlightStep(step);                    
            $(".map-driver, .map-pedestrian, .btn-OK, .search-results, .step-usertype").hide();
            $(".btn-back, .btn-next").show("fast");
            $(".step-shift").show("fast");
            if($("#hdn-validate").val() == "true")
                $(".btn-default").show("fast");
            $(".btn-next").css("margin-left", "60px");
            $(".table-found td").remove();
            break;
        case 2:
            highlightStep(step);                    
            $(".step-shift, .btn-next, .btn-default").hide();
            (_user == 2) ? $(".btn-OK, .map-pedestrian").show("slow") : $(".btn-OK, .map-driver").show("slow");
            break;
    }      
}

/**
 * Highlights current step
 *
 * @param {Number} step - current step
 */
function highlightStep(index) {
    index = (index == -1) ? Math.abs(index) : index+1;   //If it's -1
    var $step = $(".step"+ index);
    $step.css("opacity", "1");
    $step.siblings().css("opacity", ".2");
}

/**
 * Highlights related icon.
 *
 * @param {jquery} $target - Clicked Image
 */
function highlightImage($target) {
    $target.siblings().css("opacity", ".08");  
    $target.css("opacity", "1");
}

/**
 * Changes values of decisions in search form.
 *
 * @param {jquery} $target - img pressed.
 */
function changeDecision($target){
    //1: Pedestrian - 2: Driver
    //1: Morning - 2: Afternoon

    if($target.hasClass("choice-pedestrian"))
        _user  = 1;  
    else if($target.hasClass("choice-driver"))
        _user  = 2; 
    else if($target.hasClass("choice-morning"))
        _shift = 1; 
    else
        _shift = 2;     
}



