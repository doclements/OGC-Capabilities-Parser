/**
 * 
 */
package uk.ac.bodc.utils.wmscapstest;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.co.bodc.utils.exceptions.WmsParseException;

/**
 * @author olly
 *
 */
public class WmsLayer {
	
	private String title;
	private String layerName;
	private String layerAbstract;
	private List<String> crs;
	private String bbox;
	private Node node;
	private XPathExpression expr;
	private XPath xpath;
	private Object result;
	String baseUrl;
	/**
	 * 
	 */
	public WmsLayer(Node node, String baseUrl) {
		this.baseUrl = baseUrl;
		this.xpath = XPathFactory.newInstance().newXPath();
		   // XPath Query for showing all nodes value
		  this.xpath.setNamespaceContext(new PersonalNamespaceContext());
		this.node = node;
		try {
			populate();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void populate() throws XPathExpressionException {
		setTitle();
		setLayerAbstract();
		setLayerName();
		setCrs();
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 * @throws XPathExpressionException 
	 */
	public void setTitle() throws XPathExpressionException {
		expr = xpath.compile("./Title/text()");
		 result = expr.evaluate(this.node, XPathConstants.STRING);
		 System.out.println("adding layer title "+result);
		 this.title = (String)result;
	}
	/**
	 * @return the layerName
	 */
	public String getLayerName() {
		return layerName;
	}
	/**
	 * @param layerName the layerName to set
	 * @throws XPathExpressionException 
	 */
	public void setLayerName() throws XPathExpressionException {
		 expr = xpath.compile("./Name/text()");
		 result = expr.evaluate(this.node, XPathConstants.STRING);
		 this.layerName = (String)result;
	}
	/**
	 * @return the layerAbstract
	 */
	public String getLayerAbstract() {
		return layerAbstract;
	}
	/**
	 * @param layerAbstract the layerAbstract to set
	 * @throws XPathExpressionException 
	 */
	public void setLayerAbstract() throws XPathExpressionException {
		expr = xpath.compile("./Abstract/text()");
		result = expr.evaluate(this.node, XPathConstants.STRING);
		this.layerAbstract = (String)result;
	}
	/**
	 * @return the crs
	 */
	public List<String> getCrs() {
		return crs;
	}
	/**
	 * @param crs the crs to set
	 * @throws XPathExpressionException 
	 */
	public void setCrs() throws XPathExpressionException {
		expr = xpath.compile(".//SRS");

		  result = expr.evaluate(this.node, XPathConstants.NODESET);
		  ArrayList<String> c = new ArrayList<String>();
		 NodeList nodes = (NodeList) result;
		 System.out.println("adding srs to layer"+this.getTitle());
		  for (int i = 0; i < nodes.getLength(); i++) {
			  System.out.println("inside loop "+i+"   "+nodes.getLength());
			  expr = xpath.compile("./text()");
			  result = expr.evaluate(nodes.item(i), XPathConstants.STRING);
			  String res = (String)result;
			  String[] te;
			  if (res.contains(" ")){
				  te = res.split(" ");
				  for (String d : te){
					  c.add(d);
				  }
			  }
			  else{
			  c.add((String)result);
			  }
		  }
		this.crs = c;
	}
	/**
	 * @return the bbox
	 */
	public String getBbox() {
		return bbox;
	}
	/**
	 * @param bbox the bbox to set
	 */
	public void setBbox(String bbox) {
		this.bbox = bbox;
	}
	
	public void getLayerDemo(String dest) throws IOException, WmsParseException{
		String getMap = this.baseUrl +
		  		"?request=GetMap&styles=visual_bright&service=wms&" +
		  		"layers="+this.getLayerName()+"&bbox=-180,-60,180,84&" +
		  		"format=image/jpeg&" +
		  		"width=1280&height=720&version=1.1.1&" +
		  		"srs=EPSG:4326&transparent=true";
		saveImage(getMap, dest);
		
	}
	
	private static void saveImage(String imageUrl, String destinationFile) throws IOException, WmsParseException {
		URL url = new URL(imageUrl);
		URLConnection con = url.openConnection();
		if(!con.getContentType().equals("image/jpeg")){
			InputStream is = con.getInputStream();
			BufferedReader in = new BufferedReader(
                    new InputStreamReader(is));
					String inputLine;
					String out = "";
					while ((inputLine = in.readLine()) != null) {
							out += inputLine;
					}
					in.close();
			throw new WmsParseException("Image production Failed", out);
		}
		InputStream is = con.getInputStream();
		
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
