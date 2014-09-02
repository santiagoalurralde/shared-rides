var _step           = -1,   //Contador de Pasos
    _previous       = "",
    _previous2      = "",
    _previousTarget = "",
    _days           = new Array(),
    _matrices       = new Array(),
    _projections    = new Array(),
    _userType;

for (var l=0; l<5; l++) {
    _days[l]        = null;
    _matrices[l]    = null;
    _projections[l] = null;
}


/*******************************************************
 * STEPS
 *******************************************************/

$(document).ready(function() {
    //Start pedestrians map.
    initMap();
   
    start();
   
    $(".btn-map").click(function(){
        saveMap();              
    });
    
    $(".btn-next, .btn-back").click(function(){
        update(_step);
    });
   
    $(".btn-OK").click(function(){
        signUp();
    });

    $("#cellphone, #number").keyup(function(){
        checkNumeric(this);
    });
});

/**
 * Establishes the starting environment
 */
function start() {
    $(".map-driver, .map-pedestrian").hide();
   
    highlightStep(0);
   
    fillRowsInOut($(".table-signup .row-in"));
    fillRowsInOut($(".table-signup .row-out"));

    var l,
        neighborhoods   = ["Alta Córdoba","Alto Alberdi","Alto Verde","Argüello","Bella Vista","Centro","Cerro Chico","Cerro de las Rosas","Cerveceros","Chateau Carreras","Cofico","Colinas del Cerro","Country Jockey Club","Country Las Delicias","Country Lomas de la Carolina","Crisol","Dean Funes","Ejército Argentino","El Quebracho","Empalme","Ferreyra","General Bustos","General Paz","General Pueyrredon","Granja De Funes","Güemes","Ituzaingo","Jardín","Juniors","La France","Las Flores","Las Palmas","Las Violetas","Los Boulevares","Los Paraísos","Marques De Sobremonte","Nueva Córdoba","Observatorio","Palermo Bajo","Patricios","Poeta Lugones","San Martín","San Vicente","Urca","Villa Belgrano","Villa Cabrera","Villa Centenario","Villa Warcalde","Yapeyu","Yofre"],
        brands          = ["Alfa-Romeo", "Audi", "BMW", "Citroen", "Chery", "Chevrolet", "Chrysler", "Daihatsu", "Dodge", "Fiat", "Ford", "Honda", "Hyundai", "Jeep", "Kia", "Land Rover", "Mazda", "Mercedes Benz", "Mini", "Mitsubishi", "Nissan", "Peugeot", "Renault", "Seat",  "Subaru", "Suzuki", "Toyota", "Volkswagen", "Volvo"];

    for(l=0; l<neighborhoods.length; l++) {
        $("#neighborhood")
            .append("<option value='" + neighborhoods[l] + "'>"
                        + neighborhoods[l] +
                    "</option>");
    }

    for(l=0; l<brands.length; l++) {
        $("#brand")
            .append("<option value='" + brands[l] + "'>"
                        + brands[l] +
                    "</option>");
    }
}

/**
 * Checks if we can add 1 step
 */
function stepNext() {  
    if(_step == 0 && (!checkValues0() || $(".alerts.alert-pw-match").is(":visible") || checkUserExists()))
        console.log("Can't proceed to second step");
    else if(_step == 1 && !checkValues1())
        console.log("Can't proceed to third step");
    else
        _step++;
}

/**
 * Moves back 1 step
 */
function stepBack() {
    toggleAlert(false, "", "alert-pw-match");
    _step--;
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
            $(".step-first, .btn-next").show();
            $(".step-second").hide();
            $(".btn-back").hide("fast");
            break;
        case 1:
            highlightStep(step);
            $(".map-driver, .map-pedestrian, .step-first, .step-third").hide();
            $(".step-second, .split-beginning").show();
            $(".btn-back, .btn-next").show("fast");
            $(".btn-OK").hide("fast");
            break;
        case 2:
            highlightStep(step);
            fillRowType();
            $(".step-second, .split-beginning").hide();
            $(".step-third").show();
            $(".btn-next").hide("fast");
            $(".btn-OK").show("slow");
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
    $(".step-signup"+ index).css("opacity", "1")
                            .siblings().css("opacity", ".2");
}


/*******************************************************
 * CHECKERS
 *******************************************************/

/**
 * Checks if values in step 1 are filled.
 *
 * @return {Boolean} TRUE values are OK
 */
