/**
 * 
 */
package uk.co.bodc.utils.exceptions;

/**
 * @author olly
 *
 */
public class WmsParseException extends Exception {
	private String msg;
	private String xmlMsg = "";
	public Exception previous;
	private String separator = "\n";
	public boolean hasPrevious = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 318516080411073207L;

	/**
	 * 
	 */
	public WmsParseException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public WmsParseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WmsParseException(String msg, String xml, Exception previous){
		this.hasPrevious = true;
		this.msg = msg;
		this.xmlMsg = xml;
		this.previous = previous;
	}
	public WmsParseException(String msg, String xml){
		super(msg);
		this.msg = msg;
		this.xmlMsg = xml;
	}
	public WmsParseException(String msg, Exception previous){
		this.hasPrevious = true;
		System.out.println("exception created");
		this.msg = msg;
		this.previous = previous;
	}
	/**
	 * @param cause
	 */
	public WmsParseException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WmsParseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	public String traceBack() {
	    return traceBack("\n");
	  }  

	  public String traceBack(String sep) {

		  this.separator = sep;
	  
	    String text = line("Wms Parse Exception");
	    
	      text += line("Message: " + this.msg );
	      text += line("Original Exception returned from server     : " + this.xmlMsg);
	      
	      
	   // text += line(e.previous.toString());
	    return text;
	  }  
	  private String line(String s) {
		    return s + separator;
		  }  
}
