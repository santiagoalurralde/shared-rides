var _listenerScheduleTarget;


/*******************************************************************************
 * FUNCTIONS
 ******************************************************************************/

/**
 * Cleans Tables at the beginning and after updating.
 */
function createTables() {
	var content = "<tr><th>"+ $('#lbl-user').val() +"</th></tr>";	
	$(".table-pending, .table-associated").html(content);
}

load();

/**
 * Receives associations from server and calls createTables().
 */
function load() {
	createTables();

	var $pending		 = $(".pending"),
		$associated      = $(".associated"),
		$tablePending    = $(".table-pending"),
		$tableAssociated = $(".table-associated"),
		$alertPending 	 = $(".alert-pending"),
		$alertAssociated = $(".alert-associated");

	/* Brings people info. JsonArray with 2 JsonArray: One with pending and another one 
	 * with accepted associations. Each one contains association id and full name.
	 */
	$.post("loadAssociations.do", 
		function(json) {
			var jsonNew = $.parseJSON(json);
		
			//Pending
			$.each(jsonNew[0], function(i, data) {
				var applicant =	"<div>"+
									"<a href='/Shared-Rides/profile.do?user="+ data.userId +"'>"+
										"<img src='printImgFile.do?pic="+ data.pic +"'>"+
									"</a>"+
									"<span>"+ data.name +"</span>"+
									"<input type='hidden' id="+ data.userId +">"+ 			
									"<button class='btn btn-pending' onclick='listenerSchedule(this)'>"+ $('#lbl-request').val() +"</button>"+
								"</div>";
	
				$tablePending.append("<tr><td>"+ applicant +"</td></tr>").show();
				$alertPending.hide();
			});		
			
			//Associated
			$.each(jsonNew[1], function(i, data) {
				var friend =	"<div>"+
									"<a href='/Shared-Rides/profile.do?user="+ data.userId +"'>"+
										"<img src='printImgFile.do?pic="+ data.pic +"'>"+
									"</a>"+
									"<span>"+ data.name +"</span>"+
									"<input type='hidden' id="+ data.userId +">"+ 	
									"<button class='btn btn-associated' onclick='listenerSchedule(this)'>"+ $('#lbl-association').val() +"</button>"+
								"</div>";
	
				$tableAssociated.append("<tr><td>"+ friend +"</td></tr>").show();
				$alertAssociated.hide();
			});	
			
			if(jsonNew[0] == "" && $pending.find(".alerts").length == 0) {
				$("<div></div>", {
					"class": "alerts alert-pending",
					html: "<img src='resources/images/message.png'> <p><br>"+ $('#lbl-norequests').val()
				}).appendTo($pending);

				$tablePending.hide();
			}
			if(jsonNew[1] == "" && $associated.find(".alerts").length == 0) {
				$("<div></div>", {
					"class": "alerts alert-associated",
					html: "<img src='resources/images/message.png'> <p><br>"+ $('#lbl-noassocs').val(),
				}).appendTo($associated);						

				$tableAssociated.hide();
			}
	});
}

/**
 * Performs all the work by calling other functions
 *
 * @param {string} json - list of users
 * @param {number} typeAssoc - type of association
 */
function viewSchedule(json, typeAssoc) {	
	var jsonNew		= $.parseJSON(json),
		days		= new Array(),
		$requested 	= $(".requested"),
		$offered 	= $(".offered");
	
	days[7] = null;	
	
	$.each(jsonNew.requested, function(i, data) {	
		fetchDays(days, data);
	});	
	printSchedule(days, $(".table-requested"), typeAssoc);
	
	days		= new Array();
	days[7] 	= null;	
	
	$.each(jsonNew.offered, function(i, data) {	
		fetchDays(days, data);
	});
	printSchedule(days, $(".table-offered"), typeAssoc);
	
	if(jsonNew.requested == "") {
		$requested.hide();
		$requested.siblings().css({"float":"none", "width":"100%"});
	}
	if(jsonNew.offered == "") {
		$offered.hide();
		$offered.siblings().css({"float":"none", "width":"100%"});
	}
}

/**
 * Fills up array "days" with objects
 *
 * @param {array} days - received empty.
 * @param {object} data - contains association and hour for a certain day.
 */
function fetchDays(days, data) {
	if(days[data.day] == null)
		days[data.day] = new Day();

	//in
	if(data.inout == 1)	{
		days[data.day].inHour 		= data.hour;
		days[data.day].assocIdIn	= data.assocId;
	}
	else {
		days[data.day].outHour 		= data.hour;
		days[data.day].assocIdOut 	= data.assocId;
	}	
}

/**
 * Prints both schedules
 *
 * @param {array} days - each cell is a day and contains hour and association id.
 * @param {jquery} $table - table that is going to be printed.
 * @param {number} typeAssoc - [0|1] Pending or Associated 
 */
