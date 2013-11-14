function initMap(lonPed, latPed){
	var map         = new OpenLayers.Map('map2');
  
	/*  Projections */
	var proj4326    = new OpenLayers.Projection("EPSG:4326");
	var projmerc    = new OpenLayers.Projection("EPSG:900913");
  
	/*  Layers    */
	var layerOSM    = new OpenLayers.Layer.OSM("Street Map");
	var layerMarker = new OpenLayers.Layer.Markers( "Markers" );

	map.addLayers([layerOSM]);
	map.addLayer(layerMarker);

	/*  Set Start Position  */
	var lonlatStart= new OpenLayers.LonLat( -64.183 ,-31.416 )
  		.transform(
  				proj4326,
  				map.getProjectionObject()
  		);
	var zoomStart=16;
	map.setCenter (lonlatStart, zoomStart);

	if (!map.getCenter()) map.zoomToMaxExtent();

	/*	Add Marker			*/
	
	alert(lonPed);
	alert(latPed);
	
    var size 	= new OpenLayers.Size(35,35);
    var offset 	= new OpenLayers.Pixel(-(size.w/2), -size.h);
    var icon 	= new OpenLayers.Icon('resources/images/marker.png',size,offset);

    markerPed = new OpenLayers.Marker(new OpenLayers.LonLat(lonPed, latPed).transform(
  				proj4326,
  				map.getProjectionObject()
  		),icon.clone());
    layerMarker.addMarker(markerPed); 
	
	map.addControl(new OpenLayers.Control.LayerSwitcher());
}


function initMap1(urlGpxDriver){
	var map         = new OpenLayers.Map('map3');
  
	/*  Projections */
	var proj4326    = new OpenLayers.Projection("EPSG:4326");
	var projmerc    = new OpenLayers.Projection("EPSG:900913");
  
	/*  Layers    */
	var layerOSM    = new OpenLayers.Layer.OSM("Street Map");
	var layerTrack 	= new OpenLayers.Layer.OSM.CycleMap("TrackLayer");
	map.addLayers([layerOSM]);
	map.addLayer(layerTrack);
	
	/*  Set Start Position  */
	var lonlatStart= new OpenLayers.LonLat( -64.183 ,-31.416 )
		.transform(
				proj4326,
				map.getProjectionObject()
    );
	var zoomStart=16;
	map.setCenter (lonlatStart, zoomStart);

	if (!map.getCenter()) map.zoomToMaxExtent();

	map.addControl(new OpenLayers.Control.LayerSwitcher());

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



