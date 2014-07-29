package com.shared.rides.util;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class CreateGPXFile {
	
	public static void createGPX(String name, Double[][] markers){
		
		Element gpx = new Element("gpx");
		Document doc = new Document(gpx);
        doc.setRootElement(gpx);
        
        Element trk = new Element("trk");
        doc.getRootElement().addContent(trk);
        
        Element trkseg = new Element("trkseg");
        doc.getRootElement().getChild("trk").addContent(trkseg);
        
        for(int i = 0; i < markers.length; i++){
        	Element trkpt = new Element("trkpt");
            trkpt.setAttribute(new Attribute("lat", markers[i][0].toString()));
            trkpt.setAttribute(new Attribute("lon", markers[i][1].toString()));
            doc.getRootElement().getChild("trk").getChild("trkseg").addContent(trkpt);            
        }
        
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        
        try {
			xmlOutput.output(doc, new FileWriter("/home/leandrobagur/WORKSPACE/trunk/Shared-Rides/src/main/webapp/resources/gpxFiles/" + name));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
}
