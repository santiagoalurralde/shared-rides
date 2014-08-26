var _step           = -1,   //Contador de Pasos
    _previous       = "",
    _previous2      = "",
    _previousTarget = "",
    _userType,
    _days           = new Array(),
    _matrices       = new Array(),
    _projections    = new Array();

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

    //Pressing next or back
    $(".btn-next, .btn-back").click(function(){
        update(_step);
    });
   
    $(".btn-map").click(function(){
        saveMap();              
    });
   
    $(".btn-OK").click(function(){
        signUp();
    });
});

/**
 * Establishes the starting environment
 */
function start() {
    $(".map-driver, .map-pedestrian").hide();
   
    highlightStep(0);
   
    fillRowsInOut("in");            
    fillRowsInOut("out");
   
    var l,
        neighborhoods   = ["Alta Córdoba","Alto Alberdi","Alto Verde","Argüello","Bella Vista","Centro","Cerro Chico","Cerro de las Rosas","Cerveceros","Chateau Carreras","Cofico","Colinas del Cerro","Country Jockey Club","Country Las Delicias","Country Lomas de la Carolina","Crisol","Dean Funes","Ejército Argentino","El Quebracho","Empalme","Ferreyra","General Bustos","General Paz","General Pueyrredon","Granja De Funes","Güemes","Ituzaingo","Jardín","Juniors","La France","Las Flores","Las Palmas","Las Violetas","Los Boulevares","Los Paraísos","Marques De Sobremonte","Nueva Córdoba","Observatorio","Palermo Bajo","Patricios","Poeta Lugones","San Martín","San Vicente","Urca","Villa Belgrano","Villa Cabrera","Villa Centenario","Villa Warcalde","Yapeyu","Yofre"],
        brands          = ["Alfa-Romeo", "Audi", "BMW", "Citroen", "Chery", "Chevrolet", "Chrysler", "Daihatsu", "Dodge", "Fiat", "Ford", "Honda", "Hyundai", "Jeep", "Kia", "Land Rover", "Mazda", "Mercedes Benz", "Mini", "Mitsubishi", "Nissan", "Peugeot", "Renault", "Seat",  "Subaru", "Suzuki", "Toyota", "Volkswagen", "Volvo"];

    for(l=0; l<neighborhoods.length; l++)
        $("#neighborhood").append(  "<option value='"+ neighborhoods[l] +"'>"
                                        + neighborhoods[l] +
                                    "</option>");         
    for(l=0; l<brands.length; l++)
        $("#brand").append( "<option value='"+ brands[l] +"'>"
                                + brands[l] +
                            "</option>");  
}

/**
 * Checks if we can add 1 step
 */
