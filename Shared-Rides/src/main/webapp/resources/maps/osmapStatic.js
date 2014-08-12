var _mapPedestrian, _mapDriver, _proj4326, _layerMarkers, _layerTrack; 

/**
 * Starts Pedestrian's map
 */
function initMapPedestrian(){
	_mapPedestrian	= new OpenLayers.Map('map2');
  
	/*  Projections */
	_proj4326    = new OpenLayers.Projection("EPSG:4326");
  
	/*  Layers    */
	var layerOSM    = new OpenLayers.Layer.OSM("Street Map");
	_layerMarkers = new OpenLayers.Layer.Markers( "Markers" );

	_mapPedestrian.addLayers([layerOSM]);
	_mapPedestrian.addLayer(_layerMarkers);

	/*  Set Start Position  */
	var lonlatStart= new OpenLayers.LonLat( -64.183 , -31.416 ) //COMPLETAR CON LON LAT DE CORDOBA
  		.transform(
  				_proj4326,
  				_mapPedestrian.getProjectionObject()
  		);
	var zoomStart=16;
	_mapPedestrian.setCenter (lonlatStart, zoomStart);

	if (!_mapPedestrian.getCenter()) 
		_mapPedestrian.zoomToMaxExtent();
}

/**
 * Starts Driver's map
 */
function initMapDriver(){
	_mapDriver	= new OpenLayers.Map('map3');
  
	/*  Projections */
	_proj4326    	= new OpenLayers.Projection("EPSG:4326");
  
	/*  Layers    */
	var layerOSM    = new OpenLayers.Layer.OSM("Street Map");
	_layerTrack 	= new OpenLayers.Layer.OSM.CycleMap("TrackLayer");
	_mapDriver.addLayers([layerOSM]);
	_mapDriver.addLayer(_layerTrack);
	
	/*  Set Start Position  */
	var lonlatStart= new OpenLayers.LonLat( -64.183 ,-31.416 )
		.transform(
				_proj4326,
				_mapDriver.getProjectionObject()
    );
	var zoomStart=16;
	_mapDriver.setCenter (lonlatStart, zoomStart);

	if (!_mapDriver.getCenter())
		_mapDriver.zoomToMaxExtent();
}

/**
 * Sets Pedestrian's Marker
 */
function setMapPedestrian(lonPed, latPed){
	/*	Add Marker			*/	
    var size 	= new OpenLayers.Size(35,35);
    var offset 	= new OpenLayers.Pixel(-(size.w/2), -size.h);
    var icon 	= new OpenLayers.Icon('resources/images/markerred.png',size,offset);

    markerPed = new OpenLayers.Marker(new OpenLayers.LonLat(lonPed, latPed).transform(
    			_proj4326,
  				_mapPedestrian.getProjectionObject()
  		),icon.clone());

    _layerMarkers.clearMarkers();
    _layerMarkers.addMarker(markerPed); 	

}

/**
 * Sets Driver's Track
 */
function setMapDriver(urlGpxDriver){
	// Define the map layer
	// Here we use a predefined layer that will be kept up to date with URL changes

	var gpx = new OpenLayers.Layer.Vector("driver track", {
		strategies: [new OpenLayers.Strategy.Fixed()],
		protocol: new OpenLayers.Protocol.HTTP({
		url: 'resources/gpxFiles/'+ urlGpxDriver,
		format: new OpenLayers.Format.GPX()
	}),
	style: {strokeColor: "green", strokeWidth: 5, strokeOpacity: 0.5},
	projection: new OpenLayers.Projection("EPSG:4326")
	});
	
	map.addLayer(gpx);		
}




