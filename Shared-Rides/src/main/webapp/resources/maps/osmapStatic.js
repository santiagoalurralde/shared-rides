var map;
var lonJs;
var latJs;

function initMap(){
	var map         = new OpenLayers.Map('map2');
  
	/*  Projections */
	var proj4326    = new OpenLayers.Projection("EPSG:4326");
	var projmerc    = new OpenLayers.Projection("EPSG:900913");
  
  /*  Layers    */
  var layerOSM    = new OpenLayers.Layer.OSM("Street Map");
  var layerMarkers= new OpenLayers.Layer.Markers( "Markers" );

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

  map.addLayer(layerMarkers);
  map.addControl(new OpenLayers.Control.LayerSwitcher());
}