function printSchedule(days, $table, typeAssoc) {		
	
	var	rIn 			= "<tr id='in'><td>"+ $('#lbl-arrival').val() +"</td></tr>", 
		rOut 			= "<tr id='out'><td>"+ $('#lbl-departure').val() +"</td></tr>",
		bCancelAssoc	=	"<button title='Cancel Association' onclick='actionAssociation(this, false)'>" +
								"<img src='resources/images/cancel.png'>" +
							"</button>",
	    bCancelPetit	= 	"<button title='Cancel Petition' onclick='actionAssociation(this, false)'>" +
								"<img src='resources/images/cancel.png'>" +
							"</button>",
		bAcceptPetit	= 	"<button title='Accept Petition' onclick='actionAssociation(this, true)'>" +
								"<img src='resources/images/accept.png'>" +
							"</button>",
		$rowIn			=	$table.find("#in"),
		$rowOut			=	$table.find("#out"),
		$rowDays		= 	$table.find("tr:first"),
		$scheduleData 	= 	$(".schedule-data");

	if(!$scheduleData.is(":visible"))			//Display schedules' section
		$scheduleData.show("bounce", 400);
	
	$table.html("<tr><th></th></tr>");
	
	for(var i=1; i<days.length; i++) {
		if(days[i]!=null) {
			var buttons;
			
			$table.find("tr:first").append("<th id='"+ i +"'>"+ getDayLabel(i) +"</th>"); 		//Create label for current day
			
			if(!$rowIn.length)		//If IN Row doesn't exist, create it
				$table.append(rIn);
			
			if(!$rowOut.length)		//If OUT Row doesn't exist, create it
				$table.append(rOut);

			buttons = (typeAssoc == 0) ? bAcceptPetit + bCancelPetit : bCancelAssoc;
			
			var contentIn,
				contentOut,
				$inputIn,
				$inputOut;

			if(days[i].inHour != "" && days[i].outHour == "") {	     
				//Just IN
				$inputIn = 
					$("<input type='hidden'>", {
						"class": "assoc-id",
						value: days[i].assocIdIn 
					});

				contentIn = days[i].inHour + buttons;
				
				$rowIn.append("<td>"+ contentIn +"</td>");
				$inputIn.appendTo($rowIn);
				$rowOut.append("<td></td>"); 			
			}
			else if(days[i].inHour != "" && days[i].outHour != "") {
				//IN & OUT

				$inputIn = 
					$("<input type='hidden'>", {
						"class": "assoc-id",
						value: days[i].assocIdIn 
					});
				$inputOut = 
					$("<input type='hidden'>", {
						"class": "assoc-id",
						value: days[i].assocIdOut 
					});

				contentIn 	= days[i].inHour + buttons;
				contentOut 	= days[i].outHour + buttons;
								
				$rowIn.append("<td>"+ contentIn +"</td>");		
				$inputIn.appendTo($rowIn);		
				$rowOut.append("<td>"+ contentOut +"</td>");
				$inputOut.appendTo($rowOut);
			}
			else {	
				//Just OUT
				$inputOut = 
					$("<input type='hidden'>", {
						"class": "assoc-id",
						value: days[i].assocIdOut 
					});

				contentOut = days[i].outHour + buttons;
				
				$rowIn.append("<td></td>");
				$rowOut.append("<td>"+ contentOut +"</td>");
				$inputIn.appendTo($rowOut);				
			}
		}
	}	
}


/*******************************************************************************
 * EVENTS
 ******************************************************************************/

/**
 * Receives button object that shares cell with input that contains id.
 *
 * @param {DOM} target - button that was pressed for viewing schedule.
 */
function listenerSchedule(target) {
	_listenerScheduleTarget = target;
	
	$userId = $(target).siblings("input").attr("id");
	
	if($(target).hasClass("btn-pending"))
		$.post("viewSchedule.do", {"userId": $userId , "typeAssoc": 0}, 
			function(json) {
				viewSchedule(json, 0);
			}
		);
	else
		$.post("viewSchedule.do", {"userId": $userId , "typeAssoc": 1}, 
			function(json) {
				viewSchedule(json, 1);
			}
		);	
}

/**
 * Sends whether the request was cancelled or accepted to the server.
 *
 * @param {DOM} target - button where action was defined.
 * @param {string} action - Accept or Cancel
 */
function actionAssociation(target, action) {			
	var $assocId = $(target).siblings(".assoc-id").val();
		
	$.post("responseAssoc.do", {"assocId": $assocId, "response": action}, 
		function() {
			alert($('#lbl-action').val());
			load();
			listenerSchedule(_listenerScheduleTarget);
		}
	);
}


/*******************************************************************************
 * CONSTRUCTORS
 ******************************************************************************/

/**
 * Represents a day and contains the associations and hours of them.
 *
 * @constructor
 */
function Day() { 
	this.assocIdIn	= "";
	this.assocIdOut	= "";		
  	this.inHour 	= "";
  	this.outHour 	= "";
}


