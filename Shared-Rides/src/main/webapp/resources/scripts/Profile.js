var	_schPed 		= [], 
	_schDriver      = [],
	$pedestrianData = $(".pedestrian-data"),
	$driverData     = $(".driver-data");


/*******************************************************************************
 * FIXES
 ******************************************************************************/

if ($("#val-driver").val() == "false") {	// It's not a driver
	fixView($pedestrianData);
}
else if ($("#val-pedestrian").val() == "false") {	// It's not a pedestrian
	fixView($driverData);
}
else {
	$.merge($pedestrianData, $driverData).show();
}

/*******************************************************************************
 * EVENTS
 ******************************************************************************/

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

    // Tooltip
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

	// If it's my profile, it shouldn't rate
	if ($("#val-mine").val() == "true") {
		$(".star").unwrap();
	}

	// Request Association
	$(".btn-request-assoc").click(function() {
		var $this = $(this);
		if (confirm(getLabel("lblAlertConfirm"))) {
			requestAssociation($this);
		}
	});

	// Show Current Map
	$(".cell-check-map").click(function() {
		showMap($(this));
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
	// FLAG: 
	// 0 Mine
	// 1 Not allowed (X)
	// 2 Allowed (Steering or Seat) 
	// 3 Sent and not responded (Clock)
	// 4 Sent and Accepted (Tick)
	
	if (flag == 1) {
		$target.prop("disabled", true);
		$img.attr("src", "resources/images/disabled.png");		
	}
	else if (flag == 2) {
		$target.prop("disabled", false);
		if ($target.closest("table").hasClass("table-ped")) {
			$img.attr("src", "resources/images/steering.png");
		}
		else {
			$img.attr("src", "resources/images/seat.png");
		}
	}
	else if (flag == 3){
		$target.prop("disabled", true);
		$img.attr("src", "resources/images/clock.png");				
	}
	else if (flag == 4){
		$target.prop("disabled", true);
		$img.attr("src", "resources/images/accepted.png");			
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
	$target.siblings()
           .hide();
	
	$target.css({"float":"none", "width":"100%", "text-align":"left"})
           .show();

	$(".sr-schedule").css("font-size", "85%");
	$(".star").css({"float":"left", "margin-right":"1%", "margin-left":"0"});
	$(".rating").css("margin", "2% 0 0 0");
	$(".profile-data").css({"padding-right":"70px", "padding-left":"70px"});
	$(".pedestrian-data").css("margin-left", "0");
	$(".map-static").css({"height":"400px", "width":"760px"});
	$(".map-container").css("margin-left", "0");
}

/**
 * Completes individual schedule table.
 * 
 * @param {string} type - userType (side of schedule)
 */
function fillTable(schedule, isDriver) {
	var $table, 
		image, 
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
	var	$rowDay  = $table.find(".row-day"),
		$rowOut  = $table.find(".row-out"),
		$rowIn   = $table.find(".row-in");

	for (var i=0; i<schedule.length; i++) {
		
		$("<th></th>", {
			html	: getDayLabel(schedule[i].day)	
		}).appendTo($rowDay);
		
		$("<td></td>", {
			class	: "cell-check-map",
			html	: schedule[i].hourIn
		}).appendTo($rowIn);
		
		$("<td></td>", {
			class	: "cell-check-map",
			html	: schedule[i].hourOut
		}).appendTo($rowOut);
		
		var	$lastCellIn = $rowIn.find(".cell-check-map:last-child"),
			$lastCellOut = $rowOut.find(".cell-check-map:last-child");
		
		if ($valMine == "false") {
			// If it's my profile can't invite myself
			$("<button></button>", {
				class	: "btn-request-assoc btn-req-in"+ schedule[i].day,
				html	: "<img src='resources/images/"+ image +"'/>"
			}).appendTo($lastCellIn);
			
			$("<button></button>", {
				class	: "btn-request-assoc btn-req-out"+ schedule[i].day,
				html	: "<img src='resources/images/"+ image +"'/>"
			}).appendTo($lastCellOut);
		}

		if (!isDriver) {
			$("<input>", {
				type	: "hidden",
				class	: "hdn-lat",
				value	: schedule[i].latIn
			}).appendTo($lastCellIn);
			
			$("<input>", {
				type	: "hidden",
				class	: "hdn-lon",
				value	: schedule[i].lonIn
			}).appendTo($lastCellIn);
			
			$("<input>", {
				type	: "hidden",
				class	: "hdn-lat",
				value	: schedule[i].latOut
			}).appendTo($lastCellOut);
			
			$("<input>", {
				type	: "hidden",
				class	: "hdn-lon",
				value	: schedule[i].lonOut
			}).appendTo($lastCellOut);			
		} 
		else {
			$("<input>", {
				type	: "hidden",
				class	: "hdn-path",
				value	: schedule[i].pathIn
			}).appendTo($lastCellIn);
			
			$("<input>", {
				type	: "hidden",
				class	: "hdn-path",
				value	: schedule[i].pathOut
			}).appendTo($lastCellOut);
		}
	}

	$(".cell-check-map").prop("title", getLabel("lblTipCheckMap"));
	
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
		var	$btnReqIn	= $(".btn-req-in"+ schedule[i].day),
			$btnReqOut	= $(".btn-req-out"+ schedule[i].day);

		if (!isDriver) {
			// If Pedestrian has driver, can't invite him.
			if (schedule[i].hasDriverIn == "true" || schedule[i].allowIn != 2) {
				disable($btnReqIn, schedule[i].allowIn);
			}
			if (schedule[i].hasDriverOut == "true" || schedule[i].allowOut != 2) {
				disable($btnReqOut, schedule[i].allowIn);
			}
		} 
		else {
			// If Driver has no seats, can't invite him.
			if (schedule[i].freeSeatsIn == 0 || schedule[i].allowIn == 2) {		
				disable($btnReqIn, schedule[i].allowIn);
			}
			if (schedule[i].freeSeatsOut == 0 || schedule[i].allowOut == 2) {				
				disable($btnReqOut, schedule[i].allowIn);
			}
		}
	}
}

/**
 * Shows current map according to selected cell.
 * 
 * @param {jquery} $target - clicked cell.
 */
function showMap($target) {
	var	isPedestrian 	= $target.closest("table").hasClass("table-ped"),
		hdnLon 			= $target.find(".hdn-lon").val(),
		hdnLat 			= $target.find(".hdn-lat").val(),
		hdnPath			= $target.find(".hdn-path").val();
	
	if (isPedestrian) {
		setMapPedestrian(hdnLon, hdnLat);
	}
	else {
		setMapDriver(hdnPath);
	}
}

/**
 * Requests an association according to selected cell.
 * 
 * @param {jquery} $target - clicked button.
 */
function requestAssociation($target) {
	var day    		= $target.closest("td").index(),
		inOut  		= ($target.closest("tr").hasClass("row-in")) ? "0" : "1",
		idUser 		= $("#val-id").val(),
		$btnReqIn	= $(".btn-req-in"+ day),
		$btnReqOut	= $(".btn-req-out"+ day);

	if (inOut == "0") {
		disable($btnReqIn, 3);
	}
	else {
		disable($btnReqOut, 3);
	}

	$.post("requestAssoc.do",
			{
				"day"	: day,
				"inout"	: inOut,
				"idUser": idUser
			}, 
			function(msg) {
				if (msg != "") {
					alert(msg);
				}
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