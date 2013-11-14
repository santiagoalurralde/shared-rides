/*------------------------------------------------------------------------------
	Define class TrackMarker.
------------------------------------------------------------------------------*/
var m = {
	
		
	//Constructor
	initialize	: function(track, lonlat, options) {
		this.name 			= "";										// set only explicitly for waypoints
		this.track 			= track;
		this.map 			= track.map;								// needed by Handler
		var params 			= [lonlat, this.track.newIcon(), options];
		
		OpenLayers.Marker.prototype.initialize.apply(this, params);
		
		this.dragHandler	= new OpenLayers.Handler.Drag(this,
								{
									move: this.moveTo, 
									done: this.moveTo,
									up	: this.deAct
								});
    
		this.events.register('mouseover', 	this, this.mouseOver);
		this.events.register('mouseout', 	this, this.mouseOut);
		this.events.register('click', 		this, this.click);    
	},
	
	
	//Desactivar
	deAct		: function(evt){
		this.dragHandler.deactivate();
		
		if( this.myLine ){
			this.movePoint(1, this.lonlat);
		}
		
		if( this.nextMark ){
			this.nextMark.movePoint(0, this.lonlat);
		}
	},
  
	
	//Mouse Over
	mouseOver	: function(evt) {
		this.setOpacity(0.6);
		this.dragHandler.activate();
	},
  
	//Mouse Out
	mouseOut	: function(evt)	{
		this.setOpacity(1.0);
		
		if(!this.dragHandler.dragging){
			this.dragHandler.deactivate();
		}
		this.lock = false;
	},
	
	
	//Mover hacia
	moveTo		: function(xy){ 
		var xyl	 	 = this.map.getLayerPxFromViewPortPx(xy);
		xyl.x 		-= 8;
		xyl.y		-= 8;
		OpenLayers.Marker.prototype.moveTo.apply(this, [xyl]);
		this.lock	 = true;
	},
	
  
	//Clickear
	click		: function(evt){
		
		if( this.lock ){
			this.lock = false;
			return;
		}
		
		//Quitar Marker
		if( evt.ctrlKey ){					
			this.dragHandler.deactivate();
			this.track.delMarker(this);
			return;
		}
	},

	
	//Mover Punto
	movePoint	: function(which, lonlat){
		var p 	= this.myLine.geometry.components[which];
		p.x 	= lonlat.lon;
		p.y 	= lonlat.lat;
		p.move(0,0);
		this.track.vectors.drawFeature(this.myLine);
	},
	  
};
TrackMarker 	= OpenLayers.Class(OpenLayers.Marker, m);

//------------------------------------------------------------------------------


/*------------------------------------------------------------------------------
	Define class GpxTrack.
------------------------------------------------------------------------------*/

