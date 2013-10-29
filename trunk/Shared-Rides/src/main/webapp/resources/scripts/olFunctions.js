
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
  
  //####MOUSEMOVE####
  
  /*  Add Marker  */
  var count = 0;

  map.events.register("click", map, function(e) {
    var position        = this.events.getMousePosition(e);
    var icon            = new OpenLayers.Icon('resources/images/pin.png');   
    
    var lonlat          = map.getLonLatFromPixel(position);
    var lonlatTransf    = lonlat.transform(map.getProjectionObject(), proj4326);
    lonJs       		= lonlatTransf.lon;
    latJs       		= lonlatTransf.lat;
    var lonlat          = lonlatTransf.transform(proj4326, map.getProjectionObject());

    
    
    
    if(count>0)
      layerMarkers.clearMarkers();
      
    layerMarkers.addMarker(new OpenLayers.Marker(lonlat,icon)); 
    count = count + 1;
  });
  

  map.addLayer(layerMarkers);
  map.addControl(new OpenLayers.Control.LayerSwitcher());
}


/*
  map.events.register("mousemove", map, function(e) { 
      var position = this.events.getMousePosition(e);
      OpenLayers.Util.getElement("coords").innerHTML = 'MOUSE POSITION '+position;
      var lonlat = map.getLonLatFromPixel( this.events.getMousePosition(e) );
      OpenLayers.Util.getElement("lonlatTG").innerHTML = 'lonlat => '+lonlat;
      var lonlatTransf = lonlat.transform(map.getProjectionObject(), proj4326);
      OpenLayers.Util.getElement("lonlatTrans").innerHTML = 'lonlatTransf => '+lonlatTransf;
      OpenLayers.Util.getElement("lonlatDouble").innerHTML = 'lonlat => '+lonlat;
  });
  
*/



