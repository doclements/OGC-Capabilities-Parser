package uk.ac.bodc.utils.wmscapstest;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

public class PersonalNamespaceContext implements NamespaceContext {

	@Override
	public String getNamespaceURI(String prefix) {
		 if (prefix == null) throw new NullPointerException("Null prefix");
	        else if ("xlink".equals(prefix)) return "http://www.w3.org/1999/xlink";
	        else if ("xml".equals(prefix)) return XMLConstants.XML_NS_URI;
	        return XMLConstants.NULL_NS_URI;
	}

	@Override
	public String getPrefix(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator getPrefixes(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
