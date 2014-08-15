var _schPed = [];
var _schDriver = [];

/*******************************************************************************
 * FIXES
 ******************************************************************************/

if ($("#valDriver").val() === 'false') // It's not a driver
	fixView($('#pedestrianData'), $('#driverData'));

if ($("#valPedestrian").val() === 'false') // It's not a pedestrian
	fixView($('#driverData'), $('#pedestrianData'));

/*******************************************************************************
 * EVENTS
 ******************************************************************************/

$(function() {
	$(document).tooltip(
			{
				position : {
					my : "center bottom-20",
					at : "center top",
					using : function(position, feedback) {
						$(this).css(position);
						$("<div>").addClass("arrow")
								.addClass(feedback.vertical).addClass(
										feedback.horizontal).appendTo(this);
					}
				}
			});
});

$(document).ready(function() {

	fillTable(_schDriver, "Driver");
	fillTable(_schPed, "Pedestrian");

	// Fancybox
	$(".ifancybox").fancybox({
		'width' : 500,
		'height' : 330,
		'autoSize' : false,
		'fitToView' : false,
		'type' : 'iframe'
	});

	// If it's my profile, it shouldn't rate
	if ($("#valMine").val() == "true") {
		$(".star").unwrap();
	}

	// Request Association
	$(".btnRequestAssoc").click(function() {
		if (confirm("Confirma el envío de esta petición")) {
			requestAssociation(this);
			$(this).prop("disabled", true);
			checkDisabled($(this));
		}
	});

	// Show Current Map
	$(".cellCheckMap").click(function() {
		showMap(this);
	});
});

function checkDisabled($target) {
	if ($target.prop("disabled"))
		$target.find("img").attr("src", "resources/images/disabled.png");
	else {
		if($target.closest("table").attr("id") == "tablePed")
			$target.find("img").attr("src", "resources/images/steering.png");
		else
			$target.find("img").attr("src", "resources/images/seat.png");
	}
}

/*******************************************************************************
 * FUNCTIONS
 ******************************************************************************/

/**
 * Fixes profile display according to user type.
 * 
 * @param {jquery}
 *            $targetThis - div that's gonna stay.
 * @param {jquery}
 *            $targetOther - div that's gonna be hidden.
 */
function fixView($targetThis, $targetOther) {
	$targetOther.css("display", "none");
	$targetThis.css("float", "none").css("width", "100%").css("text-align",
			"left");
	$('.star').css("float", "left").css("margin-right", "1%").css(
			"margin-left", "0%");
	$('.theRating').css("margin", "2% 0% 0% 0%");
	$('#profileData').css("padding-right", "70px").css("padding-left", "70px");
	$('#line').css("display", "none");
	$('.mapStatic').css("height", "400px").css("width", "760px");
	$('.mapContainer').css("margin-left", "0px");
}

/**
 * Completes individual schedule table.
 * 
 * @param {array}
 *            schedule - array that contains each day
 * @param {string}
 *            type - userType (side of schedule)
 */
function fillTable(schedule, type) {

	var $table, image, btnReqIn, btnReqOut;

	btnReqIn = "";
	btnReqOut = "";

	if (type == "Driver") {
		$table = $("#tableDriver");
		image = "seat.png";
	} else {
		$table = $("#tablePed");
		image = "steering.png";
	}

	// Create Rows
	$table.append("<tr id='rDay'></tr><tr id='rIn'></tr><tr id='rOut'></tr>");
	$table.find("#rDay").append("<th></th>");
	$table.find("#rIn").append('<td>' + $('#lblArrival').val() + '</td>');
	$table.find("#rOut").append('<td>' + $('#lblDeparture').val() + '</td>');

	for (var i = 0; i < schedule.length; i++) {
		var hdnsMapIn, hdnsMapOut;
		var hdnDay = '<input class="hdnDay"	type="hidden" value="'
				+ schedule[i].day + '"/>';
		var hdnIn = '<input class="hdnInOut" 	type="hidden" value="1"/>';
		var hdnOut = '<input class="hdnInOut"	type="hidden" value="2"/>';

		if ($("#valMine").val() == "false") {
			// If it's my profile can't invite myself
			btnReqIn = '<button class="btnRequestAssoc" id="btnReqIn'
					+ schedule[i].day +'" onChange="checkDisabled($(this));">'
					+ '<img src="resources/images/' + image + '"/>'
					+ '</button>';
			btnReqOut = '<button class="btnRequestAssoc" id="btnReqOut'
					+ schedule[i].day +'" onChange="checkDisabled($(this));">'
					+ '<img src="resources/images/' + image + '"/>'
					+ '</button>';
		}

		if (type == "Pedestrian") {
			hdnsMapIn = '<input class="hdnLat"	type="hidden" value="'
					+ schedule[i].latIn + '"/>'
					+ '<input class="hdnLon"	type="hidden" value="'
					+ schedule[i].lonIn + '"/>';
			hdnsMapOut = '<input class="hdnLat"	type="hidden" value="'
					+ schedule[i].latOut + '"/>'
					+ '<input class="hdnLon"	type="hidden" value="'
					+ schedule[i].lonOut + '"/>';
		} 
		else {
			hdnsMapOut = '<input class="hdnPath"	type="hidden" value="'
					+ schedule[i].pathIn + '"/>';
			hdnsMapIn = '<input class="hdnPath"	type="hidden" value="'
					+ schedule[i].pathOut + '"/>';
		}

		$table.find("#rDay").append(
				"<th>" + getDayLabel(schedule[i].day) + "</th>");
		$table.find("#rIn").append(
				"<td class='cellCheckMap'>" + schedule[i].hourIn + btnReqIn
						+ hdnDay + hdnIn + hdnsMapIn + "</td>");
		$table.find("#rOut").append(
				"<td class='cellCheckMap'>" + schedule[i].hourOut + btnReqOut
						+ hdnDay + hdnOut + hdnsMapOut + "</td>");

		delete hdnsMapIn, hdnsMapOut, hdnDay, hdnIn, hdnOut;
	}

	$(".cellCheckMap").prop("title", "Ver mapa en este horario");
	
	disableRequests(schedule, type);
}

