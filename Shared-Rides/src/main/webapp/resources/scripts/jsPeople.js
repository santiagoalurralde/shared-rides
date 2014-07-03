function createTables(){
	$( "#tablePending" ).append("<tr><th> Usuario  </th></tr>");
	$( "#tableAssociated" ).append("<tr><th> Usuario  </th></tr>");
}

createTables();

$.post( 'loadAssociations.do', 
	function(json){
	/*
	Aca va a traer la informacion de las personas, como un json.
	Es un JsonArray que tiene adentro dos JsonArray: uno con las asociaciones pendientes y otro con las
	asociaciones aceptadas (o sea mis amigos)
	Dentro de cada uno de estos; tengo el id de la asociacion y el nombre completo de la persona
	Ese id de la asociacion no hace falta mostrarlo, pero sirve para dps buscar la info de esa asoc para mostrar
	abajo
	*/

	var jsonNew = $.parseJSON(json);
	
	$.each(jsonNew[0], function(i, data){
		var applicant = "<div>" +
							"<a style='margin-right: 10px' href='/Shared-Rides/profile.do?user="+ data.userId+"'>"+
								"<img src='resources/profilePic/"+ data.pic +"'>"+
							"</a>"+
							"<span style='vertical-align: top; margin-right: 10px'>"+ data.name + "</span>"+
							"<input type='hidden' id="+ data.userId +">"+ 																
							"<button onclick='viewPendingSchedule(this)' style='vertical-align: top'>"+ $('#lblRequest').val() +"</button>"+
						"</div>";
			
		$( "#tablePending" ).append("<tr><td>"+ applicant +"</td></tr>");
	});		
	
	$.each(jsonNew[1], function(i, data){
		var applicant = "<div id='pic'>" +
							"<a style='margin-right: 10px' href='/Shared-Rides/profile.do?user="+ data.userId +"'>"+
								"<img src='resources/profilePic/"+ data.pic +"'>"+
							"</a>"+
							"<span style='vertical-align: top; margin-right: 10px'>"+ data.name +"</span>"+
							"<input type='hidden' id="+ data.userId +">"+ 								
							"<button onclick='viewAssociatedSchedule(this)' style='vertical-align: top'>"+ $('#lblAssociation').val() +"</button>"+
						"</div>";
		
		$( "#tableAssociated" ).append("<tr><td>"+ applicant +"</td></tr>");
	});				
});

function viewPendingSchedule(target)
{	
	var $userId = $(target).parent().find("input").attr("id");
	
	$.post( "viewSchedule.do", { "userId": $userId , "typeAssoc": 0}, 
		function(json)
		{
			viewSchedule(json, 0);
		}
	);
}

function viewAssociatedSchedule(target)
{	
	var $userId = $(target).parent().find("input").attr("id");
	
	$.post( "viewSchedule.do", { "userId": $userId , "typeAssoc": 1}, 
		function(json)
		{
			viewSchedule(json, 1);
		}
	);
}

function viewSchedule(json, typeAssoc)
{
	var jsonNew = $.parseJSON(json);
	//{"requested":[{"day":2,"inout":2,"hour":20},{"day":5,"inout":2,"hour":20}],"offered":[]}

	var days	= new Array();
	days[7] 	= null;	
	
	$.each(jsonNew.requested, function(i, data){	
		fetchDays(days, data);
	});
	printSchedule(days, $("#tableRequested"), typeAssoc);
	
	days	= new Array();
	days[7] = null;	
	
	$.each(jsonNew.offered, function(i, data){	
		fetchDays(days, data);
	});
	printSchedule(days, $("#tableOffered"), typeAssoc);
}

function fetchDays(days, data)
{
	if(days[data.day] == null)
	{
		days[data.day] = new Day();
	}
	if(data.inout == 1)	//in
	{
		days[data.day].inHour 		= data.hour;
		days[data.day].assocIdIn	= data.assocId;
	}
	else
	{
		days[data.day].outHour 		= data.hour;
		days[data.day].assocIdOut 	= data.assocId;
	}	
}

