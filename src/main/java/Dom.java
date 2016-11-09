//java -classpath ../../../work/dom4j-1.6.1.jar:. Dom

import org.dom4j.*;
import org.dom4j.io.*;
import java.util.Iterator;
import java.util.List;

public class Dom {

    public static void main(String[] args) {

	//String xml = "<root><!--comment1--><element name='hoge'>elementBODY</element><!--comment2--><xxx name='xname'>xbody</xxx><!--comment3--></root>";
	Dom dom = new Dom();
	String fname = args[0];
	dom.execxml(fname);
	return;

    }

    public void execxml(String fname) {
	SAXReader reader = new SAXReader();
	try {
	    
	    Document doc = reader.read(fname);
	    Element root = doc.getRootElement();
	    for (Iterator i = root.elementIterator(); i.hasNext();) {
		Element elem = (Element) i.next();
		Element elem2 = elem.element("job");
		System.out.println(elem2.getStringValue());
	    }

	    List nodes = doc.selectNodes("//job");
	    for (Iterator i = nodes.iterator(); i.hasNext();) {
		Node n = (Node) i.next();
		System.out.println(n.getText());
	    }
		
	} catch (DocumentException e) {
	    e.printStackTrace();
	}
	return;

    }

}
