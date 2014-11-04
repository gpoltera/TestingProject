/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

/**
 *
 * @author Gian
 */
public class XMLParser {

    private Document doc;
    private Element rootElement;

    public XMLParser() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        createECDHRoot();
    }

    private void createECDHRoot() {
        // root element    
        rootElement = doc.createElement("ecdh");
        doc.appendChild(rootElement);
    }

    public void addECDHRequest(String id, String value) {
        Element requestElement = doc.createElement("request");
        rootElement.appendChild(requestElement);

        // set request attribute
        requestElement.setAttribute("id", id);

        // add value
        Element valueElement = doc.createElement("value");
        valueElement.appendChild(doc.createTextNode(value));
        requestElement.appendChild(valueElement);
    }

    public String getXMLasString() {
        String result = "";
        DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
        LSSerializer lsSerializer = domImplementation.createLSSerializer();
        result = lsSerializer.writeToString(doc);

        return result;
    }

    public static void main(String[] args) {
        XMLParser xml = new XMLParser();
        xml.addECDHRequest("AERS", "value1");
        xml.addECDHRequest("sdsd", "value2");
        xml.addECDHRequest("dfdf", "value3");
        System.out.println(xml.getXMLasString());
    }
}
