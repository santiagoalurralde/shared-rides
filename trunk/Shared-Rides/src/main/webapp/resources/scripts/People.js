var _listenerScheduleTarget;

function createTables() {
	//Clean Tables at the beginning and after updating.
	var content = "<tr><th> Usuario  </th></tr>";
	
	$( "#tablePending" ).html(content);
	$( "#tableAssociated" ).html(content);
}

load();

function load() {
	createTables();
	
	$.post( 'loadAssociations.do', 
		function(json) {
		
		/* Brings people info. JsonArray with 2 JsonArray: One with pending and another one 
		 * with accepted associations. Each one contains association id and full name.
		 */
		 
		var jsonNew = $.parseJSON(json);

		$.each(jsonNew[0], function(i, data) {
			var applicant =	"<div>"+
								"<a style='float: left; margin-right: 25px' href='/Shared-Rides/profile.do?user="+ data.userId +"'>"+
									"<img src='resources/profilePic/"+ data.pic +"'>"+
								"</a>"+
								"<span style='float: left; vertical-align: top;'>"+ data.name +"</span>"+
								"<input type='hidden' id="+ data.userId +">"+ 			
								"<button class='btn pending' onclick='listenerSchedule(this)' style='float: right; vertical-align: top'>"+ $('#lblRequest').val() +"</button>"+
							"</div>";

			$( "#tablePending" ).append("<tr><td>"+ applicant +"</td></tr>").show();
			$( "#alertPending" ).hide();
		});		
		
		$.each(jsonNew[1], function(i, data) {
			var applicant =	"<div>"+
								"<a style='float: left; margin-right: 25px' href='/Shared-Rides/profile.do?user="+ data.userId +"'>"+
									"<img src='resources/profilePic/"+ data.pic +"'>"+
								"</a>"+
								"<span style='float: left; vertical-align: top;'>"+ data.name +"</span>"+
								"<input type='hidden' id="+ data.userId +">"+ 	
								"<button class='btn associated' onclick='listenerSchedule(this)' style='float: right; vertical-align: top'>"+ $('#lblAssociation').val() +"</button>"+
							"</div>";

			$( "#tableAssociated" ).append("<tr><td>"+ applicant +"</td></tr>").show();
			$( "#alertAssociated" ).hide();
		});	
		
		if(jsonNew[0] == "" && $("#pending").find(".alerts").length == 0) {
			$( "#pending" ).append("<div class='alerts' id='alertPending'><img src='resources/images/message.png'> <p><br> No cuenta con peticiones pendientes </div>");
			$( "#tablePending" ).hide();
		}
		if(jsonNew[1] == "" && $("#associated").find(".alerts").length == 0) {			
			$( "#associated" ).append("<div class='alerts' id='alertAssociated'><img src='resources/images/message.png'> <p><br> Actualmente no posee asociaciones </div>");
			$( "#tableAssociated" ).hide();
		}
	});
}

function listenerSchedule(target) {
	// Receives button object that shares cell with input that contains id.
	
	_listenerScheduleTarget = target;
	
	$userId = $(target).parent().find("input").attr("id");
	
	if($(target).hasClass("pending"))
		$.post( "viewSchedule.do", { "userId": $userId , "typeAssoc": 0}, 
			function(json) {
				viewSchedule(json, 0);
			}
		);
	else
		$.post( "viewSchedule.do", { "userId": $userId , "typeAssoc": 1}, 
			function(json) {
				viewSchedule(json, 1);
			}
		);	
}