function disableRequests(schedule, type){

	for (var i = 0; i < schedule.length; i++) {
		if (type == "Pedestrian") {
			// If Pedestrian has driver, can't invite him.
			if (schedule.hasDriverIn == true || schedule[i].allowIn) {
				alert(schedule.hasDriverIn);
				alert(schedule[i].allowIn);				
				$("#btnReqIn"+schedule[i].day).prop("disabled", true);
				checkDisabled($("#btnReqIn"+ schedule[i].day));
			}
			if (schedule.hasDriverOut == true || schedule[i].allowOut) {
				alert(schedule.hasDriverOut);
				alert(schedule[i].allowOut);
				$("#btnReqOut"+schedule[i].day).prop("disabled", true);
				checkDisabled($("#btnReqOut"+ schedule[i].day));				
			}
		} 
		else {
			if (schedule.freeSeatsIn == 0 || schedule[i].allowIn) {
				alert(schedule.freeSeatsIn);
				alert(schedule[i].allowIn);				
				$("#btnReqIn"+schedule[i].day).prop("disabled", true); // DISABLE!!!!!!!!!!!
				checkDisabled($("#btnReqIn"+ schedule[i].day));
			}
			if (schedule.freeSeatsOut == 0 || schedule[i].allowOut) {
				alert(schedule.freeSeatsOut);
				alert(schedule[i].allowOut);					
				$("#btnReqOut"+schedule[i].day).prop("disabled", true);
				checkDisabled($("#btnReqOut"+ schedule[i].day));
			}
		}
	}
}

/**
 * Shows current map according to selected cell.
 * 
 * @param {javascript}
 *            target - clicked cell.
 */
function showMap(target) {
	if ($(target).closest("table").attr("id") == "tablePed")
		setMapPedestrian($(target).find(".hdnLon").val(), $(target).find(
				".hdnLat").val());
	else
		setMapDriver($(target).find(".hdnPath").val());
}

/**
 * Requests an association according to selected cell.
 * 
 * @param {javascript}
 *            target - clicked button.
 */
function requestAssociation(target) {
	var $day = $(target).parent().find(".hdnDay").val();
	var $inOut = $(target).parent().find(".hdnInOut").val();
	var $idUser = $("#valId").val();

	if ($inOut == "in") {
		$("#btnReqIn" + $day).prop("disabled", true);
		checkDisabled($("#btnReqIn" + schedule[i].day));
	}
	if ($inOut == "out") {
		$("#btnReqOut" + $day).prop("disabled", true);		
		checkDisabled($("#btnReqIn" + schedule[i].day));
	}

	$.post("requestAssoc.do", {
		"day" : $day,
		"inout" : $inOut,
		"idUser" : $idUser
	}, function(msg) {
		if (msg != '')
			window.alert(msg);
	});
}

/*******************************************************************************
 * CONSTRUCTORS
 ******************************************************************************/

/**
 * Constructor
 */
function DetailSchedulePedestrian() {
	this.day = "";
	this.allowIn = "";
	this.allowOut = "";
	this.hourIn = "";
	this.hourOut = "";
	this.hasDriverIn = false;
	this.hasDriverOut = false;
	this.pathIn = "";
	this.pathOut = "";
}

/**
 * Constructor
 */
function DetailScheduleDriver() {
	this.day = "";
	this.allowIn = "";
	this.allowOut = "";
	this.hourIn = "";
	this.hourOut = "";
	this.freeSeatsIn = "";
	this.freeSeatsOut = "";
	this.latIn = "";
	this.latOut = "";
	this.lonIn = "";
	this.lonOut = "";
}