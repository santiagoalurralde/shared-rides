var map;
var _lon;
var _lat;
var _layerMarkers;
var _proj4326;
var _countMarkers = 0;

function initMap(){
	map         	= new OpenLayers.Map('map2');
  
	/*  Projections */
	proj4326		= new OpenLayers.Projection("EPSG:4326");
  
	/*  Layers    */
	var layerOSM    = new OpenLayers.Layer.OSM("Street Map");
	_layerMarkers	= new OpenLayers.Layer.Markers( "Markers" );

	map.addLayers([layerOSM]);
	  
	/*  Set Start Position  */
	var lonlatStart= new OpenLayers.LonLat( -64.183 ,-31.416 )
	     .transform(
	        proj4326,
	        map.getProjectionObject()
	  );
	var zoomStart=16;
	map.setCenter (lonlatStart, zoomStart);
	
	if (!map.getCenter()) map.zoomToMaxExtent();
	 	  
	/*  Add Marker  */
	_countMarkers = 0;
	
	map.events.register("click", map, function(e) {
		var position       	= this.events.getMousePosition(e);
	    var icon           	= new OpenLayers.Icon('resources/images/pin.png');   
	    
	    var lonlat         	= map.getLonLatFromPixel(position);
	    var lonlatTrans	    = lonlat.transform(map.getProjectionObject(), proj4326);
	    _lon    	   		= lonlatTrans.lon;
	    _lat	       		= lonlatTrans.lat;
	    var lonlat         	= lonlatTrans.transform(proj4326, map.getProjectionObject());
	
	    if(_countMarkers>0)
	    	_layerMarkers.clearMarkers();
	      
	    _layerMarkers.addMarker(new OpenLayers.Marker(lonlat,icon)); 
	    _countMarkers++;
	});

	map.addLayer(_layerMarkers);
}

function clearMarkers()
{
	_layerMarkers.clearMarkers();
}

function drawPreviousMarkers(lonlatPrevious)
{
	if(lonlatPrevious != null)
	{
		var icon		= new OpenLayers.Icon('resources/images/pin.png');   	
		alert("adentro90");
		_layerMarkers.addMarker(new OpenLayers.Marker(lonlatPrevious,icon)); 
	    _countMarkers++;
  	}

}



