package com.shared.rides.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class ReadGPXFile {
	
	private static double [][] pointsList;
	
	public static double[][] readFile(String pathFile){
		try {
			 
			File fXmlFile = new File(pathFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			doc.getDocumentElement().normalize();
		 
			//Busco el nodo trkpt
			NodeList nList = doc.getElementsByTagName("trkpt");
		 
			pointsList = new double[nList.getLength()][2];
			//Recorro cada uno de estos nodos
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
		  
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					pointsList[temp][0] = Double.parseDouble(eElement.getAttribute("lat"));
					pointsList[temp][1] = Double.parseDouble(eElement.getAttribute("lon"));
				}
			}
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		return pointsList;
	}
	
}
