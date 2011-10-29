/**
 * 
 */
package uk.ac.bodc.utils.wmscapstest;


import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import uk.co.bodc.utils.exceptions.WmsParseException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import javax.xml.parsers.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 * @author olly
 *
 */
public class WebMapService {
	
	private URL url;
	private XPathExpression expr;
	private XPath xpath;
	private Document doc;
	private String title;
	private String wmsAbstract;
	private String baseUrl;
	private List<WmsLayer> layers;
	private Object result;
	
	/**
	 * @param url
	 * @throws WmsParseException 
	 */
	public WebMapService(String url) throws WmsParseException {
		try {
			this.url = new URL(url+"?request=GetCapabilities&service=wms&version=1.1.0");
			System.out.print("creating new WMS object from \n"+ url+"?request=getcapabilities&service=wms&version=1.1.0");
			populate();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw new WmsParseException("URL Malformed : "+this.url, e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			throw new WmsParseException("Sax Parse error : "+this.url, this.doc.toString(), e);
		} catch (IOException e) {
System.out.println("error");
throw new WmsParseException("Wms Error using : "+this.url, e);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private void populate() throws SAXException, IOException, ParserConfigurationException, XPathExpressionException, WmsParseException, TransformerException {
		
		  DocumentBuilderFactory domFactory =   DocumentBuilderFactory.newInstance();
		  domFactory.setNamespaceAware(true); 
		  DocumentBuilder builder = domFactory.newDocumentBuilder();

		  BufferedReader in = new BufferedReader( new InputStreamReader(this.url.openStream()));
		  String inputLine;
		  	String sb = new String();
			while ((inputLine = in.readLine()) != null) {
			//System.out.println(inputLine);
			if(inputLine.contains("DOCTYPE")){
				
			}
			else{
			sb += inputLine;
			}
			}
			in.close();
			//System.out.println(sb);
			
		  this.doc = builder.parse(new InputSource(new StringReader(sb)));
		  
		  this.xpath = XPathFactory.newInstance().newXPath();
		   // XPath Query for showing all nodes value
		  this.xpath.setNamespaceContext(new PersonalNamespaceContext());
		  testException();
		  setTitle();
		  setWmsAbstract();
		  setBaseUrl();
		  setLayers();
		  
		
	}



	private void testException() throws WmsParseException, TransformerException, IOException {
		try {
		expr = xpath.compile("//ServiceException");

			result = expr.evaluate(doc, XPathConstants.STRING);
			if(result.toString().length()>0){
				throw new WmsParseException("Service Exceptmion @ url : "+this.url, stringFromDoc(this.doc));

			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			throw new WmsParseException("Service Exuuception @ url : "+this.url,stringFromDoc(this.doc), e);
		}
		
	}
	public String stringFromDoc(Document doc) throws TransformerException, IOException{
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);
		Source source = new DOMSource(doc);
		transformer.transform(source, result);
		writer.close();
		String xml = writer.toString();
		return xml;
	}


	public String getTitle() {
		return title;
	}



	private void setTitle() throws XPathExpressionException {
		expr = xpath.compile("/WMT_MS_Capabilities/Service/Title/text()");
		result = expr.evaluate(doc, XPathConstants.STRING);
		this.title = (String)result;
	}



	public String getWmsAbstract() {
		return wmsAbstract;
	}



	private void setWmsAbstract() throws XPathExpressionException {
		expr = xpath.compile("/WMT_MS_Capabilities/Service/Abstract/text()");
		result = expr.evaluate(doc, XPathConstants.STRING);
		this.wmsAbstract = (String)result;
	}



	public String getBaseUrl() {
		return baseUrl;
	}



	private void setBaseUrl() throws XPathExpressionException {
		 expr = xpath.compile("/WMT_MS_Capabilities/Capability/Request/GetMap/DCPType/HTTP/Get/OnlineResource/@xlink:href");
		 result = expr.evaluate(doc, XPathConstants.STRING);
		 this.baseUrl = (String)result;
	}



	public List<WmsLayer> getLayers() {
		return layers;
	}
	
	/*
	 * TO DO pick up BBOX to parent layer using xpath //Layer[count(Layer)>0 and ..//..[count(Layer)<1]] this way all child layers should fall within this
	 */


	private void setLayers() throws XPathExpressionException {
		//had to use find any layer with no children as using [@queryable] was not working?!?
		//DERP the distinguishing feature of a CALLABLE LAYER IS A Name tag!!!!!!!
		expr = xpath.compile("//Layer[count(Name)=1]");
		ArrayList<WmsLayer> temp = new ArrayList<WmsLayer>();
		  result = expr.evaluate(doc, XPathConstants.NODESET);
		  
		 NodeList nodes = (NodeList) result;
		  for (int i = 0; i < nodes.getLength(); i++) {
			  WmsLayer l = new WmsLayer(nodes.item(i), this.baseUrl);
			  temp.add(l);
		  }
		this.layers = temp;
	}
	
	
	
	
			  

}
