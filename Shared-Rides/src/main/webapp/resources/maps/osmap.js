function updateScale(evt){
	var scale 	= this.getScale();
	var size	= this.getSize();
	var factor	= 25.4/72.0*1e-6*scale;
	var xkm		= (size.w*factor).toFixed(3);
	var ykm		= (size.h*factor).toFixed(3);
	var e		= document.getElementById('currentscale');
	e.innerHTML	= scale.toFixed(0) + ", "+xkm+"&times;"+ykm+" km<sup style='font-size:smaller'>2</sup>";
	updateLocation.call(this, evt);
}

function updateLocation(evt){
	var lonlat	= p2p(p900913, p4326, this.getCenter());
	var href	= ""+location.protocol+"//"
		+ location.hostname
		+ (location.port ? ":"+location.port : "") 
		+ location.pathname.replace(/\/upload/, "/map")
		+ "?lon="+lonlat.lon.toFixed(4)
		+ "&lat="+lonlat.lat.toFixed(4)
		+ "&zoom="+this.getZoom()
		+ "&maph="+parseInt(document.getElementById('map').style.height)
		+ "&layer="+map.baseLayer.name
		;
  document.getElementById('permalink').href = href;
  location.url	= href;
}

var mouseposElem= {element: document.getElementById('mousepos')};

var options		= {
		projection : "EPSG:900913",
		controls: [new OpenLayers.Control.Navigation(),
		           new OpenLayers.Control.PanZoomBar()],
};

var map 		= new OpenLayers.Map('map', options);

var layerMapnik	= new OpenLayers.Layer.OSM.Mapnik("Mapnik");
map.addLayer(layerMapnik);

var layerCycle	= new OpenLayers.Layer.OSM.CycleMap("Cycle");
map.addLayer(layerCycle);

Proj4js.defs["EPSG:54004"] 	= "+proj=merc +lon_0=0 +k=1 +x_0=0 +y_0=0 +ellps=WGS84 +datum=WGS84 +units=m +no_defs ";
Proj4js.defs["EPSG:54004"] 	= "+proj=merc +lat_ts=0 +lon_0=0 +k=1.000000 +x_0=0 +y_0=0 +ellps=WGS84 +datum=WGS84 +units=m  no_defs";
Proj4js.defs["EPSG:900913"] = "+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext  +no_defs";

p54004 	= new Proj4js.Proj("EPSG:54004");
p4326	= new Proj4js.Proj("EPSG:4326");
p900913 = new Proj4js.Proj("EPSG:900913");

function sizeMap(deltaPx){ 
	var e			= document.getElementById('map');
	var h			= e.style.height;
	
	if( h=='' )
		h = window.getComputedStyle(e, null).height;
	
	var height		= parseInt(h)+deltaPx;
	if( height<100 ) 
		return;
	
	e.style.height 	= ""+ height + 'px';
	map.updateSize();
};

/*
 * Convierte de una proyección a otra
 */

function p2p(pFrom, pTo, lonlat){
	var xy	= {x: lonlat.lon, y:lonlat.lat};
	xy 		= Proj4js.transform(pFrom, pTo, xy);
	return new OpenLayers.LonLat(xy.x, xy.y);  
};

function mapFullsize(){
	var maph	= window.innerHeight-100;
	document.getElementById('map').style.height	= ""+maph+"px";
	map.updateSize();
}

// Parsear parametros de la URL para lat, lon and zoom
var floatreString = "([-]?[0-9]+([.][0-9]+)?)($|&)";

var paramDef = {
  lat 	:	[new RegExp("[?&]lat="+floatreString), null],
  lon 	:	[new RegExp("[?&]lon="+floatreString),  null],
  zoom	:	[new RegExp("[?&]zoom=([0-9]|1[0-9])($|&)"), null],
  maph	:	[new RegExp("[?&]maph=([0-9][0-9][0-9][0-9]?)($|&)"), null],
  layer :	[new RegExp("[?&]layer=([A-Za-z]+)($|&)"), "Mapnik"],
};

var params = {};
for(key in paramDef) {
	var m = location.search.match(paramDef[key][0]);
	eval("var "+key+" = (m==null) ? paramDef[key][1] : m[1]");
};

/*
 * Inicializar las coordenadas del mapa, zoom y tamaño, y la capa desde
 * los parámetros de la URL o desde los predeterminados.
 */

//Inicializar Coordenadas
if( lat && lon ){
	lonlatIsDefault	= false;
	var lonlat		= new OpenLayers.LonLat(lon, lat);
} 
else{
	lonlatIsDefault	= true;
	var lonlat		= new OpenLayers.LonLat( -64.183 ,-31.416 );
}
lonlat = p2p(p4326, p900913, lonlat);

//Inicializar Zoom
if( zoom ){
	zoomIsDefault	= false;
} 
else{
	zoomIsDefault	= true;
	zoom			= 16;
}

//Inicializar Tamaño
if( maph ){
	document.getElementById('map').style.height = ""+maph+"px";
	map.updateSize();
} 
else{
	mapFullsize();
}
var l = map.getLayersByName(layer);
if( l && l.length>0 ) map.setBaseLayer(l[0]);

map.events.register("zoomend", map, updateScale);
map.events.register("moveend", map, updateLocation);


//Inicializar Mapa
function initMapCoords(lonlat, zoom, map){
	map.setCenter(lonlat, zoom, false, false);
	updateScale.apply(map, null);
}

