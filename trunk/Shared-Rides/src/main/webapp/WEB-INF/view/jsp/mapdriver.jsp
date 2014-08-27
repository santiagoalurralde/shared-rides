<script src="resources/maps/OpenLayers.js"		type="text/javascript"></script>    
<script src="resources/maps/OpenStreetMap.js"  	type="text/javascript"></script>
<script src="resources/maps/proj4js.js"			type="text/javascript"></script>
<script src="resources/maps/osmap.js" 			type="text/javascript"></script>
<script src="resources/maps/track.js" 			type="text/javascript"></script>

<div class="map-driver">
	<div id="map"></div>
	<span class="t1" style = "visibility:hidden"><a id="permalink" href=""></a></span>
	<span id="mapinfo"><span id="currentscale" style="display:none"></span></span>						
</div>

<script>
	initMapCoords(lonlat, zoom, map);
</script>