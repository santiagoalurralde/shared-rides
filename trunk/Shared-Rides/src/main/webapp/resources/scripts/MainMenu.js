var _step  	= -1,   //Contador de Pasos
    _user   = 0,    //Tipo de Usuario
    _shift  = 0;    //Turno


/*******************************************************
 * STEPS
 *******************************************************/

$(document).ready(function() {  
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

    $(".btn-next").click(function() {
        stepNext();
    });

    $(".btn-back").click(function() {
        stepBack();
    });
});

/**
 * Checks if we can add 1 step
 */
function stepNext() {
    if(_step == 0 && _user == 0) {
        $("#dlg-choose-type").dialog({modal: true, draggable: false});
    }
    else if(_step == 1 && _shift == 0) {
        $("#dlg-choose-shift").dialog({modal: true, draggable: false});
    }
    else {
        _step++;
    }
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
    var $tableFound     = $(".table-found"),
        templateFound   = Handlebars.compile($("#temp-table-found").html()); 

    $.post("find.do", {"user": _user , "shift": _shift},
        function(json) {
            var peopleFound = $.parseJSON(json);
            if(peopleFound == "") {
                $(".alerts").show();  
                $tableFound.hide();                              
            }
            else {
                $.each(peopleFound, function(i, person) {
                	person.distance = Math.ceil(person.distance);                    
                    var resultFound = templateFound(person);
                    $(".alerts").hide();  
                    $tableFound.append(resultFound).show();
                });    
            }
    });
   
    //Brings the list
    $(".sr-maps, .btn-OK").hide();       
    $(".search-results").show("fast");
}

/**
 * Sends all data collected through post, and shows results
 */
function customSearch() {
    var coords,
        $tableFound     = $(".table-found"),
        templateFound   = Handlebars.compile($("#temp-table-found").html());

    if(_user == 2) {
        coords = "[{lon=" + _lon.toString() + " , lat=" + _lat.toString() + "}]";
    }
    else {
        coords = gpxTrack.confirm();
    }
                   
    $.post("find.do", {"user": _user , "shift": _shift, "mapData": coords},
        function(json) {
            var peopleFound = $.parseJSON(json);
            if(peopleFound == "") {
                $(".alerts").show();  
                $tableFound.hide();                              
            }
            else {
                $.each(peopleFound, function(i, data) {
                    data.distance = Math.ceil(data.distance);                    
                    var resultFound = templateFound(data);
                    $(".alerts").hide();  
                    $tableFound.append(resultFound).show();
                });    
            }
    });
   
    //Brings the list
    $(".sr-maps, .btn-OK").hide();       
    $(".search-results").show("fast");
}

/**
 * Establishes the starting environment
 */
function start() {      
    highlightStep(_step); 
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
            $(".sr-maps").html("");
            $(".btn-OK, .search-results, .step-usertype").hide();
            $(".btn-back, .btn-next").show("fast");
            $(".step-shift").show("fast");

            if($("#val-validate").val() == "true")
                $(".btn-default").show("fast");

            $(".btn-next").css("margin-left", "60px");
            $(".table-found td").remove();
            break;
        case 2:
        	var $maps = $(".sr-maps");
            highlightStep(step);
            $(".btn-OK").show();
            $maps.show();
            (_user == 2) ? $maps.load("mappedestrian.do") : $maps.load("mapdriver.do");
            $(".step-shift, .btn-next, .btn-default").hide();
            break;
    }
}

/**
 * Highlights current step
 *
 * @param {Number} step - current step
 */
function highlightStep(index) {
    index = (index == -1) ? Math.abs(index) : index + 1;
    $(".step"+ index).css("opacity", "1")
                     .siblings().css("opacity", ".2");
}

/**
 * Highlights related icon.
 *
 * @param {jquery} $target - Clicked Image
 */
function highlightImage($target) {
    $target.css("opacity", "1")
           .siblings().css("opacity", ".08");
}

/**
 * Changes values of decisions in search form.
 *
 * @param {jquery} $target - img pressed.
 */
function changeDecision($target){
    //1: Pedestrian - 2: Driver
    //1: Morning - 2: Afternoon

    if($target.hasClass("choice-pedestrian")) {
        _user = 1;
    }
    else if($target.hasClass("choice-driver")) {
        _user = 2;
    }
    else if($target.hasClass("choice-morning")) {
        _shift = 1;
    }
    else {
        _shift = 2;
    }
}

