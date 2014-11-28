var _listenerScheduleTarget;


/*******************************************************
 * FUNCTIONS
 *******************************************************/

/**
 * Cleans Tables at the beginning and after updating.
 */
function createTables() {
	var contentResetter = $("<tr></tr>", {
		html: "<th>"+ getLabel("lblUser") +"</th>"
	});
	$(".table-pending, .table-associated").html(contentResetter);
}

load();

/**
 * Receives associations from server, calls createTables().
 */
function load() {
	createTables();

	var	$pending		 = $(".pending"),
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
			var users = $.parseJSON(json);
		
			//Pending
			$.each(users[0], function(i, user) {
				var applicant =	"<a href='/Shared-Rides/profile.do?user="+ user.userId +"'>"+
									"<img src='printImgFile.do?pic="+ user.pic +"'>"+
								"</a>"+
								"<span>"+ user.name +"</span>"+
								"<input type='hidden' id="+ user.userId +">"+
								"<button class='btn btn-pending' onclick='listenerSchedule(this)'>"+ getLabel("lblRequest") +"</button>";
	
				$tablePending.append("<tr><td><div>"+ applicant +"</div></td></tr>").show();
				$alertPending.hide();
			});		
			
			//Associated
			$.each(users[1], function(i, user) {
				var friend =	"<a href='/Shared-Rides/profile.do?user="+ user.userId +"'>"+
									"<img src='printImgFile.do?pic="+ user.pic +"'>"+
								"</a>"+
								"<span>"+ user.name +"</span>"+
								"<input type='hidden' id="+ user.userId +">"+
								"<button class='btn btn-associated' onclick='listenerSchedule(this)'>"+ getLabel("lblAssociation") +"</button>";

				$tableAssociated.append("<tr><td><div>"+ friend +"</div></td></tr>").show();
				$alertAssociated.hide();
			});	
			
			if(users[0] == "" && $pending.find(".alerts").length == 0) {
				$("<div></div>", {
					class: "alerts alert-pending",
					html: "<img src='resources/images/message.png'> <p><br>"+ getLabel("lblNoRequests")
				}).appendTo($pending);

				$tablePending.hide();
			}
			if(users[1] == "" && $associated.find(".alerts").length == 0) {
				$("<div></div>", {
					class: "alerts alert-associated",
					html: "<img src='resources/images/message.png'> <p><br>"+ getLabel("lblNoAssocs")
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
	var relations   	= $.parseJSON(json),
		days			= new Array(),
		$requested 		= $(".requested"),
		$offered 		= $(".offered");
		$scheduleData   = $(".schedule-data");
	
	days[7] = null;	
	
	$.each(relations.requested, function(i, data) {
		fetchDays(days, data);
	});	
	printSchedule(days, $(".table-requested"), typeAssoc);
	
	days		= new Array();
	days[7] 	= null;

	$.each(relations.offered, function(i, data) {
		fetchDays(days, data);
	});
	printSchedule(days, $(".table-offered"), typeAssoc);
	
	$requested.show();
	$offered.show();
	if(relations.requested == "") {
		$requested.hide()
				  .siblings().css({"float":"none", "width":"100%"});
	}
	if(relations.offered == "") {
		$offered.hide()
		        .siblings().css({"float":"none", "width":"100%"});
	}
	if(relations.offered != "" && relations.requested != "") {
		$requested.css({"float":"left", "width":"50%"});
		$offered.css({"float":"right", "width":"50%"});
	}
	if(relations.requested == "" && relations.offered == "") {
		$scheduleData.hide();
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
	if(data.inout == 0)	{
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
	
	$table.html("<tr><th></th></tr>");
	
	var	rIn 			= 	"<tr id='in'><td>"+ getLabel("lblArrival") +"</td></tr>", 
		rOut 			= 	"<tr id='out'><td>"+ getLabel("lblDeparture") +"</td></tr>",
		bCancelAssoc	=	"<button title='Cancel Association' onclick='actionAssociation(this, false)'>" +
								"<img src='resources/images/cancel.png'>" +
							"</button>",
	    bCancelPetit	= 	"<button title='Cancel Petition' onclick='actionAssociation(this, false)'>" +
								"<img src='resources/images/cancel.png'>" +
							"</button>",
		bAcceptPetit	= 	"<button title='Accept Petition' onclick='actionAssociation(this, true)'>" +
								"<img src='resources/images/accept.png'>" +
							"</button>",
		$rowDays		= 	$table.find("tr:first"),
		$scheduleData 	= 	$(".schedule-data");

	if(!$scheduleData.is(":visible")) {			//Display schedules' section
		$scheduleData.show("bounce", 400);
	}
		
	for(var i=1; i<days.length; i++) {
		if(days[i]!=null) {
			var buttons;
			
			$rowDays.append("<th id='"+ i +"'>"+ getDayLabel(i) +"</th>"); 		//Create label for current day
			
			if(!$table.find("#in").length)		//If IN Row doesn't exist, create it
				$table.append(rIn);

			if(!$table.find("#out").length)		//If OUT Row doesn't exist, create it
				$table.append(rOut);

			buttons = (typeAssoc == 0) ? (bAcceptPetit + bCancelPetit): bCancelAssoc;
			
			var	contentIn,
				contentOut,
				$rowIn	= $table.find("#in"),
				$rowOut	= $table.find("#out");

			if(days[i].inHour != "" && days[i].outHour == "") {	     
				//Just IN			
				contentIn = days[i].inHour + buttons;
				
				$rowIn.append("<td data-assoc='"+ days[i].assocIdIn +"'>"+ contentIn +"</td>");
				$rowOut.append("<td></td>"); 			
			}
			else if(days[i].inHour != "" && days[i].outHour != "") {
				//IN & OUT
				contentIn 	= days[i].inHour + buttons;
				contentOut 	= days[i].outHour + buttons;
								
				$rowIn.append("<td data-assoc='"+ days[i].assocIdIn +"'>"+ contentIn +"</td>");
				$rowOut.append("<td data-assoc='"+ days[i].assocIdOut +"'>"+ contentOut +"</td>");
			}
			else {	
				//Just OUT
				contentOut = days[i].outHour + buttons;
				
				$rowIn.append("<td></td>");
				$rowOut.append("<td data-assoc='"+ days[i].assocIdOut +"'>"+ contentOut +"</td>");
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
	
	if($(target).hasClass("btn-pending")) {
		$.post("viewSchedule.do", {"userId": $userId , "typeAssoc": 0}, 
			function(json) {
				viewSchedule(json, 0);
			}
		);
	}
	else {
		$.post("viewSchedule.do", {"userId": $userId , "typeAssoc": 1}, 
			function(json) {
				viewSchedule(json, 1);
			}
		);
	}
}

/**
 * Sends whether the request was cancelled or accepted to the server.
 *
 * @param {DOM} target - button where action was defined.
 * @param {string} action - Accept or Cancel
 */
function actionAssociation(target, action) {			
	var assocId = $(target).parent().data("assoc");
		
	$.post("responseAssoc.do", {"assocId": assocId, "response": action}, 
		function() {
			alert(getLabel("lblAction"));
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