var m = {
	initialize	: function(map, options) {
		this.map	= map;
		OpenLayers.Control.prototype.initialize.apply(this, [options]);
		map.addControl(this);
		
		this.icon	=	new OpenLayers.Icon("resources/images/track.png", 		//Icono Track
						new OpenLayers.Size(17, 17),
						new OpenLayers.Pixel(-8,-8));
		this.icon.setOpacity(0.8);    

		//Éstilo de las Líneas en los tracks
		this.style					= OpenLayers.Util.extend(OpenLayers.Feature.Vector.style['default'], {});
		this.style.strokeColor 		= "gold";
		this.style.strokeColor 		= "#50A410";
		this.style.strokeWidth 		= 3;
		this.style.cursor 			= "default";
		this.style.hoverStrokeColor = "#bbaaaa";
		this.style.hoverStrokeWidth = 3;

		//Initialize the marker counter to generate consecutive default
		//Marker names.
		this.mID = 0;
	},
	
	draw		: function(){
		this.map.events.register("click", this, this.setMarkEvent);
	},
	

	newIcon		: function(){
		return this.icon.clone();
	},
	
	
	createLine	: function(llfrom, llto){
		var p1 		= new OpenLayers.Geometry.Point(llfrom.lon, llfrom.lat);
		var p2 		= new OpenLayers.Geometry.Point(llto.lon, llto.lat);
		var geom 	= new OpenLayers.Geometry.LineString([p1, p2]);
		var v 		= new OpenLayers.Feature.Vector(geom);
		v.style 	= this.style;
		
		this.vectors.addFeatures(v); 
		return v;   
	},

	setMarkEvent: function(evt) {
		if (!OpenLayers.Event.isLeftClick(evt))
			return;
		
		if( evt.ctrlKey )
			return;
		
		var lonlat 		= this.map.getLonLatFromViewPortPx(evt.xy); 
		var l 			= this.markers.markers.length;
		var previous 	= null;
   
		if( l>0 ) {
			previous = this.markers.markers[l-1];
		}
		
		this.setMark(lonlat, previous, null);
	},
	
	setMark		: function(lonlat, previous, next) {
		var mark				= new TrackMarker(this, lonlat);
		this.markers.addMarker(mark);
		
		if( previous ) {
			mark.myLine 			= this.createLine(previous.lonlat, lonlat);	
			mark.myLine.forMarker 	= mark;
			previous.nextMark 		= mark;
			mark.prevMark 			= previous;
		}
		if( next ){
			// get the order right (insert method missing on marker layer)
			var l = this.markers.markers.length-2;
			
			while( this.markers.markers[l]!=next ) {
				this.markers.markers[l+1] 	= this.markers.markers[l];
				l -= 1;
			}
			
			this.markers.markers[l+1]		= this.markers.markers[l];
			this.markers.markers[l] 		= mark;
			mark.nextMark					= next;
			next.prevMark					= mark;
			
			if( !next.myLine ){
				// next was the first and only mark until now
				next.myLine = this.createLine(lonlat, next.lonlat);
			}
			next.movePoint(0, mark.lonlat);
		}
	},
 
	// supports upload of marks. The server sends an array of elements
	// with lon, lat and name to run over
	marksFromList: function(marks){
		for(i=0; i<marks.length; i++)
		{
			var minfo		= marks[i];             					// again a list: lon, lat, name
			var lonlat		= this.fromGrad(minfo[0], minfo[1]);
			var previous 	= i>0 ? this.markers.markers[i-1] : null;
			this.setMark(lonlat, previous, null);
		}
	},

	deleteMarker: function() {
		this.delMarker(m);
	},

	delMarker	: function(m) {
		this.markers.removeMarker(m);
		
		if( m.prevMark ) {
			m.prevMark.nextMark = m.nextMark;
		}
		
		if( m.nextMark ) {
			var n 		= m.nextMark;
			n.prevMark 	= m.prevMark;
			if( m.prevMark ){
				n.movePoint(0, m.prevMark.lonlat);
			} 
			else{
				n.myLine.destroy();
				n.myLine = null;
			}
		}
		
		if( m.myLine ){
			m.myLine.destroy();
		}
		
		m.destroy();
	},

	clear		: function() {
		var markers 	= this.markers.markers;
		
		for(var i=markers.length; i>0; ){
			this.delMarker(markers[--i]);
		}
		this.mID 		= 0;
	},

	confirm		: function(hint){
		//Este es el "save" original
		
		var markers		= this.markers.markers;
		if( markers.length<2 ) {
			/* Si no hay marcadores aparece el cartel de advertencia */
			alert("Marca una ruta en el mapa!")
			
			return;
		}

		//var routename	= document.getElementById('routename').value;
		//var isTrack		= true;

		var html = "";

		var m	= markers[0];
		var ll	= this.toGrad(m.lonlat);
		html	+= '[{lat="'+ll.lat+'" , lon="'+ll.lon+'"}';
		
		//Empiezo a partir del segundo marker
		for(var i=1; i<markers.length; i++) {
			var m	= markers[i];
			var ll	= this.toGrad(m.lonlat);
			html	+= ',{lat="'+ll.lat+'" , lon="'+ll.lon+'"}';
		}
		html += ']';
		
		return html;
	},
	
	fromGrad	: function(lon, lat) {
		var xy 	= {x:lon, y:lat};
		xy 		= Proj4js.transform(p4326, p900913, xy);
		return new OpenLayers.LonLat(xy.x, xy.y);
	},
	
	toGrad		: function(lonlat){
		var xy 	= {x: lonlat.lon, y:lonlat.lat};
		xy 		= Proj4js.transform(p900913, p4326, xy);
		return new OpenLayers.LonLat(xy.x, xy.y);
	}
  
};
GpxTrack = OpenLayers.Class(OpenLayers.Control, m);

//----------------------------------------------------------------------

var options = {
		minResolution: 1.15,
		maxResolution: 150000
};

var markers = new OpenLayers.Layer.Markers("GPX points",options);
var vectors = new OpenLayers.Layer.Vector("Lines", options);
	
map.addLayer(vectors);
map.addLayer(markers);
gpxTrack	= new GpxTrack(map, {"markers":markers, "vectors": vectors});


