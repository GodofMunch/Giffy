package main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ConfigParams {

    public static String dbip;
    public static String dbPort;
    public static String dbName;

    public static void loadConfig() {
        File configFile = new File("C:/Users/HP/Desktop/couchdb/src/main/resources/giffyConfig.xml");

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(configFile);
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName("config");

            for(int i = 0; i < nodes.getLength(); i ++){

                Node node = nodes.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;

                    dbip = e.getElementsByTagName("dbip").item(0).getTextContent().trim();
                    dbPort = e.getElementsByTagName("dbPort").item(0).getTextContent().trim();
                    dbName = e.getElementsByTagName("dbName").item(0).getTextContent().trim();
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
