package uk.ac.bodc.utils.wmscapstest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import uk.co.bodc.utils.exceptions.WmsParseException;

public class App {

  public static void main(String[] args) 
 throws ParserConfigurationException, SAXException, 
  IOException, XPathExpressionException {

	  
	    try{
	  //WebMapService wms = new WebMapService("http://137.227.242.85/ArcGIS/services/FWS_Wetlands_WMS/mapserver/wmsserver");
	  //WebMapService wms = new WebMapService("http://www.gebco.net/data_and_products/web_map_service/mapserv");
	  //WebMapService wms = new WebMapService("http://apps1.gdr.nrcan.gc.ca/cgi-bin/worldmin_en-ca_ows");
	  WebMapService wms = new WebMapService("http://wms.jpl.nasa.gov/wms.cgi");
	  //WebMapService wms = new WebMapService("http://wetlandsfws.usgs.gov/ArcGIS/services/FWS_Wetlands_WMS/MapServer/WMSServer");
	System.out.println("doing it");
	  System.out.println(wms.getTitle());
	  System.out.println(wms.getWmsAbstract());
	  System.out.println(wms.getBaseUrl());
	/*  for(WmsLayer l : wms.getLayers()){
		  System.out.println(l.getTitle());
		  System.out.println(l.getLayerAbstract());
		  System.out.println(l.getLayerName());
		  for(String s : l.getCrs()){
			  System.out.println(s);
		  }
		  System.out.println("crs for image = "+wms.getLayers().get(1).getCrs().get(0));

	  }*/
 /*
	  layers=['global_mosaic'],
			  ...                     styles=['visual_bright'],
			  ...                     srs='EPSG:4326',
			  ...                     bbox=(-112, 36, -106, 41),
			  ...                     size=(300, 250),
			  ...                     format='image/jpeg',
			  ...                     transparent=True
			  minx="-180" miny="-60" maxx="180" maxy="84"
			  
			  */
	  for(WmsLayer l : wms.getLayers()){
		  try{
			  System.out.println("getting image for layer "+l.getLayerName()+" : "+l.getTitle());
		  l.getLayerDemo("/home/olly/mineral_dump/test"+l.getLayerName()+".jpg");
		  }
		  catch(WmsParseException e){
			  System.out.println("exception caught");
			  //System.exit(1);
			  if(e.hasPrevious)e.previous.printStackTrace();
			  System.out.print(e.traceBack());
		  }
		  
		 
	  }
	  
	  }

		  catch(WmsParseException e){
			  System.out.println("exception caught");
			  //System.exit(1);
			  if(e.hasPrevious)e.previous.printStackTrace();
			  System.out.print(e.traceBack());
			  System.exit(1);
		  }
	  
	  
/*  expr = xpath.compile("//Layer/Layer");

  result = expr.evaluate(doc, XPathConstants.NODESET);
  
 NodeList nodes = (NodeList) result;
  for (int i = 0; i < nodes.getLength(); i++) {
	  expr = xpath.compile("./Name/text()");
	  result = expr.evaluate(nodes.item(i), XPathConstants.STRING);
 System.out.println(result); 
 expr = xpath.compile("./Title/text()");
 result = expr.evaluate(nodes.item(i), XPathConstants.STRING);
System.out.println(result); 
expr = xpath.compile("./Abstract/text()");
result = expr.evaluate(nodes.item(i), XPathConstants.STRING);
System.out.println(result);
  }*/
  }
  
  public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}
  
  
  
  
  
  
  
}