function stepNext() {  
    if(_step == 0 && (!checkValues0() || $(".alerts.alert-pw-match").is(":visible") || checkUserExists()))
        console.log("No es posible avanzar al segundo paso");
    else if(_step == 1 && !checkValues1())
        console.log("No es posible avanzar al terccer paso");
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

 /**
 * Highlights current step
 *
 * @param {Number} step - current step
 */
function highlightStep(index) {
    var $step = $(".step-signup"+index);
    $step.css("opacity", "1");
    $step.siblings().css("opacity", ".2");
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
        ipts        = $(".step-first input"),
        lastElement = ipts.index($("#cellphone")),
        iptL;
   
    for(var l=0; l<lastElement+1; l++) {
        iptL = $(ipts[l]);
        flag = checkLength(iptL);
    }
    
    if(!flag) {
        //TODO spring message INCOMPLETE INPUTS
        var msg = "Los campos señalados están incompletos, debe llenarlos para proceder.";
        toggleAlert(true, msg, "alert-inputs");
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
        flag = checkLength($("#plate-letters"));
        flag = checkLength($("#plate-numbers"));
        flag = checkLength($("#model"));
        flag = checkLength($("#number-seats"));
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
        //TODO spring message INCOMPLETE INPUTS
        var msg = "Los campos señalados están incompletos, debe llenarlos para proceder.";
        toggleAlert(true, msg, "alert-inputs");
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
        var aux = d+1;
        if($(".chkActive"+aux).prop("checked"))
            _days[d] = "Unsuscribed";
        else    
            if(_days[d] == null) {
                var msg = "Quedan dias por completar";
                toggleAlert(true, msg, "alert-schedules");
                return false;
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

    $passwordFirst.on("change", function(event) {
        checkPassword();
    });

    if($passwordFirst.val() != $passwordCheck.val()) {
        paint($passwordFirst, true);    
        paint($passwordCheck, true);
        //TODO MESSAGE
        var msg = ("Las contraseñas deben coincidir");
        toggleAlert(true, msg, "alert-pw-match");    
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
    if($(".alerts").hasClass("alert-inputs"))
        toggleAlert(false, "", "alert-inputs");         
   
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
        return false;
    }
    else {
        paint($(target), false)
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
    if(flag)
        $target.addClass("painted");
    else
        $target.removeClass("painted");
}

/**
 * Shows or hides alert.
 *
 * @param {Bool} flag - TRUE for activating alert.
 * @param {String} msg - Text that the alert is going to contain.
 * @param {String} flag - Additional class for the alert.
 */
function toggleAlert(flag, msg, cls){
    var $alertPlusClass = $(".alerts."+cls);

    if(flag) {
        $(".alerts").addClass(cls);     
        $alertPlusClass.html("<p>"+ msg);
        $alertPlusClass.show("fast");          
    }
    else {
        $alertPlusClass.hide("fast");
        $alertPlusClass.removeClass(cls);       
    }
}

/**
 * Checks if value in element is smaller than limit
 *
 * @param {Element}
 * @param {val} Minimum limit
 */
function checkBounds(target, val) {
    if(target.value > val) {
        alert("Este campo no admite valores mayores a "+ val);
        target.value = _previous2;
    }
    else
        _previous2 = target.value;
}

/**
 * Checks if key pressed is in the alphabet
 *
 * @param {Element}
 * @param {val} Minimum limit
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
 * @param {Element}
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
        data: { personalId: pId }
        }).done(function(isValidate) {
            if(!isValidate) {
                //TODO MESSAGE
                var msg = "El usuario con el ID especificado ya existe";
                flag = true;                
                toggleAlert(flag, msg, "alert-inputs");
            }
    });
    return flag;
}

/**
 * Checks if hours are valid.
 */
function checkHours(target){
    var id      = $(target).attr("id"),
        d       = id.slice(0,1),
        io      = id.substring(1, id.length),
        flag    = true,
        hourOut = $("#"+ d +"out").find("option:selected").val(),
        hourIn  = $("#"+ d +"in").find("option:selected").val();
    
    if(io == "out")
        $("#"+ d +"in").on("change", function(event) {
            checkHours();
        }); 

    if(hourOut < hourIn){
        //TODO MESSAGE
        $(".map-definition").hide();
        var msg = "La hora de salida debe ser después de la hora de entrada";
        toggleAlert(true, msg, "alert-schedules");
        $(".btn-map").prop("disabled", true);
    }
    else {
        $(".map-definition").show();        
        toggleAlert(false, "", "alert-schedules");      
        $(".btn-map").prop("disabled", false);
    }
}

/**
 * Disables day if unsuscribed
 *  
 * @param {jquery} $target - checkbox that indicates if suscribed and has changed.
 */
function suscribes($target){
    var d       = $target.parent().index()+1,
        $btnIn  = $("#in td:nth-child("+ d +") #btnin"),
        $btnOut = $("#out td:nth-child("+ d +") #btnout");
 
    if($target.prop("checked")) {
        $btnIn.prop("disabled", true).removeClass("btnDefine").addClass("unsuscribed");
        $btnOut.prop("disabled", true).removeClass("btnDefine").addClass("unsuscribed");
    }
    else {
        $btnIn.prop("disabled", false).removeClass("unsuscribed").addClass("btnDefine");
        $btnOut.prop("disabled", false).removeClass("unsuscribed").addClass("btnDefine");              
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
           
            $.post("register.do", {"organization": org ,
                                   "personal-id":   pId,
                                   "pw":           pw,
                                   "name":         name,
                                   "surname":      surname,
                                   "email":        email,                                                                  
                                   "phone":        phone,  
                                   "street":       street,
                                   "number":       number,
                                   "neighborhood": nbh,
                                   "shift":        shift,
                                   "userType":     usType,
                                   "brand":        brand,
                                   "modelVehicle": modelVehicle,
                                   "plate-letters": plNumb,
                                   "plate-numbers": plLett,
                                   "number-seats":  nSeats,
                                   "days":         JSON.stringify(_days)
                                   },  
                function(str) {
                     //TODO message
                     alert("Alta de Usuario Completa");
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
        h           = $("#"+ d + io ).find("option:selected").val(),
        nthChild    = Number(d)+1,
        index       = Number(d)-1;

    if(h == "none") {
        //TODO MESSAGE
        alert("Selecciona la hora a definir");
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

        //TODO MESSAGE
        $("#"+ io + " td:nth-child("+ nthChild +")").find("#btn"+ io).html("Modificar");
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
            $("#"+ io +" td:nth-child("+ nthChild +")").find("#btn"+ io).html("Modificar");
        }
        else {
            alert("Selecciona un punto en el mapa");
            return false;
        }
    }
   
    $("input[name='chkActive'], .btnDefine").prop("disabled", false);              
    $("#"+ d + io ).prop("disabled", true);      
    $(".map-driver, .map-pedestrian, .map-definition").hide();    
}

/**
 * Opens type of map according to user type in day; and shows previously saved maps.
 */
function defineMap(target) {    
    var d           = $(target).parent().index(),
        io          = $(target).parent().parent().attr("id"),
        userTypeDay = $("#userType"+ d).find("option:selected").val(),                         
        index       = Number(d)-1;

    if(userTypeDay == "0") {  
        /* Hasn't selected any user type for this day. */
        //TODO MESSAGE
        var msg = "Seleccione el tipo de usuario de este día";
        toggleAlert(true, msg, "alert-schedule");
        return false;
    }
    toggleAlert(false, "", "alert-schedule");    

    $("input[name='chkActive'], .btnDefine").prop("disabled", true);                    
    $("#"+ d + io).prop("disabled", false);
   
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
 * @param {String} io - in/out
 */
function fillRowsInOut(io) {
    var button = "<button id='btn"+ io +"' class='btnDefine' " +
                    "onClick='defineMap(this); return false;'>Definir</button>",
        select = "";
           
    for(var d=1; d<6; d++) {
        /* Days */
        select = "<select id='"+ d + io +"' onchange='checkHours(this);'></select>";
        var $currentCell = $("#"+ d + io);

        $(".table-signup #"+ io).append("<td>"+ select + button +"</td>");
        $currentCell.append("<option value='none'>Hora</option>");                                  
       
        for(var j=0; j<24; j++) {
            /* Hours */
            $currentCell.append("<option value='"+ j +":00'>"+ j +":00 hs</option>");                      
            $currentCell.append("<option value='"+ j +":30'>"+ j +":30 hs</option>");
        }
       
        /* Disable hour selects -> Enabled when defining map */
        $currentCell.prop("disabled", true);              
    }
}

/**
 * Inserts type of user in first row
 */
function fillRowType() {
    var content      = "";   
        $userTypeRow = $(".usertype-row");
    
    $userTypeRow.html("<td> Tipo de Usuario </td>");

    for(var d=1; d<6; d++) {
        if(_userType == "driver-pedestrian") {
            /* Select for user type each day */
            //TODO SELECCIONAR MESSAGE
            content =   "<select id='userType"+ d +"'>"+
                            "<option value='0'>Seleccionar</option>"+
                            "<option value='pedestrian'>Peaton</option>"+      
                            "<option value='driver'>Conductor</option>"+
                        "</select>";
        }
        else if(_userType == "driver") {
            $("#hdn-usertype-day").val(_userType);
            //TODO CONDUCTOR MESSAGE            
            content = "Conductor";
        }
        else { /* _userType == "pedestrian" */
            $("#hdn-usertype-day").val(_userType); 
            //TODO PEATON MESSAGE            
            content = "Peaton";
        }
        content += "<br><br>";
        content += "<input type='checkbox' name='chkActive' class='chkActive"+ d +"'"+
                    "onclick='suscribes($(this));'>"+ $("#lbl-unsuscribe").val();
        $userTypeRow.append("<td>"+ content +"</td>");                      
    }
}

/**
 * Checks whether or not showing the drivers data, and sets global variable.
 *
 * @param {Element} target
 */
function userTypeChanged(target) {
    _userType = $(target).find("option:selected").val();
           
    if(_userType == "pedestrian" || _userType == "0")
        $(".drives").hide("slow");
    else
        $(".drives").show("slow");
}


/*******************************************************
 * CONSTRUCTORS
 *******************************************************/

/**
 * Constructor
 */
function Track() {
    this.isPedestrian   = false;
    this.trackIn        = "";
    this.hourIn         = "";  
    this.trackOut       = "";  
    this.hourOut        = "";          
}

/**
 * Constructor
 */
function Stop() {
    this.isPedestrian   = true;
    this.stopIn         = "";
    this.hourIn         = "";  
    this.stopOut        = "";
    this.hourOut        = "";          
}

/**
 * Constructor
 */
function Matrix() {
    this.matrixIn  = "";
    this.matrixOut = "";                  
}

/**
 * Constructor
 */
function Projection() {
    this.projIn  = "";
    this.projOut = "";                  
}