function checkValues0() {              
    var flag        = true,
        ipts        = $(".step-first input"),       // First step's inputs (array).
        lastElement = ipts.index($("#cellphone")),
        iptL;
   
    for(var l=0; l<lastElement+1; l++) {
        iptL = $(ipts[l]);          //Jquery-cached input from the array of inputs.
        flag = checkLength(iptL);
    }
    
    if(!flag) {
        toggleAlert(true, getLabel("lblAlertInputs"), "alert-inputs");
    }
    
    return flag;
}

/**
 * Checks if values in step 2 are filled.
 *
 * @return {Boolean} TRUE values are OK
 */
function checkValues1() {
    var flag    = true,
        max     = 3,
        selects = $(".step-second select"),
        selectL;
   
    if(_userType != "pedestrian") {
        /* Is not a pedestrian */
        max = 4;
        flag = checkLength($("#plate-letters")) & checkLength($("#plate-numbers"))
                & checkLength($("#model")) & checkLength($("#number-seats"));
    }
   
    for(var l=0; l<max; l++){
        selectL = $(selects[l]);
        if(selectL.val() == "0") {
            flag = false;
            paint(selectL, true);
        }
    }
   
    flag = checkLength($("#street"));
    flag = checkLength($("#number"));
   
    for(var l=0; l<3; l++){
        selectL = $(selects[l]);
        if(selectL.val() == "0") {
            flag = false;
            paint(selectL, true);
        }
    }
   
    if(!flag) {
        toggleAlert(true, getLabel("lblAlertInputs"), "alert-inputs");
    }
    return flag;
}

/**
 * Checks if values in step 3 are filled.
 *
 * @return {Boolean} TRUE values are OK
 */
function checkValues2() {      
    for (var d=0; d<5; d++) {
        var aux = d + 1;
        if($(".chk-active"+aux).prop("checked")) {
            _days[d] = "Unsubscribed";
        }
        else {
            if (_days[d] == null) {
                toggleAlert(true, getLabel("lblAlertEmptyDays"), "alert-schedules");
                return false;
            }
        }
    }
    return true;
}

/**
 * Checks if passwords are the same.
 *
 */
function checkPassword() {
    var $passwordFirst = $("#password-first"),
        $passwordCheck = $("#password-check");

    $passwordFirst.change(function(event) {
        checkPassword();
    });

    if($passwordFirst.val() != $passwordCheck.val()) {
        paint($passwordFirst, true);    
        paint($passwordCheck, true);
        toggleAlert(true, getLabel("lblAlertPasswords"), "alert-pw-match");
        return false;
    }              
    else {
        paint($passwordFirst, false);  
        paint($passwordCheck, false);  
        toggleAlert(false, "", "alert-pw-match");    
        return true;
    }
}

/**
 * Checks if email is cool. Returns TRUE if it is.
 *
 * @param {Element} target - element we're gonna check.
 */