function printSchedule(days, $table, typeAssoc)
{	
	if(!$( "#scheduleData" ).is(":visible"))			//Display schedules' section
		$( "#scheduleData" ).show( "bounce", 400 );
	
	$table.html("<tr>"		+
					"<th>"	+
					"</th>"	+
				"</tr>");
	
	for(var i=1;i<days.length;i++)
	{
		if(days[i]!=null)
		{
			var buttons;
			
			$table.find( "tr:first" ).append("<th id='"+ i +"'>"+ dayLabel(i) +"</th>");
			
			if(!$table.find("#in").length) 		//Fila de In no existe la creamos
				$table.append("<tr id='in'><td>"+ $('#lblArrival').val() +"</td></tr>");
			
			if(!$table.find("#out").length)		//Fila de Out no existe la creamos
				$table.append("<tr id='out'><td>"+ $('#lblDeparture').val() +"</td></tr>");

			if(typeAssoc == 0)
			{
				buttons = 	"<button onclick='actionAssociation(this, true)' style='margin-left: 4px'><img src='resources/images/accept.png' width='15px'></button>"+
							"<button onclick='actionAssociation(this, false)' style='margin-left: 4px'><img src='resources/images/cancel.png' width='15px'></button>";
			}
			else
			{
				buttons = 	"<button onclick='actionAssociation(this, false)' style='margin-left: 4px'><img src='resources/images/cancel.png' width='15px'></button>";
			}
			
			if(days[i].inHour != "")
			{	
				
				var content = 	"<td>"+ 
									days[i].inHour + buttons +
									"<input type='hidden' value='"+ days[i].assocIdIn +"' id='assocId'>" +
								"</td>";
				
				if($table.find("#in #emptyCell"+i).length)			//Si ya existe celda in vacía donde queremos insertar.
				{
					$table.find( "#in #emptyCell"+i ).append(content);
					$table.find( "#in #emptyCell"+i ).removeAttr('id');
					$table.find( "#in #emptyCell"+i ).removeClass('emptyCells');
				}
				else
					$table.find( "#in" ).append(content);
				
				if(!$table.find("#out #emptyCell"+i).length)		//Si no existe celda out vacía en la misma columna
					$table.find( "#out" ).append("<td id='emptyCell"+ i +" class='emptyCells'></td>"); 
			}
			
			if(days[i].outHour != "")
			{	
				var content =	"<td>"+ 
									days[i].outHour + buttons +
									"<input type='hidden' value='"+ days[i].assocIdOut +"' id='assocId'>" +
								"</td>";
				
				if(!$table.find("#in #emptyCell"+i).length)			//Si no existe celda in vacía en la misma columna
					$table.find( "#in" ).append("<td id='emptyCell"+ i +" class='emptyCells'></td>"); 
				
				if($table.find("#out #emptyCell"+i).length)			//Si ya existe celda in vacía donde queremos insertar.
				{
					$table.find( "#out #emptyCell"+i ).append(content);
					$table.find( "#out #emptyCell"+i ).removeAttr('id');
					$table.find( "#out #emptyCell"+i ).removeClass('emptyCells');
				}
				else
					$table.find( "#out" ).append(content);
			}
			
			$table.find( ".emptyCells" ).css( "background-color", "#FAFAFA");
		}
	}	
}

function actionAssociation(target, action)
{	
	var $assocId = $(target).parent().find("#assocId").val();
		
	$.post( "responseAssoc.do", { "assocId": $assocId , "response": action}, 
			function()
			{
				alert("respondido");
			}
	);
}


function Day()
{ 
	// Constructor

	this.assocIdIn	= "";
	this.assocIdOut	= "";		
  	this.inHour 	= "";
  	this.outHour 	= "";
}

function dayLabel(index)
{
	var label;

	switch (index)
	{	
		case 1:
			label = $('#lblMonday').val();
			break;
		case 2:
			label = $('#lblTuesday').val();
			break;
		case 3:
			label = $('#lblWednesday').val();
			break;
		case 4:
			label = $('#lblThursday').val();		
			break;					
		case 5:
			label = $('#lblFriday').val();		
			break;
	}	
	return label;
}