function viewSchedule(json, typeAssoc) {
	// Performs all the work by calling other functions
	
	var jsonNew = $.parseJSON(json);

	var days	= new Array();
	days[7] 	= null;	
	
	$.each(jsonNew.requested, function(i, data) {	
		fetchDays(days, data);
	});	
	printSchedule(days, $("#tableRequested"), typeAssoc);
	
	days		= new Array();
	days[7] 	= null;	
	
	$.each(jsonNew.offered, function(i, data) {	
		fetchDays(days, data);
	});
	printSchedule(days, $("#tableOffered"), typeAssoc);
	
	if(jsonNew.requested == "") {
		$( "#requested" ).hide();
		$( "#offered" ).css("float", "none").css("width", "100%");
	}
	if(jsonNew.offered == "") {
		$( "#offered" ).hide();
		$( "#requested" ).css("float", "none").css("width", "100%");
	}
}

function fetchDays(days, data) {
	// Fills up array "days" with objects
	
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

function printSchedule(days, $table, typeAssoc) {	
	// Prints both schedules
	
	if(!$( "#scheduleData" ).is(":visible"))			//Display schedules' section
		$( "#scheduleData" ).show( "bounce", 400 );
	
	$table.html("<tr><th></th></tr>");
	
	var rIn 		= "<tr id='in'><td>"+ $('#lblArrival').val() +"</td></tr>";
	var rOut		= "<tr id='out'><td>"+ $('#lblDeparture').val() +"</td></tr>";
	var btnCancel 	= "<button onclick='actionAssociation(this, false)' style='margin-left: 4px'><img src='resources/images/cancel.png' width='15px'></button>";
	var btnAccept  	= "<button onclick='actionAssociation(this, true)' style='margin-left: 4px'><img src='resources/images/accept.png' width='15px'></button>";
	
	for(var i=1;i<days.length;i++) {
		if(days[i]!=null) {
			var buttons;
			
			$table.find( "tr:first" ).append("<th id='"+ i +"'>"+ getDayLabel(i) +"</th>"); 		//Create label for current day
			
			if(!$table.find("#in").length) 		//If IN Row doesn't exist, create it
				$table.append(rIn);
			
			if(!$table.find("#out").length)		//If OUT Row doesn't exist, create it
				$table.append(rOut);

			if(typeAssoc == 0)
				buttons = 	btnAccept + btnCancel;
			else
				buttons = 	btnCancel;
			
			var content;
			
			if(days[i].inHour != "" && days[i].outHour == "") {	     
				//Just IN 
				content = 	days[i].inHour + buttons +
							"<input type='hidden' value='"+ days[i].assocIdIn +"' id='assocId'>";
				
				$table.find( "#in" ).append("<td>"+ content +"</td>");
				$table.find( "#out" ).append("<td class='emptyCells'></td>"); 			
			}
			else if(days[i].inHour != "" && days[i].outHour != "") {
				//IN & OUT
				content = 	days[i].inHour + buttons +
							"<input type='hidden' value='"+ days[i].assocIdIn +"' id='assocId'>";
				
				var content2 =	days[i].outHour + buttons +
								"<input type='hidden' value='"+ days[i].assocIdOut +"' id='assocId'>";
				
				$table.find( "#in" ).append("<td>"+ content +"</td>");				
				$table.find( "#out" ).append("<td>"+ content2 +"</td>");

			}
			else {	
				//Just OUT
				content =	days[i].outHour + buttons +
							"<input type='hidden' value='"+ days[i].assocIdOut +"' id='assocId'>";
				
				$table.find( "#in" ).append("<td class='emptyCells'></td>");
				$table.find( "#out" ).append("<td>"+ content +"</td>");
			}
		}
	}	
}

function actionAssociation(target, action) {	
	// Cancels or Accepts the request.
	
	var $assocId = $( target ).parent().find( "#assocId" ).val();
		
	$.post( "responseAssoc.do", {"assocId": $assocId , "response": action}, 
		function() {
			alert("Acción Completada");
			load();
			listenerSchedule(_listenerScheduleTarget);
		}
	);
}

function Day() { 
	// Constructor

	this.assocIdIn	= "";
	this.assocIdOut	= "";		
  	this.inHour 	= "";
  	this.outHour 	= "";
}