function checkEmail(target) {
    var email   = $(target).val(),
        re      = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

/**
 * Checks each element and hides alerts. (Calls checkLength).
 *
 * @param {Element} target - element we're gonna check.
 */
function checkIt(target) {
    if($(".alerts").hasClass("alert-inputs")) {
        toggleAlert(false, "", "alert-inputs");
    }
    checkLength($(target));   
}

/**
 * Checks if element is complete.
 *
 * @param {jquery} $target - element we're gonna check.
 */
function checkLength($target) {
    if($target.val().length == 0) {
        paint($target, true);
        toggleAlert(true, getLabel("lblAlertInputs"), "alert-inputs");
        return false;
    }
    else {
        paint($target, false);
        toggleAlert(false, "", "alert-inputs");        
        return true;
    }
}

/**
 * Paints or Unpaints element.
 *
 * @param {jquery} $target - element we're gonna paint.
 * @param {Bool} flag - TRUE for painting element.
 */
function paint($target, flag) {
    if(flag) {
        $target.addClass("painted");
    }
    else {
        $target.removeClass("painted");
    }
}

/**
 * Shows or hides alert.
 *
 * @param {Bool} flag - TRUE for activating alert.
 * @param {String} msg - Text that the alert is going to contain.
 * @param {String} flag - Additional class for the alert.
 */
function toggleAlert(flag, msg, cls) {
	var $alertPlusClass = "";
	
    if(flag) {
        $(".alerts").addClass(cls);
        $alertPlusClass = $(".alerts."+ cls);
        $alertPlusClass.html("<p>"+ msg);
        $alertPlusClass.show("fast");          
    }
    else {
    	$alertPlusClass = $(".alerts."+ cls);
    	$alertPlusClass.hide("fast");
    	$alertPlusClass.removeClass(cls);       
    }
}

/**
 * Checks if value in element is smaller than limit
 *
 * @param {Element}
 * @param {number} Minimum limit
 */
function checkBounds(target, val) {
    if(target.value > val) {
        alert("Este campo no admite valores mayores a "+ val);
        target.value = _previous2;
    }
    else {
        _previous2 = target.value;
    }
}

/**
 * Checks if key pressed is in the alphabet
 *
 * @param {event}
 */
function checkAlphabetic(event) {
    var inputValue = event.charCode;
    if((inputValue > 47 && inputValue < 58) && (inputValue != 32)) {
        event.preventDefault();
    }
}

/**
 * Checks if value in element is numeric
 *
 * @param {Element} target - input that we're checking.
 */
function checkNumeric(target) {
    if(target.value.length > 0 && isNaN(target.value)) {                                            
        if(_previousTarget != target)
            _previous = "";
        target.value = _previous;
    }
    else {
        _previous       = target.value;
        _previousTarget = target;
    }
}

/**
 * Checks if user with a personal-id was already created. Returns true if exists.
 */
function checkUserExists() {
    var pId = $("#personal-id").val().toString(),
        flag = false;
   
    $.ajax({
        url: "validateNewUser.do",              
        type: "GET",
        data: {personalId: pId}
        }).done(function(isValidate) {
            if(!isValidate) {
                flag = true;
                toggleAlert(flag, getLabel("lblAlertIdExists"), "alert-inputs");
            }
    });
    return flag;
}

/**
 * Checks if hours are valid.
 */
function checkHours($target){
	
    var d        = $target.parent().index(),
        $hourOut = $("#hour-"+ d + "out"),
        $hourIn  = $("#hour-"+ d + "in");

    if($hourOut.val() < $hourIn.val() && $hourOut.val() != "none") {
    	alert("true");
        
    	toggleAlert(true, getLabel("lblAlertHours"), "alert-schedules");
        $(".map-definition").hide();
        $(".btn-map").prop("disabled", true);
    }
    else {
    	alert("false");

        toggleAlert(false, "", "alert-schedules");      	
        $(".map-definition").show();        
        $(".btn-map").prop("disabled", false);
    }
}

/**
 * Disables day if unsubscribed
 *  
 * @param {jquery} $target - checkbox that indicates if subscribed and has changed.
 */
function subscribes($target){
    var d          = $target.parent().index() + 1,
        $btnIn     = $(".row-in td:nth-child("+ d +") button"),
        $btnOut    = $(".row-out td:nth-child("+ d +") button");

    // If checked, disable buttons.(UNCHECKED by default, see in definition)
    if($target.prop("checked")) {
        $btnIn.add($btnOut).addClass("btn-unsubscribed")
		   .removeClass("btn-define")
		   .prop("disabled", true);        
    }
    else {
        $btnIn.add($btnOut).addClass("btn-define")
		   .removeClass("btn-unsubscribed")
		   .prop("disabled", false);      	
    }
}

/*******************************************************
 * FUNCTIONS
 *******************************************************/

/**
 * Sends everything to server.
 */
function signUp() {    
    if(checkValues2()) {
        var org             = $("#organization").find("option:selected").val(),
            pId             = $("#personal-id").val(),
            pw              = $("#password-first" ).val(),
            name            = $("#name").val(),
            surname         = $("#surname").val(),
            email           = $("#email").val(),
            phone           = $("#cellphone").val(),
            number          = $("#number").val(),
            street          = $("#street").val(),
            nbh             = $("#neighborhood").val(),
            shift           = $("#shift").val(),
            usType          = $("#userType").find("option:selected").val(),
            brand           = $("#brand").find("option:selected").val(),
            modelVehicle    = $("#model").val(),
            plNumb          = $("#plate-numbers").val(),
            plLett          = $("#plate-letters").val(),
            nSeats          = $("#number-seats").find("option:selected").val();
           
            $.post("register.do", {
            					   "organization": 	org,
                                   "personalId":  	pId,
                                   "pw":           	pw,
                                   "name":         	name,
                                   "surname":      	surname,
                                   "email":        	email,                                                                  
                                   "phone":        	phone,  
                                   "street":       	street,
                                   "number":       	number,
                                   "neighborhood": 	nbh,
                                   "shift":        	shift,
                                   "userType":     	usType,
                                   "brand":       	brand,
                                   "modelVehicle": 	modelVehicle,
                                   "plateLetters":  plNumb,
                                   "plateNumbers":  plLett,
                                   "numberSeats":   nSeats,
                                   "days":         	JSON.stringify(_days)
                                   },  
                function(str) {
                     alert(getLabel("lblAlertRegistered"));
                });
    }
}

/**
 * Saves map previously defined.
 */
function saveMap() {
    var d           = $("#hdn-day").val(),
        io          = $("#hdn-inout").val(),
        userTypeDay = $("#hdn-usertype-day").val(),
        h           = $("#hour-"+ d + io).val(),
        index       = Number(d)-1;

    if(h == "none") {
        alert(getLabel("lblAlertPickHour"));
        return false;
    }
   
    if(userTypeDay == "driver") {
        if(_matrices[index] == null)
            _matrices[index] = new Matrix();
        if(_days[index] == null)
            _days[index] = new Track();
       
        if(io == "in") {
            _matrices[index].matrixIn = gpxTrack.matrixify();                              
            _days[index].trackIn      = gpxTrack.myConfirm();
            if(_days[index].trackIn == "")          //Hasn't defined a route.
                return;                        
            _days[index].hourIn = h;
        }      
        else {
            _matrices[index].matrixOut = gpxTrack.matrixify();
            _days[index].trackOut      = gpxTrack.myConfirm();
            if(_days[index].trackOut == "")         //Hasn't defined a route.
                return;
            _days[index].hourOut = h;                      
        }
        gpxTrack.clear();

        $("#btn-"+ d + io).html(getLabel("lblButtonModify"));
    }
    else {
        if(_lon != "" && _lat != "") {
            if(_projections[index] == null)
                _projections[index] = new Projection();
            if(_days[index] == null)
                _days[index] = new Stop();
           
            if(io == "in") {
                var lonlatCurrent               = new OpenLayers.LonLat(_lon, _lat);
                    _days[index].stopIn         = new OpenLayers.LonLat(_lon, _lat);
                    _days[index].hourIn         = h;    
                    _projections[index].projIn  = lonlatCurrent.transform(proj4326, map.getProjectionObject());                                                
            }
            else {
                var lonlatCurrent               = new OpenLayers.LonLat(_lon, _lat);
                    _days[index].stopOut        = new OpenLayers.LonLat(_lon, _lat);
                    _days[index].hourOut        = h;    
                    _projections[index].projOut = lonlatCurrent.transform(proj4326, map.getProjectionObject());
            }
            $("#btn-"+ d + io).html(getLabel("lblButtonModify"));
        }
        else {
            alert(getLabel("lblAlertPickStop"));
            return false;
        }
    }
   
    $("input[name='chk-active'], .btn-define").prop("disabled", false);
    $("#hour-"+ d + io).prop("disabled", true);
    $(".map-driver, .map-pedestrian, .map-definition").hide();    
}

/**
 * Opens type of map according to user type in day; and shows previously saved maps.
 */
function defineMap($target) {
    var d           = $target.parent().index(),
        io          = $target.closest("tr").data("io"),
        userTypeDay = $("usertype-day-"+ d).val(),
        index       = Number(d)-1;

    if(userTypeDay == "0") {  
        /* Hasn't selected any user type for this day. */
        toggleAlert(true, getLabel("lblAlertPickType"), "alert-schedule");
        return false;
    }
    toggleAlert(false, "", "alert-schedule");    

    $("input[name='chk-active'], .btn-define").prop("disabled", true);
    $("#hour-"+ d + io).prop("disabled", false);
   
    if(_userType != "driver-pedestrian")    
        /* If it's not mixed, every day has the same user type */
        userTypeDay = _userType;
   
    /* Save in Hidden fields */
    $("#hdn-day").val(d);
    $("#hdn-inout").val(io);
    $("#hdn-usertype-day").val(userTypeDay);
   
    if(userTypeDay == "pedestrian") {
        $(".map-driver").hide();              
        $(".map-pedestrian").show("slow");                          
    }
    else {                                                                                                                                  
        $(".map-pedestrian").hide();  
        $(".map-driver" ).show("slow");
    }
   
    $( ".map-definition" ).show("fast");                              
           
    if(userTypeDay == "driver") {
        gpxTrack.clear();
        if(io == "in" && _days[index] != null)
            initMapCoords(lonlat, zoom, map, _matrices[index].matrixIn);
        if(io == "out" && _days[index] != null)
            initMapCoords(lonlat, zoom, map, _matrices[index].matrixOut);
    }
    else {
        clearMarkers();
        _lon = "";
        _lat = "";
        if((io == "in" && _days[index] != null) && _days[index].stopIn != "")
            drawPreviousMarkers(_projections[index].projIn);
        if((io == "out" && _days[index] != null) && _days[index].stopOut != "")
            drawPreviousMarkers(_projections[index].projOut);
    }
}

/**
 * Inserts available hours in selected row.
 *
 * @param {jquery} $row -
 */
function fillRowsInOut($row) {
    var io = $row.data("io");

    for(var d=1; d<6; d++) {
        /* Days */
        var button = "<button id='btn-"+ d + io +"' class='btn-define'"+
                "onclick='defineMap($(this)); return false;'>Definir</button>",
            select = "<select id='hour-"+ d + io +"'"+
                "onchange='checkHours($(this));'></select>";

        $row.append("<td>"+ select + button +"</td>");

        var $select = $row.find("#hour-"+ d + io);
        $select.append("<option value='none'>Hora</option>");
       
        for(var j=0; j<24; j++) {
            /* Hours */
            $select.append("<option value='"+ j +":00'>"+ j +":00 hs</option>")
                   .append("<option value='"+ j +":30'>"+ j +":30 hs</option>")
                   .prop("disabled", true);
        }
        /* Disable hour selects -> Enabled when defining map */
    }
}

/**
 * Inserts type of user in first row
 */
function fillRowType() {
    var content      = "";   
        $userTypeRow = $(".row-usertype");
    
    $userTypeRow.html("<td>"+ getLabel("lblUserType") +"</td>");

    for(var d=1; d<6; d++) {
        if(_userType == "driver-pedestrian") {
            /* Select for user type each day */
            content =   "<select id='usertype-day-"+ d +"'>"+
                            "<option value='0'>"+           getLabel("lblSelectSelect") +"</option>"+
                            "<option value='pedestrian'>"+  getLabel("lblPedestrian")   +"</option>"+
                            "<option value='driver'>"+      getLabel("lblDriver")       +"</option>"+
                        "</select>";
        }
        else if(_userType == "driver") {
            $("#hdn-usertype-day").val(_userType);
            content = getLabel("lblDriver");
        }
        else { /* _userType == "pedestrian" */
            $("#hdn-usertype-day").val(_userType); 
            content = getLabel("lblPedestrian");
        }

        content += "<br><br>";
        content += "<input type='checkbox' name='chk-active' class='chk-active"+ d +"'"+
                        "onclick='subscribes($(this));'>"+ getLabel("lblUnsubscribe");

        $userTypeRow.append("<td>"+ content +"</td>");
    }
}

/**
 * Checks whether or not showing the drivers data, and sets global variable.
 *
 * @param {Element} target
 */
function userTypeChanged(target) {
    _userType = $(target).val();
           
    (_userType == "pedestrian" || _userType == "0") ? $(".drives").hide("slow") : $(".drives").show("slow");
}


/*******************************************************
 * CONSTRUCTORS
 *******************************************************/

/**
 * Track
 *
 * @constructor
 */
function Track() {
    this.isPedestrian = false;
    this.trackIn      = "";
    this.hourIn       = "";
    this.trackOut     = "";
    this.hourOut      = "";
}

/**
 * Stop
 *
 * @constructor
 */
function Stop() {
    this.isPedestrian = true;
    this.stopIn       = "";
    this.hourIn       = "";
    this.stopOut      = "";
    this.hourOut      = "";
}

/**
 * Matrix
 *
 * @constructor
 */
function Matrix() {
    this.matrixIn  = "";
    this.matrixOut = "";                  
}

/**
 * Projection
 *
 * @constructor
 */
function Projection() {
    this.projIn  = "";
    this.projOut = "";                  
}

