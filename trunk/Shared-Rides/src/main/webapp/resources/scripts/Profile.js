var	_schPed 		= [], 
	_schDriver      = [],
	$pedestrianData = $(".pedestrian-data"),
	$driverData     = $(".driver-data");


/*******************************************************************************
 * FIXES
 ******************************************************************************/

if ($("#val-driver").val() == "false") // It's not a driver
	fixView($pedestrianData);
else if ($("#val-pedestrian").val() == "false") // It's not a pedestrian
	fixView($driverData);
else
	$.merge($pedestrianData, $driverData).show();

/*******************************************************************************
 * EVENTS
 ******************************************************************************/

$(function() {
	$(document).tooltip({
			position : {
				my 		: "center bottom-20",
				at  	: "center top",
				using	: function(position, feedback) {
					$(this).css(position);
					$("<div>").addClass("arrow").addClass(feedback.vertical).addClass(feedback.horizontal).appendTo(this);
				}
			}
	});
});

$(document).ready(function() {

	fillTable(_schPed, false);
	fillTable(_schDriver, true);

	// Fancybox
	$(".ifancybox").fancybox({
		'width' 	: 500,
		'height' 	: 330,
		'autoSize' 	: false,
		'fitToView' : false,
		'type' 		: 'iframe'
	});

	// If it's my profile, it shouldn't rate
	if ($("#val-mine").val() == "true")
		$(".star").unwrap();

	// Request Association
	$(".btn-request-assoc").click(function() {
		//TODO MESSAGE
		if (confirm("Confirma el envío de esta petición")) {
			requestAssociation(this);
			disable($(this), true);
		}
	});

	// Show Current Map
	$(".cell-check-map").click(function() {
		showMap(this);
	});
});

/**
 * Sets disabled button.
 * 
 * @param {jquery} $target 	- button that can be disabled.
 * @param {Boolean} flag 	- TRUE for disabling.
 */
function disable($target, flag) {
	var $img = $target.find("img");

	if (flag){
		$target.prop("disabled", true);
		$img.attr("src", "resources/images/disabled.png");
	}
	else {
		$target.prop("disabled", false);
		if($target.closest("table").hasClass("table-ped"))
			$img.attr("src", "resources/images/steering.png");
		else
			$img.attr("src", "resources/images/seat.png");
	}
}

/*******************************************************************************
 * FUNCTIONS
 ******************************************************************************/

/**
 * Fixes profile display according to user type.
 * 
 * @param {jquery} $targetThis - div that's gonna stay.
 * @param {jquery} $targetOther - div that's gonna be hidden.
 */
function fixView($target) {
	$target.siblings().hide();
	$target.css({"float":"none", "width":"100%", "text-align":"left"}).show();
	$(".sr-schedule").css("font-size", "85%");
	$('.star').css({"float":"left", "margin-right":"1%", "margin-left":"0"});
	$('.rating').css("margin", "2% 0 0 0");
	$('.profile-data').css({"padding-right":"70px", "padding-left":"70px"});
	$('.map-static').css({"height":"400px", "width":"760px"});
	$('.map-container').css("margin-left", "0");
}

/**
 * Completes individual schedule table.
 * 
 * @param {string} type - userType (side of schedule)
 */
function fillTable(schedule, isDriver) {
	var $table, 
		image, 
		hdnsMapIn, 
		hdnsMapOut,	
		$valMine = $("#val-mine").val();
	
	if (isDriver) {
		$table = $(".table-driver");
		image = "seat.png";
	}
	else {
		$table = $(".table-ped");
		image = "steering.png";
	}

	// Create Rows
	$table.append("<tr id='day'></tr><tr id='in'></tr><tr id='out'></tr>");
	var	$rowDay  = $table.find("#day"),
		$rowOut  = $table.find("#out"),
		$rowIn   = $table.find("#in");

	$rowDay.append("<th></th>");
	$rowIn.append("<td>"+ $("#lbl-arrival").val() +"</td>");
	$rowOut.append("<td>"+ $("#lbl-departure").val() +"</td>");

	for (var i=0; i<schedule.length; i++) {
		var	btnReqIn	= "", 
			btnReqOut	= "";

		if ($valMine == "false") {
			// If it's my profile can't invite myself
			btnReqIn	=	
				"<button class='btn-request-assoc btn-req-in"				+
					schedule[i].day +"' onclick='disable($(this), true);'>"	+
					"<img src='resources/images/"+ image +"'/>"				+
				"</button>";
			btnReqOut	= 	
				"<button class='btn-request-assoc btn-req-out"				+
					schedule[i].day +"' onclick='disable($(this), true);'>"	+
					"<img src='resources/images/"+ image +"'/>"				+
				"</button>";
		}

		if (!isDriver) {
			hdnsMapIn 	=	
				"<input class='hdn-lat' type='hidden' value='"	+
					schedule[i].latIn	+"'/>"					+
				"<input class='hdn-lon' type='hidden' value='"	+
					schedule[i].lonIn	+"'/>";	
			hdnsMapOut	=
				"<input class='hdn-lat' type='hidden' value='"	+
					schedule[i].latOut	+"'/>"					+
			 	"<input class='hdn-lon' type='hidden' value='"	+
					schedule[i].lonOut	+"'/>";						
		} 
		else {
			hdnsMapOut	= 	
			"<input class='hdn-path' type='hidden' value='"	+
				schedule[i].pathIn	+"'/>";
			hdnsMapIn 	= 	
			"<input class='hdn-path' type='hidden' value='"	+
				schedule[i].pathOut	+"'/>";
		}

		$rowDay.append("<th>"+ getDayLabel(schedule[i].day) +"</th>");
		$rowIn.append(
			"<td class='cell-check-map'>"+ 
				schedule[i].hourIn + btnReqIn + hdnsMapIn + 
			"</td>");
		$rowOut.append(
			"<td class='cell-check-map'>"+ 
				schedule[i].hourOut + btnReqOut	+ hdnsMapOut + 
			"</td>");
	}

	//TODO MESSAGE
	$(".cell-check-map").prop("title", "Ver mapa en este horario");
	
	disableRequests(schedule, isDriver);
}

/**
 * Disables requests buttons according to previous requests.
 *
 * @param {array} schedule - array with days' length that contains parameters that allow requests or not.
 * @param {string} type - user type ["Pedestrian" | "Driver"].
 */
function disableRequests(schedule, isDriver) {

	for (var i=0; i<schedule.length; i++) {
		var $btnReqIn	= $(".btn-req-in"+ schedule[i].day),
			$btnReqOut	= $(".btn-req-out"+ schedule[i].day);

		if (!isDriver) {
			// If Pedestrian has driver, can't invite him.
			if (schedule[i].hasDriverIn == "true" || schedule[i].allowIn == "false")
				disable($btnReqIn, true);
			if (schedule[i].hasDriverOut == "true" || schedule[i].allowOut == "false")
				disable($btnReqOut, true);
		} 
		else {
			// If Driver has no seats, can't invite him.
			if (schedule[i].freeSeatsIn == 0 || schedule[i].allowIn == "false")		
				disable($btnReqIn, true);
			if (schedule[i].freeSeatsOut == 0 || schedule[i].allowOut == "false")				
				disable($btnReqOut, true);
		}
	}
}

/**
 * Shows current map according to selected cell.
 * 
 * @param {javascript} target - clicked cell.
 */
function showMap(target) {
	if ($(target).closest("table").hasClass("table-ped"))
		setMapPedestrian($(target).find(".hdn-lon").val(), $(target).find(".hdn-lat").val());
	else
		setMapDriver($(target).find(".hdn-path").val());
}

/**
 * Requests an association according to selected cell.
 * 
 * @param {javascript} target - clicked button.
 */
function requestAssociation(target) {
	var day    		= $(target).closest("td").index(),
		inOut  		= ($(target).closest("tr").attr("id") == "in") ? "0" : "1";
		idUser 		= $("#val-id").val(),
		$btnReqIn	= $(".btn-req-in"+ day),
		$btnReqOut	= $(".btn-req-out"+ day);

//TRY THIS OUT
//
//$(target).closest("td").index()+1

	if (inOut == "0")
		disable($btnReqIn, true);
	if (inOut == "1")
		disable($btnReqOut, true);

	$.post("requestAssoc.do",
			{
				"day"	: day,
				"inout"	: inOut,
				"idUser": idUser
			}, 
			function(msg) {
				if (msg != "")
					alert(msg);
	});
}

/*******************************************************************************
 * CONSTRUCTORS
 ******************************************************************************/

/**
 * Constructor
 */
function DetailSchedulePedestrian() {
	this.day          = "";
	this.allowIn      = "";
	this.allowOut     = "";
	this.hourIn       = "";
	this.hourOut      = "";
	this.hasDriverIn  = "";
	this.hasDriverOut = "";
	this.pathIn       = "";
	this.pathOut      = "";
}

/**
 * Constructor
 */
function DetailScheduleDriver() {
	this.day          = "";
	this.allowIn      = "";
	this.allowOut     = "";
	this.hourIn       = "";
	this.hourOut      = "";
	this.freeSeatsIn  = 0;
	this.freeSeatsOut = 0;
	this.latIn        = "";
	this.latOut       = "";
	this.lonIn        = "";
	this.lonOut       = "";
}